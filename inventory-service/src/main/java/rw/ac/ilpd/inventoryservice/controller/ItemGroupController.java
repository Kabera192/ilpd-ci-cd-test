package rw.ac.ilpd.inventoryservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.inventoryservice.service.ItemGroupService;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.inventory.ItemGroupRequest;
import rw.ac.ilpd.sharedlibrary.dto.inventory.ItemGroupResponse;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/item-groups")
@RequiredArgsConstructor
public class ItemGroupController {
    private final ItemGroupService itemGroupService;

    @GetMapping
    public ResponseEntity<PagedResponse<ItemGroupResponse>> getAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort-by", defaultValue = "name") String sortBy,
            @RequestParam(name = "order", defaultValue = "asc") String order) {
        return ResponseEntity.ok(itemGroupService.getAll(page, size, sortBy, order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemGroupResponse> get(
            @PathVariable UUID id) {
        return ResponseEntity.ok(itemGroupService.get(id));
    }

    @PostMapping
    public ResponseEntity<ItemGroupResponse> create(
            @Valid @RequestBody ItemGroupRequest request) {
        return new ResponseEntity<>(itemGroupService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemGroupResponse> edit(
            @PathVariable UUID id,
            @Valid @RequestBody ItemGroupRequest request) {
        return ResponseEntity.ok(itemGroupService.edit(id, request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ItemGroupResponse> patch(
            @PathVariable UUID id,
            @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(itemGroupService.patch(id, updates));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(
            @PathVariable UUID id) {
        return ResponseEntity.ok(itemGroupService.delete(id));
    }
}