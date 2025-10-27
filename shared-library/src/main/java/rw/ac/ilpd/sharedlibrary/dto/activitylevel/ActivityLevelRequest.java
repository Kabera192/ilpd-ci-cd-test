/**
 * This file defines the ActivityLevelRequest DTO used for submitting
 * level-specific data related to an activity.
 *
 * Author: Mohamed Gaye
 * Last Changed Date: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.activitylevel;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;
import rw.ac.ilpd.sharedlibrary.enums.ActivityLevelLevels;
import rw.ac.ilpd.sharedlibrary.enums.LevelEnum;

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
    @RestrictedString
    private String levelRefId;
}
