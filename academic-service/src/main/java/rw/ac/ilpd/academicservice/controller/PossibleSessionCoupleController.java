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
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.academicservice.service.PossibleSessionCoupleService;
import rw.ac.ilpd.sharedlibrary.dto.possiblesessioncouple.PossibleSessionCoupleRequest;
import rw.ac.ilpd.sharedlibrary.dto.possiblesessioncouple.PossibleSessionCoupleResponse;

import jakarta.validation.Valid;

import java.util.UUID;

/**
 * REST controller for managing PossibleSessionCouple entities.
 */
@RestController
@RequestMapping("/possible-session-couples")
@RequiredArgsConstructor
@Tag(name = "Possible Session Couple", description = "Endpoints for managing possible session couples")
public class PossibleSessionCoupleController
{
    private final PossibleSessionCoupleService possibleSessionCoupleService;

    @PostMapping
    @Operation(summary = "Create a new possible session couple", description = "Creates a new possible session couple with two session IDs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Possible session couple created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<PossibleSessionCoupleResponse> createPossibleSessionCouple(
            @Valid @RequestBody PossibleSessionCoupleRequest request)
    {
        PossibleSessionCoupleResponse response = possibleSessionCoupleService.createPossibleSessionCouple(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a possible session couple by ID", description = "Retrieves a possible session couple by its unique UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Possible session couple found"),
            @ApiResponse(responseCode = "404", description = "Possible session couple not found or deleted")
    })
    public ResponseEntity<PossibleSessionCoupleResponse> getPossibleSessionCoupleById(
            @Parameter(description = "UUID of the possible session couple") @PathVariable UUID id)
    {
        PossibleSessionCoupleResponse response = possibleSessionCoupleService.getPossibleSessionCoupleById(id);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @GetMapping
    @Operation(summary = "Get all possible session couples", description = "Retrieves a paginated and sorted list of all non-deleted possible session couples")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of possible session couples retrieved successfully")
    })
    public ResponseEntity<Page<PossibleSessionCoupleResponse>> getAllPossibleSessionCouples(
            @Parameter(description = "Page number (0-based)")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Field to sort by (e.g., id, deletedStatus)")
            @RequestParam(name = "sort-by", defaultValue = "id") String sortBy,
            @Parameter(description = "Sort direction (asc or desc)")
            @RequestParam(defaultValue = "asc") String order)
    {
        Page<PossibleSessionCoupleResponse> response = possibleSessionCoupleService.getAllPossibleSessionCouples(page, size, sortBy, order);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a possible session couple", description = "Updates an existing possible session couple by its UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Possible session couple updated successfully"),
            @ApiResponse(responseCode = "404", description = "Possible session couple not found or deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<PossibleSessionCoupleResponse> updatePossibleSessionCouple(
            @Parameter(description = "UUID of the possible session couple") @PathVariable UUID id,
            @Valid @RequestBody PossibleSessionCoupleRequest request)
    {
        PossibleSessionCoupleResponse response = possibleSessionCoupleService.updatePossibleSessionCouple(id, request);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Soft delete a possible session couple", description = "Soft deletes a possible session couple by setting its deletedStatus to true")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Possible session couple soft deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Possible session couple not found or already deleted")
    })
    public ResponseEntity<Void> deletePossibleSessionCouple(
            @Parameter(description = "UUID of the possible session couple") @PathVariable UUID id)
    {
        boolean deleted = possibleSessionCoupleService.deletePossibleSessionCouple(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}