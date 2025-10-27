package rw.ac.ilpd.inventoryservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.inventoryservice.service.DonationService;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.inventory.DonationRequest;
import rw.ac.ilpd.sharedlibrary.dto.inventory.DonationResponse;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/donations")
@RequiredArgsConstructor
public class DonationController {
    private final DonationService donationService;

    @GetMapping
    public ResponseEntity<PagedResponse<DonationResponse>> getAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort-by", defaultValue = "id") String sortBy,
            @RequestParam(name = "order", defaultValue = "asc") String order
    ) {
        return ResponseEntity.ok(donationService.getAll(page, size, sortBy, order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonationResponse> get(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(donationService.get(id));
    }

    @PostMapping
    public ResponseEntity<DonationResponse> create(
            @Valid @RequestBody DonationRequest request
    ) {
        return new ResponseEntity<>(donationService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DonationResponse> edit(
            @PathVariable UUID id,
            @Valid @RequestBody DonationRequest request
    ) {
        return ResponseEntity.ok(donationService.edit(id, request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DonationResponse> patch(
            @PathVariable UUID id,
            @RequestBody Map<String, Object> updates
    ) {
        return ResponseEntity.ok(donationService.patch(id, updates));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(donationService.delete(id));
    }
}