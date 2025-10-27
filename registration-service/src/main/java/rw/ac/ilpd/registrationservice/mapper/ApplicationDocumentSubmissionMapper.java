package rw.ac.ilpd.registrationservice.mapper;

import org.springframework.stereotype.Component;
import rw.ac.ilpd.registrationservice.model.nosql.document.ApplicationDocumentSubmission;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationDeferringCommentResponse;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationDocumentSubmissionRequest;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationDocumentSubmissionResponse;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for ApplicationDocumentSubmission with versioning support.
 * Maintains backward compatibility while adding version tracking.
 */
@Component
public class ApplicationDocumentSubmissionMapper {

    /**
     * Maps request to entity with initial version setup
     */
    public ApplicationDocumentSubmission toEntity(ApplicationDocumentSubmissionRequest request) {
        ApplicationDocumentSubmission submission = new ApplicationDocumentSubmission();
        submission.setApplicationId(request.getApplicationId());
        submission.setDocumentId(request.getDocumentId());
        submission.setRequiredDocNameId(request.getRequiredDocNameId());

        // Initialize versioning - addNewVersion will be called by service if needed
        return submission;
    }

    /**
     * Maps entity to response with versioning info
     */
    public ApplicationDocumentSubmissionResponse toResponse(ApplicationDocumentSubmission entity) {
        ApplicationDocumentSubmissionResponse response = new ApplicationDocumentSubmissionResponse();

        // Basic fields (maintain backward compatibility)
        response.setId(entity.getId());
        response.setApplicationId(entity.getApplicationId());
        response.setDocumentId(entity.getDocumentId()); // Current document ID
        response.setRequiredDocNameId(entity.getRequiredDocNameId());
        response.setDocVerificationStatus(entity.getVerificationStatus() != null ?
                entity.getVerificationStatus().name() : "PENDING");
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());

        // NEW: Versioning fields
        response.setVersions(entity.getVersions() != null ? entity.getVersions() : Collections.emptyList());
        response.setCurrentVersion(entity.getCurrentVersion());
        response.setHasBeenReplaced(entity.hasBeenReplaced());

        // Comments (will be populated by service if needed)
        response.setComments(Collections.emptyList());
        response.setCommentCount(0);

        return response;
    }

    /**
     * Maps entity to response with application context and comments
     */
    public ApplicationDocumentSubmissionResponse toResponseWithContext(
            ApplicationDocumentSubmission entity,
            String applicationId,
            List<ApplicationDeferringCommentResponse> comments) {

        ApplicationDocumentSubmissionResponse response = toResponse(entity);

        // Set context
        response.setApplicationId(applicationId);
        response.setComments(comments != null ? comments : Collections.emptyList());
        response.setCommentCount(comments != null ? comments.size() : 0);

        return response;
    }

    /**
     * Maps list of entities to responses
     */
    public List<ApplicationDocumentSubmissionResponse> toResponseList(List<ApplicationDocumentSubmission> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Maps list of entities to responses with context
     */
    public List<ApplicationDocumentSubmissionResponse> toResponseListWithContext(
            List<ApplicationDocumentSubmission> entities,
            String applicationId) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream()
                .map(entity -> toResponseWithContext(entity, applicationId, Collections.emptyList()))
                .collect(Collectors.toList());
    }

    /**
     * Updates existing entity with new request data
     * NOTE: For document replacement, use the addNewVersion() method on the entity
     */
    public ApplicationDocumentSubmission updateEntity(ApplicationDocumentSubmission existing,
                                                      ApplicationDocumentSubmissionRequest request) {
        // Only update basic fields, not document replacement
        existing.setRequiredDocNameId(request.getRequiredDocNameId());
        return existing;
    }
}