package rw.ac.ilpd.registrationservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import rw.ac.ilpd.mis.shared.config.MisConfig;
import rw.ac.ilpd.mis.shared.config.privilege.auth.SuperPrivilege;
import rw.ac.ilpd.mis.shared.config.privilege.registration.UniversityPriv;
import rw.ac.ilpd.mis.shared.security.Secured;
import rw.ac.ilpd.registrationservice.projection.CountryUniversityCount;
import rw.ac.ilpd.registrationservice.service.UniversityService;
import rw.ac.ilpd.sharedlibrary.dto.application.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.application.UniversityRequest;
import rw.ac.ilpd.sharedlibrary.dto.application.UniversityResponse;

import java.util.List;

/**
 * Controller class responsible for handling API requests related to universities.
 * Provides endpoints for managing university data including retrieval, creation, updating,
 * deletion, and analytics.
 */
@RestController
@RequestMapping(MisConfig.REGISTRATION_PATH + UniversityPriv.UNIVERSITY_PATH)
@Validated
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Universities", description = "University management endpoints")
@SecurityRequirement(name = "Bearer Authentication")
public class UniversityController {

    /**
     * Service layer dependency providing operations related to university management.
     * This includes the retrieval, creation, update, and deletion of university data,
     * as well as additional functionalities like searching and analytics.
     * <p>
     * It acts as an intermediary between the controller layer and the data layer,
     * encapsulating business logic for handling university-related requests.
     */
    private final UniversityService universityService;

    /**
     * Constructs a new UniversityController with the provided university service.
     *
     * @param universityService the service layer used for performing university-related operations
     */
    @Autowired
    public UniversityController(UniversityService universityService) {
        this.universityService = universityService;
    }

    /**
     * Retrieves a university by its unique identifier.
     *
     * @param id the unique identifier of the university to be retrieved
     * @return a {@code ResponseEntity} containing the university details if the university is found,
     * or an appropriate HTTP status if not found or access is denied
     */
    @GetMapping(UniversityPriv.VIEW_UNIVERSITY_PATH)
    @Operation(summary = "Get university by ID", description = "Retrieves a university by its unique identifier")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "University retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "University not found"), @ApiResponse(responseCode =
            "403", description = "Access denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, UniversityPriv.VIEW_UNIVERSITY})
    public ResponseEntity<UniversityResponse> getUniversityById(@PathVariable String id) {
        UniversityResponse university = universityService.getUniversityById(id);
        return ResponseEntity.ok(university);
    }

    /**
     * Retrieves a list of all universities with optional pagination parameters.
     *
     * @param page the index of the page to retrieve, starting from 0; default value is 0
     * @param size the number of universities to retrieve per page; default value is 10,
     *             minimum value is 1, and maximum value is 100
     * @return a {@code ResponseEntity} containing a list of {@code UniversityResponse} objects representing the
     * retrieved universities
     */
    @GetMapping(UniversityPriv.VIEW_ALL_UNIVERSITIES_PATH)
    @Operation(summary = "Get all universities", description = "Retrieves all universities with optional pagination")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Universities retrieved successfully"), @ApiResponse(responseCode = "403", description = "Access denied - insufficient permissions")})

    @Secured({SuperPrivilege.SUPER_ADMIN, UniversityPriv.VIEW_ALL_UNIVERSITIES})
    public ResponseEntity<List<UniversityResponse>> getAllUniversities(@RequestParam(defaultValue = "0") @Min(0) int page, @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {
        List<UniversityResponse> universities = universityService.getAllUniversities(page, size);
        return ResponseEntity.ok(universities);
    }

    /**
     * Retrieves a paginated list of universities based on the provided page number and size.
     *
     * @param page the page number to retrieve, must be greater than or equal to 0. Default is 0.
     * @param size the number of universities per page, must be between 1 and 100. Default is 10.
     * @return a ResponseEntity containing a PagedResponse of UniversityResponse objects.
     */
    @GetMapping(UniversityPriv.VIEW_PAGED_UNIVERSITIES_PATH)
    @Operation(summary = "Get paginated universities", description = "Retrieves universities with pagination support")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Universities retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, UniversityPriv.VIEW_PAGED_UNIVERSITIES})
    public ResponseEntity<PagedResponse<UniversityResponse>> getPagedUniversities(@RequestParam(defaultValue = "0") @Min(0) int page, @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {
        PagedResponse<UniversityResponse> universities = universityService.getPagedUniversities(page, size);
        return ResponseEntity.ok(universities);
    }

    /**
     * Searches universities by name with optional pagination.
     *
     * @param name the name or part of the name of the universities to search for
     * @param page the zero-based page index for pagination (default is 0, must be greater than or equal to 0)
     * @param size the number of records per page for pagination (default is 10, must be between 1 and 100)
     * @return a response entity containing a list of universities that match the search criteria, wrapped in a
     * ResponseEntity
     */
    @GetMapping(UniversityPriv.SEARCH_UNIVERSITIES_PATH)
    @Operation(summary = "Search universities by name", description =
            "Searches universities by name with optional " + "pagination")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Universities found successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, UniversityPriv.SEARCH_UNIVERSITIES})
    public ResponseEntity<List<UniversityResponse>> searchUniversitiesByName(@RequestParam String name,
                                                                             @RequestParam(defaultValue = "0") @Min(0) int page, @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {
        List<UniversityResponse> universities = universityService.searchUniversitiesByName(name, page, size);
        return ResponseEntity.ok(universities);
    }

    /**
     * Retrieves a list of universities located in a specific country, with support for pagination.
     *
     * @param country the country for which universities should be retrieved
     * @param page    the page number for pagination, starting from 0 (default is 0)
     * @param size    the number of items per page, with a minimum of 1 and a maximum of 100 (default is 10)
     * @return a ResponseEntity containing a list of universities matching the specified country
     */
    @GetMapping(UniversityPriv.VIEW_UNIVERSITIES_BY_COUNTRY_PATH)
    @Operation(summary = "Get universities by country", description =
            "Retrieves universities located in a specific " + "country")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Universities retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, UniversityPriv.VIEW_UNIVERSITIES_BY_COUNTRY})
    public ResponseEntity<List<UniversityResponse>> getUniversitiesByCountry(@PathVariable String country,
                                                                             @RequestParam(defaultValue = "0") @Min(0) int page, @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {
        List<UniversityResponse> universities = universityService.getUniversitiesByCountry(country, page, size);
        return ResponseEntity.ok(universities);
    }

    /**
     * Creates a new university based on the provided request data.
     *
     * @param universityRequest the request object containing details for creating a new university
     * @return a ResponseEntity containing the created university information and HTTP status 201 if successful
     */
    @PostMapping(UniversityPriv.CREATE_UNIVERSITY_PATH)
    @Operation(summary = "Create university", description = "Creates a new university")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "University created successfully"), @ApiResponse(responseCode = "400", description = "Invalid input data"), @ApiResponse(responseCode = "403", description = "Access denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, UniversityPriv.CREATE_UNIVERSITY})
    public ResponseEntity<UniversityResponse> createUniversity(@Valid @RequestBody UniversityRequest universityRequest) {
        UniversityResponse university = universityService.createUniversity(universityRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(university);
    }

    /**
     * Updates an existing university identified by its unique identifier.
     *
     * @param id                The unique identifier of the university to be updated.
     * @param universityRequest The details of the university to be updated, encapsulated in a UniversityRequest object.
     * @return A ResponseEntity containing the updated UniversityResponse object if the update is successful.
     */
    @PutMapping(UniversityPriv.UPDATE_UNIVERSITY_PATH)
    @Operation(summary = "Update university", description = "Updates an existing university")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "University updated successfully"),
            @ApiResponse(responseCode = "404", description = "University not found"), @ApiResponse(responseCode =
            "400", description = "Invalid input data"), @ApiResponse(responseCode = "403", description = "Access " +
            "denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, UniversityPriv.UPDATE_UNIVERSITY})
    public ResponseEntity<UniversityResponse> updateUniversity(@PathVariable String id,
                                                               @Valid @RequestBody UniversityRequest universityRequest) {
        UniversityResponse university = universityService.updateUniversity(id, universityRequest);
        return ResponseEntity.ok(university);
    }

    /**
     *
     */
    @DeleteMapping(UniversityPriv.DELETE_UNIVERSITY_PATH)
    @Operation(summary = "Delete university", description = "Deletes a university by its unique identifier")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "University deleted successfully"),
            @ApiResponse(responseCode = "404", description = "University not found"), @ApiResponse(responseCode =
            "403", description = "Access denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, UniversityPriv.DELETE_UNIVERSITY})
    public ResponseEntity<Void> deleteUniversity(@PathVariable String id) {
        universityService.deleteUniversity(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves the number of universities per country.
     *
     * @return a {@code ResponseEntity} containing a list of {@code CountryUniversityCount},
     * where each item represents the count of universities in a specific country.
     */
    @GetMapping(UniversityPriv.VIEW_COUNTRY_COUNTS_PATH)
    @Operation(summary = "Get university counts by country", description = "Retrieves the number of universities per "
            + "country")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Country counts retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, UniversityPriv.VIEW_ANALYTICS})
    public ResponseEntity<List<CountryUniversityCount>> getUniversityCountsByCountry() {
        List<CountryUniversityCount> countryCounts = universityService.getUniversityCountsByCountry();
        return ResponseEntity.ok(countryCounts);
    }
}