package rw.ac.ilpd.academicservice.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import rw.ac.ilpd.academicservice.exception.MappingException;
import rw.ac.ilpd.academicservice.model.sql.Lecturer;
import rw.ac.ilpd.academicservice.model.sql.ShortCourseTopic;
import rw.ac.ilpd.academicservice.model.sql.ShortCourseTopicLecturer;
import rw.ac.ilpd.academicservice.repository.sql.LecturerRepository;
import rw.ac.ilpd.academicservice.repository.sql.ShortCourseTopicRepository;
import rw.ac.ilpd.sharedlibrary.dto.lecturercomponentmaterial.LecturerComponentMaterialResponse;
import rw.ac.ilpd.sharedlibrary.dto.shortcoursetopiclecturer.ShortCourseTopicLecturerRequest;
import rw.ac.ilpd.sharedlibrary.dto.shortcoursetopiclecturer.ShortCourseTopicLecturerResponse;

import java.util.Optional;
import java.util.UUID;

/**
 * This class handles logic to map a ShortCourseTopicLecturerRequest to a
 * ShortCourseTopicLecturer entity and map the ShortCourseTopicLecturer entity from the DB
 * to a ShortCourseTopicLecturerResponse object.
 */
@Component
@Slf4j
public class ShortCourseTopicLecturerMapper
{
    /**
     * Converts a ShortCourseTopicLecturerRequest obj to a
     * ShortCourseTopicLecturer entity.
     *
     * Parameter:
     *      ShortCourseTopicLecturerRequest -> Object to be converted into a ShortCourseTopicLecturer entity.
     *
     * Returns:
     *      ShortCourseTopicLecturer entity object or null in case of errors in the conversion
     *      process.
     */
    public ShortCourseTopicLecturer toShortCourseTopicLecturer(
            ShortCourseTopicLecturerRequest request,
            ShortCourseTopic shortCourseTopic,
            Lecturer lecturer)
    {
        if (request == null)
        {
            log.warn("Attempted to map null ShortCourseTopicLecturerRequest");
            return null;
        }

        log.debug("Mapping ShortCourseTopicLecturerRequest obj: {} to ShortCourseTopicLecturer"
                , request);

        return ShortCourseTopicLecturer.builder()
                .shortCourseTopic(shortCourseTopic)
                .lecturer(lecturer)
                .roomId(UUID.fromString(request.getRoomId()))
                .build();
    }

    /**
     * Converts a ShortCourseTopicLecturer entity to a ShortCourseTopicResponse object
     *
     * Parameter:
     *      ShortCourseTopicLecturer -> Object of the SHortCourseTopicLecturer entity
     *      to be converted into a ShortCourseTopicLecturerResponse DTO
     *
     * Returns:
     *      ShortCourseTopicLecturerResponse object to the caller or null if an error is
     *      encountered during the mapping process.
     */
    public ShortCourseTopicLecturerResponse fromShortCourseTopicLecturer(
            ShortCourseTopicLecturer shortCourseTopicLecturer)
    {
        if (shortCourseTopicLecturer == null)
        {
            log.warn("Attempted to map null ShortCourseTopicLecturer object");
            return null;
        }

        log.debug("Mapping ShortCourseTopicLecturer: {}", shortCourseTopicLecturer +
                " to ShortCourseTopicLecturerResponse");

        return ShortCourseTopicLecturerResponse.builder()
                .id(shortCourseTopicLecturer.getId().toString())
                .topicId(shortCourseTopicLecturer.getShortCourseTopic().getId().toString())
                .lecturerId(shortCourseTopicLecturer.getLecturer().getId().toString())
                .roomId(shortCourseTopicLecturer.getRoomId().toString())
                .createdAt(shortCourseTopicLecturer.getCreatedAt().toString())
                .build();
    }
}
