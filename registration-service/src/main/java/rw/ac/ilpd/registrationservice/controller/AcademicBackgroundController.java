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
import org.springframework.http.MediaType;
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
import rw.ac.ilpd.mis.shared.config.privilege.registration.AcademicBackgroundPriv;
import rw.ac.ilpd.mis.shared.security.Secured;
import rw.ac.ilpd.mis.shared.util.helpers.MisResponse;
import rw.ac.ilpd.registrationservice.projection.CountryAcademicCount;
import rw.ac.ilpd.registrationservice.projection.DegreeCount;
import rw.ac.ilpd.registrationservice.service.AcademicBackgroundService;
import rw.ac.ilpd.sharedlibrary.dto.application.AcademicBackgroundRequest;
import rw.ac.ilpd.sharedlibrary.dto.application.AcademicBackgroundResponse;
import rw.ac.ilpd.sharedlibrary.dto.application.MultipleAcademicBackgroundRequest;
import rw.ac.ilpd.sharedlibrary.dto.application.MultipleAcademicBackgroundResponse;
import rw.ac.ilpd.sharedlibrary.dto.application.PagedResponse;

import java.util.List;

/**
 * Controller class for managing user-centric academic backgrounds.
 * Supports multiple academic backgrounds per user with embedded recommender data.
 */
@RestController
@RequestMapping(value = MisConfig.REGISTRATION_PATH + AcademicBackgroundPriv.ACADEMIC_BACKGROUND_PATH, produces =
        MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
@Validated
@Tag(name = "Academic Backgrounds", description = "API for managing user academic backgrounds")
@SecurityRequirement(name = "Bearer Authentication")
public class AcademicBackgroundController {

    private final AcademicBackgroundService academicBackgroundService;

    @Autowired
    public AcademicBackgroundController(AcademicBackgroundService academicBackgroundService) {
        this.academicBackgroundService = academicBackgroundService;
    }

    /**
     * Creates a single academic background for a user.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create academic background", description = "Creates a new academic background for a user")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Academic background created " +
            "successfully"), @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "403", description = "Access denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, AcademicBackgroundPriv.CREATE_ACADEMIC_BACKGROUND})
    public ResponseEntity<MisResponse<AcademicBackgroundResponse>> createAcademicBackground(@Valid @RequestBody AcademicBackgroundRequest request) {

        AcademicBackgroundResponse response = academicBackgroundService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MisResponse<>(true, "Academic background created successfully", response));
    }

    /**
     * Creates multiple academic backgrounds for a user in a single request.
     */
    @PostMapping(value = "/bulk", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create multiple academic backgrounds", description = "Creates multiple academic backgrounds" +
            " for a user in a single request")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Academic backgrounds created " +
            "successfully"), @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "403", description = "Access denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, AcademicBackgroundPriv.CREATE_ACADEMIC_BACKGROUND})
    public ResponseEntity<MisResponse<MultipleAcademicBackgroundResponse>> createMultipleAcademicBackgrounds(@Valid @RequestBody MultipleAcademicBackgroundRequest request) {

        MultipleAcademicBackgroundResponse response = academicBackgroundService.createMultiple(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MisResponse<>(true, "Academic backgrounds created successfully", response));
    }

    /**
     * Retrieves all academic backgrounds for a specific user.
     */
    @GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get academic backgrounds by user ID", description = "Retrieves all academic backgrounds for" +
            " a specific user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Academic backgrounds retrieved " +
            "successfully"), @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "403", description = "Access denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, AcademicBackgroundPriv.GET_ACADEMIC_BACKGROUND})
    public ResponseEntity<MisResponse<MultipleAcademicBackgroundResponse>> getAcademicBackgroundsByUserId(@PathVariable String userId) {

        MultipleAcademicBackgroundResponse response = academicBackgroundService.findByUserId(userId);
        return ResponseEntity.ok(new MisResponse<>(true, "Academic backgrounds retrieved successfully", response));
    }

    /**
     * Retrieves academic backgrounds for a user with pagination.
     */
    @GetMapping(value = "/user/{userId}/paginated", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get paginated academic backgrounds by user ID", description = "Retrieves paginated academic" +
            " backgrounds for a specific user")
    @Secured({SuperPrivilege.SUPER_ADMIN, AcademicBackgroundPriv.GET_ACADEMIC_BACKGROUND})
    public ResponseEntity<MisResponse<PagedResponse<AcademicBackgroundResponse>>> getAcademicBackgroundsByUserIdPaginated(@PathVariable String userId, @RequestParam(defaultValue = "0") @Min(0) int page, @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {

        PagedResponse<AcademicBackgroundResponse> response = academicBackgroundService.findByUserIdPaginated(userId,
                page, size);
        return ResponseEntity.ok(new MisResponse<>(true, "Academic backgrounds retrieved successfully", response));
    }

    /**
     * Adds a new academic background to an existing user.
     */
    @PostMapping(value = "/user/{userId}/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Add academic background to user", description = "Adds a new academic background to an " +
            "existing user")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Academic background added successfully")
            , @ApiResponse(responseCode = "400", description = "Invalid input data"), @ApiResponse(responseCode =
            "404", description = "User not found"), @ApiResponse(responseCode = "403", description = "Access denied -" +
            " insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, AcademicBackgroundPriv.CREATE_ACADEMIC_BACKGROUND})
    public ResponseEntity<MisResponse<AcademicBackgroundResponse>> addAcademicBackgroundToUser(@PathVariable String userId, @Valid @RequestBody AcademicBackgroundRequest request) {

        AcademicBackgroundResponse response = academicBackgroundService.addAcademicBackgroundToUser(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MisResponse<>(true, "Academic background added successfully", response));
    }

    /**
     * Retrieves a single academic background by its ID.
     */
    @GetMapping(value = AcademicBackgroundPriv.GET_ACADEMIC_BACKGROUND_BY_ID_PATH, produces =
            MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get academic background by ID", description = "Retrieves a specific academic background by " +
            "its unique identifier")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Academic background retrieved " +
            "successfully"), @ApiResponse(responseCode = "404", description = "Academic background not found"),
            @ApiResponse(responseCode = "403", description = "Access denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, AcademicBackgroundPriv.GET_ACADEMIC_BACKGROUND})
    public ResponseEntity<MisResponse<AcademicBackgroundResponse>> getAcademicBackgroundById(@PathVariable String id) {
        AcademicBackgroundResponse response = academicBackgroundService.findById(id);
        return ResponseEntity.ok(new MisResponse<>(true, "Academic background retrieved successfully", response));
    }

    /**
     * Retrieves all academic backgrounds with pagination.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all academic backgrounds", description = "Retrieves all academic backgrounds with " +
            "pagination")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Academic backgrounds retrieved " +
            "successfully"), @ApiResponse(responseCode = "403", description = "Access denied - insufficient " +
            "permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, AcademicBackgroundPriv.GET_ALL_ACADEMIC_BACKGROUNDS})
    public ResponseEntity<MisResponse<PagedResponse<AcademicBackgroundResponse>>> getAllAcademicBackgrounds(@RequestParam(defaultValue = "0") @Min(0) int page, @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {

        PagedResponse<AcademicBackgroundResponse> response = academicBackgroundService.findAll(page, size);
        return ResponseEntity.ok(new MisResponse<>(true, "Academic backgrounds retrieved successfully", response));
    }

    /**
     * Updates an existing academic background.
     */
    @PutMapping(value = AcademicBackgroundPriv.UPDATE_ACADEMIC_BACKGROUND_PATH, produces =
            MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update academic background", description = "Updates an existing academic background")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Academic background updated " +
            "successfully"), @ApiResponse(responseCode = "404", description = "Academic background not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"), @ApiResponse(responseCode = "403"
            , description = "Access denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, AcademicBackgroundPriv.UPDATE_ACADEMIC_BACKGROUND})
    public ResponseEntity<MisResponse<AcademicBackgroundResponse>> updateAcademicBackground(@PathVariable String id,
                                                                                            @Valid @RequestBody AcademicBackgroundRequest request) {

        AcademicBackgroundResponse response = academicBackgroundService.update(id, request);
        return ResponseEntity.ok(new MisResponse<>(true, "Academic background updated successfully", response));
    }

    /**
     * Deletes a single academic background by its ID.
     */
    @DeleteMapping(value = AcademicBackgroundPriv.DELETE_ACADEMIC_BACKGROUND_PATH, produces =
            MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete academic background", description = "Deletes an academic background by its unique " +
            "identifier")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Academic background deleted " +
            "successfully"), @ApiResponse(responseCode = "404", description = "Academic background not found"),
            @ApiResponse(responseCode = "403", description = "Access denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, AcademicBackgroundPriv.DELETE_ACADEMIC_BACKGROUND})
    public ResponseEntity<MisResponse<Void>> deleteAcademicBackground(@PathVariable String id) {
        try {
            academicBackgroundService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new MisResponse<>(true, "Academic background deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MisResponse<>(false, "Academic background not found for id: " + id));
        }
    }

    /**
     * Deletes all academic backgrounds for a specific user.
     */
    @DeleteMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete all academic backgrounds for user", description = "Deletes all academic backgrounds " +
            "for a specific user")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Academic backgrounds deleted " +
            "successfully"), @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "403", description = "Access denied - insufficient permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, AcademicBackgroundPriv.DELETE_ACADEMIC_BACKGROUND})
    public ResponseEntity<MisResponse<Void>> deleteAllAcademicBackgroundsByUserId(@PathVariable String userId) {
        try {
            academicBackgroundService.deleteAllByUserId(userId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new MisResponse<>(true, "All academic backgrounds deleted successfully for user: " + userId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MisResponse<>(false, "No academic backgrounds found for user: " + userId));
        }
    }

    /**
     * Search academic backgrounds by degree or university name.
     */
    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Search academic backgrounds", description = "Search academic backgrounds by degree or " +
            "university name")
    @Secured({SuperPrivilege.SUPER_ADMIN, AcademicBackgroundPriv.GET_ACADEMIC_BACKGROUND})
    public ResponseEntity<MisResponse<List<AcademicBackgroundResponse>>> searchAcademicBackgrounds(@RequestParam String searchTerm) {

        List<AcademicBackgroundResponse> response = academicBackgroundService.searchByDegreeOrUniversity(searchTerm);
        return ResponseEntity.ok(new MisResponse<>(true, "Search completed successfully", response));
    }

    /**
     * Search a specific user's academic backgrounds.
     */
    @GetMapping(value = "/user/{userId}/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Search user's academic backgrounds", description = "Search academic backgrounds for a " +
            "specific user")
    @Secured({SuperPrivilege.SUPER_ADMIN, AcademicBackgroundPriv.GET_ACADEMIC_BACKGROUND})
    public ResponseEntity<MisResponse<MultipleAcademicBackgroundResponse>> searchUserAcademicBackgrounds(@PathVariable String userId, @RequestParam String searchTerm) {

        MultipleAcademicBackgroundResponse response = academicBackgroundService.searchUserAcademicBackgrounds(userId,
                searchTerm);
        return ResponseEntity.ok(new MisResponse<>(true, "Search completed successfully", response));
    }

    // Statistics and Analytics Endpoints

    /**
     * Retrieves the degree distribution statistics.
     */
    @GetMapping(value = "/degree-distribution", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get degree distribution", description = "Retrieves degree distribution statistics")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Degree distribution retrieved " +
            "successfully"), @ApiResponse(responseCode = "403", description = "Access denied - insufficient " +
            "permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, AcademicBackgroundPriv.GET_DEGREE_DISTRIBUTION})
    public ResponseEntity<MisResponse<List<DegreeCount>>> getDegreeDistribution() {
        List<DegreeCount> response = academicBackgroundService.getDegreeDistribution();
        return ResponseEntity.ok(new MisResponse<>(true, "Degree distribution retrieved successfully", response));
    }

    /**
     * Retrieves the country-wise academic background distribution.
     */
    @GetMapping(value = "/country-distribution", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get country distribution", description = "Retrieves country-wise academic background " +
            "distribution")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Country distribution retrieved " +
            "successfully"), @ApiResponse(responseCode = "403", description = "Access denied - insufficient " +
            "permissions")})
    @Secured({SuperPrivilege.SUPER_ADMIN, AcademicBackgroundPriv.GET_COUNTRY_DISTRIBUTION})
    public ResponseEntity<MisResponse<List<CountryAcademicCount>>> getCountryDistribution() {
        List<CountryAcademicCount> response = academicBackgroundService.getCountryDistribution();
        return ResponseEntity.ok(new MisResponse<>(true, "Country distribution retrieved successfully", response));
    }

    /**
     * Gets degree distribution for a specific user.
     */
    @GetMapping(value = "/user/{userId}/degree-distribution", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get user's degree distribution", description = "Retrieves degree distribution for a " +
            "specific user")
    @Secured({SuperPrivilege.SUPER_ADMIN, AcademicBackgroundPriv.GET_DEGREE_DISTRIBUTION})
    public ResponseEntity<MisResponse<List<DegreeCount>>> getUserDegreeDistribution(@PathVariable String userId) {
        List<DegreeCount> response = academicBackgroundService.getUserDegreeDistribution(userId);
        return ResponseEntity.ok(new MisResponse<>(true, "User degree distribution retrieved successfully", response));
    }

    /**
     * Gets country distribution for a specific user.
     */
    @GetMapping(value = "/user/{userId}/country-distribution", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get user's country distribution", description = "Retrieves country distribution for a " +
            "specific user")
    @Secured({SuperPrivilege.SUPER_ADMIN, AcademicBackgroundPriv.GET_COUNTRY_DISTRIBUTION})
    public ResponseEntity<MisResponse<List<CountryAcademicCount>>> getUserCountryDistribution(@PathVariable String userId) {
        List<CountryAcademicCount> response = academicBackgroundService.getUserCountryDistribution(userId);
        return ResponseEntity.ok(new MisResponse<>(true, "User country distribution retrieved successfully", response));
    }
}