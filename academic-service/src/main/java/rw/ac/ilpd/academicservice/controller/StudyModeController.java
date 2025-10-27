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
import rw.ac.ilpd.academicservice.service.StudyModeService;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.studymode.StudyModeRequest;
import rw.ac.ilpd.sharedlibrary.dto.studymode.StudyModeResponse;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/study-modes")
@RequiredArgsConstructor
@Tag(name = "Study Mode", description = "Endpoints for managing study modes")
public class StudyModeController
{
    private final StudyModeService service;

    @Operation(summary = "Get all study modes",
            description = "Retrieves a paginated list of study modes with sorting options.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Study modes retrieved successfully with pagination metadata"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination or sorting parameters")
    })
    @GetMapping
    public ResponseEntity<PagedResponse<StudyModeResponse>> getAll(
            @Parameter(description = "Page number (0-based, default: 0)") @RequestParam(name = "page", defaultValue = "0") int page,
            @Parameter(description = "Number of items per page (default: 10)") @RequestParam(name = "size", defaultValue = "10") int size,
            @Parameter(description = "Sort field (e.g., 'id', default: 'id')") @RequestParam(name = "sort-by", defaultValue = "id") String sortBy,
            @Parameter(description = "Sort direction (asc or desc, default: asc)") @RequestParam(name = "order", defaultValue = "asc") String order
    )
    {
        return ResponseEntity.ok(service.getAll(page, size, sortBy, order));
    }

    @Operation(summary = "Get a study mode by ID",
            description = "Retrieves a study mode by its unique ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Study mode found"),
            @ApiResponse(responseCode = "404", description = "Study mode not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<StudyModeResponse> get(@Parameter(description = "UUID of the study mode to retrieve") @PathVariable UUID id)
    {
        return ResponseEntity.ok(service.get(id));
    }

    @Operation(summary = "Create a new study mode",
            description = "Creates a new study mode.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Study mode created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<StudyModeResponse> create(
            @Parameter(description = "Request payload for creating a study mode") @Valid @RequestBody StudyModeRequest request
    )
    {
        return new ResponseEntity<>(service.create(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Update a study mode",
            description = "Updates an existing study mode by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Study mode updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Study mode not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<StudyModeResponse> edit(
            @Parameter(description = "UUID of the study mode to update") @PathVariable UUID id,
            @Parameter(description = "Request payload for updating the study mode") @Valid @RequestBody StudyModeRequest request
    )
    {
        return ResponseEntity.ok(service.edit(id, request));
    }

    @Operation(summary = "Partially update a study mode",
            description = "Partially updates a study mode by its ID with provided fields.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Study mode partially updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid update data"),
            @ApiResponse(responseCode = "404", description = "Study mode not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<StudyModeResponse> patch(
            @Parameter(description = "UUID of the study mode to partially update") @PathVariable UUID id,
            @Parameter(description = "Map of fields to update") @RequestBody Map<String, Object> updates
    )
    {
        return ResponseEntity.ok(service.patch(id, updates));
    }

    @Operation(summary = "Delete a study mode",
            description = "Deletes a study mode by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Study mode deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Study mode not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@Parameter(description = "UUID of the study mode to delete") @PathVariable UUID id)
    {
        return ResponseEntity.ok(service.delete(id));
    }
}