package rw.ac.ilpd.academicservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.academicservice.service.ShortCourseTopicMaterialService;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.shortcoursetopicmaterial.ShortCourseTopicMaterialRequest;
import rw.ac.ilpd.sharedlibrary.dto.shortcoursetopicmaterial.ShortCourseTopicMaterialResponse;

/**
 * Controller that handles all endpoints that deal with the ShortCourseTopicMaterial resource.
 */
@Tag(name = "Short Course Topic Material", description = "Endpoints for managing course materials assigned to short course topics")
@RestController
@RequestMapping("/short-course-topic-materials")
@RequiredArgsConstructor
@Slf4j
public class ShortCourseTopicMaterialController
{
    private final ShortCourseTopicMaterialService shortCourseTopicMaterialService;

    /**
     * Endpoint to create a new ShortCourseTopicMaterial
     */
    @Operation(summary = "Create a new short course topic material",
            description = "Assigns a course material to a short course topic.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Short course topic material created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Course material or topic not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<ShortCourseTopicMaterialResponse> createShortCourseTopicMaterial(
            @Parameter(description = "Request payload for creating a short course topic material")
            @Valid @RequestBody ShortCourseTopicMaterialRequest shortCourseTopicMaterialRequest)
    {
        log.debug("Create shortCourseTopicMaterial endpoint reached for request: {}", shortCourseTopicMaterialRequest);
        return new ResponseEntity<>(shortCourseTopicMaterialService.createShortCourseTopicMaterial(shortCourseTopicMaterialRequest), HttpStatus.CREATED);
    }

    /**
     * Endpoint to get all shortCourseTopicMaterials
     */
    @Operation(summary = "Get all short course topic materials",
            description = "Retrieves a paginated list of short course topic materials with sorting options.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Short course topic materials retrieved successfully with pagination metadata"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination or sorting parameters")
    })
    @GetMapping
    public ResponseEntity<PagedResponse<ShortCourseTopicMaterialResponse>> getAllShortCourseTopicMaterials(
            @Parameter(description = "Page number (0-based, default: 0)") @RequestParam(required = false, defaultValue = "0") int page,
            @Parameter(description = "Number of items per page (default: 10)") @RequestParam(required = false, defaultValue = "10") int size,
            @Parameter(description = "Sort field (e.g., 'name', default: 'name')") @RequestParam(name = "sort-by", required = false, defaultValue = "name") String sortBy,
            @Parameter(description = "Sort direction (asc or desc, default: asc)") @RequestParam(required = false, defaultValue = "asc") String order
    )
    {
        log.info("Get all shortCourseTopicMaterials endpoint reached");
        return ResponseEntity.ok(shortCourseTopicMaterialService.getAllShortCourseTopicMaterials(page, size, sortBy, order));
    }

    /**
     * Endpoint to get a shortCourseTopicMaterial by id
     */
    @Operation(summary = "Get a short course topic material by ID",
            description = "Retrieves a short course topic material by its unique ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Short course topic material found"),
            @ApiResponse(responseCode = "404", description = "Short course topic material not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ShortCourseTopicMaterialResponse> getShortCourseTopicMaterialById(
            @Parameter(description = "ID of the short course topic material to retrieve") @Valid @NotNull(message = "shortCourseTopicMaterial id must not be null") @NotBlank(message = "shortCourseTopicMaterial id must not be blank") @PathVariable String id)
    {
        log.debug("Get program by id endpoint reached for request: {}", id);
        return ResponseEntity.ok(shortCourseTopicMaterialService.getShortCourseTopicMaterialById(id));
    }

    /**
     * Endpoint to update the entire shortCourseTopicMaterial resource
     */
    @Operation(summary = "Update a short course topic material",
            description = "Updates an existing short course topic material by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Short course topic material updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Short course topic material not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ShortCourseTopicMaterialResponse> updateShortCourseTopicMaterial(
            @Parameter(description = "ID of the short course topic material to update")
            @Valid @NotNull(message = "shortCourseTopicMaterial id must not be null")
            @NotBlank(message = "shortCourseTopicMaterial id must not be blank") @PathVariable String id,
            @Parameter(description = "Request payload for updating the short course topic material")
            @Valid @RequestBody ShortCourseTopicMaterialRequest shortCourseTopicMaterialRequest
    )
    {
        log.debug(" Update shortCourseTopicMaterial endpoint reached with request: {}"
                , shortCourseTopicMaterialRequest);
        return ResponseEntity.ok(shortCourseTopicMaterialService.updateShortCourseTopicMaterial(
                id, shortCourseTopicMaterialRequest));
    }

    /**
     * Endpoint to permanently delete a shortCourseTopicMaterial entity
     */
    @Operation(summary = "Delete a short course topic material",
            description = "Permanently deletes a short course topic material by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Short course topic material deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Short course topic material not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteShortCourseTopicMaterial(
            @Parameter(description = "ID of the short course topic material to delete") @Valid @NotNull(message = "shortCourseTopicMaterial id must not be null") @NotBlank(message = "shortCourseTopicMaterial id must not be blank") @PathVariable String id
    )
    {
        log.debug("permanently delete shortCourseTopicMaterial endpoint reached for request: {}", id);
        return new ResponseEntity<>(shortCourseTopicMaterialService.deleteShortCourseTopicMaterial(id), HttpStatus.OK);
    }
}