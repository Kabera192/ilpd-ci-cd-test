package rw.ac.ilpd.notificationservice.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.notificationservice.exception.MaxUploadSizeExceedException;
import rw.ac.ilpd.notificationservice.service.DocumentService;
import rw.ac.ilpd.notificationservice.util.FileDownloadHelperObj;
import rw.ac.ilpd.sharedlibrary.dto.document.*;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/documents")
@Tag(name = "Document controller apis",description = "CRUD for document")
@Slf4j
@RequiredArgsConstructor
public class DocumentController implements DocumentApi{
        private final DocumentService documentService;

        @Override
        public ResponseEntity<DocumentResponse> uploadSingleObject(@ModelAttribute @Valid ObjectStorageRequest request) {
            log.info("Uploading single object: {}", request);
            try {
                DocumentResponse documentResponse = documentService.uploadObject(request);
                log.info("Successfully uploaded object: {}", documentResponse);
                return ResponseEntity.ok(documentResponse);
            } catch (Exception e) {
                log.error("Error uploading single object", e);
                if (e instanceof MaxUploadSizeExceedException) {
                    throw new MaxUploadSizeExceedException("Maximum upload size exceeded 10MB");
                }
                return ResponseEntity.badRequest().build();
            }
        }

        @Override
        public ResponseEntity<List<DocumentResponse>> uploadMultipleObject(@Valid @ModelAttribute ObjectListStorageRequest request) {
            log.info("Uploading multiple objects: {}", request);
            try {
                List<DocumentResponse> documentResponse = documentService.uploadMultipleObject(request);
                log.info("Successfully uploaded {} objects", documentResponse.size());
                return ResponseEntity.ok(documentResponse);
            } catch (Exception e) {
                log.error("Error uploading multiple objects", e);
                return ResponseEntity.badRequest().build();
            }
        }

    @Override
    public ResponseEntity<DocumentResponse> updateSingleObject(String id, ObjectStorageRequest request) {
            log.info("Updating single object having : {}", id);
            DocumentResponse documentResponse = documentService.updateSingleObject(id,request);
        return ResponseEntity.ok(documentResponse);
    }

    @Override
        public ResponseEntity<DocumentResponse> findDocument(@PathVariable @NotBlank(message = "Specify a document you're in need of") String id) {
            log.info("Finding document with id: {}", id);
            return documentService.findDocument(id)
                    .map(dr -> {
                        log.info("Document found: {}", dr);
                        return new ResponseEntity<>(dr, HttpStatus.OK);
                    })
                    .orElseGet(() -> {
                        log.warn("Document with id {} not found", id);
                        return ResponseEntity.notFound().build();
                    });
        }

        @Override
        public ResponseEntity<List<DocumentResponse>> getAll() {
            log.info("Fetching all documents");
            List<DocumentResponse> allDocuments = documentService.getAll();
            log.info("Total documents fetched: {}", allDocuments.size());
            return ResponseEntity.ok(allDocuments);
        }

    @Override
    public ResponseEntity<Resource> downloadDocument(@PathVariable String id) {
        FileDownloadHelperObj fileInfo = documentService.downloadDocumentFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileInfo.fileName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(fileInfo.resource());
    }

    @Override
        public ResponseEntity<List<DocumentResponse>> findListDocumentDetailByDocumentIds(@RequestBody Set<String> ids) {
            log.info("Finding documents by IDs: {}", ids);
            List<DocumentResponse> documents = documentService.findDocumentWithInsByIds(ids);
            log.info("Found {} documents", documents.size());
            return ResponseEntity.ok(documents);
        }

        @Override
        public ResponseEntity<String> deleteDocument(@PathVariable String id) {
            log.info("Deleting document with id: {}", id);
            String result = documentService.deleteDocumentById(id);
            log.info("Delete result: {}", result);
            return ResponseEntity.ok(result);
        }
    }

