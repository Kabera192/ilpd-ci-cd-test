/**
 * This file defines the CurriculumRequest DTO used for handling curriculum data.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.curriculum;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurriculumRequest {
    @NotBlank(message = "Name should not be null or blank")
    @RestrictedString
    private String name;

    @NotBlank(message = "Description should not be null or blank")
    @RestrictedString
    private String description;

//    @NotBlank(message = "Status should not be null or blank")
//    @RestrictedString
//    private String status; // "ACTIVE" or "RETIRED"
//
//    private LocalDateTime endedAt;

    @NotBlank(message = "Program Id should not be null or blank")
    @RestrictedString
    private String programId;
}
