package rw.ac.ilpd.academicservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.ilpd.academicservice.mapper.PossibleSessionCoupleMapper;
import rw.ac.ilpd.academicservice.model.sql.PossibleSessionCouple;
import rw.ac.ilpd.academicservice.model.sql.StudyModeSession;
import rw.ac.ilpd.academicservice.repository.sql.PossibleSessionCoupleRepository;
import rw.ac.ilpd.sharedlibrary.dto.possiblesessioncouple.PossibleSessionCoupleRequest;
import rw.ac.ilpd.sharedlibrary.dto.possiblesessioncouple.PossibleSessionCoupleResponse;

import java.util.Optional;
import java.util.UUID;

/**
 * Service class for managing PossibleSessionCouple entities.
 * Handles business logic for creating, retrieving, updating, and deleting session couples.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PossibleSessionCoupleService
{
    private final PossibleSessionCoupleRepository possibleSessionCoupleRepository;
    private final PossibleSessionCoupleMapper possibleSessionCoupleMapper;
    private final StudyModeSessionService studyModeSessionService;

    /**
     * Creates a new possible session couple based on the provided request DTO.
     *
     * @param request The PossibleSessionCoupleRequest containing session couple details.
     * @return PossibleSessionCoupleResponse containing the saved session couple details.
     * @throws IllegalArgumentException if session1Id or session2Id is not found.
     */
    @Transactional
    public PossibleSessionCoupleResponse createPossibleSessionCouple(PossibleSessionCoupleRequest request)
    {
        log.info("Creating new possible session couple for session1Id: {}, session2Id: {}",
                request.getSession1Id(), request.getSession2Id());

        StudyModeSession session1 = studyModeSessionService.getEntity(request.getSession1Id())
                .orElseThrow(() ->
                {
                    log.error("StudyModeSession with ID {} not found", request.getSession1Id());
                    return new IllegalArgumentException("StudyModeSession with ID " + request.getSession1Id() + " not found");
                });
        StudyModeSession session2 = studyModeSessionService.getEntity(request.getSession2Id())
                .orElseThrow(() ->
                {
                    log.error("StudyModeSession with ID {} not found", request.getSession2Id());
                    return new IllegalArgumentException("StudyModeSession with ID " + request.getSession2Id() + " not found");
                });

        PossibleSessionCouple sessionCouple = possibleSessionCoupleMapper.toPossibleSessionCouple(request, session1, session2);
        sessionCouple.setDeletedStatus(false); // Initialize as not deleted
        PossibleSessionCouple savedSessionCouple = possibleSessionCoupleRepository.save(sessionCouple);

        log.debug("Successfully created possible session couple with ID: {}", savedSessionCouple.getId());
        return possibleSessionCoupleMapper.fromPossibleSessionCouple(savedSessionCouple);
    }

    /**
     * Updates an existing possible session couple.
     *
     * @param id      The UUID of the session couple to update.
     * @param request The PossibleSessionCoupleRequest containing updated details.
     * @return Updated PossibleSessionCoupleResponse if found and not deleted, null otherwise.
     * @throws IllegalArgumentException if session1Id or session2Id is not found.
     */
    @Transactional
    public PossibleSessionCoupleResponse updatePossibleSessionCouple(UUID id, PossibleSessionCoupleRequest request)
    {
        log.info("Updating possible session couple with ID: {}", id);

        Optional<PossibleSessionCouple> existingSessionCouple = possibleSessionCoupleRepository.findByIdAndDeletedStatusFalse(id);
        if (existingSessionCouple.isPresent())
        {
            StudyModeSession session1 = studyModeSessionService.getEntity(request.getSession1Id())
                    .orElseThrow(() ->
                    {
                        log.error("StudyModeSession with ID {} not found", request.getSession1Id());
                        return new IllegalArgumentException("StudyModeSession with ID " + request.getSession1Id() + " not found");
                    });
            StudyModeSession session2 = studyModeSessionService.getEntity(request.getSession2Id())
                    .orElseThrow(() ->
                    {
                        log.error("StudyModeSession with ID {} not found", request.getSession2Id());
                        return new IllegalArgumentException("StudyModeSession with ID " + request.getSession2Id() + " not found");
                    });

            PossibleSessionCouple sessionCouple = possibleSessionCoupleMapper.toPossibleSessionCouple(request, session1, session2);
            sessionCouple.setId(id);
            sessionCouple.setDeletedStatus(false); // Preserve not deleted status
            PossibleSessionCouple updatedSessionCouple = possibleSessionCoupleRepository.save(sessionCouple);

            log.debug("Successfully updated possible session couple with ID: {}", id);
            return possibleSessionCoupleMapper.fromPossibleSessionCouple(updatedSessionCouple);
        }

        log.warn("Possible session couple with ID: {} not found or is deleted", id);
        return null;
    }

    /**
     * Retrieves a possible session couple by its ID.
     *
     * @param id The UUID of the session couple to retrieve.
     * @return PossibleSessionCoupleResponse if found and not deleted, null otherwise.
     */
    @Transactional(readOnly = true)
    public PossibleSessionCoupleResponse getPossibleSessionCoupleById(UUID id)
    {
        log.info("Retrieving possible session couple with ID: {}", id);

        Optional<PossibleSessionCouple> sessionCouple = possibleSessionCoupleRepository.findByIdAndDeletedStatusFalse(id);
        if (sessionCouple.isPresent())
        {
            log.debug("Found possible session couple with ID: {}", id);
            return possibleSessionCoupleMapper.fromPossibleSessionCouple(sessionCouple.get());
        }

        log.warn("Possible session couple with ID: {} not found or is deleted", id);
        return null;
    }

    /**
     * Retrieves all possible session couples with pagination and sorting, excluding deleted ones.
     *
     * @param page    The page number (0-based).
     * @param size    The number of items per page.
     * @param sortBy  The field to sort by (e.g., id, deletedStatus).
     * @param sortDir The sort direction (asc or desc).
     * @return Page of PossibleSessionCoupleResponse objects.
     */
    @Transactional(readOnly = true)
    public Page<PossibleSessionCoupleResponse> getAllPossibleSessionCouples(int page, int size, String sortBy, String sortDir)
    {
        log.info("Retrieving possible session couples with page: {}, size: {}, sortBy: {}, sortDir: {}",
                page, size, sortBy, sortDir);

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<PossibleSessionCouple> sessionCouples = possibleSessionCoupleRepository.findByDeletedStatusFalse(pageable);
        Page<PossibleSessionCoupleResponse> response = sessionCouples.map(possibleSessionCoupleMapper::fromPossibleSessionCouple);

        log.debug("Retrieved {} possible session couples", response.getTotalElements());
        return response;
    }

    /**
     * Soft deletes a possible session couple by setting its deletedStatus to true.
     *
     * @param id The UUID of the session couple to delete.
     * @return true if deleted, false if not found or already deleted.
     */
    @Transactional
    public boolean deletePossibleSessionCouple(UUID id)
    {
        log.info("Soft deleting possible session couple with ID: {}", id);

        Optional<PossibleSessionCouple> sessionCouple = possibleSessionCoupleRepository.findByIdAndDeletedStatusFalse(id);
        if (sessionCouple.isPresent())
        {
            PossibleSessionCouple existingSessionCouple = sessionCouple.get();
            existingSessionCouple.setDeletedStatus(true);
            possibleSessionCoupleRepository.save(existingSessionCouple);
            log.debug("Successfully soft deleted possible session couple with ID: {}", id);
            return true;
        }

        log.warn("Possible session couple with ID: {} not found or already deleted", id);
        return false;
    }

    /**
     * Utility function that finds a possible-session-couple by id
     */
    @Transactional(readOnly = true)
    public Optional<PossibleSessionCouple> getEntity(UUID id)
    {
        return possibleSessionCoupleRepository.findByIdAndDeletedStatusFalse(id);
    }
}