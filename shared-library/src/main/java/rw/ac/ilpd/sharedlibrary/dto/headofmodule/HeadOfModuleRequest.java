/**
 * This file defines the HeadsOfModuleRequest DTO used for handling heads of module assignments.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.headofmodule;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HeadOfModuleRequest {
    @NotBlank(message = "lecturer ID cannot be null or blank")
    @RestrictedString
    private String lecturerId;

    @NotBlank(message = "Module ID cannot be null or blank")
    @RestrictedString
    private String moduleId;
}
