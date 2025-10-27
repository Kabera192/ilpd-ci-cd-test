package rw.ac.ilpd.inventoryservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.sharedlibrary.dto.inventory.UserItemRequisitionRequest;
import rw.ac.ilpd.sharedlibrary.dto.inventory.UserItemRequisitionResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.inventoryservice.service.UserItemRequisitionService;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user-item-requisitions")
@RequiredArgsConstructor
@Tag(name = "User Item Requisitions", description = "Manage user item requisitions")
public class UserItemRequisitionController {
    private final UserItemRequisitionService userItemRequisitionService;

    @GetMapping
    @Operation(summary = "Get all user item requisitions (paginated)")
    public ResponseEntity<PagedResponse<UserItemRequisitionResponse>> getAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort-by", defaultValue = "id") String sortBy,
            @RequestParam(name = "order", defaultValue = "asc") String order
    ) {
        return ResponseEntity.ok(userItemRequisitionService.getAll(page, size, sortBy, order));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a specific user item requisition by ID")
    public ResponseEntity<UserItemRequisitionResponse> get(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(userItemRequisitionService.get(id));
    }

    @PostMapping
    @Operation(summary = "Create a new user item requisition")
    public ResponseEntity<UserItemRequisitionResponse> create(
            @Valid @RequestBody UserItemRequisitionRequest request
    ) {
        return new ResponseEntity<>(userItemRequisitionService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing user item requisition")
    public ResponseEntity<UserItemRequisitionResponse> edit(
            @PathVariable UUID id,
            @Valid @RequestBody UserItemRequisitionRequest request
    ) {
        return ResponseEntity.ok(userItemRequisitionService.edit(id, request));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Partially update a user item requisition")
    public ResponseEntity<UserItemRequisitionResponse> patch(
            @PathVariable UUID id,
            @RequestBody Map<String, Object> updates
    ) {
        return ResponseEntity.ok(userItemRequisitionService.patch(id, updates));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user item requisition (soft delete)")
    public ResponseEntity<Boolean> delete(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(userItemRequisitionService.delete(id));
    }
}