/*
 * File: StockInRequest.java
 * 
 * Description: Data Transfer Object representing a stock-in record in the inventory system.
 *              Contains information about the stock item, including its unique identifier,
 *              associated item ID, unit price, quantity, acquisition and expiration dates,
 *              delivery note, remaining quantity, and creation timestamp.
 *              Validation constraints ensure required fields are present and values are positive where applicable.
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified: [Date]
 */
package rw.ac.ilpd.mis.shared.dto.inventory;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockInRequest {

    @NotBlank(message = "Item ID is required")
    private String itemId;

    @NotNull(message = "Unit price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Unit price must be positive")
    private BigDecimal unitPrice;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;

    private String acquisitionDate;

    private String expirationDate;

    @NotBlank(message = "Delivery note ID is required")
    private String deliveryNoteId;

}