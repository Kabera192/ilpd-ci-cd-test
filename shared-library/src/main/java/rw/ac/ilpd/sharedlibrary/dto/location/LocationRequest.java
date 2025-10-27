/**
 * Request payload for creating or updating a location within ILPD systems.
 *
 * <p>This DTO encapsulates properties such as the location's name, hierarchical parent,
 * geographical coordinates, and block classification.</p>
 *
 * @author Mohamed Gaye
 * @lastChanged 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.location;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationRequest {

    @NotBlank(message = "Name cannot be null or blank")
    @RestrictedString
    private String name;

    @NotBlank(message = "Type ID cannot be null or blank")
    @RestrictedString
    private String typeId;
    @RestrictedString
    private String parentLocationId; // Nullable

    private BigDecimal latitude; // Nullable

    private BigDecimal longitude; // Nullable
    @RestrictedString
    private String blocType; // "INTERNAL" or "EXTERNAL" // Nullable

    
}
