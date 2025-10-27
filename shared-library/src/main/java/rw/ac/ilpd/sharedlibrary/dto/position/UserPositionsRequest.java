/*
 * File: UserPositionsRequest.java
 * 
 * Description: Data Transfer Object representing the association between a user and a position.
 *              Contains information about the user's position assignment, including creation and quit dates.
 *              Fields:
 *                - id: Unique identifier for the user-position association.
 *                - userId: Unique identifier of the user. (Required)
 *                - positionId: Unique identifier of the position. (Required)
 *                - createdAt: Timestamp when the association was created. (Required)
 *                - quiteDate: Timestamp when the user quit the position (if applicable).
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified Date: 2025-07-07
 */
package rw.ac.ilpd.sharedlibrary.dto.position;



import jakarta.validation.constraints.NotBlank;
import lombok.*;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;
import rw.ac.ilpd.sharedlibrary.dto.validation.uuid.ValidUuid;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPositionsRequest {

    @NotBlank(message = "User ID is required")
    @ValidUuid(message = "Provide a correct user")
    private String userId;

    @NotBlank(message = "Position ID is required")
    @RestrictedString
    private String positionId;
}