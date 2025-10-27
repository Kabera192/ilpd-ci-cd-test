package rw.ac.ilpd.hostelservice.analytics.payment;
import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Schema(description = "Comprehensive dashboard data for payment management")
public class PaymentDashboardResponse {
//    @Schema(description = "Overall payment statistics")
    private PaymentOverviewStats paymentOverview;

//    @Schema(description = "Payment status distribution")
    private PaymentStatusDistribution statusDistribution;

//    @Schema(description = "Payment method analytics")
    private PaymentMethodAnalytics methodAnalytics;

//    @Schema(description = "Revenue analytics")
    private RevenueAnalytics revenueAnalytics;

//    @Schema(description = "Document management statistics")
    private DocumentAnalytics documentAnalytics;

//    @Schema(description = "Document deletion rate metrics")
    private DocumentDeletionMetrics deletionMetrics;

//    @Schema(description = "Recent payment activities")
    private List<RecentPaymentActivity> recentActivities;

//    @Schema(description = "Payment trends over time")
    private PaymentTrendAnalytics trendAnalytics;

//    @Schema(description = "Risk and compliance metrics")
    private RiskMetrics riskMetrics;
}
