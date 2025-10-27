/**
 * This file defines the AssessmentTypeRequest DTO used for handling assessment type data.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.assessmenttype;

import jakarta.validation.constraints.*;
import lombok.*;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;
import rw.ac.ilpd.sharedlibrary.dto.validation.uuid.ValidUuid;
import rw.ac.ilpd.sharedlibrary.enums.AssessmentTitle;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentTypeRequest {

    @NotBlank(message = "Title cannot be blank")
    @RestrictedString
    private String title;

    @NotBlank(message = "Acronym can not be blank.")
    @Size(max = 10, message = "Acronym must not exceed 10 characters.")
    @Size(min = 2, message = "Acronym must be at least 2 characters long.")
    @RestrictedString
    private String acronym;

    @NotNull(message = "Weight cannot be null")
    @DecimalMin(value = "0",message = "Average weight is 0.0")
    @Max(value = 100,message = "Activity weight can not go beyond 100 ")
    @Positive(message = "Weight can not be negative")
    private BigDecimal weight;

    @NotBlank(message = "Assessment group is required")
    @ValidUuid(message = "Invalid input format. Please provide a valid assessment group identifier.")
    private String assessmentGroupId;
}
