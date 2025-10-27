/*
 * File Name: EvaluationFormQuestionSectionRequest.java
 * 
 * Description: Data Transfer Object representing a section of questions in an evaluation form.
 *              Contains the unique identifier and the title of the section.
 *              The title field must not be null or blank.
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified Date: 2025-07-05
 */
package rw.ac.ilpd.mis.shared.dto.evaluation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluationFormQuestionSectionRequest {

    @NotNull(message = "Title is required")
    @NotBlank(message = "Title cannot be blank")
    private String title;
}