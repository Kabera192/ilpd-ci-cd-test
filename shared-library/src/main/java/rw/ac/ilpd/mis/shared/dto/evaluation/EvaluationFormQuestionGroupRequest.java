/*
 * File: EvaluationFormQuestionGroupRequest.java
 * 
 * Description: Data Transfer Object representing a group of questions in an evaluation form.
 *              Contains fields for unique identifier, name, description, and creation timestamp.
 *              Includes validation constraints for required fields and maximum length.
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com) 
 * Last Modified: 2025-07-05
 */
package rw.ac.ilpd.mis.shared.dto.evaluation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluationFormQuestionGroupRequest {

    @NotNull(message = "Name is required")
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 150, message = "Name cannot exceed 150 characters")
    private String name;

    @NotNull(message = "Description is required")
    @NotBlank(message = "Description cannot be blank")
    private String description;
}