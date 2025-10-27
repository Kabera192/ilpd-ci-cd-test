package rw.ac.ilpd.registrationservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rw.ac.ilpd.mis.shared.config.MisConfig;
import rw.ac.ilpd.mis.shared.config.privilege.auth.SuperPrivilege;
import rw.ac.ilpd.mis.shared.config.privilege.registration.ApplicationPriv;
import rw.ac.ilpd.mis.shared.security.Secured;
import rw.ac.ilpd.registrationservice.service.ApplicationService;
import rw.ac.ilpd.registrationservice.service.IDocumentService;
import rw.ac.ilpd.registrationservice.service.NotificationDocumentService;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationDocumentSubmissionRequest;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationDocumentSubmissionResponse;
import rw.ac.ilpd.sharedlibrary.dto.application.DocumentUploadAttachmentResponse;
import rw.ac.ilpd.sharedlibrary.dto.document.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * REST controller for document management operations.
 */
@RestController
@RequestMapping(value = MisConfig.REGISTRATION_PATH + "/documents", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@SecurityRequirement(name = "bearerAuth")
public class DocumentController {

    private final IDocumentService documentService;
    private final ApplicationService applicationService;
    Logger log = org.slf4j.LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    public DocumentController(IDocumentService documentService, ApplicationService applicationService) {
        this.documentService = documentService;
        this.applicationService = applicationService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload single document")
    @ApiResponse(responseCode = "201", description = "Document uploaded successfully")
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.ADD_DOCUMENT_TO_APPLICATION})
    public ResponseEntity<DocumentResponse> uploadSingleObject(
            @RequestParam("attachedFile") MultipartFile attachedFile,
            @RequestParam("bucketName") @NotBlank String bucketName,
            @RequestParam("objectPath") @NotBlank String objectPath,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {

        String uploadedBy = userDetails != null ? userDetails.getUsername() : "system";

        ObjectStorageRequest request = new ObjectStorageRequest();
        request.setAttachedFile(attachedFile);
        request.setBucketName(bucketName);
        request.setObjectPath(objectPath);

        DocumentResponse response = documentService.uploadSingleObject(request, uploadedBy);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(value = "/upload-object-list", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload multiple documents")
    @ApiResponse(responseCode = "201", description = "Documents uploaded successfully")
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.ADD_DOCUMENT_TO_APPLICATION})
    public ResponseEntity<List<DocumentResponse>> uploadMultipleObjects(
            @ModelAttribute @Valid ObjectListStorageRequest request,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {

        String uploadedBy = userDetails != null ? userDetails.getUsername() : "system";
        List<DocumentResponse> responses = documentService.uploadMultipleObjects(request, uploadedBy);
        return ResponseEntity.status(HttpStatus.CREATED).body(responses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get document by ID")
    @ApiResponse(responseCode = "200", description = "Document retrieved successfully")
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.VIEW_DOCUMENT})
    public ResponseEntity<DocumentResponse> findDocument(@PathVariable @NotBlank String id) {
        DocumentResponse response = documentService.findDocument(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Get all documents")
    @ApiResponse(responseCode = "200", description = "Documents retrieved successfully")
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.VIEW_ALL_DOCUMENTS})
    public ResponseEntity<List<DocumentResponse>> getAllDocuments() {
        List<DocumentResponse> responses = documentService.getAllDocuments();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/by-ids")
    @Operation(summary = "Get documents by IDs")
    @ApiResponse(responseCode = "200", description = "Documents retrieved successfully")
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.VIEW_DOCUMENT})
    public ResponseEntity<List<DocumentResponse>> findListDocumentDetailByDocumentIds(
            @RequestParam Set<String> ids) {
        List<DocumentResponse> responses = documentService.findListDocumentDetailByDocumentIds(ids);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/download/{id}")
    @Operation(summary = "Download document")
    @ApiResponse(responseCode = "200", description = "File downloaded successfully")
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.VIEW_DOCUMENT})
    public ResponseEntity<Resource> downloadDocument(@PathVariable @NotBlank String id) {
        try {
            if (documentService instanceof NotificationDocumentService adapter) {
                Resource resource = adapter.loadFileAsResource(id);

                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .header(HttpHeaders.CONTENT_DISPOSITION,
                                "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete document")
    @ApiResponse(responseCode = "200", description = "Document deleted successfully")
    @Secured({SuperPrivilege.SUPER_ADMIN})
    public ResponseEntity<String> deleteDocument(@PathVariable @NotBlank String id) {
        String message = documentService.deleteDocument(id);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/types")
    @Operation(summary = "Create document type")
    @ApiResponse(responseCode = "201", description = "Document type created successfully")
    @Secured({SuperPrivilege.SUPER_ADMIN})
    public ResponseEntity<DocumentTypeResponse> createDocumentType(
            @Valid @RequestBody DocumentTypeRequest request) {
        DocumentTypeResponse response = documentService.createDocumentType(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/types")
    @Operation(summary = "Get all document types")
    @ApiResponse(responseCode = "200", description = "Document types retrieved successfully")
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.VIEW_ALL_APPLICATIONS})
    public ResponseEntity<List<DocumentTypeResponse>> getAllDocumentTypes() {
        List<DocumentTypeResponse> responses = documentService.getAllDocumentTypes();
        return ResponseEntity.ok(responses);
    }

    @PostMapping(value = "/templates", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Create template")
    @ApiResponse(responseCode = "201", description = "Template created successfully")
    @Secured({SuperPrivilege.SUPER_ADMIN})
    public ResponseEntity<TemplateResponse> createTemplate(
            @RequestParam("name") @NotBlank String name,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "isActive", defaultValue = "true") Boolean isActive,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {

        String uploadedBy = userDetails != null ? userDetails.getUsername() : "system";

        TemplateRequest request = new TemplateRequest();
        request.setName(name);
        request.setIsActive(isActive);

        DocumentRequest docRequest = new DocumentRequest();
        docRequest.setFile(file);
        request.setDocument(docRequest);

        TemplateResponse response = documentService.createTemplate(request, uploadedBy);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/templates")
    @Operation(summary = "Get all templates")
    @ApiResponse(responseCode = "200", description = "Templates retrieved successfully")
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.VIEW_ALL_APPLICATIONS})
    public ResponseEntity<List<TemplateResponse>> getAllTemplates() {
        List<TemplateResponse> responses = documentService.getAllTemplates();
        return ResponseEntity.ok(responses);
    }


    // Add these new endpoints to your existing DocumentController

    /**
     * Upload single document with optional application attachment
     */
    @PostMapping(value = "/upload-with-attachment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload single document and optionally attach to application")
    @ApiResponse(responseCode = "201", description = "Document uploaded and attached successfully")
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.ADD_DOCUMENT_TO_APPLICATION})
    public ResponseEntity<DocumentUploadAttachmentResponse> uploadSingleObjectWithAttachment(
            @RequestParam("attachedFile") MultipartFile attachedFile,
            @RequestParam("bucketName") @NotBlank String bucketName,
            @RequestParam("objectPath") @NotBlank String objectPath,
            @RequestParam(value = "applicationId", required = false) String applicationId,
            @RequestParam(value = "requiredDocNameId", required = false) String requiredDocNameId,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {

        String uploadedBy = userDetails != null ? userDetails.getUsername() : "system";

        // 1. Upload document using your existing logic
        ObjectStorageRequest request = new ObjectStorageRequest();
        request.setAttachedFile(attachedFile);
        request.setBucketName(bucketName);
        request.setObjectPath(objectPath);

        DocumentResponse documentResponse = documentService.uploadSingleObject(request, uploadedBy);

        // 2. If applicationId is provided, attach to application
        ApplicationDocumentSubmissionResponse submissionResponse = null;
        if (applicationId != null && !applicationId.trim().isEmpty()) {
            submissionResponse = attachDocumentToApplication(
                    applicationId,
                    documentResponse.getId(),
                    requiredDocNameId != null ? requiredDocNameId : "others"
            );
        }

        // 3. Return combined response
        DocumentUploadAttachmentResponse response = new DocumentUploadAttachmentResponse();
        response.setDocument(documentResponse);
        response.setAttachment(submissionResponse);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Upload multiple documents with optional application attachment
     */
    @PostMapping(value = "/upload-object-list-with-attachment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload multiple documents and optionally attach to application")
    @ApiResponse(responseCode = "201", description = "Documents uploaded and attached successfully")
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.ADD_DOCUMENT_TO_APPLICATION})
    public ResponseEntity<List<DocumentUploadAttachmentResponse>> uploadMultipleObjectsWithAttachment(
            @ModelAttribute @Valid ObjectListStorageRequest request,
            @RequestParam(value = "applicationId", required = false) String applicationId,
            @RequestParam(value = "requiredDocNameIds", required = false) List<String> requiredDocNameIds,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {

        String uploadedBy = userDetails != null ? userDetails.getUsername() : "system";

        // 1. Upload documents using your existing logic
        List<DocumentResponse> documentResponses = documentService.uploadMultipleObjects(request, uploadedBy);

        // 2. If applicationId is provided, attach documents to application
        List<DocumentUploadAttachmentResponse> responses = new ArrayList<>();
        for (int i = 0; i < documentResponses.size(); i++) {
            DocumentResponse docResponse = documentResponses.get(i);
            ApplicationDocumentSubmissionResponse submissionResponse = null;

            if (applicationId != null && !applicationId.trim().isEmpty()) {
                String requiredDocNameId = (requiredDocNameIds != null && i < requiredDocNameIds.size())
                        ? requiredDocNameIds.get(i) : "others";

                submissionResponse = attachDocumentToApplication(
                        applicationId,
                        docResponse.getId(),
                        requiredDocNameId
                );
            }

            DocumentUploadAttachmentResponse response = new DocumentUploadAttachmentResponse();
            response.setDocument(docResponse);
            response.setAttachment(submissionResponse);
            responses.add(response);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(responses);
    }

    // Helper method to attach document to application
    // Update your attachDocumentToApplication method to handle string names:
    private ApplicationDocumentSubmissionResponse attachDocumentToApplication(
            String applicationId, String documentId, String requiredDocNameId) {
        try {
            log.info("Attempting to attach document {} to application {} with requiredDocNameId: {}",
                    documentId, applicationId, requiredDocNameId);

            // Convert string name to UUID (this creates a consistent UUID from string)
            String processedRequiredDocNameId = convertToValidUUID(requiredDocNameId);

            ApplicationDocumentSubmissionRequest submissionRequest = new ApplicationDocumentSubmissionRequest();
            submissionRequest.setDocumentId(documentId);
            submissionRequest.setRequiredDocNameId(processedRequiredDocNameId);
            submissionRequest.setApplicationId(applicationId); // Add this if required
            submissionRequest.setDocVerificationStatus("PENDING"); // Add this if required

            ApplicationDocumentSubmissionResponse response = applicationService.addDocumentToApplication(applicationId, submissionRequest);
            log.info("Successfully attached document {} to application {}", documentId, applicationId);
            return response;
        } catch (Exception e) {
            log.error("Failed to attach document {} to application {}: {}",
                    documentId, applicationId, e.getMessage(), e);
            return null;
        }
    }

    // Helper method to convert string names to consistent UUIDs
    private String convertToValidUUID(String name) {
        if (name == null || name.trim().isEmpty()) {
            name = "others";
        }

        // Check if it's already a valid UUID format
        if (isValidUUID(name)) {
            return name;
        }

        // Generate a consistent UUID based on the string name
        // This ensures the same string always produces the same UUID
        return UUID.nameUUIDFromBytes(name.toLowerCase().getBytes()).toString();
    }

    private boolean isValidUUID(String str) {
        if (str == null) return false;
        try {
            UUID.fromString(str);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}