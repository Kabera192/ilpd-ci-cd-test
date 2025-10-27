package rw.ac.ilpd.registrationservice.client.fallback;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import rw.ac.ilpd.registrationservice.client.NotificationDocumentClient;
import rw.ac.ilpd.sharedlibrary.dto.document.*;

import java.util.List;
import java.util.Set;

/**
 * Fallback implementation for NotificationDocumentClient.
 * Returns 503 Service Unavailable when the notification service is down.
 */
@Component
public class NotificationDocumentFallback implements NotificationDocumentClient {

    private static final Logger logger = LoggerFactory.getLogger(NotificationDocumentFallback.class);

    @Override
    public ResponseEntity<DocumentResponse> uploadSingleObject(
            MultipartFile attachedFile,
            String bucketName,
            String objectPath) {
        logger.error("Notification service is unavailable - single document upload failed");
        return ResponseEntity.status(503).build();
    }

    @Override
    public ResponseEntity<List<DocumentResponse>> uploadMultipleObjects(
            List<MultipartFile> attachedFiles,
            String bucketName,
            String objectPath) {
        logger.error("Notification service is unavailable - multiple documents upload failed");
        return ResponseEntity.status(503).build();
    }

    @Override
    public ResponseEntity<DocumentResponse> findDocument(String id) {
        logger.error("Notification service is unavailable - document find failed for id: {}", id);
        return ResponseEntity.status(503).build();
    }

    @Override
    public ResponseEntity<List<DocumentResponse>> getAllDocuments() {
        logger.error("Notification service is unavailable - get all documents failed");
        return ResponseEntity.status(503).build();
    }

    @Override
    public ResponseEntity<List<DocumentResponse>> findListDocumentDetailByDocumentIds(Set<String> ids) {
        logger.error("Notification service is unavailable - find documents by ids failed for {} ids",
                ids != null ? ids.size() : 0);
        return ResponseEntity.status(503).build();
    }

    @Override
    public ResponseEntity<String> deleteDocument(String id) {
        logger.error("Notification service is unavailable - document delete failed for id: {}", id);
        return ResponseEntity.status(503).build();
    }

    @Override
    public ResponseEntity<Resource> downloadDocument(String id) {
        logger.error("Notification service is unavailable - document download failed for id: {}", id);
        return ResponseEntity.status(503).build();
    }
}