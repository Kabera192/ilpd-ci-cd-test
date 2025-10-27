package rw.ac.ilpd.reportingservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.reportingservice.service.EvaluationFormTypeService;
import rw.ac.ilpd.sharedlibrary.dto.evaluation.EvaluationFormTypeRequest;
import rw.ac.ilpd.sharedlibrary.dto.evaluation.EvaluationFormTypeResponse;
import java.util.List;

@RestController
@RequestMapping("/evaluation-form-types")
@RequiredArgsConstructor
@Validated
@Slf4j
public class EvaluationFormTypeController {

    private final EvaluationFormTypeService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new evaluation form type", description = "Creates a new evaluation form type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Name already exists")
    })
    public ResponseEntity<EvaluationFormTypeResponse> create(
            @Valid @RequestBody EvaluationFormTypeRequest request) {

        log.info("POST /api/v1/evaluation-form-types - Creating evaluation form type");

        EvaluationFormTypeResponse response = service.create(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get evaluation form type by ID", description = "Retrieves an evaluation form type by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Evaluation form type not found")
    })
    public ResponseEntity<EvaluationFormTypeResponse> getById(
            @PathVariable @NotBlank(message = "ID cannot be blank") String id) {

        log.info("GET /api/v1/evaluation-form-types/{} - Fetching evaluation form type", id);

        EvaluationFormTypeResponse response = service.getById(id);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update evaluation form type", description = "Updates an existing evaluation form type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Evaluation form type not found"),
            @ApiResponse(responseCode = "409", description = "Name already exists")
    })
    public ResponseEntity<EvaluationFormTypeResponse> update(
            @PathVariable @NotBlank(message = "ID cannot be blank") String id,
            @Valid @RequestBody EvaluationFormTypeRequest request) {

        log.info("PUT /api/v1/evaluation-form-types/{} - Updating evaluation form type", id);

        EvaluationFormTypeResponse response = service.update(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete evaluation form type", description = "Hard deletes an evaluation form type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Evaluation form type not found")
    })
    public ResponseEntity<String> delete(
            @PathVariable @NotBlank(message = "ID cannot be blank") String id) {

        log.info("DELETE /api/v1/evaluation-form-types/{} - Deleting evaluation form type", id);

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/soft-delete")
    @Operation(summary = "Soft delete evaluation form type", description = "Soft deletes an evaluation form type by setting active to false")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully soft deleted"),
            @ApiResponse(responseCode = "404", description = "Evaluation form type not found")
    })
    public ResponseEntity<String> softDelete(
            @PathVariable @NotBlank(message = "ID cannot be blank") String id) {

        log.info("PATCH /api/v1/evaluation-form-types/{}/soft-delete - Soft deleting evaluation form type", id);

        service.softDelete(id);

        return ResponseEntity.ok("Evaluation form type soft deleted successfully");
    }

    @GetMapping
    @Operation(summary = "Get all evaluation form types", description = "Retrieves all evaluation form types with pagination")
    public ResponseEntity<Page<EvaluationFormTypeResponse>> getAll(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {

        log.info("GET /api/v1/evaluation-form-types - Fetching all evaluation form types");

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<EvaluationFormTypeResponse> response = service.getAll(pageable);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/active")
    @Operation(summary = "Get all active evaluation form types", description = "Retrieves all active evaluation form types with pagination")
    public ResponseEntity<Page<EvaluationFormTypeResponse>> getAllActive(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {

        log.info("GET /api/v1/evaluation-form-types/active - Fetching all active evaluation form types");

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<EvaluationFormTypeResponse> response = service.getAllActive(pageable);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    @Operation(summary = "Get all active evaluation form types as list", description = "Retrieves all active evaluation form types as a simple list")
    public ResponseEntity<List<EvaluationFormTypeResponse>> getAllActiveList() {

        log.info("GET /api/v1/evaluation-form-types/list - Fetching all active evaluation form types as list");

        List<EvaluationFormTypeResponse> response = service.getAllActiveList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    @Operation(summary = "Search evaluation form types by name", description = "Searches active evaluation form types by name")
    public ResponseEntity<Page<EvaluationFormTypeResponse>> searchByName(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {

        log.info("GET /api/v1/evaluation-form-types/search - Searching evaluation form types by name: {}", name);

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<EvaluationFormTypeResponse> response = service.searchByName(name, pageable);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/exists")
    @Operation(summary = "Check if evaluation form type exists", description = "Checks if an evaluation form type exists by ID")
    public ResponseEntity<Boolean> existsById(
            @PathVariable @NotBlank(message = "ID cannot be blank") String id) {

        log.info("GET /api/v1/evaluation-form-types/{}/exists - Checking if evaluation form type exists", id);

        boolean exists = service.existsById(id);

        return ResponseEntity.ok(exists);
    }

    @GetMapping("/count")
    @Operation(summary = "Get count of active evaluation form types", description = "Returns the count of active evaluation form types")
    public ResponseEntity<Long> countActive() {

        log.info("GET /api/v1/evaluation-form-types/count - Getting count of active evaluation form types");

        long count = service.countActive();

        return ResponseEntity.ok(count);
    }
}
