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
import rw.ac.ilpd.academicservice.service.StudentService;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.student.StudentRequest;
import rw.ac.ilpd.sharedlibrary.dto.student.StudentResponse;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
@Tag(name = "Student", description = "Endpoints for managing students")
public class StudentController
{
    private final StudentService studentService;

    @Operation(summary = "Get all students",
            description = "Retrieves a paginated list of students with sorting options.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Students retrieved successfully with pagination metadata"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination or sorting parameters")
    })
    @GetMapping
    public ResponseEntity<PagedResponse<StudentResponse>> getAll(
            @Parameter(description = "Page number (0-based, default: 0)") @RequestParam(name = "page", defaultValue = "0") int page,
            @Parameter(description = "Number of items per page (default: 10)") @RequestParam(name = "size", defaultValue = "10") int size,
            @Parameter(description = "Sort field (e.g., 'id', default: 'id')") @RequestParam(name = "sort-by", defaultValue = "id") String sortBy,
            @Parameter(description = "Sort direction (asc or desc, default: asc)") @RequestParam(name = "order", defaultValue = "asc") String order
    )
    {
        return ResponseEntity.ok(studentService.getAll(page, size, sortBy, order));
    }

    @Operation(summary = "Get a student by ID",
            description = "Retrieves a student by their unique ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Student found"),
            @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> get(
            @Parameter(description = "UUID of the student to retrieve") @PathVariable UUID id
    )
    {
        return ResponseEntity.ok(studentService.get(id));
    }

    @Operation(summary = "Create a new student",
            description = "Creates a new student.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Student created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<StudentResponse> create(
            @Parameter(description = "Request payload for creating a student") @Valid @RequestBody StudentRequest request
    )
    {
        return new ResponseEntity<>(studentService.create(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Update a student",
            description = "Updates an existing student by their ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Student updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Student not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> edit(
            @Parameter(description = "UUID of the student to update") @PathVariable UUID id,
            @Parameter(description = "Request payload for updating the student") @Valid @RequestBody StudentRequest request
    )
    {
        return ResponseEntity.ok(studentService.edit(id, request));
    }

    @Operation(summary = "Partially update a student",
            description = "Partially updates a student by their ID with provided fields.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Student partially updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid update data"),
            @ApiResponse(responseCode = "404", description = "Student not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<StudentResponse> patch(
            @Parameter(description = "UUID of the student to partially update") @PathVariable UUID id,
            @Parameter(description = "Map of fields to update") @RequestBody Map<String, Object> updates
    )
    {
        return ResponseEntity.ok(studentService.patch(id, updates));
    }

    @Operation(summary = "Delete a student",
            description = "Deletes a student by their ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Student deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(
            @Parameter(description = "UUID of the student to delete") @PathVariable UUID id
    )
    {
        return ResponseEntity.ok(studentService.delete(id));
    }
}