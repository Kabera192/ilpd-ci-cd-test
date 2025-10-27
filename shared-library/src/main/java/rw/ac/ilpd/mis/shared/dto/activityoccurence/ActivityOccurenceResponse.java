/**
 * This file defines the ActivityOccurenceResponse DTO used to represent
 * the response structure for individual occurrences of a scheduled activity.
 *
 * Author: Mohamed Gaye
 * Last Changed Date: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.activityoccurence;

import lombok.*;
import rw.ac.ilpd.mis.shared.dto.attendancemissing.AttendanceMissingResponse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityOccurenceResponse {
    private String id;
    private String activityId;
    private LocalDate day;
    private LocalTime startTime;
    private LocalTime endTime;
    private String lecturerId;
    private Boolean hasDoneAttendance;
    private Boolean deletedStatus;
    private List<AttendanceMissingResponse> attendanceMissingResponses;
}
