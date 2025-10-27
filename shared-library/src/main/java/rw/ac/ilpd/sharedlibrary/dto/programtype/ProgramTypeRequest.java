/**
 * Request payload for creating or updating a program type in the ILPD system.
 *
 * <p>This DTO contains the name of the program type.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.programtype;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

@Getter
@ToString
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramTypeRequest {

    @NotBlank(message = "Name cannot be null or blank")
    @Size(max = 50, min = 2,
            message = "The program type name is too long or too short. " +
            "It should be between 2 and 50 characters")
    @RestrictedString
    private String name;

    
}
