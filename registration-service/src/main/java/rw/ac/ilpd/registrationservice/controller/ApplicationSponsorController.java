package rw.ac.ilpd.registrationservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import rw.ac.ilpd.mis.shared.config.MisConfig;
import rw.ac.ilpd.mis.shared.config.privilege.auth.SuperPrivilege;
import rw.ac.ilpd.mis.shared.config.privilege.registration.ApplicationSponsorPriv;
import rw.ac.ilpd.mis.shared.security.Secured;
import rw.ac.ilpd.registrationservice.projection.ApplicationSponsorCount;
import rw.ac.ilpd.registrationservice.projection.SponsorApplicationCount;
import rw.ac.ilpd.registrationservice.service.ApplicationSponsorService;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationSponsorRequest;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationSponsorResponse;
import rw.ac.ilpd.sharedlibrary.dto.application.PagedResponse;

import java.util.List;

/**
 * This controller provides REST APIs for managing application sponsors, allowing
 * operations such as retrieval, creation, updating, and deletion of application sponsor data.
 * It includes endpoints for handling sponsors associated with specific applications or sponsors.
 * <p>
 * The controller is secured with role-based access control and uses pagination for list retrieval
 * endpoints.
 * <p>
 * Annotations:
 * - `@RestController` indicates that this class handles REST requests and responses.
 * - `@RequestMapping` maps the base URL for all endpoints in this controller.
 * - `@Validated` enables validation of method-level constraints on request parameters.
 * - `@CrossOrigin` allows cross-origin requests with specified origins and headers.
 * - `@Tag` groups API endpoints under "Application Sponsors" with descriptions.
 * - `@SecurityRequirement` applies a Bearer Authentication security requirement.
 */
@RestController
@RequestMapping(MisConfig.REGISTRATION_PATH + ApplicationSponsorPriv.SPONSORS)
@Validated
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Application Sponsors", description = "Application sponsor management endpoints")
@SecurityRequirement(name = "Bearer Authentication")
public class ApplicationSponsorController {

    /**
     * Represents the service responsible for handling operations related to
     * application sponsors. This may include creating, updating, retrieving,
     * or deleting sponsor-specific details associated with an application.
     */
    private final ApplicationSponsorService applicationSponsorService;

    /**
     * Constructs an instance of ApplicationSponsorController with the specified ApplicationSponsorService.
     *
     * @param applicationSponsorService the service used to handle operations related to application sponsors
     */
    @Autowired
    public ApplicationSponsorController(ApplicationSponsorService applicationSponsorService) {
        this.applicationSponsorService = applicationSponsorService;
    }

    /**
     * Retrieves an application sponsor by its unique identifier.
     *
     * @param id the unique identifier of the application sponsor to retrieve
     * @return a ResponseEntity containing the retrieved ApplicationSponsorResponse if found
     */
    @GetMapping(ApplicationSponsorPriv.VIEW_APPLICATION_SPONSOR_PATH)
    @Operation(summary = "Get application sponsor by ID", description = "Retrieves an application sponsor by its " +
            "unique identifier")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Application sponsor retrieved " +
            "successfully"), @ApiResponse(responseCode = "404", description = "Application sponsor not found"),
            @ApiResponse(responseCode = "403", description = "Access denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationSponsorPriv.VIEW_APPLICATION_SPONSOR})
    public ResponseEntity<ApplicationSponsorResponse> getApplicationSponsorById(@PathVariable String id) {
        ApplicationSponsorResponse applicationSponsor = applicationSponsorService.getApplicationSponsorById(id);
        return ResponseEntity.ok(applicationSponsor);
    }

    /**
     * Retrieves a list of all application sponsors with optional pagination.
     *
     * @param page the page number for pagination, must be a non-negative integer (default is 0)
     * @param size the number of records per page for pagination, must be between 1 and 100 (default is 10)
     * @return a ResponseEntity containing a list of ApplicationSponsorResponse objects
     */
    @GetMapping(ApplicationSponsorPriv.VIEW_ALL_APPLICATION_SPONSORS_PATH)
    @Operation(summary = "Get all application sponsors", description = "Retrieves all application sponsors with " +
            "optional pagination")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Application sponsors retrieved " +
            "successfully"), @ApiResponse(responseCode = "403", description = "Access denied - insufficient " +
            "permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationSponsorPriv.VIEW_ALL_APPLICATION_SPONSORS})
    public ResponseEntity<List<ApplicationSponsorResponse>> getAllApplicationSponsors(@RequestParam(defaultValue = "0"
    ) @Min(0) int page, @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {
        List<ApplicationSponsorResponse> applicationSponsors = applicationSponsorService.getAllApplicationSponsors(page,
                size);
        return ResponseEntity.ok(applicationSponsors);
    }

    /**
     * Retrieves a paginated list of application sponsors.
     *
     * @param page the page number to retrieve, defaults to 0 if not provided, must be 0 or greater.
     * @param size the number of items per page, defaults to 10 if not provided, must be between 1 and 100.
     * @return a {@code ResponseEntity} containing a {@code PagedResponse} of {@code ApplicationSponsorResponse},
     * representing the paginated list of application sponsors.
     */
    @GetMapping(ApplicationSponsorPriv.VIEW_PAGED_APPLICATION_SPONSORS_PATH)
    @Operation(summary = "Get paginated application sponsors", description =
            "Retrieves application sponsors with " + "pagination support")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Application sponsors retrieved " +
            "successfully"), @ApiResponse(responseCode = "403", description = "Access denied - insufficient " +
            "permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationSponsorPriv.VIEW_PAGED_APPLICATION_SPONSORS})
    public ResponseEntity<PagedResponse<ApplicationSponsorResponse>> getPagedApplicationSponsors(@RequestParam(defaultValue = "0") @Min(0) int page, @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {
        PagedResponse<ApplicationSponsorResponse> applicationSponsors =
                applicationSponsorService.getPagedApplicationSponsors(
                page, size);
        return ResponseEntity.ok(applicationSponsors);
    }

    /**
     * Retrieves a list of sponsors associated with a specific application.
     *
     * @param applicationId the unique identifier of the application for which sponsors are being retrieved
     * @param page          the page number for pagination, must be greater than or equal to 0; default is 0
     * @param size          the number of items per page, must be between 1 and 100; default is 10
     * @return a ResponseEntity containing a list of ApplicationSponsorResponse objects if the application exists,
     * or a ResponseEntity with an appropriate error response if not
     */
    @GetMapping(ApplicationSponsorPriv.VIEW_APPLICATION_SPONSORS_BY_APPLICATION_PATH)
    @Operation(summary = "Get sponsors by application", description = "Retrieves sponsors for a specific application")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Application sponsors retrieved " +
            "successfully"), @ApiResponse(responseCode = "404", description = "Application not found"),
            @ApiResponse(responseCode = "403", description = "Access denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationSponsorPriv.VIEW_APPLICATION_SPONSORS_BY_APPLICATION})
    public ResponseEntity<List<ApplicationSponsorResponse>> getApplicationSponsorsByApplication(@PathVariable String applicationId, @RequestParam(defaultValue = "0") @Min(0) int page, @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {
        List<ApplicationSponsorResponse> applicationSponsors =
                applicationSponsorService.getApplicationSponsorsByApplication(
                applicationId, page, size);
        return ResponseEntity.ok(applicationSponsors);
    }

    /**
     * Retrieves a paginated list of application sponsors for a specified sponsor.
     *
     * @param sponsorId the unique identifier of the sponsor whose application sponsors are to be retrieved
     * @param page      the page number of the results to retrieve, starting from 0 (default is 0)
     * @param size      the number of records per page, with a minimum of 1 and maximum of 100 (default is 10)
     * @return a ResponseEntity containing a list of application sponsors associated with the specified sponsor
     */
    @GetMapping(ApplicationSponsorPriv.VIEW_APPLICATION_SPONSORS_BY_SPONSOR_PATH)
    @Operation(summary = "Get applications by sponsor", description = "Retrieves applications for a specific sponsor")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Application sponsors retrieved " +
            "successfully"), @ApiResponse(responseCode = "403", description = "Access denied - insufficient " +
            "permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationSponsorPriv.VIEW_APPLICATION_SPONSORS_BY_SPONSOR})
    public ResponseEntity<List<ApplicationSponsorResponse>> getApplicationSponsorsBySponsor(@PathVariable String sponsorId, @RequestParam(defaultValue = "0") @Min(0) int page, @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {
        List<ApplicationSponsorResponse> applicationSponsors =
                applicationSponsorService.getApplicationSponsorsBySponsor(
                sponsorId, page, size);
        return ResponseEntity.ok(applicationSponsors);
    }

    /**
     * Creates a new application sponsor relationship.
     *
     * @param applicationSponsorRequest The request payload containing the details of the application sponsor to be
     *                                  created.
     * @return A ResponseEntity containing the created ApplicationSponsorResponse, wrapped with an HTTP 201 status if
     * the operation is successful.
     */
    @PostMapping(ApplicationSponsorPriv.CREATE_APPLICATION_SPONSOR_PATH)
    @Operation(summary = "Create application sponsor", description = "Creates a new application sponsor relationship")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Application sponsor created " +
            "successfully"), @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "403", description = "Access denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationSponsorPriv.CREATE_APPLICATION_SPONSOR})
    public ResponseEntity<ApplicationSponsorResponse> createApplicationSponsor(@Valid @RequestBody ApplicationSponsorRequest applicationSponsorRequest) {
        ApplicationSponsorResponse applicationSponsor = applicationSponsorService.createApplicationSponsor(
                applicationSponsorRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationSponsor);
    }
    /**
     * Creates a new application sponsor with recommendation letter upload in one step.
     */
    @PostMapping(value = ApplicationSponsorPriv.CREATE_APPLICATION_SPONSOR_PATH + "/with-recommendation",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Create sponsor with recommendation letter",
            description = "Creates a new sponsor relationship and uploads recommendation letter in one step")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sponsor created with recommendation letter successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data or file"),
            @ApiResponse(responseCode = "403", description = "Access denied - insufficient permissions")
    })
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationSponsorPriv.CREATE_APPLICATION_SPONSOR})
    public ResponseEntity<ApplicationSponsorResponse> createSponsorWithRecommendationLetter(
            @RequestParam("applicationId") @NotBlank String applicationId,
            @RequestParam("sponsorId") @NotBlank String sponsorId,
            @RequestParam("recommendationLetter") MultipartFile recommendationFile,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {

        String uploadedBy = userDetails != null ? userDetails.getUsername() : "system";

        ApplicationSponsorResponse sponsor = applicationSponsorService.createSponsorWithRecommendationLetter(
                applicationId, sponsorId, recommendationFile, uploadedBy);

        return ResponseEntity.status(HttpStatus.CREATED).body(sponsor);
    }
    /**
     * Updates an existing application sponsor with the provided details.
     *
     * @param id                        the unique identifier of the application sponsor to be updated
     * @param applicationSponsorRequest the updated details for the application sponsor
     * @return a ResponseEntity containing the updated ApplicationSponsorResponse
     */
    @PutMapping(ApplicationSponsorPriv.UPDATE_APPLICATION_SPONSOR_PATH)
    @Operation(summary = "Update application sponsor", description = "Updates an existing application sponsor")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Application sponsor updated " +
            "successfully"), @ApiResponse(responseCode = "404", description = "Application sponsor not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"), @ApiResponse(responseCode = "403"
            , description = "Access denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationSponsorPriv.UPDATE_APPLICATION_SPONSOR})
    public ResponseEntity<ApplicationSponsorResponse> updateApplicationSponsor(@PathVariable String id,
                                                                               @Valid @RequestBody ApplicationSponsorRequest applicationSponsorRequest) {
        ApplicationSponsorResponse applicationSponsor = applicationSponsorService.updateApplicationSponsor(id,
                applicationSponsorRequest);
        return ResponseEntity.ok(applicationSponsor);
    }

    /**
     * Deletes an application sponsor identified by its unique ID.
     *
     * @param id the unique identifier of the application sponsor to be deleted
     * @return a ResponseEntity indicating the completion of the delete operation with no content
     */
    @DeleteMapping(ApplicationSponsorPriv.DELETE_APPLICATION_SPONSOR_PATH)
    @Operation(summary = "Delete application sponsor", description =
            "Deletes an application sponsor by its unique " + "identifier")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Application sponsor deleted " +
            "successfully"), @ApiResponse(responseCode = "404", description = "Application sponsor not found"),
            @ApiResponse(responseCode = "403", description = "Access denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationSponsorPriv.DELETE_APPLICATION_SPONSOR})
    public ResponseEntity<Void> deleteApplicationSponsor(@PathVariable String id) {
        applicationSponsorService.deleteApplicationSponsor(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Deletes all sponsors associated with the specified application.
     *
     * @param applicationId the identifier of the application whose sponsors are to be deleted
     * @return a ResponseEntity with no content, indicating the deletion was successful
     */
    @DeleteMapping(ApplicationSponsorPriv.DELETE_APPLICATION_SPONSORS_BY_APPLICATION_PATH)
    @Operation(summary = "Delete sponsors by application", description = "Deletes all sponsors for a specific " +
            "application")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Application sponsors deleted " +
            "successfully"), @ApiResponse(responseCode = "404", description = "Application not found"),
            @ApiResponse(responseCode = "403", description = "Access denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationSponsorPriv.DELETE_APPLICATION_SPONSORS_BY_APPLICATION})
    public ResponseEntity<Void> deleteApplicationSponsorsByApplication(@PathVariable String applicationId) {
        applicationSponsorService.deleteApplicationSponsorsByApplication(applicationId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Deletes all application sponsors associated with the specified sponsor.
     *
     * @param sponsorId the ID of the sponsor whose associated application sponsors are to be deleted
     * @return a ResponseEntity with no content, indicating successful deletion
     */
    @DeleteMapping(ApplicationSponsorPriv.DELETE_APPLICATION_SPONSORS_BY_SPONSOR_PATH)
    @Operation(summary = "Delete applications by sponsor", description =
            "Deletes all applications for a specific " + "sponsor")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Application sponsors deleted " +
            "successfully"), @ApiResponse(responseCode = "403", description = "Access denied - insufficient " +
            "permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationSponsorPriv.DELETE_APPLICATION_SPONSORS_BY_SPONSOR})
    public ResponseEntity<Void> deleteApplicationSponsorsBySponsor(@PathVariable String sponsorId) {
        applicationSponsorService.deleteApplicationSponsorsBySponsor(sponsorId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves the application counts categorized by sponsor.
     *
     * @return a ResponseEntity containing a list of SponsorApplicationCount objects representing application counts
     * for each sponsor
     */
    @GetMapping(ApplicationSponsorPriv.VIEW_SPONSOR_APPLICATION_COUNTS_PATH)
    @Operation(summary = "Get sponsor application counts", description = "Retrieves application counts per sponsor")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Sponsor counts retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationSponsorPriv.VIEW_SPONSOR_APPLICATION_COUNTS})
    public ResponseEntity<List<SponsorApplicationCount>> getSponsorApplicationCounts() {
        List<SponsorApplicationCount> sponsorCounts = applicationSponsorService.getSponsorApplicationCounts();
        return ResponseEntity.ok(sponsorCounts);
    }

    /**
     * Retrieves sponsor counts for each application.
     *
     * @return a ResponseEntity containing a list of ApplicationSponsorCount objects.
     * Each object represents the sponsor counts associated with specific applications.
     */
    @GetMapping(ApplicationSponsorPriv.VIEW_APPLICATION_SPONSOR_COUNTS_PATH)
    @Operation(summary = "Get application sponsor counts", description = "Retrieves sponsor counts per application")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Application counts retrieved " +
            "successfully"), @ApiResponse(responseCode = "403", description = "Access denied - insufficient " +
            "permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, ApplicationSponsorPriv.VIEW_APPLICATION_SPONSOR_COUNTS})
    public ResponseEntity<List<ApplicationSponsorCount>> getApplicationSponsorCounts() {
        List<ApplicationSponsorCount> applicationCounts = applicationSponsorService.getApplicationSponsorCounts();
        return ResponseEntity.ok(applicationCounts);
    }
}