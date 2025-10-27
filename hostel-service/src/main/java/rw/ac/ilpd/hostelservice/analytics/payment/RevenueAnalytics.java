package rw.ac.ilpd.hostelservice.analytics.payment;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Schema(description = "Revenue analytics")
public class RevenueAnalytics {
//    @Schema(description = "Total revenue", example = "485750.50")
    private BigDecimal totalRevenue;

//    @Schema(description = "This month's revenue", example = "125430.75")
    private BigDecimal monthlyRevenue;

//    @Schema(description = "Today's revenue", example = "8750.25")
    private BigDecimal dailyRevenue;

//    @Schema(description = "Revenue growth percentage", example = "15.5")
    private Double revenueGrowth;

//    @Schema(description = "Revenue by payment method")
    private Map<String, BigDecimal> revenueByMethod;

//    @Schema(description = "Monthly revenue trend (last 12 months)")
    private List<MonthlyRevenue> monthlyTrend;
}
