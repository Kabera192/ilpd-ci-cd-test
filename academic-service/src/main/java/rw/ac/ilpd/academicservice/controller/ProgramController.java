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
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.academicservice.service.ProgramService;
import rw.ac.ilpd.mis.shared.config.privilege.academic.ProgramPriv;
import rw.ac.ilpd.mis.shared.config.privilege.auth.SuperPrivilege;
import rw.ac.ilpd.mis.shared.security.Secured;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.program.ProgramRequest;
import rw.ac.ilpd.sharedlibrary.dto.program.ProgramResponse;

/**
 * Controller that handles all endpoints that deal with the Program resource.
 */
@RestController
@RequestMapping(ProgramPriv.PROGRAM_PATH)
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Program", description = "Endpoints for managing academic programs")
@SecurityRequirement(name = "Bearer Authentication")
public class ProgramController
{
    private final ProgramService programService;

    /**
     * Endpoint to create a new Program
     */
    @Operation(summary = "Create a new program",
            description = "Creates a new academic program.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Program created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(ProgramPriv.CREATE_PROGRAM_PATH)
    @Secured({SuperPrivilege.SUPER_ADMIN, ProgramPriv.CREATE_PROGRAM})
    public ResponseEntity<ProgramResponse> createProgram(
            @Parameter(description = "Request payload for creating a program")
            @Valid @RequestBody ProgramRequest programRequest)
    {
        log.debug("Create program endpoint reached for request: {}", programRequest);
        return ResponseEntity.ok(programService.createProgram(programRequest));
    }

    /**
     * Endpoint to get all programs
     */
    @Operation(summary = "Get all programs",
            description = "Retrieves a paginated list of academic programs with sorting options.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Programs retrieved successfully with pagination metadata"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination or sorting parameters")
    })
    @GetMapping(ProgramPriv.GET_ALL_PROGRAMS_PATH)
    @Secured({SuperPrivilege.SUPER_ADMIN, ProgramPriv.GET_ALL_PROGRAMS})
    public ResponseEntity<PagedResponse<ProgramResponse>> getAllPrograms(
            @Parameter(description = "Page number (0-based, default: 0)")
            @RequestParam(required = false, defaultValue = "0") int page,
            @Parameter(description = "Number of items per page (default: 10)")
            @RequestParam(required = false, defaultValue = "10") int size,
            @Parameter(description = "Sort field (e.g., 'name', default: 'name')")
            @RequestParam(name = "sort-by", required = false, defaultValue = "name") String sortBy,
            @Parameter(description = "Sort direction (asc or desc, default: asc)")
            @RequestParam(required = false, defaultValue = "asc") String order
    )
    {
        log.info("Get all programs endpoint reached");
        return ResponseEntity.ok(programService.getAllPrograms(page, size, sortBy, order));
    }

    /**
     * Endpoint to get a program by id
     */
    @Operation(summary = "Get a program by ID",
            description = "Retrieves an academic program by its unique ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Program found"),
            @ApiResponse(responseCode = "404", description = "Program not found")
    })
    @GetMapping(ProgramPriv.GET_PROGRAM_PATH)
    @Secured({SuperPrivilege.SUPER_ADMIN, ProgramPriv.GET_PROGRAM})
    public ResponseEntity<ProgramResponse> getProgramById(
            @Parameter(description = "ID of the program to retrieve")
            @PathVariable @Validated @UUID(message = "Format of id provided is invalid")
            @NotBlank(message = "program id must not be blank")
            String id)
    {
        log.debug("Get program by id endpoint reached for request: {}", id);
        return ResponseEntity.ok(programService.getProgramById(id));
    }

    /**
     * Endpoint to update the entire program resource
     */
    @Operation(summary = "Update a program",
            description = "Updates an existing academic program by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Program updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Program not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping(ProgramPriv.UPDATE_PROGRAM_PATH)
    @Secured({SuperPrivilege.SUPER_ADMIN, ProgramPriv.UPDATE_PROGRAM})
    public ResponseEntity<ProgramResponse> updateProgram(
            @Parameter(description = "ID of the program to update")
            @PathVariable @Validated @UUID(message = "Format of id provided is invalid")
            @NotBlank(message = "program id must not be blank") String id,
            @Parameter(description = "Request payload for updating the program")
            @Valid @RequestBody ProgramRequest programRequest
    )
    {
        log.debug("Update program endpoint reached with request: {}", programRequest);
        return ResponseEntity.ok(programService.updateProgram(id, programRequest));
    }

    /**
     * Endpoint to update the deleteStatus of a program
     */
    @Operation(summary = "Update the delete status of a program",
            description = "Updates the delete status of an academic program by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Delete status updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Program not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping(ProgramPriv.UPDATE_PROGRAM_DELETE_STATUS_PATH)
    @Secured({SuperPrivilege.SUPER_ADMIN, ProgramPriv.UPDATE_PROGRAM_DELETE_STATUS})
    public ResponseEntity<Boolean> updateDeleteStatus(
            @Parameter(description = "ID of the program to update")
            @PathVariable @Validated @UUID(message = "Format of id provided is invalid")
            @NotBlank(message = "program id must not be blank") String id,
            @Parameter(description = "New delete status (default: false)")
            @RequestParam(name = "delete-status", required = false, defaultValue = "false") boolean deleteStatus
    )
    {
        log.debug("program type patch-delete endpoint reached for request: {}", id);
        return new ResponseEntity<>(programService.updateDeleteStatus(id, deleteStatus), HttpStatus.OK);
    }

    /**
     * Endpoint to permanently delete a Program entity
     */
    @Operation(summary = "Delete a program",
            description = "Permanently deletes an academic program by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Program deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Program not found")
    })
    @DeleteMapping(ProgramPriv.DELETE_PROGRAM_PATH)
    @Secured({SuperPrivilege.SUPER_ADMIN, ProgramPriv.DELETE_PROGRAM})
    public ResponseEntity<Boolean> deleteProgram(
            @Parameter(description = "ID of the program to delete")
            @Validated @UUID(message = "Format of id provided is invalid")
            @NotBlank(message = "program id must not be blank")
            @PathVariable String id
    )
    {
        log.debug("permanently delete program endpoint reached for request: {}", id);
        return new ResponseEntity<>(programService.deleteProgram(id), HttpStatus.NO_CONTENT);
    }
}