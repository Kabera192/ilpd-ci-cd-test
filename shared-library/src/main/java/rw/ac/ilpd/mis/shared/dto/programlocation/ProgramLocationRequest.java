/**
 * Request payload to associate a program with a location in the ILPD system.
 *
 * <p>This DTO includes references to program and location IDs.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.programlocation;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramLocationRequest {

    @NotBlank(message = "Program ID cannot be null or blank")
    private String programId;

    @NotBlank(message = "Location ID cannot be null or blank")
    private String locationId;
}
