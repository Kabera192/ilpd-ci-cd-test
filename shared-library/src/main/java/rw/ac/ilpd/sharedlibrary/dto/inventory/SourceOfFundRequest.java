/*
 * File: SourceOfFundRequest.java
 * 
 * Description: Data Transfer Object representing a Source of Fund in the inventory module.
 *              Contains information such as name, description, contact details, deletion status, and creation timestamp.
 *              Validation constraints are applied to ensure data integrity:
 *                - name: Required, not blank, max 100 characters.
 *                - description: Required, not blank.
 *                - phone: Required, not blank, max 15 characters.
 *                - email: Required, valid email format, max 150 characters.
 *                - createdAt: Required.
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified: 2025-07-05
 */
package rw.ac.ilpd.sharedlibrary.dto.inventory;

import lombok.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SourceOfFundRequest {

    @NotNull(message = "Name is required")
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    @RestrictedString
    private String name;

    @NotNull(message = "Description is required")
    @NotBlank(message = "Description cannot be blank")
    @RestrictedString
    private String description;

    @NotNull(message = "Phone is required")
    @NotBlank(message = "Phone cannot be blank")
    @Size(max = 15, message = "Phone cannot exceed 15 characters")
    @RestrictedString
    private String phone;

    @NotNull(message = "Email is required")
    @Email(message = "Email should be valid")
    @Size(max = 150, message = "Email cannot exceed 150 characters")
    @RestrictedString
    private String email;

    
}