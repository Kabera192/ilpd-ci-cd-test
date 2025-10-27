/* UniversityDto.java
 *
 * Description: Data Transfer Object representing a University. Contains fields for unique identifier,
 *              name, and country. Includes validation constraints to ensure name and country are not null or blank.
 *
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified: 2025-07-05
 */

package rw.ac.ilpd.mis.shared.dto.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UniversityResponse {
    private String id;
    private String name;
    private String country;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}
