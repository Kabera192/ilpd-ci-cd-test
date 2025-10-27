/**
 * lecturerComponentRequest DTO.
 * Represents the request data for linking an lecturer with a component, including active status.
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.lecturercomponent;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LecturerComponentRequest {

    @NotBlank(message = "lecturer ID cannot be null")
    @RestrictedString
    private String lecturerId;

    @NotBlank(message = "Component ID cannot be null")
    @RestrictedString
    private String componentId;

    @NotNull(message = "Active status cannot be null")
    private Boolean activeStatus;
}
