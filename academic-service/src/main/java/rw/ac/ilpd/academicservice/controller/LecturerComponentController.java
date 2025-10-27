package rw.ac.ilpd.academicservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.sharedlibrary.dto.lecturercomponent.LecturerComponentRequest;
import rw.ac.ilpd.sharedlibrary.dto.lecturercomponent.LecturerComponentResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.academicservice.service.LecturerComponentService;
import rw.ac.ilpd.sharedlibrary.dto.validation.uuid.ValidUuid;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/lecturer-components")
@RequiredArgsConstructor
@Tag(name = "Lecturer Component", description = "Lecturer Component Management APIs")
public class LecturerComponentController {
    private final LecturerComponentService lecturerComponentService;

    @GetMapping
    @Operation(summary = "Get all lecturer-component associations with pagination")
    public ResponseEntity<PagedResponse<LecturerComponentResponse>> getAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort-by", defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "order", defaultValue = "desc") String order) {
        return ResponseEntity.ok(lecturerComponentService.getAll(page, size, sortBy, order));
    }
    @GetMapping("/all-by-component-id")
    public ResponseEntity<PagedResponse<LecturerComponentResponse>> getAllLecturerComponentByComponentId(
            @RequestParam(name = "componentId") @ValidUuid(message = "Wrong specified component format") String componentId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort-by", defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "order", defaultValue = "desc") String order
    ){
        Sort sort=order.equals("desc")?Sort.by(sortBy).ascending():Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        PagedResponse<LecturerComponentResponse> response = lecturerComponentService.getAllLecturerComponentByComponentId(pageable,componentId);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    @Operation(summary = "Get a lecturer-component association by ID")
    public ResponseEntity<LecturerComponentResponse> get(
            @PathVariable UUID id) {
        return ResponseEntity.ok(lecturerComponentService.get(id));
    }

    @PostMapping
    @Operation(summary = "Create a new lecturer-component association")
    public ResponseEntity<LecturerComponentResponse> create(
            @Valid @RequestBody LecturerComponentRequest request) {
        return new ResponseEntity<>(lecturerComponentService.create(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a lecturer-component association")
    public ResponseEntity<Boolean> delete(
            @PathVariable UUID id) {
        return ResponseEntity.ok(lecturerComponentService.delete(id));
    }
}