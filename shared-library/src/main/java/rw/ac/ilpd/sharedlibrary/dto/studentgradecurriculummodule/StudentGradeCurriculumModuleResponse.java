/**
 * Response payload representing a student's grade for a curriculum module in the ILPD system.
 *
 * <p>This DTO returns the grade record's ID, student, curriculum module, intake, grade value, creation timestamp, and pass/fail status.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.studentgradecurriculummodule;

import lombok.*;
import rw.ac.ilpd.sharedlibrary.enums.ResultStatus;

import java.math.BigDecimal;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentGradeCurriculumModuleResponse {
    private String id;
    private String studentId;
    private String curriculumModuleId;
    private String intakeId;
    private BigDecimal grade;
    private String createdAt;
    private ResultStatus resultStatus;
}
