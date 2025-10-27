/**
 * Response payload representing a room type entity in the ILPD system.
 *
 * <p>This DTO returns the ID, name, and type of a room type.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.roomtype;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomTypeResponse {
    private String id;
    private String name;
}
