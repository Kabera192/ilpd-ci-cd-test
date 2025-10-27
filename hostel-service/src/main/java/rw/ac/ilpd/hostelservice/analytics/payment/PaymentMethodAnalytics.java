package rw.ac.ilpd.hostelservice.analytics.payment;

import lombok.*;

import java.math.BigDecimal;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Schema(description = "Payment method analytics")
public class PaymentMethodAnalytics {
//    @Schema(description = "Online payments count", example = "650")
    private Long onlinePayments;

//    @Schema(description = "Bank transfer payments count", example = "320")
    private Long bankTransferPayments;

//    @Schema(description = "Cash payments count", example = "180")
    private Long cashPayments;

//    @Schema(description = "Mobile money payments count", example = "75")
    private Long mobileMoneyPayments;

//    @Schema(description = "Credit card payments count", example = "25")
    private Long creditCardPayments;

//    @Schema(description = "Method distribution percentages")
    private Map<String, Double> methodPercentages;

//    @Schema(description = "Average amount by payment method")
    private Map<String, BigDecimal> averageAmountByMethod;
}