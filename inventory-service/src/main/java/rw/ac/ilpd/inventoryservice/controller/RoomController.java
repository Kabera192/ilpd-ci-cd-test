package rw.ac.ilpd.inventoryservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.inventoryservice.service.RoomService;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.room.RoomRequest;
import rw.ac.ilpd.sharedlibrary.dto.room.RoomResponse;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
@Tag(name = "Rooms", description = "API for managing rooms in the inventory system")
public class RoomController {
    private final RoomService roomService;

    @Operation(summary = "Get all rooms with pagination")
    @GetMapping
    public ResponseEntity<PagedResponse<RoomResponse>> getAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort-by", defaultValue = "id") String sortBy,
            @RequestParam(name = "order", defaultValue = "asc") String order
    ) {
        return ResponseEntity.ok(roomService.getAll(page, size, sortBy, order));
    }

    @Operation(summary = "Get a room by ID")
    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> get(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(roomService.get(id));
    }

    @Operation(summary = "Create a new room")
    @PostMapping
    public ResponseEntity<RoomResponse> create(
            @Valid @RequestBody RoomRequest request
    ) {
        return new ResponseEntity<>(roomService.create(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing room")
    @PutMapping("/{id}")
    public ResponseEntity<RoomResponse> edit(
            @PathVariable UUID id,
            @Valid @RequestBody RoomRequest request
    ) {
        return ResponseEntity.ok(roomService.edit(id, request));
    }

    @Operation(summary = "Partially update a room")
    @PatchMapping("/{id}")
    public ResponseEntity<RoomResponse> patch(
            @PathVariable UUID id,
            @RequestBody Map<String, Object> updates
    ) {
        return ResponseEntity.ok(roomService.patch(id, updates));
    }

    @Operation(summary = "Delete a room")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(roomService.delete(id));
    }
}