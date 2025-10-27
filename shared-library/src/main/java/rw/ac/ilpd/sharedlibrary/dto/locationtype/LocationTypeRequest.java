/**
 * Request payload for creating or updating a location type in the ILPD system.
 *
 * <p>This DTO includes basic details such as name.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.locationtype;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationTypeRequest {

    @NotBlank(message = "Name cannot be null or blank")
    @RestrictedString
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters long")
    @Pattern(
            regexp = "^(?!\\d+$)[a-zA-Z0-9 ]+$",
            message = "Name must be alphanumeric and not numeric only"
    )
    private String name;

    
}
