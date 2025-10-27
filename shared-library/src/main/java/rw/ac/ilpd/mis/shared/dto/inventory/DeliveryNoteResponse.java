/*
 * File: DeliveryNoteResponse.java
 * 
 * Description:
 *   Data Transfer Object representing a Delivery Note in the inventory system.
 *   Contains information such as the unique identifier, supplier ID (required), 
 *   related document ID, and creation timestamp (required).
 *   Uses Lombok annotations for boilerplate code generation and Jakarta Validation 
 *   for field constraints.
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified Date: 2025-07-05
 */
package rw.ac.ilpd.mis.shared.dto.inventory;

import lombok.*;



@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryNoteResponse {
    private String id;
    private String supplierId;

    private String documentId;
    private String createdAt;
}