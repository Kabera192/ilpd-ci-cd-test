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
import rw.ac.ilpd.academicservice.service.IntakeApplicationRequiredDocService;
import rw.ac.ilpd.sharedlibrary.dto.application.IntakeApplicationRequiredDocRequest;
import rw.ac.ilpd.sharedlibrary.dto.application.IntakeApplicationRequiredDocResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.util.BulkResponse;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/intake-app-req-docs")
@RequiredArgsConstructor
@Tag(name = "Intake Application Required Document", description = "Endpoints for managing required documents for intake applications")
public class IntakeApplicationRequiredDocController
{

    private final IntakeApplicationRequiredDocService service;

    @Operation(summary = "Get all required documents for intake applications",
            description = "Retrieves a paginated list of required documents for intake applications with sorting options.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Documents retrieved successfully with pagination metadata"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination or sorting parameters")
    })
    @GetMapping
    public ResponseEntity<PagedResponse<IntakeApplicationRequiredDocResponse>> getAll(
            @Parameter(description = "Page number (0-based, default: 0)") @RequestParam(name = "page", defaultValue = "0") int page,
            @Parameter(description = "Number of items per page (default: 10)") @RequestParam(name = "size", defaultValue = "10") int size,
            @Parameter(description = "Sort field (e.g., 'id', default: 'id')") @RequestParam(name = "sort-by", defaultValue = "id") String sortBy,
            @Parameter(description = "Sort direction (asc or desc, default: asc)") @RequestParam(name = "order", defaultValue = "asc") String order
    )
    {
        return ResponseEntity.ok(service.getAll(page, size, sortBy, order));
    }

    @Operation(summary = "Get a required document by ID",
            description = "Retrieves a required document for an intake application by its unique ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Document found"),
            @ApiResponse(responseCode = "404", description = "Document not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<IntakeApplicationRequiredDocResponse> get(@Parameter(description = "UUID of the document to retrieve") @PathVariable UUID id)
    {
        return ResponseEntity.ok(service.get(id));
    }

    @Operation(summary = "Create a new required document for intake application",
            description = "Creates a new required document for an intake application.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Document created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<BulkResponse<IntakeApplicationRequiredDocResponse>> create(
            @Parameter(description = "Request payload for creating a required document")
            @Valid @RequestBody IntakeApplicationRequiredDocRequest request
    )
    {
        return new ResponseEntity<>(service.create(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Update a required document for intake application",
            description = "Updates an existing required document for an intake application by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Document updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Document not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<BulkResponse<IntakeApplicationRequiredDocResponse>> edit(
            @Parameter(description = "UUID of the document to update") @PathVariable UUID id,
            @Parameter(description = "Request payload for updating the document") @Valid @RequestBody IntakeApplicationRequiredDocRequest request
    )
    {
        return ResponseEntity.ok(service.edit(id, request));
    }

    @Operation(summary = "Partially update a required document for intake application",
            description = "Partially updates a required document for an intake application by its ID with provided fields.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Document partially updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid update data"),
            @ApiResponse(responseCode = "404", description = "Document not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<IntakeApplicationRequiredDocResponse> patch(
            @Parameter(description = "UUID of the document to partially update") @PathVariable UUID id,
            @Parameter(description = "Map of fields to update") @RequestBody Map<String, Object> updates
    )
    {
        return ResponseEntity.ok(service.patch(id, updates));
    }

    @Operation(summary = "Delete a required document for intake application",
            description = "Deletes a required document for an intake application by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Document deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Document not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@Parameter(description = "UUID of the document to delete") @PathVariable UUID id)
    {
        return ResponseEntity.ok(service.delete(id));
    }
}