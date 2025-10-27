/**
 * Response payload representing a location entity in the ILPD system.
 *
 * <p>This DTO returns details such as ID, type, parent, coordinates,
 * block classification, and creation timestamp.</p>
 *
 * @author Mohamed Gaye
 * @lastChanged 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.location;

import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationResponse {
    private String id;
    private String name;
    private String typeId;
    private String parentLocationId;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String blocType;
    private Boolean deleteStatus;
    private String createdAt;
}
