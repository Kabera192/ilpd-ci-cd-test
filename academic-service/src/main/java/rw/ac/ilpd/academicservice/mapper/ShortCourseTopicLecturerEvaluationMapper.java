package rw.ac.ilpd.academicservice.mapper;

import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import rw.ac.ilpd.academicservice.model.sql.ShortCourseTopicLecturerEvaluation;
import rw.ac.ilpd.sharedlibrary.dto.shortcoursetopiclecturerevaluation.ShortCourseTopicLecturerEvaluationRequest;
import rw.ac.ilpd.sharedlibrary.dto.shortcoursetopiclecturerevaluation.ShortCourseTopicLecturerEvaluationResponse;

@Mapper(componentModel = "spring")
public interface ShortCourseTopicLecturerEvaluationMapper {
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now(java.time.ZoneOffset.UTC))")
    ShortCourseTopicLecturerEvaluation toEvaluation(ShortCourseTopicLecturerEvaluationRequest request);
    ShortCourseTopicLecturerEvaluation toEvaluationUpdate(@MappingTarget ShortCourseTopicLecturerEvaluation sctlEvaluation, ShortCourseTopicLecturerEvaluationRequest request);

    ShortCourseTopicLecturerEvaluationResponse fromEvaluation(ShortCourseTopicLecturerEvaluation saved);
}
