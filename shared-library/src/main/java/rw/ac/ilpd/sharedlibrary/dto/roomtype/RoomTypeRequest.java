/**
 * Request payload for creating or updating a room type in the ILPD system.
 *
 * <p>This DTO contains the name and type of the room type.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.roomtype;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomTypeRequest
{
    @NotBlank(message = "Name cannot be null")
    @RestrictedString
    private String name;

}
