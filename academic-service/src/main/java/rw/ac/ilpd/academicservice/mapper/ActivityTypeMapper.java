package rw.ac.ilpd.academicservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import rw.ac.ilpd.academicservice.model.nosql.document.ActivityType;
import rw.ac.ilpd.sharedlibrary.dto.activitytype.ActivityTypeRequest;
import rw.ac.ilpd.sharedlibrary.dto.activitytype.ActivityTypeResponse;

@Mapper(componentModel = "spring")
public interface ActivityTypeMapper {
    @Mapping(target = "isDeleted",constant = "false")
    ActivityType toActivityType(ActivityTypeRequest activityTypeRequest);
    ActivityType toActivityTypeUpdate(@MappingTarget ActivityType activityType, ActivityTypeRequest activityTypeRequest);
    ActivityTypeResponse fromActivityType(ActivityType activityType);
}
