package rw.ac.ilpd.sharedlibrary.dto.application;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommenderRequest {

    @NotBlank(message = "First name cannot be blank")
    @RestrictedString
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @RestrictedString
    private String lastName;

    @Pattern(regexp = "^\\+250[0-9]{9}$", message = "Phone number must be in format +250xxxxxxxxx")
    private String phoneNumber;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be blank")
    private String email;
}
