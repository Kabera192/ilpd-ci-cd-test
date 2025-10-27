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
import rw.ac.ilpd.academicservice.service.IntakeStudentRetakeResitCurriculumModuleService;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.intakestudentretakerestcurriculummodule.IntakeStudentRetakeRestCurriculumModuleRequest;
import rw.ac.ilpd.sharedlibrary.dto.intakestudentretakerestcurriculummodule.IntakeStudentRetakeRestCurriculumModuleResponse;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/intake-student-retake-resit-curriculum-modules")
@RequiredArgsConstructor
@Tag(name = "Intake Student Retake/Resit Curriculum Module", description = "Endpoints for managing student retake or resit curriculum modules")
public class IntakeStudentRetakeResitCurriculumModuleController
{
    private final IntakeStudentRetakeResitCurriculumModuleService service;

    @Operation(summary = "Get all retake/resit curriculum modules",
            description = "Retrieves a paginated list of student retake or resit curriculum modules with sorting options.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Modules retrieved successfully with pagination metadata"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination or sorting parameters")
    })
    @GetMapping
    public ResponseEntity<PagedResponse<IntakeStudentRetakeRestCurriculumModuleResponse>> getAll(
            @Parameter(description = "Page number (0-based, default: 0)") @RequestParam(name = "page", defaultValue = "0") int page,
            @Parameter(description = "Number of items per page (default: 10)") @RequestParam(name = "size", defaultValue = "10") int size,
            @Parameter(description = "Sort field (e.g., 'id', default: 'id')") @RequestParam(name = "sort-by", defaultValue = "id") String sortBy,
            @Parameter(description = "Sort direction (asc or desc, default: asc)") @RequestParam(name = "order", defaultValue = "asc") String order
    )
    {
        return ResponseEntity.ok(service.getAll(page, size, sortBy, order));
    }

    @Operation(summary = "Get a retake/resit curriculum module by ID",
            description = "Retrieves a student retake or resit curriculum module by its unique ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Module found"),
            @ApiResponse(responseCode = "404", description = "Module not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<IntakeStudentRetakeRestCurriculumModuleResponse> get(
            @Parameter(description = "UUID of the module to retrieve") @PathVariable UUID id
    )
    {
        return ResponseEntity.ok(service.get(id));
    }

    @Operation(summary = "Create a new retake/resit curriculum module",
            description = "Creates a new student retake or resit curriculum module.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Module created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<IntakeStudentRetakeRestCurriculumModuleResponse> create(
            @Parameter(description = "Request payload for creating a module") @Valid @RequestBody IntakeStudentRetakeRestCurriculumModuleRequest request
    )
    {
        return new ResponseEntity<>(service.create(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Update a retake/resit curriculum module",
            description = "Updates an existing student retake or resit curriculum module by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Module updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Module not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<IntakeStudentRetakeRestCurriculumModuleResponse> edit(
            @Parameter(description = "UUID of the module to update") @PathVariable UUID id,
            @Parameter(description = "Request payload for updating the module") @Valid @RequestBody IntakeStudentRetakeRestCurriculumModuleRequest request
    )
    {
        return ResponseEntity.ok(service.edit(id, request));
    }

    @Operation(summary = "Partially update a retake/resit curriculum module",
            description = "Partially updates a student retake or resit curriculum module by its ID with provided fields.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Module partially updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid update data"),
            @ApiResponse(responseCode = "404", description = "Module not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<IntakeStudentRetakeRestCurriculumModuleResponse> patch(
            @Parameter(description = "UUID of the module to partially update") @PathVariable UUID id,
            @Parameter(description = "Map of fields to update") @RequestBody Map<String, Object> updates
    )
    {
        return ResponseEntity.ok(service.patch(id, updates));
    }

    @Operation(summary = "Delete a retake/resit curriculum module",
            description = "Deletes a student retake or resit curriculum module by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Module deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Module not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(
            @Parameter(description = "UUID of the module to delete") @PathVariable UUID id
    )
    {
        return ResponseEntity.ok(service.delete(id));
    }
}