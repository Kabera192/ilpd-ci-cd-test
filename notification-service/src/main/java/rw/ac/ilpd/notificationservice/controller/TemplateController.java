/*
 * File: TemplateController.java
 *
 * Description: REST controller for handling template-related HTTP requests.
 *              Provides endpoints for creating, retrieving, updating, and soft-deleting templates.
 *              Supports pagination and sorting for listing templates, with optional filtering by isActive.
 *              Includes Swagger annotations for API documentation.
 */
package rw.ac.ilpd.notificationservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.notificationservice.service.TemplateService;
import rw.ac.ilpd.sharedlibrary.dto.document.TemplateRequest;
import rw.ac.ilpd.sharedlibrary.dto.document.TemplateResponse;

@Tag(name = "Templates", description = "API for managing document templates in the ILPD MIS")
@RestController
@RequestMapping("/templates")
@RequiredArgsConstructor
@Slf4j
public class TemplateController
{

    private final TemplateService templateService;

    @Operation(
            summary = "Create a new template",
            description = "Creates a new template with the provided details, including name, document, and active status."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Template created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<TemplateResponse> createTemplate(
            @Valid @RequestBody TemplateRequest request)
    {
        log.info("Received request to create template with name: {}", request.getName());
        TemplateResponse response = templateService.createTemplate(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Retrieve a template by ID",
            description = "Fetches a template by its unique ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Template retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Template not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TemplateResponse> getTemplateById(
            @Parameter(description = "ID of the template to retrieve") @PathVariable String id)
    {
        log.info("Received request to retrieve template with ID: {}", id);
        TemplateResponse response = templateService.getTemplateById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Retrieve all templates",
            description = "Fetches a paginated and sorted list of templates, optionally filtered by active status."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Templates retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination or sorting parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<Page<TemplateResponse>> getAllTemplates(
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Field to sort by", example = "name")
            @RequestParam(defaultValue = "name") String sortBy,
            @Parameter(description = "Sort direction (asc or desc)", example = "asc")
            @RequestParam(defaultValue = "asc") String order)
    {
        log.info("Received request to retrieve all templates with page: {}, size: {}, sortBy: {}, order: {}",
                page, size, sortBy, order);
        Page<TemplateResponse> response = templateService.getAllTemplates(page, size, sortBy, order);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Update an existing template",
            description = "Updates the details of an existing template by its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Template updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Template not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TemplateResponse> updateTemplate(
            @Parameter(description = "ID of the template to update") @PathVariable String id,
            @Valid @RequestBody TemplateRequest request)
    {
        log.info("Received request to update template with ID: {}", id);
        TemplateResponse response = templateService.updateTemplate(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Soft-delete a template",
            description = "Marks a template as inactive by setting isActive to false."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Template soft-deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Template not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTemplate(
            @Parameter(description = "ID of the template to soft-delete") @PathVariable String id)
    {
        log.info("Received request to soft-delete template with ID: {}", id);
        templateService.deleteTemplate(id);
        return ResponseEntity.noContent().build();
    }
}