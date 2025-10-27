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
import rw.ac.ilpd.academicservice.service.ShortCourseTopicLecturerService;
import rw.ac.ilpd.mis.shared.config.privilege.academic.ShortCourseTopicLecturerPriv;
import rw.ac.ilpd.mis.shared.config.privilege.auth.SuperPrivilege;
import rw.ac.ilpd.mis.shared.security.Secured;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.shortcoursetopiclecturer.ShortCourseTopicLecturerRequest;
import rw.ac.ilpd.sharedlibrary.dto.shortcoursetopiclecturer.ShortCourseTopicLecturerResponse;

/**
 * Controller that handles all endpoints that deal with the ShortCourseTopicLecturer resource.
 */
@RestController
@RequestMapping(ShortCourseTopicLecturerPriv.SHORT_COURSE_TOPIC_LECTURER_PATH)
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Short Course Topic Lecturer", description = "Endpoints for managing lecturers assigned to short course topics")
public class ShortCourseTopicLecturerController
{
    private final ShortCourseTopicLecturerService shortCourseTopicLecturerService;

    /**
     * Endpoint to create a new ShortCourseTopicLecturer
     */
    @Operation(summary = "Create a new short course topic lecturer",
            description = "Assigns a lecturer to a short course topic.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lecturer assignment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Secured({SuperPrivilege.SUPER_ADMIN, ShortCourseTopicLecturerPriv.CREATE_SHORT_COURSE_TOPIC_LECTURER})
    @PostMapping(ShortCourseTopicLecturerPriv.CREATE_SHORT_COURSE_TOPIC_LECTURER_PATH)
    public ResponseEntity<ShortCourseTopicLecturerResponse> createShortCourseTopicLecturer(
            @Parameter(description = "Request payload for creating a short course topic lecturer")
            @Valid @RequestBody ShortCourseTopicLecturerRequest shortCourseTopicLecturerRequest)
    {
        log.debug("Create shortCourseTopicLecturer endpoint reached for request: {}", shortCourseTopicLecturerRequest);
        return ResponseEntity.ok(shortCourseTopicLecturerService.createShortCourseTopicLecturer(shortCourseTopicLecturerRequest));
    }

    /**
     * Endpoint to get all shortCourseTopicLecturers
     */
    @Operation(summary = "Get all short course topic lecturers",
            description = "Retrieves a paginated list of short course topic lecturers with sorting options.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lecturers retrieved successfully with pagination metadata"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination or sorting parameters")
    })
    @Secured({SuperPrivilege.SUPER_ADMIN, ShortCourseTopicLecturerPriv.GET_ALL_SHORT_COURSE_TOPIC_LECTURER})
    @GetMapping(ShortCourseTopicLecturerPriv.GET_ALL_SHORT_COURSE_TOPIC_LECTURER_PATH)
    public ResponseEntity<PagedResponse<ShortCourseTopicLecturerResponse>> getAllShortCourseTopicLecturers(
            @Parameter(description = "Page number (0-based, default: 0)") @RequestParam(required = false, defaultValue = "0") int page,
            @Parameter(description = "Number of items per page (default: 10)") @RequestParam(required = false, defaultValue = "10") int size,
            @Parameter(description = "Sort field (e.g., 'name', default: 'name')") @RequestParam(name = "sort-by", required = false, defaultValue = "name") String sortBy,
            @Parameter(description = "Sort direction (asc or desc, default: asc)") @RequestParam(required = false, defaultValue = "asc") String order
    )
    {
        log.info("Get all shortCourseTopicLecturers endpoint reached");
        return ResponseEntity.ok(shortCourseTopicLecturerService.getAllShortCourseTopicLecturers(page, size, sortBy, order));
    }

    /**
     * Endpoint to get a shortCourseTopicLecturer by id
     */
    @Operation(summary = "Get a short course topic lecturer by ID",
            description = "Retrieves a short course topic lecturer by their unique ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lecturer found"),
            @ApiResponse(responseCode = "404", description = "Lecturer not found")
    })
    @Secured({SuperPrivilege.SUPER_ADMIN, ShortCourseTopicLecturerPriv.GET_SHORT_COURSE_TOPIC_LECTURER})
    @GetMapping(ShortCourseTopicLecturerPriv.GET_SHORT_COURSE_TOPIC_LECTURER_PATH)
    public ResponseEntity<ShortCourseTopicLecturerResponse> getShortCourseTopicLecturerById(
            @Parameter(description = "ID of the lecturer to retrieve") @Valid @NotNull(message = "shortCourseTopicLecturer id must not be null") @NotBlank(message = "shortCourseTopicLecturer id must not be blank") @PathVariable String id)
    {
        log.debug("Get program by id endpoint reached for request: {}", id);
        return ResponseEntity.ok(shortCourseTopicLecturerService.getShortCourseTopicLecturerById(id));
    }

    /**
     * Endpoint to update the entire shortCourseTopicLecturer resource
     */
    @Operation(summary = "Update a short course topic lecturer",
            description = "Updates an existing short course topic lecturer by their ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lecturer updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Lecturer not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Secured({SuperPrivilege.SUPER_ADMIN, ShortCourseTopicLecturerPriv.UPDATE_SHORT_COURSE_TOPIC_LECTURER})
    @PutMapping(ShortCourseTopicLecturerPriv.UPDATE_SHORT_COURSE_TOPIC_LECTURER_PATH)
    public ResponseEntity<ShortCourseTopicLecturerResponse> updateShortCourseTopicLecturer(
            @Parameter(description = "ID of the lecturer to update") @Valid @NotNull(message = "shortCourseTopicLecturer id must not be null") @NotBlank(message = "shortCourseTopicLecturer id must not be blank") @PathVariable String id,
            @Parameter(description = "Request payload for updating the lecturer") @Valid @RequestBody ShortCourseTopicLecturerRequest shortCourseTopicLecturerRequest
    )
    {
        log.debug(" Update shortCourseTopicLecturer endpoint reached with request: {}"
                , shortCourseTopicLecturerRequest);
        return ResponseEntity.ok(shortCourseTopicLecturerService.updateShortCourseTopicLecturer(id, shortCourseTopicLecturerRequest));
    }

    /**
     * Endpoint to permanently delete a shortCourseTopicLecturer entity
     */
    @Operation(summary = "Delete a short course topic lecturer",
            description = "Permanently deletes a short course topic lecturer by their ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lecturer deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Lecturer not found")
    })
    @Secured({SuperPrivilege.SUPER_ADMIN, ShortCourseTopicLecturerPriv.DELETE_SHORT_COURSE_TOPIC_LECTURER})
    @DeleteMapping(ShortCourseTopicLecturerPriv.DELETE_SHORT_COURSE_TOPIC_LECTURER_PATH)
    public ResponseEntity<Boolean> deleteShortCourseTopicLecturer(
            @Parameter(description = "ID of the lecturer to delete") @Valid @NotNull(message = "shortCourseTopicLecturer id must not be null") @NotBlank(message = "shortCourseTopicLecturer id must not be blank") @PathVariable String id
    )
    {
        log.debug("permanently delete shortCourseTopicLecturer endpoint reached for request: {}", id);
        return new ResponseEntity<>(shortCourseTopicLecturerService.deleteShortCourseTopicLecturer(id), HttpStatus.OK);
    }
}