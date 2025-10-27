package rw.ac.ilpd.hostelservice.analytics.payment;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Schema(description = "Overall payment statistics")
public class PaymentOverviewStats {
//    @Schema(description = "Total number of payments", example = "1250")
    private Long totalPayments;

//    @Schema(description = "Total payment amount", example = "485750.50")
    private BigDecimal totalAmount;

//    @Schema(description = "Average payment amount", example = "388.60")
    private BigDecimal averageAmount;

//    @Schema(description = "Number of completed payments", example = "890")
    private Long completedPayments;

//    @Schema(description = "Number of pending payments", example = "120")
    private Long pendingPayments;

//    @Schema(description = "Number of failed payments", example = "45")
    private Long failedPayments;

//    @Schema(description = "Completion rate percentage", example = "71.2")
    private Double completionRate;

//    @Schema(description = "Today's payment count", example = "25")
    private Long todayPayments;

//    @Schema(description = "This month's payment count", example = "345")
    private Long monthlyPayments;
}
