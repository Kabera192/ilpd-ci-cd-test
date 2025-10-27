/**
 * This file defines the AuthenticationRequest DTO used for handling authentication data.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.auth.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationRequest {
    @NotNull(message = "Email should be provided")
    private String email;

    @NotNull(message = "Password should be provided")
    private String password;
}
