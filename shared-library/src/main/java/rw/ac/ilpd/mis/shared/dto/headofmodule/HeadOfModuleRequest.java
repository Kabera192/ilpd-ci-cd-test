/**
 * This file defines the HeadsOfModuleRequest DTO used for handling heads of module assignments.
 *
 * Author: Mohamed Gaye
 * Last changed date: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.headofmodule;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HeadOfModuleRequest {
    @NotBlank(message = "lecturer ID cannot be null or blank")
    private String lecturerId;

    @NotBlank(message = "Module ID cannot be null or blank")
    private String moduleId;

    @NotBlank(message = "From date cannot be null or blank")
    private String from;

    // can be null
    private String to;
}
