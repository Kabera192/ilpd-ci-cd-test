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
package rw.ac.ilpd.mis.shared.dto.evaluation;

import jakarta.validation.constraints.*;
import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluationFormAnswerRequest {

    @NotBlank(message = "Question ID is required")
    private String questionId;

    @NotBlank(message = "Selected option ID is required")
    private String selectedOptionId;

    @NotBlank(message = "Form ID is required")
    private String formId;

    private String userAnsweredId;

    @NotNull(message = "Written answer is required")
    @NotBlank(message = "Written answer cannot be blank")
    private String writtenAnswer;

    @Positive(message = "Awarded marks must be positive")
    private Integer awardedMarks;

    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    private String email;
}