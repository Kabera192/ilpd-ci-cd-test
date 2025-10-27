/**
 * IntakeStudentRetakeRestCurriculumModuleResponse DTO.
 * Represents the response payload for a student's retake or rest status on a curriculum module.
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.intakestudentretakerestcurriculummodule;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntakeStudentRetakeRestCurriculumModuleResponse {
    private String id;
    private String studentId;
    private String intakeId;
    private String curriculumModuleId;
    private String type;
}
