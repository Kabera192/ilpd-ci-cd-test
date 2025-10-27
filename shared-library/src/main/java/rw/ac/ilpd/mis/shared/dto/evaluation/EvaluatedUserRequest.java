/*
 * File: EvaluatedUserRequest.java
 * 
 * Description: Data Transfer Object representing a user who has been evaluated.
 *              Contains information about the evaluated user, the evaluation form, and their unique identifiers.
 *              Fields:
 *                - id: Unique identifier for the evaluated user record.
 *                - evaluationFormId: Unique identifier of the evaluation form. Cannot be null.
 *                - userId: Unique identifier of the user being evaluated. Cannot be null.
 *              Validation:
 *                - evaluationFormId and userId are required fields and must not be null.
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified: 2025-07-05
 */
package rw.ac.ilpd.mis.shared.dto.evaluation;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluatedUserRequest {

    @NotBlank(message = "Evaluation form ID is required")
    private String evaluationFormId;

    @NotBlank(message = "User ID is required")
    private String userId;
}