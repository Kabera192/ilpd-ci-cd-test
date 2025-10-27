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
import rw.ac.ilpd.academicservice.service.IntakeApplicationRequiredDocNameService;
import rw.ac.ilpd.sharedlibrary.dto.application.IntakeApplicationRequiredDocNameRequest;
import rw.ac.ilpd.sharedlibrary.dto.application.IntakeApplicationRequiredDocNameResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/intake-app-req-doc-names")
@Tag(name = "Intake Application Required Document Name", description = "Endpoints for managing required document names for intake applications")
public class IntakeApplicationRequiredDocNameController
{
    private final IntakeApplicationRequiredDocNameService service;

    @Operation(summary = "Get all required document names for intake applications",
            description = "Retrieves a paginated list of required document names with sorting options.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Document names retrieved successfully with pagination metadata"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination or sorting parameters")
    })
    @GetMapping
    public ResponseEntity<PagedResponse<IntakeApplicationRequiredDocNameResponse>> getAll(
            @Parameter(description = "Page number (0-based, default: 0)") @RequestParam(name = "page", defaultValue = "0") int page,
            @Parameter(description = "Number of items per page (default: 10)") @RequestParam(name = "size", defaultValue = "10") int size,
            @Parameter(description = "Sort field (e.g., 'id', default: 'id')") @RequestParam(name = "sort-by", defaultValue = "id") String sortBy,
            @Parameter(description = "Sort direction (asc or desc, default: asc)") @RequestParam(name = "order", defaultValue = "asc") String order
    )
    {
        return ResponseEntity.ok(service.getAll(page, size, sortBy, order));
    }

    @Operation(summary = "Get a required document name by ID",
            description = "Retrieves a required document name for an intake application by its unique ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Document name found"),
            @ApiResponse(responseCode = "404", description = "Document name not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<IntakeApplicationRequiredDocNameResponse> get(
            @Parameter(description = "UUID of the document name to retrieve") @PathVariable UUID id
    )
    {
        return ResponseEntity.ok(service.get(id));
    }

    @Operation(summary = "Create a new required document name",
            description = "Creates a new required document name for an intake application.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Document name created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<IntakeApplicationRequiredDocNameResponse> create(
            @Parameter(description = "Request payload for creating a document name") @Valid @RequestBody IntakeApplicationRequiredDocNameRequest request
    )
    {
        return new ResponseEntity<>(service.create(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Update a required document name",
            description = "Updates an existing required document name for an intake application by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Document name updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Document name not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<IntakeApplicationRequiredDocNameResponse> edit(
            @Parameter(description = "UUID of the document name to update") @PathVariable UUID id,
            @Parameter(description = "Request payload for updating the document name") @Valid @RequestBody IntakeApplicationRequiredDocNameRequest request
    )
    {
        return ResponseEntity.ok(service.edit(id, request));
    }

    @Operation(summary = "Delete a required document name",
            description = "Deletes a required document name for an intake application by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Document name deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Document name not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(
            @Parameter(description = "UUID of the document name to delete") @PathVariable UUID id
    )
    {
        return ResponseEntity.ok(service.delete(id));
    }
}