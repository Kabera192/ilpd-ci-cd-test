/*
* This entity is no longer managed by the end users, we have a bin to init it now*/
package rw.ac.ilpd.academicservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.academicservice.service.ActivityTypeService;
import rw.ac.ilpd.sharedlibrary.dto.activitytype.ActivityTypeRequest;
import rw.ac.ilpd.sharedlibrary.dto.activitytype.ActivityTypeResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;

/**
 * Controller for managing ActivityType endpoints.
 */
@RestController
@RequestMapping("/activity-types")
@RequiredArgsConstructor
@Tag(name = "Activity Type ", description = "Endpoints for managing activity types (Author) Michael")
public class ActivityTypeController {

    private final ActivityTypeService activityTypeService;

    /**
     * Create a new ActivityType.
     *
     * @param activityTypeRequest DTO containing activity type data
     * @return ResponseEntity with created ActivityTypeResponse
     */
//    @Operation(summary = "Create ActivityType", description = "Creates a new activity type")
//    @PostMapping
//    public ResponseEntity<ActivityTypeResponse> createActivityType(
//            @RequestBody @Valid ActivityTypeRequest activityTypeRequest
//    ) {
//        return activityTypeService.createActivityType(activityTypeRequest);
//    }

    /**
     * Update an existing ActivityType.
     *
     * @param id ActivityType ID
     * @param activityTypeRequest DTO containing updated data
     * @return ResponseEntity with updated ActivityTypeResponse
     */
//    @Operation(summary = "Update ActivityType", description = "Updates an existing activity type")
//    @PutMapping("/{id}")
//    public ResponseEntity<ActivityTypeResponse> updateActivityType(
//            @Parameter(description = "ActivityType ID") @PathVariable @NotBlank(message = "Activity type can not be blank") String id,
//            @RequestBody @Valid ActivityTypeRequest activityTypeRequest
//    ) {
//        return activityTypeService.updateActivityType(id, activityTypeRequest);
//    }

    /**
     * Get a paged list of ActivityTypes.
     *
     * @param page Page number
     * @param size Page size
     * @param sort Sort field
     * @param search Search keyword
     * @param display Display filter ("active" or "archive")
     * @param orderBy Order type ("asc" or "desc")
     * @return PagedResponse of ActivityTypeResponse
     */
    @Operation(summary = "Get paged ActivityTypes", description = "Retrieves a paged list of activity types")
    @GetMapping
    public PagedResponse<ActivityTypeResponse> getListOfPagedActivity(
            @Parameter(description = "Page number") @RequestParam(value = "page", defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(value = "size", defaultValue = "10") int size,
            @Parameter(description = "Sort field") @RequestParam(value = "sort", defaultValue = "name") String sort,
            @Parameter(description = "Search keyword") @RequestParam(value = "search", defaultValue = "") String search,
            @Parameter(description = "Display filter") @RequestParam(value = "display", defaultValue = "active") String display,
            @Parameter(description = "Order type (desc or asc)") @RequestParam(value = "order-by", defaultValue = "asc") String orderBy
    ) {
        return activityTypeService.getListOfPagedActivity(page, size, sort, search, display, orderBy);
    }

    /**
     * Delete (archive) an ActivityType.
     *
     * @param id ActivityType ID
     * @return ResponseEntity with status message
     */
//    @Operation(summary = "Delete ActivityType", description = "Deletes (archives) an activity type")
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteActivityType(
//            @Parameter(description = "ActivityType ID") @PathVariable @NotBlank(message = "Activity type can not be blank") String id
//    ) {
//        return activityTypeService.deleteActivityType(id);
//    }
//    @Operation(summary = "Undo Delete ActivityType", description = "Undo Delete (archives) an activity type")
//    @PatchMapping("delete/{id}/undo")
//    public ResponseEntity<String> undoDeleteActivityType(
//            @Parameter(description = "ActivityType ID") @PathVariable @NotBlank(message = "Activity type can not be blank") String id
//            ) {
//        return activityTypeService.undoDeleteActivityType(id);
//    }
}
