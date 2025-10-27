/*
 * File: HeadOfUnitRequest.java
 * 
 * Description: Data Transfer Object representing the Head of a Unit.
 *              Contains information about the user assigned as the head of a specific unit,
 *              including their unique identifiers, assignment timestamps, and status.
 *              Fields:
 *                - id: Unique identifier for the HeadOfUnit record.
 *                - userId: Unique identifier of the user who is the head of the unit. (Required)
 *                - unitId: Unique identifier of the unit. (Required)
 *                - createdAt: Timestamp when the headship was assigned. (Required)
 *                - isCurrentlyHead: Flag indicating if the user is currently the head of the unit. (Required)
 *              Validation constraints ensure that userId, unitId, createdAt,
 *              and isCurrentlyHead are not null.
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified: 2025-07-07
 */
package rw.ac.ilpd.sharedlibrary.dto.unit;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HeadOfUnitRequest {

    @NotBlank(message = "User ID is required")
    @RestrictedString
    private String userId;

    @NotBlank(message = "Unit ID is required")
    @RestrictedString
    private String unitId;

    @NotNull(message = "Is currently head flag is required")
    private Boolean isCurrentlyHead;
}