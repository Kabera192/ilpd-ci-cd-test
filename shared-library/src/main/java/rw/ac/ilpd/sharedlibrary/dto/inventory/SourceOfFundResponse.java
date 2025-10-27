/*
 * File: SourceOfFundResponse.java
 * 
 * Description: Data Transfer Object representing a Source of Fund in the inventory module.
 *              Contains information such as name, description, contact details, deletion status, and creation timestamp.
 *              Validation constraints are applied to ensure data integrity:
 *                - name: Required, not blank, max 100 characters.
 *                - description: Required, not blank.
 *                - phone: Required, not blank, max 15 characters.
 *                - email: Required, valid email format, max 150 characters.
 *                - isDeleted: Required.
 *                - createdAt: Required.
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified: 2025-07-05
 */
package rw.ac.ilpd.sharedlibrary.dto.inventory;

import lombok.*;



@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SourceOfFundResponse {
    private String id;
    private String name;
    private String description;
    private String phone;
    private String email;
    private Boolean isDeleted;
    private String createdAt;
}