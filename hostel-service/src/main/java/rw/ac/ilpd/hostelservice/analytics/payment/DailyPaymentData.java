package rw.ac.ilpd.hostelservice.analytics.payment;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Schema(description = "Daily payment data")
public class DailyPaymentData {
//    @Schema(description = "Date", example = "2024-08-03")
    private String date;

//    @Schema(description = "Payment count", example = "25")
    private Long count;

//    @Schema(description = "Total amount", example = "8750.25")
    private BigDecimal amount;
}