package rw.ac.ilpd.inventoryservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.inventoryservice.service.LocationService;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.location.LocationRequest;
import rw.ac.ilpd.sharedlibrary.dto.location.LocationResponse;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
@Tag(name = "Locations", description = "API for managing locations in the inventory system")
public class LocationController {
    private final LocationService locationService;

    @Operation(summary = "Get all locations with pagination (excluding deleted ones)")
    @GetMapping
    public ResponseEntity<PagedResponse<LocationResponse>> getAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort-by", defaultValue = "name") String sortBy,
            @RequestParam(name = "order", defaultValue = "asc") String order
    ) {
        return ResponseEntity.ok(locationService.getAll(page, size, sortBy, order));
    }

    @Operation(summary = "Get a location by ID")
    @GetMapping("/{id}")
    public ResponseEntity<LocationResponse> get(
            @PathVariable String id
    ) {
        return ResponseEntity.ok(locationService.get(id));
    }

    @Operation(summary = "Create a new location")
    @PostMapping
    public ResponseEntity<LocationResponse> create(
            @Valid @RequestBody LocationRequest request
    ) {
        return new ResponseEntity<>(locationService.create(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing location")
    @PutMapping("/{id}")
    public ResponseEntity<LocationResponse> edit(
            @PathVariable String id,
            @Valid @RequestBody LocationRequest request
    ) {
        return ResponseEntity.ok(locationService.edit(id, request));
    }

    @Operation(summary = "Partially update a location")
    @PatchMapping("/{id}")
    public ResponseEntity<LocationResponse> patch(
            @PathVariable String id,
            @RequestBody Map<String, Object> updates
    ) {
        return ResponseEntity.ok(locationService.patch(id, updates));
    }

    @Operation(summary = "Soft delete a location")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(
            @PathVariable String id
    ) {
        return ResponseEntity.ok(locationService.delete(id));
    }

    @Operation(summary = "Get a location by ID")
    @GetMapping(path = "/countries")
    public ResponseEntity<List<LocationResponse>> getCountries(
            @RequestParam(name = "search",defaultValue = "")String search
    ) {
       List<LocationResponse> response= locationService.getAllLocationByLocationTypeName(search,"country");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get a location by ID")
    @GetMapping("/campus")
    public ResponseEntity<List<LocationResponse>> getCampus(
            @RequestParam(name = "search",defaultValue = "")String search
    ) {
        List<LocationResponse> response= locationService.getAllLocationByLocationTypeName(search,"campus");
        return ResponseEntity.ok(response);
    }
}