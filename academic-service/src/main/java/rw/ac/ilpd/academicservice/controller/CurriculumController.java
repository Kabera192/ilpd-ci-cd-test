package rw.ac.ilpd.academicservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.sharedlibrary.dto.curriculum.CurriculumRequest;
import rw.ac.ilpd.sharedlibrary.dto.curriculum.CurriculumResponse;
import rw.ac.ilpd.sharedlibrary.dto.module.ModuleResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.academicservice.service.CurriculumService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/curriculums")
@RequiredArgsConstructor
@Tag(name = "Curriculum", description = "Curriculum Management APIs")
public class CurriculumController {
    private final CurriculumService curriculumService;

//    @GetMapping("{currId}/get-modules")
//    @Operation(summary = "Get all modules that belong to a curriculum")
//    public ResponseEntity<List<ModuleResponse>> getModules(@PathVariable UUID currId){
//        return ResponseEntity.ok(curriculumService.getCurriculumModules(currId));
//    }

    @GetMapping
    @Operation(summary = "Get all curriculums with pagination")
    public ResponseEntity<PagedResponse<CurriculumResponse>> getAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort-by", defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "order", defaultValue = "desc") String order) {
        return ResponseEntity.ok(curriculumService.getAll(page, size, sortBy, order));
    }

    @GetMapping("/all")
    @Operation(summary = "Get all curriculums Active and inactive with pagination")
    public ResponseEntity<PagedResponse<CurriculumResponse>> getAllAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort-by", defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "order", defaultValue = "desc") String order) {
        return ResponseEntity.ok(curriculumService.getAllAll(page, size, sortBy, order));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a curriculum by ID")
    public ResponseEntity<CurriculumResponse> get(
            @PathVariable UUID id) {
        return ResponseEntity.ok(curriculumService.get(id));
    }

    @GetMapping("/{id}/set-inactive")
    @Operation(summary = "Set a curriculum to inactive")
    public ResponseEntity<CurriculumResponse> set_inactive(
            @PathVariable UUID id) {
        return ResponseEntity.ok(curriculumService.set_inactive(id));
    }

    @PostMapping
    @Operation(summary = "Create a new curriculum")
    public ResponseEntity<CurriculumResponse> create(
            @Valid @RequestBody CurriculumRequest request) {
        return new ResponseEntity<>(curriculumService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing curriculum")
    public ResponseEntity<CurriculumResponse> edit(
            @PathVariable UUID id,
            @Valid @RequestBody CurriculumRequest request) {
        return ResponseEntity.ok(curriculumService.edit(id, request));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Partially update a curriculum")
    public ResponseEntity<CurriculumResponse> patch(
            @PathVariable UUID id,
            @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(curriculumService.patch(id, updates));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a curriculum")
    public ResponseEntity<Boolean> delete(
            @PathVariable UUID id) {
        return ResponseEntity.ok(curriculumService.delete(id));
    }
}