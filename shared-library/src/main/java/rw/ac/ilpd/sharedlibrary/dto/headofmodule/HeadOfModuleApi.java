package rw.ac.ilpd.sharedlibrary.dto.headofmodule;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.mis.shared.config.privilege.academic.HeadOfModulePriv;
import rw.ac.ilpd.mis.shared.config.privilege.auth.SuperPrivilege;
import rw.ac.ilpd.mis.shared.security.Secured;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface HeadOfModuleApi {
    @Secured({SuperPrivilege.SUPER_ADMIN,HeadOfModulePriv.CREATE_HOM})
    @PostMapping(HeadOfModulePriv.CREATE_HOM_PATH)
     ResponseEntity<HeadOfModuleResponse> createHeadOfModule(
            @Valid @RequestBody HeadOfModuleRequest request
    ) ;


    /**
     * Update a HeadOfModule by ID.
     */
    @Secured({SuperPrivilege.SUPER_ADMIN,HeadOfModulePriv.UPDATE_HOM})
    @PutMapping(HeadOfModulePriv.UPDATE_HOM_PATH)
    ResponseEntity<HeadOfModuleResponse> updateHeadOfModule(
            @PathVariable String id,
            @Valid @RequestBody HeadOfModuleRequest request
    );
    /*
    * Update head of module ending date
    * */
    @Secured({SuperPrivilege.SUPER_ADMIN,HeadOfModulePriv.UPDATE_TO_DATE})
    @PatchMapping(path=HeadOfModulePriv.UPDATE_TO_DATE_PATH,consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<HeadOfModuleResponse> updateHeadOfModuleEndingDate(
            @PathVariable String id,
            @Valid @RequestParam(name = "to-date") LocalDateTime to
    );

    /**
     * Delete a HeadOfModule by ID.
     */
    @Secured({SuperPrivilege.SUPER_ADMIN,HeadOfModulePriv.DELETE_HOM_BY_ID})
    @DeleteMapping(HeadOfModulePriv.DELETE_HOM_BY_ID_PATH)
    ResponseEntity<String> deleteHeadOfModule(
           @PathVariable String id
    );

    /**
     * Get paginated HeadOfModules with optional search and sorting.
     */
    @Secured({SuperPrivilege.SUPER_ADMIN,HeadOfModulePriv.PAGED_LIST_OF_ACTIVE_HOM})
    @GetMapping(HeadOfModulePriv.PAGED_LIST_OF_ACTIVE_HOM_PATH)
    ResponseEntity<PagedResponse<HeadOfModuleResponse>> getPagedHeadOfModules(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "asc") String orderBy,
            @RequestParam(defaultValue = "") String search
    );

    @Secured({SuperPrivilege.SUPER_ADMIN,HeadOfModulePriv.PAGED_LIST_OF_HOM_HISTORY})
    @GetMapping(HeadOfModulePriv.PAGED_LIST_OF_HOM_HISTORY_PATH)
    ResponseEntity<PagedResponse<HeadOfModuleResponse>> getPagedHeadOfModulesHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "asc") String orderBy,
            @RequestParam(defaultValue = "") String search
    );
    /**
     * Get a HeadOfModule by ID.
     */
    @Secured({SuperPrivilege.SUPER_ADMIN,HeadOfModulePriv.GET_HOM_BY_ID})
    @GetMapping(HeadOfModulePriv.GET_HOM_BY_ID_PATH)
    public ResponseEntity<HeadOfModuleResponse> getHeadOfModule(
            @PathVariable String id
    );

    /**
     * Get all HeadOfModules.
     */
    @Secured({HeadOfModulePriv.LIST_OF_ACTIVE_HOM,SuperPrivilege.SUPER_ADMIN})
    @GetMapping(HeadOfModulePriv.LIST_OF_ACTIVE_HOM_PATH)
//    public ResponseEntity<List<UserModuleDetailResponse>> getAllHeadOfModules(@RequestParam(defaultValue = "",required = false) String search,
////                                                                              @RequestParam(defaultValue = "active") String display);
    public ResponseEntity<List<HeadOfModuleResponse>> getAllHeadOfModules(@RequestParam(defaultValue = "",required = false) String search,
                                                                          @RequestParam(defaultValue = "active") String display);

}
