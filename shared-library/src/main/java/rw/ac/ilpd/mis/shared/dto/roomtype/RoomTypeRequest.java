/**
 * Request payload for creating or updating a room type in the ILPD system.
 *
 * <p>This DTO contains the name and type of the room type.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.roomtype;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomTypeRequest
{
    @NotBlank(message = "Name cannot be null")
    private String name;

}
