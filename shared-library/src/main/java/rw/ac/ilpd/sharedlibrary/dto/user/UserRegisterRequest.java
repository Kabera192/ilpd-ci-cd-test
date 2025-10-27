/**
 * Request payload for registering a new user in the ILPD system.
 *
 * <p>This DTO contains the user's full names, email, password, and gender.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;
import rw.ac.ilpd.sharedlibrary.enums.Gender;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterRequest {
    @NotBlank(message = "Names should be provided")
    @RestrictedString
    private String names;

    @NotBlank(message = "Email should be provided")
    @RestrictedString
    private String email;

    @NotBlank(message = "Password should be provided")
    @RestrictedString
    private String password;

    @NotNull(message = "Gender should be provided")
    private Gender gender;
}
