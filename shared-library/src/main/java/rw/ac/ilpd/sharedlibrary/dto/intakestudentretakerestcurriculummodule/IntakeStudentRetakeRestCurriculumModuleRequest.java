/**
 * IntakeStudentRetakeRestCurriculumModuleRequest DTO.
 * Represents the request payload for managing a student's retake or rest status on a curriculum module.
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.intakestudentretakerestcurriculummodule;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;
import rw.ac.ilpd.sharedlibrary.enums.ModuleRetakeResitType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntakeStudentRetakeRestCurriculumModuleRequest {

    @NotBlank(message = "Student ID cannot be null or blank")
    @RestrictedString
    private String studentId;

    @NotBlank(message = "Intake ID cannot be null or blank")
    @RestrictedString
    private String intakeId;

    @NotBlank(message = "Curriculum module ID cannot be null or blank")
    @RestrictedString
    private String curriculumModuleId;

    @NotBlank(message = "Type cannot be null or blank")
    @RestrictedString
    private String type;
}
