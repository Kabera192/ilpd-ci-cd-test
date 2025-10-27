package rw.ac.ilpd.academicservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import rw.ac.ilpd.academicservice.integration.client.UserClient;
import rw.ac.ilpd.academicservice.mapper.ShortCourseTopicMapper;
import rw.ac.ilpd.academicservice.model.sql.ShortCourseTopic;
import rw.ac.ilpd.academicservice.repository.sql.ShortCourseTopicRepository;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.shortcoursetopic.ShortCourseTopicRequest;
import rw.ac.ilpd.sharedlibrary.dto.shortcoursetopic.ShortCourseTopicResponse;
import rw.ac.ilpd.sharedlibrary.dto.user.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service for managing ShortCourseTopic CRUD operations.
 */
@Service
@RequiredArgsConstructor
public class ShortCourseTopicService {
    private final ShortCourseTopicRepository shortCourseTopicRepository;
    private final ShortCourseTopicMapper shortCourseTopicMapper;
    private final UserClient userClient;

    /**
     * Creates a new ShortCourseTopic.
     *
     * @param request DTO containing topic data
     * @return ResponseEntity with created ShortCourseTopicResponse
     */
    public ResponseEntity<ShortCourseTopicResponse> createShortCourseTopic(ShortCourseTopicRequest request, Principal principal) {
       shortCourseTopicRepository.findByIntakeId(UUID.fromString(request.getIntakeId()))
                .orElseThrow(()->new EntityNotFoundException("Intake doesn't exist"));
        UserResponse user=userClient.getUserByEmail(principal.getName());
        if(user==null){throw new EntityNotFoundException("The creator of the short course topic doesn't exist");}
        ShortCourseTopic topic = shortCourseTopicMapper.toShortCourseTopic(request);
        ShortCourseTopic saved = shortCourseTopicRepository.save(topic);
        return ResponseEntity.ok(shortCourseTopicMapper.fromShortCourseTopic(saved));
    }

    /**
     * Retrieves a ShortCourseTopic by ID.
     *
     * @param id Topic ID
     * @return ResponseEntity with ShortCourseTopicResponse
     */
    public Optional<ShortCourseTopic> getEntity(String id) {
        return shortCourseTopicRepository.findById(UUID.fromString(id));
    }
    public ResponseEntity<ShortCourseTopicResponse> getShortCourseTopic(String id) {
        ShortCourseTopic topic = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("ShortCourseTopic not found"));
        return ResponseEntity.ok(shortCourseTopicMapper.fromShortCourseTopic(topic));
    }

   /**
     * Retrieves all ShortCourseTopics with optional filtering for archived (deleted) topics and search.
     *
     * @param display Display filter: "archive" for deleted, "all" for all, otherwise only active (deleteStatus=false)
     * @param search Search keyword (applied to topic name or description)
     * @return ResponseEntity with list of ShortCourseTopicResponse
     */
    public ResponseEntity<List<ShortCourseTopicResponse>> getAllShortCourseTopics(
            @RequestParam(defaultValue = "active") String display,
            @RequestParam(defaultValue = "search") String search
    ) {
        List<ShortCourseTopic> topics;

        if ("archive".equalsIgnoreCase(display)) {
            // Only archived topics (deleteStatus = true)
            topics =search.isBlank()? shortCourseTopicRepository.findByIsDeleted(true):
            shortCourseTopicRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndIsDeleted(search,search,true);
        } else if ("all".equalsIgnoreCase(display)) {
            // All topics, apply search if provided
            if (search != null && !search.isBlank()) {
                topics = shortCourseTopicRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(search, search);
            } else {
                topics = shortCourseTopicRepository.findAll();
            }
        } else {
            // Only active topics (deleteStatus = false), apply search if provided
            if (search != null && !search.isBlank()) {
                topics = shortCourseTopicRepository.findByIsDeletedAndNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                        false, search, search);
            } else {
                topics = shortCourseTopicRepository.findByIsDeleted(false);
            }
        }

        List<ShortCourseTopicResponse> response = topics.stream()
                .map(shortCourseTopicMapper::fromShortCourseTopic)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves paged ShortCourseTopics with filtering, search, sorting, and order by.
     *
     * @param page Page number
     * @param size Page size
     * @param display Display filter: "archive" for deleted, "all" for all, otherwise only active (deleteStatus=false)
     * @param search Search keyword (applied to topic name or description)
     * @param sort Sort field
     * @param orderBy Sort order ("asc" or "desc")
     * @return ResponseEntity with paged ShortCourseTopicResponse
     */
    public ResponseEntity<PagedResponse<ShortCourseTopicResponse>> getPagedShortCourseTopics(
            int page,
            int size,
            String display,
            String search,
            String sort,
            String orderBy
    ) {
        Sort.Direction direction = orderBy.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));

        Page<ShortCourseTopic> topicPage;

        if ("archive".equalsIgnoreCase(display)) {
            topicPage = search == null || search.isBlank()
                    ? shortCourseTopicRepository.findByIsDeleted(true, pageable)
                    : shortCourseTopicRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndIsDeleted(
                        search, search, true, pageable);
        } else if ("all".equalsIgnoreCase(display)) {
            topicPage = search == null || search.isBlank()
                    ? shortCourseTopicRepository.findAll(pageable)
                    : shortCourseTopicRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                        search, search, pageable);
        } else {
            topicPage = search == null || search.isBlank()
                    ? shortCourseTopicRepository.findByIsDeleted(false, pageable)
                    : shortCourseTopicRepository.findByIsDeletedAndNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                        false, search, search, pageable);
        }

        List<ShortCourseTopicResponse> content = topicPage.getContent().stream()
                .map(shortCourseTopicMapper::fromShortCourseTopic)
                .collect(Collectors.toList());

        PagedResponse<ShortCourseTopicResponse> response = new PagedResponse<>(
                content,
                topicPage.getNumber(),
                topicPage.getSize(),
                topicPage.getTotalElements(),
                topicPage.getTotalPages(),
                topicPage.isLast()
        );

        return ResponseEntity.ok(response);
    }
    /**
     * Updates a ShortCourseTopic by ID.
     *
     * @param id Topic ID
     * @param request DTO containing updated data
     * @return ResponseEntity with updated ShortCourseTopicResponse
     */
    public ResponseEntity<ShortCourseTopicResponse> updateShortCourseTopic(String id, ShortCourseTopicRequest request) {
        ShortCourseTopic topic = shortCourseTopicRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException("ShortCourseTopic not found"));
        if(topic.isDeleted()) throw new EntityNotFoundException("Short Course Topic not found");
        
        ShortCourseTopic updated = shortCourseTopicMapper.toShortCourseTopicUpdate(topic, request);
        ShortCourseTopic saved = shortCourseTopicRepository.save(updated);
        return ResponseEntity.ok(shortCourseTopicMapper.fromShortCourseTopic(saved));
    }

    /**
     * Deletes a ShortCourseTopic by ID.
     *
     * @param id Topic ID
     * @return ResponseEntity with status message
     */
    public ResponseEntity<String> deleteShortCourseTopic(String id) {
        ShortCourseTopic topic = shortCourseTopicRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException("ShortCourseTopic not found"));
        if (topic.getShortCourseTopicLecturers().isEmpty()) {
            shortCourseTopicRepository.delete(topic);
        } else {
            shortCourseTopicRepository.save(topic);
            return ResponseEntity.ok("Short Course Topic archived successfully.");
        }
        return ResponseEntity.ok("ShortCourseTopic deleted successfully.");
    }

    public ResponseEntity<String> undoDeleteShortCourseTopic(String id) {
        ShortCourseTopic topic = shortCourseTopicRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException("ShortCourseTopic not found"));
        if (!topic.isDeleted()) {
            return ResponseEntity.status(404).body("Short Course Topic is not archived.");
        }
        topic.setDeleted(false);
        shortCourseTopicRepository.save(topic);
        return ResponseEntity.ok("Short Course Topic restored successfully.");
    }
}
