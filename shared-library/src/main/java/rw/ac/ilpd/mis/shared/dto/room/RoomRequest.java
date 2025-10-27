/**
 * Request payload for creating or updating a room in the ILPD system.
 *
 * <p>This DTO contains references to location, name, code, and type of the room.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.room;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomRequest
{
    @NotBlank(message = "Location ID cannot be null")
    private String locationId;

    @NotBlank(message = "Name cannot be null")
    private String name;

    private String code;

    @NotBlank(message = "Type ID cannot be null")
    private String typeId;
}
