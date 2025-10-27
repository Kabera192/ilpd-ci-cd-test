/*
 * File Name: PositionResponse.java
 * 
 * Description: 
 *   Data Transfer Object representing a Position.
 *   Contains information about a position, including its unique identifier and name.
 *   Validation constraints:
 *     - name: must not be null, must not be blank, and cannot exceed 100 characters.
 *
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified Date: 2025-07-07
 */
package rw.ac.ilpd.sharedlibrary.dto.position;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PositionResponse {
    private String id;
    private String name;
    private String abbreviation;
    private boolean isDeleted;
    private String createdAt;
    private String updatedAt;
}