package rw.ac.ilpd.hostelservice.analytics.payment;

import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Schema(description = "Payment trend analytics")
public class PaymentTrendAnalytics {
//    @Schema(description = "Daily payment counts (last 30 days)")
    private List<DailyPaymentData> dailyTrend;

//    @Schema(description = "Weekly payment growth", example = "12.5")
    private Double weeklyGrowth;

//    @Schema(description = "Monthly payment growth", example = "8.3")
    private Double monthlyGrowth;

//    @Schema(description = "Peak payment hours")
    private Map<Integer, Long> paymentsByHour;

//    @Schema(description = "Peak payment days of week")
    private Map<String, Long> paymentsByDayOfWeek;
}