package rw.ac.ilpd.academicservice.mapper;

import org.springframework.stereotype.Component;
import rw.ac.ilpd.academicservice.model.nosql.document.Activity;
import rw.ac.ilpd.academicservice.model.nosql.embedding.ActivityLevel;
import rw.ac.ilpd.sharedlibrary.dto.activity.ActivityRequest;
import rw.ac.ilpd.sharedlibrary.dto.activity.ActivityResponse;
import rw.ac.ilpd.sharedlibrary.dto.activitylevel.ActivityLevelResponse;
import java.util.UUID;

@Component
public class ActivityMapper {
    public Activity toActivity(ActivityRequest request) {
        return Activity.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .activityTypeId(request.getActivityType())
                .intakeId(request.getIntakeId() != null ? UUID.fromString(request.getIntakeId()) : null)
                .roomId(request.getRoomId() != null ? UUID.fromString(request.getRoomId()) : null)
                .componentId(request.getComponentId() != null ? UUID.fromString(request.getComponentId()) : null)
                .moduleId(request.getModuleId() != null ? UUID.fromString(request.getModuleId()) : null)
                .lecturerId(request.getLecturerId() != null ? UUID.fromString(request.getLecturerId()) : null)
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .startDay(request.getStartDay())
                .endDay(request.getEndDay())
                .activityLevels(request.getActivityLevelRequests().stream().map(x->new ActivityLevel(UUID.randomUUID().toString(), x.getLevel(), UUID.fromString(x.getLevelRefId()))).toList())
                .build();
    }

    public static void updateEntityFromRequest(Activity activity, ActivityRequest request) {
        activity.setTitle(request.getTitle());
        activity.setDescription(request.getDescription());
        activity.setActivityTypeId(request.getActivityType());
        activity.setIntakeId(UUID.fromString(request.getIntakeId()));
        activity.setRoomId(UUID.fromString(request.getRoomId()));
        activity.setComponentId(getUUID(request.getComponentId()));
        activity.setModuleId(getUUID(request.getModuleId()));
        activity.setLecturerId(getUUID(request.getLecturerId()));
        activity.setStartTime(request.getStartTime());
        activity.setEndTime(request.getEndTime());
        activity.setStartDay(request.getStartDay());
        activity.setEndDay(request.getEndDay());

    }

    public ActivityResponse fromActivity(Activity activity) {
        return ActivityResponse.builder()
                .id(activity.getId())
                .title(activity.getTitle())
                .description(activity.getDescription())
                .activityType(activity.getActivityTypeId())
                .intakeId(activity.getIntakeId() != null ? activity.getIntakeId().toString() : null)
                .roomId(activity.getRoomId() != null ? activity.getRoomId().toString() : null)
                .createdAt(activity.getCreatedAt() != null ? activity.getCreatedAt().toString() : null)
                .createdBy(activity.getCreatedById() != null ? activity.getCreatedById().toString() : null)
                .componentId(activity.getComponentId() != null ? activity.getComponentId().toString() : null)
                .moduleId(activity.getModuleId() != null ? activity.getModuleId().toString() : null)
                .lecturerId(activity.getLecturerId() != null ? activity.getLecturerId().toString() : null)
                .startTime(activity.getStartTime())
                .lecturerId(activity.getLecturerId() != null ? activity.getLecturerId().toString() : null)
                .endTime(activity.getEndTime())
                .startDay(activity.getStartDay())
                .endDay(activity.getEndDay())
                .activityLevelResponses(activity.getActivityLevels().stream().map(x -> new ActivityLevelResponse(x.getId(), x.getLevel().toString(), x.getLevelRefId().toString())).toList())
                .build();
    }

    private static UUID getUUID(String id) {
        return id != null && !id.isBlank() ? UUID.fromString(id) : null;
    }
}
