/*
 * File: ModuleComponentAssessmentDto.java
 * 
 * Description: Data Transfer Object representing the assessment of a module component.
 *              This DTO encapsulates all necessary information related to a module component assessment,
 *              including identifiers, metadata, and validation constraints.
 *              Fields include:
 *                - id: Unique identifier for the assessment.
 *                - componentId: Unique identifier for the component being assessed. (Required)
 *                - moduleId: Unique identifier for the module. (Required)
 *                - intakeId: Unique identifier for the intake. (Required)
 *                - title: Title of the assessment. Must not be blank and cannot exceed 100 characters. (Required)
 *                - description: Description of the assessment. Must not be blank. (Required)
 *                - postedBy: Identifier of the user who posted the assessment. (Required)
 *                - dueDate: Due date for the assessment. (Required)
 *                - assessmentTypeId: Identifier for the type of assessment. (Required)
 *                - status: Status of the assessment. (Required)
 *                - evaluationFormsId: Identifier for the evaluation forms associated with the assessment. (Required)
 *                - createdAt: Timestamp when the assessment was created. (Required)
 *                - updatedAt: Timestamp when the assessment was last updated. (Required)
 *                - mode: Mode of the assessment whether it is an individual or group assessment. (Required)
 *                - maxGrade: Maximum grade for the assessment. Must be a positive value. (Required)
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified: 2025-07-07
 */
package rw.ac.ilpd.mis.shared.dto.assessment;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModuleComponentAssessmentRequest {

    @NotBlank(message = "Component ID is required")
    private String componentId;

    @NotBlank(message = "Module ID is required")
    private String moduleId;

    @NotBlank(message = "Intake ID is required")
    private String intakeId;

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotBlank(message = "Posted by user ID is required")
    private String postedBy;

    @NotBlank(message = "Due date is required")
    private String dueDate;

    @NotBlank(message = "Assessment type ID is required")
    private String assessmentTypeId;

    @NotBlank(message = "Status is required")
    private String status;

    @NotBlank(message = "Evaluation forms ID is required")
    private String evaluationFormsId;

    @NotBlank(message = "Mode is required")
    private String mode;

    @NotNull(message = "Max grade is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Max grade must be positive")
    private BigDecimal maxGrade;
}
