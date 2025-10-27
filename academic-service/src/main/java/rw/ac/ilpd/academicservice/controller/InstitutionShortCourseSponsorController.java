package rw.ac.ilpd.academicservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.academicservice.service.InstitutionShortCourseSponsorService;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.institutionshortcoursesponsor.InstitutionShortCourseSponsorRequest;
import rw.ac.ilpd.sharedlibrary.dto.institutionshortcoursesponsor.InstitutionShortCourseSponsorResponse;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/institution-short-course-sponsors")
@RequiredArgsConstructor
@Tag(name = "Institution Short Course Sponsor",
        description = "Endpoints for managing sponsors of institution short courses")
public class InstitutionShortCourseSponsorController
{
    private final InstitutionShortCourseSponsorService sponsorService;

    @Operation(summary = "Get all institution short course sponsors",
            description = "Retrieves a paginated list of institution short course sponsors with sorting options.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sponsors retrieved successfully with pagination metadata"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination or sorting parameters")
    })
    @GetMapping
    public ResponseEntity<PagedResponse<InstitutionShortCourseSponsorResponse>> getAll(
            @Parameter(description = "Page number (0-based, default: 0)")
            @RequestParam(name = "page", defaultValue = "0") int page,
            @Parameter(description = "Number of items per page (default: 10)")
            @RequestParam(name = "size", defaultValue = "10") int size,
            @Parameter(description = "Sort field (e.g., 'name', default: 'name')")
            @RequestParam(name = "sort-by", defaultValue = "name") String sortBy,
            @Parameter(description = "Sort direction (asc or desc, default: asc)")
            @RequestParam(name = "order", defaultValue = "asc") String order
    )
    {
        return ResponseEntity.ok(sponsorService.getAll(page, size, sortBy, order));
    }

    @Operation(summary = "Get an institution short course sponsor by ID",
            description = "Retrieves an institution short course sponsor by its unique ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sponsor found"),
            @ApiResponse(responseCode = "404", description = "Sponsor not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<InstitutionShortCourseSponsorResponse> get(
            @Parameter(description = "UUID of the sponsor to retrieve") @PathVariable UUID id
    )
    {
        return ResponseEntity.ok(sponsorService.get(id));
    }

    @Operation(summary = "Create a new institution short course sponsor",
            description = "Creates a new institution short course sponsor.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Sponsor created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<InstitutionShortCourseSponsorResponse> create(
            @Parameter(description = "Request payload for creating a sponsor") @Valid @RequestBody InstitutionShortCourseSponsorRequest request
    )
    {
        return new ResponseEntity<>(sponsorService.create(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an institution short course sponsor",
            description = "Updates an existing institution short course sponsor by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sponsor updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Sponsor not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<InstitutionShortCourseSponsorResponse> edit(
            @Parameter(description = "UUID of the sponsor to update") @PathVariable UUID id,
            @Parameter(description = "Request payload for updating the sponsor") @Valid @RequestBody InstitutionShortCourseSponsorRequest request
    )
    {
        return ResponseEntity.ok(sponsorService.edit(id, request));
    }

    @Operation(summary = "Partially update an institution short course sponsor",
            description = "Partially updates an institution short course sponsor by its ID with provided fields.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sponsor partially updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid update data"),
            @ApiResponse(responseCode = "404", description = "Sponsor not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<InstitutionShortCourseSponsorResponse> patch(
            @Parameter(description = "UUID of the sponsor to partially update") @PathVariable UUID id,
            @Parameter(description = "Map of fields to update") @RequestBody Map<String, Object> updates
    )
    {
        return ResponseEntity.ok(sponsorService.patch(id, updates));
    }

    @Operation(summary = "Delete an institution short course sponsor",
            description = "Deletes an institution short course sponsor by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sponsor deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Sponsor not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(
            @Parameter(description = "UUID of the sponsor to delete") @PathVariable UUID id
    )
    {
        return ResponseEntity.ok(sponsorService.delete(id));
    }
}