package rw.ac.ilpd.academicservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.academicservice.service.ProgramLocationService;
import rw.ac.ilpd.sharedlibrary.dto.program.ProgramResponse;
import rw.ac.ilpd.sharedlibrary.dto.programlocation.ProgramLocationRequest;
import rw.ac.ilpd.sharedlibrary.dto.programlocation.ProgramLocationResponse;

/**
 * REST controller for managing program-location associations.
 * Provides endpoints for creating, retrieving, updating, and deleting program-location associations.
 */
@RestController
@RequestMapping("/program-locations")
@RequiredArgsConstructor
@Tag(name = "Program Locations", description = "Endpoints for managing program-location associations")
public class ProgramLocationController
{
    private final ProgramLocationService programLocationService;

    /**
     * Creates a new program-location association.
     *
     * @param request The ProgramLocationRequest containing program and location IDs.
     * @return ResponseEntity with the created ProgramLocationResponse.
     */
    @Operation(summary = "Create a new program-location association",
            description = "Associates a program with a location using their respective IDs.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Program-location association created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Program or location not found"),
            @ApiResponse(responseCode = "409", description = "Program-location association already exists")
    })
    @PostMapping
    public ResponseEntity<ProgramLocationResponse> createProgramLocation(
            @Validated @RequestBody ProgramLocationRequest request)
    {
        ProgramLocationResponse response = programLocationService.createProgramLocation(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Retrieves all program-location associations with pagination and sorting.
     * Pagination parameters (page, size, sortBy, order).
     * @return ResponseEntity with a paginated list of ProgramLocationResponse.
     */
    @Operation(summary = "Get all program-location associations",
            description = "Retrieves a paginated and sorted list of all program-location associations.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Program-location associations retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination or sorting parameters")
    })
    @GetMapping
    public ResponseEntity<Page<ProgramLocationResponse>> getAllProgramLocations(
            @Parameter(description = "Page number (0-based)")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Field to sort by (e.g., id, deletedStatus)")
            @RequestParam(name = "sort-by", defaultValue = "id") String sortBy,
            @Parameter(description = "Sort direction (asc or desc)")
            @RequestParam(defaultValue = "asc") String order)
    {
        Page<ProgramLocationResponse> response = programLocationService.findAllProgramLocations(page,
                                                                                size, sortBy, order);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Retrieves a program-location association by its ID.
     *
     * @param id The ID of the program-location association.
     * @return ResponseEntity with the ProgramLocationResponse.
     */
    @Operation(summary = "Get a program-location association by ID",
            description = "Retrieves the details of a program-location association by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Program-location association retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid ID format"),
            @ApiResponse(responseCode = "404", description = "Program-location association not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProgramLocationResponse> getProgramLocationById(@PathVariable String id)
    {
        ProgramLocationResponse response = programLocationService.getProgramLocationById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Retrieves programs by location.
     *
     * @param id The ID of the program-location association.
     * @return ResponseEntity with the ProgramLocationResponse.
     */
    @Operation(summary = "Get a program-location association by ID",
            description = "Retrieves the details of a program-location association by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Program-location association retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid ID format"),
            @ApiResponse(responseCode = "404", description = "Program-location association not found")
    })
    @GetMapping("/{id}/programs")
    public ResponseEntity<Page<ProgramResponse>> getProgramsByLocation(
                            @Parameter(description = "id of the program-location of interest")
                            @PathVariable String id,
                            @Parameter(description = "Page number (0-based)")
                            @RequestParam(defaultValue = "0") int page,
                            @Parameter(description = "Number of items per page")
                            @RequestParam(defaultValue = "10") int size,
                            @Parameter(description = "Field to sort by (e.g., id, deletedStatus)")
                            @RequestParam(name = "sort-by", defaultValue = "id") String sortBy,
                            @Parameter(description = "Sort direction (asc or desc)")
                            @RequestParam(defaultValue = "asc") String order)
    {
        Page<ProgramResponse> response = programLocationService.getProgramsByLocation(id, page, size, sortBy, order);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Updates an existing program-location association.
     *
     * @param id      The ID of the program-location association to update.
     * @param request The ProgramLocationRequest containing the new program and location IDs.
     * @return ResponseEntity with the updated ProgramLocationResponse.
     */
    @Operation(summary = "Update a program-location association",
            description = "Updates the program or location ID of an existing program-location association.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Program-location association updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data or ID format"),
            @ApiResponse(responseCode = "404", description = "Program-location association or program not found"),
            @ApiResponse(responseCode = "409", description = "New program-location association already exists")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProgramLocationResponse> updateProgramLocation(
            @PathVariable String id, @Validated @RequestBody ProgramLocationRequest request)
    {
        ProgramLocationResponse response = programLocationService.updateProgramLocation(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Deletes a program-location association by its ID.
     *
     * @param id The ID of the program-location association to delete.
     * @return ResponseEntity with no content.
     */
    @Operation(summary = "Delete a program-location association",
            description = "Deletes a program-location association by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Program-location association deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid ID format"),
            @ApiResponse(responseCode = "404", description = "Program-location association not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgramLocation(@PathVariable String id)
    {
        programLocationService.deleteProgramLocation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}