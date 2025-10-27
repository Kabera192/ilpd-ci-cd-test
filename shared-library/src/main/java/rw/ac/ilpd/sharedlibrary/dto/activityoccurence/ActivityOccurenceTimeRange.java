package rw.ac.ilpd.sharedlibrary.dto.activityoccurence;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityOccurenceTimeRange {
    private LocalDate day;
    private LocalTime startTime;
    private LocalTime endTime;
}
