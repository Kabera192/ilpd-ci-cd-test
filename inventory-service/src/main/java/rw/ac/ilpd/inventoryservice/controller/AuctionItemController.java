package rw.ac.ilpd.inventoryservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.inventoryservice.service.AuctionItemService;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.inventory.AuctionItemRequest;
import rw.ac.ilpd.sharedlibrary.dto.inventory.AuctionItemResponse;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/auction-items")
@RequiredArgsConstructor
public class AuctionItemController {
    private final AuctionItemService auctionItemService;

    @GetMapping
    public ResponseEntity<PagedResponse<AuctionItemResponse>> getAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort-by", defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "order", defaultValue = "desc") String order
    ) {
        return ResponseEntity.ok(auctionItemService.getAll(page, size, sortBy, order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuctionItemResponse> get(
            @PathVariable String id
    ) {
        return ResponseEntity.ok(auctionItemService.get(id));
    }

    @PostMapping
    public ResponseEntity<AuctionItemResponse> create(
            @Valid @RequestBody AuctionItemRequest request,
            @RequestHeader("X-User-Id") UUID recordedBy
    ) {
        return new ResponseEntity<>(auctionItemService.create(request, recordedBy), HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(
            @PathVariable String id
    ) {
        return ResponseEntity.ok(auctionItemService.delete(id));
    }
}