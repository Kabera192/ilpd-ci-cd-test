package rw.ac.ilpd.academicservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import rw.ac.ilpd.academicservice.model.sql.ShortCourseTopic;
import rw.ac.ilpd.sharedlibrary.dto.shortcoursetopic.ShortCourseTopicRequest;
import rw.ac.ilpd.sharedlibrary.dto.shortcoursetopic.ShortCourseTopicResponse;

@Mapper(componentModel = "spring")
public interface ShortCourseTopicMapper {
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now(java.time.ZoneOffset.UTC))")
    ShortCourseTopic toShortCourseTopic(ShortCourseTopicRequest topic);
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now(java.time.ZoneOffset.UTC))")
    ShortCourseTopic toShortCourseTopicUpdate(@MappingTarget ShortCourseTopic shortCourseTopic, ShortCourseTopicRequest topic);
    ShortCourseTopicResponse fromShortCourseTopic(ShortCourseTopic topic);
}
