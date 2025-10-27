/*
 * File: RequestTypeController.java
 *
 * Description: REST controller for handling request type-related HTTP requests.
 *              Provides endpoints for creating, retrieving, updating, soft-deleting request types,
 *              and adding roles to a request type. Supports pagination and sorting for listing request types.
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
import rw.ac.ilpd.notificationservice.service.RequestTypeService;
import rw.ac.ilpd.sharedlibrary.dto.request.RequestTypeRequest;
import rw.ac.ilpd.sharedlibrary.dto.request.RequestTypeResponse;
import rw.ac.ilpd.sharedlibrary.dto.request.RequestTypeRoleRequest;

@Tag(name = "Request Types", description = "API for managing request types in the ILPD MIS")
@RestController
@RequestMapping("/request-types")
@RequiredArgsConstructor
@Slf4j
public class RequestTypeController
{
    private final RequestTypeService requestTypeService;

    @Operation(
            summary = "Create a new request type",
            description = "Creates a new request type with the provided details, including optional roles."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Request type created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<RequestTypeResponse> createRequestType(
            @Valid @RequestBody RequestTypeRequest request)
    {
        log.info("Received request to create request type with name: {}", request.getName());
        RequestTypeResponse response = requestTypeService.createRequestType(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Retrieve a request type by ID",
            description = "Fetches a request type by its unique ID, excluding soft-deleted request types."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Request type retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Request type not found or is deleted"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RequestTypeResponse> getRequestTypeById(
            @Parameter(description = "ID of the request type to retrieve") @PathVariable String id)
    {
        log.info("Received request to retrieve request type with ID: {}", id);
        RequestTypeResponse response = requestTypeService.getRequestTypeById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Retrieve all request types",
            description = "Fetches a paginated and sorted list of non-deleted request types."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Request types retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination or sorting parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<Page<RequestTypeResponse>> getAllRequestTypes(
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Field to sort by", example = "name")
            @RequestParam(defaultValue = "name") String sortBy,
            @Parameter(description = "Sort direction (asc or desc)", example = "asc")
            @RequestParam(defaultValue = "asc") String order)
    {
        log.info("Received request to retrieve all request types with page: {}, size: {}, sortBy: {}, order: {}",
                page, size, sortBy, order);
        Page<RequestTypeResponse> response = requestTypeService.getAllRequestTypes(page, size, sortBy, order);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Update an existing request type",
            description = "Updates the details of an existing request type by its ID, including optional roles."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Request type updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Request type not found or is deleted"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<RequestTypeResponse> updateRequestType(
            @Parameter(description = "ID of the request type to update") @PathVariable String id,
            @Valid @RequestBody RequestTypeRequest request)
    {
        log.info("Received request to update request type with ID: {}", id);
        RequestTypeResponse response = requestTypeService.updateRequestType(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Soft-delete a request type",
            description = "Marks a request type as deleted by setting deletedStatus to true."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Request type soft-deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Request type not found or is already deleted"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequestType(
            @Parameter(description = "ID of the request type to soft-delete") @PathVariable String id)
    {
        log.info("Received request to soft-delete request type with ID: {}", id);
        requestTypeService.deleteRequestType(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Add a role to a request type",
            description = "Adds a new role to an existing request type by its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid role data"),
            @ApiResponse(responseCode = "404", description = "Request type not found or is deleted"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/{id}/roles")
    public ResponseEntity<RequestTypeResponse> addRoleToRequestType(
            @Parameter(description = "ID of the request type to add a role to") @PathVariable String id,
            @Valid @RequestBody RequestTypeRoleRequest roleRequest)
    {
        log.info("Received request to add role to request type with ID: {}", id);
        RequestTypeResponse response = requestTypeService.addRoleToRequestType(id, roleRequest);
        return ResponseEntity.ok(response);
    }
}