/**
 * This file defines the AttendanceMissingRequest DTO used for handling attendance missing data.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.attendancemissing;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceMissingRequest {

    @NotBlank(message = "Activity occurrence ID cannot be null")
    private String activityOccurrenceId;

    @NotBlank(message = "User ID cannot be null")
    private String userId;
}
