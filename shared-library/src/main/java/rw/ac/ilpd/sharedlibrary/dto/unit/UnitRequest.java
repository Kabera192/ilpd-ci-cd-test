/*
 * File: UnitRequest.java
 * 
 * Description: Data Transfer Object representing a Unit. Contains information about a unit including its unique
 *              identifier, name, acronym, and description.
 * Validation constraints:
 *   - name: Required, not blank, maximum 150 characters.
 *   - acronym: Required, not blank, maximum 10 characters.
 *   - description: Required, not blank.
 *
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified: 2025-07-07
 */
package rw.ac.ilpd.sharedlibrary.dto.unit;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnitRequest {

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
}