/**
 * This file defines the ActivityOccurenceRequest DTO used to represent
 * individual occurrences of a scheduled activity within the system.
 *
 * Author: Mohamed Gaye
 * Last Changed Date: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.activityoccurence;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityOccurenceRequest {

    // TODO: REMOVE THIS FIELD SINCE ACTIVITY_OCCURRENCE IS AN EMBEDDED CLASS
//    private String activityId;

    @NotNull(message = "Day cannot be null")
    private LocalDate day;

    @NotBlank(message = "Lecturer ID cannot be null")
    @RestrictedString
    private String lecturerId;

    private LocalTime startTime;

    private LocalTime endTime;

    private Boolean hasDoneAttendance; // Nullable

    
}
