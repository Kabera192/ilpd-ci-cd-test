/*
 * File: ItemGroupResponse.java
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



@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemGroupResponse {
    private String id;
    private String name;
    private String acronym;
    private String description;
    private String createdAt;
}