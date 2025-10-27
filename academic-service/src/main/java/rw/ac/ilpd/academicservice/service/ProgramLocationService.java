package rw.ac.ilpd.academicservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.ilpd.academicservice.mapper.ProgramLocationMapper;
import rw.ac.ilpd.academicservice.mapper.ProgramMapper;
import rw.ac.ilpd.academicservice.model.sql.Program;
import rw.ac.ilpd.academicservice.model.sql.ProgramLocation;
import rw.ac.ilpd.academicservice.repository.sql.ProgramLocationRepository;
import rw.ac.ilpd.sharedlibrary.dto.program.ProgramResponse;
import rw.ac.ilpd.sharedlibrary.dto.programlocation.ProgramLocationRequest;
import rw.ac.ilpd.sharedlibrary.dto.programlocation.ProgramLocationResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class for managing ProgramLocation entities.
 * Provides business logic for creating, retrieving, updating, and deleting program-location associations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProgramLocationService
{
    private final ProgramLocationRepository programLocationRepository;
    private final ProgramService programService;
    private final ProgramLocationMapper programLocationMapper;
    private final ProgramMapper programMapper;

    /**
     * Creates a new program-location association based on the provided request.
     *
     * @param request The ProgramLocationRequest containing program and location IDs.
     * @return ProgramLocationResponse containing details of the created association.
     * @throws EntityNotFoundException() if the program or location ID is invalid.
     * @throws IllegalArgumentException if the request is invalid or the association already exists.
     */
    @Transactional
    public ProgramLocationResponse createProgramLocation(ProgramLocationRequest request)
    {
        log.info("Creating new program-location association for programId: {} and locationId: {}",
                request.getProgramId(), request.getLocationId());

        // Validate program existence
        Program program = programService.getEntity(UUID.fromString(request.getProgramId()))
                .orElseThrow(() ->
                {
                    log.error("Program not found for ID: {}", request.getProgramId());
                    return new EntityNotFoundException("Program not found with ID: " + request.getProgramId());
                });

        // Check if the association already exists
        if (programLocationRepository.existsByProgramAndLocationId(
                program, request.getLocationId()))
        {
            log.warn("Program-location association already exists for programId: {} and locationId: {}",
                    request.getProgramId(), request.getLocationId());
            throw new IllegalArgumentException("Program-location association already exists");
        }

        // Map request to entity
        ProgramLocation programLocation = programLocationMapper.toProgramLocation(request, program);
        if (programLocation == null)
        {
            log.error("Failed to map ProgramLocationRequest to ProgramLocation entity");
            throw new IllegalArgumentException("Failed to create program-location association");
        }

        // Save the entity
        ProgramLocation savedProgramLocation = programLocationRepository.save(programLocation);
        log.debug("Successfully saved program-location association with ID: {}", savedProgramLocation.getId());

        // Map to response DTO
        ProgramLocationResponse response = programLocationMapper.fromProgramLocation(savedProgramLocation);
        if (response == null)
        {
            log.error("Failed to map saved ProgramLocation to ProgramLocationResponse");
            throw new IllegalStateException("Failed to map saved program-location to response");
        }

        log.info("Successfully created program-location association with ID: {}", response.getId());
        return response;
    }

    /**
     * Retrieves a program-location association by its ID.
     *
     * @param id The UUID of the program-location association.
     * @return ProgramLocationResponse containing the association details.
     * @throws EntityNotFoundException() if the association is not found.
     */
    @Transactional(readOnly = true)
    public ProgramLocationResponse getProgramLocationById(String id)
    {
        log.info("Retrieving program-location association with ID: {}", id);

        // Validate ID format
        UUID uuid;
        try
        {
            uuid = UUID.fromString(id);
        }
        catch (IllegalArgumentException e)
        {
            log.error("Invalid UUID format for ID: {}", id);
            throw new IllegalArgumentException("Invalid program-location ID format");
        }

        // Find the program-location association
        ProgramLocation programLocation = programLocationRepository.findById(uuid)
                .orElseThrow(() ->
                {
                    log.error("Program-location association not found for ID: {}", id);
                    return new EntityNotFoundException("Program-location association not found with ID: " + id);
                });

        // Map to response DTO
        ProgramLocationResponse response = programLocationMapper.fromProgramLocation(programLocation);
        if (response == null)
        {
            log.error("Failed to map ProgramLocation to ProgramLocationResponse for ID: {}", id);
            throw new IllegalStateException("Failed to map program-location to response");
        }

        log.debug("Successfully retrieved program-location association with ID: {}", id);
        return response;
    }

    /**
     * Retrieves a program-location association by its ID.
     *
     * @param locationId The UUID of the program-location association.
     * @return ProgramLocationResponse containing the association details.
     * @throws EntityNotFoundException if the location does not exist.
     */
    @Transactional(readOnly = true)
    public Page<ProgramResponse> getProgramsByLocation(String locationId, int page, int size,
                                                       String sortBy, String sortDir)
    {
        log.info("Retrieving programs by location: {}", locationId);
        // todo: check if the location exists

        // find all programs by location
        Pageable pageable = setPageable(page, size, sortBy, sortDir);

        // Fetch paginated program locations
        Page<Program> programs = programLocationRepository.findProgramByLocationId(locationId, pageable);
        log.debug("Retrieved {} programs by locationId for page: {}",
                programs.getTotalElements(), pageable);

        // Map entities to response DTOs
        List<ProgramResponse> responses = programs.getContent().stream()
                .map(programMapper::fromProgram)
                .filter(response ->
                {
                    if (response == null)
                    {
                        log.warn("Failed to map a Program entity to ProgramResponse");
                        return false;
                    }
                    return true;
                })
                .collect(Collectors.toList());

        log.info("Successfully retrieved {} programs by locationId for page: {}",
                responses.size(), pageable);
        return new PageImpl<>(responses, pageable, programs.getTotalElements());
    }

    /**
     * Updates an existing program-location association with a new location ID.
     *
     * @param id      The UUID of the program-location association to update.
     * @param request The ProgramLocationRequest containing the new location ID.
     * @return ProgramLocationResponse containing the updated association details.
     * @throws EntityNotFoundException() if the association or program is not found.
     * @throws IllegalArgumentException if the request is invalid or the new association already exists.
     */
    @Transactional
    public ProgramLocationResponse updateProgramLocation(String id, ProgramLocationRequest request)
    {
        log.info("Updating program-location association with ID: {}", id);

        // Validate ID format
        UUID uuid;
        try
        {
            uuid = UUID.fromString(id);
        }
        catch (IllegalArgumentException e)
        {
            log.error("Invalid UUID format for ID: {}", id);
            throw new IllegalArgumentException("Invalid program-location ID format");
        }

        // Find existing program-location
        ProgramLocation programLocation = programLocationRepository.findById(uuid)
                .orElseThrow(() ->
                {
                    log.error("Program-location association not found for ID: {}", id);
                    return new EntityNotFoundException("Program-location association not found with ID: " + id);
                });

        // Validate program existence
        Program program = programService.getEntity(UUID.fromString(request.getProgramId()))
                .orElseThrow(() ->
                {
                    log.error("Program not found for ID: {}", request.getProgramId());
                    return new EntityNotFoundException("Program not found with ID: " + request.getProgramId());
                });

        // Check if the new association already exists
        if (!programLocation.getLocationId().equals(request.getLocationId()) &&
                programLocationRepository.existsByProgramAndLocationId(program, request.getLocationId()))
        {
            log.warn("Program-location association already exists for programId: {} and locationId: {}",
                    request.getProgramId(), request.getLocationId());
            throw new IllegalArgumentException("Program-location association already exists");
        }

        // Update fields
        programLocation.setProgram(program);
        programLocation.setLocationId(request.getLocationId());
        log.debug("Updated program-location association with ID: {} with new programId: {} and locationId: {}",
                id, request.getProgramId(), request.getLocationId());

        // Save updated entity
        ProgramLocation updatedProgramLocation = programLocationRepository.save(programLocation);
        log.debug("Successfully saved updated program-location association with ID: {}", id);

        // Map to response DTO
        ProgramLocationResponse response = programLocationMapper.fromProgramLocation(updatedProgramLocation);
        if (response == null)
        {
            log.error("Failed to map updated ProgramLocation to ProgramLocationResponse for ID: {}", id);
            throw new IllegalStateException("Failed to map updated program-location to response");
        }

        log.info("Successfully updated program-location association with ID: {}", id);
        return response;
    }

    /**
     * Deletes a program-location association by its ID.
     *
     * @param id The UUID of the program-location association to delete.
     * @throws EntityNotFoundException() if the association is not found.
     */
    @Transactional
    public void deleteProgramLocation(String id)
    {
        log.info("Deleting program-location association with ID: {}", id);

        // Validate ID format
        UUID uuid;
        try
        {
            uuid = UUID.fromString(id);
        }
        catch (IllegalArgumentException e)
        {
            log.error("Invalid UUID format for ID: {}", id);
            throw new IllegalArgumentException("Invalid program-location ID format");
        }

        // Check if association exists
        if (!programLocationRepository.existsById(uuid))
        {
            log.error("Program-location association not found for ID: {}", id);
            throw new EntityNotFoundException("Program-location association not found with ID: " + id);
        }

        // Delete the association
        programLocationRepository.deleteById(uuid);
        log.info("Successfully deleted program-location association with ID: {}", id);
    }

    /**
     * Retrieves all program-location associations with pagination and sorting support.
     *
     * @return Page of ProgramLocationResponse containing the list of associations.
     */
    @Transactional(readOnly = true)
    public Page<ProgramLocationResponse> findAllProgramLocations(int page, int size, String sortBy, String sortDir)
    {
        log.info("Retrieving possible session couples with page: {}, size: {}, sortBy: {}, sortDir: {}",
                page, size, sortBy, sortDir);

        Pageable pageable = setPageable(page, size, sortBy, sortDir);

        // Fetch paginated program locations
        Page<ProgramLocation> programLocations = programLocationRepository.findAll(pageable);
        log.debug("Retrieved {} program-location associations for page: {}",
                programLocations.getTotalElements(), pageable);

        // Map entities to response DTOs
        List<ProgramLocationResponse> responses = programLocations.getContent().stream()
                .map(programLocationMapper::fromProgramLocation)
                .filter(response ->
                {
                    if (response == null)
                    {
                        log.warn("Failed to map a ProgramLocation entity to ProgramLocationResponse");
                        return false;
                    }
                    return true;
                })
                .collect(Collectors.toList());

        log.info("Successfully retrieved {} program-location associations for page: {}",
                responses.size(), pageable);
        return new PageImpl<>(responses, pageable, programLocations.getTotalElements());
    }

    /**
     * Utility function to be used by other service classes to find a
     * program location by id
     * */
    @Transactional(readOnly = true)
    public Optional<ProgramLocation> getEntity(String id)
    {
        log.info("Retrieving program-location association with ID: {}", id);

        // Validate ID format
        UUID uuid;
        try
        {
            uuid = UUID.fromString(id);
        }
        catch (IllegalArgumentException e)
        {
            log.error("Invalid UUID format for ID: {}", id);
            throw new IllegalArgumentException("Invalid program-location ID format");
        }

        // Find the program-location association
        return programLocationRepository.findById(uuid);
    }

    private Pageable setPageable(int page, int size, String sortBy, String sortDir)
    {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        return PageRequest.of(page, size, sort);
    }
}