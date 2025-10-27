package rw.ac.ilpd.sharedlibrary.dto.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Enhanced ApplicationDocumentSubmissionResponse with simple versioning support
 * Backward compatible - all existing fields preserved
 */
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class ApplicationDocumentSubmissionResponse {

    // All getters and setters
    private String id;
    private String applicationId;
    private String documentId; // KEEP for backward compatibility
    private String requiredDocNameId;
    private String docVerificationStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    private List<ApplicationDeferringCommentResponse> comments;
    private int commentCount;

    private List<DocumentVersion> versions;
    private int currentVersion;
    private boolean hasBeenReplaced;

    public ApplicationDocumentSubmissionResponse() {
        this.currentVersion = 1;
        this.hasBeenReplaced = false;
    }

}