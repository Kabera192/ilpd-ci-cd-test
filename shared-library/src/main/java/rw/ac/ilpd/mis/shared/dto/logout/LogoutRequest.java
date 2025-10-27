/**
 * Logout request
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */

package rw.ac.ilpd.mis.shared.dto.logout;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogoutRequest {
    @NotBlank(message = "Token should be provided")
    private String token;
}
