/**
 * This file defines the ActivityRequest DTO used for transferring activity data.
 * It includes validation annotations and supports optional fields where applicable.
 *
 * Author: Mohamed Gaye
 * Last Changed Date: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.activity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import jakarta.validation.constraints.NotNull;
import rw.ac.ilpd.sharedlibrary.dto.activitylevel.ActivityLevelRequest;
import rw.ac.ilpd.sharedlibrary.dto.activitylevel.ActivityLevelResponse;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityRequest {

    @NotBlank(message = "Title cannot be null")
    @RestrictedString
    private String title;

    @RestrictedString
    private String description; // Optional

    @NotBlank(message = "Activity type cannot be null or blank")
    @RestrictedString
    private String activityType;

    private String intakeId;

    private String roomId;

    private String componentId; // Nullable

    private String moduleId; // Nullable

    private String lecturerId; // Nullable

    @NotNull(message = "Start time cannot be null")
    private LocalTime startTime;

    @NotNull(message = "End time cannot be null")
    private LocalTime endTime;

    @NotNull(message = "Start day cannot be null")
    private LocalDate startDay;

    @NotNull(message = "End day cannot be null")
    private LocalDate endDay;

    @NotEmpty(message = "The list of levels should not be empty")
    private List<ActivityLevelRequest> activityLevelRequests;
}
