package rw.ac.ilpd.hostelservice.analytics.payment;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
//@Schema(description = "Monthly revenue data")
public class MonthlyRevenue {
//    @Schema(description = "Month", example = "2024-07")
    private String month;

//    @Schema(description = "Revenue amount", example = "125430.75")
    private BigDecimal amount;

//    @Schema(description = "Payment count", example = "345")
    private Long paymentCount;
}