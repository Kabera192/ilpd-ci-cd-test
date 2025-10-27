package rw.ac.ilpd.academicservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.sharedlibrary.dto.intakestudent.IntakeStudentRequest;
import rw.ac.ilpd.sharedlibrary.dto.intakestudent.IntakeStudentResponse;
import rw.ac.ilpd.academicservice.service.IntakeStudentService;

/**
 * REST controller for managing intake-student associations.
 * Provides endpoints for creating, retrieving, updating, and deleting intake-student associations.
 */
@RestController
@RequestMapping("/intake-students")
@RequiredArgsConstructor
@Tag(name = "Intake Students", description = "Endpoints for managing student-intake associations")
public class IntakeStudentController
{

    private final IntakeStudentService intakeStudentService;

    /**
     * Creates a new intake-student association.
     *
     * @param request The IntakeStudentRequest containing student and intake IDs and other details.
     * @return ResponseEntity with the created IntakeStudentResponse.
     */
    @Operation(summary = "Create a new intake-student association",
            description = "Associates a student with an intake, including enforcement and status details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Intake-student association created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data or invalid status"),
            @ApiResponse(responseCode = "404", description = "Student or intake not found"),
            @ApiResponse(responseCode = "409", description = "Intake-student association already exists"),
            @ApiResponse(responseCode = "400", description = "Intake has reached maximum student capacity")
    })
    @PostMapping
    public ResponseEntity<IntakeStudentResponse> createIntakeStudent(
            @Validated @RequestBody IntakeStudentRequest request)
    {
        IntakeStudentResponse response = intakeStudentService.createIntakeStudent(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Retrieves an intake-student association by its ID.
     *
     * @param id The ID of the intake-student association.
     * @return ResponseEntity with the IntakeStudentResponse.
     */
    @Operation(summary = "Get an intake-student association by ID",
            description = "Retrieves the details of an intake-student association by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Intake-student association retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid ID format"),
            @ApiResponse(responseCode = "404", description = "Intake-student association not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<IntakeStudentResponse> getIntakeStudentById(@PathVariable String id)
    {
        IntakeStudentResponse response = intakeStudentService.getIntakeStudentById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Retrieves all intake-student associations with pagination and sorting.
     *
     * Pagination parameters (page, size, sortBy, order).
     * @return ResponseEntity with a paginated list of IntakeStudentResponse.
     */
    @Operation(summary = "Get all intake-student associations",
            description = "Retrieves a paginated and sorted list of all intake-student associations.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Intake-student associations retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination or sorting parameters")
    })
    @GetMapping
    public ResponseEntity<Page<IntakeStudentResponse>> getAllIntakeStudents(
            @Parameter(description = "Page number (0-based)")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Field to sort by (e.g., id, deletedStatus)")
            @RequestParam(name = "sort-by", defaultValue = "id") String sortBy,
            @Parameter(description = "Sort direction (asc or desc)")
            @RequestParam(defaultValue = "asc") String order)
    {
        Page<IntakeStudentResponse> response = intakeStudentService.findAllIntakeStudents(page, size, sortBy, order);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Updates an existing intake-student association.
     *
     * @param id      The ID of the intake-student association to update.
     * @param request The IntakeStudentRequest containing updated details.
     * @return ResponseEntity with the updated IntakeStudentResponse.
     */
    @Operation(summary = "Update an intake-student association",
            description = "Updates the details of an existing intake-student association.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Intake-student association updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data, ID format, or status"),
            @ApiResponse(responseCode = "404", description = "Intake-student association, student, or intake not found"),
            @ApiResponse(responseCode = "409", description = "New intake-student association already exists"),
            @ApiResponse(responseCode = "400", description = "Intake has reached maximum student capacity")
    })
    @PutMapping("/{id}")
    public ResponseEntity<IntakeStudentResponse> updateIntakeStudent(
            @PathVariable String id, @Validated @RequestBody IntakeStudentRequest request)
    {
        IntakeStudentResponse response = intakeStudentService.updateIntakeStudent(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Deletes an intake-student association by its ID.
     *
     * @param id The ID of the intake-student association to delete.
     * @return ResponseEntity with no content.
     */
    @Operation(summary = "Delete an intake-student association",
            description = "Deletes an intake-student association by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Intake-student association deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid ID format"),
            @ApiResponse(responseCode = "404", description = "Intake-student association not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIntakeStudent(@PathVariable String id)
    {
        intakeStudentService.deleteIntakeStudent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}