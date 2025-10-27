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
import rw.ac.ilpd.academicservice.service.ShortCourseStudentService;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.shortcourse.ShortCourseStudentRequest;
import rw.ac.ilpd.sharedlibrary.dto.shortcourse.ShortCourseStudentResponse;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/short-course-students")
@RequiredArgsConstructor
@Tag(name = "Short Course Student", description = "Endpoints for managing students enrolled in short courses")
public class ShortCourseStudentController
{

    private final ShortCourseStudentService scsService;

    @Operation(summary = "Get all short course students",
            description = "Retrieves a paginated list of students enrolled in short courses with sorting options.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Students retrieved successfully with pagination metadata"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination or sorting parameters")
    })
    @GetMapping
    public ResponseEntity<PagedResponse<ShortCourseStudentResponse>> getAll(
            @Parameter(description = "Page number (0-based, default: 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page (default: 10)") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort field (e.g., 'id', default: 'id')") @RequestParam(name = "sort-by", defaultValue = "id") String sortBy,
            @Parameter(description = "Sort direction (asc or desc, default: asc)") @RequestParam(defaultValue = "asc") String order
    )
    {
        return ResponseEntity.ok(scsService.getAll(page, size, sortBy, order));
    }

    @Operation(summary = "Get a short course student by ID",
            description = "Retrieves a short course student by their unique ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Student found"),
            @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ShortCourseStudentResponse> get(@Parameter(description = "UUID of the student to retrieve") @PathVariable UUID id)
    {
        return ResponseEntity.ok(scsService.get(id));
    }

    @Operation(summary = "Create a new short course student",
            description = "Enrolls a new student in a short course.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Student created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<ShortCourseStudentResponse> create(@Parameter(description = "Request payload for creating a short course student") @Valid @RequestBody ShortCourseStudentRequest request)
    {
        return new ResponseEntity<>(scsService.create(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Update a short course student",
            description = "Updates an existing short course student by their ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Student updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Student not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ShortCourseStudentResponse> edit(
            @Parameter(description = "UUID of the student to update") @PathVariable UUID id,
            @Parameter(description = "Request payload for updating the student") @Valid @RequestBody ShortCourseStudentRequest request
    )
    {
        return ResponseEntity.ok(scsService.edit(id, request));
    }

    @Operation(summary = "Partially update a short course student",
            description = "Partially updates a short course student by their ID with provided fields.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Student partially updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid update data"),
            @ApiResponse(responseCode = "404", description = "Student not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<ShortCourseStudentResponse> patch(
            @Parameter(description = "UUID of the student to partially update") @PathVariable UUID id,
            @Parameter(description = "Map of fields to update") @RequestBody Map<String, Object> updates
    )
    {
        return ResponseEntity.ok(scsService.patch(id, updates));
    }

    @Operation(summary = "Delete a short course student",
            description = "Deletes a short course student by their ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Student deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@Parameter(description = "UUID of the student to delete") @PathVariable UUID id)
    {
        return ResponseEntity.ok(scsService.delete(id));
    }
}