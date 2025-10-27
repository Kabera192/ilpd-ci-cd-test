package rw.ac.ilpd.registrationservice.client;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import rw.ac.ilpd.registrationservice.client.fallback.NotificationDocumentFallback;
import rw.ac.ilpd.registrationservice.config.NotificationDocumentClientConfig;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentResponse;
import rw.ac.ilpd.sharedlibrary.dto.document.ObjectStorageRequest;
import rw.ac.ilpd.sharedlibrary.dto.document.ObjectListStorageRequest;

import java.util.List;
import java.util.Set;

/**
 * Feign client for notification service document API.
 */
@FeignClient(name = "NOTIFICATION-SERVICE", contextId = "notificationDocumentClient", configuration =
        NotificationDocumentClientConfig.class, fallback = NotificationDocumentFallback.class, path = "/documents")
public interface NotificationDocumentClient {

    /**
     * Upload a single document
     * 
     * @param attachedFile the file to upload
     * @param bucketName the bucket name
     * @param objectPath the object path
     * @return the document response
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<DocumentResponse> uploadSingleObject(
            @RequestPart("attachedFile") MultipartFile attachedFile,
            @RequestPart("bucketName") String bucketName,
            @RequestPart("objectPath") String objectPath
    );

    /**
     * Upload multiple documents
     * 
     * @param attachedFiles the files to upload
     * @param bucketName the bucket name
     * @param objectPath the object path
     * @return the list of document responses
     */
    @PostMapping(value = "/upload-object-list", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<List<DocumentResponse>> uploadMultipleObjects(
            @RequestPart("attachedFiles") List<MultipartFile> attachedFiles,
            @RequestPart("bucketName") String bucketName,
            @RequestPart("objectPath") String objectPath
    );

    /**
     * Find a document by ID
     * 
     * @param id the document ID
     * @return the document response
     */
    @GetMapping("/{id}")
    ResponseEntity<DocumentResponse> findDocument(@PathVariable String id);

    /**
     * Get all documents
     * 
     * @return the list of document responses
     */
    @GetMapping
    ResponseEntity<List<DocumentResponse>> getAllDocuments();

    /**
     * Find documents by IDs
     * 
     * @param ids the set of document IDs
     * @return the list of document responses
     */
    @PostMapping("/find-list-by-ids")
    ResponseEntity<List<DocumentResponse>> findListDocumentDetailByDocumentIds(@RequestBody Set<String> ids);

    /**
     * Delete a document
     * 
     * @param id the document ID
     * @return a success message
     */
    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteDocument(@PathVariable String id);

    /**
     * Download a document
     * 
     * @param id the document ID
     * @return the document resource
     */
    @GetMapping("/{id}/download")
    ResponseEntity<Resource> downloadDocument(@PathVariable String id);
}