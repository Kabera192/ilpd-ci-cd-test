package rw.ac.ilpd.academicservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.academicservice.service.IntakeAssessmentGroupService;
import rw.ac.ilpd.sharedlibrary.dto.intakeassessmentgroup.IntakeAssessmentGroupRequest;
import rw.ac.ilpd.sharedlibrary.dto.intakeassessmentgroup.IntakeAssessmentGroupResponse;
import rw.ac.ilpd.sharedlibrary.dto.assessment.IntakeAssessmentGroupStudentRequest;

import jakarta.validation.Valid;
import java.util.UUID;

/**
 * REST controller for managing IntakeAssessmentGroup entities.
 * Provides endpoints for creating, retrieving, updating, and deleting intake assessment groups,
 * as well as managing students within those groups.
 */
@RestController
@RequestMapping("/intake-assessment-groups")
@RequiredArgsConstructor
@Tag(name = "Intake Assessment Group", description = "Endpoints for managing intake assessment groups and their students")
public class IntakeAssessmentGroupController
{

    private final IntakeAssessmentGroupService service;

    @Operation(summary = "Create a new intake assessment group",
            description = "Creates a new intake assessment group with optional student assignments.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Group created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<IntakeAssessmentGroupResponse> createIntakeAssessmentGroup(
            @Parameter(description = "Request payload for creating an intake assessment group")
            @Valid @RequestBody IntakeAssessmentGroupRequest request)
    {
        IntakeAssessmentGroupResponse response = service.createIntakeAssessmentGroup(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Get an intake assessment group by ID",
            description = "Retrieves an intake assessment group by its unique ID, including assigned students.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Group found"),
            @ApiResponse(responseCode = "404", description = "Group not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<IntakeAssessmentGroupResponse> getIntakeAssessmentGroupById(
            @Parameter(description = "ID of the intake assessment group to retrieve")
            @PathVariable String id)
    {
        IntakeAssessmentGroupResponse response = service.getIntakeAssessmentGroupById(id);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get groups by intake and component IDs with pagination and sorting",
            description = "Retrieves intake assessment groups for a given intake and component, with pagination and sorting options.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Groups retrieved successfully with pagination metadata"),
            @ApiResponse(responseCode = "400", description = "Invalid intake, component ID, or pagination parameters")
    })
    @GetMapping("/intake/{intakeId}/component/{componentId}")
    public ResponseEntity<Page<IntakeAssessmentGroupResponse>> getGroupsByIntakeAndComponent(
            @Parameter(description = "UUID of the intake")
            @PathVariable UUID intakeId,
            @Parameter(description = "UUID of the component")
            @PathVariable UUID componentId,
            @Parameter(description = "Page number (0-based, default: 0)")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page (default: 10)")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort field (e.g., 'name', 'createdAt', default: 'createdAt')")
            @RequestParam(defaultValue = "createdAt") String sort,
            @Parameter(description = "Sort direction (ASC or DESC, default: ASC)")
            @RequestParam(defaultValue = "ASC") String direction)
    {
        Sort sortOrder = Sort.by(Sort.Direction.fromString(direction), sort);
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<IntakeAssessmentGroupResponse> responses = service.
                getGroupsByIntakeAndComponent(intakeId, componentId, pageable);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Update an intake assessment group",
            description = "Updates an existing intake assessment group by its ID, including student assignments.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Group updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Group not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<IntakeAssessmentGroupResponse> updateIntakeAssessmentGroup(
            @Parameter(description = "ID of the intake assessment group to update")
            @PathVariable String id,
            @Parameter(description = "Request payload for updating the intake assessment group")
            @Valid @RequestBody IntakeAssessmentGroupRequest request)
    {
        IntakeAssessmentGroupResponse response = service.updateIntakeAssessmentGroup(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete an intake assessment group",
            description = "Deletes an intake assessment group by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Group deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Group not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIntakeAssessmentGroup(
            @Parameter(description = "ID of the intake assessment group to delete")
            @PathVariable String id)
    {
        boolean deleted = service.deleteIntakeAssessmentGroup(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Add a student to an intake assessment group",
            description = "Adds a student to an existing intake assessment group by its ID. " +
                    "If the student is set as leader, existing leaders are demoted.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Student added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Group not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/{id}/students")
    public ResponseEntity<IntakeAssessmentGroupResponse> addStudentToGroup(
            @Parameter(description = "ID of the intake assessment group")
            @PathVariable String id,
            @Parameter(description = "Request payload for adding a student to the group")
            @Valid @RequestBody IntakeAssessmentGroupStudentRequest studentRequest)
    {
        IntakeAssessmentGroupResponse response = service.addStudentToGroup(id, studentRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Remove a student from an intake assessment group",
            description = "Removes a student from an existing intake assessment group by its ID and the student's ID. " +
                    "If the student is the leader, a new leader is assigned from remaining students.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Student removed successfully"),
            @ApiResponse(responseCode = "404", description = "Group or student not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}/students/{studentId}")
    public ResponseEntity<IntakeAssessmentGroupResponse> removeStudentFromGroup(
            @Parameter(description = "ID of the intake assessment group")
            @PathVariable String id,
            @Parameter(description = "UUID of the student to remove")
            @PathVariable UUID studentId)
    {
        IntakeAssessmentGroupResponse response = service.removeStudentFromGroup(id, studentId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update leadership status of a student in a group",
            description = "Updates the isLeader status for a specific student in an intake assessment group.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Leadership status updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Group or student not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/{id}/students/{studentId}/leadership")
    public ResponseEntity<IntakeAssessmentGroupResponse> updateStudentLeadership(
            @Parameter(description = "ID of the intake assessment group")
            @PathVariable String id,
            @Parameter(description = "UUID of the student to update")
            @PathVariable UUID studentId,
            @Parameter(description = "New leadership status (true for leader, false for non-leader)")
            @RequestParam boolean isLeader)
    {
        IntakeAssessmentGroupResponse response = service.updateStudentLeadership(id, studentId, isLeader);
        return ResponseEntity.ok(response);
    }
}