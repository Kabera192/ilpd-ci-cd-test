package rw.ac.ilpd.academicservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.academicservice.mapper.ShortCourseTopicMaterialMapper;
import rw.ac.ilpd.academicservice.model.sql.*;
import rw.ac.ilpd.academicservice.repository.sql.ShortCourseTopicMaterialRepository;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.shortcoursetopicmaterial.ShortCourseTopicMaterialRequest;
import rw.ac.ilpd.sharedlibrary.dto.shortcoursetopicmaterial.ShortCourseTopicMaterialResponse;

import java.util.List;
import java.util.UUID;

/**
 * Service class that is responsible for managing ShortCourseTopicMaterial-related operations
 * and business logic for that with pagination and sorting.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ShortCourseTopicMaterialService
{
    private final ShortCourseTopicMaterialRepository shortCourseTopicMaterialRepository;
    private final CourseMaterialService courseMaterialService;
    private final ShortCourseTopicLecturerService shortCourseTopicLecturerService;
    private final ShortCourseTopicMaterialMapper shortCourseTopicMaterialMapper;

    /**
     * Create and persist a shortCourseTopicMaterial entity to the database.
     */
    public ShortCourseTopicMaterialResponse createShortCourseTopicMaterial(
            ShortCourseTopicMaterialRequest shortCourseTopicMaterialRequest)
    {
        log.debug("Creating new shortCourseTopicMaterial: {}, in service layer", shortCourseTopicMaterialRequest);

        log.debug("""
                Finding course material: {} for the new ShortCourseTopicMaterial"""
                , shortCourseTopicMaterialRequest.getCourseMaterialId());

        // find the CourseMaterial entity that is referenced by the ShortCourseTopicMaterial to be saved
        CourseMaterial courseMaterial = courseMaterialService.findByIdAndIsDeleted(
                shortCourseTopicMaterialRequest.getCourseMaterialId(), false)
                .orElse(null);

        if (courseMaterial == null)
        {
            log.warn("Could not find course material: {}", shortCourseTopicMaterialRequest.getCourseMaterialId());
            throw new EntityNotFoundException("course material: "
                    + shortCourseTopicMaterialRequest.getCourseMaterialId() + " not found");
        }
        log.debug("course material found: {}", courseMaterial);

        // find the Short course Lecturer entity that is referenced by the ShortCourseTopicMaterial to be saved
        ShortCourseTopicLecturer lecturer = shortCourseTopicLecturerService.getEntity(
                shortCourseTopicMaterialRequest.getTopicLecturerId()).orElse(null);

        if (lecturer == null)
        {
            log.warn("Could not find short course lecturer: {}", shortCourseTopicMaterialRequest.getTopicLecturerId());
            throw new EntityNotFoundException("Short course lecturer: "
                    + shortCourseTopicMaterialRequest.getTopicLecturerId() + " not found");
        }
        log.debug("Short course lecturer found: {}", lecturer);

        ShortCourseTopicMaterial shortCourseTopicMaterialToSave = shortCourseTopicMaterialMapper
                .toShortCourseTopicMaterial(shortCourseTopicMaterialRequest, courseMaterial, lecturer);

        return shortCourseTopicMaterialMapper.fromShortCourseTopicMaterial(shortCourseTopicMaterialRepository
                .save(shortCourseTopicMaterialToSave));
    }

    /**
     * Update the entire resource of a ShortCourseTopicMaterial
     */
    public ShortCourseTopicMaterialResponse updateShortCourseTopicMaterial(String shortCourseTopicMaterialId,
                                            ShortCourseTopicMaterialRequest shortCourseTopicMaterialRequest)
    {
        log.debug("Updating shortCourseTopicMaterial: {}, in service layer", shortCourseTopicMaterialId);
        log.debug("Finding shortCourseTopicMaterial: {} to update", shortCourseTopicMaterialId);

        ShortCourseTopicMaterial shortCourseTopicMaterial = shortCourseTopicMaterialRepository.findById(UUID
                .fromString(shortCourseTopicMaterialId)).orElse(null);

        if (shortCourseTopicMaterial == null)
        {
            log.warn("Could not find shortCourseTopicMaterial: {} to update", shortCourseTopicMaterialId);
            throw new EntityNotFoundException("ShortCourseTopicMaterial: " + shortCourseTopicMaterialId + " not found");
        }

        log.debug("Mapping sent course material: {}", shortCourseTopicMaterialRequest.getCourseMaterialId());
        CourseMaterial courseMaterial = courseMaterialService.findByIdAndIsDeleted(
                shortCourseTopicMaterialRequest.getCourseMaterialId(), false).orElse(null);

        if (courseMaterial == null)
        {
            log.warn("""
                    Could not find course material: {} to update shortCourseTopicMaterial: {}"""
                    , shortCourseTopicMaterialRequest.getCourseMaterialId(), shortCourseTopicMaterialId);
            throw new EntityNotFoundException("Course material: "
                    + shortCourseTopicMaterialRequest.getCourseMaterialId() + " not found");
        }
        log.debug("Course material being mapped to shortCourseTopicMaterial found: {}", courseMaterial);

        log.debug("Mapping sent short course lecturer: {}", shortCourseTopicMaterialRequest.getTopicLecturerId());
        ShortCourseTopicLecturer lecturer = shortCourseTopicLecturerService.getEntity(
                shortCourseTopicMaterialRequest.getTopicLecturerId()).orElse(null);

        if (lecturer == null)
        {
            log.warn("""
                    Could not find short course lecturer: {} to update shortCourseTopicMaterial: {}"""
                    , shortCourseTopicMaterialRequest.getTopicLecturerId(), shortCourseTopicMaterialId);
            throw new EntityNotFoundException("short course lecturer: "
                    + shortCourseTopicMaterialRequest.getTopicLecturerId() + " not found");
        }
        log.debug("Short course lecturer being mapped to shortCourseTopicMaterial found: {}", lecturer);

        log.debug("Updating shortCourseTopicMaterial to: {}", shortCourseTopicMaterialRequest);

        shortCourseTopicMaterial.setTopicLecturer(lecturer);
        shortCourseTopicMaterial.setCourseMaterial(courseMaterial);

        return shortCourseTopicMaterialMapper.fromShortCourseTopicMaterial(shortCourseTopicMaterialRepository
                .save(shortCourseTopicMaterial));
    }

    /**
     * Fetch all shortCourseTopicMaterials in the database with pagination and sorting by any order
     * the user wants.
     */
    public PagedResponse<ShortCourseTopicMaterialResponse> getAllShortCourseTopicMaterials(int page, int size,
                                                                                           String sortBy, String order)
    {
        log.debug("Getting all shortCourseTopicMaterials from service layer");

        // if order == desc then sort by descending order and vice versa.
        Sort sort = order.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        // Should only find programs that are not deleted!
        Page<ShortCourseTopicMaterial> shortCourseTopicMaterials = shortCourseTopicMaterialRepository.findAll(pageable);

        List<ShortCourseTopicMaterialResponse> shortCourseTopicMaterialResponses = shortCourseTopicMaterials.getContent().stream()
                .map(shortCourseTopicMaterialMapper::fromShortCourseTopicMaterial).toList();

        return new PagedResponse<>(
                shortCourseTopicMaterialResponses,
                shortCourseTopicMaterials.getNumber(),
                shortCourseTopicMaterials.getSize(),
                shortCourseTopicMaterials.getTotalElements(),
                shortCourseTopicMaterials.getTotalPages(),
                shortCourseTopicMaterials.isLast()
        );
    }

    /**
     * Fetch a shortCourseTopicMaterial by the ID
     * */
    public ShortCourseTopicMaterialResponse getShortCourseTopicMaterialById(String shortCourseTopicMaterialId)
    {
        log.debug("Finding shortCourseTopicMaterial by id {}", shortCourseTopicMaterialId);
        ShortCourseTopicMaterial shortCourseTopicMaterial = shortCourseTopicMaterialRepository.findById(
                UUID.fromString(shortCourseTopicMaterialId)).orElse(null);

        if (shortCourseTopicMaterial == null)
        {
            log.warn("Could not find shortCourseTopicMaterial: {}", shortCourseTopicMaterialId);
            throw new EntityNotFoundException("ShortCourseTopicMaterial: " + shortCourseTopicMaterialId + " not found");
        }

        log.debug("Successfully found shortCourseTopicMaterial: {}", shortCourseTopicMaterial);
        return shortCourseTopicMaterialMapper.fromShortCourseTopicMaterial(shortCourseTopicMaterial);
    }

    /**
     * Delete a shortCourseTopicMaterial in the database permanently
     * */
    public boolean deleteShortCourseTopicMaterial(String shortCourseTopicMaterialId)
    {
        log.warn("Permanently deleting shortCourseTopicMaterial: {}", shortCourseTopicMaterialId);
        ShortCourseTopicMaterial shortCourseTopicMaterial = shortCourseTopicMaterialRepository.findById(UUID.fromString(shortCourseTopicMaterialId)).orElse(null);

        if (shortCourseTopicMaterial == null)
        {
            log.warn("Could not find shortCourseTopicMaterial to delete: {}", shortCourseTopicMaterialId);
            throw new EntityNotFoundException("ShortCourseTopicMaterial: " + shortCourseTopicMaterialId + " not found");
        }

        shortCourseTopicMaterialRepository.delete(shortCourseTopicMaterial);
        log.info("Deleted shortCourseTopicMaterial successfully: {}", shortCourseTopicMaterial);
        return true;
    }
}
