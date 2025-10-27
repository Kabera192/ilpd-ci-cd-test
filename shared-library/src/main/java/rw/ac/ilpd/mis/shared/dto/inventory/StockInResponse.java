/*
 * File: StockInResponse.java
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

import lombok.*;

import java.math.BigDecimal;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockInResponse {
    private String id;
    private String itemId;
    private BigDecimal unitPrice;
    private Integer quantity;

    private String acquisitionDate;

    private String expirationDate;
    private String deliveryNoteId;
    private Integer remainingQuantity;
    private String createdAt;
}