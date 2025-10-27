/**
 * This file defines the AuthenticationRequest DTO used for handling authentication data.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationRequest {
    @NotBlank(message = "Email should be provided")
    private String email;

    @NotBlank(message = "Password should be provided")
    private String password;
}
