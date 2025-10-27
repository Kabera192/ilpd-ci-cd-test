/**
 * Request payload for creating or updating a short course topic in the ILPD system.
 *
 * <p>This DTO includes intake ID, name, optional description, creator ID, and deletion status.</p>
 *
 * Author: Mohamed Gaye
 * Last Changed: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.shortcoursetopic;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortCourseTopicRequest {

    @NotBlank(message = "Intake ID cannot be null")
    private String intakeId;

    @NotBlank(message = "Name cannot be null")
    private String name;

    private String description; // Optional

//    @NotNull(message = "Created by cannot be null")
//    private String createdBy;

    
}
