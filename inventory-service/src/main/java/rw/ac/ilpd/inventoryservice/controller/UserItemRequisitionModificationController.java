package rw.ac.ilpd.inventoryservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.inventoryservice.service.UserItemRequisitionModificationService;
import rw.ac.ilpd.sharedlibrary.dto.inventory.UserItemRequisitionModificationRequest;
import rw.ac.ilpd.sharedlibrary.dto.inventory.UserItemRequisitionModificationResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user-item-requisition-modifications")
@RequiredArgsConstructor
@Tag(name = "User Item Requisition Modifications",
        description = "API for managing modifications to user item requisitions")
public class UserItemRequisitionModificationController {

    private final UserItemRequisitionModificationService modificationService;

    @GetMapping
    @Operation(summary = "Get all modifications with pagination")
    public ResponseEntity<PagedResponse<UserItemRequisitionModificationResponse>> getAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort-by", defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "order", defaultValue = "desc") String order) {
        return ResponseEntity.ok(modificationService.getAll(page, size, sortBy, order));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a modification by ID")
    public ResponseEntity<UserItemRequisitionModificationResponse> get(
            @PathVariable UUID id) {
        return ResponseEntity.ok(modificationService.get(id));
    }

    @PostMapping
    @Operation(summary = "Create a new modification record")
    public ResponseEntity<UserItemRequisitionModificationResponse> create(
            @Valid @RequestBody UserItemRequisitionModificationRequest request) {
        return new ResponseEntity<>(modificationService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing modification record")
    public ResponseEntity<UserItemRequisitionModificationResponse> edit(
            @PathVariable UUID id,
            @Valid @RequestBody UserItemRequisitionModificationRequest request) {
        return ResponseEntity.ok(modificationService.edit(id, request));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Partially update a modification record")
    public ResponseEntity<UserItemRequisitionModificationResponse> patch(
            @PathVariable UUID id,
            @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(modificationService.patch(id, updates));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Soft delete a modification record (set deleteStatus to true)")
    public ResponseEntity<Boolean> delete(
            @PathVariable UUID id) {
        return ResponseEntity.ok(modificationService.delete(id));
    }
}