package rw.ac.ilpd.sharedlibrary.dto.document;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;

public interface DocumentApi {

    /**
     * Upload a single document object.
     *
     * Consumes: multipart/form-data
     * @param request contains the file and metadata for the document
     * @return ResponseEntity with details of the uploaded document
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<DocumentResponse> uploadSingleObject(@ModelAttribute @Valid ObjectStorageRequest request);
    /**
     * Upload multiple document objects at once.
     *
     * Consumes: multipart/form-data
     * @param request contains the list of files and metadata for the documents
     * @return ResponseEntity with details of all uploaded documents
     */
    @PostMapping(path = "/upload-object-list", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<List<DocumentResponse>> uploadMultipleObject(@Valid @ModelAttribute ObjectListStorageRequest request);
    @PostMapping("/{id}/download")
    ResponseEntity<Resource> downloadDocument(@PathVariable  String id);

    @PutMapping(path = "/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<DocumentResponse> updateSingleObject(@PathVariable String id,@ModelAttribute @Valid ObjectStorageRequest request);

    /**
     * Find a document by its unique identifier.
     *
     * @param id the document ID (must not be blank)
     * @return ResponseEntity with details of the found document
     */
    @GetMapping("/{id}")
    ResponseEntity<DocumentResponse> findDocument(
            @PathVariable @NotBlank(message = "Specify a document you're in need of") String id);

    /**
     * Retrieve all documents in the storage.
     *
     * @return ResponseEntity containing the list of all stored documents
     */
    @GetMapping()
    ResponseEntity<List<DocumentResponse>> getAll();

    /**
     * Find multiple documents by their IDs.
     *
     * @param ids the set of document IDs to retrieve
     * @return ResponseEntity with details of the found documents
     */
    @PostMapping("/find-list-by-ids")
    ResponseEntity<List<DocumentResponse>> findListDocumentDetailByDocumentIds(@RequestBody Set<String> ids);

    /**
     * Delete a document by its unique identifier.
     *
     * @param id the document ID
     * @return ResponseEntity with a confirmation message of deletion
     */
    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteDocument(@PathVariable String id);

}
