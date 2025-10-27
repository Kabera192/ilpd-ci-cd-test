package rw.ac.ilpd.registrationservice.client.fallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import rw.ac.ilpd.registrationservice.client.NotificationDocumentTypeClient;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentTypeRequest;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentTypeResponse;
import rw.ac.ilpd.sharedlibrary.dto.minioobject.BucketDetailResponse;
import rw.ac.ilpd.sharedlibrary.dto.minioobject.BucketStatisticsResponse;
import rw.ac.ilpd.sharedlibrary.dto.minioobject.ObjectDetailResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.util.ResponseDetailWrapper;

import java.util.Collections;
import java.util.List;

/**
 * Fallback implementation of the NotificationDocumentTypeClient interface.
 * This class provides default responses when the notification service is unavailable.
 */
@Component
public class NotificationDocumentTypeFallback implements NotificationDocumentTypeClient {

    private static final Logger logger = LoggerFactory.getLogger(NotificationDocumentTypeFallback.class);

    @Override
    public ResponseEntity<DocumentTypeResponse> createDocumentType(DocumentTypeRequest documentTypeRequest) {
        logger.error("Notification service is unavailable - document type creation failed");
        return ResponseEntity.status(503).build();
    }

    @Override
    public ResponseEntity<DocumentTypeResponse> findDocumentType(String id) {
        logger.error("Notification service is unavailable - document type find failed for id: {}", id);
        return ResponseEntity.status(503).build();
    }

    @Override
    public PagedResponse<DocumentTypeResponse> getPagedDocumentType(int page, int size, String sort, String search) {
        logger.error("Notification service is unavailable - get paged document types failed");
        return new PagedResponse<>(Collections.emptyList(), 0, 0, 0, 0, true);
    }

    @Override
    public ResponseEntity<DocumentTypeResponse> updateDocumentType(String id, DocumentTypeRequest documentTypeRequest) {
        logger.error("Notification service is unavailable - document type update failed for id: {}", id);
        return ResponseEntity.status(503).build();
    }

    @Override
    public ResponseEntity<String> deleteDocumentType(String id) {
        logger.error("Notification service is unavailable - document type delete failed for id: {}", id);
        return ResponseEntity.status(503).build();
    }

    @Override
    public ResponseDetailWrapper<DocumentTypeResponse, BucketDetailResponse> getBucketDetails(String id) {
        logger.error("Notification service is unavailable - get bucket details failed for id: {}", id);
        return new ResponseDetailWrapper<>(null, null);
    }

    @Override
    public ResponseDetailWrapper<DocumentTypeResponse, List<ObjectDetailResponse>> getAllObjectsWithInBucket(String id) {
        logger.error("Notification service is unavailable - get all objects in bucket failed for id: {}", id);
        return new ResponseDetailWrapper<>(null, Collections.emptyList());
    }

    @Override
    public BucketStatisticsResponse getBucketStatistics(String id) {
        logger.error("Notification service is unavailable - get bucket statistics failed for id: {}", id);
        return new BucketStatisticsResponse();
    }
}