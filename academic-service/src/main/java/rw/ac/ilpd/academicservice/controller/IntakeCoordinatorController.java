package rw.ac.ilpd.academicservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.academicservice.service.IntakeCoordinatorService;
import rw.ac.ilpd.mis.shared.config.privilege.academic.IntakeCoordinatorPriv;
import rw.ac.ilpd.mis.shared.config.privilege.auth.SuperPrivilege;
import rw.ac.ilpd.mis.shared.security.Secured;
import rw.ac.ilpd.sharedlibrary.dto.intake.IntakeCoordinatorRequest;
import rw.ac.ilpd.sharedlibrary.dto.intake.IntakeCoordinatorResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;

/**
 * Controller that handles all endpoints that deal with the IntakeCoordinator resource.
 */
@RestController
@RequestMapping(value = IntakeCoordinatorPriv.INTAKE_COORDINATOR_PATH)
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Intake Coordinator", description = "Endpoints for managing intake coordinators")
@SecurityRequirement(name = "Bearer Authentication")
public class IntakeCoordinatorController
{
    private final IntakeCoordinatorService intakeCoordinatorService;

    /**
     * Endpoint to create a new IntakeCoordinator
     */
    @Operation(summary = "Create a new intake coordinator",
            description = "Creates a new intake coordinator.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Coordinator created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(IntakeCoordinatorPriv.CREATE_INTAKE_COORDINATOR_PATH)
    @Secured({SuperPrivilege.SUPER_ADMIN, IntakeCoordinatorPriv.CREATE_INTAKE_COORDINATOR})
    public ResponseEntity<IntakeCoordinatorResponse> createIntakeCoordinator(
            @Parameter(description = "Request payload for creating an intake coordinator")
            @Valid @RequestBody IntakeCoordinatorRequest coordinatorRequest)
    {
        log.debug("Create coordinator endpoint reached for request: {}", coordinatorRequest);
        return ResponseEntity.ok(intakeCoordinatorService.createIntakeCoordinator(coordinatorRequest));
    }

    /**
     * Endpoint to get all coordinators
     */
    @Operation(summary = "Get all intake coordinators",
            description = "Retrieves a paginated list of intake coordinators with sorting options.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Coordinators retrieved successfully with pagination metadata"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination or sorting parameters")
    })
    @GetMapping(value = IntakeCoordinatorPriv.GET_ALL_INTAKE_COORDINATORS_PATH)
    @Secured({SuperPrivilege.SUPER_ADMIN, IntakeCoordinatorPriv.GET_ALL_INTAKE_COORDINATORS})
    public ResponseEntity<PagedResponse<IntakeCoordinatorResponse>> getAllIntakeCoordinators(
            @Parameter(description = "Page number (0-based, default: 0)") @RequestParam(required = false, defaultValue = "0") int page,
            @Parameter(description = "Number of items per page (default: 10)") @RequestParam(required = false, defaultValue = "10") int size,
            @Parameter(description = "Sort field (e.g., 'createdAt', default: 'createdAt')") @RequestParam(name = "sort-by", required = false, defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Sort direction (asc or desc, default: desc)") @RequestParam(required = false, defaultValue = "desc") String order
    )
    {
        log.info("Get all coordinators endpoint reached");
        return ResponseEntity.ok(intakeCoordinatorService.getAllIntakeCoordinators(page, size, sortBy, order));
    }

    /**
     * Endpoint to get a coordinator by id
     */
    @Operation(summary = "Get an intake coordinator by ID",
            description = "Retrieves an intake coordinator by its unique ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Coordinator found"),
            @ApiResponse(responseCode = "404", description = "Coordinator not found")
    })
    @GetMapping(IntakeCoordinatorPriv.GET_INTAKE_COORDINATOR_PATH)
    @Secured({SuperPrivilege.SUPER_ADMIN, IntakeCoordinatorPriv.GET_INTAKE_COORDINATOR})
    public ResponseEntity<IntakeCoordinatorResponse> getIntakeCoordinatorById(
            @Parameter(description = "ID of the coordinator to retrieve") @Valid @NotNull(message = "coordinator id must not be null") @NotBlank(message = "coordinator id must not be blank") @PathVariable String id)
    {
        log.debug("Get program by id endpoint reached for request: {}", id);
        return ResponseEntity.ok(intakeCoordinatorService.getIntakeCoordinatorById(id));
    }

    /**
     * Endpoint to update the entire coordinator resource
     */
    @Operation(summary = "Update an intake coordinator",
            description = "Updates an existing intake coordinator by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Coordinator updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Coordinator not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping(IntakeCoordinatorPriv.UPDATE_INTAKE_COORDINATOR_PATH)
    @Secured({SuperPrivilege.SUPER_ADMIN, IntakeCoordinatorPriv.UPDATE_INTAKE_COORDINATOR})
    public ResponseEntity<IntakeCoordinatorResponse> updateIntakeCoordinator(
            @Parameter(description = "ID of the coordinator to update") @Valid @NotNull(message = "coordinator id must not be null") @NotBlank(message = "coordinator id must not be blank") @PathVariable String id,
            @Parameter(description = "Request payload for updating the coordinator") @Valid @RequestBody IntakeCoordinatorRequest coordinatorRequest
    )
    {
        log.debug("Update coordinator endpoint reached with request: {}", coordinatorRequest);
        return ResponseEntity.ok(intakeCoordinatorService.updateIntakeCoordinator(id, coordinatorRequest));
    }

    /**
     * Endpoint to permanently delete a coordinator entity
     */
    @Operation(summary = "Delete an intake coordinator",
            description = "Permanently deletes an intake coordinator by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Coordinator deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Coordinator not found")
    })
    @DeleteMapping(IntakeCoordinatorPriv.DELETE_INTAKE_COORDINATOR_PATH)
    @Secured({SuperPrivilege.SUPER_ADMIN, IntakeCoordinatorPriv.DELETE_INTAKE_COORDINATOR})
    public ResponseEntity<Boolean> deleteIntakeCoordinator(
            @Parameter(description = "ID of the coordinator to delete") @Valid @NotNull(message = "coordinator id must not be null") @NotBlank(message = "coordinator id must not be blank") @PathVariable String id
    )
    {
        log.debug("permanently delete coordinator endpoint reached for request: {}", id);
        return new ResponseEntity<>(intakeCoordinatorService.deleteIntakeCoordinator(id), HttpStatus.OK);
    }
}