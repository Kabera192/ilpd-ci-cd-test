/*
 * File: ItemGroupRequest.java
 * 
 * Description: Data Transfer Object representing an Item Group in the inventory system.
 *              Encapsulates details such as unique identifier, name, acronym, description,
 *              and creation timestamp. Includes validation constraints to ensure required
 *              fields are not null, not blank, and within specified length limits.
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified: 2025-07-05
 */
package rw.ac.ilpd.sharedlibrary.dto.inventory;

import lombok.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemGroupRequest {

    @NotNull(message = "Name is required")
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 50, message = "Name cannot exceed 50 characters")
    @RestrictedString
    private String name;

    @NotNull(message = "Acronym is required")
    @NotBlank(message = "Acronym cannot be blank")
    @Size(max = 50, message = "Acronym cannot exceed 50 characters")
    @RestrictedString
    private String acronym;

    @NotNull(message = "Description is required")
    @NotBlank(message = "Description cannot be blank")
    @RestrictedString
    private String description;
}