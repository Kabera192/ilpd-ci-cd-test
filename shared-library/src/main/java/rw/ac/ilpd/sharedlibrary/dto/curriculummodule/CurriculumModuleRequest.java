/**
 * This file defines the CurriculumModuleRequest DTO used for handling curriculum module data.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.curriculummodule;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurriculumModuleRequest {
    @NotBlank(message = "Curriculum ID cannot be null or blank")
    @RestrictedString
    private String curriculumId;

    @NotBlank(message = "Module ID cannot be null or blank")
    @RestrictedString
    private String moduleId;

    @Positive(message = "Module order must be positive or zero")
    private Integer moduleOrder;

    @NotNull(message = "Credits cannot be null")
    @PositiveOrZero(message = "Credits must be positive or zero")
    private Integer credits;
}
