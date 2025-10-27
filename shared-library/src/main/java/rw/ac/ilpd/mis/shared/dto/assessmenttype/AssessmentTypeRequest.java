/**
 * This file defines the AssessmentTypeRequest DTO used for handling assessment type data.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.assessmenttype;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentTypeRequest {

    @NotBlank(message = "Title cannot be blank")
    private String title; // Using enum

    @NotBlank(message = "Acronym cannot be blank")
    private String acronym;

    @NotNull(message = "Weight cannot be null")
    @DecimalMin(value = "0",message = "Average weight is 0.0")
    @Positive(message = "Weight can not be negative")
    private BigDecimal weight;

    @NotBlank(message = "Assessment group ID cannot be null")
    private String assessmentGroupId;
}
