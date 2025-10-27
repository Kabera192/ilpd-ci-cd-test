/*
 * File: EvaluationFormQuestionResponse.java
 * 
 * Description: Data Transfer Object representing a question in an evaluation form.
 *              Encapsulates details such as question text, group and section IDs, weight, and type.
 *              Includes validation constraints to ensure required fields are present and valid.
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified: 2025-07-05
 */
package rw.ac.ilpd.sharedlibrary.dto.evaluation;

import lombok.*;
import rw.ac.ilpd.mis.shared.dto.evaluation.EvaluationFormOptionResponse;
import rw.ac.ilpd.mis.shared.enums.QuestionType;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluationFormQuestionResponse {
    private String id;
    private String groupId;
    private String questionText;
    private double weight;
    private String sectionId;
    private QuestionType type;
    private List<EvaluationFormOptionResponse> options;
}