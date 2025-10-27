package rw.ac.ilpd.hostelservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.hostelservice.analytics.payment.*;
import rw.ac.ilpd.hostelservice.mapper.HostelReservationRoomPaymentMapper;
import rw.ac.ilpd.hostelservice.model.nosql.document.HostelReservationRoomPayment;
import rw.ac.ilpd.hostelservice.model.nosql.embedding.PaymentDocument;
import rw.ac.ilpd.hostelservice.repository.nosql.HostelReservationRoomPaymentRepository;
import rw.ac.ilpd.sharedlibrary.enums.PaymentDocumentStatus;
import rw.ac.ilpd.sharedlibrary.enums.PaymentMethod;
import rw.ac.ilpd.sharedlibrary.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentDashboardService {

    private final HostelReservationRoomPaymentRepository paymentRepository;
    private final HostelReservationRoomPaymentMapper paymentMapper;

    public PaymentDashboardResponse getDashboardData() {
        log.info("Generating payment dashboard data");

        List<HostelReservationRoomPayment> allPayments = paymentRepository.findAll();

        return PaymentDashboardResponse.builder()
                .paymentOverview(generatePaymentOverview(allPayments))
                .statusDistribution(generateStatusDistribution(allPayments))
                .methodAnalytics(generateMethodAnalytics(allPayments))
                .revenueAnalytics(generateRevenueAnalytics(allPayments))
                .documentAnalytics(generateDocumentAnalytics(allPayments))
                .deletionMetrics(generateDeletionMetrics(allPayments))
                .recentActivities(generateRecentActivities())
                .trendAnalytics(generateTrendAnalytics(allPayments))
                .riskMetrics(generateRiskMetrics(allPayments))
                .build();
    }

    private PaymentOverviewStats generatePaymentOverview(List<HostelReservationRoomPayment> payments) {
        long totalPayments = payments.size();
        BigDecimal totalAmount = payments.stream()
                .map(HostelReservationRoomPayment::getAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal averageAmount = totalPayments > 0 ?
                totalAmount.divide(BigDecimal.valueOf(totalPayments), 2, BigDecimal.ROUND_HALF_UP) :
                BigDecimal.ZERO;

        long completedPayments = payments.stream()
                .mapToLong(p -> PaymentStatus.COMPLETED.equals(p.getPaymentStatus()) ? 1 : 0)
                .sum();

        long pendingPayments = payments.stream()
                .mapToLong(p -> PaymentStatus.NOT_COMPLETE.equals(p.getPaymentStatus()) ? 1 : 0)
                .sum();

        long failedPayments = payments.stream()
                .mapToLong(p -> PaymentStatus.NOT_COMPLETE.equals(p.getPaymentStatus()) ? 1 : 0)
                .sum();

        double completionRate = totalPayments > 0 ?
                (double) completedPayments / totalPayments * 100 : 0;

        LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        long todayPayments = payments.stream()
                .mapToLong(p -> p.getCreatedAt() != null && p.getCreatedAt().isAfter(today) ? 1 : 0)
                .sum();

        LocalDateTime monthStart = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        long monthlyPayments = payments.stream()
                .mapToLong(p -> p.getCreatedAt() != null && p.getCreatedAt().isAfter(monthStart) ? 1 : 0)
                .sum();

        return PaymentOverviewStats.builder()
                .totalPayments(totalPayments)
                .totalAmount(totalAmount)
                .averageAmount(averageAmount)
                .completedPayments(completedPayments)
                .pendingPayments(pendingPayments)
                .failedPayments(failedPayments)
                .completionRate(Math.round(completionRate * 100.0) / 100.0)
                .todayPayments(todayPayments)
                .monthlyPayments(monthlyPayments)
                .build();
    }

    private PaymentStatusDistribution generateStatusDistribution(List<HostelReservationRoomPayment> payments) {
        Map<PaymentStatus, Long> statusCounts = payments.stream()
                .collect(Collectors.groupingBy(
                        HostelReservationRoomPayment::getPaymentStatus,
                        Collectors.counting()
                ));

        long total = payments.size();
        Map<String, Double> percentages = statusCounts.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().name(),
                        entry -> total > 0 ? Math.round((double) entry.getValue() / total * 10000.0) / 100.0 : 0.0
                ));

        return PaymentStatusDistribution.builder()
                .notComplete(statusCounts.getOrDefault(PaymentStatus.NOT_COMPLETE, 0L))
                .pending(statusCounts.getOrDefault(PaymentStatus.NOT_COMPLETE, 0L))
                .completed(statusCounts.getOrDefault(PaymentStatus.COMPLETED, 0L))
                .failed(statusCounts.getOrDefault(PaymentStatus.NOT_COMPLETE, 0L))
                .cancelled(statusCounts.getOrDefault(PaymentStatus.NOT_COMPLETE, 0L))
                .percentages(percentages)
                .build();
    }

    private PaymentMethodAnalytics generateMethodAnalytics(List<HostelReservationRoomPayment> payments) {
        Map<PaymentMethod, Long> methodCounts = payments.stream()
                .collect(Collectors.groupingBy(
                        HostelReservationRoomPayment::getPaymentMethod,
                        Collectors.counting()
                ));

        Map<PaymentMethod, BigDecimal> methodAmounts = payments.stream()
                .filter(p -> p.getAmount() != null)
                .collect(Collectors.groupingBy(
                        HostelReservationRoomPayment::getPaymentMethod,
                        Collectors.reducing(BigDecimal.ZERO,
                                HostelReservationRoomPayment::getAmount,
                                BigDecimal::add)
                ));

        long total = payments.size();
        Map<String, Double> methodPercentages = methodCounts.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().name(),
                        entry -> total > 0 ? Math.round((double) entry.getValue() / total * 10000.0) / 100.0 : 0.0
                ));

        Map<String, BigDecimal> averageAmountByMethod = methodCounts.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().name(),
                        entry -> {
                            BigDecimal totalAmount = methodAmounts.getOrDefault(entry.getKey(), BigDecimal.ZERO);
                            long count = entry.getValue();
                            return count > 0 ?
                                    totalAmount.divide(BigDecimal.valueOf(count), 2, BigDecimal.ROUND_HALF_UP) :
                                    BigDecimal.ZERO;
                        }
                ));

        return PaymentMethodAnalytics.builder()
                .onlinePayments(methodCounts.getOrDefault(PaymentMethod.ONLINE, 0L))
                .bankTransferPayments(methodCounts.getOrDefault(PaymentMethod.CASH, 0L))
                .cashPayments(methodCounts.getOrDefault(PaymentMethod.CASH, 0L))
                .mobileMoneyPayments(methodCounts.getOrDefault(PaymentMethod.ONLINE, 0L))
                .creditCardPayments(methodCounts.getOrDefault(PaymentMethod.ONLINE, 0L))
                .methodPercentages(methodPercentages)
                .averageAmountByMethod(averageAmountByMethod)
                .build();
    }

    private RevenueAnalytics generateRevenueAnalytics(List<HostelReservationRoomPayment> payments) {
        List<HostelReservationRoomPayment> completedPayments = payments.stream()
                .filter(p -> PaymentStatus.COMPLETED.equals(p.getPaymentStatus()))
                .collect(Collectors.toList());

        BigDecimal totalRevenue = completedPayments.stream()
                .map(HostelReservationRoomPayment::getAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        LocalDateTime monthStart = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        BigDecimal monthlyRevenue = completedPayments.stream()
                .filter(p -> p.getCreatedAt() != null && p.getCreatedAt().isAfter(monthStart))
                .map(HostelReservationRoomPayment::getAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        BigDecimal dailyRevenue = completedPayments.stream()
                .filter(p -> p.getCreatedAt() != null && p.getCreatedAt().isAfter(today))
                .map(HostelReservationRoomPayment::getAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calculate revenue growth (simplified - comparing this month to last month)
        LocalDateTime lastMonthStart = monthStart.minusMonths(1);
        BigDecimal lastMonthRevenue = completedPayments.stream()
                .filter(p -> p.getCreatedAt() != null &&
                        p.getCreatedAt().isAfter(lastMonthStart) &&
                        p.getCreatedAt().isBefore(monthStart))
                .map(HostelReservationRoomPayment::getAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        double revenueGrowth = 0.0;
        if (lastMonthRevenue.compareTo(BigDecimal.ZERO) > 0) {
            revenueGrowth = monthlyRevenue.subtract(lastMonthRevenue)
                    .divide(lastMonthRevenue, 4, BigDecimal.ROUND_HALF_UP)
                    .multiply(BigDecimal.valueOf(100))
                    .doubleValue();
        }

        Map<String, BigDecimal> revenueByMethod = completedPayments.stream()
                .filter(p -> p.getAmount() != null)
                .collect(Collectors.groupingBy(
                        p -> p.getPaymentMethod().name(),
                        Collectors.reducing(BigDecimal.ZERO,
                                HostelReservationRoomPayment::getAmount,
                                BigDecimal::add)
                ));

        List<MonthlyRevenue> monthlyTrend = generateMonthlyRevenueTrend(completedPayments);

        return RevenueAnalytics.builder()
                .totalRevenue(totalRevenue)
                .monthlyRevenue(monthlyRevenue)
                .dailyRevenue(dailyRevenue)
                .revenueGrowth(Math.round(revenueGrowth * 100.0) / 100.0)
                .revenueByMethod(revenueByMethod)
                .monthlyTrend(monthlyTrend)
                .build();
    }

    private List<MonthlyRevenue> generateMonthlyRevenueTrend(List<HostelReservationRoomPayment> completedPayments) {
        Map<String, List<HostelReservationRoomPayment>> paymentsByMonth = completedPayments.stream()
                .filter(p -> p.getCreatedAt() != null)
                .collect(Collectors.groupingBy(p ->
                        p.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM"))
                ));

        return paymentsByMonth.entrySet().stream()
                .map(entry -> {
                    String month = entry.getKey();
                    List<HostelReservationRoomPayment> monthPayments = entry.getValue();
                    BigDecimal amount = monthPayments.stream()
                            .map(HostelReservationRoomPayment::getAmount)
                            .filter(Objects::nonNull)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    return MonthlyRevenue.builder()
                            .month(month)
                            .amount(amount)
                            .paymentCount((long) monthPayments.size())
                            .build();
                })
                .sorted(Comparator.comparing(MonthlyRevenue::getMonth))
                .collect(Collectors.toList());
    }

    private DocumentAnalytics generateDocumentAnalytics(List<HostelReservationRoomPayment> payments) {
        List<PaymentDocument> allDocuments = payments.stream()
                .filter(p -> p.getPaymentDocuments() != null)
                .flatMap(p -> p.getPaymentDocuments().stream())
                .collect(Collectors.toList());

        long totalDocuments = allDocuments.size();

        Map<PaymentDocumentStatus, Long> statusCounts = allDocuments.stream()
                .collect(Collectors.groupingBy(
                        PaymentDocument::getPaymentDocumentStatus,
                        Collectors.counting()
                ));

        long verifiedDocuments = statusCounts.getOrDefault(PaymentDocumentStatus.ACCEPTED, 0L);
        long pendingVerification = statusCounts.getOrDefault(PaymentDocumentStatus.PENDING, 0L);
        long rejectedDocuments = statusCounts.getOrDefault(PaymentDocumentStatus.REJECTED, 0L);

        // Simulated deleted documents count (you'd track this separately in real implementation)
        long deletedDocuments = (long) (totalDocuments * 0.023); // 2.3% deletion rate

        double verificationRate = totalDocuments > 0 ?
                (double) verifiedDocuments / totalDocuments * 100 : 0;

        double averageDocumentsPerPayment = payments.size() > 0 ?
                (double) totalDocuments / payments.size() : 0;

        Map<String, Long> statusDistribution = statusCounts.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().name(),
                        Map.Entry::getValue
                ));

        return DocumentAnalytics.builder()
                .totalDocuments(totalDocuments)
                .verifiedDocuments(verifiedDocuments)
                .pendingVerification(pendingVerification)
                .rejectedDocuments(rejectedDocuments)
                .deletedDocuments(deletedDocuments)
                .verificationRate(Math.round(verificationRate * 100.0) / 100.0)
                .averageDocumentsPerPayment(Math.round(averageDocumentsPerPayment * 100.0) / 100.0)
                .statusDistribution(statusDistribution)
                .build();
    }

    private DocumentDeletionMetrics generateDeletionMetrics(List<HostelReservationRoomPayment> payments) {
        List<PaymentDocument> allDocuments = payments.stream()
                .filter(p -> p.getPaymentDocuments() != null)
                .flatMap(p -> p.getPaymentDocuments().stream())
                .collect(Collectors.toList());

        // Simulated deletion metrics (in real implementation, you'd track actual deletions)
        long totalDocuments = allDocuments.size();
        long monthlyDeletions = (long) (totalDocuments * 0.018); // 1.8% monthly deletion rate
        long weeklyDeletions = (long) (monthlyDeletions * 0.25); // ~25% of monthly deletions per week

        double deletionRate = totalDocuments > 0 ?
                (double) monthlyDeletions / totalDocuments * 100 : 0;

        List<DailyDeletionData> dailyDeletionTrend = generateDailyDeletionTrend();

        // Simulated deletion reasons
        Map<String, Long> deletionReasons = Map.of(
                "DUPLICATE_DOCUMENT", monthlyDeletions * 35 / 100,
                "INCORRECT_FORMAT", monthlyDeletions * 25 / 100,
                "USER_REQUESTED", monthlyDeletions * 20 / 100,
                "EXPIRED_DOCUMENT", monthlyDeletions * 15 / 100,
                "SYSTEM_CLEANUP", monthlyDeletions * 5 / 100
        );

        // Simulated deletion patterns by hour
        Map<Integer, Long> deletionsByHour = generateDeletionsByHour();

        List<UserDeletionActivity> topDeletionUsers = generateTopDeletionUsers();

        double averageTimeToDelete = 24.5; // hours

        return DocumentDeletionMetrics.builder()
                .monthlyDeletions(monthlyDeletions)
                .weeklyDeletions(weeklyDeletions)
                .deletionRate(Math.round(deletionRate * 100.0) / 100.0)
                .dailyDeletionTrend(dailyDeletionTrend)
                .deletionReasons(deletionReasons)
                .deletionsByHour(deletionsByHour)
                .topDeletionUsers(topDeletionUsers)
                .averageTimeToDelete(averageTimeToDelete)
                .build();
    }

    private List<DailyDeletionData> generateDailyDeletionTrend() {
        List<DailyDeletionData> trend = new ArrayList<>();
        LocalDateTime date = LocalDateTime.now().minusDays(30);

        for (int i = 0; i < 30; i++) {
            // Simulate realistic deletion patterns
            long deletions = (long) (Math.random() * 5); // 0-4 deletions per day
            trend.add(DailyDeletionData.builder()
                    .date(date.plusDays(i).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .deletions(deletions)
                    .build());
        }

        return trend;
    }

    private Map<Integer, Long> generateDeletionsByHour() {
        Map<Integer, Long> deletionsByHour = new HashMap<>();

        // Simulate peak deletion hours (typically during business hours)
        for (int hour = 0; hour < 24; hour++) {
            long deletions;
            if (hour >= 9 && hour <= 17) {
                deletions = (long) (Math.random() * 8 + 2); // 2-10 deletions during business hours
            } else {
                deletions = (long) (Math.random() * 3); // 0-2 deletions outside business hours
            }
            deletionsByHour.put(hour, deletions);
        }

        return deletionsByHour;
    }

    private List<UserDeletionActivity> generateTopDeletionUsers() {
        List<UserDeletionActivity> topUsers = new ArrayList<>();

        // Simulate top deletion users
        String[] userIds = {"user-001", "user-002", "user-003", "user-004", "user-005"};

        for (String userId : userIds) {
            topUsers.add(UserDeletionActivity.builder()
                    .userId(userId)
                    .deletionCount((long) (Math.random() * 15 + 5)) // 5-20 deletions
                    .lastDeletion(LocalDateTime.now().minusHours((long) (Math.random() * 72))) // Last 3 days
                    .build());
        }

        return topUsers.stream()
                .sorted(Comparator.comparing(UserDeletionActivity::getDeletionCount).reversed())
                .collect(Collectors.toList());
    }

    private List<RecentPaymentActivity> generateRecentActivities() {
        // This would typically come from an audit/activity log table
        List<RecentPaymentActivity> activities = new ArrayList<>();

        String[] activityTypes = {"PAYMENT_CREATED", "PAYMENT_COMPLETED", "PAYMENT_FAILED", "DOCUMENT_UPLOADED", "DOCUMENT_VERIFIED"};
        String[] userIds = {"user-001", "user-002", "user-003", "user-004", "user-005"};

        for (int i = 0; i < 10; i++) {
            activities.add(RecentPaymentActivity.builder()
                    .paymentId("pay-" + UUID.randomUUID().toString().substring(0, 8))
                    .activityType(activityTypes[(int) (Math.random() * activityTypes.length)])
                    .amount(BigDecimal.valueOf(Math.random() * 5000 + 100))
                    .status(PaymentStatus.values()[(int) (Math.random() * PaymentStatus.values().length)].name())
                    .timestamp(LocalDateTime.now().minusHours((long) (Math.random() * 48)))
                    .userId(userIds[(int) (Math.random() * userIds.length)])
                    .build());
        }

        return activities.stream()
                .sorted(Comparator.comparing(RecentPaymentActivity::getTimestamp).reversed())
                .collect(Collectors.toList());
    }

    private PaymentTrendAnalytics generateTrendAnalytics(List<HostelReservationRoomPayment> payments) {
        List<DailyPaymentData> dailyTrend = generateDailyPaymentTrend(payments);

        // Calculate weekly and monthly growth
        LocalDateTime weekAgo = LocalDateTime.now().minusWeeks(1);
        LocalDateTime twoWeeksAgo = LocalDateTime.now().minusWeeks(2);
        LocalDateTime monthAgo = LocalDateTime.now().minusMonths(1);
        LocalDateTime twoMonthsAgo = LocalDateTime.now().minusMonths(2);

        long thisWeekPayments = payments.stream()
                .mapToLong(p -> p.getCreatedAt() != null && p.getCreatedAt().isAfter(weekAgo) ? 1 : 0)
                .sum();

        long lastWeekPayments = payments.stream()
                .mapToLong(p -> p.getCreatedAt() != null &&
                        p.getCreatedAt().isAfter(twoWeeksAgo) &&
                        p.getCreatedAt().isBefore(weekAgo) ? 1 : 0)
                .sum();

        long thisMonthPayments = payments.stream()
                .mapToLong(p -> p.getCreatedAt() != null && p.getCreatedAt().isAfter(monthAgo) ? 1 : 0)
                .sum();

        long lastMonthPayments = payments.stream()
                .mapToLong(p -> p.getCreatedAt() != null &&
                        p.getCreatedAt().isAfter(twoMonthsAgo) &&
                        p.getCreatedAt().isBefore(monthAgo) ? 1 : 0)
                .sum();

        double weeklyGrowth = lastWeekPayments > 0 ?
                ((double) thisWeekPayments - lastWeekPayments) / lastWeekPayments * 100 : 0;

        double monthlyGrowth = lastMonthPayments > 0 ?
                ((double) thisMonthPayments - lastMonthPayments) / lastMonthPayments * 100 : 0;

        Map<Integer, Long> paymentsByHour = generatePaymentsByHour(payments);
        Map<String, Long> paymentsByDayOfWeek = generatePaymentsByDayOfWeek(payments);

        return PaymentTrendAnalytics.builder()
                .dailyTrend(dailyTrend)
                .weeklyGrowth(Math.round(weeklyGrowth * 100.0) / 100.0)
                .monthlyGrowth(Math.round(monthlyGrowth * 100.0) / 100.0)
                .paymentsByHour(paymentsByHour)
                .paymentsByDayOfWeek(paymentsByDayOfWeek)
                .build();
    }

    private List<DailyPaymentData> generateDailyPaymentTrend(List<HostelReservationRoomPayment> payments) {
        Map<String, List<HostelReservationRoomPayment>> paymentsByDate = payments.stream()
                .filter(p -> p.getCreatedAt() != null)
                .filter(p -> p.getCreatedAt().isAfter(LocalDateTime.now().minusDays(30)))
                .collect(Collectors.groupingBy(p ->
                        p.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                ));

        List<DailyPaymentData> dailyTrend = new ArrayList<>();
        LocalDateTime date = LocalDateTime.now().minusDays(30);

        for (int i = 0; i < 30; i++) {
            String dateStr = date.plusDays(i).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            List<HostelReservationRoomPayment> dayPayments = paymentsByDate.getOrDefault(dateStr, new ArrayList<>());

            BigDecimal dayAmount = dayPayments.stream()
                    .map(HostelReservationRoomPayment::getAmount)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            dailyTrend.add(DailyPaymentData.builder()
                    .date(dateStr)
                    .count((long) dayPayments.size())
                    .amount(dayAmount)
                    .build());
        }

        return dailyTrend;
    }

    private Map<Integer, Long> generatePaymentsByHour(List<HostelReservationRoomPayment> payments) {
        return payments.stream()
                .filter(p -> p.getCreatedAt() != null)
                .collect(Collectors.groupingBy(
                        p -> p.getCreatedAt().getHour(),
                        Collectors.counting()
                ));
    }

    private Map<String, Long> generatePaymentsByDayOfWeek(List<HostelReservationRoomPayment> payments) {
        return payments.stream()
                .filter(p -> p.getCreatedAt() != null)
                .collect(Collectors.groupingBy(
                        p -> p.getCreatedAt().getDayOfWeek().name(),
                        Collectors.counting()
                ));
    }

    private RiskMetrics generateRiskMetrics(List<HostelReservationRoomPayment> payments) {
        BigDecimal highValueThreshold = BigDecimal.valueOf(10000);

        long highValuePayments = payments.stream()
                .filter(p -> p.getAmount() != null)
                .mapToLong(p -> p.getAmount().compareTo(highValueThreshold) > 0 ? 1 : 0)
                .sum();

        // Simulate suspicious activities (would be based on actual risk rules)
        long suspiciousActivities = (long) (payments.size() * 0.002); // 0.2% suspicious rate

        long failedPayments = payments.stream()
                .mapToLong(p -> PaymentStatus.NOT_COMPLETE.equals(p.getPaymentStatus()) ? 1 : 0)
                .sum();

        double failedPaymentRate = payments.size() > 0 ?
                (double) failedPayments / payments.size() * 100 : 0;

        // Simulate average processing time
        double averageProcessingTime = 12.5; // minutes

        // Payments requiring manual review
        long paymentsRequiringReview = highValuePayments + suspiciousActivities;

        return RiskMetrics.builder()
                .highValuePayments(highValuePayments)
                .suspiciousActivities(suspiciousActivities)
                .failedPaymentRate(Math.round(failedPaymentRate * 100.0) / 100.0)
                .averageProcessingTime(averageProcessingTime)
                .paymentsRequiringReview(paymentsRequiringReview)
                .build();
    }
}
