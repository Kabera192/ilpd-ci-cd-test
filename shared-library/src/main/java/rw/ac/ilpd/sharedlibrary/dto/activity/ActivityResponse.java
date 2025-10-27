/**
 * This file defines the ActivityResponse DTO used to represent the response structure
 * of an activity entity within the system.
 *
 * Author: Mohamed Gaye
 * Last Changed Date: 07/07/2025
 */
package rw.ac.ilpd.sharedlibrary.dto.activity;

import lombok.*;
import rw.ac.ilpd.sharedlibrary.dto.activitylevel.ActivityLevelResponse;

import java.time.LocalDate;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityResponse {
    private String id;
    private String title;
    private String description;
    private String activityType;
    private String intakeId;
    private String roomId;
    private String createdAt;
    private String createdBy;
    private String componentId;
    private String moduleId;
    private String lecturerId;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate startDay;
    private LocalDate endDay;
    private List<ActivityLevelResponse> activityLevelResponses;
}
