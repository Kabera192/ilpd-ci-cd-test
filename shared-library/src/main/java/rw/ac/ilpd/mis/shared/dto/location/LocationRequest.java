/**
 * Request payload for creating or updating a location within ILPD systems.
 *
 * <p>This DTO encapsulates properties such as the location's name, hierarchical parent,
 * geographical coordinates, and block classification.</p>
 *
 * @author Mohamed Gaye
 * @lastChanged 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.location;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationRequest {

    @NotBlank(message = "Name cannot be null or blank")
    private String name;

    @NotBlank(message = "Type ID cannot be null or blank")
    private String typeId;

    private String parentLocationId; // Nullable

    private BigDecimal latitude; // Nullable

    private BigDecimal longitude; // Nullable

    private String blocType; // "INTERNAL" or "EXTERNAL" // Nullable

    
}
