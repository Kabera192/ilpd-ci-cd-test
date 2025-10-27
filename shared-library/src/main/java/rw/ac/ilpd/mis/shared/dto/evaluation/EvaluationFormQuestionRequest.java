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
package rw.ac.ilpd.mis.shared.dto.evaluation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluationFormQuestionRequest {

    @NotBlank(message = "Group ID is required")
    private String groupId;

    @NotNull(message = "Question text is required")
    @NotBlank(message = "Question text cannot be blank")
    private String questionText;

    @NotNull(message = "Weight is required")
    @Positive(message = "Weight must be positive")
    private Integer weight;

    @NotBlank(message = "Section ID is required")
    private String sectionId;

    @NotBlank(message = "Question type is required")
    private String type;
}