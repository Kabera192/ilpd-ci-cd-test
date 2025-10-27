/**
 * Request payload for creating or updating a short course topic in the ILPD system.
 *
 * <p>This DTO includes intake ID, name, optional description, creator ID, and deletion status.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.shortcoursetopic;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortCourseTopicRequest {

    @NotBlank(message = "Intake ID cannot be null")
    @RestrictedString
    private String intakeId;

    @NotBlank(message = "Name cannot be null")
    @RestrictedString
    private String name;
    @RestrictedString
    private String description; // Optional

//    @NotNull(message = "Created by cannot be null")
//    private String createdBy;

    
}
