/**
 * Request payload for creating or updating a program type in the ILPD system.
 *
 * <p>This DTO contains the name of the program type.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.programtype;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@ToString
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramTypeRequest {

    @NotBlank(message = "Name cannot be null or blank")
    private String name;

    
}
