package rw.ac.ilpd.reportingservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.reportingservice.exception.EntityNotFoundException;
import rw.ac.ilpd.reportingservice.service.AnnualToDoService;
import rw.ac.ilpd.sharedlibrary.dto.planning.AnnualToDoRequest;
import rw.ac.ilpd.sharedlibrary.dto.planning.AnnualToDoResponse;
import rw.ac.ilpd.sharedlibrary.dto.planning.AnnualToDoStatusSummary;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/annual-todos")
@RequiredArgsConstructor
@Validated
@Slf4j
public class AnnualToDoController {

    private final AnnualToDoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new annual todo", description = "Creates a new annual todo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Request ID already exists")
    })
    public ResponseEntity<AnnualToDoResponse> create(
            @Valid @RequestBody AnnualToDoRequest request) {

        log.info("POST /api/v1/annual-todos - Creating annual todo");

        AnnualToDoResponse response = service.create(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get annual todo by ID", description = "Retrieves an annual todo by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Annual todo not found")
    })
    public ResponseEntity<AnnualToDoResponse> getById(
            @PathVariable @NotBlank(message = "ID cannot be blank") String id) {

        log.info("GET /api/v1/annual-todos/{} - Fetching annual todo", id);

        AnnualToDoResponse response = service.getById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/request/{requestId}")
    @Operation(summary = "Get annual todo by request ID", description = "Retrieves an annual todo by its request ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Annual todo not found")
    })
    public ResponseEntity<AnnualToDoResponse> getByRequestId(
            @PathVariable @NotBlank(message = "Request ID cannot be blank") String requestId) {

        log.info("GET /api/v1/annual-todos/request/{} - Fetching annual todo by request ID", requestId);

        AnnualToDoResponse response = service.getByRequestId(requestId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update annual todo", description = "Updates an existing annual todo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Annual todo not found")
    })
    public ResponseEntity<AnnualToDoResponse> update(
            @PathVariable @NotBlank(message = "ID cannot be blank") String id,
            @Valid @RequestBody AnnualToDoRequest request) {

        log.info("PUT /api/v1/annual-todos/{} - Updating annual todo", id);

        AnnualToDoResponse response = service.update(id, request);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update annual todo status", description = "Updates the status of an annual todo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid status"),
            @ApiResponse(responseCode = "404", description = "Annual todo not found")
    })
    public ResponseEntity<AnnualToDoResponse> updateStatus(
            @PathVariable @NotBlank(message = "ID cannot be blank") String id,
            @RequestParam @NotBlank(message = "Status cannot be blank")
            @Pattern(regexp = "PENDING|IN_PROGRESS|COMPLETED|CANCELLED|ON_HOLD",
                    message = "Status must be one of: PENDING, IN_PROGRESS, COMPLETED, CANCELLED, ON_HOLD") String status) {

        log.info("PATCH /api/v1/annual-todos/{}/status - Updating status to: {}", id, status);

        AnnualToDoResponse response = service.updateStatus(id, status);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete annual todo", description = "Hard deletes an annual todo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Annual todo not found")
    })
    public ResponseEntity<Void> delete(
            @PathVariable @NotBlank(message = "ID cannot be blank") String id) {

        log.info("DELETE /api/v1/annual-todos/{} - Deleting annual todo", id);

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/soft-delete")
    @Operation(summary = "Soft delete annual todo", description = "Soft deletes an annual todo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully soft deleted"),
            @ApiResponse(responseCode = "404", description = "Annual todo not found")
    })
    public ResponseEntity<String> softDelete(
            @PathVariable @NotBlank(message = "ID cannot be blank") String id) {

        log.info("PATCH /api/v1/annual-todos/{}/soft-delete - Soft deleting annual todo", id);

        service.softDelete(id);

        return ResponseEntity.ok("Annual todo soft deleted successfully");
    }

    @PatchMapping("/{id}/restore")
    @Operation(summary = "Restore annual todo", description = "Restores a soft deleted annual todo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully restored"),
            @ApiResponse(responseCode = "404", description = "Annual todo not found"),
            @ApiResponse(responseCode = "409", description = "Request ID conflict with existing active todo")
    })
    public ResponseEntity<String> restore(
            @PathVariable @NotBlank(message = "ID cannot be blank") String id) {

        log.info("PATCH /api/v1/annual-todos/{}/restore - Restoring annual todo", id);

        service.restore(id);

        return ResponseEntity.ok("Annual todo restored successfully");
    }

    @GetMapping
    @Operation(summary = "Get all annual todos", description = "Retrieves all annual todos with pagination")
    public ResponseEntity<Page<AnnualToDoResponse>> getAll(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection) {

        log.info("GET /api/v1/annual-todos - Fetching all annual todos");

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<AnnualToDoResponse> response = service.getAll(pageable);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/active")
    @Operation(summary = "Get all active annual todos", description = "Retrieves all active annual todos with pagination")
    public ResponseEntity<Page<AnnualToDoResponse>> getAllActive(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection) {

        log.info("GET /api/v1/annual-todos/active - Fetching all active annual todos");

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<AnnualToDoResponse> response = service.getAllActive(pageable);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    @Operation(summary = "Get all active annual todos as list", description = "Retrieves all active annual todos as a simple list")
    public ResponseEntity<List<AnnualToDoResponse>> getAllActiveList() {

        log.info("GET /api/v1/annual-todos/list - Fetching all active annual todos as list");

        List<AnnualToDoResponse> response = service.getAllActiveList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search/description")
    @Operation(summary = "Search annual todos by description", description = "Searches active annual todos by description")
    public ResponseEntity<Page<AnnualToDoResponse>> searchByDescription(
            @RequestParam @NotBlank(message = "Search description cannot be blank") String description,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection) {

        log.info("GET /api/v1/annual-todos/search/description - Searching annual todos by description: {}", description);

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<AnnualToDoResponse> response = service.searchByDescription(description, pageable);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search/request-id")
    @Operation(summary = "Search annual todos by request ID", description = "Searches active annual todos by request ID")
    public ResponseEntity<Page<AnnualToDoResponse>> searchByRequestId(
            @RequestParam @NotBlank(message = "Search request ID cannot be blank") String requestId,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection) {

        log.info("GET /api/v1/annual-todos/search/request-id - Searching annual todos by request ID: {}", requestId);

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<AnnualToDoResponse> response = service.searchByRequestId(requestId, pageable);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get annual todos by status", description = "Retrieves annual todos with the given status")
    public ResponseEntity<Page<AnnualToDoResponse>> getByStatus(
            @PathVariable @NotBlank(message = "Status cannot be blank")
            @Pattern(regexp = "PENDING|IN_PROGRESS|COMPLETED|CANCELLED|ON_HOLD",
                    message = "Status must be one of: PENDING, IN_PROGRESS, COMPLETED, CANCELLED, ON_HOLD") String status,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection) {

        log.info("GET /api/v1/annual-todos/status/{} - Fetching annual todos by status", status);

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<AnnualToDoResponse> response = service.getByStatus(status, pageable);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/unit/{unitId}")
    @Operation(summary = "Get annual todos by unit ID", description = "Retrieves annual todos for the given unit")
    public ResponseEntity<Page<AnnualToDoResponse>> getByUnitId(
            @PathVariable @NotBlank(message = "Unit ID cannot be blank")
            @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$",
                    message = "Unit ID must be a valid UUID format") String unitId,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection) {

        log.info("GET /api/v1/annual-todos/unit/{} - Fetching annual todos by unit ID", unitId);

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<AnnualToDoResponse> response = service.getByUnitId(unitId, pageable);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/cost-range")
    @Operation(summary = "Get annual todos by cost range", description = "Retrieves annual todos within the given cost range")
    public ResponseEntity<Page<AnnualToDoResponse>> getByCostRange(
            @RequestParam @NotNull(message = "Min cost is required")
            @DecimalMin(value = "0.0", inclusive = false, message = "Min cost must be positive") BigDecimal minCost,
            @RequestParam @NotNull(message = "Max cost is required")
            @DecimalMin(value = "0.0", inclusive = false, message = "Max cost must be positive") BigDecimal maxCost,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size,
            @RequestParam(defaultValue = "cost") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {

        log.info("GET /api/v1/annual-todos/cost-range - Fetching annual todos by cost range: {} - {}", minCost, maxCost);

        if (minCost.compareTo(maxCost) > 0) {
            throw new EntityNotFoundException("Min cost cannot be greater than max cost");
        }

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<AnnualToDoResponse> response = service.getByCostRange(minCost, maxCost, pageable);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/date-range")
    @Operation(summary = "Get annual todos by date range", description = "Retrieves annual todos within the given date range")
    public ResponseEntity<List<AnnualToDoResponse>> getByDateRange(
            @RequestParam @NotNull(message = "Start date is required")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @NotNull(message = "End date is required")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        log.info("GET /api/v1/annual-todos/date-range - Fetching annual todos by date range: {} to {}", startDate, endDate);

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }

        List<AnnualToDoResponse> response = service.getByDateRange(startDate, endDate);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/overdue")
    @Operation(summary = "Get overdue annual todos", description = "Retrieves all overdue annual todos")
    public ResponseEntity<List<AnnualToDoResponse>> getOverdueTasks() {

        log.info("GET /api/v1/annual-todos/overdue - Fetching overdue annual todos");

        List<AnnualToDoResponse> response = service.getOverdueTasks();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/multi-status")
    @Operation(summary = "Get annual todos by multiple statuses", description = "Retrieves annual todos with any of the given statuses")
    public ResponseEntity<List<AnnualToDoResponse>> getByMultipleStatuses(
            @RequestParam @NotEmpty(message = "Statuses list cannot be empty") List<String> statuses) {

        log.info("GET /api/v1/annual-todos/multi-status - Fetching annual todos by multiple statuses: {}", statuses);

        // Validate each status
        for (String status : statuses) {
            if (!status.matches("PENDING|IN_PROGRESS|COMPLETED|CANCELLED|ON_HOLD")) {
                throw new IllegalArgumentException("Invalid status: " + status);
            }
        }

        List<AnnualToDoResponse> response = service.getByMultipleStatuses(statuses);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/exists")
    @Operation(summary = "Check if annual todo exists", description = "Checks if an annual todo exists by ID")
    public ResponseEntity<Boolean> existsById(
            @PathVariable @NotBlank(message = "ID cannot be blank") String id) {

        log.info("GET /api/v1/annual-todos/{}/exists - Checking if annual todo exists", id);

        boolean exists = service.existsById(id);

        return ResponseEntity.ok(exists);
    }

    @GetMapping("/request/{requestId}/exists")
    @Operation(summary = "Check if request ID exists", description = "Checks if a request ID exists")
    public ResponseEntity<Boolean> existsByRequestId(
            @PathVariable @NotBlank(message = "Request ID cannot be blank") String requestId) {

        log.info("GET /api/v1/annual-todos/request/{}/exists - Checking if request ID exists", requestId);

        boolean exists = service.existsByRequestId(requestId);

        return ResponseEntity.ok(exists);
    }

    @GetMapping("/count/active")
    @Operation(summary = "Get count of active annual todos", description = "Returns the count of active annual todos")
    public ResponseEntity<Long> countActive() {

        log.info("GET /api/v1/annual-todos/count/active - Getting count of active annual todos");

        long count = service.countActive();

        return ResponseEntity.ok(count);
    }

    @GetMapping("/count/status/{status}")
    @Operation(summary = "Get count by status", description = "Returns the count of annual todos with the given status")
    public ResponseEntity<Long> countByStatus(
            @PathVariable @NotBlank(message = "Status cannot be blank")
            @Pattern(regexp = "PENDING|IN_PROGRESS|COMPLETED|CANCELLED|ON_HOLD",
                    message = "Status must be one of: PENDING, IN_PROGRESS, COMPLETED, CANCELLED, ON_HOLD") String status) {

        log.info("GET /api/v1/annual-todos/count/status/{} - Getting count by status", status);

        long count = service.countByStatus(status);

        return ResponseEntity.ok(count);
    }
    @GetMapping("/assign-to")
    public Page<AnnualToDoResponse> getByAssignedTo(
                                                    @RequestParam(defaultValue = "0") @Min(0) int page,
                                                    @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size,
                                                    @RequestParam(defaultValue = "createdAt") String sortBy,
                                                    @RequestParam(defaultValue = "desc") String sortDirection,
                                                    Principal assignedTo){
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return service.getByAssignedTo(assignedTo.getName(), pageable);
    }

    @GetMapping("/unit-status-summary")
   public ResponseEntity<List<AnnualToDoStatusSummary>> getStatusSummaryByUnitId(UUID unitId){
        List<AnnualToDoStatusSummary> response=service.getStatusSummaryByUnitId(unitId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/task-priority")
    public Page<AnnualToDoResponse> getByPriority(@RequestParam(defaultValue = "1") Integer priority,
                                           @RequestParam(defaultValue = "0") @Min(0) int page,
                                           @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size,
                                           @RequestParam(defaultValue = "createdAt") String sortBy,
                                           @RequestParam(defaultValue = "desc") String sortDirection
                                           ){
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return service.getByPriority(priority, pageable);
    }

}