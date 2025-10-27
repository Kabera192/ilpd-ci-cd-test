package rw.ac.ilpd.registrationservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rw.ac.ilpd.mis.shared.config.MisConfig;
import rw.ac.ilpd.mis.shared.config.privilege.auth.SuperPrivilege;
import rw.ac.ilpd.mis.shared.config.privilege.registration.ApplicationPriv;
import rw.ac.ilpd.mis.shared.security.Secured;
import rw.ac.ilpd.mis.shared.util.helpers.MisResponse;
import rw.ac.ilpd.registrationservice.projection.ApplicationDocumentStatistics;
import rw.ac.ilpd.registrationservice.projection.ApplicationStatusCount;
import rw.ac.ilpd.registrationservice.projection.DailyApplicationCount;
import rw.ac.ilpd.registrationservice.projection.IntakeApplicationCount;
import rw.ac.ilpd.registrationservice.service.ApplicationService;
import rw.ac.ilpd.registrationservice.service.SecurityOwnershipService;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationAnalyticsResponse;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationDeferringCommentRequest;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationDocumentSubmissionRequest;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationDocumentSubmissionResponse;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationRequest;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationResponse;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationSearchRequest;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationStatusUpdateRequest;
import rw.ac.ilpd.sharedlibrary.dto.application.BatchApplicationRequest;
import rw.ac.ilpd.sharedlibrary.dto.application.BatchResponse;
import rw.ac.ilpd.sharedlibrary.dto.application.DocumentVerificationStatusUpdateRequest;
import rw.ac.ilpd.sharedlibrary.dto.application.DocumentVersion;
import rw.ac.ilpd.sharedlibrary.dto.application.PagedResponse;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The ApplicationController class provides REST endpoints for managing and retrieving applications.
 * This controller facilitates operations such as retrieving, creating, searching, and listing applications,
 * as well as accessing or filtering applications based on various criteria.
 * <p>
 * Fields:
 * - applicationService: Service for application-related operations.
 * - securityOwnershipService: Service for managing security and ownership-related functionality.
 * <p>
 * Constructor:
 * - ApplicationController(ApplicationService applicationService, SecurityOwnershipService securityOwnershipService):
 * Initializes the controller with the required services.
 * <p>
 * Methods:
 * - getApplicationById(String id, boolean includeDocuments, boolean includeAcademicBackground, boolean
 * includeSponsors):
 * Retrieves an application by its unique identifier, with optional nested details.
 * <p>
 * - getCompleteApplicationDetails(String id):
 * Retrieves an application by its unique identifier, including all possible details (documents, academic background,
 * sponsors).
 * <p>
 * - getAllApplications(int page, int size, boolean includeDocuments, boolean includeAcademicBackground, boolean
 * includeSponsors):
 * Retrieves all applications with optional pagination and nested details.
 * <p>
 * - getPagedApplications(int page, int size, boolean includeDocuments, boolean includeAcademicBackground, boolean
 * includeSponsors):
 * Retrieves a paginated list of applications with optional details.
 * <p>
 * - getApplicationsByUser(String userId, boolean includeDocuments, boolean includeAcademicBackground, boolean
 * includeSponsors):
 * Retrieves applications for a specific user.
 * <p>
 * - getApplicationsByStatus(String status, boolean includeDocuments, boolean includeAcademicBackground, boolean
 * includeSponsors):
 * Retrieves applications with a specific status.
 * <p>
 * - searchApplications(ApplicationSearchRequest searchRequest):
 * Searches for applications based on provided criteria.
 * <p>
 * - getApplicationsByDocument(String documentId, boolean includeDocuments, boolean includeAcademicBackground,
 * boolean includeSponsors):
 * Finds applications containing a specific document submission.
 * <p>
 * - getApplicationsWithoutDocuments(boolean includeAcademicBackground, boolean includeSponsors):
 * Retrieves applications that have no document submissions.
 * <p>
 * - createApplication(ApplicationRequest applicationRequest):
 * Creates a new application.
 * <p>
 * - createApplicationsBatch(BatchApplicationRequest batchRequest):
 * Creates multiple applications in a single request.
 */
@RestController
@RequestMapping(value = MisConfig.REGISTRATION_PATH + ApplicationPriv.APPLICATIONS_PATH, produces =
        MediaType.APPLICATION_JSON_VALUE)
@Validated
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Applications", description = "Application management endpoints")
@SecurityRequirement(name = "Bearer Authentication")
public class ApplicationController {

    /**
     * A service component responsible for handling application-specific business logic.
     * This variable provides the primary interface for interacting with application services.
     * It is marked as final, indicating that it is immutable once initialized.
     */
    private final ApplicationService applicationService;
    /**
     * Represents a service responsible for handling operations related to
     * the ownership of securities. This service may include functionalities
     * such as retrieving ownership details, managing ownership records,
     * and performing operations on relevant security ownership data.
     */
    private final SecurityOwnershipService securityOwnershipService;

    /**
     * Initializes an instance of {@code ApplicationController} with the specified services.
     *
     * @param applicationService       the service responsible for application-related operations
     * @param securityOwnershipService the service responsible for handling security ownership operations
     */
    @Autowired
    public ApplicationController(ApplicationService applicationService,
                                 SecurityOwnershipService securityOwnershipService) {
        this.applicationService = applicationService;
        this.securityOwnershipService = securityOwnershipService;
    }

    /**
     * Retrieves an application by its unique identifier with optional nested details.
     *
     * @param id                        the unique identifier of the application to retrieve
     * @param includeDocuments          a boolean flag indicating whether to include documents in the response
     * @param includeAcademicBackground a boolean flag indicating whether to include academic background details in
     *                                  the response
     * @param includeSponsors           a boolean flag indicating whether to include sponsor details in the response
     * @return a ResponseEntity containing the application details if found, or an appropriate error response
     */
    @GetMapping(value = ApplicationPriv.VIEW_APPLICATION_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get application by ID", description =
            "Retrieves an application by its unique identifier " + "with optional nested details")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Application retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Application not found"), @ApiResponse(responseCode =
            "403", description = "Access denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.VIEW_APPLICATION})
    public ResponseEntity<MisResponse<ApplicationResponse>> getApplicationById(@PathVariable String id,
                                                                               @RequestParam(defaultValue = "false") boolean includeDocuments, @RequestParam(defaultValue = "false") boolean includeAcademicBackground, @RequestParam(defaultValue = "false") boolean includeSponsors) {
        ApplicationResponse application = applicationService.getApplicationById(id, includeDocuments,
                includeAcademicBackground, includeSponsors);
        if (application == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MisResponse<>(false, "Application not found for id: " + id));
        }
        return ResponseEntity.ok(new MisResponse<>(true, "Application retrieved successfully", application));
    }

    /**
     * Retrieves the complete details of an application by its unique identifier.
     * This includes all associated information such as documents, academic background, and sponsors.
     *
     * @param id the unique identifier of the application
     * @return a ResponseEntity containing the complete application details wrapped in an ApplicationResponse object,
     * or an appropriate error response if the application is not found or access is denied
     */
    @GetMapping(value = ApplicationPriv.VIEW_APPLICATION_PATH + "/detailed", produces =
            MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get complete application details", description =
            "Retrieves an application by its unique " + "identifier with all possible details included (documents, " + "academic background, sponsors)")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description =
            "Complete application details retrieved " + "successfully"), @ApiResponse(responseCode = "404",
            description = "Application not found"), @ApiResponse(responseCode = "403", description =
            "Access denied " + "-" + " insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.VIEW_APPLICATION})
    public ResponseEntity<MisResponse<ApplicationResponse>> getCompleteApplicationDetails(@PathVariable String id) {
        ApplicationResponse application = applicationService.getApplicationById(id, true, true, true);
        if (application == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MisResponse<>(false, "Application not found for id: " + id));
        }
        return ResponseEntity.ok(
                new MisResponse<>(true, "Complete application details retrieved successfully", application));
    }

    /**
     * Retrieves all applications with optional pagination and nested details,
     * such as documents, academic background, and sponsors.
     *
     * @param page                      the page number for pagination, must be zero or greater. Defaults to 0 if not
     *                                  specified.
     * @param size                      the number of records per page, must be between 1 and 100. Defaults to 10 if
     *                                  not specified.
     * @param includeDocuments          a flag indicating whether to include the associated documents. Defaults to
     *                                  false.
     * @param includeAcademicBackground a flag indicating whether to include the academic background information.
     *                                  Defaults to false.
     * @param includeSponsors           a flag indicating whether to include sponsors details. Defaults to false.
     * @return a ResponseEntity containing the list of {@code ApplicationResponse} objects retrieved.
     */
    @GetMapping(value = ApplicationPriv.VIEW_ALL_APPLICATIONS_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all applications", description =
            "Retrieves all applications with optional pagination " + "and nested details")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Applications retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No applications found"), @ApiResponse(responseCode =
            "403", description = "Access denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.VIEW_ALL_APPLICATIONS})
    public ResponseEntity<MisResponse<List<ApplicationResponse>>> getAllApplications(@RequestParam(defaultValue = "0") @Min(0) int page, @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size, @RequestParam(defaultValue = "false") boolean includeDocuments, @RequestParam(defaultValue = "false") boolean includeAcademicBackground, @RequestParam(defaultValue = "false") boolean includeSponsors) {
        List<ApplicationResponse> applications = applicationService.getAllApplications(page, size, includeDocuments,
                includeAcademicBackground, includeSponsors);
        if (applications == null || applications.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MisResponse<>(false, "No applications found"));
        }
        return ResponseEntity.ok(new MisResponse<>(true, "Applications retrieved successfully", applications));
    }
    
    /**
     * Retrieves all applications with complete details, including documents, academic background, sponsors, and comments.
     * This method is similar to getCompleteApplicationDetails but returns a list of all applications instead of a single one.
     *
     * @param page the page number for pagination, must be zero or greater. Defaults to 0 if not specified.
     * @param size the number of records per page, must be between 1 and 100. Defaults to 10 if not specified.
     * @return a ResponseEntity containing the list of {@code ApplicationResponse} objects with complete details.
     */
    @GetMapping(value = ApplicationPriv.VIEW_ALL_APPLICATIONS_PATH + "/detailed", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all applications with complete details", description =
            "Retrieves all applications with complete details including documents, academic background, sponsors, and comments")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Applications with complete details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No applications found"), @ApiResponse(responseCode =
            "403", description = "Access denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.VIEW_ALL_APPLICATIONS})
    public ResponseEntity<MisResponse<List<ApplicationResponse>>> getAllApplicationsDetailed(@RequestParam(defaultValue = "0") @Min(0) int page, @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {
        List<ApplicationResponse> applications = applicationService.getAllApplications(page, size, true, true, true, true);
        if (applications == null || applications.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MisResponse<>(false, "No applications found"));
        }
        return ResponseEntity.ok(new MisResponse<>(true, "Applications with complete details retrieved successfully", applications));
    }

    /**
     * Retrieves a paginated list of applications with optional details.
     *
     * @param page                      the page number to retrieve, must be greater than or equal to 0. Default
     *                                  value is 0.
     * @param size                      the number of records per page, must be between 1 and 100. Default value is 10.
     * @param includeDocuments          whether to include document details in the response. Default value is false.
     * @param includeAcademicBackground whether to include academic background details in the response. Default value
     *                                  is false.
     * @param includeSponsors           whether to include sponsor details in the response. Default value is false.
     * @return a {@link ResponseEntity} containing a {@link PagedResponse} of {@link ApplicationResponse},
     * representing the paginated list of applications.
     */
    @GetMapping(value = ApplicationPriv.VIEW_PAGED_APPLICATIONS_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get paginated applications", description = "Retrieves a paginated list of applications")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Applications retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No applications found"), @ApiResponse(responseCode =
            "403", description = "Access denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.VIEW_PAGED_APPLICATIONS})
    public ResponseEntity<MisResponse<PagedResponse<ApplicationResponse>>> getPagedApplications(@RequestParam(defaultValue = "0") @Min(0) int page, @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size, @RequestParam(defaultValue = "false") boolean includeDocuments, @RequestParam(defaultValue = "false") boolean includeAcademicBackground, @RequestParam(defaultValue = "false") boolean includeSponsors) {
        PagedResponse<ApplicationResponse> applications = applicationService.getPagedApplications(page, size,
                includeDocuments, includeAcademicBackground, includeSponsors);
        if (applications == null || applications.getContent().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MisResponse<>(false, "No applications found"));
        }
        return ResponseEntity.ok(new MisResponse<>(true, "Applications retrieved successfully", applications));
    }

    /**
     * Retrieves all applications associated with a specific user.
     *
     * @param userId                    the identifier of the user whose applications are to be retrieved
     * @param includeDocuments          a boolean indicating whether to include application-related documents
     * @param includeAcademicBackground a boolean indicating whether to include the user's academic background
     *                                  information
     * @param includeSponsors           a boolean indicating whether to include sponsor information for the applications
     * @return a ResponseEntity containing a list of ApplicationResponse objects representing the user's applications
     */
    @GetMapping(ApplicationPriv.VIEW_APPLICATIONS_BY_USER_PATH)
    @Operation(summary = "Get applications by user", description = "Retrieves all applications for a specific user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "User applications retrieved " +
            "successfully"), @ApiResponse(responseCode = "403", description = "Access denied - insufficient " +
            "permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.VIEW_APPLICATIONS_BY_USER})
    public ResponseEntity<List<ApplicationResponse>> getApplicationsByUser(@PathVariable String userId,
                                                                           @RequestParam(defaultValue = "false") boolean includeDocuments, @RequestParam(defaultValue = "false") boolean includeAcademicBackground, @RequestParam(defaultValue = "false") boolean includeSponsors) {
        List<ApplicationResponse> applications = applicationService.getApplicationsByUser(userId, includeDocuments,
                includeAcademicBackground, includeSponsors);
        return ResponseEntity.ok(applications);
    }

    /**
     * Retrieves all applications with a specific status.
     *
     * @param status                    the status of the applications to filter by, provided as a path variable
     * @param includeDocuments          a flag to include associated documents in the response, provided as a request
     *                                  parameter
     * @param includeAcademicBackground a flag to include academic background details in the response, provided as a
     *                                  request parameter
     * @param includeSponsors           a flag to include sponsor information in the response, provided as a request
     *                                  parameter
     * @return a ResponseEntity containing a list of ApplicationResponse objects that match the specified status
     */
    @GetMapping(ApplicationPriv.VIEW_APPLICATIONS_BY_STATUS_PATH)
    @Operation(summary = "Get applications by status", description = "Retrieves all applications with a specific " +
            "status")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Applications retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.VIEW_APPLICATIONS_BY_STATUS})
    public ResponseEntity<List<ApplicationResponse>> getApplicationsByStatus(@PathVariable String status,
                                                                             @RequestParam(defaultValue = "false") boolean includeDocuments, @RequestParam(defaultValue = "false") boolean includeAcademicBackground, @RequestParam(defaultValue = "false") boolean includeSponsors) {
        List<ApplicationResponse> applications = applicationService.getApplicationsByStatus(status, includeDocuments,
                includeAcademicBackground, includeSponsors);
        return ResponseEntity.ok(applications);
    }

    /**
     * Searches for applications based on the specified criteria.
     * This method processes the search request and returns a list of matching applications.
     *
     * @param searchRequest the search criteria encapsulated in an ApplicationSearchRequest object
     * @return a ResponseEntity containing a list of ApplicationResponse objects that match the search criteria
     */
    @PostMapping(ApplicationPriv.SEARCH_APPLICATIONS_PATH)
    @Operation(summary = "Search applications", description = "Searches for applications based on specified criteria")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Applications found successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.SEARCH_APPLICATIONS})
    public ResponseEntity<List<ApplicationResponse>> searchApplications(@Valid @RequestBody ApplicationSearchRequest searchRequest) {
        List<ApplicationResponse> applications = applicationService.searchApplications(searchRequest);
        return ResponseEntity.ok(applications);
    }

    /**
     * Retrieves applications that include a specific document submission based on the provided document ID.
     *
     * @param documentId                the unique identifier of the document to search for in applications
     * @param includeDocuments          a flag indicating whether to include detailed document information in the
     *                                  response
     * @param includeAcademicBackground a flag indicating whether to include academic background details in the response
     * @param includeSponsors           a flag indicating whether to include sponsor details in the response
     * @return a {@link ResponseEntity} containing a list of {@link ApplicationResponse} objects that match the
     * search criteria
     */
    @GetMapping(ApplicationPriv.VIEW_APPLICATIONS_BY_DOCUMENT_PATH)
    @Operation(summary = "Get applications by document", description = "Finds applications containing a specific " +
            "document submission")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Applications found successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.VIEW_APPLICATIONS_BY_DOCUMENT})
    public ResponseEntity<List<ApplicationResponse>> getApplicationsByDocument(@PathVariable String documentId,
                                                                               @RequestParam(defaultValue = "false") boolean includeDocuments, @RequestParam(defaultValue = "false") boolean includeAcademicBackground, @RequestParam(defaultValue = "false") boolean includeSponsors) {
        List<ApplicationResponse> applications = applicationService.getApplicationsByDocument(documentId,
                includeDocuments, includeAcademicBackground, includeSponsors);
        return ResponseEntity.ok(applications);
    }

    /**
     * Retrieves a list of applications that have no document submissions.
     *
     * @param includeAcademicBackground a boolean flag indicating whether to include academic background information
     *                                  in the response.
     * @param includeSponsors           a boolean flag indicating whether to include sponsor details in the response.
     * @return a ResponseEntity containing a list of ApplicationResponse objects representing applications without
     * any document submissions.
     */
    @GetMapping(ApplicationPriv.VIEW_APPLICATIONS_WITHOUT_DOCUMENTS_PATH)
    @Operation(summary = "Get applications without documents", description =
            "Retrieves applications that have no " + "document submissions")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Applications retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.VIEW_APPLICATIONS_WITHOUT_DOCUMENTS})
    public ResponseEntity<List<ApplicationResponse>> getApplicationsWithoutDocuments(@RequestParam(defaultValue =
            "false") boolean includeAcademicBackground, @RequestParam(defaultValue = "false") boolean includeSponsors) {
        List<ApplicationResponse> applications = applicationService.getApplicationsWithoutDocuments(
                includeAcademicBackground, includeSponsors);
        return ResponseEntity.ok(applications);
    }

    /**
     * Handles the creation of a new application.
     *
     * @param applicationRequest the request payload containing the necessary application details
     * @return a ResponseEntity containing the created ApplicationResponse with HTTP status 201 (Created)
     */
    @PostMapping(value = ApplicationPriv.CREATE_APPLICATION_PATH, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create application", description = "Creates a new application")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Application created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"), @ApiResponse(responseCode = "403"
            , description = "Access denied - insufficient permissions")})
    //@Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.CREATE_APPLICATION})
    public ResponseEntity<MisResponse<ApplicationResponse>> createApplication(@Valid @RequestBody ApplicationRequest applicationRequest) {
        ApplicationResponse application = applicationService.createApplication(applicationRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MisResponse<>(true, "Application created successfully", application));
    }

    /**
     * Creates multiple applications in a single batch request. This method processes the provided
     * batch request and returns a response containing the details of the applications created.
     * The user must have the necessary privileges to perform this operation.
     *
     * @param batchRequest the batch request containing the details of the applications to be created;
     *                     must be valid and include all required fields.
     * @return a ResponseEntity containing a BatchResponse with details of the created applications.
     * The response has a status of 201 if the operation succeeds, 400 for invalid input data,
     * or 403 if the user lacks proper access.
     */
    @PostMapping(value = ApplicationPriv.CREATE_APPLICATIONS_BATCH_PATH, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create multiple applications", description =
            "Creates multiple applications in a single " + "batch request")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Applications created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"), @ApiResponse(responseCode = "403"
            , description = "Access denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.CREATE_APPLICATIONS_BATCH})
    public ResponseEntity<MisResponse<BatchResponse<ApplicationResponse>>> createApplicationsBatch(@Valid @RequestBody BatchApplicationRequest batchRequest) {
        BatchResponse<ApplicationResponse> response = applicationService.createApplicationsBatch(batchRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MisResponse<>(true, "Applications created successfully", response));
    }

    /**
     * Updates an existing application by its identifier.
     *
     * @param id                 the unique identifier of the application to update
     * @param applicationRequest the request body containing updated application details
     * @param currentUserId      the ID of the current user making the request, retrieved from the "X-User-ID" header
     * @return a ResponseEntity containing the updated application details
     * @throws AccessDeniedException if the current user does not have permission to update the application
     */
    @PutMapping(value = ApplicationPriv.UPDATE_APPLICATION_PATH, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update application", description = "Updates an existing application")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Application updated successfully"),
            @ApiResponse(responseCode = "404", description = "Application not found"), @ApiResponse(responseCode =
            "400", description = "Invalid input data"), @ApiResponse(responseCode = "403", description = "Access " +
            "denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.UPDATE_APPLICATION})
    public ResponseEntity<MisResponse<ApplicationResponse>> updateApplication(@PathVariable String id,
                                                                              @Valid @RequestBody ApplicationRequest applicationRequest, @RequestHeader(value = "X-User-ID", required = false) String currentUserId) {

        // Check if the current user is the owner of the application
        if (currentUserId != null && !securityOwnershipService.isApplicationOwner(id, currentUserId)) {
            throw new AccessDeniedException("You do not have permission to update this application");
        }

        ApplicationResponse application = applicationService.updateApplication(id, applicationRequest);
        if (application == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MisResponse<>(false, "Application not found for id: " + id));
        }
        return ResponseEntity.ok(new MisResponse<>(true, "Application updated successfully", application));
    }

    /**
     * Updates the status of an application based on the given application ID and status update request.
     *
     * @param id                  the ID of the application to update
     * @param statusUpdateRequest the request containing the new status to be applied to the application
     * @return a ResponseEntity containing the updated application details
     * if the operation is successful
     */
    @PatchMapping(value = ApplicationPriv.UPDATE_APPLICATION_STATUS_PATH, produces = MediaType.APPLICATION_JSON_VALUE
            , consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update application status", description = "Updates the status of an application")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Application status updated successfully"), @ApiResponse(responseCode = "404", description
            = "Application not found"), @ApiResponse(responseCode = "400", description = "Invalid status"),
            @ApiResponse(responseCode = "403", description = "Access denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.UPDATE_APPLICATION_STATUS})
    public ResponseEntity<MisResponse<ApplicationResponse>> updateApplicationStatus(@PathVariable String id,
                                                                                    @Valid @RequestBody ApplicationStatusUpdateRequest statusUpdateRequest) {
        ApplicationResponse application = applicationService.updateApplicationStatus(id, statusUpdateRequest);
        if (application == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MisResponse<>(false, "Application not found for id: " + id));
        }
        return ResponseEntity.ok(new MisResponse<>(true, "Application status updated successfully", application));
    }

    /**
     * Deletes an application and all its related data.
     * <p>
     * This method removes the application identified by the provided ID.
     * It validates the current user's permission and ownership before deletion.
     * If the user lacks sufficient permissions or the application is not found,
     * appropriate exceptions are thrown.
     *
     * @param id            the unique identifier of the application to be deleted
     * @param currentUserId the identifier of the current user making the request,
     *                      retrieved from the "X-User-ID" header (optional)
     * @return a ResponseEntity with a status of 204 (No Content) indicating successful deletion,
     * or a relevant error status if the application could not be deleted
     */
    @DeleteMapping(ApplicationPriv.DELETE_APPLICATION_PATH)
    @Operation(summary = "Delete application", description = "Deletes an application and all its related data")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Application deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Application not found"), @ApiResponse(responseCode =
            "403", description = "Access denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.DELETE_APPLICATION})
    public ResponseEntity<Void> deleteApplication(@PathVariable String id, @RequestHeader(value = "X-User-ID",
            required = false) String currentUserId) {

        // Check if the current user is the owner of the application
        if (currentUserId != null && !securityOwnershipService.isApplicationOwner(id, currentUserId)) {
            throw new AccessDeniedException("You do not have permission to delete this application");
        }

        applicationService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }


    /**
     * Adds a document submission to a specific application.
     *
     * @param applicationId   the unique identifier of the application to which the document should be added
     * @param documentRequest the request payload containing the details of the document to be added
     * @return a ResponseEntity containing the created ApplicationDocumentSubmissionResponse object
     */
    @PostMapping(ApplicationPriv.ADD_DOCUMENT_TO_APPLICATION_PATH)
    @Operation(summary = "Add document to application", description = "Adds a document submission to a specific " +
            "application")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Document added successfully"),
            @ApiResponse(responseCode = "404", description = "Application not found"), @ApiResponse(responseCode =
            "400", description = "Invalid document data"), @ApiResponse(responseCode = "403", description =
            "Access " + "denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.ADD_DOCUMENT_TO_APPLICATION})
    public ResponseEntity<ApplicationDocumentSubmissionResponse> addDocumentToApplication(@PathVariable String applicationId, @Valid @RequestBody ApplicationDocumentSubmissionRequest documentRequest) {
        ApplicationDocumentSubmissionResponse document = applicationService.addDocumentToApplication(applicationId,
                documentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(document);
    }

    /**
     * Retrieves all document submissions for a specific application.
     *
     * @param applicationId the unique identifier of the application whose documents are to be retrieved
     * @return a ResponseEntity containing a list of document submission responses
     * or an appropriate error response
     */
    @GetMapping(ApplicationPriv.VIEW_APPLICATION_DOCUMENTS_PATH)
    @Operation(summary = "Get application documents", description = "Retrieves all document submissions for a " +
            "specific application")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Documents retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Application not found"), @ApiResponse(responseCode =
            "403", description = "Access denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.VIEW_APPLICATION_DOCUMENTS})
    public ResponseEntity<List<ApplicationDocumentSubmissionResponse>> getApplicationDocuments(@PathVariable String applicationId) {
        List<ApplicationDocumentSubmissionResponse> documents = applicationService.getApplicationDocuments(
                applicationId);
        return ResponseEntity.ok(documents);
    }

    /**
     * Removes a document submission from an application.
     *
     * @param applicationId the unique identifier of the application from which the document is to be removed
     * @param documentId    the unique identifier of the document to be removed from the application
     * @param currentUserId optionally provided, the identifier of the user making the request used for ownership
     *                      validation
     * @return a {@code ResponseEntity<Void>} indicating the result of the operation. Returns a 204 status code upon
     * success.
     * If the application or document is not found, a 404 status code is returned. If the user lacks sufficient
     * permissions, a 403 status code is returned.
     */
    @DeleteMapping(ApplicationPriv.REMOVE_DOCUMENT_FROM_APPLICATION_PATH)
    @Operation(summary = "Remove document from application", description =
            "Removes a document submission from an " + "application")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Document removed successfully"),
            @ApiResponse(responseCode = "404", description = "Application or document not found"),
            @ApiResponse(responseCode = "403", description = "Access denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.REMOVE_DOCUMENT_FROM_APPLICATION})
    public ResponseEntity<Void> removeDocumentFromApplication(@PathVariable String applicationId,
                                                              @PathVariable String documentId, @RequestHeader(value =
                    "X-User-ID", required = false) String currentUserId) {

        // Check if the current user is the owner of the application
        if (currentUserId != null) {
            boolean isApplicationOwner = securityOwnershipService.isApplicationOwner(applicationId, currentUserId);
            boolean isDocumentOwner = securityOwnershipService.isDocumentOwner(documentId, currentUserId);

            if (!isApplicationOwner || !isDocumentOwner) {
                throw new AccessDeniedException(
                        "You do not have permission to remove this document from the application");
            }
        }

        applicationService.removeDocumentFromApplication(applicationId, documentId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves all document submissions across all applications.
     *
     * @param page the page number to retrieve, defaults to 0 if not specified, must be greater than or equal to 0
     * @param size the number of documents per page to retrieve, defaults to 10 if not specified, must be between 1
     *             and 100
     * @return a ResponseEntity containing a list of ApplicationDocumentSubmissionResponse objects representing the
     * retrieved documents
     */
    @GetMapping(ApplicationPriv.VIEW_ALL_DOCUMENTS_PATH)
    @Operation(summary = "Get all document submissions", description =
            "Retrieves all document submissions across " + "all" + " applications")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Documents retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.VIEW_ALL_DOCUMENTS})
    public ResponseEntity<List<ApplicationDocumentSubmissionResponse>> getAllDocuments(@RequestParam(defaultValue =
            "0") @Min(0) int page, @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {
        List<ApplicationDocumentSubmissionResponse> documents = applicationService.getAllDocuments(page, size);
        return ResponseEntity.ok(documents);
    }

    /**
     * Retrieves a specific document submission by its ID.
     *
     * @param documentId the unique identifier of the document to be retrieved
     * @return ResponseEntity containing the document submission response if found,
     * or an appropriate error response if not found or access is denied
     */
    @GetMapping(ApplicationPriv.VIEW_DOCUMENT_PATH)
    @Operation(summary = "Get document by ID", description = "Retrieves a specific document submission by its ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Document retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Document not found"), @ApiResponse(responseCode = "403"
            , description = "Access denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.VIEW_DOCUMENT})
    public ResponseEntity<ApplicationDocumentSubmissionResponse> getDocumentById(@PathVariable String documentId) {
        ApplicationDocumentSubmissionResponse document = applicationService.getDocumentById(documentId);
        return ResponseEntity.ok(document);
    }

    /**
     * Updates the verification status of a document submission. This method is accessible
     * to users with the required permissions. It locates the specified document by its ID
     * and updates its status based on the provided request details.
     *
     * @param documentId          the ID of the document to update
     * @param statusUpdateRequest the request payload containing the status update details
     * @return a ResponseEntity containing the updated document submission response
     */
    @PatchMapping(ApplicationPriv.UPDATE_DOCUMENT_STATUS_PATH)
    @Operation(summary = "Update document verification status", description =
            "Updates the verification status of a " + "document submission")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Document status updated successfully"),
            @ApiResponse(responseCode = "404", description = "Document not found"), @ApiResponse(responseCode = "400"
            , description = "Invalid status"), @ApiResponse(responseCode = "403", description = "Access denied - " +
            "insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.UPDATE_DOCUMENT_STATUS})
    public ResponseEntity<ApplicationDocumentSubmissionResponse> updateDocumentStatus(@PathVariable String documentId
            , @Valid @RequestBody DocumentVerificationStatusUpdateRequest statusUpdateRequest) {
        ApplicationDocumentSubmissionResponse document = applicationService.updateDocumentStatus(documentId,
                statusUpdateRequest);
        return ResponseEntity.ok(document);
    }

    /**
     * Retrieves document submissions filtered by their specific verification status.
     *
     * @param status the verification status based on which documents are to be fetched
     * @return a ResponseEntity containing a list of ApplicationDocumentSubmissionResponse objects
     * representing the documents with the specified status
     */
    @GetMapping(ApplicationPriv.VIEW_DOCUMENTS_BY_STATUS_PATH)
    @Operation(summary = "Get documents by status", description =
            "Retrieves document submissions with a specific " + "verification status")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Documents retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.VIEW_DOCUMENTS_BY_STATUS})
    public ResponseEntity<List<ApplicationDocumentSubmissionResponse>> getDocumentsByStatus(@PathVariable String status) {
        List<ApplicationDocumentSubmissionResponse> documents = applicationService.getDocumentsByStatus(status);
        return ResponseEntity.ok(documents);
    }

    /**
     * Rejects a document submission with the provided comments. This method updates
     * the document's verification status to "REJECTED" and associates the specified
     * comments with the document record.
     *
     * @param documentId     the unique identifier of the document to be rejected
     * @param commentRequest the details of the rejection comments, encapsulated as
     *                       an {@code ApplicationDeferringCommentRequest} object
     * @return a {@code ResponseEntity} containing an
     * {@code ApplicationDocumentSubmissionResponse} which includes
     * the details of the updated document
     */
    @PatchMapping(ApplicationPriv.UPDATE_DOCUMENT_STATUS_PATH + "/reject")
    @Operation(summary = "Reject document with comments", description = "Rejects a document submission with comments")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Document rejected successfully"),
            @ApiResponse(responseCode = "404", description = "Document not found"), @ApiResponse(responseCode = "400"
            , description = "Invalid input"), @ApiResponse(responseCode = "403", description = "Access denied - " +
            "insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.UPDATE_DOCUMENT_STATUS})
    public ResponseEntity<ApplicationDocumentSubmissionResponse> rejectDocumentWithComments(@PathVariable String documentId, @Valid @RequestBody ApplicationDeferringCommentRequest commentRequest) {
        // Create a status update request with REJECTED status and the provided comment
        DocumentVerificationStatusUpdateRequest statusUpdateRequest = DocumentVerificationStatusUpdateRequest.builder()
                .verificationStatus("REJECTED").comment(commentRequest.getContent()).build();

        // Use the existing updateDocumentStatus method
        ApplicationDocumentSubmissionResponse document = applicationService.updateDocumentStatus(documentId,
                statusUpdateRequest);
        return ResponseEntity.ok(document);
    }

    /**
     * Retrieves comprehensive application analytics overview.
     *
     * @return ResponseEntity containing ApplicationAnalyticsResponse with analytics data.
     * Returns a status code of 200 if the analytics were retrieved successfully.
     * Returns a status code of 403 if access is denied due to insufficient permissions.
     */
    @GetMapping(ApplicationPriv.VIEW_APPLICATION_ANALYTICS_PATH)
    @Operation(summary = "Get application analytics overview", description =
            "Retrieves comprehensive application " + "analytics")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Analytics retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.VIEW_APPLICATION_ANALYTICS})
    public ResponseEntity<ApplicationAnalyticsResponse> getApplicationAnalytics() {
        ApplicationAnalyticsResponse analytics = applicationService.getApplicationAnalytics();
        return ResponseEntity.ok(analytics);
    }

    /**
     * Retrieves the number of applications per status.
     * This endpoint provides a summary of application counts categorized by their respective statuses.
     *
     * @return a ResponseEntity containing a list of ApplicationStatusCount objects, where each object provides
     * a status and the corresponding count of applications. If successful, the response will have a
     * status code of 200 (OK). If access is denied due to insufficient permissions, the response will
     * have a status code of 403 (Forbidden).
     */
    @GetMapping(ApplicationPriv.VIEW_APPLICATION_STATUS_COUNTS_PATH)
    @Operation(summary = "Get application status counts", description =
            "Retrieves the number of applications per " + "status")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Status counts retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.VIEW_APPLICATION_STATUS_COUNTS})
    public ResponseEntity<List<ApplicationStatusCount>> getApplicationStatusCounts() {
        List<ApplicationStatusCount> statusCounts = applicationService.getApplicationStatusCounts();
        return ResponseEntity.ok(statusCounts);
    }

    /**
     * Retrieves the number of applications per intake based on the analytics data.
     * This operation is secured and accessible only to authorized roles.
     *
     * @return ResponseEntity containing a list of IntakeApplicationCount objects, where each object represents
     * the intake and its corresponding application count. Returns an appropriate HTTP response code
     * based on the outcome of the request.
     */
    @GetMapping(ApplicationPriv.VIEW_APPLICATION_INTAKE_COUNTS_PATH)
    @Operation(summary = "Get application intake counts", description =
            "Retrieves the number of applications per " + "intake")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Intake counts retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.VIEW_APPLICATION_INTAKE_COUNTS})
    public ResponseEntity<List<IntakeApplicationCount>> getApplicationIntakeCounts() {
        List<IntakeApplicationCount> intakeCounts = applicationService.getApplicationIntakeCounts();
        return ResponseEntity.ok(intakeCounts);
    }

    /**
     * Retrieves daily application counts for a specified date range.
     *
     * @param startDate The start date of the range for which daily application counts are to be retrieved.
     * @param endDate   The end date of the range for which daily application counts are to be retrieved.
     * @return A ResponseEntity containing a list of DailyApplicationCount objects representing the daily counts
     * within the specified date range.
     */
    @GetMapping(ApplicationPriv.VIEW_DAILY_APPLICATION_COUNTS_PATH)
    @Operation(summary = "Get daily application counts", description = "Retrieves daily application counts for a " +
            "specified date range")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Daily counts retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.VIEW_DAILY_APPLICATION_COUNTS})
    public ResponseEntity<List<DailyApplicationCount>> getDailyApplicationCounts(@RequestParam @Parameter(description = "Start date") LocalDateTime startDate, @RequestParam @Parameter(description = "End date") LocalDateTime endDate) {
        List<DailyApplicationCount> dailyCounts = applicationService.getDailyApplicationCounts(startDate, endDate);
        return ResponseEntity.ok(dailyCounts);
    }

    /**
     * Retrieves average document counts by application status.
     *
     * @return a ResponseEntity containing a list of ApplicationDocumentStatistics,
     * where each entry represents average document statistics categorized by application status.
     */
    @GetMapping(ApplicationPriv.VIEW_DOCUMENT_STATISTICS_PATH)
    @Operation(summary = "Get document statistics by application status", description =
            "Retrieves average document " + "counts by application status")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Document statistics retrieved " +
            "successfully"), @ApiResponse(responseCode = "403", description = "Access denied - insufficient " +
            "permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.VIEW_DOCUMENT_STATISTICS})
    public ResponseEntity<List<ApplicationDocumentStatistics>> getDocumentStatistics() {
        List<ApplicationDocumentStatistics> statistics = applicationService.getDocumentStatisticsByStatus();
        return ResponseEntity.ok(statistics);
    }

    // ADD THESE METHODS to your existing ApplicationController.java

    /**
     * Replace a document (NEW ENDPOINT)
     */
    @PutMapping("/documents/{documentSubmissionId}/replace")
    @Operation(summary = "Replace a document submission")
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.UPDATE_APPLICATION})
    public ResponseEntity<MisResponse<ApplicationDocumentSubmissionResponse>> replaceDocument(
            @PathVariable String documentSubmissionId,
            @RequestParam String newDocumentId,
            @RequestParam(required = false) String replacementReason) {

        try {
            ApplicationDocumentSubmissionResponse response = applicationService.replaceDocumentInApplication(
                    documentSubmissionId,
                    newDocumentId,
                    replacementReason,
                    "current-user" // Replace with actual user extraction
            );

            return ResponseEntity.ok(new MisResponse<>(true, "Document replaced successfully", response));

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MisResponse<>(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MisResponse<>(false, "Failed to replace document: " + e.getMessage()));
        }
    }

    /**
     * Get document version history (NEW ENDPOINT)
     */
    @GetMapping("/documents/{documentSubmissionId}/versions")
    @Operation(summary = "Get document version history")
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.VIEW_APPLICATION})
    public ResponseEntity<MisResponse<List<DocumentVersion>>> getDocumentVersionHistory(
            @PathVariable String documentSubmissionId) {

        try {
            List<DocumentVersion> versions = applicationService.getDocumentVersionHistory(documentSubmissionId);
            return ResponseEntity.ok(new MisResponse<>(true, "Version history retrieved successfully", versions));

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MisResponse<>(false, e.getMessage()));
        }
    }

    /**
     * Check if document can be replaced (NEW ENDPOINT)
     */
    @GetMapping("/documents/{documentSubmissionId}/can-replace")
    @Operation(summary = "Check if document can be replaced")
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationPriv.VIEW_APPLICATION})
    public ResponseEntity<MisResponse<Boolean>> canReplaceDocument(@PathVariable String documentSubmissionId) {

        boolean canReplace = applicationService.canReplaceDocument(documentSubmissionId);
        String message = canReplace ? "Document can be replaced" : "Document cannot be replaced";

        return ResponseEntity.ok(new MisResponse<>(true, message, canReplace));
    }
}