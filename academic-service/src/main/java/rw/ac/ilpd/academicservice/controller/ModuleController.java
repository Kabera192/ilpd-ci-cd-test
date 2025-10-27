package rw.ac.ilpd.academicservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.academicservice.service.ModuleService;
import rw.ac.ilpd.mis.shared.config.privilege.academic.ModulePriv;
import rw.ac.ilpd.mis.shared.config.privilege.auth.SuperPrivilege;
import rw.ac.ilpd.sharedlibrary.dto.module.ModuleRequest;
import rw.ac.ilpd.sharedlibrary.dto.module.ModuleResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;

@RestController
@RequestMapping(path = ModulePriv.BASE_PATH)
@Tag(name = "Module controller", description = "APIs for managing modules (Author) Michael")
public class ModuleController {
    private final ModuleService moduleService;
    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }
    /**
     * Creates a new module.
     *
     * @param moduleRequest DTO containing module details
     * @return the created module response
     */
    @Secured({SuperPrivilege.SUPER_ADMIN, ModulePriv.CREATE_MODULE})
    @PostMapping(path = ModulePriv.CREATE_MODULE_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Create a module",
            description = "Adds a new module to the system."
    )
    public ResponseEntity<ModuleResponse> createModule(
            @RequestBody @Valid ModuleRequest moduleRequest
    ) {
        return moduleService.createModule(moduleRequest);
    }

    /**
     * Permanently deletes a module by its ID.
     * This operation removes the module from the database entirely.
     * Use this only when the module should no longer exist.
     *
     * @param id the UUID of the module to be deleted
     * @return HTTP 200 OK if deleted successfully, or 404 if not found
     */
    @Secured({SuperPrivilege.SUPER_ADMIN, ModulePriv.DELETE_MODULE})
    @DeleteMapping(ModulePriv.DELETE_MODULE_PATH)
    @Operation(
            summary = "Delete a module",
            description = "Removes the module from the database entirely."
    )
    public ResponseEntity<String> deleteModule(
            @Parameter(description = "UUID of the module to delete") @PathVariable String id
    ) {
        return moduleService.deleteModule(id);
    }

    /**
     * Updates the delete status of a module (soft delete or restore).
     *
     * @param id     the UUID of the module to update
     * @param delete the new delete status (true = deleted, false = active)
     * @return the updated module response or 404 if not found
     * @throws Exception in case of any service-level failure
     */
    @Secured({SuperPrivilege.SUPER_ADMIN, ModulePriv.UPDATE_MODULE_DELETE_STATUS})
    @PatchMapping(ModulePriv.UPDATE_MODULE_DELETE_STATUS_PATH)
    @CacheEvict(value = "modules", key = "#id")
    @Operation(
            summary = "Update module delete status",
            description = "Soft deletes or restores a module by updating its delete status."
    )
    public ResponseEntity<ModuleResponse> updateDeleteStatus(
            @Parameter(description = "UUID of the module to update") @PathVariable String id,
            @Parameter(description = "Delete status: true for deleted, false for active") @RequestParam(defaultValue = "false") boolean delete
    ) throws Exception {
        return moduleService.updateModuleDeleteStatus(id, delete);
    }

    /**
     * Retrieves a module by its ID.
     *
     * @param id the UUID of the module
     * @return the module details or 404 if not found
     */
    @Secured({SuperPrivilege.SUPER_ADMIN, ModulePriv.GET_MODULE})
    @GetMapping(ModulePriv.GET_MODULE_PATH)
    @Cacheable(value = "modules", key = "#id")
    @Operation(
            summary = "Get module by ID",
            description = "Fetches details of a module by its UUID."
    )
    public ResponseEntity<ModuleResponse> getModule(
            @Parameter(description = "UUID of the module to retrieve") @PathVariable String id
    ) {
        return moduleService.getEntity(id);
    }

    /**
     * Updates an existing module.
     *
     * @param id            the UUID of the module to update
     * @param moduleRequest the updated module details
     * @return the updated module response or 404 if not found
     */
    @Secured({SuperPrivilege.SUPER_ADMIN, ModulePriv.UPDATE_MODULE})
    @PutMapping(ModulePriv.UPDATE_MODULE_PATH)
    @CacheEvict(value = "modules", key = "#id")
    @Operation(
            summary = "Update module",
            description = "Updates details of an existing module."
    )
    public ResponseEntity<ModuleResponse> updateModule(
            @Parameter(description = "UUID of the module to update") @PathVariable String id,
            @RequestBody @Valid ModuleRequest moduleRequest
    ) {
        return moduleService.updateModule(id, moduleRequest);
    }

    /**
     * Retrieves a paginated list of modules.
     *
     * @param page    the page number (default 0)
     * @param size    the page size (default 10)
     * @param sort    the sort field (default "name")
     * @param search  the search keyword (default "")
     * @param display filter for display status (default "all")
     * @return paged response of modules
     */
    @Secured({SuperPrivilege.SUPER_ADMIN, ModulePriv.LIST_MODULES})
    @GetMapping(ModulePriv.LIST_MODULES_PATH)
    @Operation(
            summary = "Get paginated modules",
            description = "Retrieves a paginated, sorted, and filtered list of modules."
    )
    public PagedResponse<ModuleResponse> getModulePage(
            @Parameter(description = "Page number") @RequestParam(value = "page", defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(value = "size", defaultValue = "10") int size,
            @Parameter(description = "Sort field") @RequestParam(value = "sort", defaultValue = "name") String sort,
            @Parameter(description = "Search keyword") @RequestParam(value = "search", defaultValue = "") String search,
            @Parameter(description = "Display filter") @RequestParam(value = "display", defaultValue = "active") String display
    ) {
        return moduleService.getAllModulePage(page, size, sort, search, display);
    }

}
