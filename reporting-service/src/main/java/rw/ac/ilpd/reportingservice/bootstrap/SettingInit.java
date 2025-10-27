package rw.ac.ilpd.reportingservice.bootstrap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rw.ac.ilpd.reportingservice.service.SettingService;
import rw.ac.ilpd.sharedlibrary.dto.setting.SettingRequest;
import rw.ac.ilpd.sharedlibrary.dto.setting.SettingResponse;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SettingInit implements CommandLineRunner {
    private final SettingService settingService;
    private static final List<SettingRequest> settings = List.of(

            // ============================
            //  ACADEMIC SETTINGS
            // ============================
            new SettingRequest("Maximum Score",
                    "Defines the highest possible score a student can achieve in an assessment.",
                    "ACADEMIC", "MAX_SCORE", "MAX_SCORE", "100"),

            new SettingRequest("Minimum Score",
                    "Defines the lowest possible score that can be awarded in an assessment.",
                    "ACADEMIC", "MIN_SCORE", "MIN_SCORE", "5"),

            new SettingRequest("Average passing marks",
                    "Define the overall average passing mark a student must achieve on total.",
                    "ACADEMIC", "AVG_PASSING_MARK", "APM", "60"),
            new SettingRequest("Minimum Module passing marks",
                    "Define the minimum  passing mark a student must achieve on a module.",
                    "ACADEMIC", "MIN_MODULE_PASSING_MARKS", "MMPM", "50"),

            new SettingRequest(
                    "Maximum module resit pass mark",
                    "Set the minimum score a student must achieve in a module to resit.",
                    "ACADEMIC",
                    "MODULE_RESIT_PASS_MARK",
                    "MRPM",
                    "60"
            ),
            new SettingRequest(
                    "Maximum module resit pass mark",
                    "Set the minimum score a student must achieve in a module to resit.",
                    "ACADEMIC",
                    "MODULE_RESIT_PASS_MARK",
                    "MRPM",
                    "60"
            ),

            new SettingRequest("Grading Scheme",
                    "Specifies the grading system used (e.g., A-F, Percentage, or GPA-based).",
                    "ACADEMIC", "GRADING_SCHEME", "GRD", "A-F"),

            new SettingRequest("Minimum Attendance (%)",
                    "The minimum percentage of attendance required to be eligible for exams.",
                    "ACADEMIC", "MIN_ATTENDANCE_PERCENTAGE", "RAP", "75"),

            new SettingRequest("Max Exam Retake Attempts",
                    "Specifies the maximum number of times a student can retake an exam.",
                    "ACADEMIC", "MAX_EXAM_RETAKE_ATTEMPTS", "MERA", "2"),

            new SettingRequest("Max Resit Attempts",
                    "Defines how many times a student can resit a failed module or course.",
                    "ACADEMIC", "MAX_RESIT_ATTEMPTS", "MRA", "2"),

            new SettingRequest("Late Submission Penalty (%)",
                    "Percentage of marks deducted for submitting coursework after the deadline.",
                    "ACADEMIC", "LATE_SUBMISSION_PENALTY", "LSP", "10"),

            new SettingRequest("Max Credit Hours Per Semester",
                    "The maximum number of credit hours a student can register for in a semester.",
                    "ACADEMIC", "MAX_CREDIT_HOURS", "MCH", "24"),


            new SettingRequest("Drop/Add Deadline (Days)",
                    "The maximum number of days within which students can drop or add courses.",
                    "ACADEMIC", "DROP_ADD_DEADLINE_DAYS", "DAD", "7"),

            new SettingRequest("Maximum Intakes a program can have in a Year",
                    "Defines how many intakes program can have in a year.",
                    "ACADEMIC", "MAX_PROGRAM_INTAKES", "MPI", "5"),

            // ============================
            // SYSTEM SETTINGS
            // ============================
            new SettingRequest("Default Language",
                    "The default language used by the system interface and notifications.",
                    "SYSTEM", "DEFAULT_LANGUAGE", "DLANG", "en"),

            new SettingRequest("System Timezone",
                    "The default timezone used for timestamps, logs, and scheduling.",
                    "SYSTEM", "SYSTEM_TIMEZONE", "TZ", "Africa/Kigali"),

            new SettingRequest("Enable Maintenance Mode",
                    "If true, the system will display a maintenance message and restrict user access.",
                    "SYSTEM", "MAINTENANCE_MODE", "MMODE", "false"),

            new SettingRequest("Backup Frequency (Hours)",
                    "Specifies how often the system creates automatic backups.",
                    "SYSTEM", "BACKUP_FREQUENCY_HOURS", "BFH", "24"),

            new SettingRequest("File Upload Max Size (MB)",
                    "Maximum allowed size for uploaded files.",
                    "SYSTEM", "FILE_UPLOAD_MAX_SIZE_MB", "FUMS", "50"),

            new SettingRequest("Allowed File Types",
                    "Comma-separated list of file types that can be uploaded.",
                    "SYSTEM", "ALLOWED_FILE_TYPES", "AFT", "pdf,docx,jpg,png"),

            new SettingRequest("Enable Audit Logging",
                    "If true, all user actions are logged for audit purposes.",
                    "SYSTEM", "ENABLE_AUDIT_LOGGING", "AUDIT", "true"),

            new SettingRequest("Default Currency",
                    "Currency used for financial transactions and reports.",
                    "SYSTEM", "DEFAULT_CURRENCY", "DCUR", "FRW"),


            // ============================
            // 🔐 SECURITY SETTINGS
            // ============================
            new SettingRequest("Max Login Attempts",
                    "Maximum number of failed login attempts before account is locked.",
                    "SECURITY", "MAX_LOGIN_ATTEMPTS", "MLA", "5"),

            new SettingRequest("Password Expiry (Days)",
                    "Number of days after which passwords must be changed.",
                    "SECURITY", "PASSWORD_EXPIRY_DAYS", "PED", "90"),

            new SettingRequest("Session Timeout (Minutes)",
                    "Time of inactivity before user sessions expire.",
                    "SECURITY", "SESSION_TIMEOUT_MINUTES", "STM", "30"),

            new SettingRequest("Account Lock Duration (Minutes)",
                    "Duration for which an account remains locked after exceeding max login attempts.",
                    "SECURITY", "ACCOUNT_LOCK_DURATION", "ALD", "15"),

            new SettingRequest("Password Min Length",
                    "Minimum number of characters required for a password.",
                    "SECURITY", "PASSWORD_MIN_LENGTH", "PML", "10"),

            new SettingRequest("Password Complexity Required",
                    "If true, passwords must include letters, numbers, and special characters.",
                    "SECURITY", "PASSWORD_COMPLEXITY_REQUIRED", "PCR", "true"),


            // ============================
            // 📩 NOTIFICATION SETTINGS
            // ============================
            new SettingRequest("Default Email Sender",
                    "The default sender address for system-generated emails.",
                    "NOTIFICATION", "DEFAULT_EMAIL_SENDER", "DEMAIL", "noreply@system.com"),

            new SettingRequest("Enable Email Notifications",
                    "If true, users will receive email notifications.",
                    "NOTIFICATION", "ENABLE_EMAIL_NOTIFICATIONS", "ENOT", "true"),

            new SettingRequest("Enable SMS Notifications",
                    "If true, users will receive SMS notifications.",
                    "NOTIFICATION", "ENABLE_SMS_NOTIFICATIONS", "SNOT", "false"),

            new SettingRequest("Daily Reminder Time",
                    "Time of day when daily reminders are sent to users.",
                    "NOTIFICATION", "DAILY_REMINDER_TIME", "DRT", "08:00"),

            new SettingRequest("Notification Retention Days",
                    "Number of days notifications are kept before automatic deletion.",
                    "NOTIFICATION", "NOTIFICATION_RETENTION_DAYS", "NRD", "30"),


            // ============================
            // 💰 FINANCIAL SETTINGS
            // ============================
            new SettingRequest("Default Tuition Fee",
                    "The default tuition fee for new students or courses.",
                    "FINANCE", "DEFAULT_TUITION_FEE", "DTF", "1000000"),

            new SettingRequest("Late Payment Penalty (%)",
                    "Percentage penalty applied for late tuition or fee payments.",
                    "FINANCE", "LATE_PAYMENT_PENALTY", "LPP", "5"),

            new SettingRequest("Installment Allowed",
                    "If true, students can pay fees in multiple installments.",
                    "FINANCE", "INSTALLMENT_ALLOWED", "INST", "true"),

            new SettingRequest("Max Installments",
                    "Maximum number of installments allowed for a single payment.",
                    "FINANCE", "MAX_INSTALLMENTS", "MINS", "3"),

            new SettingRequest("Refund Policy Days",
                    "Number of days within which students can request a refund.",
                    "FINANCE", "REFUND_POLICY_DAYS", "RPD", "14"),
//          Banks
            new SettingRequest("Bank of Kigali (BK Urubuto)", "Bank account for receiving payments at BK Urubuto.", "BANK", "BK_URUBUTO", "BKU", "1234567890"),
            new SettingRequest("Bank of Kigali", "Bank account for receiving payments at BK .", "BANK", "BK", "BK", "1234567890"),

            //  PERFORMANCE SETTINGS
            // ============================
            new SettingRequest("Cache Expiry (Minutes)",
                    "Time after which cached data is invalidated.",
                    "PERFORMANCE", "CACHE_EXPIRY_MINUTES", "CEM", "60"),

            new SettingRequest("Enable Query Caching",
                    "If true, database queries are cached to improve performance.",
                    "PERFORMANCE", "ENABLE_QUERY_CACHING", "EQC", "true"),

            new SettingRequest("API Rate Limit (Requests/min)",
                    "Maximum number of API requests allowed per minute.",
                    "PERFORMANCE", "API_RATE_LIMIT", "ARL", "500")
    );


    @Override
    public void run(String... args) {
        log.info("🚀 Application started — initializing default data... {}",settings);
        initializeDefaultApplicationSettings();
        log.info("✅ Data initialization complete.");
    }

    private void initializeDefaultApplicationSettings() {
        log.debug("Creating default settings...");
        List<SettingResponse>savedSettings=settingService.createList(settings);
        savedSettings.forEach(savedSetting->{
            log.info(savedSetting.getId()+":"+savedSetting.getName());
        });
        log.info("Total settings created: {}.",savedSettings.size());

    }
}
