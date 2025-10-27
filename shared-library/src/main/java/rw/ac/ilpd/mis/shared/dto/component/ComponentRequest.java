/**
 * This file defines the ComponentRequest DTO used for handling component data.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.component;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComponentRequest {

    @NotBlank(message = "Name cannot be null or blank")
    private String name;

    @NotBlank(message = "Acronym cannot be null or blank")
    private String acronym;

    @NotBlank(message = "Curriculum module ID cannot be null or blank")
    private String curriculumModuleId;

    @NotNull(message = "Credits cannot be null")
    private Integer credits;
}
