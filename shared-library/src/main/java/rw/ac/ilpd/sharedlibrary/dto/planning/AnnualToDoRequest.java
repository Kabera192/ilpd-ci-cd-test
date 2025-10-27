/*
 * File: AnnualToDoRequest.java
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


import jakarta.validation.constraints.*;
import lombok.*;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;
import rw.ac.ilpd.sharedlibrary.dto.validation.uuid.ValidUuid;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnnualToDoRequest {

//    @NotBlank(message = "Request ID is required")
//    @RestrictedString
//    private String requestId;
//
//    @NotNull(message = "Description is required")
//    @NotBlank(message = "Description cannot be blank")
//    @RestrictedString
//    private String description;
//
//    @NotNull(message = "Cost is required")
//    @DecimalMin(value = "0.0", inclusive = false, message = "Cost must be positive")
//    private BigDecimal cost;
//
//    @NotNull(message = "Start date is required")
//    private LocalDate startDate;
//
//    @NotNull(message = "End date is required")
//    private LocalDate endDate;
//
//    @NotBlank(message = "Status is required")
//    @RestrictedString
//    private String status;
//
//    @NotBlank(message = "Unit ID is required")
//    @RestrictedString
//    private String unitId;
@NotBlank(message = "Request ID is required")
@Size(min = 3, max = 50, message = "Request ID must be between 3 and 50 characters")
@Pattern(regexp = "^[A-Z0-9_-]+$", message = "Request ID can only contain uppercase letters, numbers, hyphens, and underscores")
@RestrictedString
private String requestId;

    @NotNull(message = "Description is required")
    @NotBlank(message = "Description cannot be blank")
    @Size(min = 10, max = 1000, message = "Description must be between 10 and 1000 characters")
    @RestrictedString
    private String description;

    @NotNull(message = "Cost is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Cost must be positive")
    @DecimalMax(value = "999999999.99", message = "Cost cannot exceed 999,999,999.99")
    private BigDecimal cost;

    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start date must be today or in the future")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @NotBlank(message = "Status is required")
    @RestrictedString
    private String status;

    @NotBlank(message = "Unit ID is required")
    @ValidUuid(message = "Unit ID must be a valid UUID format")
    private String unitId;

    @Size(max = 500, message = "Notes cannot exceed 500 characters")
    private String notes;

    @Min(value = 1, message = "Priority must be between 1 and 5")
    @Max(value = 5, message = "Priority must be between 1 and 5")
    private Integer priority;

    @Size(max = 100, message = "Assigned to cannot exceed 100 characters")
    private String assignedTo;

    @AssertTrue(message = "End date must be after start date")
    public boolean isEndDateValid() {
        if (startDate == null || endDate == null) {
            return true; // Let other validations handle null checks
        }
        return endDate.isAfter(startDate) || endDate.isEqual(startDate);
    }
}