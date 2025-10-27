package rw.ac.ilpd.registrationservice.mapper;

import org.springframework.stereotype.Component;
import rw.ac.ilpd.registrationservice.model.nosql.document.ApplicationDeferringComment;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationDeferringCommentRequest;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationDeferringCommentResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A mapper component for transforming data between ApplicationDeferringComment-related entities
 * and their associated request/response objects.
 * <p>
 * This class provides methods for converting between:
 * - ApplicationDeferringCommentRequest and ApplicationDeferringComment entities
 * - ApplicationDeferringComment entities and ApplicationDeferringCommentResponse objects
 * - Lists of ApplicationDeferringComment entities and their corresponding response objects
 * - Updating existing ApplicationDeferringComment entities using request data
 * <p>
 * It utilizes the structure and data encapsulated in the ApplicationDeferringComment entity,
 * ApplicationDeferringCommentRequest, and ApplicationDeferringCommentResponse for facilitating
 * object transformations and updates.
 */
@Component
public class ApplicationDeferringCommentMapper {

    /**
     * Converts an {@code ApplicationDeferringCommentRequest} object into an {@code ApplicationDeferringComment} entity.
     *
     * @param request the {@code ApplicationDeferringCommentRequest} containing the data to populate
     *                the {@code ApplicationDeferringComment} entity
     * @return a new {@code ApplicationDeferringComment} entity initialized with the data from the request
     */
    public ApplicationDeferringComment toEntity(ApplicationDeferringCommentRequest request) {
        return new ApplicationDeferringComment(null, // ID will be generated
                request.getContent(), request.getDeferredDocId(), LocalDateTime.now());
    }

    /**
     * Converts an instance of {@code ApplicationDeferringComment} entity into an instance of
     * {@code ApplicationDeferringCommentResponse} containing the corresponding response data.
     *
     * @param entity the {@code ApplicationDeferringComment} entity to be converted. Must not be {@code null}.
     * @return an instance of {@code ApplicationDeferringCommentResponse} containing the transformed data.
     * The returned object includes the entity's ID, comment content, deferred document ID,
     * and creation timestamp.
     */
    public ApplicationDeferringCommentResponse toResponse(ApplicationDeferringComment entity) {
        return ApplicationDeferringCommentResponse.builder().id(entity.getId()).content(entity.getComment())
                .deferredDocId(entity.getSubmittedDocumentId()).createdAt(entity.getCreatedAt()).build();
    }

    /**
     * Converts a list of ApplicationDeferringComment entities into a list of corresponding
     * ApplicationDeferringCommentResponse objects.
     *
     * @param entities the list of ApplicationDeferringComment entities to be transformed
     * @return a list of ApplicationDeferringCommentResponse objects corresponding to the input entities
     */
    public List<ApplicationDeferringCommentResponse> toResponseList(List<ApplicationDeferringComment> entities) {
        return entities.stream().map(this::toResponse).collect(Collectors.toList());
    }

    /**
     * Updates an existing ApplicationDeferringComment entity using the data provided in the
     * ApplicationDeferringCommentRequest object. Updates include modifying the comment content
     * and the related deferred document ID.
     *
     * @param existing the existing ApplicationDeferringComment entity to be updated
     * @param request  the ApplicationDeferringCommentRequest containing the new comment content
     *                 and related deferred document ID
     * @return the updated ApplicationDeferringComment entity
     */
    public ApplicationDeferringComment updateEntity(ApplicationDeferringComment existing,
                                                    ApplicationDeferringCommentRequest request) {
        existing.setComment(request.getContent());
        existing.setSubmittedDocumentId(request.getDeferredDocId());
        return existing;
    }
}
