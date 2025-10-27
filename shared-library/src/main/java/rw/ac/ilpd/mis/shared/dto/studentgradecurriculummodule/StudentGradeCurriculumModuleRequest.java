/**
 * Request payload for submitting a student's grade for a curriculum module in the ILPD system.
 *
 * <p>This DTO includes student ID, curriculum module ID, intake ID, grade, and pass/fail status.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.studentgradecurriculummodule;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentGradeCurriculumModuleRequest {

    @NotBlank(message = "Student ID cannot be null or blank")
    private String studentId;

    @NotBlank(message = "Curriculum module ID cannot be null or blank")
    private String curriculumModuleId;

    @NotBlank(message = "Intake ID cannot be null or blank")
    private String intakeId;

    @NotNull(message = "Grade cannot be null")
    @DecimalMin(value = "0.0", message = "Grade cannot be negative")
    @DecimalMax(value = "100.0", message = "Grade cannot exceed 100")
    private BigDecimal grade;
//
//    @NotNull(message = "Result status cannot be null or blank")
//    private ResultStatus resultStatus;
}
