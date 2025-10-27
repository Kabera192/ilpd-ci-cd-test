/*
* This entity is read only, and is no longer going to be managed by end users, there is a bean to initialize it we need specific data for it
**/




package rw.ac.ilpd.inventoryservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.inventoryservice.service.LocationTypeService;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.locationtype.LocationTypeRequest;
import rw.ac.ilpd.sharedlibrary.dto.locationtype.LocationTypeResponse;

import java.util.Map;

@RestController
@RequestMapping("/location-types")
@RequiredArgsConstructor
@Tag(name = "Location Types", description = "API for managing location types in the inventory system")
public class LocationTypeController {
    private final LocationTypeService locationTypeService;

    @Operation(summary = "Get all location types with pagination (excluding deleted ones)")
    @GetMapping
    public ResponseEntity<PagedResponse<LocationTypeResponse>> getAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort-by", defaultValue = "name") String sortBy,
            @RequestParam(name = "order", defaultValue = "asc") String order
    ) {
        return ResponseEntity.ok(locationTypeService.getAll(page, size, sortBy, order));
    }

    @Operation(summary = "Get a location type by ID")
    @GetMapping("/{id}")
    public ResponseEntity<LocationTypeResponse> get(
            @PathVariable String id
    ) {
        return ResponseEntity.ok(locationTypeService.get(id));
    }

//    @Operation(summary = "Create a new location type")
//    @PostMapping
//    public ResponseEntity<LocationTypeResponse> create(
//            @Valid @RequestBody LocationTypeRequest request
//    ) {
//        return new ResponseEntity<>(locationTypeService.create(request), HttpStatus.CREATED);
//    }

//    @Operation(summary = "Update an existing location type")
//    @PutMapping("/{id}")
//    public ResponseEntity<LocationTypeResponse> edit(
//            @PathVariable String id,
//            @Valid @RequestBody LocationTypeRequest request
//    ) {
//        return ResponseEntity.ok(locationTypeService.edit(id, request));
//    }

//    @Operation(summary = "Partially update a location type")
//    @PatchMapping("/{id}")
//    public ResponseEntity<LocationTypeResponse> patch(
//            @PathVariable String id,
//            @RequestBody Map<String, Object> updates
//    ) {
//        return ResponseEntity.ok(locationTypeService.patch(id, updates));
//    }

//    @Operation(summary = "Soft delete a location type")
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Boolean> delete(
//            @PathVariable String id
//    ) {
//        return ResponseEntity.ok(locationTypeService.delete(id));
//    }
}