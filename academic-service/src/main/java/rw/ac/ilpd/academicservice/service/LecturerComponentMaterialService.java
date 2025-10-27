package rw.ac.ilpd.academicservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.ilpd.academicservice.exception.DeleteFailedException;
import rw.ac.ilpd.academicservice.mapper.LecturerComponentMaterialMapper;
import rw.ac.ilpd.academicservice.model.sql.CourseMaterial;
import rw.ac.ilpd.academicservice.model.sql.LecturerComponent;
import rw.ac.ilpd.academicservice.model.sql.LecturerComponentMaterial;
import rw.ac.ilpd.academicservice.repository.sql.LecturerComponentMaterialRepository;
import rw.ac.ilpd.sharedlibrary.dto.coursematerial.CourseMaterialRequest;
import rw.ac.ilpd.sharedlibrary.dto.coursematerial.CourseMaterialResponse;
import rw.ac.ilpd.sharedlibrary.dto.lecturercomponentmaterial.LecturerComponentMaterialRequest;
import rw.ac.ilpd.sharedlibrary.dto.lecturercomponentmaterial.LecturerComponentMaterialResponse;
import rw.ac.ilpd.sharedlibrary.util.ObjectUploadPath;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service class for managing LecturerComponentMaterial entities.
 * Provides business logic for creating, retrieving, updating, and deleting lecturer component materials,
 * with support for pagination and sorting.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LecturerComponentMaterialService
{
    private final LecturerComponentMaterialRepository repository;
    private final LecturerComponentMaterialMapper mapper;
    private final CourseMaterialService courseMaterialService;
    private final LecturerComponentService lecturerComponentService;

    /**
     * Creates a new LecturerComponentMaterial based on the provided request.
     *
     * @param request The LecturerComponentMaterialRequest containing course material and lecturer component IDs.
     * @return LecturerComponentMaterialResponse with the created material's details.
     * @throws IllegalArgumentException if the request is invalid or referenced entities are not found.
     */
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public LecturerComponentMaterialResponse createLecturerComponentMaterial(LecturerComponentMaterialRequest request)
    {
        log.info("Creating new lecturer component material for lecturerComponentId: {}",request.getLecturerComponentId());

        try
        {
            LocalDate date = LocalDate.now();
            LecturerComponent lecturerComponent = lecturerComponentService
                    .getEntity(UUID.fromString(request.getLecturerComponentId())).orElseThrow(() -> new EntityNotFoundException("The  lectured component specified  does not exist"));
//            create course material first
            CourseMaterialResponse courseMaterial = courseMaterialService.createCourseMaterial(CourseMaterialRequest
                    .builder()
                    .bucketName(ObjectUploadPath.Academic.BASE)
                    .objectPath(ObjectUploadPath.Academic.COURSE_MATERIAL_PATH + "/"+date.getYear()+"/"+date.getMonth())
                    .title(request.getTitle())
                    .description(request.getDescription())
                    .attachment(request.getAttachment())
                    .build());
//            find  course material exists
            CourseMaterial getSavedCourseMaterialData = courseMaterialService.getEntity(courseMaterial.getId()).
                    orElseThrow(()->new EntityNotFoundException("Course material not found"));
//            map the request to entity
            LecturerComponentMaterial lecturerComponentMaterial = mapper.toLecturerComponentMaterial(getSavedCourseMaterialData, lecturerComponent);

//           save the lecturer component material
            LecturerComponentMaterial savedEntity = repository.save(lecturerComponentMaterial);
            log.debug("Successfully saved lecturer component material with ID: {}", savedEntity.getId());

            return mapper.fromLecturerComponentMaterial(savedEntity);
        }
        catch (Exception e)
        {
            log.error("Error creating lecturer component material: {}", e.getMessage(), e);
            throw new IllegalStateException("Failed to create lecturer component material", e);
        }
    }

    /**
     * Retrieves a LecturerComponentMaterial by its ID.
     *
     * @param id The UUID of the material to retrieve.
     * @return LecturerComponentMaterialResponse if found, otherwise null.
     */
    @Transactional(readOnly = true)
    public LecturerComponentMaterialResponse getLecturerComponentMaterialById(UUID id)
    {
        log.info("Retrieving lecturer component material with ID: {}", id);

        Optional<LecturerComponentMaterial> materialOptional = repository.findById(id);
        if (materialOptional.isPresent())
        {
            log.debug("Found lecturer component material with ID: {}", id);
            return mapper.fromLecturerComponentMaterial(materialOptional.get());
        }
        else
        {
            log.warn("No lecturer component material found with ID: {}", id);
            return null;
        }
    }

    /**
     * Retrieves all LecturerComponentMaterials for a given lecturer component with pagination and sorting.
     *
     * @param lecturerComponentId The UUID of the lecturer component.
     * @param pageable            Pagination and sorting parameters (page number, page size, sort field, and direction).
     * @return Page of LecturerComponentMaterialResponse objects with pagination metadata.
     */
    @Transactional(readOnly = true)
    public Page<LecturerComponentMaterialResponse> getMaterialsByLecturerComponent(UUID lecturerComponentId,
                                                                                   Pageable pageable)
    {
        log.info("Retrieving materials for lecturer component ID: {} with pagination: {}"
                , lecturerComponentId, pageable);

        LecturerComponent lecturerComponent = lecturerComponentService.getEntity(lecturerComponentId).orElse(null);
        if (lecturerComponent == null)
        {
            log.warn("No lecturer component found with ID: {}", lecturerComponentId);
            throw new IllegalArgumentException("Lecturer component with id: " + lecturerComponentId + " not found");
        }

        Page<LecturerComponentMaterial> materialsPage = repository.
                findByLecturerComponent(lecturerComponent, pageable);
        log.debug("Found {} materials for lecturer component ID: {} on page {}",
                materialsPage.getTotalElements(), lecturerComponentId, pageable.getPageNumber());

        List<LecturerComponentMaterialResponse> responses = materialsPage.getContent().stream()
                .map(mapper::fromLecturerComponentMaterial)
                .toList();

        return new PageImpl<>(responses, pageable, materialsPage.getTotalElements());
    }

    /**
     * Updates an existing LecturerComponentMaterial.
     *
     * @param id      The UUID of the material to update.
     * @param request The LecturerComponentMaterialRequest with updated details.
     * @return LecturerComponentMaterialResponse with the updated material's details.
     * @throws IllegalArgumentException if the material or referenced entities are not found.
     */
@Transactional(readOnly = false)
public String updateLecturerComponentMaterial(UUID id,
                                                                         LecturerComponentMaterialRequest request)
    {
        log.info("Updating lecturer component material with ID: {}", id);

        Optional<LecturerComponentMaterial> materialOptional = repository.findById(id);
        if (materialOptional.isEmpty())
        {
            log.error("No lecturer component material found with ID: {}", id);
            throw new IllegalArgumentException("Lecturer component material not found");
        }

        try
        {
            LocalDate date = LocalDate.now();
            CourseMaterialResponse cmr=courseMaterialService
                    .updateCourseMaterial(
                            materialOptional.get().getCourseMaterial().getId().toString(),
                            CourseMaterialRequest
                                .builder()
                                .bucketName(ObjectUploadPath.Academic.BASE)
                                .objectPath(ObjectUploadPath.Academic.COURSE_MATERIAL_PATH + "/"+date.getYear()+"/"+date.getMonth())
                                .title(request.getTitle())
                                .description(request.getDescription())
                                .attachment(request.getAttachment())
                                .build());
//            find  course material exists
            CourseMaterial getUpdatedCourseMaterialData = courseMaterialService.getEntity(cmr.getId()).
                    orElseThrow(()->new EntityNotFoundException("Course material not found"));
//            updatedEntity.setId(id); // Preserve the existing ID
            LecturerComponentMaterial lecturerComponentMaterial=mapper.toLecturerComponentMaterialUpdate(materialOptional.get(),getUpdatedCourseMaterialData);
            LecturerComponentMaterial savedEntity = repository.save(lecturerComponentMaterial);
            log.debug("Successfully updated lecturer component material with ID: {}", id);
            return "Lecturer course material Updated successfully";
        }
        catch (Exception e)
        {
            log.error("Error updating lecturer component material: {}", e.getMessage(), e);
            throw new IllegalStateException("Failed to update lecturer component material", e);
        }
    }

    /**
     * Deletes a LecturerComponentMaterial by its ID.
     *
     * @param id The UUID of the material to delete.
     * @return true if deletion was successful, false if material not found.
     */
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public boolean deleteLecturerComponentMaterial(UUID id)
    {
        log.info("Deleting lecturer component material with ID: {}", id);
        LecturerComponentMaterial lecturerComponentMaterial = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("Lecturer component material not found"));
//        Extract course material
        CourseMaterial courseMaterial=lecturerComponentMaterial.getCourseMaterial();
       try {
            repository.delete(lecturerComponentMaterial);
            log.info("Hard delete ... component material");
            log.warn("Successfully deleted lecturer component material with ID: {}", id);
            boolean isDeleted=courseMaterialService.deleteCourseMaterial(courseMaterial);
            log.warn("Successfully deleted lecturer component material with ID: {}", id);
            if (isDeleted) {
                log.info("Successfully deleted lecturer component material with ID: {}", id);
                return true;
            }else {
                throw new EntityNotFoundException("Some things went wrong");
           }
       }catch (Exception e){
           throw new DeleteFailedException("Unable to delete lecturer component material");
       }
    }

    /**
     * Converts a LecturerComponentMaterialRequest to an entity, resolving CourseMaterial and LecturerComponent references.
     *
     * @param request The request DTO containing course material and lecturer component IDs.
     * @return LecturerComponentMaterial entity with resolved references.
     * @throws IllegalArgumentException if referenced entities are not found.
     */
//    private LecturerComponentMaterial toEntityWithResolvedReferences(
//            String id  //Add lecturer component material incase we're going to make update
//            ,LecturerComponentMaterialRequest request)
//    {
//        Optional<CourseMaterial> courseMaterialOptional = courseMaterialService
//                .findByIdAndIsDeleted(request.getCourseMaterialId().toString(), false);
//        if (courseMaterialOptional.isEmpty())
//        {
//            log.error("Course material not found with ID: {}", request.getCourseMaterialId());
//            throw new IllegalArgumentException("Course material not found");
//        }
//
//        Optional<LecturerComponent> lecturerComponentOptional = lecturerComponentService
//                .getEntity(request.getLecturerComponentId());
//        if (lecturerComponentOptional.isEmpty())
//        {
//            log.error("Lecturer component not found with ID: {}", request.getLecturerComponentId());
//            throw new IllegalArgumentException("Lecturer component not found");
//        }
//
//        LecturerComponentMaterial entity = mapper.toLecturerComponentMaterial(request,
//                courseMaterialOptional.get(),
//                lecturerComponentOptional.get());
//
//        return entity;
//    }

    /**
     * Utility method to find a LecturerComponentMaterial by id to be used by other service classes
     */
    public Optional<LecturerComponentMaterial> getEntity(UUID id)
    {
        return repository.findById(id);
    }
}