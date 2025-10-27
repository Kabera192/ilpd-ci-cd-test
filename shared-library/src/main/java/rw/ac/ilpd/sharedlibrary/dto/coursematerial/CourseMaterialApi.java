package rw.ac.ilpd.sharedlibrary.dto.coursematerial;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.mis.shared.config.privilege.academic.CourseMaterialPriv;
import rw.ac.ilpd.mis.shared.config.privilege.auth.SuperPrivilege;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.util.ResponseDetailWrapper;

import java.util.List;
import java.util.Set;

public interface CourseMaterialApi {
    /**
     * Creates a new course material document.
     *
     * @param request DTO containing course material details and file
     * @return ResponseEntity containing created CourseMaterialResponse
     */
    @Secured(CourseMaterialPriv.CREATE_COURSE_MATERIAL)
    @PostMapping(path = CourseMaterialPriv.CREATE_COURSE_MATERIAL_PATH, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CourseMaterialResponse> createCourseMaterialDocument(
            @ModelAttribute @Valid CourseMaterialRequest request
    );

    /**
     * Retrieves paginated list of course materials with search, filter, and sorting options.
     *
     * @param page page number (default: 0)
     * @param size page size (default: 10)
     * @param sort field to sort by (default: title)
     * @param search search keyword (default: empty)
     * @param display filter option (default: all)
     * @param orderBy sorting order (asc/desc, default: asc)
     * @return paginated list of course materials
     */
    @Secured(CourseMaterialPriv.GET_PAGED_COURSE_MATERIALS_PATH)
    @GetMapping(path =  CourseMaterialPriv.GET_PAGED_COURSE_MATERIALS_PATH)
    PagedResponse<ResponseDetailWrapper<CourseMaterialResponse, DocumentResponse>> getPagedCourseMaterials(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "title") String sort,
            @RequestParam(value = "search", defaultValue = "") String search,
            @RequestParam(value = "display", defaultValue = "all") String display,
            @RequestParam(value = "order", defaultValue = "asc") String orderBy
    ) ;

    /**
     * Retrieves a single course material by ID.
     *
     * @param id course material identifier
     * @return course material with document details
     */
    @Secured({SuperPrivilege.SUPER_ADMIN,CourseMaterialPriv.GET_COURSE_MATERIAL_BY_ID})
    @GetMapping(path =  CourseMaterialPriv.GET_COURSE_MATERIAL_BY_ID_PATH)
    ResponseDetailWrapper<CourseMaterialResponse, DocumentResponse> getCourseMaterialById(
            @PathVariable String id
    ) ;

    /**
     * Retrieves multiple course materials by a set of IDs.
     *
     * @param id set of course material IDs
     * @return list of course materials with document details
     */
    @Secured({SuperPrivilege.SUPER_ADMIN,CourseMaterialPriv.GET_COURSE_MATERIAL_LIST})
    @PostMapping(path =  CourseMaterialPriv.GET_COURSE_MATERIAL_LIST_PATH)
    List<ResponseDetailWrapper<CourseMaterialResponse, DocumentResponse>> getCourseMaterialById(
            @RequestBody Set<String> id
    );

    /**
     * Updates an existing course material document.
     *
     * @param id course material identifier
     * @param request DTO containing updated details and file
     * @return updated CourseMaterialResponse
     */
    @Secured({SuperPrivilege.SUPER_ADMIN,CourseMaterialPriv.UPDATE_COURSE_MATERIAL})
    @PutMapping(path =CourseMaterialPriv.UPDATE_COURSE_MATERIAL_PATH, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<CourseMaterialResponse> updateCourseMaterial(
            @PathVariable String id,
            @ModelAttribute @Valid CourseMaterialRequest request
    );
}
