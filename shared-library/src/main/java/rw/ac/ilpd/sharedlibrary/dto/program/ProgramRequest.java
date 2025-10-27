/**
 * Request payload for creating or updating a program in the ILPD system.
 *
 * <p>This DTO includes code, name, description, acronym, program type.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.program;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramRequest {

    @NotBlank(message = "Code cannot be null or blank")
    @RestrictedString
    @Size(min = 2, max = 50, message = "Input provided is too long. " +
            "It should be between 2 and 50 characters.")
    private String code;

    @NotBlank(message = "Name cannot be null or blank")
    @RestrictedString
    @Size(min = 2, max = 50, message = "Input provided is too long. " +
            "It should be between 2 and 50 characters.")
    private String name;

    private String description; // Optional field

    @NotBlank(message = "Acronym cannot be null or blank")
    @RestrictedString
    @Size(min = 2, max = 50, message = "Input provided is too long. " +
            "It should be between 2 and 50 characters.")
    private String acronym;

    @NotBlank(message = "Program type ID cannot be null or blank")
    @RestrictedString
    private String programTypeId;

    
}
