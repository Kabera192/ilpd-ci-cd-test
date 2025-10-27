/**
 * IntakeStudentRequest DTO.
 * Represents the request payload for associating a student with an intake, including enforcement details and status.
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.intakestudent;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;
import rw.ac.ilpd.sharedlibrary.enums.StudentStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntakeStudentRequest {

    @NotBlank(message = "Student ID cannot be null or blank")
    @RestrictedString
    private String studentId;

    @NotBlank(message = "Intake ID cannot be null or blank")
    @RestrictedString
    private String intakeId;

    private Boolean isEnforced; // Optional (defaults to false)
    @RestrictedString
    private String enforcementComment; // Optional

    @NotBlank(message = "Status cannot be null or blank")
    @RestrictedString
    private String status;

    private Boolean isRetaking; // Optional
}
