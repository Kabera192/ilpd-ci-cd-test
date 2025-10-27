/**
 * This file defines the DeliberationRuleGroupCurriculumRequest DTO used for handling deliberation rule group curriculum data.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.deliberationrulegroupcurriculum;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliberationRuleGroupCurriculumRequest {

    @NotBlank(message = "Curriculum ID cannot be null or blank")
    @RestrictedString
    private String curriculumId;

    @NotBlank(message = "Status cannot be null or blank")
    @RestrictedString
    private String status;
}
