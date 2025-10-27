/*
 * File: AnnualToDoResponse.java
 * 
 * Description: Data Transfer Object representing an annual to-do item in the planning module.
 *              Encapsulates details such as description, cost, scheduling, status, and metadata.
 *              Fields:
 *                - id: Unique identifier for the to-do item.
 *                - requestId: Identifier of the related request (required).
 *                - description: Description of the to-do item (required, cannot be blank).
 *                - cost: Cost associated with the to-do item (required, must be positive).
 *                - startDate: Start date of the to-do item (required).
 *                - endDate: End date of the to-do item (required).
 *                - status: Current status of the to-do item (required).
 *                - unitId: Identifier of the unit responsible for the to-do item (required).
 *                - createdAt: Timestamp when the to-do item was created (required).
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified: 2025-07-07
 */
package rw.ac.ilpd.sharedlibrary.dto.planning;

import java.math.BigDecimal;
import java.time.LocalDate;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnnualToDoResponse {
//    private String id;
//    private String requestId;
//    private String description;
//    private BigDecimal cost;
//    private LocalDate startDate;
//    private LocalDate endDate;
//    private String status;
//    private String unitId;
//    private String createdAt;
private String id;
    private String requestId;
    private String description;
    private BigDecimal cost;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String statusDisplayName;
    private String unitId;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;
    private boolean active;
    private String notes;
    private Integer priority;
    private String priorityLabel;
    private String assignedTo;
    private LocalDate actualStartDate;
    private LocalDate actualEndDate;
    private BigDecimal actualCost;
    private Long durationInDays;
    private boolean isOverdue;
    private BigDecimal costVariance;
}