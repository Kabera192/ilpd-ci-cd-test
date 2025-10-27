package rw.ac.ilpd.registrationservice.service;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.ilpd.registrationservice.client.NotificationDocumentClient;
import rw.ac.ilpd.registrationservice.client.NotificationDocumentTypeClient;
import rw.ac.ilpd.registrationservice.mapper.DocumentMapper;
import rw.ac.ilpd.registrationservice.model.nosql.document.Template;
import rw.ac.ilpd.registrationservice.repository.TemplateRepository;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentResponse;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentTypeRequest;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentTypeResponse;
import rw.ac.ilpd.sharedlibrary.dto.document.ObjectListStorageRequest;
import rw.ac.ilpd.sharedlibrary.dto.document.ObjectStorageRequest;
import rw.ac.ilpd.sharedlibrary.dto.document.TemplateRequest;
import rw.ac.ilpd.sharedlibrary.dto.document.TemplateResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Service that delegates document operations to the notification service.
 */
@Service
@Transactional
public class NotificationDocumentService implements IDocumentService {
    private static final Logger log = LoggerFactory.getLogger(NotificationDocumentService.class);

    private final NotificationDocumentClient documentClient;
    private final NotificationDocumentTypeClient documentTypeClient;
    private final DocumentMapper documentMapper;
    private final TemplateRepository templateRepository;
    private final DocumentUploadService documentUploadService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    public NotificationDocumentService(NotificationDocumentClient documentClient,
                                       NotificationDocumentTypeClient documentTypeClient,
                                       DocumentMapper documentMapper,
                                       TemplateRepository templateRepository,
                                       DocumentUploadService documentUploadService) {
        this.documentClient = documentClient;
        this.documentTypeClient = documentTypeClient;
        this.documentMapper = documentMapper;
        this.templateRepository = templateRepository;
        this.documentUploadService = documentUploadService;
    }

    @Override
    public DocumentResponse uploadSingleObject(ObjectStorageRequest request, String uploadedBy) {
        log.info("Delegating single document upload to notification service using RestTemplate");

        try {
            // Get the current authorization token
            String authHeader = httpServletRequest.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new RuntimeException("No authorization token found");
            }

            DocumentResponse response = documentUploadService.uploadDocument(
                    request.getAttachedFile(),
                    request.getBucketName(),
                    request.getObjectPath(),
                    authHeader
            );

            if (response != null) {
                log.info("Successfully uploaded document: {}", response.getId());
                return response;
            } else {
                log.error("Failed to upload document - null response");
                throw new RuntimeException("Failed to upload document through notification service");
            }
        } catch (IOException e) {
            log.error("Failed to upload document", e);
            throw new RuntimeException("Failed to upload document: " + e.getMessage(), e);
        }
    }

    @Override
    public List<DocumentResponse> uploadMultipleObjects(ObjectListStorageRequest request, String uploadedBy) {
        log.info("Delegating multiple document upload to notification service using RestTemplate");

        try {
            // Get the current authorization token
            String authHeader = httpServletRequest.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new RuntimeException("No authorization token found");
            }

            List<DocumentResponse> responses = documentUploadService.uploadMultipleDocuments(
                    request.getAttachedFiles(),
                    request.getBucketName(),
                    request.getObjectPath(),
                    authHeader
            );

            if (responses != null && !responses.isEmpty()) {
                log.info("Successfully uploaded {} documents", responses.size());
                return responses;
            } else {
                log.error("Failed to upload documents - null or empty response");
                throw new RuntimeException("Failed to upload documents through notification service");
            }
        } catch (IOException e) {
            log.error("Failed to upload documents", e);
            throw new RuntimeException("Failed to upload documents: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentResponse findDocument(String id) {
        log.info("Finding document: {}", id);
        ResponseEntity<DocumentResponse> response = documentClient.findDocument(id);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        } else {
            log.error("Document not found: {}", id);
            throw new RuntimeException("Document not found: " + id);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentResponse> getAllDocuments() {
        log.info("Getting all documents");
        ResponseEntity<List<DocumentResponse>> response = documentClient.getAllDocuments();

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        } else {
            log.error("Failed to retrieve documents");
            return Collections.emptyList();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentResponse> findListDocumentDetailByDocumentIds(Set<String> ids) {
        log.info("Finding documents by IDs: {}", ids);
        ResponseEntity<List<DocumentResponse>> response = documentClient.findListDocumentDetailByDocumentIds(ids);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        } else {
            log.error("Failed to find documents by IDs");
            return Collections.emptyList();
        }
    }

    @Override
    public String deleteDocument(String id) {
        log.info("Deleting document: {}", id);
        ResponseEntity<String> response = documentClient.deleteDocument(id);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody() != null ? response.getBody() : "Document deleted successfully";
        } else {
            log.error("Failed to delete document: {}", id);
            throw new RuntimeException("Failed to delete document: " + id);
        }
    }

    @Override
    public DocumentTypeResponse createDocumentType(DocumentTypeRequest request) {
        log.info("Creating document type");
        ResponseEntity<DocumentTypeResponse> response = documentTypeClient.createDocumentType(request);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        } else {
            log.error("Failed to create document type");
            throw new RuntimeException("Failed to create document type");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentTypeResponse> getAllDocumentTypes() {
        log.info("Getting all document types");
        PagedResponse<DocumentTypeResponse> response = documentTypeClient.getPagedDocumentType(0, 1000, "name", "");

        if (response != null && response.getContent() != null) {
            return response.getContent();
        } else {
            log.error("Failed to retrieve document types");
            return Collections.emptyList();
        }
    }

    @Override
    public TemplateResponse createTemplate(TemplateRequest request, String uploadedBy) {
        log.info("Creating template: {}", request.getName());

        // Upload document to notification service
        ObjectStorageRequest uploadRequest = new ObjectStorageRequest();
        uploadRequest.setBucketName("templates");
        uploadRequest.setObjectPath("templates");
        uploadRequest.setAttachedFile(request.getDocument().getFile());

        DocumentResponse document = uploadSingleObject(uploadRequest, uploadedBy);

        // Save template locally
        Template template = new Template();
        template.setName(request.getName());
        template.setDocumentId(document.getId());
        template.setIsActive(request.getIsActive());

        Template saved = templateRepository.save(template);
        return documentMapper.toResponse(saved, document);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TemplateResponse> getAllTemplates() {
        log.info("Getting all templates");
        List<Template> templates = templateRepository.findAll();

        return templates.stream().map(template -> {
            DocumentResponse doc = findDocument(template.getDocumentId());
            return documentMapper.toResponse(template, doc);
        }).toList();
    }

    /**
     * Load a document file as a resource for downloading.
     */
    public org.springframework.core.io.Resource loadFileAsResource(String id) {
        log.info("Downloading document: {}", id);

        try {
            ResponseEntity<org.springframework.core.io.Resource> response = documentClient.downloadDocument(id);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            } else {
                log.error("Failed to download document: {}", id);
                throw new RuntimeException("Failed to download document: " + id);
            }
        } catch (Exception e) {
            log.error("Error downloading document: {}", e.getMessage(), e);
            throw new RuntimeException("Error downloading document: " + e.getMessage(), e);
        }
    }
}