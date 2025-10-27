/**
 * This file defines the ComponentRequest DTO used for handling component data.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.component;

import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.UUID;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComponentRequest {

    @NotBlank(message = "Name cannot be null or blank")
    @Size(min = 2, max = 100, message = "The name provided is too long or too short. " +
            "The length should be between 2 and 100 characters")
    @RestrictedString
    private String name;

    @NotBlank(message = "Acronym cannot be null or blank")
    @Size(min = 2, max = 100, message = "The name provided is too long or too short. " +
            "The length should be between 2 and 100 characters")
    @RestrictedString
    private String acronym;

    @NotNull(message = "Curriculum module ID cannot be null or blank")
    @UUID(message = "id provided has an invalid format")
    private String curriculumModuleId;

    @NotNull(message = "Credits cannot be null")
    @Max(value = 100, message = "Credits cannot be higher than 100")
    @Positive(message = "Credits have to be a positive number")
    private Integer credits;
}
