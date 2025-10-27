package rw.ac.ilpd.inventoryservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.inventoryservice.service.RoomTypeService;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.roomtype.RoomTypeRequest;
import rw.ac.ilpd.sharedlibrary.dto.roomtype.RoomTypeResponse;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/room-types")
@RequiredArgsConstructor
@Tag(name = "Room Types", description = "API for managing room types in the inventory system")
public class RoomTypeController {
    private final RoomTypeService roomTypeService;

    @Operation(summary = "Get all room types with pagination")
    @GetMapping
    public ResponseEntity<PagedResponse<RoomTypeResponse>> getAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort-by", defaultValue = "name") String sortBy,
            @RequestParam(name = "order", defaultValue = "asc") String order
    ) {
        return ResponseEntity.ok(roomTypeService.getAll(page, size, sortBy, order));
    }

    @Operation(summary = "Get a room type by ID")
    @GetMapping("/{id}")
    public ResponseEntity<RoomTypeResponse> get(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(roomTypeService.get(id));
    }

    @Operation(summary = "Create a new room type")
    @PostMapping
    public ResponseEntity<RoomTypeResponse> create(
            @Valid @RequestBody RoomTypeRequest request
    ) {
        return new ResponseEntity<>(roomTypeService.create(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing room type")
    @PutMapping("/{id}")
    public ResponseEntity<RoomTypeResponse> edit(
            @PathVariable UUID id,
            @Valid @RequestBody RoomTypeRequest request
    ) {
        return ResponseEntity.ok(roomTypeService.edit(id, request));
    }

    @Operation(summary = "Delete a room type")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(roomTypeService.delete(id));
    }
}