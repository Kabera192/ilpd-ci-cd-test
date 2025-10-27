package rw.ac.ilpd.academicservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.academicservice.service.ComponentService;
import rw.ac.ilpd.sharedlibrary.dto.component.ComponentRequest;
import rw.ac.ilpd.sharedlibrary.dto.component.ComponentResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;

/**
 * Controller that handles all endpoints that deal with the Component resource.
 */
@RestController
@RequestMapping("/components")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Component", description = "Endpoints for managing academic components")
public class ComponentController
{
    private final ComponentService componentService;

    /**
     * Endpoint to create a new Component
     */
    @Operation(summary = "Create a new component",
            description = "Creates a new academic component.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Component created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<ComponentResponse> createComponent(@Parameter(description = "Request payload for creating a component") @Valid @RequestBody ComponentRequest componentRequest)
    {
        log.debug("Create component endpoint reached for request: {}", componentRequest);
        return ResponseEntity.ok(componentService.createComponent(componentRequest));
    }

    /**
     * Endpoint to get all components
     */
    @Operation(summary = "Get all components",
            description = "Retrieves a paginated list of academic components with sorting options.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Components retrieved successfully with pagination metadata"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination or sorting parameters")
    })
    @GetMapping
    public ResponseEntity<PagedResponse<ComponentResponse>> getAllComponents(
            @Parameter(description = "Page number (0-based, default: 0)") @RequestParam(required = false, defaultValue = "0") int page,
            @Parameter(description = "Number of items per page (default: 10)") @RequestParam(required = false, defaultValue = "10") int size,
            @Parameter(description = "Sort field (e.g., 'name', default: 'name')") @RequestParam(name = "sort-by", required = false, defaultValue = "name") String sortBy,
            @Parameter(description = "Sort direction (asc or desc, default: asc)") @RequestParam(required = false, defaultValue = "asc") String order
    )
    {
        log.info("Get all components endpoint reached");
        return ResponseEntity.ok(componentService.getAllComponents(page, size, sortBy, order));
    }

    /**
     * Endpoint to get a component by id
     */
    @Operation(summary = "Get a component by ID",
            description = "Retrieves an academic component by its unique ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Component found"),
            @ApiResponse(responseCode = "404", description = "Component not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ComponentResponse> getComponentById(
            @Parameter(description = "ID of the component to retrieve")
            @Valid @NotBlank(message = "component id must not be blank")
            @UUID(message = "Format of the id provided is invalid") @PathVariable String id)
    {
        log.debug("Get program by id endpoint reached for request: {}", id);
        return ResponseEntity.ok(componentService.getComponentById(id));
    }

    /**
     * Endpoint to update the entire component resource
     */
    @Operation(summary = "Update a component",
            description = "Updates an existing academic component by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Component updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Component not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ComponentResponse> updateComponent(
            @Parameter(description = "ID of the component to update")
            @Valid @NotBlank(message = "component id must not be blank")
            @UUID(message = "Format of the id provided is invalid") @PathVariable String id,
            @Parameter(description = "Request payload for updating the component")
            @Valid @RequestBody ComponentRequest componentRequest
    )
    {
        log.debug("Update component endpoint reached with request: {}", componentRequest);
        return ResponseEntity.ok(componentService.updateComponent(id, componentRequest));
    }

    /**
     * Endpoint to permanently delete a component entity
     */
    @Operation(summary = "Delete a component",
            description = "Permanently deletes an academic component by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Component deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Component not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteComponent(
            @Parameter(description = "ID of the component to delete")
            @Valid @NotBlank(message = "component id must not be blank")
            @UUID(message = "Format of the id provided is invalid") @PathVariable String id
    )
    {
        log.debug("permanently delete component endpoint reached for request: {}", id);
        return new ResponseEntity<>(componentService.deleteComponent(id), HttpStatus.OK);
    }
}