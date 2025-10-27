package rw.ac.ilpd.sharedlibrary.dto.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Embedded document representing a version of a document submission.
 * Used to track document replacement history.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentVersion {

    // Getters and Setters
    private String versionId;
    private String documentId; // Reference to the actual document
    private int versionNumber;
    private String uploadedBy; // User ID who uploaded this version
    private LocalDateTime uploadedAt;
    private String changeReason; // "Initial upload", "Resubmission after rejection", etc.
    private String originalFileName; // For tracking file names

    // Constructor with parameters
    public DocumentVersion(String documentId, int versionNumber, String uploadedBy, String changeReason, String originalFileName) {
        this();
        this.documentId = documentId;
        this.versionNumber = versionNumber;
        this.uploadedBy = uploadedBy;
        this.changeReason = changeReason;
        this.originalFileName = originalFileName;
    }

    @Override
    public String toString() {
        return "DocumentVersion{" +
                "versionId='" + versionId + '\'' +
                ", documentId='" + documentId + '\'' +
                ", versionNumber=" + versionNumber +
                ", uploadedBy='" + uploadedBy + '\'' +
                ", uploadedAt=" + uploadedAt +
                ", changeReason='" + changeReason + '\'' +
                ", originalFileName='" + originalFileName + '\'' +
                '}';
    }
}