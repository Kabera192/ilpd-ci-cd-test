package rw.ac.ilpd.academicservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.academicservice.service.CourseMaterialService;
import rw.ac.ilpd.sharedlibrary.dto.coursematerial.CourseMaterialApi;
import rw.ac.ilpd.sharedlibrary.dto.coursematerial.CourseMaterialRequest;
import rw.ac.ilpd.sharedlibrary.dto.coursematerial.CourseMaterialResponse;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.util.ResponseDetailWrapper;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/course-materials")
@Tag(name = "Course Material", description = "Endpoints for managing course materials")
public class CourseMaterialController implements CourseMaterialApi
{
    private final CourseMaterialService courseMaterialService;

    public CourseMaterialController(CourseMaterialService courseMaterialService)
    {
        this.courseMaterialService = courseMaterialService;
    }

    @Operation(summary = "Create a new course material",
            description = "Creates a new course material document.")
//    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
    public ResponseEntity<CourseMaterialResponse> createCourseMaterialDocument(
      @ModelAttribute @Valid CourseMaterialRequest request
    )
    {
        CourseMaterialResponse response= courseMaterialService.createCourseMaterial(request);
        return ResponseEntity.ok(response);
    }

    //    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "update course material",
            description = "update course material document.")

    @Override
    public ResponseEntity<CourseMaterialResponse> updateCourseMaterial(
            @PathVariable String id, @ModelAttribute @Valid CourseMaterialRequest request
    )
    {
        return ResponseEntity.ok(courseMaterialService.updateCourseMaterial(id,request));
    }
    @Operation(summary = "Get all course materials",
            description = "Retrieves a paginated list of course materials with sorting, search, and display options.")
//    @GetMapping()
    @Override
    public PagedResponse<ResponseDetailWrapper<CourseMaterialResponse, DocumentResponse>> getPagedCourseMaterials(@Parameter(description = "Page number (0-based, default: 0)") @RequestParam(value = "page", defaultValue = "0") int page,
                                                                                                                  @Parameter(description = "Number of items per page (default: 10)") @RequestParam(value = "size", defaultValue = "10") int size,
                                                                                                                  @Parameter(description = "Sort field (e.g., 'title', default: 'title')") @RequestParam(value = "sort", defaultValue = "title") String sort,
                                                                                                                  @Parameter(description = "Search keyword (default: empty)") @RequestParam(value = "search", defaultValue = "") String search,
                                                                                                                  @Parameter(description = "Display filter (e.g., 'all', default: 'all')") @RequestParam(value = "display", defaultValue = "all") String display,
                                                                                                                  @Parameter(description = "Display filter (e.g., 'all', default: 'all')") @RequestParam(value = "order", defaultValue = "asc") String orderBy)
    {
        return  courseMaterialService.getPagedCourseMaterials(page, size, sort, search, display,orderBy);
    }
    @Operation(summary = "find course materials by id")
//    @GetMapping("/{id}")
    @Override
    public ResponseDetailWrapper<CourseMaterialResponse, DocumentResponse> getCourseMaterialById(
            @PathVariable String id
    ){
        return  courseMaterialService.getCourseMaterialById(id);
    }

    @Operation(summary = "find course materials by id")
//    @PostMapping("/find-list")
    @Override
    public List<ResponseDetailWrapper<CourseMaterialResponse, DocumentResponse>> getCourseMaterialById(
            @RequestBody Set<String> id
    ){
        return  courseMaterialService.getListOfCourseMaterial(id);
    }

}
