package rw.ac.ilpd.academicservice.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import rw.ac.ilpd.academicservice.model.sql.CourseMaterial;
import rw.ac.ilpd.academicservice.model.sql.ShortCourseTopicLecturer;
import rw.ac.ilpd.academicservice.model.sql.ShortCourseTopicMaterial;
import rw.ac.ilpd.sharedlibrary.dto.shortcoursetopicmaterial.ShortCourseTopicMaterialRequest;
import rw.ac.ilpd.sharedlibrary.dto.shortcoursetopicmaterial.ShortCourseTopicMaterialResponse;

/**
 * This class handles logic to map a ShortCourseTopicMaterialRequest to a
 * ShortCourseTopicMaterial entity and map the ShortCourseTopicMaterial entity from the DB
 * to a ShortCourseTopicMaterialResponse object.
 */
@Component
@Slf4j
public class ShortCourseTopicMaterialMapper
{
    /**
     * Converts a ShortCourseTopicMaterialRequest obj to a ShortCourseTopicMaterial entity.
     *
     * Parameter:
     *      ShortCourseTopicMaterialRequest -> Object to be converted into a ShortCourseTopicMaterial entity.
     *
     * Returns:
     *      ShortCourseTopicMaterial entity object or null in case of errors in the conversion
     *      process.
     * */
    public ShortCourseTopicMaterial toShortCourseTopicMaterial(
            ShortCourseTopicMaterialRequest request,
            CourseMaterial courseMaterial,
            ShortCourseTopicLecturer courseTopicLecturer)
    {
        if (request == null)
        {
            log.warn("Attempted to map null ShortCourseTopicMaterialRequest");
            return null;
        }

        log.debug("Mapping ShortCourseTopicMaterialRequest obj: {} to ShortCourseTopicMaterial"
                , request);

        return ShortCourseTopicMaterial.builder()
                .courseMaterial(courseMaterial)
                .topicLecturer(courseTopicLecturer)
                .build();
    }

    /**
     * Converts a ShortCourseTopicMaterial entity to a ShortCourseTopicMaterialResponse object
     *
     * Parameter:
     *      ShortCourseTopicMaterial -> Object of the ShortCourseTopicMaterial entity to be converted into a
     *      ShortCourseTopicMaterialResponse DTO
     *
     * Returns:
     *      ShortCourseTopicMaterialResponse object to the caller or null if an error is
     *      encountered during the mapping process.
     * */
    public ShortCourseTopicMaterialResponse fromShortCourseTopicMaterial(
            ShortCourseTopicMaterial shortCourseTopicMaterial)
    {
        if (shortCourseTopicMaterial == null)
        {
            log.warn("Attempted to map null ShortCourseTopicMaterial object");
            return null;
        }

        log.debug("Mapping ShortCourseTopicMaterial: {} to ShortCourseTopicMaterialResponse object"
                , shortCourseTopicMaterial);

        return ShortCourseTopicMaterialResponse.builder()
                .id(shortCourseTopicMaterial.getId().toString())
                .courseMaterialId(shortCourseTopicMaterial.getCourseMaterial().getId().toString())
                .topicLecturerId(shortCourseTopicMaterial.getTopicLecturer().getId().toString())
                .build();
    }
}
