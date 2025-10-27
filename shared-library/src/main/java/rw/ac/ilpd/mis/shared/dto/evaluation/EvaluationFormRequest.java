/*
 * File: EvaluationFormRequest.java
 *
 * Description:
 *   Data Transfer Object representing an Evaluation Form.
 *   Contains information about an evaluation form, including its title, description,
 *   type, creation details, status, and associated group.
 *   Validation constraints are applied to ensure required fields are provided and meet
 *   specified criteria.
 *
 *   Fields:
 *     - id: Unique identifier of the evaluation form.
 *     - title: Title of the evaluation form (required, max 100 characters).
 *     - description: Description of the evaluation form (required).
 *     - typeId: Identifier for the type of evaluation form (required).
 *     - createdAt: Timestamp when the form was created (required).
 *     - createdByUserId: Identifier of the user who created the form (required).
 *     - status: Status of the form, either PUBLISHED or PRIVATE (required).
 *     - groupId: Identifier of the group associated with the form (required).
 *
 *   The FormStatus enum defines possible statuses for the evaluation form.
 *
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified: 2025-07-05
 */
package rw.ac.ilpd.mis.shared.dto.evaluation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluationFormRequest {

    @NotNull(message = "Title is required")
    @NotBlank(message = "Title cannot be blank")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    @NotNull(message = "Description is required")
    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotBlank(message = "Type ID is required")
    private String typeId;

    @NotBlank(message = "Created by user ID is required")
    private String createdByUserId;

    @NotBlank(message = "Status is required")
    private String status;

    @NotBlank(message = "Group ID is required")
    private String groupId;
}