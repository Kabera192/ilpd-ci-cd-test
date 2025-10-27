/**
 * Request payload for submitting a student's grade for a curriculum module in the ILPD system.
 *
 * <p>This DTO includes student ID, curriculum module ID, intake ID, grade, and pass/fail status.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.studentgradecurriculummodule;

import lombok.*;
import jakarta.validation.constraints.*;
import rw.ac.ilpd.sharedlibrary.dto.validation.uuid.ValidUuid;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentGradeCurriculumModuleRequest {

    @NotBlank(message = "Student is required")
    @ValidUuid(message = "Student is required")
    private String studentId;

    @NotBlank(message = "Curriculum module is required")
    @ValidUuid(message = "Wrong curriculum module identifier format")
    private String curriculumModuleId;

    @NotBlank(message = "Intake is required")
    @ValidUuid(message = "Wrong intake identifier format")
    private String intakeId;

    @NotNull(message = "Grade cannot be null")
    @DecimalMin(value = "0.0", message = "Grade cannot be negative")
    @DecimalMax(value = "100.0", message = "Grade cannot exceed 100")
    private BigDecimal grade;
//
//    @NotNull(message = "Result status cannot be null or blank")
//    private ResultStatus resultStatus;
}
