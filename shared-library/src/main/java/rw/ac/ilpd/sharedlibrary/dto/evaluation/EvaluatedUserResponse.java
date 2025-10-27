/*
 * File: EvaluatedUserDto.java
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
package rw.ac.ilpd.sharedlibrary.dto.evaluation;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluatedUserResponse {
    private String id;
    private String evaluationFormId;
    private String userId;
}