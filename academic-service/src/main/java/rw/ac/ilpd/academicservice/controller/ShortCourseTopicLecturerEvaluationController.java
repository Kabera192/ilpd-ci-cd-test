package rw.ac.ilpd.academicservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.academicservice.service.ShortCourseTopicLecturerEvaluationService;
import rw.ac.ilpd.mis.shared.config.privilege.academic.ShortCourseTopicLecturerEvaluationPriv;
import rw.ac.ilpd.mis.shared.config.privilege.auth.SuperPrivilege;
import rw.ac.ilpd.sharedlibrary.dto.shortcoursetopiclecturerevaluation.ShortCourseTopicLecturerEvaluationRequest;
import rw.ac.ilpd.sharedlibrary.dto.shortcoursetopiclecturerevaluation.ShortCourseTopicLecturerEvaluationResponse;

/**
 * Controller for managing ShortCourseTopicLecturerEvaluation endpoints.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(ShortCourseTopicLecturerEvaluationPriv.BASE_PATH)
@Tag(name = "Short Course Topic Lecturer Evaluation Apis", description = "APIs for managing Short Course Topic Lecturer Evaluations (Author: Michael)")
public class ShortCourseTopicLecturerEvaluationController {

    private final ShortCourseTopicLecturerEvaluationService service;

    /**
     * Create a new evaluation.
     */
    @Secured({SuperPrivilege.SUPER_ADMIN, ShortCourseTopicLecturerEvaluationPriv.CREATE_EVALUATION})
    @Operation(
            summary = "Create Short course topic lecturer Evaluation",
            description = "Creates a new short course topic lecturer evaluation"
    )
    @PostMapping(ShortCourseTopicLecturerEvaluationPriv.CREATE_EVALUATION_PATH)
    public ResponseEntity<ShortCourseTopicLecturerEvaluationResponse> createEvaluation(
            @Valid @RequestBody ShortCourseTopicLecturerEvaluationRequest request
    ) {
        return service.createEvaluation(request);
    }

    /**
     * Get an evaluation by ID.
     */
    @Secured({SuperPrivilege.SUPER_ADMIN, ShortCourseTopicLecturerEvaluationPriv.GET_EVALUATION})
    @Operation(
            summary = "Get Evaluation by ID",
            description = "Retrieves an evaluation by its ID"
    )
    @GetMapping(ShortCourseTopicLecturerEvaluationPriv.GET_EVALUATION_PATH)
    public ResponseEntity<ShortCourseTopicLecturerEvaluationResponse> getEvaluation(
            @Parameter(description = "Evaluation ID") @PathVariable String id
    ) {
        return service.getScTopicLecturerEvaluation(id);
    }
//    /**
//     * Get all evaluations with optional filtering and search.
//     */
//    @Operation(summary = "Get All Evaluations", description = "Retrieves all evaluations with optional filtering and search")
//    @GetMapping
//    public ResponseEntity<List<ShortCourseTopicLecturerEvaluationResponse>> getAllEvaluations(
//            @Parameter(description = "Display filter: archive, all, or active") @RequestParam(defaultValue = "active") String display,
//            @Parameter(description = "Search keyword") @RequestParam(defaultValue = "") String search
//    ) {
//        return service.getAllEvaluations(display, search);
//    }
//
//    /**
//     * Get paged evaluations with filtering, search, sorting, and order by.
//     */
//    @Operation(summary = "Get Paged Evaluations", description = "Retrieves paged evaluations with filtering, search, sorting, and order by")
//    @GetMapping("/paged")
//    public ResponseEntity<PagedResponse<ShortCourseTopicLecturerEvaluationResponse>> getPagedEvaluations(
//            @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
//            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
//            @Parameter(description = "Display filter") @RequestParam(defaultValue = "active") String display,
//            @Parameter(description = "Search keyword") @RequestParam(defaultValue = "") String search,
//            @Parameter(description = "Sort field") @RequestParam(defaultValue = "name") String sort,
//            @Parameter(description = "Order by (asc or desc)") @RequestParam(defaultValue = "asc") String orderBy
//    ) {
//        return service.getPagedEvaluations(page, size, display, search, sort, orderBy);
//    }
//
//    /**
//     * Update an evaluation by ID.
//     */
//    @Operation(summary = "Update Evaluation", description = "Updates an existing evaluation by ID")
//    @PutMapping("/{id}")
//    public ResponseEntity<ShortCourseTopicLecturerEvaluationResponse> updateEvaluation(
//            @Parameter(description = "Evaluation ID") @PathVariable String id,
//            @Valid @RequestBody ShortCourseTopicLecturerEvaluationRequest request
//    ) {
//        return service.updateEvaluation(id, request);
//    }
//
//    /**
//     * Archive (soft delete) an evaluation by ID.
//     */
//    @Operation(summary = "Delete Evaluation", description = "Archives (soft deletes) an evaluation by ID")
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteEvaluation(
//            @Parameter(description = "Evaluation ID") @PathVariable String id
//    ) {
//        return service.deleteEvaluation(id);
//    }
//
//    /**
//     * Restore an archived evaluation by ID.
//     */
//    @Operation(summary = "Undo Delete Evaluation", description = "Restores an archived evaluation by ID")
//    @PatchMapping("/undo-delete/{id}")
//    public ResponseEntity<String> undoDeleteEvaluation(
//            @Parameter(description = "Evaluation ID") @PathVariable String id
//    ) {
//        return service.undoDeleteEvaluation(id);
//    }
}
