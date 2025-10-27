/*
 * File Name: UserItemRequisitionModificationResponse.java
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
 * - deleteStatus: Indicates if the requisition is marked for deletion. (Required)
 * - createdAt: The timestamp when the modification was created. (Required)
 * Lombok annotations are used for boilerplate code generation.
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
public class UserItemRequisitionModificationResponse {
    private String id;
    private String userItemRequisitionId;
    private String updatedBy;
    private Integer updatedQuantity;
    private Boolean deleteStatus;
    private String createdAt;
}