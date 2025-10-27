package rw.ac.ilpd.academicservice.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import rw.ac.ilpd.academicservice.exception.MappingException;
import rw.ac.ilpd.academicservice.model.sql.Intake;
import rw.ac.ilpd.academicservice.model.sql.IntakeCoordinator;
import rw.ac.ilpd.academicservice.repository.sql.IntakeRepository;
import rw.ac.ilpd.sharedlibrary.dto.intake.IntakeCoordinatorRequest;
import rw.ac.ilpd.sharedlibrary.dto.intake.IntakeCoordinatorResponse;
import rw.ac.ilpd.sharedlibrary.dto.programtype.ProgramTypeResponse;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * This class handles logic to map an IntakeCoordinatorRequest to a IntakeCoordinator entity
 * and map the IntakeCoordinator entity from the DB to an IntakeCoordinatorResponse object.
 * */
@Component
@Slf4j
public class IntakeCoordinatorMapper
{
    /**
     * Converts an IntakeCoordinatorRequest to an IntakeCoordinator entity.
     *
     * Parameter:
     *      IntakeCoordinatorRequest -> Object to be converted into an IntakeCoordinator entity.
     *
     * Returns:
     *      IntakeCoordinator entity object or null in case of errors in the conversion
     *      process.
     * */
    public IntakeCoordinator toIntakeCoordinator(
            IntakeCoordinatorRequest coordinatorRequest, Intake intake)
    {
        if (coordinatorRequest == null)
        {
            log.warn("Attempted to map null IntakeCoordinatorRequest: {}", coordinatorRequest);
            return null;
        }

        log.debug("Mapping IntakeCoordinatorRequest: {} to IntakeCoordinator object", coordinatorRequest);

        return IntakeCoordinator.IntakeCoordinatorBuilder
                .anIntakeCoordinator()
                .userId(UUID.fromString(coordinatorRequest.getCoordinatorId()))
                .intake(intake)
                .from(LocalDateTime.parse(coordinatorRequest.getFrom()))
                .to(LocalDateTime.parse(coordinatorRequest.getTo()))
                .build();
    }

    /**
     * Converts an IntakeCoordinator entity to an IntakeCoordinatorResponse entity.
     *
     * Parameter:
     *      IntakeCoordinator -> Object of the IntakeCoordinator entity to be converted into a
     *      IntakeCoordinatorResponse DTO
     *
     * Returns:
     *      IntakeCoordinatorResponse object to the caller or null if an error is
     *      encountered during the mapping process.
     * */
    public IntakeCoordinatorResponse fromIntakeCoordinator(IntakeCoordinator intakeCoordinator)
    {
        if (intakeCoordinator == null)
        {
            log.warn("Attempted to map null IntakeCoordinator object");
            return null;
        }

        log.debug("Mapping IntakeCoordinator: {} to IntakeCoordinatorResponse object", intakeCoordinator);

        return IntakeCoordinatorResponse.builder()
                .id(intakeCoordinator.getId().toString())
                .coordinatorId(intakeCoordinator.getUserId().toString())
                .intakeId(intakeCoordinator.getIntake().getId().toString())
                .from(intakeCoordinator.getFrom().toString())
                .to(intakeCoordinator.getTo().toString())
                .createdAt(intakeCoordinator.getCreatedAt().toString())
                .build();
    }
}
