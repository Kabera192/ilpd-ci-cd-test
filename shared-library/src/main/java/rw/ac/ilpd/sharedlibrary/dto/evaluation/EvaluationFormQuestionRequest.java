/*
 * File: EvaluationFormQuestionRequest.java
 * 
 * Description: Data Transfer Object representing a question in an evaluation form.
 *              Encapsulates details such as question text, group and section IDs, weight, and type.
 *              Includes validation constraints to ensure required fields are present and valid.
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified: 2025-07-05
 */
package rw.ac.ilpd.sharedlibrary.dto.evaluation;

import jakarta.validation.constraints.Pattern;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;
import rw.ac.ilpd.sharedlibrary.enums.QuestionType;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluationFormQuestionRequest {

    @NotBlank(message = "Group ID is required")
    @RestrictedString
    private String groupId;

    @NotNull(message = "Question text is required")
    @NotBlank(message = "Question text cannot be blank")
    @Pattern(regexp = "^(?=.*[a-zA-Z].*[a-zA-Z])[a-zA-Z0-9-?.\s]+$",
            message = "Invalid characters used.")
    private String questionText;

    @NotNull(message = "Weight is required")
    @Positive(message = "Weight must be positive")
    private double weight;

    @NotBlank(message = "Section ID is required")
    @RestrictedString
    private String sectionId;

    @NotNull(message = "Question type is required")
    private QuestionType type;

    private List<EvaluationFormOptionRequest> options;
}