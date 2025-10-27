/**
 * Request payload for creating or updating a location type in the ILPD system.
 *
 * <p>This DTO includes basic details such as name.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.locationtype;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationTypeRequest {

    @NotBlank(message = "Name cannot be null or blank")
    private String name;

    
}
