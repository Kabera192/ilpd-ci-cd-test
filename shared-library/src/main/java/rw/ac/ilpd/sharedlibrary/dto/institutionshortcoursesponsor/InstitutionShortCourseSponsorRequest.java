/**
 * This file defines the InstitutionShortCourseSponsorRequest DTO for creating or updating short course sponsors.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.institutionshortcoursesponsor;

import lombok.*;
import jakarta.validation.constraints.*;
import rw.ac.ilpd.sharedlibrary.dto.validation.address.OptionalValidAddress;
import rw.ac.ilpd.sharedlibrary.dto.validation.phonenumber.ValidPhoneNumber;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstitutionShortCourseSponsorRequest {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Email(message = "Wrong email format make sure you provide a correct email")
    private String email;

    @ValidPhoneNumber(message = "Wrong phone number the correct pattern start with +[country code][phone-number]")
    private String phone;
    @OptionalValidAddress(message = "Address contains invalid characters. Only letters, numbers, spaces, commas (,), apostrophes ('), slashes (/), periods (.) and hyphens (-) are allowed.")
    private String address;

    @NotBlank(message = "Sponsor can not be blank")
    @Pattern(regexp = "^(INDIVIDUAL|ORGANIZATION)$", message = "Sponsor is either INDIVIDUAL or ORGANIZATION")
    private String type;
}
