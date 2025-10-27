package rw.ac.ilpd.hostelservice.analytics.payment;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RiskMetrics {
//    @Schema(description = "High-value payments (>10000)", example = "15")
    private Long highValuePayments;

//    @Schema(description = "Suspicious activity count", example = "3")
    private Long suspiciousActivities;

//    @Schema(description = "Failed payment rate", example = "3.6")
    private Double failedPaymentRate;

//    @Schema(description = "Average processing time (minutes)", example = "12.5")
    private Double averageProcessingTime;

//    @Schema(description = "Payments requiring review", example = "8")
    private Long paymentsRequiringReview;
}