/*
 * File: DonationResponse.java
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

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DonationResponse {
    private String id;
    private String stockInId;
    private String donorId;
    private Integer quantity;
}