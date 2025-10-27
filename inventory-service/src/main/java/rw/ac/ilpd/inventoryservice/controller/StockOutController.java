package rw.ac.ilpd.inventoryservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.inventoryservice.service.StockOutService;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.inventory.StockOutRequest;
import rw.ac.ilpd.sharedlibrary.dto.inventory.StockOutResponse;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/stock-outs")
@RequiredArgsConstructor
public class StockOutController {
    private final StockOutService stockOutService;

    @GetMapping
    public ResponseEntity<PagedResponse<StockOutResponse>> getAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort-by", defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "order", defaultValue = "desc") String order
    ) {
        return ResponseEntity.ok(stockOutService.getAll(page, size, sortBy, order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockOutResponse> get(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(stockOutService.get(id));
    }

    @PostMapping
    public ResponseEntity<StockOutResponse> create(
            @Valid @RequestBody StockOutRequest request
    ) {
        return new ResponseEntity<>(stockOutService.create(request), HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(stockOutService.delete(id));
    }
}