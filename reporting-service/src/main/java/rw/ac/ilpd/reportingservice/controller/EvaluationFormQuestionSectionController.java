/*
 * File: EvaluationFormQuestionSectionController.java
 *
 * Description: REST Controller for managing Evaluation Form Question Sections.
 *              Provides endpoints for CRUD operations on question sections.
 *
 * Last Modified Date: 2025-08-13
 */
package rw.ac.ilpd.reportingservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.sharedlibrary.dto.evaluation.EvaluationFormQuestionSectionRequest;
import rw.ac.ilpd.sharedlibrary.dto.evaluation.EvaluationFormQuestionSectionResponse;
import rw.ac.ilpd.reportingservice.service.EvaluationFormQuestionSectionService;

import jakarta.validation.Valid;

@Tag(name = "Evaluation Form Question Sections", description = "API for managing question sections in evaluation forms")
@RestController
@RequestMapping("/evaluation/question-sections")
@RequiredArgsConstructor
@Slf4j
public class EvaluationFormQuestionSectionController
{
    private final EvaluationFormQuestionSectionService service;

    @Operation(summary = "Create a new question section", description = "Creates a new section for questions in the evaluation form")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Question section created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<EvaluationFormQuestionSectionResponse> create(
            @Parameter(description = "Request body for creating a question section") @Valid @RequestBody EvaluationFormQuestionSectionRequest request)
    {
        log.info("Creating new question section with title: {}", request.getTitle());
        EvaluationFormQuestionSectionResponse response = service.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all question sections", description = "Retrieves a paginated list of all question sections with sorting support")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of question sections retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<Page<EvaluationFormQuestionSectionResponse>> getAll(
            @Parameter(description = "Pagination and sorting parameters") Pageable pageable)
    {
        log.info("Fetching all question sections with pagination: {}", pageable);
        Page<EvaluationFormQuestionSectionResponse> responses = service.getAll(pageable);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Get a question section by ID", description = "Retrieves a specific question section by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Question section found"),
            @ApiResponse(responseCode = "404", description = "Question section not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EvaluationFormQuestionSectionResponse> getById(
            @Parameter(description = "Unique ID of the question section") @PathVariable String id)
    {
        log.info("Fetching question section with ID: {}", id);
        EvaluationFormQuestionSectionResponse response = service.getById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update a question section", description = "Updates an existing question section by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Question section updated successfully"),
            @ApiResponse(responseCode = "404", description = "Question section not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EvaluationFormQuestionSectionResponse> update(
            @Parameter(description = "Unique ID of the question section") @PathVariable String id,
            @Parameter(description = "Request body for updating the question section") @Valid @RequestBody EvaluationFormQuestionSectionRequest request)
    {
        log.info("Updating question section with ID: {}", id);
        EvaluationFormQuestionSectionResponse response = service.update(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a question section", description = "Deletes a question section by its ID, ensuring no associated questions exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Question section deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Question section not found"),
            @ApiResponse(responseCode = "409", description = "Question section has associated questions and cannot be deleted")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Unique ID of the question section") @PathVariable String id)
    {
        log.info("Deleting question section with ID: {}", id);
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}