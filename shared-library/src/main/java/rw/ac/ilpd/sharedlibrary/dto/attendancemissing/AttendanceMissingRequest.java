/**
 * This file defines the AttendanceMissingRequest DTO used for handling attendance missing data.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.attendancemissing;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceMissingRequest {

    @NotBlank(message = "Activity occurrence ID cannot be null")
    @RestrictedString
    private String activityOccurrenceId;

    @NotBlank(message = "User ID cannot be null")
    @RestrictedString
    private String userId;
}
