/**
 * IntakeAssessmentGroupRequest DTO.
 * Represents the request payload for creating or updating an intake assessment group, including component, intake, and group name.
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.intakeassessmentgroup;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import rw.ac.ilpd.mis.shared.dto.assessment.IntakeAssessmentGroupStudentRequest;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntakeAssessmentGroupRequest {

    @NotBlank(message = "Component ID cannot be null or blank")
    private String componentId;

    @NotBlank(message = "Intake ID cannot be null or blank")
    private String intakeId;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    private List<IntakeAssessmentGroupStudentRequest> students;
}
