package rw.ac.ilpd.academicservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.academicservice.service.LecturerComponentMaterialService;
import rw.ac.ilpd.mis.shared.config.privilege.academic.LecturerComponentMaterialPriv;
import rw.ac.ilpd.mis.shared.config.privilege.auth.SuperPrivilege;
import rw.ac.ilpd.mis.shared.security.Secured;
import rw.ac.ilpd.sharedlibrary.dto.lecturercomponentmaterial.LecturerComponentMaterialRequest;
import rw.ac.ilpd.sharedlibrary.dto.lecturercomponentmaterial.LecturerComponentMaterialResponse;

import jakarta.validation.Valid;

import java.util.UUID;

/**
 * REST controller for managing LecturerComponentMaterial entities.
 * Provides endpoints for creating, retrieving, updating, and deleting lecturer component materials,
 * with support for pagination and sorting.
 */
@RestController
@RequestMapping(LecturerComponentMaterialPriv.LECTURER_COMPONENT_MATERIAL_PATH)
@RequiredArgsConstructor
@Tag(name = "Lecturer Component Material", description = "Endpoints for managing lecturer component materials")
@SecurityRequirement(name = "Bearer Authentication")
public class LecturerComponentMaterialController
{
    private final LecturerComponentMaterialService service;

    @Operation(summary = "Create a new lecturer component material",
            description = "Links a course material to a lecturer component.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Material created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = LecturerComponentMaterialPriv.CREATE_LECTURER_COMPONENT_MATERIAL_PATH, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Secured({SuperPrivilege.SUPER_ADMIN, LecturerComponentMaterialPriv.CREATE_LECTURER_COMPONENT_MATERIAL})
    public ResponseEntity<LecturerComponentMaterialResponse> createLecturerComponentMaterial(
            @ModelAttribute @Valid LecturerComponentMaterialRequest request) {
        LecturerComponentMaterialResponse response = service.createLecturerComponentMaterial(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @Operation(summary = "Get a lecturer component material by ID",
            description = "Retrieves a lecturer component material by its unique ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Material found"),
            @ApiResponse(responseCode = "404", description = "Material not found")
    })
    @Secured({SuperPrivilege.SUPER_ADMIN, LecturerComponentMaterialPriv.GET_LECTURER_COMPONENT_MATERIAL})
    @GetMapping(LecturerComponentMaterialPriv.GET_LECTURER_COMPONENT_MATERIAL_PATH)
    public ResponseEntity<LecturerComponentMaterialResponse> getLecturerComponentMaterialById(
            @Parameter(description = "UUID of the lecturer component material to retrieve")
            @PathVariable UUID id)
    {
        LecturerComponentMaterialResponse response = service.getLecturerComponentMaterialById(id);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get materials by lecturer component ID with pagination and sorting",
            description = "Retrieves all lecturer component materials for a given lecturer component, with pagination and sorting options.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Materials retrieved successfully with pagination metadata"),
            @ApiResponse(responseCode = "400", description = "Invalid lecturer component ID or pagination parameters")
    })
    @Secured({SuperPrivilege.SUPER_ADMIN, LecturerComponentMaterialPriv.GET_MATERIALS_BY_LECTURER_COMPONENT})
    @GetMapping(LecturerComponentMaterialPriv.GET_MATERIALS_BY_LECTURER_COMPONENT_PATH)
    public ResponseEntity<Page<LecturerComponentMaterialResponse>> getMaterialsByLecturerComponent(
            @Parameter(description = "UUID of the lecturer component")
            @PathVariable UUID lecturerComponentId,
            @Parameter(description = "Page number (0-based, default: 0)")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page (default: 10)")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort field (e.g., 'id', default: 'id')")
            @RequestParam(defaultValue = "id") String sort,
            @Parameter(description = "Sort direction (ASC or DESC, default: ASC)")
            @RequestParam(defaultValue = "ASC") String direction)
    {
        Sort sortOrder = Sort.by(Sort.Direction.fromString(direction), sort);
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<LecturerComponentMaterialResponse> responses = service.getMaterialsByLecturerComponent(
                lecturerComponentId, pageable);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Update a lecturer component material",
            description = "Updates an existing lecturer component material by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Material updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Material not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Secured({SuperPrivilege.SUPER_ADMIN, LecturerComponentMaterialPriv.UPDATE_LECTURER_COMPONENT_MATERIAL})
    @PutMapping(LecturerComponentMaterialPriv.UPDATE_LECTURER_COMPONENT_MATERIAL_PATH)
    public ResponseEntity<String> updateLecturerComponentMaterial(
            @Parameter(description = "UUID of the lecturer component material to update")
            @PathVariable UUID id,
            @Parameter(description = "Request payload for updating the lecturer component material")
            @Valid @ModelAttribute LecturerComponentMaterialRequest request)
    {
        String response = service.updateLecturerComponentMaterial(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a lecturer component material",
            description = "Deletes a lecturer component material by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Material deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Material not found")
    })
    @Secured({SuperPrivilege.SUPER_ADMIN, LecturerComponentMaterialPriv.DELETE_LECTURER_COMPONENT_MATERIAL})
    @DeleteMapping(LecturerComponentMaterialPriv.DELETE_LECTURER_COMPONENT_MATERIAL_PATH)
    public ResponseEntity<Void> deleteLecturerComponentMaterial(
            @Parameter(description = "UUID of the lecturer component material to delete")
            @PathVariable UUID id)
    {
        boolean deleted = service.deleteLecturerComponentMaterial(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}