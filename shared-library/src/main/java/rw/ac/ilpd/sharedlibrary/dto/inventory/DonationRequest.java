/*
 * File: DonationRequest.java
 * 
 * Description:
 *   Data Transfer Object representing a donation in the inventory system.
 *   Contains information about the donation, including the associated stock-in entry,
 *   donor, and the quantity donated.
 *   Validation constraints:
 *     - stockInId: must not be null
 *     - donorId: must not be null
 *     - quantity: must not be null and must be positive
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified Date: 2025-07-05
 */
package rw.ac.ilpd.sharedlibrary.dto.inventory;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DonationRequest {

    @NotBlank(message = "Stock in ID is required")
    @RestrictedString
    private String stockInId;

    @NotBlank(message = "Donor ID is required")
    @RestrictedString
    private String donorId;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;
}