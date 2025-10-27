/*
 * File: EvaluationFormResponse.java
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
package rw.ac.ilpd.sharedlibrary.dto.evaluation;

import lombok.*;

import java.util.UUID;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluationFormResponse {
    private String id;
    private String title;
    private String description;
    private String typeId;
    private String createdAt;
    private String createdByUserId;
    private UUID evaluatedUser;
    private String status;
    private String groupId;
}