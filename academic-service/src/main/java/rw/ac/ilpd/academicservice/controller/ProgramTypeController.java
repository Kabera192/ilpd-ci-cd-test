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
import rw.ac.ilpd.academicservice.service.ProgramTypeService;
import rw.ac.ilpd.mis.shared.config.privilege.academic.ProgramTypePriv;
import rw.ac.ilpd.mis.shared.config.privilege.auth.SuperPrivilege;
import rw.ac.ilpd.mis.shared.security.Secured;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.programtype.ProgramTypeRequest;
import rw.ac.ilpd.sharedlibrary.dto.programtype.ProgramTypeResponse;

/**
 * Controller that handles all endpoints that deal with the ProgramType resource.
 */
@RestController
@RequestMapping(ProgramTypePriv.PROGRAM_TYPE_PATH)
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Program Type", description = "Endpoints for managing program types")
@SecurityRequirement(name = "Bearer Authentication")
public class ProgramTypeController
{
    private final ProgramTypeService programTypeService;

    /**
     * Endpoint to create a new Program
     */
    @Operation(summary = "Create a new program type",
            description = "Creates a new program type.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Program type created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Secured({SuperPrivilege.SUPER_ADMIN, ProgramTypePriv.CREATE_PROGRAM_TYPE})
    @PostMapping(ProgramTypePriv.CREATE_PROGRAM_TYPE_PATH)
    public ResponseEntity<ProgramTypeResponse> createProgramType(@Parameter(description = "Request payload for creating a program type") @Valid @RequestBody ProgramTypeRequest programTypeRequest)
    {
        log.debug("Create program type endpoint reached for request: {}", programTypeRequest);
        return ResponseEntity.ok(programTypeService.createProgramType(programTypeRequest));
    }

    /**
     * Endpoint to get all program types
     */
    @Operation(summary = "Get all program types",
            description = "Retrieves a paginated list of program types with sorting options.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Program types retrieved successfully with pagination metadata"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination or sorting parameters")
    })
    @Secured({SuperPrivilege.SUPER_ADMIN, ProgramTypePriv.GET_ALL_PROGRAM_TYPES})
    @GetMapping(ProgramTypePriv.GET_ALL_PROGRAM_TYPES_PATH)
    public ResponseEntity<PagedResponse<ProgramTypeResponse>> getAllProgramTypes(
            @Parameter(description = "Page number (0-based, default: 0)") @RequestParam(required = false, defaultValue = "0") int page,
            @Parameter(description = "Number of items per page (default: 10)") @RequestParam(required = false, defaultValue = "10") int size,
            @Parameter(description = "Sort field (e.g., 'name', default: 'name')") @RequestParam(name = "sort-by", required = false, defaultValue = "name") String sortBy,
            @Parameter(description = "Sort direction (asc or desc, default: asc)") @RequestParam(required = false, defaultValue = "asc") String order
    )
    {
        log.info("Get all program types endpoint reached");
        return ResponseEntity.ok(programTypeService.getAllProgramTypes(page, size, sortBy, order));
    }

    /**
     * Endpoint to get a program type by id
     */
    @Operation(summary = "Get a program type by ID",
            description = "Retrieves a program type by its unique ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Program type found"),
            @ApiResponse(responseCode = "404", description = "Program type not found")
    })
    @Secured({SuperPrivilege.SUPER_ADMIN, ProgramTypePriv.GET_PROGRAM_TYPE})
    @GetMapping(ProgramTypePriv.GET_PROGRAM_TYPE_PATH)
    public ResponseEntity<ProgramTypeResponse> getProgramTypeById(
            @Parameter(description = "ID of the program type to retrieve") @Valid @NotNull(message = "program id must not be null") @NotBlank(message = "program id must not be blank") @PathVariable String id)
    {
        log.debug("Get program by id endpoint reached for request: {}", id);
        return ResponseEntity.ok(programTypeService.getProgramTypeById(id));
    }

    /**
     * Endpoint to update the entire program type resource
     */
    @Operation(summary = "Update a program type",
            description = "Updates an existing program type by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Program type updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Program type not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Secured({SuperPrivilege.SUPER_ADMIN, ProgramTypePriv.UPDATE_PROGRAM_TYPE})
    @PutMapping(ProgramTypePriv.UPDATE_PROGRAM_TYPE_PATH)
    public ResponseEntity<ProgramTypeResponse> updateProgramType(
            @Parameter(description = "ID of the program type to update") @Valid @NotNull(message = "program id must not be null") @NotBlank(message = "program id must not be blank") @PathVariable String id,
            @Parameter(description = "Request payload for updating the program type") @Valid @RequestBody ProgramTypeRequest programTypeRequest
    )
    {
        log.debug("Update program type endpoint reached with request: {}", programTypeRequest);
        return ResponseEntity.ok(programTypeService.updateProgramType(id, programTypeRequest));
    }

    /**
     * Endpoint to update the deleteStatus of a program type
     */
    @Operation(summary = "Update the delete status of a program type",
            description = "Updates the delete status of a program type by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Delete status updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Program type not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Secured({SuperPrivilege.SUPER_ADMIN, ProgramTypePriv.UPDATE_PROGRAM_TYPE_DELETE_STATUS})
    @PatchMapping(ProgramTypePriv.UPDATE_PROGRAM_TYPE_DELETE_STATUS_PATH)
    public ResponseEntity<Boolean> updateDeleteStatus(
            @Parameter(description = "ID of the program type to update")
            @Valid @NotNull(message = "program id must not be null")
            @NotBlank(message = "program id must not be blank") @PathVariable String id,
            @Parameter(description = "New delete status (default: false)")
            @RequestParam(required = false, defaultValue = "false") boolean deleteStatus
    )
    {
        log.debug("program type patch-delete endpoint reached for request: {}", id);
        return new ResponseEntity<>(programTypeService.updateDeleteStatus(id, deleteStatus), HttpStatus.OK);
    }

    /**
     * Endpoint to permanently delete a ProgramType entity
     */
    @Operation(summary = "Delete a program type",
            description = "Permanently deletes a program type by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Program type deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Program type not found")
    })
    @Secured({SuperPrivilege.SUPER_ADMIN, ProgramTypePriv.DELETE_PROGRAM_TYPE})
    @DeleteMapping(ProgramTypePriv.DELETE_PROGRAM_TYPE_PATH)
    public ResponseEntity<Boolean> deleteProgramType(
            @Parameter(description = "ID of the program type to delete") @Valid @NotNull(message = "program id must not be null") @NotBlank(message = "program id must not be blank") @PathVariable String id
    )
    {
        log.debug("permanently delete program type endpoint reached for request: {}", id);
        return new ResponseEntity<>(programTypeService.deleteProgramType(id), HttpStatus.OK);
    }
}