package rw.ac.ilpd.academicservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.academicservice.model.nosql.embedding.ActivityOccurrence;
import rw.ac.ilpd.academicservice.model.nosql.embedding.AttendanceMissing;
import rw.ac.ilpd.academicservice.service.ActivityService;
import rw.ac.ilpd.sharedlibrary.dto.activity.ActivityRequest;
import rw.ac.ilpd.sharedlibrary.dto.activity.ActivityResponse;
import rw.ac.ilpd.sharedlibrary.dto.activitylevel.ActivityLevelRequest;
import rw.ac.ilpd.sharedlibrary.dto.activitylevel.ActivityLevelResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;

import java.util.List;

@RestController
@RequestMapping("/activities")
@RequiredArgsConstructor
@Tag(name = "Activities", description = "Endpoints for managing activities and their nested resources")
public class ActivityController {

    private final ActivityService activityService;

    @Operation(summary = "Create a new activity")
    @PostMapping
    public ResponseEntity<ActivityResponse> create(@Valid @RequestBody ActivityRequest request) {
        return new ResponseEntity<>(activityService.createActivity(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Get a paginated list of all activities")
    @GetMapping
    public ResponseEntity<PagedResponse<ActivityResponse>> getAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort-by", defaultValue = "id") String sortBy,
            @RequestParam(name = "order", defaultValue = "asc") String order
    ) {
        return ResponseEntity.ok(activityService.getAllActivities(page, size, sortBy, order));
    }


    @Operation(summary = "Get a specific activity by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ActivityResponse> getById(@PathVariable String id) {
        return ResponseEntity.ok(activityService.getActivityById(id));
    }

    @Operation(summary = "Update an activity by ID")
    @PutMapping("/{id}")
    public ResponseEntity<ActivityResponse> update(@PathVariable String id, @Valid @RequestBody ActivityRequest request) {
        return ResponseEntity.ok(activityService.updateActivity(id, request));
    }

    @Operation(summary = "Delete an activity by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        activityService.deleteActivity(id);
        return ResponseEntity.noContent().build();
    }

    // ---------- Activity Levels ----------

    @Operation(summary = "Add level to an activity")
    @PostMapping("/{id}/activity-levels")
    public ResponseEntity<ActivityLevelResponse> addLevel(
            @PathVariable String id,
            @Valid @RequestBody ActivityLevelRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(activityService.addActivityLevel(id, request));
    }

    @Operation(summary = "List all levels of an activity")
    @GetMapping("/{id}/activity-levels")
    public ResponseEntity<List<ActivityLevelResponse>> getLevels(@PathVariable String id) {
        return ResponseEntity.ok(activityService.getActivityLevels(id));
    }

    @Operation(summary = "Delete a level from an activity")
    @DeleteMapping("/{id}/activity-levels/{levelId}")
    public ResponseEntity<Void> deleteLevel(@PathVariable String id, @PathVariable String levelId) {
        activityService.deleteActivityLevel(id, levelId);
        return ResponseEntity.noContent().build();
    }

    // ---------- Activity Occurrences ----------

    @Operation(summary = "Add occurrence to activity")
    @PostMapping("/{id}/activity-occurrences")
    public ResponseEntity<Void> addOccurrence(
            @PathVariable String id,
            @RequestBody ActivityOccurrence occurrence
    ) {
        activityService.addActivityOccurrence(id, occurrence);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get all occurrences for an activity")
    @GetMapping("/{id}/activity-occurrences")
    public ResponseEntity<List<ActivityOccurrence>> getOccurrences(@PathVariable String id) {
        return ResponseEntity.ok(activityService.getActivityOccurrences(id));
    }

    @Operation(summary = "Delete a specific activity occurrence")
    @DeleteMapping("/{id}/activity-occurrences/{occurrenceId}")
    public ResponseEntity<Void> deleteOccurrence(@PathVariable String id, @PathVariable String occurrenceId) {
        activityService.deleteActivityOccurrence(id, occurrenceId);
        return ResponseEntity.noContent().build();
    }

    // ---------- Attendance Missings ----------

    @Operation(summary = "Add attendance missing to an occurrence")
    @PostMapping("/{id}/activity-occurrences/{occurrenceId}/attendance-missings")
    public ResponseEntity<Void> addAttendanceMissing(
            @PathVariable String id,
            @PathVariable String occurrenceId,
            @RequestBody AttendanceMissing attendanceMissing
    ) {
        activityService.addAttendanceMissing(id, occurrenceId, attendanceMissing);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "List attendance missings of an occurrence")
    @GetMapping("/{id}/activity-occurrences/{occurrenceId}/attendance-missings")
    public ResponseEntity<List<AttendanceMissing>> getAttendanceMissings(
            @PathVariable String id,
            @PathVariable String occurrenceId
    ) {
        return ResponseEntity.ok(activityService.getAttendanceMissings(id, occurrenceId));
    }

    @Operation(summary = "Delete an attendance missing")
    @DeleteMapping("/{id}/activity-occurrences/{occurrenceId}/attendance-missings/{missingId}")
    public ResponseEntity<Void> deleteAttendanceMissing(
            @PathVariable String id,
            @PathVariable String occurrenceId,
            @PathVariable String missingId
    ) {
        activityService.deleteAttendanceMissing(id, occurrenceId, missingId);
        return ResponseEntity.noContent().build();
    }
}

