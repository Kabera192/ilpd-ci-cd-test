package rw.ac.ilpd.registrationservice.model.nosql.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import rw.ac.ilpd.sharedlibrary.dto.application.DocumentVersion;
import rw.ac.ilpd.sharedlibrary.enums.DocumentVerificationStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * ApplicationDocumentSubmission entity with versioning support for document replacement.
 * Maintains backward compatibility while adding version tracking capabilities.
 */
@Document(collection = "reg_application_document_submission")
public class ApplicationDocumentSubmission {

    @Id
    private String id;

    private String applicationId;
    private String documentId; // Current document ID (backward compatibility)
    private String requiredDocNameId;
    private DocumentVerificationStatus verificationStatus;
    private LocalDateTime createdAt;

    // NEW: Versioning fields
    private List<DocumentVersion> versions;
    private int currentVersion;
    private LocalDateTime updatedAt;

    // Default constructor
    public ApplicationDocumentSubmission() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.verificationStatus = DocumentVerificationStatus.PENDING;
        this.versions = new ArrayList<>();
        this.currentVersion = 1;
    }

    // Constructor with parameters
    public ApplicationDocumentSubmission(String applicationId, String documentId, String requiredDocNameId) {
        this();
        this.applicationId = applicationId;
        this.documentId = documentId;
        this.requiredDocNameId = requiredDocNameId;

        // Add initial version to history
        this.versions.add(new DocumentVersion(
                documentId,
                1,
                null, // Will be set by service
                "Initial upload",
                null  // Will be set by service
        ));
    }

    // NEW: Method to add new version when document is replaced
    public void addNewVersion(String newDocumentId, String uploadedBy, String changeReason, String originalFileName) {
        this.currentVersion++;
        this.documentId = newDocumentId; // Update current document ID
        this.updatedAt = LocalDateTime.now();

        DocumentVersion newVersion = new DocumentVersion(
                newDocumentId,
                this.currentVersion,
                uploadedBy,
                changeReason,
                originalFileName
        );

        this.versions.add(newVersion);

        // Reset status to PENDING when document is replaced
        this.verificationStatus = DocumentVerificationStatus.PENDING;
    }

    // NEW: Helper method to check if document has been replaced
    public boolean hasBeenReplaced() {
        return currentVersion > 1;
    }

    // NEW: Get latest version info
    public DocumentVersion getLatestVersion() {
        if (versions == null || versions.isEmpty()) {
            return null;
        }
        return versions.get(versions.size() - 1);
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getRequiredDocNameId() {
        return requiredDocNameId;
    }

    public void setRequiredDocNameId(String requiredDocNameId) {
        this.requiredDocNameId = requiredDocNameId;
    }

    public DocumentVerificationStatus getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(DocumentVerificationStatus verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // NEW: Versioning getters and setters
    public List<DocumentVersion> getVersions() {
        return versions;
    }

    public void setVersions(List<DocumentVersion> versions) {
        this.versions = versions;
    }

    public int getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(int currentVersion) {
        this.currentVersion = currentVersion;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "ApplicationDocumentSubmission{" +
                "id='" + id + '\'' +
                ", applicationId='" + applicationId + '\'' +
                ", documentId='" + documentId + '\'' +
                ", requiredDocNameId='" + requiredDocNameId + '\'' +
                ", verificationStatus=" + verificationStatus +
                ", currentVersion=" + currentVersion +
                ", hasBeenReplaced=" + hasBeenReplaced() +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}