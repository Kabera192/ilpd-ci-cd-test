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
import rw.ac.ilpd.academicservice.service.StudyModeSessionService;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.studymodesession.StudyModeSessionRequest;
import rw.ac.ilpd.sharedlibrary.dto.studymodesession.StudyModeSessionResponse;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/study-mode-sessions")
@RequiredArgsConstructor
@Tag(name = "Study Mode Session", description = "Endpoints for managing study mode sessions")
public class StudyModeSessionController
{

    private final StudyModeSessionService service;

    @Operation(summary = "Get all study mode sessions",
            description = "Retrieves a paginated list of study mode sessions with sorting options.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Study mode sessions retrieved successfully with pagination metadata"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination or sorting parameters")
    })
    @GetMapping
    public ResponseEntity<PagedResponse<StudyModeSessionResponse>> getAll(
            @Parameter(description = "Page number (0-based, default: 0)") @RequestParam(name = "page", defaultValue = "0") int page,
            @Parameter(description = "Number of items per page (default: 10)") @RequestParam(name = "size", defaultValue = "10") int size,
            @Parameter(description = "Sort field (e.g., 'id', default: 'id')") @RequestParam(name = "sort-by", defaultValue = "id") String sortBy,
            @Parameter(description = "Sort direction (asc or desc, default: asc)") @RequestParam(name = "order", defaultValue = "asc") String order
    )
    {
        return ResponseEntity.ok(service.getAll(page, size, sortBy, order));
    }

    @Operation(summary = "Get a study mode session by ID",
            description = "Retrieves a study mode session by its unique ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Study mode session found"),
            @ApiResponse(responseCode = "404", description = "Study mode session not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<StudyModeSessionResponse> get(@Parameter(description = "UUID of the study mode session to retrieve") @PathVariable UUID id)
    {
        return ResponseEntity.ok(service.get(id));
    }

    @Operation(summary = "Create a new study mode session",
            description = "Creates a new study mode session.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Study mode session created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<StudyModeSessionResponse> create(
            @Parameter(description = "Request payload for creating a study mode session") @Valid @RequestBody StudyModeSessionRequest request
    )
    {
        return new ResponseEntity<>(service.create(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Update a study mode session",
            description = "Updates an existing study mode session by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Study mode session updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Study mode session not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<StudyModeSessionResponse> edit(
            @Parameter(description = "UUID of the study mode session to update") @PathVariable UUID id,
            @Parameter(description = "Request payload for updating the study mode session") @Valid @RequestBody StudyModeSessionRequest request
    )
    {
        return ResponseEntity.ok(service.edit(id, request));
    }

    @Operation(summary = "Partially update a study mode session",
            description = "Partially updates a study mode session by its ID with provided fields.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Study mode session partially updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid update data"),
            @ApiResponse(responseCode = "404", description = "Study mode session not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<StudyModeSessionResponse> patch(
            @Parameter(description = "UUID of the study mode session to partially update") @PathVariable UUID id,
            @Parameter(description = "Map of fields to update") @RequestBody Map<String, Object> updates
    )
    {
        return ResponseEntity.ok(service.patch(id, updates));
    }

    @Operation(summary = "Delete a study mode session",
            description = "Deletes a study mode session by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Study mode session deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Study mode session not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@Parameter(description = "UUID of the study mode session to delete") @PathVariable UUID id)
    {
        return ResponseEntity.ok(service.delete(id));
    }
}