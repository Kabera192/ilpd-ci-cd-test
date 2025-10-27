package rw.ac.ilpd.academicservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.academicservice.service.ShortCourseTopicService;
import rw.ac.ilpd.mis.shared.config.privilege.academic.ShortCourseTopicPriv;
import rw.ac.ilpd.mis.shared.config.privilege.auth.SuperPrivilege;
import rw.ac.ilpd.mis.shared.security.Secured;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.shortcoursetopic.ShortCourseTopicRequest;
import rw.ac.ilpd.sharedlibrary.dto.shortcoursetopic.ShortCourseTopicResponse;

import java.security.Principal;
import java.util.List;

/**
 * Controller for managing ShortCourseTopic CRUD endpoints.
 */
@RestController
@RequestMapping(ShortCourseTopicPriv.BASE_PATH)
@RequiredArgsConstructor
@Tag(name = "Short Course Topic Apis", description = "APIs for managing Short Course Topics (Author: Michael)")
public class ShortCourseTopicController {

    private final ShortCourseTopicService service;
    /**
     * Create a new ShortCourseTopic.
     */
    @Secured({SuperPrivilege.SUPER_ADMIN, ShortCourseTopicPriv.CREATE})
    @Operation(summary = "Create ShortCourseTopic", description = "Creates a new short course topic")
    @PostMapping(ShortCourseTopicPriv.CREATE_PATH)
    public ResponseEntity<ShortCourseTopicResponse> createShortCourseTopic(
            @Valid @RequestBody ShortCourseTopicRequest request, Principal principal
    ) {
        return service.createShortCourseTopic(request, principal);
    }

    /**
     * Get a ShortCourseTopic by ID.
     */
    @Secured({SuperPrivilege.SUPER_ADMIN, ShortCourseTopicPriv.GET})
    @Operation(summary = "Get ShortCourseTopic by ID", description = "Retrieves a short course topic by its ID")
    @GetMapping(ShortCourseTopicPriv.GET_PATH)
    public ResponseEntity<ShortCourseTopicResponse> getShortCourseTopic(
            @Parameter(description = "ShortCourseTopic ID") @PathVariable String id
    ) {
        return service.getShortCourseTopic(id);
    }

    /**
     * Get all ShortCourseTopics (optionally paginated).
     */
    @Secured({SuperPrivilege.SUPER_ADMIN, ShortCourseTopicPriv.LIST_ALL})
    @Operation(summary = "Get all ShortCourseTopics", description = "Retrieves all short course topics")
    @GetMapping(ShortCourseTopicPriv.LIST_ALL_PATH)
    public ResponseEntity<List<ShortCourseTopicResponse>> getAllShortCourseTopics(
            @RequestParam(defaultValue = "active") String display,
            @RequestParam(defaultValue = "", required = false) String search
    ) {
        return service.getAllShortCourseTopics(display, search);
    }

    /**
     * Get paged ShortCourseTopics.
     */
    @Secured({SuperPrivilege.SUPER_ADMIN, ShortCourseTopicPriv.LIST_PAGED})
    @Operation(summary = "Get paged ShortCourseTopics", description = "Retrieves paged short course topics")
    @GetMapping(ShortCourseTopicPriv.LIST_PAGED_PATH)
    public ResponseEntity<PagedResponse<ShortCourseTopicResponse>> getPagedShortCourseTopics(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "active") String display,
            @RequestParam(defaultValue = "", required = false) String search,
            @RequestParam(defaultValue = "name") String sort,
            @RequestParam(defaultValue = "asc", name = "order") String orderBy
    ) {
        return service.getPagedShortCourseTopics(page, size, display, search, sort, orderBy);
    }

    /**
     * Update a ShortCourseTopic by ID.
     */
    @Secured({SuperPrivilege.SUPER_ADMIN, ShortCourseTopicPriv.UPDATE})
    @Operation(summary = "Update ShortCourseTopic", description = "Updates an existing short course topic")
    @PutMapping(ShortCourseTopicPriv.UPDATE_PATH)
    public ResponseEntity<ShortCourseTopicResponse> updateShortCourseTopic(
            @Parameter(description = "ShortCourseTopic ID") @PathVariable String id,
            @Valid @RequestBody ShortCourseTopicRequest request
    ) {
        return service.updateShortCourseTopic(id, request);
    }

    /**
     * Delete a ShortCourseTopic by ID.
     */
    @Secured({SuperPrivilege.SUPER_ADMIN, ShortCourseTopicPriv.DELETE})
    @Operation(summary = "Delete ShortCourseTopic", description = "Deletes a short course topic by its ID")
    @DeleteMapping(ShortCourseTopicPriv.DELETE_PATH)
    public ResponseEntity<String> deleteShortCourseTopic(
            @Parameter(description = "ShortCourseTopic ID") @PathVariable String id
    ) {
        return service.deleteShortCourseTopic(id);
    }

    /**
     * Undo Delete a ShortCourseTopic by ID.
     */
    @Secured({SuperPrivilege.SUPER_ADMIN, ShortCourseTopicPriv.UNDO_DELETE})
    @Operation(summary = "Undo Delete ShortCourseTopic", description = "Undoes the deletion of a short course topic by its ID")
    @PatchMapping(ShortCourseTopicPriv.UNDO_DELETE_PATH)
    public ResponseEntity<String> undoDeleteShortCourseTopic(
            @Parameter(description = "ShortCourseTopic ID") @PathVariable String id
    ) {
        return service.undoDeleteShortCourseTopic(id);
    }
}
