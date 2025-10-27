/* UniversityDto.java
 *
 * Description: Data Transfer Object representing a University. Contains fields for unique identifier, 
 *              name, and country. Includes validation constraints to ensure name and country are not null or blank.
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified: 2025-07-05
 */

package rw.ac.ilpd.mis.shared.dto.application;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UniversityRequest {

    @NotNull(message = "University name is required")
    @NotBlank(message = "University name cannot be blank")
    @Size(min = 2, max = 200, message = "University name must be between 2 and 200 characters")
    private String name;

    @NotNull(message = "Country is required")
    @NotBlank(message = "Country cannot be blank")
    @Size(min = 2, max = 100, message = "Country must be between 2 and 100 characters")
    private String country;
}
