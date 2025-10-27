/**
 * This file defines the ActivityLevelRequest DTO used for submitting
 * level-specific data related to an activity.
 *
 * Author: Mohamed Gaye
 * Last Changed Date: 07/07/2025
 */
package rw.ac.ilpd.mis.shared.dto.activitylevel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import rw.ac.ilpd.mis.shared.enums.ActivityLevelLevels;

@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ActivityLevelRequest {

    @NotNull(message = "Level cannot be null")
    private ActivityLevelLevels level; // Will be validated against enum values

    @NotBlank(message = "Level reference ID cannot be null")
    private String levelRefId;
}
