package rw.ac.ilpd.registrationservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.registrationservice.client.fallback.NotificationDocumentTypeFallback;
import rw.ac.ilpd.registrationservice.config.NotificationDocumentTypeClientConfig;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentTypeRequest;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentTypeResponse;
import rw.ac.ilpd.sharedlibrary.dto.minioobject.BucketDetailResponse;
import rw.ac.ilpd.sharedlibrary.dto.minioobject.BucketStatisticsResponse;
import rw.ac.ilpd.sharedlibrary.dto.minioobject.ObjectDetailResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.util.ResponseDetailWrapper;

import java.util.List;

/**
 * Feign client interface for interacting with the notification service's document type API.
 * Provides methods to create, find, update, and delete document types, as well as
 * get bucket details, objects, and statistics.
 * 
 * This client is configured with a fallback mechanism to handle service unavailability,
 * using the {@code NotificationDocumentTypeFallback} implementation. The configuration for this client is
 * defined in {@code FeignConfig}.
 */
@FeignClient(
        name = "NOTIFICATION-SERVICE",
        contextId = "notificationDocumentTypeClient",
        configuration = NotificationDocumentTypeClientConfig.class,
        fallback = NotificationDocumentTypeFallback.class,
        path = "/document-types"
)
public interface NotificationDocumentTypeClient {

    /**
     * Creates a new document type in the notification service.
     *
     * @param documentTypeRequest the document type request containing the type details
     * @return a response entity containing the created document type response
     */
    @PostMapping
    ResponseEntity<DocumentTypeResponse> createDocumentType(@RequestBody DocumentTypeRequest documentTypeRequest);

    /**
     * Finds a document type by its ID in the notification service.
     *
     * @param id the ID of the document type to find
     * @return a response entity containing the document type response if found
     */
    @GetMapping("/{id}")
    ResponseEntity<DocumentTypeResponse> findDocumentType(@PathVariable String id);

    /**
     * Gets a paged list of document types from the notification service.
     *
     * @param page the page number (0-based)
     * @param size the page size
     * @param sort the sort field
     * @param search the search keyword
     * @return a paged response containing document type responses
     */
    @GetMapping
    PagedResponse<DocumentTypeResponse> getPagedDocumentType(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "name") String sort,
            @RequestParam(value = "search", defaultValue = "") String search
    );

    /**
     * Updates a document type in the notification service.
     *
     * @param id the ID of the document type to update
     * @param documentTypeRequest the document type request containing the updated details
     * @return a response entity containing the updated document type response
     */
    @PutMapping("/{id}")
    ResponseEntity<DocumentTypeResponse> updateDocumentType(
            @PathVariable String id,
            @RequestBody DocumentTypeRequest documentTypeRequest
    );

    /**
     * Deletes a document type by its ID from the notification service.
     *
     * @param id the ID of the document type to delete
     * @return a response entity containing a success message
     */
    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteDocumentType(@PathVariable String id);

    /**
     * Gets bucket details for a document type from the notification service.
     *
     * @param id the ID of the document type
     * @return a response detail wrapper containing the document type response and bucket details
     */
    @GetMapping("/{id}/bucket-properties")
    ResponseDetailWrapper<DocumentTypeResponse, BucketDetailResponse> getBucketDetails(@PathVariable String id);

    /**
     * Gets all objects in a bucket for a document type from the notification service.
     *
     * @param id the ID of the document type
     * @return a response detail wrapper containing the document type response and a list of object details
     */
    @GetMapping("/{id}/bucket-objects")
    ResponseDetailWrapper<DocumentTypeResponse, List<ObjectDetailResponse>> getAllObjectsWithInBucket(@PathVariable String id);

    /**
     * Gets bucket statistics for a document type from the notification service.
     *
     * @param id the ID of the document type
     * @return bucket statistics response
     */
    @GetMapping("/{id}/bucket-statistics")
    BucketStatisticsResponse getBucketStatistics(@PathVariable String id);
}