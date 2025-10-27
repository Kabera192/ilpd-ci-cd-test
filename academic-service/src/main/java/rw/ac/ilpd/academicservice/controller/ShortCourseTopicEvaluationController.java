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
import rw.ac.ilpd.academicservice.service.ShortCourseTopicEvaluationService;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.shortcoursetopicevaluation.ShortCourseTopicEvaluationRequest;
import rw.ac.ilpd.sharedlibrary.dto.shortcoursetopicevaluation.ShortCourseTopicEvaluationResponse;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/short-course-topic-evaluations")
@RequiredArgsConstructor
@Tag(name = "Short Course Topic Evaluation", description = "Endpoints for managing evaluations of short course topics")
public class ShortCourseTopicEvaluationController
{
    private final ShortCourseTopicEvaluationService evaluationService;

    @Operation(summary = "Get all short course topic evaluations",
            description = "Retrieves a paginated list of short course topic evaluations with sorting options.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Evaluations retrieved successfully with pagination metadata"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination or sorting parameters")
    })
    @GetMapping
    public ResponseEntity<PagedResponse<ShortCourseTopicEvaluationResponse>> getAll(
            @Parameter(description = "Page number (0-based, default: 0)") @RequestParam(name = "page", defaultValue = "0") int page,
            @Parameter(description = "Number of items per page (default: 10)") @RequestParam(name = "size", defaultValue = "10") int size,
            @Parameter(description = "Sort field (e.g., 'id', default: 'id')") @RequestParam(name = "sort-by", defaultValue = "id") String sortBy,
            @Parameter(description = "Sort direction (asc or desc, default: asc)") @RequestParam(name = "order", defaultValue = "asc") String order
    )
    {
        return ResponseEntity.ok(evaluationService.getAll(page, size, sortBy, order));
    }

    @Operation(summary = "Get a short course topic evaluation by ID",
            description = "Retrieves a short course topic evaluation by its unique ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Evaluation found"),
            @ApiResponse(responseCode = "404", description = "Evaluation not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ShortCourseTopicEvaluationResponse> get(
            @Parameter(description = "UUID of the evaluation to retrieve") @PathVariable UUID id
    )
    {
        return ResponseEntity.ok(evaluationService.get(id));
    }

    @Operation(summary = "Create a new short course topic evaluation",
            description = "Creates a new evaluation for a short course topic.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Evaluation created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<ShortCourseTopicEvaluationResponse> create(
            @Parameter(description = "Request payload for creating an evaluation") @Valid @RequestBody ShortCourseTopicEvaluationRequest request
    )
    {
        return new ResponseEntity<>(evaluationService.create(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Update a short course topic evaluation",
            description = "Updates an existing short course topic evaluation by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Evaluation updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Evaluation not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ShortCourseTopicEvaluationResponse> edit(
            @Parameter(description = "UUID of the evaluation to update") @PathVariable UUID id,
            @Parameter(description = "Request payload for updating the evaluation") @Valid @RequestBody ShortCourseTopicEvaluationRequest request
    )
    {
        return ResponseEntity.ok(evaluationService.edit(id, request));
    }

    @Operation(summary = "Partially update a short course topic evaluation",
            description = "Partially updates a short course topic evaluation by its ID with provided fields.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Evaluation partially updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid update data"),
            @ApiResponse(responseCode = "404", description = "Evaluation not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<ShortCourseTopicEvaluationResponse> patch(
            @Parameter(description = "UUID of the evaluation to partially update") @PathVariable UUID id,
            @Parameter(description = "Map of fields to update") @RequestBody Map<String, Object> updates
    )
    {
        return ResponseEntity.ok(evaluationService.patch(id, updates));
    }

    @Operation(summary = "Delete a short course topic evaluation",
            description = "Deletes a short course topic evaluation by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Evaluation deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Evaluation not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(
            @Parameter(description = "UUID of the evaluation to delete") @PathVariable UUID id
    )
    {
        return ResponseEntity.ok(evaluationService.delete(id));
    }
}