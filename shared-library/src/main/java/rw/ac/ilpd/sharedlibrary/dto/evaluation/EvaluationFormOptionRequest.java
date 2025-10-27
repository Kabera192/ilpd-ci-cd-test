/*
 * File: EvaluationFormOptionRequest.java
 * 
 * Description: Data Transfer Object representing an option for an evaluation form question.
 *              Contains information about the option's unique identifier, the related question,
 *              the option text, and whether the option is correct.
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified Date: 2025-07-05
 */
package rw.ac.ilpd.sharedlibrary.dto.evaluation;

import lombok.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluationFormOptionRequest {

    @NotNull(message = "Option text is required")
    @NotBlank(message = "Option text cannot be blank")
    @RestrictedString
    private String optionText;

    @NotNull(message = "Is correct flag is required")
    private Boolean isCorrect;
}