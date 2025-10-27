/**
 * This file defines the ResetPasswordRequest DTO used for handling password reset data.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResetPasswordRequest {
    @NotBlank(message = "Email should not be null or blank")
    private String email;

    @NotBlank(message = "OTP should not be null or blank")
    private String otp;

    @NotBlank(message = "New Password should not be null or blank")
    private String newPassword;

    @NotBlank(message = "Confirmed password should not be null or blank")
    private String confirmPassword;
}
