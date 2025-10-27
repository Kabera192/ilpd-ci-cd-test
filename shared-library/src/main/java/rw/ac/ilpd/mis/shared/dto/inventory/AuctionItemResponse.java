/*
 * File: AuctionItemResponse.java
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

import lombok.*;



@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuctionItemResponse {
    private String id;
    private String stockOutId;
    private String recordedBy;
    private Integer quantity;
    private String createdAt;
}