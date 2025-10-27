/*
 * File: ItemRequest.java
 * 
 * Description: Data Transfer Object representing an inventory item.
 *              Contains fields such as id, name, acronym, description, category, groupId,
 *              unitMeasure, depreciationRate, isInStock, and createdAt.
 *              Validation constraints ensure required fields and correct formats.
 *              Lombok annotations are used for boilerplate code generation.
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified: 2025-07-05
 */
package rw.ac.ilpd.sharedlibrary.dto.inventory;

import lombok.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.DecimalMin;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

import java.math.BigDecimal;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemRequest {

    @NotNull(message = "Name is required")
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 150, message = "Name cannot exceed 150 characters")
    @RestrictedString
    private String name;

    @NotNull(message = "Acronym is required")
    @NotBlank(message = "Acronym cannot be blank")
    @Size(max = 10, message = "Acronym cannot exceed 10 characters")
    @RestrictedString
    private String acronym;

    @NotNull(message = "Description is required")
    @NotBlank(message = "Description cannot be blank")
    @RestrictedString
    private String description;

    @NotBlank(message = "Category is required")
    @RestrictedString
    private String category;

    @NotBlank(message = "Group ID is required")
    @RestrictedString
    private String groupId;

    @NotNull(message = "Unit measure is required")
    @NotBlank(message = "Unit measure cannot be blank")
    @Size(max = 10, message = "Unit measure cannot exceed 10 characters")
    @RestrictedString
    private String unitMeasure;

    @DecimalMin(value = "0.0", inclusive = false, message = "Depreciation rate must be positive")
    private BigDecimal depreciationRate;
}