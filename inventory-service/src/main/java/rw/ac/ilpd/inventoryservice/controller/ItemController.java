package rw.ac.ilpd.inventoryservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.inventoryservice.service.ItemService;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.inventory.ItemRequest;
import rw.ac.ilpd.sharedlibrary.dto.inventory.ItemResponse;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<PagedResponse<ItemResponse>> getAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort-by", defaultValue = "name") String sortBy,
            @RequestParam(name = "order", defaultValue = "asc") String order) {
        return ResponseEntity.ok(itemService.getAll(page, size, sortBy, order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> get(
            @PathVariable UUID id) {
        return ResponseEntity.ok(itemService.get(id));
    }

    @PostMapping
    public ResponseEntity<ItemResponse> create(
            @Valid @RequestBody ItemRequest request) {
        return new ResponseEntity<>(itemService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemResponse> edit(
            @PathVariable UUID id,
            @Valid @RequestBody ItemRequest request) {
        return ResponseEntity.ok(itemService.edit(id, request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ItemResponse> patch(
            @PathVariable UUID id,
            @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(itemService.patch(id, updates));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(
            @PathVariable UUID id) {
        return ResponseEntity.ok(itemService.delete(id));
    }
}