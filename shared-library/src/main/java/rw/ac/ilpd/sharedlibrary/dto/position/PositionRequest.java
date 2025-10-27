/*
 * File Name: PositionRequest.java
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

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PositionRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Abbreviation is required")
    @Size(min = 1, max = 10, message = "Abbreviation must be between 1 and 10 characters")
    private String abbreviation;
}