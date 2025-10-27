package rw.ac.ilpd.hostelservice.analytics.payment;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Schema(description = "Daily deletion data")
public class DailyDeletionData {
//    @Schema(description = "Date", example = "2024-08-03")
    private String date;

//    @Schema(description = "Number of deletions", example = "3")
    private Long deletions;
}
