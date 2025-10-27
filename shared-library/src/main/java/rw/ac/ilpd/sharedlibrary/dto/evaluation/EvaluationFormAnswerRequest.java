/*
 * File: EvaluationFormAnswerRequest.java
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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluationFormAnswerRequest {

    @NotBlank(message = "Question ID is required")
    @RestrictedString
    private String questionId;

    private String selectedOptionId;

    private String writtenAnswer;

    @Email(message = "This field expects an email")
    private String email;
}