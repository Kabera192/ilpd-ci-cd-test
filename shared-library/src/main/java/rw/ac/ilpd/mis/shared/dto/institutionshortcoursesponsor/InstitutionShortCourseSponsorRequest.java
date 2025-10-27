/**
 * This file defines the InstitutionShortCourseSponsorRequest DTO for creating or updating short course sponsors.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.institutionshortcoursesponsor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import rw.ac.ilpd.mis.shared.enums.SponsorType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstitutionShortCourseSponsorRequest {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Email(message = "Email should be valid")
    private String email;

    private String phone;

    private String address;

    @NotNull(message = "Type cannot be null")
    private SponsorType type;
}
