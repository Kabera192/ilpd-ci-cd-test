/**
 * Request payload for creating or updating a program in the ILPD system.
 *
 * <p>This DTO includes code, name, description, acronym, program type.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.program;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramRequest {

    @NotBlank(message = "Code cannot be null or blank")
    private String code;

    @NotBlank(message = "Name cannot be null or blank")
    private String name;

    private String description; // Optional field

    @NotBlank(message = "Acronym cannot be null or blank")
    private String acronym;

    @NotBlank(message = "Program type ID cannot be null or blank")
    private String programTypeId;

    
}
