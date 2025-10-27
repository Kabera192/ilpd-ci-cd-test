/**
 * Request payload for creating or updating a room in the ILPD system.
 *
 * <p>This DTO contains references to location, name, code, and type of the room.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.room;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomRequest
{
    @NotBlank(message = "Location ID cannot be null")
    @RestrictedString
    private String locationId;

    @NotBlank(message = "Name cannot be null")
    @RestrictedString
    private String name;
    @RestrictedString
    private String code;

    @NotBlank(message = "Type ID cannot be null")
    @RestrictedString
    private String typeId;
}
