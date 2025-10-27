/*
 * File: EvaluationFormAnswerResponse.java
 * 
 * Description: Data Transfer Object representing an answer to an evaluation form question.
 *              Contains fields for question ID, selected option, written answer, awarded marks,
 *              respondent's email, and creation timestamp. Validation annotations enforce required
 *              fields and constraints.
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
public class EvaluationFormAnswerResponse {
    private String id;
    private String questionId;
    private String selectedOptionId;

    private String userAnsweredId;
    private String writtenAnswer;
    private Integer awardedMarks;
    private String email;
    private String createdAt;
}