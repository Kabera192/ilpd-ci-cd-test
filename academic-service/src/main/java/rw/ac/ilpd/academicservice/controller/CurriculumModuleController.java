package rw.ac.ilpd.academicservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.sharedlibrary.dto.curriculummodule.CurriculumModuleRequest;
import rw.ac.ilpd.sharedlibrary.dto.curriculummodule.CurriculumModuleResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.academicservice.service.CurriculumModuleService;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/curriculum-modules")
@RequiredArgsConstructor
@Tag(name = "Curriculum Module", description = "Curriculum Module Management APIs")
public class CurriculumModuleController {
    private final CurriculumModuleService curriculumModuleService;

    @GetMapping
    @Operation(summary = "Get all curriculum modules with pagination")
    public ResponseEntity<PagedResponse<CurriculumModuleResponse>> getAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort-by", defaultValue = "moduleOrder") String sortBy,
            @RequestParam(name = "order", defaultValue = "asc") String order) {
        return ResponseEntity.ok(curriculumModuleService.getAll(page, size, sortBy, order));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a curriculum module by ID")
    public ResponseEntity<CurriculumModuleResponse> get(
            @PathVariable UUID id) {
        return ResponseEntity.ok(curriculumModuleService.get(id));
    }

    @PostMapping
    @Operation(summary = "Create a new curriculum module")
    public ResponseEntity<CurriculumModuleResponse> create(
            @Valid @RequestBody CurriculumModuleRequest request) {
        return new ResponseEntity<>(curriculumModuleService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing curriculum module")
    public ResponseEntity<CurriculumModuleResponse> edit(
            @PathVariable UUID id,
            @Valid @RequestBody CurriculumModuleRequest request) {
        return ResponseEntity.ok(curriculumModuleService.edit(id, request));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Partially update a curriculum module")
    public ResponseEntity<CurriculumModuleResponse> patch(
            @PathVariable UUID id,
            @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(curriculumModuleService.patch(id, updates));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a curriculum module")
    public ResponseEntity<Boolean> delete(
            @PathVariable UUID id) {
        return ResponseEntity.ok(curriculumModuleService.delete(id));
    }
}