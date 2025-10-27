package rw.ac.ilpd.reportingservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.reportingservice.service.EvaluationFormQuestionGroupService;
import rw.ac.ilpd.sharedlibrary.dto.evaluation.EvaluationFormQuestionGroupRequest;
import rw.ac.ilpd.sharedlibrary.dto.evaluation.EvaluationFormQuestionGroupResponse;

/*
 * File: EvaluationFormQuestionGroupController.java
 *
 * Description: REST Controller for managing Evaluation Form Question Groups.
 *              Provides endpoints for CRUD operations on question groups.
 *
 * Last Modified Date: 2025-08-13
 */
@Tag(name = "Evaluation Form Question Groups", description = "API for managing question groups in evaluation forms")
@RestController
@RequestMapping("/evaluation/question-groups")
@RequiredArgsConstructor
@Slf4j
public class EvaluationFormQuestionGroupController
{

    private final EvaluationFormQuestionGroupService service;

    @Operation(summary = "Create a new question group", description = "Creates a new group for questions in the evaluation form")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Question group created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<EvaluationFormQuestionGroupResponse> create(
            @Parameter(description = "Request body for creating a question group") @Valid @RequestBody EvaluationFormQuestionGroupRequest request)
    {
        log.info("Creating new question group with name: {}", request.getName());
        EvaluationFormQuestionGroupResponse response = service.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all question groups", description = "Retrieves a paginated list of all question groups with sorting support")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of question groups retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<Page<EvaluationFormQuestionGroupResponse>> getAll(
            @Parameter(description = "Pagination and sorting parameters") Pageable pageable)
    {
        log.info("Fetching all question groups with pagination: {}", pageable);
        Page<EvaluationFormQuestionGroupResponse> responses = service.getAll(pageable);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Get a question group by ID", description = "Retrieves a specific question group by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Question group found"),
            @ApiResponse(responseCode = "404", description = "Question group not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EvaluationFormQuestionGroupResponse> getById(
            @Parameter(description = "Unique ID of the question group") @PathVariable String id)
    {
        log.info("Fetching question group with ID: {}", id);
        EvaluationFormQuestionGroupResponse response = service.getById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update a question group", description = "Updates an existing question group by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Question group updated successfully"),
            @ApiResponse(responseCode = "404", description = "Question group not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EvaluationFormQuestionGroupResponse> update(
            @Parameter(description = "Unique ID of the question group") @PathVariable String id,
            @Parameter(description = "Request body for updating the question group") @Valid @RequestBody EvaluationFormQuestionGroupRequest request)
    {
        log.info("Updating question group with ID: {}", id);
        EvaluationFormQuestionGroupResponse response = service.update(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a question group", description = "Deletes a question group by its ID, ensuring no associated questions exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Question group deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Question group not found"),
            @ApiResponse(responseCode = "409", description = "Question group has associated questions and cannot be deleted")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Unique ID of the question group") @PathVariable String id)
    {
        log.info("Deleting question group with ID: {}", id);
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}