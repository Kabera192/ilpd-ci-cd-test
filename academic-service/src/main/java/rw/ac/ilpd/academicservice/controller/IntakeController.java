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
import rw.ac.ilpd.academicservice.service.IntakeService;
import rw.ac.ilpd.sharedlibrary.dto.intake.IntakeRequest;
import rw.ac.ilpd.sharedlibrary.dto.intake.IntakeResponse;

/**
 * REST controller for managing intakes.
 * Provides endpoints for creating, retrieving, updating, and deleting intakes.
 */
@RestController
@RequestMapping("/intakes")
@RequiredArgsConstructor
@Tag(name = "Intakes", description = "Endpoints for managing academic intakes")
public class IntakeController
{

    private final IntakeService intakeService;

    /**
     * Creates a new intake.
     *
     * @param request The IntakeRequest containing intake details.
     * @return ResponseEntity with the created IntakeResponse.
     */
    @Operation(summary = "Create a new intake",
            description = "Creates a new academic intake with details such as program, study mode, location, and dates.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Intake created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data, date format, or status"),
            @ApiResponse(responseCode = "404", description = "Referenced program, study mode, curriculum, or institution not found"),
            @ApiResponse(responseCode = "409", description = "Intake with the same name already exists for the program")
    })
    @PostMapping
    public ResponseEntity<IntakeResponse> createIntake(@Validated @RequestBody IntakeRequest request)
    {
        IntakeResponse response = intakeService.createIntake(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Retrieves an intake by its ID.
     *
     * @param id The ID of the intake.
     * @return ResponseEntity with the IntakeResponse.
     */
    @Operation(summary = "Get an intake by ID",
            description = "Retrieves the details of an intake by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Intake retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid ID format"),
            @ApiResponse(responseCode = "404", description = "Intake not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<IntakeResponse> getIntakeById(@PathVariable String id)
    {
        IntakeResponse response = intakeService.getIntakeById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Retrieves all intakes with pagination and sorting.
     * Pagination parameters (page, size, sortBy, order).
     * @return ResponseEntity with a paginated list of IntakeResponse.
     */
    @Operation(summary = "Get all intakes",
            description = "Retrieves a paginated and sorted list of all intakes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Intakes retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination or sorting parameters")
    })
    @GetMapping
    public ResponseEntity<Page<IntakeResponse>> getAllIntakes(
            @Parameter(description = "Page number (0-based)")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Field to sort by (e.g., id, deletedStatus)")
            @RequestParam(name = "sort-by", defaultValue = "id") String sortBy,
            @Parameter(description = "Sort direction (asc or desc)")
            @RequestParam(defaultValue = "asc") String order)
    {
        Page<IntakeResponse> response = intakeService.findAllIntakes(page, size, sortBy, order);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Updates an existing intake.
     *
     * @param id      The ID of the intake to update.
     * @param request The IntakeRequest containing updated details.
     * @return ResponseEntity with the updated IntakeResponse.
     */
    @Operation(summary = "Update an intake",
            description = "Updates the details of an existing intake.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Intake updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data, ID format, or status"),
            @ApiResponse(responseCode = "404", description = "Intake or referenced entities not found"),
            @ApiResponse(responseCode = "409", description = "Intake with the same name already exists for the program")
    })
    @PutMapping("/{id}")
    public ResponseEntity<IntakeResponse> updateIntake(
            @PathVariable String id, @Validated @RequestBody IntakeRequest request)
    {
        IntakeResponse response = intakeService.updateIntake(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Deletes an intake by performing a hard delete.
     *
     * @param id The ID of the intake to delete.
     * @return ResponseEntity with no content.
     */
    @Operation(summary = "Delete an intake",
            description = "Permanently deletes an intake by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Intake deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid ID format"),
            @ApiResponse(responseCode = "404", description = "Intake not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIntake(@PathVariable String id)
    {
        intakeService.deleteIntake(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Updates the status of an intake to OPEN or CLOSED.
     *
     * @param id     The ID of the intake to update.
     * @param status The new status (OPEN or CLOSED).
     * @return ResponseEntity with the updated IntakeResponse.
     */
    @Operation(summary = "Update intake status",
            description = "Updates the status of an intake to OPEN or CLOSED.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Intake status updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid ID format or status"),
            @ApiResponse(responseCode = "404", description = "Intake not found")
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<IntakeResponse> updateIntakeStatus(
            @PathVariable String id, @RequestBody String status)
    {
        IntakeResponse response = intakeService.updateIntakeStatus(id, status);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}