/*
 * File: EvaluationFormOptionResponse.java
 * 
 * Description: Data Transfer Object representing an option for an evaluation form question.
 *              Contains information about the option's unique identifier, the related question,
 *              the option text, and whether the option is correct.
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified Date: 2025-07-05
 */
package rw.ac.ilpd.mis.shared.dto.evaluation;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluationFormOptionResponse {
    private String optionText;
    private Boolean isCorrect;
}