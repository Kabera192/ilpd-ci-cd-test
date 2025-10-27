/**
 * Response payload representing a room entity in the ILPD system.
 *
 * <p>This DTO returns details such as ID, location, name, code, type, and creation timestamp.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.room;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponse {
    private String id;
    private String locationId;
    private String name;
    private String code;
    private String typeId;
    private String createdAt;
}
