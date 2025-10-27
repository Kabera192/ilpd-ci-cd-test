package rw.ac.ilpd.academicservice.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.academicservice.exception.EntityAlreadyExists;
import rw.ac.ilpd.academicservice.mapper.ActivityTypeMapper;
import rw.ac.ilpd.academicservice.model.nosql.document.ActivityType;
import rw.ac.ilpd.academicservice.repository.nosql.ActivityRepository;
import rw.ac.ilpd.academicservice.repository.nosql.ActivityTypeRepository;
import rw.ac.ilpd.sharedlibrary.dto.activitytype.ActivityTypeRequest;
import rw.ac.ilpd.sharedlibrary.dto.activitytype.ActivityTypeResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing ActivityType entities.
 */
@Service
@RequiredArgsConstructor
public class ActivityTypeService {

    // Repository for ActivityType persistence operations
    private final ActivityTypeRepository activityTypeRepository;

    // Mapper for converting between DTOs and entities
    private final ActivityTypeMapper activityTypeMapper;
//    private final ActivityService activityService;
    private final ActivityRepository activityRepository;

    /**
     * Creates a new ActivityType if it does not already exist.
     *
     * @param activityTypeRequest DTO containing activity type data
     * @return ResponseEntity with the created ActivityTypeResponse
     * @throws EntityAlreadyExists if ActivityType already exists
     */
    public ResponseEntity<ActivityTypeResponse> createActivityType(@Valid ActivityTypeRequest activityTypeRequest) {
        findByNameAndDeleteStatus(activityTypeRequest.getName(), false)
                .stream()
                .findFirst()
                .ifPresent(activityType -> {
                    throw new EntityAlreadyExists("ActivityType already exists");
                });

        ActivityType savedActivityType = activityTypeRepository.save(
                activityTypeMapper.toActivityType(activityTypeRequest)
        );
        return new ResponseEntity<>(
                activityTypeMapper.fromActivityType(savedActivityType),
                HttpStatus.CREATED
        );
    }

    /**
     * Finds an ActivityType by its ID and delete status.
     *
     * @param id ActivityType ID
     * @param isDeleted Delete status
     * @return Optional containing ActivityType if found
     */
    public Optional<ActivityType> findByIdAndDeleteStatus(String id, boolean isDeleted) {
        return activityTypeRepository.findByIdAndIsDeleted(id, isDeleted);
    }

    /**
     * Finds an ActivityType by its ID.
     *
     * @param id ActivityType ID
     * @return Optional containing ActivityType if found
     */
    public Optional<ActivityType> findById(String id) {
        return activityTypeRepository.findById(id);
    }

    public Optional<ActivityType> getEntity(String id) {
        return activityTypeRepository.findById(id);
    }

    /**
     * Finds an ActivityType by its name and delete status.
     *
     * @param name ActivityType name
     * @param isDeleted Delete status
     * @return Optional containing ActivityType if found
     */
    public Optional<ActivityType> findByNameAndDeleteStatus(String name, boolean isDeleted) {
        return activityTypeRepository.findByNameAndIsDeleted(name, isDeleted);
    }

    /**
     * Updates an existing ActivityType with new data.
     *
     * @param id ActivityType ID
     * @param activityTypeRequest DTO containing updated data
     * @return ResponseEntity with updated ActivityTypeResponse
     * @throws EntityNotFoundException if ActivityType not found
     */
    public ResponseEntity<ActivityTypeResponse> updateActivityType(
            @NotBlank String id,
            @Valid ActivityTypeRequest activityTypeRequest
    ) {
        // Check if the ActivityType is archived (deleted)
        findByIdAndDeleteStatus(id, true)
                .stream()
                .reduce((first, last) -> last)
                .ifPresent(last -> {
                    throw new EntityNotFoundException("ActivityType not found");
                });

        // Find the ActivityType to update
        ActivityType activityType = findById(id)
                .stream()
                .reduce((first, last) -> last)
                .orElseThrow(() -> new EntityNotFoundException("Can't find ActivityType to update"));

        // Save the updated ActivityType
        ActivityType updatedActivityType = activityTypeRepository.save(
                activityTypeMapper.toActivityTypeUpdate(activityType, activityTypeRequest)
        );

        return new ResponseEntity<>(
                activityTypeMapper.fromActivityType(updatedActivityType),
                HttpStatus.OK
        );
    }

    /**
     * Retrieves a paged list of ActivityTypes based on search and display criteria.
     *
     * @param page Page number
     * @param size Page size
     * @param sort Sort field
     * @param search Search keyword
     * @param display Display type ("all" or "archive")
     * @param orderBy Sort order ("asc" or "desc")
     * @return PagedResponse containing ActivityTypeResponse
     */
    public PagedResponse<ActivityTypeResponse> getListOfPagedActivity(
            int page,
            int size,
            String sort,
            String search,
            String display,
            String orderBy
    ) {
        boolean deleteStatus = display.equals("archive");
        Pageable pageable = PageRequest.of(
                page,
                size,
                orderBy.equalsIgnoreCase("asc") ? Sort.by(sort).ascending() : Sort.by(sort).descending()
        );
        List<ActivityTypeResponse> content = new ArrayList<>();
        Page<ActivityType> modulePage;

        // Fetch ActivityTypes based on display and search criteria
        if (display.equals("all")) {
            modulePage = search.isBlank()
                    ? activityTypeRepository.findAll(pageable)
                    : activityTypeRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                    search, search, pageable
            );
        } else {
            modulePage = search.isBlank()
                    ? activityTypeRepository.findByIsDeleted(deleteStatus, pageable)
                    : activityTypeRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndIsDeleted(
                    search, search, deleteStatus, pageable
            );
        }

        // Map ActivityTypes to response DTOs
        content = modulePage.getContent().stream()
                .map(activityTypeMapper::fromActivityType)
                .toList();

        // Return paged response
        return new PagedResponse<>(
                content,
                modulePage.getNumber(),
                modulePage.getSize(),
                modulePage.getTotalElements(),
                modulePage.getTotalPages(),
                modulePage.isLast()
        );
    }

    public ResponseEntity<String> deleteActivityType(@NotBlank(message = "Activity type can not be blank") String id) {
        Optional<ActivityType> optionalActivityType = findById(id);
        if (optionalActivityType.isEmpty()) {
            throw new EntityNotFoundException("ActivityType not found");
        }

        ActivityType activityType = optionalActivityType.get();
        if (activityType.isDeleted()) {
            throw new EntityAlreadyExists("ActivityType is already archived");
        }

//        boolean hasRelatedActivity = activityService.getEntityOptionExistByActivityId(activityType.getId()).isPresent();
        boolean hasRelatedActivity = activityRepository.findByActivityTypeId(activityType.getId()).isPresent();
        if (hasRelatedActivity) {
            activityType.setDeleted(true);
            activityTypeRepository.save(activityType);
            return new ResponseEntity<>("ActivityType archived successfully", HttpStatus.OK);
        } else {
            activityTypeRepository.delete(activityType);
            return new ResponseEntity<>("Activity type has deleted successfully", HttpStatus.OK);
        }
    }
    public ResponseEntity<String> undoDeleteActivityType(@NotBlank(message = "Activity type can not be blank") String id) {
        Optional<ActivityType> optionalActivityType = findByIdAndDeleteStatus(id, true);
        if (optionalActivityType.isEmpty()) {
            return new ResponseEntity<>("ActivityType is not archived", HttpStatus.NOT_FOUND);
        }
        ActivityType activityType = optionalActivityType.get();
        activityType.setDeleted(false);
        activityTypeRepository.save(activityType);
        return new ResponseEntity<>("ActivityType  restored successfully", HttpStatus.OK);
    }
}