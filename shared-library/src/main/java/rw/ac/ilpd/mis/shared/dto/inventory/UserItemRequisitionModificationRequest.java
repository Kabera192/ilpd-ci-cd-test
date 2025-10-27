/*
 * File Name: UserItemRequisitionModificationRequest.java
 *
 * Description:
 * Data Transfer Object for modifying a user item requisition.
 * This DTO captures the details required to update or modify a user item requisition,
 * including the requisition ID, the user performing the update, the updated quantity,
 * deletion status, and the timestamp of the modification.
 * Validation constraints are applied to ensure required fields are provided and valid.
 * Fields:
 * - id: Unique identifier for this modification record.
 * - userItemRequisitionId: The ID of the user item requisition being modified. (Required)
 * - updatedBy: The ID of the user who performed the update. (Required)
 * - updatedQuantity: The new quantity after modification. Must be positive. (Required)
 * - createdAt: The timestamp when the modification was created. (Required)
 * Lombok annotations are used for boilerplate code generation.
 *
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified Date: 2025-07-05
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
public class UserItemRequisitionModificationRequest {

    @NotBlank(message = "User item requisition ID is required")
    private String userItemRequisitionId;

    @NotBlank(message = "Updated by user ID is required")
    private String updatedBy;

    @NotNull(message = "Updated quantity is required")
    @Positive(message = "Updated quantity must be positive")
    private Integer updatedQuantity;

    
}