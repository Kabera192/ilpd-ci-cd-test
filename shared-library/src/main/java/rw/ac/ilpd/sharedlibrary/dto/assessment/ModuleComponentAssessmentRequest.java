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
package rw.ac.ilpd.sharedlibrary.dto.assessment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


import jakarta.validation.constraints.*;
import lombok.*;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;
import rw.ac.ilpd.sharedlibrary.enums.AssessmentMode;
import rw.ac.ilpd.sharedlibrary.enums.AssessmentStatus;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModuleComponentAssessmentRequest {

    @NotNull(message = "Component ID is required")
    private UUID componentId;

    @NotNull(message = "Module ID is required")
    private UUID moduleId;

    @NotNull(message = "Intake ID is required")
    private UUID intakeId;

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    @RestrictedString
    private String title;

    @NotBlank(message = "Description cannot be blank")
    @RestrictedString
    private String description;

    @NotNull(message = "The due date is required")
    @FutureOrPresent(message = "The due date cannot be in the past")
    private LocalDateTime dueDate;

    @NotNull(message = "The assessment type id is required")
    private UUID assessmentTypeId;

    private AssessmentStatus status;

    private UUID evaluationFormsId;

    private AssessmentMode mode;

    @NotNull(message = "Max grade is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Max grade must be positive")
    private BigDecimal maxGrade;
}
