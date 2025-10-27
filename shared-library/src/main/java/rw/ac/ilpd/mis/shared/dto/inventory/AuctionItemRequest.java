/*
 * File: AuctionItemRequest.java
 * 
 * Description: Data Transfer Object representing an item in an auction inventory.
 *              Contains information such as unique identifier, associated stock out record,
 *              the user who recorded it, quantity, and creation timestamp.
 *              Validation constraints:
 *                - stockOutId: must not be null
 *                - recordedBy: must not be null
 *                - quantity: must not be null and must be positive
 *                - createdAt: must not be null
 *
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified: 2025-07-05
 */
package rw.ac.ilpd.mis.shared.dto.inventory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuctionItemRequest {

    @NotBlank(message = "Stock out ID is required")
    private String stockOutId;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;
}