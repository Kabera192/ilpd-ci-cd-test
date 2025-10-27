/*
 * File: StockOutRequest.java
 * 
 * Description: Data Transfer Object representing a stock out operation in the inventory system.
 *              Contains information about the stock out event, including:
 *              - id: Unique identifier for the stock out record.
 *              - stockInId: Reference to the related stock in record (required).
 *              - roomId: Identifier of the room where the stock is moved (required).
 *              - userItemRequisitionId: Reference to the user item requisition, if applicable.
 *              - quantity: Number of items being moved out (required, must be positive).
 *              - createdAt: Timestamp when the stock out was created (required).
 *              Validation constraints ensure required fields are present and quantity is positive.
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified: 2025-07-05
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
public class StockOutRequest {

    @NotBlank(message = "Stock in ID is required")
    @RestrictedString
    private String stockInId;

    @NotBlank(message = "Room ID is required")
    @RestrictedString
    private String roomId;
    @RestrictedString
    private String userItemRequisitionId;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;
}