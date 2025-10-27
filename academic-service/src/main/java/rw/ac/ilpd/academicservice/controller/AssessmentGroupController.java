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
import rw.ac.ilpd.academicservice.service.AssessmentGroupService;
import rw.ac.ilpd.sharedlibrary.dto.assessmentgroup.AssessmentGroupRequest;
import rw.ac.ilpd.sharedlibrary.dto.assessmentgroup.AssessmentGroupResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/assessment-groups")
@RequiredArgsConstructor
@Tag(name = "Assessment Group", description = "Endpoints for managing assessment groups")
public class AssessmentGroupController
{

    private final AssessmentGroupService service;

    @Operation(summary = "Get all assessment groups",
            description = "Retrieves a paginated list of assessment groups with sorting options.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Assessment groups retrieved successfully with pagination metadata"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination or sorting parameters")
    })
    @GetMapping
    public ResponseEntity<PagedResponse<AssessmentGroupResponse>> getAll(
            @Parameter(description = "Page number (0-based, default: 0)") @RequestParam(name = "page", defaultValue = "0") int page,
            @Parameter(description = "Number of items per page (default: 10)") @RequestParam(name = "size", defaultValue = "10") int size,
            @Parameter(description = "Sort field (e.g., 'id', default: 'id')") @RequestParam(name = "sort-by", defaultValue = "id") String sortBy,
            @Parameter(description = "Sort direction (asc or desc, default: asc)") @RequestParam(name = "order", defaultValue = "asc") String order
    )
    {
        return ResponseEntity.ok(service.getAll(page, size, sortBy, order));
    }

    @Operation(summary = "Get an assessment group by ID",
            description = "Retrieves an assessment group by its unique ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Assessment group found"),
            @ApiResponse(responseCode = "404", description = "Assessment group not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AssessmentGroupResponse> get(@Parameter(description = "UUID of the assessment group to retrieve") @PathVariable UUID id)
    {
        return ResponseEntity.ok(service.get(id));
    }

    @Operation(summary = "Create a new assessment group",
            description = "Creates a new assessment group.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Assessment group created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<AssessmentGroupResponse> create(
            @Parameter(description = "Request payload for creating an assessment group") @Valid @RequestBody AssessmentGroupRequest request
    )
    {
        return new ResponseEntity<>(service.create(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an assessment group",
            description = "Updates an existing assessment group by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Assessment group updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Assessment group not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AssessmentGroupResponse> edit(
            @Parameter(description = "UUID of the assessment group to update") @PathVariable UUID id,
            @Parameter(description = "Request payload for updating the assessment group") @Valid @RequestBody AssessmentGroupRequest request
    )
    {
        return ResponseEntity.ok(service.edit(id, request));
    }

    @Operation(summary = "Partially update an assessment group",
            description = "Partially updates an assessment group by its ID with provided fields.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Assessment group partially updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid update data"),
            @ApiResponse(responseCode = "404", description = "Assessment group not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<AssessmentGroupResponse> patch(
            @Parameter(description = "UUID of the assessment group to partially update") @PathVariable UUID id,
            @Parameter(description = "Map of fields to update") @RequestBody Map<String, Object> updates
    )
    {
        return ResponseEntity.ok(service.patch(id, updates));
    }

    @Operation(summary = "Delete an assessment group",
            description = "Deletes an assessment group by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Assessment group deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Assessment group not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@Parameter(description = "UUID of the assessment group to delete") @PathVariable UUID id)
    {
        return ResponseEntity.ok(service.delete(id));
    }
}