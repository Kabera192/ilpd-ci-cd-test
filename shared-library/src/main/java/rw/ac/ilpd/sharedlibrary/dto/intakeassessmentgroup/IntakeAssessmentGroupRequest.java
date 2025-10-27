/**
 * IntakeAssessmentGroupRequest DTO.
 * Represents the request payload for creating or updating an intake assessment group, including component, intake, and group name.
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.intakeassessmentgroup;

import lombok.*;
import jakarta.validation.constraints.*;
import rw.ac.ilpd.sharedlibrary.dto.assessment.IntakeAssessmentGroupStudentRequest;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntakeAssessmentGroupRequest {

    @NotBlank(message = "Component ID cannot be null or blank")
    @RestrictedString
    private String componentId;

    @NotBlank(message = "Intake ID cannot be null or blank")
    @RestrictedString
    private String intakeId;

    @NotBlank(message = "Name cannot be blank")
    @RestrictedString
    private String name;

    private List<IntakeAssessmentGroupStudentRequest> students;
}
