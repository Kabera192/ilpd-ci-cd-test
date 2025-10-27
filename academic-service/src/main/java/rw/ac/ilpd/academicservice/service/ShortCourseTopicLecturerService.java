package rw.ac.ilpd.academicservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.academicservice.integration.client.RoomClient;
import rw.ac.ilpd.academicservice.mapper.ShortCourseTopicLecturerMapper;
import rw.ac.ilpd.academicservice.model.sql.Lecturer;
import rw.ac.ilpd.academicservice.model.sql.ShortCourseTopic;
import rw.ac.ilpd.academicservice.model.sql.ShortCourseTopicLecturer;
import rw.ac.ilpd.academicservice.repository.sql.LecturerRepository;
import rw.ac.ilpd.academicservice.repository.sql.ShortCourseTopicLecturerRepository;
import rw.ac.ilpd.academicservice.repository.sql.ShortCourseTopicRepository;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.shortcoursetopiclecturer.ShortCourseTopicLecturerRequest;
import rw.ac.ilpd.sharedlibrary.dto.shortcoursetopiclecturer.ShortCourseTopicLecturerResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service class that is responsible for managing ShortCourseTopicLecturer-related operations
 * and business logic for that with pagination and sorting.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ShortCourseTopicLecturerService
{
    private final ShortCourseTopicLecturerRepository shortCourseTopicLecturerRepository;
    private final ShortCourseTopicLecturerMapper shortCourseTopicLecturerMapper;
    private final ShortCourseTopicRepository shortCourseTopicRepository;
    private final LecturerRepository lecturerRepository;
    private final RoomClient roomClient;

    /**
     * Create and persist a shortCourseTopicLecturer entity to the database.
     */
    public ShortCourseTopicLecturerResponse createShortCourseTopicLecturer(
            ShortCourseTopicLecturerRequest shortCourseTopicLecturerRequest)
    {
        log.debug("Creating new shortCourseTopicLecturer: {}, in service layer", shortCourseTopicLecturerRequest);

        log.debug("""
                Finding short course topic: {} for the new ShortCourseTopicLecturer"""
                , shortCourseTopicLecturerRequest.getTopicId());

        // find the ShortCourseTopic entity that is referenced by the ShortCourseTopicLecturer to be saved
        ShortCourseTopic shortCourseTopic = shortCourseTopicRepository.findById(UUID.fromString(
                shortCourseTopicLecturerRequest.getTopicId())).orElse(null);

        if (shortCourseTopic == null)
        {
            log.warn("Could not find short course topic: {}", shortCourseTopicLecturerRequest.getTopicId());
            throw new EntityNotFoundException("short course topic: "
                    + shortCourseTopicLecturerRequest.getTopicId() + " not found");
        }
        log.debug("short course topic found: {}", shortCourseTopic);

        // find the Lecturer entity that is referenced by the ShortCourseTopicLecturer to be saved
        Lecturer lecturer = lecturerRepository.findById(UUID.fromString(
                shortCourseTopicLecturerRequest.getLecturerId())).orElse(null);

        if (lecturer == null)
        {
            log.warn("Could not find lecturer: {}", shortCourseTopicLecturerRequest.getLecturerId());
            throw new EntityNotFoundException("Lecturer: "
                    + shortCourseTopicLecturerRequest.getLecturerId() + " not found");
        }
        log.debug("Lecturer found: {}", lecturer);

        ShortCourseTopicLecturer shortCourseTopicLecturerToSave = shortCourseTopicLecturerMapper
                .toShortCourseTopicLecturer(shortCourseTopicLecturerRequest, shortCourseTopic, lecturer);

        return shortCourseTopicLecturerMapper.fromShortCourseTopicLecturer(shortCourseTopicLecturerRepository
                .save(shortCourseTopicLecturerToSave));
    }

    /**
     * Update the entire resource of a ShortCourseTopicLecturer
     */
    public ShortCourseTopicLecturerResponse updateShortCourseTopicLecturer(String shortCourseTopicLecturerId,
                                           ShortCourseTopicLecturerRequest shortCourseTopicLecturerRequest)
    {
        log.debug("Updating shortCourseTopicLecturer: {}, in service layer", shortCourseTopicLecturerId);
        log.debug("Finding shortCourseTopicLecturer: {} to update", shortCourseTopicLecturerId);

        ShortCourseTopicLecturer shortCourseTopicLecturer = shortCourseTopicLecturerRepository.findById(UUID
                .fromString(shortCourseTopicLecturerId)).orElse(null);

        if (shortCourseTopicLecturer == null)
        {
            log.warn("Could not find shortCourseTopicLecturer: {} to update", shortCourseTopicLecturerId);
            throw new EntityNotFoundException("ShortCourseTopicLecturer: " + shortCourseTopicLecturerId + " not found");
        }

        log.debug("Mapping sent short course topic: {}", shortCourseTopicLecturerRequest.getTopicId());
        ShortCourseTopic shortCourseTopic = shortCourseTopicRepository.findById(UUID.fromString(
                shortCourseTopicLecturerRequest.getTopicId())).orElse(null);

        if (shortCourseTopic == null)
        {
            log.warn("""
                    Could not find short course topic: {} to update shortCourseTopicLecturer: {}"""
                    , shortCourseTopicLecturerRequest.getTopicId(), shortCourseTopicLecturerId);
            throw new EntityNotFoundException("short course topic: "
                    + shortCourseTopicLecturerRequest.getTopicId() + " not found");
        }
        log.debug("Short course topic being mapped to shortCourseTopicLecturer found: {}", shortCourseTopic);

        log.debug("Mapping sent lecturer: {}", shortCourseTopicLecturerRequest.getLecturerId());
        Lecturer lecturer = lecturerRepository.findById(UUID.fromString(
                shortCourseTopicLecturerRequest.getLecturerId())).orElse(null);

        if (lecturer == null)
        {
            log.warn("""
                    Could not find lecturer: {} to update shortCourseTopicLecturer: {}"""
                    , shortCourseTopicLecturerRequest.getLecturerId(), shortCourseTopicLecturerId);
            throw new EntityNotFoundException("lecturer: "
                    + shortCourseTopicLecturerRequest.getLecturerId() + " not found");
        }
        log.debug("Lecturer being mapped to shortCourseTopicLecturer found: {}", lecturer);

        log.debug("Mapping sent room: {}", shortCourseTopicLecturerRequest.getRoomId());

        if (roomClient.get(UUID.fromString(shortCourseTopicLecturerRequest.getRoomId())) == null)
        {
            log.warn("""
                    Could not find room: {} to update shortCourseTopicLecturer: {}"""
                    , shortCourseTopicLecturerRequest.getRoomId(), shortCourseTopicLecturerId);
            throw new EntityNotFoundException("Room: "
                    + shortCourseTopicLecturerRequest.getLecturerId() + " not found");
        }
        log.debug("Room being mapped to shortCourseTopicLecturer found: {}", lecturer);

        log.debug("Updating shortCourseTopicLecturer to: {}", shortCourseTopicLecturerRequest);

        shortCourseTopicLecturer.setLecturer(lecturer);
        shortCourseTopicLecturer.setShortCourseTopic(shortCourseTopic);
        shortCourseTopicLecturer.setRoomId(UUID.fromString(shortCourseTopicLecturerRequest.getRoomId()));
        return shortCourseTopicLecturerMapper.fromShortCourseTopicLecturer(shortCourseTopicLecturerRepository
                .save(shortCourseTopicLecturer));
    }

    /**
     * Fetch all shortCourseTopicLecturers in the database with pagination and sorting by any order
     * the user wants.
     */
    public PagedResponse<ShortCourseTopicLecturerResponse> getAllShortCourseTopicLecturers(int page, int size,
                                                                       String sortBy, String order)
    {
        log.debug("Getting all shortCourseTopicLecturers from service layer");

        // if order == desc then sort by descending order and vice versa.
        Sort sort = order.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        // Should only find programs that are not deleted!
        Page<ShortCourseTopicLecturer> shortCourseTopicLecturers = shortCourseTopicLecturerRepository.findAll(pageable);

        List<ShortCourseTopicLecturerResponse> shortCourseTopicLecturerResponses = shortCourseTopicLecturers.getContent().stream()
                .map(shortCourseTopicLecturerMapper::fromShortCourseTopicLecturer).toList();

        return new PagedResponse<>(
                shortCourseTopicLecturerResponses,
                shortCourseTopicLecturers.getNumber(),
                shortCourseTopicLecturers.getSize(),
                shortCourseTopicLecturers.getTotalElements(),
                shortCourseTopicLecturers.getTotalPages(),
                shortCourseTopicLecturers.isLast()
        );
    }

    /**
     * Fetch a shortCourseTopicLecturer by the ID
     * */
    public ShortCourseTopicLecturerResponse getShortCourseTopicLecturerById(String shortCourseTopicLecturerId)
    {
        log.debug("Finding shortCourseTopicLecturer by id {}", shortCourseTopicLecturerId);
        ShortCourseTopicLecturer shortCourseTopicLecturer = shortCourseTopicLecturerRepository.findById(
                UUID.fromString(shortCourseTopicLecturerId)).orElse(null);

        if (shortCourseTopicLecturer == null)
        {
            log.warn("Could not find shortCourseTopicLecturer: {}", shortCourseTopicLecturerId);
            throw new EntityNotFoundException("ShortCourseTopicLecturer: " + shortCourseTopicLecturerId + " not found");
        }

        log.debug("Successfully found shortCourseTopicLecturer: {}", shortCourseTopicLecturer);
        return shortCourseTopicLecturerMapper.fromShortCourseTopicLecturer(shortCourseTopicLecturer);
    }

    /**
     * Delete a shortCourseTopicLecturer in the database permanently
     * */
    public boolean deleteShortCourseTopicLecturer(String shortCourseTopicLecturerId)
    {
        log.warn("Permanently deleting shortCourseTopicLecturer: {}", shortCourseTopicLecturerId);
        ShortCourseTopicLecturer shortCourseTopicLecturer = shortCourseTopicLecturerRepository.findById(UUID.fromString(shortCourseTopicLecturerId)).orElse(null);

        if (shortCourseTopicLecturer == null)
        {
            log.warn("Could not find shortCourseTopicLecturer to delete: {}", shortCourseTopicLecturerId);
            throw new EntityNotFoundException("ShortCourseTopicLecturer: " + shortCourseTopicLecturerId + " not found");
        }

        shortCourseTopicLecturerRepository.delete(shortCourseTopicLecturer);
        log.info("Deleted shortCourseTopicLecturer successfully: {}", shortCourseTopicLecturer);
        return true;
    }

    /**
     * Utility function that retrieves a short course topic lecturer given
     * the id. This method returns an optional
     * */
    public Optional<ShortCourseTopicLecturer> getEntity(String shortCourseTopicLecturerId)
    {
        return shortCourseTopicLecturerRepository.findById(UUID.fromString(shortCourseTopicLecturerId));
    }
}
