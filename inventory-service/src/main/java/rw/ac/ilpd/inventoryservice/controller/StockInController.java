package rw.ac.ilpd.inventoryservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.inventoryservice.service.StockInService;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.inventory.StockInRequest;
import rw.ac.ilpd.sharedlibrary.dto.inventory.StockInResponse;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/stock-ins")
@RequiredArgsConstructor
@Tag(name = "Stock In", description = "Manage stock in operations")
public class StockInController {
    // this should not be edited or patched
    private final StockInService stockInService;

    @GetMapping
    @Operation(summary = "Get all stock in records")
    public ResponseEntity<PagedResponse<StockInResponse>> getAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort-by", defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "order", defaultValue = "desc") String order
    ) {
        return ResponseEntity.ok(stockInService.getAll(page, size, sortBy, order));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a stock in record by ID")
    public ResponseEntity<StockInResponse> get(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(stockInService.get(id));
    }

    @PostMapping
    @Operation(summary = "Create a new stock in record")
    public ResponseEntity<StockInResponse> create(
            @Valid @RequestBody StockInRequest request
    ) {
        return new ResponseEntity<>(stockInService.create(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a stock in record")
    public ResponseEntity<Boolean> delete(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(stockInService.delete(id));
    }
}