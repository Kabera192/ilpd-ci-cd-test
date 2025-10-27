package rw.ac.ilpd.academicservice.scheduler.builder;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.time.LocalDate;
import java.util.*;

public abstract class TaskBuilder {

    private final ThreadPoolTaskScheduler scheduler;
    private final Map<String, ScheduledFuture<?>> tasks = new ConcurrentHashMap<>();
    private final AtomicReference<String> lastScheduledTask = new AtomicReference<>(null);

    protected TaskBuilder() {
        this.scheduler = new ThreadPoolTaskScheduler();
        this.scheduler.setThreadNamePrefix("virtual-scheduler-");
        this.scheduler.setWaitForTasksToCompleteOnShutdown(true);
        this.scheduler.setAwaitTerminationSeconds(60);
        this.scheduler.initialize();
    }

    // Wrap runnable into a Virtual Thread with auto-cleanup for one-time tasks
    private Runnable wrapVirtual(String taskName, Runnable task, boolean isOneTime) {
        return () -> {
            Thread.startVirtualThread(() -> {
                try {
                    System.out.printf("▶ [%s] running in %s%n", taskName, Thread.currentThread());
                    task.run();
                    System.out.printf("✅ [%s] completed successfully%n", taskName);

                    // Auto-cleanup for one-time tasks
                    if (isOneTime) {
                        System.out.printf("🧹 [%s] auto-removing one-time task%n", taskName);
                        removeCompletedTask(taskName);
                    }
                } catch (Exception e) {
                    System.out.printf("❌ [%s] failed with error: %s%n", taskName, e.getMessage());
                    e.printStackTrace();

                    // Still cleanup even if failed for one-time tasks
                    if (isOneTime) {
                        removeCompletedTask(taskName);
                    }
                }
            });
        };
    }

    // Backward compatibility wrapper
    private Runnable wrapVirtual(String taskName, Runnable task) {
        return wrapVirtual(taskName, task, false);
    }

    // Helper method to safely remove completed tasks
    private void removeCompletedTask(String taskName) {
        ScheduledFuture<?> future = tasks.remove(taskName);
        if (future != null && future.isDone()) {
            lastScheduledTask.compareAndSet(taskName, null);
            System.out.printf("🗑️ [%s] removed from active tasks%n", taskName);
        }
    }

    // Validate cron expression format
    private void validateCronExpression(String cronExpression) {
        if (cronExpression == null || cronExpression.trim().isEmpty()) {
            throw new IllegalArgumentException("Cron expression cannot be null or empty");
        }
        // Skip validation for special expressions like @reboot
        if (cronExpression.startsWith("@")) {
            return;
        }
        String[] parts = cronExpression.trim().split("\\s+");
        if (parts.length != 6) {
            throw new IllegalArgumentException("Cron expression must have exactly 6 fields: second minute hour day month dayOfWeek");
        }
    }

    protected void scheduleWithCron(String taskName, Runnable task, String cronExpression, boolean replaceExisting, boolean isOneTime) {
        validateCronExpression(cronExpression);
        System.out.println("Replace Existing task: " + replaceExisting);
        // Cancel existing task if it exists and replacement is enabled
        if (replaceExisting && tasks.containsKey(taskName)) {
            cancelTask(taskName);
            System.out.printf("🔄 [%s] replaced existing task%n", taskName);
        } else if (tasks.containsKey(taskName)) {
            System.out.printf("⚠️ [%s] task already exists, skipping scheduling%n", taskName);
            return;
        }

        try {
            ScheduledFuture<?> future = scheduler.schedule(
                    wrapVirtual(taskName, task, isOneTime),
                    new CronTrigger(cronExpression)
            );
            tasks.put(taskName, future);
            lastScheduledTask.set(taskName);
            System.out.printf("✅ [%s] scheduled with cron: %s %s%n",
                    taskName, cronExpression, isOneTime ? "(one-time)" : "(recurring)");
        } catch (Exception e) {
            System.out.printf("❌ Failed to schedule [%s]: %s%n", taskName, e.getMessage());
            throw e;
        }
    }

    // Backward compatibility overloads
    protected void scheduleWithCron(String taskName, Runnable task, String cronExpression, boolean replaceExisting) {
        scheduleWithCron(taskName, task, cronExpression, replaceExisting, false);
    }

    // Overloaded method for backward compatibility (defaults to replace existing)
    protected void scheduleWithCron(String taskName, Runnable task, String cronExpression) {
        scheduleWithCron(taskName, task, cronExpression, true, false);
    }

    public boolean cancelTask(String taskName) {
        ScheduledFuture<?> future = tasks.remove(taskName);
        if (future != null) {
            boolean cancelled = future.cancel(false);
            // Clear lastScheduledTask if we're cancelling it
            lastScheduledTask.compareAndSet(taskName, null);

            if (cancelled) {
                System.out.printf("❌ [%s] task cancelled successfully%n", taskName);
            } else {
                System.out.printf("⚠️ [%s] task could not be cancelled (may have already completed)%n", taskName);
            }
            return cancelled;
        }
        System.out.printf("❓ [%s] task not found%n", taskName);
        return false;
    }

    // Cancel the most recently scheduled task
    public boolean cancelLastTask() {
        String lastTask = lastScheduledTask.get();
        if (lastTask == null || !tasks.containsKey(lastTask)) {
            System.out.println("❓ No recent task to cancel");
            return false;
        }

        System.out.printf("🎯 Cancelling last scheduled task: %s%n", lastTask);
        return cancelTask(lastTask);
    }

    // Get list of active task names
    public Set<String> getActiveTasks() {
        return new HashSet<>(tasks.keySet());
    }

    // Get the name of the last scheduled task
    public String getLastScheduledTask() {
        return lastScheduledTask.get();
    }

    // Check if a task is currently scheduled
    public boolean isTaskScheduled(String taskName) {
        return tasks.containsKey(taskName);
    }

    // Get total number of active tasks
    public int getActiveTaskCount() {
        return tasks.size();
    }

    // Cancel all tasks
    public void cancelAllTasks() {
        System.out.printf("🧹 Cancelling all %d tasks%n", tasks.size());
        new HashSet<>(tasks.keySet()).forEach(this::cancelTask);
        lastScheduledTask.set(null);
    }

    // Cleanup completed tasks periodically
    public void cleanupCompletedTasks() {
        Set<String> completedTasks = new HashSet<>();
        tasks.forEach((name, future) -> {
            if (future.isDone()) {
                completedTasks.add(name);
            }
        });

        completedTasks.forEach(taskName -> {
            tasks.remove(taskName);
            System.out.printf("🧹 [%s] cleaned up completed task%n", taskName);
        });

        if (!completedTasks.isEmpty()) {
            System.out.printf("🗑️ Cleaned up %d completed tasks%n", completedTasks.size());
        }
    }

    public void shutdown() {
        cancelAllTasks();
        scheduler.shutdown();
        System.out.println("🛑 TaskBuilder shutdown completed");
    }

    // Start fluent builder
    public JobDefinition task(String name, Runnable action) {
        return new JobDefinition(name, action, this);
    }

    // === Inner DSL class ===
    public static class JobDefinition {
        private final String name;
        private final Runnable action;
        private final TaskBuilder parent;
        private boolean replaceExisting = true; // Default to replace existing tasks

        JobDefinition(String name, Runnable action, TaskBuilder parent) {
            this.name = name;
            this.action = action;
            this.parent = parent;
        }

        // Control whether to replace existing tasks with the same name
        public JobDefinition replaceIfExists(boolean replace) {
            this.replaceExisting = replace;
            return this;
        }

        // Convenience method to skip if task already exists
        public JobDefinition skipIfExists() {
            return replaceIfExists(false);
        }

        // ─────────────── Time-Based Schedules ───────────────

        // Every N seconds (1-59)
        public void everySeconds(int seconds) {
            if (seconds < 1 || seconds > 59) {
                throw new IllegalArgumentException("Seconds must be between 1 and 59");
            }
            parent.scheduleWithCron(name, action, String.format("*/%d * * * * *", seconds), replaceExisting, false);
        }

        // Every N minutes (1-59)
        public void everyMinutes(int minutes) {
            if (minutes < 1 || minutes > 59) {
                throw new IllegalArgumentException("Minutes must be between 1 and 59");
            }
            parent.scheduleWithCron(name, action, String.format("0 */%d * * * *", minutes), replaceExisting, false);
        }

        // Every N hours (1-23)
        public void everyHours(int hours) {
            if (hours < 1 || hours > 23) {
                throw new IllegalArgumentException("Hours must be between 1 and 23");
            }
            parent.scheduleWithCron(name, action, String.format("0 0 */%d * * *", hours), replaceExisting, false);
        }

        // Every N days (1-31)
        public void everyDays(int days) {
            if (days < 1 || days > 31) {
                throw new IllegalArgumentException("Days must be between 1 and 31");
            }
            parent.scheduleWithCron(name, action, String.format("0 0 0 */%d * *", days), replaceExisting, false);
        }

        // Every N weeks
        public void everyWeeks(int weeks) {
            if (weeks < 1) {
                throw new IllegalArgumentException("Weeks must be at least 1");
            }
            // Run every Sunday at midnight
            parent.scheduleWithCron(name, action, String.format("0 0 0 * * 0/%d", weeks * 7), replaceExisting, false);
        }

        // Every N months
        public void everyMonths(int months) {
            if (months < 1 || months > 12) {
                throw new IllegalArgumentException("Months must be between 1 and 12");
            }
            parent.scheduleWithCron(name, action, String.format("0 0 0 1 */%d *", months), replaceExisting, false);
        }

        // ─────────────── Specific Time Schedules ───────────────

        // Daily at specific time
        public void everyDayAt(int hour, int minute) {
            validateTime(hour, minute);
            parent.scheduleWithCron(name, action, String.format("0 %d %d * * *", minute, hour), replaceExisting, false);
        }

        // Daily at specific time with seconds
        public void everyDayAt(int hour, int minute, int second) {
            validateTime(hour, minute, second);
            parent.scheduleWithCron(name, action, String.format("%d %d %d * * *", second, minute, hour), replaceExisting, false);
        }

        // Weekly on specific day and time
        public void everyWeekAt(DayOfWeek day, int hour, int minute) {
            validateTime(hour, minute);
            parent.scheduleWithCron(name, action, String.format("0 %d %d * * %d", minute, hour, day.getValue() % 7), replaceExisting, false);
        }

        // Monthly on specific day and time
        public void everyMonthOn(int dayOfMonth, int hour, int minute) {
            if (dayOfMonth < 1 || dayOfMonth > 31) {
                throw new IllegalArgumentException("Day of month must be between 1 and 31");
            }
            validateTime(hour, minute);
            parent.scheduleWithCron(name, action, String.format("0 %d %d %d * *", minute, hour, dayOfMonth), replaceExisting, false);
        }

        // Yearly on specific month, day and time
        public void everyYearOn(int month, int day, int hour, int minute) {
            if (month < 1 || month > 12) {
                throw new IllegalArgumentException("Month must be between 1 and 12");
            }
            if (day < 1 || day > 31) {
                throw new IllegalArgumentException("Day must be between 1 and 31");
            }
            validateTime(hour, minute);
            parent.scheduleWithCron(name, action, String.format("0 %d %d %d %d *", minute, hour, day, month), replaceExisting, false);
        }

        // ─────────────── Business Schedules ───────────────

        // Weekdays only (Monday-Friday)
        public void everyWeekdayAt(int hour, int minute) {
            validateTime(hour, minute);
            parent.scheduleWithCron(name, action, String.format("0 %d %d * * 1-5", minute, hour), replaceExisting, false);
        }

        // Weekends only (Saturday-Sunday)
        public void everyWeekendAt(int hour, int minute) {
            validateTime(hour, minute);
            parent.scheduleWithCron(name, action, String.format("0 %d %d * * 0,6", minute, hour), replaceExisting, false);
        }

        // Business hours every N minutes (9 AM - 5 PM, weekdays)
        public void duringBusinessHoursEvery(int minutes) {
            if (minutes < 1 || minutes > 59) {
                throw new IllegalArgumentException("Minutes must be between 1 and 59");
            }
            parent.scheduleWithCron(name, action, String.format("0 */%d 9-17 * * 1-5", minutes), replaceExisting, false);
        }

        // ─────────────── Special Schedules ───────────────

        // At startup (runs once immediately)
        public void atStartup() {
            parent.scheduleWithCron(name, action, "@reboot", replaceExisting, false);
        }

        // At midnight daily
        public void atMidnight() {
            parent.scheduleWithCron(name, action, "0 0 0 * * *", replaceExisting, false);
        }

        // At noon daily
        public void atNoon() {
            parent.scheduleWithCron(name, action, "0 0 12 * * *", replaceExisting, false);
        }

        // First day of month
        public void firstDayOfMonth(int hour, int minute) {
            validateTime(hour, minute);
            parent.scheduleWithCron(name, action, String.format("0 %d %d 1 * *", minute, hour), replaceExisting, false);
        }

        // Last day of month (approximation - runs on 28th to catch all months)
        public void lastDayOfMonth(int hour, int minute) {
            validateTime(hour, minute);
            parent.scheduleWithCron(name, action, String.format("0 %d %d 28-31 * *", minute, hour), replaceExisting, false);
        }

        // Quarter hours (00, 15, 30, 45 minutes)
        public void everyQuarterHour() {
            parent.scheduleWithCron(name, action, "0 0,15,30,45 * * * *", replaceExisting, false);
        }

        // Half hours (00, 30 minutes)
        public void everyHalfHour() {
            parent.scheduleWithCron(name, action, "0 0,30 * * * *", replaceExisting, false);
        }

        // Multiple times per day
        public void multipleTimesDaily(int... hours) {
            if (hours.length == 0) {
                throw new IllegalArgumentException("At least one hour must be specified");
            }
            String hoursList = Arrays.stream(hours)
                    .mapToObj(String::valueOf)
                    .reduce((a, b) -> a + "," + b)
                    .orElse("0");
            parent.scheduleWithCron(name, action, String.format("0 0 %s * * *", hoursList), replaceExisting, false);
        }

        // Specific days of week
        public void onDaysOfWeek(int hour, int minute, DayOfWeek... days) {
            validateTime(hour, minute);
            if (days.length == 0) {
                throw new IllegalArgumentException("At least one day must be specified");
            }
            String daysList = Arrays.stream(days)
                    .mapToInt(day -> day.getValue() % 7)
                    .mapToObj(String::valueOf)
                    .reduce((a, b) -> a + "," + b)
                    .orElse("0");
            parent.scheduleWithCron(name, action, String.format("0 %d %d * * %s", minute, hour, daysList), replaceExisting, false);
        }

        // Specific months
        public void onMonths(int dayOfMonth, int hour, int minute, int... months) {
            if (dayOfMonth < 1 || dayOfMonth > 31) {
                throw new IllegalArgumentException("Day of month must be between 1 and 31");
            }
            validateTime(hour, minute);
            if (months.length == 0) {
                throw new IllegalArgumentException("At least one month must be specified");
            }
            String monthsList = Arrays.stream(months)
                    .mapToObj(String::valueOf)
                    .reduce((a, b) -> a + "," + b)
                    .orElse("1");
            parent.scheduleWithCron(name, action, String.format("0 %d %d %d %s *", minute, hour, dayOfMonth, monthsList), replaceExisting, false);
        }

        // ─────────────── Advanced Schedules ───────────────

        // Between specific hours (e.g., 9 AM to 5 PM)
        public void betweenHours(int startHour, int endHour, int intervalMinutes) {
            if (startHour < 0 || startHour > 23 || endHour < 0 || endHour > 23) {
                throw new IllegalArgumentException("Hours must be between 0 and 23");
            }
            if (intervalMinutes < 1 || intervalMinutes > 59) {
                throw new IllegalArgumentException("Interval minutes must be between 1 and 59");
            }
            parent.scheduleWithCron(name, action, String.format("0 */%d %d-%d * * *", intervalMinutes, startHour, endHour), replaceExisting, false);
        }

        // Conditional schedules based on day ranges
        public void onDaysOfMonth(int hour, int minute, int... days) {
            validateTime(hour, minute);
            if (days.length == 0) {
                throw new IllegalArgumentException("At least one day must be specified");
            }
            String daysList = Arrays.stream(days)
                    .mapToObj(String::valueOf)
                    .reduce((a, b) -> a + "," + b)
                    .orElse("1");
            parent.scheduleWithCron(name, action, String.format("0 %d %d %s * *", minute, hour, daysList), replaceExisting, false);
        }

        // ─────────────── One-Time Schedules ───────────────

        // Run once at specific date and time (auto-cleanup)
        public void onceAt(int month, int day, int hour, int minute) {
            validateTime(hour, minute);
            if (month < 1 || month > 12) {
                throw new IllegalArgumentException("Month must be between 1 and 12");
            }
            if (day < 1 || day > 31) {
                throw new IllegalArgumentException("Day must be between 1 and 31");
            }
            String cronExpr = String.format("0 %d %d %d %d ?", minute, hour, day, month);
            parent.scheduleWithCron(name, action, cronExpr, replaceExisting, true);
        }

        // Run once today at specific time
        public void todayAt(int hour, int minute) {
            validateTime(hour, minute);
            LocalDate today = LocalDate.now();
            onceAt(today.getMonthValue(), today.getDayOfMonth(), hour, minute);
        }

        // Run once tomorrow at specific time
        public void tomorrowAt(int hour, int minute) {
            validateTime(hour, minute);
            LocalDate tomorrow = LocalDate.now().plusDays(1);
            onceAt( tomorrow.getMonthValue(), tomorrow.getDayOfMonth(), hour, minute);
        }

        // Raw cron support (for power users)
        public void cron(String cronExpression) {
            parent.scheduleWithCron(name, action, cronExpression, replaceExisting, false);
        }

        // One-time cron with auto-cleanup
        public void cronOnce(String cronExpression) {
            parent.scheduleWithCron(name, action, cronExpression, replaceExisting, true);
        }

        // ─────────────── Helper Methods ───────────────

        private void validateTime(int hour, int minute) {
            if (hour < 0 || hour > 23) {
                throw new IllegalArgumentException("Hour must be between 0 and 23");
            }
            if (minute < 0 || minute > 59) {
                throw new IllegalArgumentException("Minute must be between 0 and 59");
            }
        }

        private void validateTime(int hour, int minute, int second) {
            validateTime(hour, minute);
            if (second < 0 || second > 59) {
                throw new IllegalArgumentException("Second must be between 0 and 59");
            }
        }
    }
}