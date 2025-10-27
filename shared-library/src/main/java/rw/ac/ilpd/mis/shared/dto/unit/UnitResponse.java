/*
 * File: UnitResponse.java
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
package rw.ac.ilpd.mis.shared.dto.unit;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnitResponse {
    private String id;
    private String name;
    private String acronym;
    private String description;
}