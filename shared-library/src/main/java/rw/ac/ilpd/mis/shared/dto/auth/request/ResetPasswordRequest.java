/**
 * This file defines the ResetPasswordRequest DTO used for handling password reset data.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.auth.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResetPasswordRequest {
    @NotNull(message = "Token should not be null")
    private String token;

    @NotNull(message = "New Password should not be null")
    private String newPassword;
}
