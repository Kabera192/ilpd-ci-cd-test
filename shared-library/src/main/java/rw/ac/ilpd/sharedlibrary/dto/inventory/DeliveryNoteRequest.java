/*
 * File: DeliveryNoteRequest.java
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
package rw.ac.ilpd.sharedlibrary.dto.inventory;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryNoteRequest {

    @NotBlank(message = "Supplier ID is required")
    @RestrictedString
    private String supplierId;
    @RestrictedString
    private String documentId;
}