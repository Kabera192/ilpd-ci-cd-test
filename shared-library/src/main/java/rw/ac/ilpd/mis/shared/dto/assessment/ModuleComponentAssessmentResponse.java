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

import lombok.*;
import rw.ac.ilpd.mis.shared.dto.assessmentattachment.AssessmentAttachmentResponse;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModuleComponentAssessmentResponse {
    private String id;
    private String componentId;
    private String moduleId;
    private String intakeId;
    private String title;
    private String description;
    private String postedBy;
    private String dueDate;
    private String assessmentTypeId;
    private String status;
    private String evaluationFormsId;
    private String createdAt;
    private String updatedAt;
    private String mode;
    private List<AssessmentAttachmentResponse> attachmentResponses;
    private BigDecimal maxGrade;
}
