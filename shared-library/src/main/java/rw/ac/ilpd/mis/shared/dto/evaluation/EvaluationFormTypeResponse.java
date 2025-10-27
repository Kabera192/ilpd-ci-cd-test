/*
 * File: EvaluationFormTypeResponse.java
 * 
 * Description: Data Transfer Object representing an Evaluation Form Type.
 *              Contains information about the evaluation form type, including its unique identifier and name.
 *              Validation constraints:
 *                - name: Must not be null, blank, and cannot exceed 100 characters.
 *
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified: 2025-07-05
 */
package rw.ac.ilpd.mis.shared.dto.evaluation;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluationFormTypeResponse {
    private String id;
    private String name;
}