package rw.ac.ilpd.academicservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.ilpd.academicservice.integration.client.UserClient;
import rw.ac.ilpd.academicservice.mapper.IntakeCoordinatorMapper;
import rw.ac.ilpd.academicservice.model.sql.Intake;
import rw.ac.ilpd.academicservice.model.sql.IntakeCoordinator;
import rw.ac.ilpd.academicservice.repository.sql.IntakeCoordinatorRepository;
import rw.ac.ilpd.academicservice.repository.sql.IntakeRepository;
import rw.ac.ilpd.sharedlibrary.dto.intake.IntakeCoordinatorRequest;
import rw.ac.ilpd.sharedlibrary.dto.intake.IntakeCoordinatorResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service class that is responsible for managing IntakeCoordinator-related operations
 * and business logic for that with pagination and sorting.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class IntakeCoordinatorService
{
    private final IntakeCoordinatorRepository intakeCoordinatorRepository;
    private final IntakeCoordinatorMapper intakeCoordinatorMapper;
    private final IntakeRepository intakeRepository;
    private final UserClient userClient;

    /**
     * Create and persist a IntakeCoordinator entity to the database.
     */
    @Transactional
    public IntakeCoordinatorResponse createIntakeCoordinator(
            IntakeCoordinatorRequest intakeCoordinatorRequest)
    {
        log.debug("Creating new intake coordinator: {}, in service layer", intakeCoordinatorRequest);

        log.debug("Finding intake: {} for the new intake coordinator", intakeCoordinatorRequest.getIntakeId());
        // first find the Intake entity that is referenced by the IntakeCoordinator to be saved
        Intake intake = intakeRepository.findById(UUID.fromString(
                intakeCoordinatorRequest.getIntakeId())).orElse(null);

        if (intake == null)
        {
            log.warn("Could not find intake: {}", intakeCoordinatorRequest.getIntakeId());
            throw new EntityNotFoundException("Intake: "
                    + intakeCoordinatorRequest.getIntakeId() + " not found");
        }
        log.debug("Intake found: {}", intake);

        // validate that the coordinator id is a valid user that exists
        log.debug("Validating whether coordinator exists as a user");

        if (userClient.getUserById(intakeCoordinatorRequest.getCoordinatorId()) == null)
        {
            log.error("Coordinator id: {} does not exist", intakeCoordinatorRequest.getCoordinatorId());
            throw new EntityNotFoundException("Coordinator id provided does not exist");
        }
        log.debug("Coordinator id successfully validated.");

        IntakeCoordinator coordinatorToSave = intakeCoordinatorMapper.toIntakeCoordinator(
                intakeCoordinatorRequest, intake);
        return intakeCoordinatorMapper.fromIntakeCoordinator(
                intakeCoordinatorRepository.save(coordinatorToSave));
    }

    /**
     * Update the entire resource of an IntakeCoordinator
     */
    @Transactional
    public IntakeCoordinatorResponse updateIntakeCoordinator(String coordinatorId,
                                                             IntakeCoordinatorRequest intakeCoordinatorRequest)
    {
        log.debug("Updating intake coordinator: {}, in service layer", coordinatorId);
        log.debug("Finding intake coordinator: {} to update", coordinatorId);

        IntakeCoordinator coordinator = intakeCoordinatorRepository.findById(UUID
                .fromString(coordinatorId)).orElse(null);

        if (coordinator == null)
        {
            log.warn("Could not find intake coordinator: {} to update", coordinatorId);
            throw new EntityNotFoundException("Intake coordinator: " + coordinatorId + " not found");
        }

        log.debug("Mapping sent intake: {}", intakeCoordinatorRequest.getIntakeId());
        Intake intake = intakeRepository.findById(UUID.fromString(
                intakeCoordinatorRequest.getIntakeId())).orElse(null);

        if (intake == null)
        {
            log.warn("""
                    Could not find intake: {} to update intake coordinator: {}"""
                    , intakeCoordinatorRequest.getIntakeId(), coordinatorId);
            throw new EntityNotFoundException("Intake: "
                    + intakeCoordinatorRequest.getIntakeId() + " not found");
        }

        // TODO: VALIDATE THAT THE USER_ID OF THE COORDINATOR EXISTS

        log.debug("Updating intake coordinator to: {}", intakeCoordinatorRequest);

        coordinator.setUserId(UUID.fromString(intakeCoordinatorRequest.getCoordinatorId()));
        coordinator.setIntake(intake);
        coordinator.setFrom(LocalDateTime.parse(intakeCoordinatorRequest.getFrom()));
        coordinator.setTo(LocalDateTime.parse(intakeCoordinatorRequest.getTo()));
        return intakeCoordinatorMapper.fromIntakeCoordinator(intakeCoordinatorRepository.save(coordinator));
    }

    /**
     * Fetch all intake coordinators in the database with pagination and sorting by any order
     * the user wants.
     */
    @Transactional(readOnly = true)
    public PagedResponse<IntakeCoordinatorResponse> getAllIntakeCoordinators(
            int page, int size, String sortBy, String order)
    {
        log.debug("Getting all intake coordinators from service layer");

        // if order == desc then sort by descending order and vice versa.
        Sort sort = order.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        // Should only find programs that are not deleted!
        Page<IntakeCoordinator> coordinators = intakeCoordinatorRepository.findAll(pageable);

        List<IntakeCoordinatorResponse> coordinatorResponses = coordinators.getContent().stream()
                .map(intakeCoordinatorMapper::fromIntakeCoordinator).toList();

        return new PagedResponse<>(
                coordinatorResponses,
                coordinators.getNumber(),
                coordinators.getSize(),
                coordinators.getTotalElements(),
                coordinators.getTotalPages(),
                coordinators.isLast()
        );
    }

    /**
     * Fetch a coordinator by the ID
     */
    @Transactional(readOnly = true)
    public IntakeCoordinatorResponse getIntakeCoordinatorById(String coordinatorId)
    {
        log.debug("Finding coordinator by id {}", coordinatorId);
        IntakeCoordinator coordinator = getEntity(
                UUID.fromString(coordinatorId)).orElse(null);

        if (coordinator == null)
        {
            log.warn("Could not find coordinator: {}", coordinatorId);
            throw new EntityNotFoundException("IntakeCoordinator: " + coordinatorId + " not found");
        }

        log.debug("Successfully found coordinator: {}", coordinator);
        return intakeCoordinatorMapper.fromIntakeCoordinator(coordinator);
    }

    /**
     * Delete a coordinator in the database permanently
     * */
    @Transactional
    public boolean deleteIntakeCoordinator(String coordinatorId)
    {
        log.warn("Permanently deleting coordinator: {}", coordinatorId);
        IntakeCoordinator coordinator = intakeCoordinatorRepository.findById(UUID.fromString(coordinatorId)).orElse(null);

        if (coordinator == null)
        {
            log.warn("Could not find coordinator to delete: {}", coordinatorId);
            throw new EntityNotFoundException("IntakeCoordinator: " + coordinatorId + " not found");
        }

        intakeCoordinatorRepository.delete(coordinator);
        log.info("Deleted coordinator successfully: {}", coordinator);
        return true;
    }

    /**
     * Utility function for retrieving an intake-coordinator to be used by other service classes
     * */
    @Transactional(readOnly = true)
    public Optional<IntakeCoordinator> getEntity(UUID coordinatorId)
    {
        return intakeCoordinatorRepository.findById(coordinatorId);
    }
}
