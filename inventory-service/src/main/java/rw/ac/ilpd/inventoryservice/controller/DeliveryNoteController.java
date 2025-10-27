package rw.ac.ilpd.inventoryservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.inventoryservice.service.DeliveryNoteService;
import rw.ac.ilpd.sharedlibrary.dto.inventory.DeliveryNoteRequest;
import rw.ac.ilpd.sharedlibrary.dto.inventory.DeliveryNoteResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/delivery-notes")
@RequiredArgsConstructor
@Tag(name = "Delivery Notes", description = "API for managing inventory delivery notes")
public class DeliveryNoteController {

    private final DeliveryNoteService deliveryNoteService;

    @GetMapping
    @Operation(summary = "Get all delivery notes with pagination")
    public ResponseEntity<PagedResponse<DeliveryNoteResponse>> getAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort-by", defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "order", defaultValue = "desc") String order) {
        return ResponseEntity.ok(deliveryNoteService.getAll(page, size, sortBy, order));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a delivery note by ID")
    public ResponseEntity<DeliveryNoteResponse> get(
            @PathVariable UUID id) {
        return ResponseEntity.ok(deliveryNoteService.get(id));
    }

    @PostMapping
    @Operation(summary = "Create a new delivery note")
    public ResponseEntity<DeliveryNoteResponse> create(
            @Valid @RequestBody DeliveryNoteRequest request) {
        return new ResponseEntity<>(deliveryNoteService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing delivery note")
    public ResponseEntity<DeliveryNoteResponse> edit(
            @PathVariable UUID id,
            @Valid @RequestBody DeliveryNoteRequest request) {
        return ResponseEntity.ok(deliveryNoteService.edit(id, request));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Partially update a delivery note")
    public ResponseEntity<DeliveryNoteResponse> patch(
            @PathVariable UUID id,
            @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(deliveryNoteService.patch(id, updates));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a delivery note")
    public ResponseEntity<Boolean> delete(
            @PathVariable UUID id) {
        return ResponseEntity.ok(deliveryNoteService.delete(id));
    }
}