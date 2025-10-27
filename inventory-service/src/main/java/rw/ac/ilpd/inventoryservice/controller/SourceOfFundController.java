package rw.ac.ilpd.inventoryservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.inventoryservice.service.SourceOfFundService;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.inventory.SourceOfFundRequest;
import rw.ac.ilpd.sharedlibrary.dto.inventory.SourceOfFundResponse;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/sources-of-funds")
@RequiredArgsConstructor
public class SourceOfFundController {
    private final SourceOfFundService sourceOfFundService;

    @GetMapping
    public ResponseEntity<PagedResponse<SourceOfFundResponse>> getAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort-by", defaultValue = "name") String sortBy,
            @RequestParam(name = "order", defaultValue = "asc") String order,
            @RequestParam(name = "include-deleted", defaultValue = "false") boolean includeDeleted
    ) {
        return ResponseEntity.ok(sourceOfFundService.getAll(page, size, sortBy, order, includeDeleted));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SourceOfFundResponse> get(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(sourceOfFundService.get(id));
    }

    @PostMapping
    public ResponseEntity<SourceOfFundResponse> create(
            @Valid @RequestBody SourceOfFundRequest request
    ) {
        return new ResponseEntity<>(sourceOfFundService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SourceOfFundResponse> edit(
            @PathVariable UUID id,
            @Valid @RequestBody SourceOfFundRequest request
    ) {
        return ResponseEntity.ok(sourceOfFundService.edit(id, request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SourceOfFundResponse> patch(
            @PathVariable UUID id,
            @RequestBody Map<String, Object> updates
    ) {
        return ResponseEntity.ok(sourceOfFundService.patch(id, updates));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(sourceOfFundService.delete(id));
    }

    @PatchMapping("/{id}/restore")
    public ResponseEntity<SourceOfFundResponse> restore(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(sourceOfFundService.restore(id));
    }
}