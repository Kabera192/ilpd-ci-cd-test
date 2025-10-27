/**
 * Request payload for registering a new user in the ILPD system.
 *
 * <p>This DTO contains the user's full names, email, password, and gender.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import rw.ac.ilpd.mis.shared.enums.Gender;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterRequest {
    @NotBlank(message = "Names should be provided")
    private String names;

    @NotBlank(message = "Email should be provided")
    private String email;

    @NotBlank(message = "Password should be provided")
    private String password;

    @NotNull(message = "Gender should be provided")
    private Gender gender;
}
