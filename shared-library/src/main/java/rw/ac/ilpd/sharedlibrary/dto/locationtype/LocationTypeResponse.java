/**
 * Response payload representing a location type entity in the ILPD system.
 *
 * <p>This DTO returns details such as ID, name, delete status, and creation timestamp.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.locationtype;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationTypeResponse {
    private String id;
    private String name;
    private Boolean deleteStatus;
    private String createdAt;
}
