package rw.ac.ilpd.hostelservice.analytics.payment;

import lombok.*;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Schema(description = "Payment status distribution")
public class PaymentStatusDistribution {
//    @Schema(description = "Not complete payments count", example = "195")
    private Long notComplete;

//    @Schema(description = "Pending payments count", example = "120")
    private Long pending;

//    @Schema(description = "Completed payments count", example = "890")
    private Long completed;

//    @Schema(description = "Failed payments count", example = "45")
    private Long failed;

//    @Schema(description = "Cancelled payments count", example = "15")
    private Long cancelled;

//    @Schema(description = "Status distribution percentages")
    private Map<String, Double> percentages;
}