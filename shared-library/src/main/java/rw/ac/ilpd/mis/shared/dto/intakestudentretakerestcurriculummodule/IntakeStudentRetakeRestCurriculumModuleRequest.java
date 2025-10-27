/**
 * IntakeStudentRetakeRestCurriculumModuleRequest DTO.
 * Represents the request payload for managing a student's retake or rest status on a curriculum module.
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.intakestudentretakerestcurriculummodule;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntakeStudentRetakeRestCurriculumModuleRequest {

    @NotBlank(message = "Student ID cannot be null or blank")
    private String studentId;

    @NotBlank(message = "Intake ID cannot be null or blank")
    private String intakeId;

    @NotBlank(message = "Curriculum module ID cannot be null or blank")
    private String curriculumModuleId;

    @NotBlank(message = "Type cannot be null or blank")
    private String type;
}
