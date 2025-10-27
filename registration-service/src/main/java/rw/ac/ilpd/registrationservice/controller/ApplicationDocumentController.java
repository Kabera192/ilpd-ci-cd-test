package rw.ac.ilpd.registrationservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import rw.ac.ilpd.registrationservice.client.NotificationDocumentTypeClient;
import rw.ac.ilpd.registrationservice.service.ApplicationDocumentIntegrationService;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationDocumentDetailResponse;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationDocumentSubmissionResponse;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationSponsorResponse;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentTypeRequest;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentTypeResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;

import java.util.List;

/**
 * Controller for application-specific document operations
 * Provides endpoints for uploading, retrieving, and managing documents attached to applications
 */
@RestController
@RequestMapping(value = MisConfig.REGISTRATION_PATH + ApplicationPriv.APPLICATIONS_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@SecurityRequirement(name = "bearerAuth")
public class ApplicationDocumentController {

    private static final Logger log = LoggerFactory.getLogger(ApplicationDocumentController.class);
    
    private final ApplicationDocumentIntegrationService integrationService;
    private final NotificationDocumentTypeClient documentTypeClient;

    @Autowired
    public ApplicationDocumentController(
            ApplicationDocumentIntegrationService integrationService,
            NotificationDocumentTypeClient documentTypeClient) {
        this.integrationService = integrationService;
        this.documentTypeClient = documentTypeClient;
    }
    
    /**
     * Helper method to get or create a default document type based on the provided type name.
     * If a document type with the given name exists, it returns its ID.
     * Otherwise, it creates a new document type and returns its ID.
     *
     * @param documentTypeName the name of the document type
     * @return the ID of the existing or newly created document type
     */
    private String getOrCreateDefaultDocumentType(String documentTypeName) {
        log.debug("Getting or creating document type: {}", documentTypeName);
        
        // Search for existing document type by name
        PagedResponse<DocumentTypeResponse> response = documentTypeClient.getPagedDocumentType(0, 10, "name", documentTypeName);
        
        if (response != null && response.getContent() != null && !response.getContent().isEmpty()) {
            // Find exact match
            for (DocumentTypeResponse type : response.getContent()) {
                if (type.getName().equalsIgnoreCase(documentTypeName)) {
                    log.debug("Found existing document type: {}", type.getId());
                    return type.getId();
                }
            }
        }
        
        // Create new document type if not found
        log.debug("Creating new document type: {}", documentTypeName);
        DocumentTypeRequest request = new DocumentTypeRequest();
        request.setName(documentTypeName);
        request.setPath(documentTypeName.toLowerCase().replace(" ", "_"));
        
        ResponseEntity<DocumentTypeResponse> createResponse = documentTypeClient.createDocumentType(request);
        
        if (createResponse.getStatusCode().is2xxSuccessful() && createResponse.getBody() != null) {
            log.debug("Created new document type: {}", createResponse.getBody().getId());
            return createResponse.getBody().getId();
        } else {
            log.error("Failed to create document type: {}", documentTypeName);
            throw new RuntimeException("Failed to create document type: " + documentTypeName);
        }
    }

    @PostMapping(value = "/{applicationId}/documents/upload-and-attach",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload and attach document",
            description = "Upload a document and attach it to application in one step")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Document uploaded and attached successfully"),
            @ApiResponse(responseCode = "404", description = "Application not found"),
            @ApiResponse(responseCode = "400", description = "Invalid file or data")
    })
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.ADD_DOCUMENT_TO_APPLICATION})
    public ResponseEntity<ApplicationDocumentSubmissionResponse> uploadAndAttachDocument(
            @PathVariable @NotBlank String applicationId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("requiredDocNameId") @NotBlank String requiredDocNameId,
            @RequestParam("documentTypeId") @NotBlank String documentTypeId,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {

        String uploadedBy = userDetails != null ? userDetails.getUsername() : "system";

        ApplicationDocumentSubmissionResponse response = integrationService.uploadAndAttachDocument(
                applicationId, file, requiredDocNameId, documentTypeId, uploadedBy);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(value = "/{applicationId}/documents/bulk-upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Bulk upload and attach documents",
            description = "Upload multiple documents and attach them to application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Documents uploaded and attached successfully"),
            @ApiResponse(responseCode = "404", description = "Application not found"),
            @ApiResponse(responseCode = "400", description = "Invalid files or data")
    })
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.ADD_DOCUMENT_TO_APPLICATION})
    public ResponseEntity<List<ApplicationDocumentSubmissionResponse>> bulkUploadAndAttachDocuments(
            @PathVariable @NotBlank String applicationId,
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("requiredDocNameIds") List<String> requiredDocNameIds,
            @RequestParam("documentTypeId") @NotBlank String documentTypeId,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {

        String uploadedBy = userDetails != null ? userDetails.getUsername() : "system";

        List<ApplicationDocumentSubmissionResponse> responses = integrationService
                .uploadAndAttachMultipleDocuments(applicationId, files, requiredDocNameIds,
                        documentTypeId, uploadedBy);

        return ResponseEntity.status(HttpStatus.CREATED).body(responses);
    }

    @GetMapping("/{applicationId}/documents/detailed")
    @Operation(summary = "Get detailed application documents",
            description = "Get application documents with rich metadata and download URLs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documents retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Application not found")
    })
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.VIEW_APPLICATION_DOCUMENTS})
    public ResponseEntity<List<ApplicationDocumentDetailResponse>> getApplicationDocumentsDetailed(
            @PathVariable @NotBlank String applicationId) {

        List<ApplicationDocumentDetailResponse> responses = integrationService
                .getApplicationDocumentsWithDetails(applicationId);

        return ResponseEntity.ok(responses);
    }
    /**
     * Upload recommendation letter for specific sponsor relationship
     */
    @PostMapping(value = "/{applicationId}/sponsors/{sponsorId}/upload-recommendation",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload recommendation letter for sponsor",
            description = "Upload recommendation letter and link to existing sponsor relationship")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Recommendation letter uploaded and linked successfully"),
            @ApiResponse(responseCode = "404", description = "Application or sponsor not found"),
            @ApiResponse(responseCode = "400", description = "Invalid file")
    })
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.ADD_DOCUMENT_TO_APPLICATION})
    public ResponseEntity<ApplicationSponsorResponse> uploadRecommendationForSponsor(
            @PathVariable @NotBlank String applicationId,
            @PathVariable @NotBlank String sponsorId,
            @RequestParam("file") MultipartFile file,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {

        String uploadedBy = userDetails != null ? userDetails.getUsername() : "system";

        // Upload recommendation letter using existing service
        ApplicationSponsorResponse response = integrationService.uploadRecommendationForSponsor(
                applicationId, sponsorId, file, uploadedBy);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    /**
     * Simple endpoint to upload a document to an applicant
     */
    @PostMapping(value = "/{applicationId}/upload-document",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload document to applicant",
            description = "Upload a document and attach it to an applicant with minimal parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Document uploaded and attached successfully"),
            @ApiResponse(responseCode = "404", description = "Application not found"),
            @ApiResponse(responseCode = "400", description = "Invalid file")
    })
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.ADD_DOCUMENT_TO_APPLICATION})
    public ResponseEntity<ApplicationDocumentSubmissionResponse> uploadDocumentToApplicant(
            @PathVariable @NotBlank String applicationId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "documentType", required = false, defaultValue = "general") String documentType,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {

        String uploadedBy = userDetails != null ? userDetails.getUsername() : "system";

        // Get or create a default document type if not specified
        String documentTypeId = getOrCreateDefaultDocumentType(documentType);
        
        // Use a default required document name
        String requiredDocNameId = "others";
        
        // Use the existing service to upload and attach the document
        ApplicationDocumentSubmissionResponse response = integrationService.uploadAndAttachDocument(
                applicationId, file, requiredDocNameId, documentTypeId, uploadedBy);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
