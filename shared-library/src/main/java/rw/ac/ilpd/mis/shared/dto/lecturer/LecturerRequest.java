/**
 * lecturerRequest DTO.
 * Used for sending lecturer data including user ID and status.
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.lecturer;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LecturerRequest {

    @NotBlank(message = "User ID cannot be null")
    private String userId;
    @NotBlank(message = "Employment Status cannot be null")
    private String employmentType;; // "PERMANENT" or "TEMPORARY"
    private LocalDate endDate;
}
