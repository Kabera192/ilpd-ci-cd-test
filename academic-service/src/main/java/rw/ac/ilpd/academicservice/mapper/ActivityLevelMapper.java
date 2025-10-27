package rw.ac.ilpd.academicservice.mapper;

import org.springframework.stereotype.Component;
import rw.ac.ilpd.academicservice.model.nosql.embedding.ActivityLevel;
import rw.ac.ilpd.sharedlibrary.dto.activitylevel.ActivityLevelRequest;
import rw.ac.ilpd.sharedlibrary.dto.activitylevel.ActivityLevelResponse;
import java.util.UUID;

@Component
public class ActivityLevelMapper {

    public ActivityLevel toActivityLevel(ActivityLevelRequest request) {
        return ActivityLevel.builder()
                .level(request.getLevel())
                .levelRefId(UUID.fromString(request.getLevelRefId()))
                .build();
    }

    public ActivityLevelResponse fromActivityLevel(ActivityLevel activityLevel) {
        return ActivityLevelResponse.builder()
                .id(activityLevel.getId())
                .level(activityLevel.getLevel() != null ? activityLevel.getLevel().toString() : null)
                .levelRefId(activityLevel.getLevelRefId() != null ?
                        activityLevel.getLevelRefId().toString() : null)
                .build();
    }

    // For updating existing activity level
    public ActivityLevel updateActivityLevel(ActivityLevelRequest request, ActivityLevel existing) {
        existing.setLevel(request.getLevel());
        existing.setLevelRefId(UUID.fromString(request.getLevelRefId()));
        return existing;
    }
}