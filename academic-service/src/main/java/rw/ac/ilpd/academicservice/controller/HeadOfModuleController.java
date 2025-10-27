package rw.ac.ilpd.academicservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.academicservice.service.HeadOfModuleService;
import rw.ac.ilpd.sharedlibrary.dto.headofmodule.HeadOfModuleApi;
import rw.ac.ilpd.sharedlibrary.dto.headofmodule.HeadOfModuleRequest;
import rw.ac.ilpd.sharedlibrary.dto.headofmodule.HeadOfModuleResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller for managing HeadOfModule endpoints.
 */
@RestController
@RequestMapping("/head-of-modules")
@RequiredArgsConstructor
@Tag(name = "Head of Module Apis", description = "APIs for managing Head of Module (Author: Michael)")
public class HeadOfModuleController implements HeadOfModuleApi {

    private final HeadOfModuleService headOfModuleService;

    /**
     * Create a new HeadOfModule.
     */
    @Operation(summary = "Create HeadOfModule", description = "Creates a new head of module")
    @Override
    public ResponseEntity<HeadOfModuleResponse> createHeadOfModule(
            @Valid @RequestBody HeadOfModuleRequest request
    ) {
        return headOfModuleService.createHeadOfModule(request);
    }

    /**
     * Get a HeadOfModule by ID.
     */
    @Operation(summary = "Get HeadOfModule by ID", description = "Retrieves a head of module by its ID")
    @Override
    public ResponseEntity<HeadOfModuleResponse> getHeadOfModule(
            @Parameter(description = "HeadOfModule ID") @PathVariable String id
    ) {
        return headOfModuleService.getHeadOfModule(id);
    }

    /**
     * Get all HeadOfModules.
     */
    @Operation(summary = "Get all HeadOfModules", description = "Retrieves all heads of module")
    @Override
    public ResponseEntity<List<HeadOfModuleResponse>> getAllHeadOfModules(@RequestParam(defaultValue = "",required = false) String search,@RequestParam(defaultValue = "active") String display) {
        List<HeadOfModuleResponse> response = headOfModuleService.getAllHeadOfModules(search, display);
        return ResponseEntity.ok(response);
    }


    /**
     * Get paginated HeadOfModules with optional search and sorting.
     */
    @Operation(summary = "Get paginated HeadOfModules", description = "Retrieves paginated heads of module with search and sorting")
    @Override
    public ResponseEntity<PagedResponse<HeadOfModuleResponse>> getPagedHeadOfModules(
            @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort field") @RequestParam(defaultValue = "createdAt") String sort,
            @Parameter(description = "Order by (asc or desc)") @RequestParam(defaultValue = "asc") String direction,
            @Parameter(description = "Search keyword") @RequestParam(defaultValue = "") String search
    ) {
        PagedResponse<HeadOfModuleResponse> response= headOfModuleService.getPagedHeadOfModules(page, size, sort, direction, search);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<PagedResponse<HeadOfModuleResponse>> getPagedHeadOfModulesHistory(int page, int size, String sort, String direction, String search) {
        Sort.Direction orderDirection = direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderDirection, sort));

        PagedResponse<HeadOfModuleResponse>response=headOfModuleService.getPagedHeadOfModulesHistory(pageable);
    return ResponseEntity.ok(response);
    }

    /**
     * Update a HeadOfModule by ID.
     */
    @Operation(summary = "Update HeadOfModule", description = "Updates an existing head of module")
    @Override
    public ResponseEntity<HeadOfModuleResponse> updateHeadOfModule(
            @Parameter(description = "HeadOfModule ID") @PathVariable String id,
            @Valid @RequestBody HeadOfModuleRequest request
    ) {
        return headOfModuleService.updateHeadOfModule(id, request);
    }

    @Operation(summary = "Update Head of module end date", description = "Update Head of module end date")
    @Override
    public ResponseEntity<HeadOfModuleResponse> updateHeadOfModuleEndingDate(String id, LocalDateTime to) {
        HeadOfModuleResponse response = headOfModuleService.updateHeadOfModuleEndingDate(id,to);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete a HeadOfModule by ID.
     */
    @Operation(summary = "Delete Head Of Module", description = "Deletes a head of module by its ID")
    @Override
    public ResponseEntity<String> deleteHeadOfModule(
            @Parameter(description = "HeadOfModule ID") @PathVariable String id
    ) {
        return headOfModuleService.deleteHeadOfModule(id);
    }
}
