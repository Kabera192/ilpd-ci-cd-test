/*
 * File: EvaluationFormUserFillerResponse.java
 * 
 * Description: 
 *   Data Transfer Object representing a user who fills out an evaluation form.
 *   Contains information about the evaluation form, user level, and related identifiers.
 * 
 *   Fields:
 *     - id: Unique identifier for the EvaluationFormUserFiller.
 *     - evaluationFormId: Unique identifier of the evaluation form being filled (required).
 *     - level: The level of the user filling the form (required).
 *     - levelId: Unique identifier for the user's level.
 *     - roleId: Unique identifier for the user's role.
 *     - intakeId: Unique identifier for the intake associated with the user.
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified Date: 2025-07-05
 */
package rw.ac.ilpd.sharedlibrary.dto.evaluation;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluationFormUserFillerResponse {
    private String id;
    private String level;

    private String levelId;

    private String roleId;

    private String intakeId;
}