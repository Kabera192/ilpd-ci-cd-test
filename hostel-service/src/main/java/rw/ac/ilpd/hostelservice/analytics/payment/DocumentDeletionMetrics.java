package rw.ac.ilpd.hostelservice.analytics.payment;

import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Schema(description = "Document deletion rate metrics")
public class DocumentDeletionMetrics {
//    @Schema(description = "Total deletions this month", example = "25")
    private Long monthlyDeletions;

//    @Schema(description = "Total deletions this week", example = "8")
    private Long weeklyDeletions;

//    @Schema(description = "Deletion rate percentage", example = "2.3")
    private Double deletionRate;

//    @Schema(description = "Daily deletion trend (last 30 days)")
    private List<DailyDeletionData> dailyDeletionTrend;

//    @Schema(description = "Deletion reasons distribution")
    private Map<String, Long> deletionReasons;

//    @Schema(description = "Most common deletion time of day")
    private Map<Integer, Long> deletionsByHour;

//    @Schema(description = "User deletion activity")
    private List<UserDeletionActivity> topDeletionUsers;

//    @Schema(description = "Average time between upload and deletion (hours)", example = "24.5")
    private Double averageTimeToDelete;
}
