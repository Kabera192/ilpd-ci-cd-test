/*
 * File: EvaluationFormQuestionController.java
 *
 * Description: REST Controller for managing Evaluation Form Questions.
 *              Provides endpoints for CRUD operations on questions.
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
import rw.ac.ilpd.sharedlibrary.dto.evaluation.EvaluationFormQuestionRequest;
import rw.ac.ilpd.sharedlibrary.dto.evaluation.EvaluationFormQuestionResponse;
import rw.ac.ilpd.reportingservice.service.EvaluationFormQuestionService;

import jakarta.validation.Valid;

@Tag(name = "Evaluation Form Questions", description = "API for managing questions in evaluation forms")
@RestController
@RequestMapping("/evaluation/questions")
@RequiredArgsConstructor
@Slf4j
public class EvaluationFormQuestionController
{

    private final EvaluationFormQuestionService service;

    @Operation(summary = "Create a new question", description = "Creates a new question in the evaluation form, ensuring group and section exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Question created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or non-existent group/section"),
    })
    @PostMapping
    public ResponseEntity<EvaluationFormQuestionResponse> create(
            @Parameter(description = "Request body for creating a question") @Valid @RequestBody EvaluationFormQuestionRequest request)
    {
        log.info("Creating new question with text: {}", request.getQuestionText());
        EvaluationFormQuestionResponse response = service.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all questions", description = "Retrieves a paginated list of all questions with sorting support")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of questions retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<Page<EvaluationFormQuestionResponse>> getAll(
            @Parameter(description = "Pagination and sorting parameters") Pageable pageable)
    {
        log.info("Fetching all questions with pagination: {}", pageable);
        Page<EvaluationFormQuestionResponse> responses = service.getAll(pageable);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Get a question by ID", description = "Retrieves a specific question by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Question found"),
            @ApiResponse(responseCode = "404", description = "Question not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EvaluationFormQuestionResponse> getById(
            @Parameter(description = "Unique ID of the question") @PathVariable String id)
    {
        log.info("Fetching question with ID: {}", id);
        EvaluationFormQuestionResponse response = service.getById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update a question", description = "Updates an existing question by its ID, re-validating group and section")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Question updated successfully"),
            @ApiResponse(responseCode = "404", description = "Question not found or non-existent group/section"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EvaluationFormQuestionResponse> update(
            @Parameter(description = "Unique ID of the question") @PathVariable String id,
            @Parameter(description = "Request body for updating the question") @Valid @RequestBody EvaluationFormQuestionRequest request)
    {
        log.info("Updating question with ID: {}", id);
        EvaluationFormQuestionResponse response = service.update(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a question", description = "Deletes a question by its ID, ensuring no associated options exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Question deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Question not found"),
            @ApiResponse(responseCode = "409", description = "Question has associated options and cannot be deleted")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Unique ID of the question") @PathVariable String id)
    {
        log.info("Deleting question with ID: {}", id);
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}