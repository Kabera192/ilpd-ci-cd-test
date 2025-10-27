package rw.ac.ilpd.academicservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.academicservice.service.AssessmentTypeService;
import rw.ac.ilpd.mis.shared.config.privilege.academic.AssessmentTypePriv;
import rw.ac.ilpd.mis.shared.config.privilege.auth.SuperPrivilege;
import rw.ac.ilpd.mis.shared.security.Secured;
import rw.ac.ilpd.sharedlibrary.dto.assessmenttype.AssessmentTypeRequest;
import rw.ac.ilpd.sharedlibrary.dto.assessmenttype.AssessmentTypeResponse;

import java.util.List;

@RestController
@RequestMapping(AssessmentTypePriv.BASE_PATH)
@RequiredArgsConstructor
@Tag(name = "Assessment Type Apis", description = "Endpoints for managing assessment types")
public class AssessmentTypeController {
    private final AssessmentTypeService assessmentTypeService;

    @Secured({SuperPrivilege.SUPER_ADMIN, AssessmentTypePriv.CREATE_ASSESSMENT_TYPE})
    @PostMapping(AssessmentTypePriv.CREATE_ASSESSMENT_TYPE_PATH)
    @Operation(summary = "Create a new assessment type",
            description = "Creates a new assessment type associated with an assessment group.")
    public ResponseEntity<AssessmentTypeResponse> createAssessmentType(@Valid @RequestBody AssessmentTypeRequest request) {
        return assessmentTypeService.createAssessmentType(request);
    }

   @Secured({SuperPrivilege.SUPER_ADMIN,AssessmentTypePriv.UPDATE_ASSESSMENT_TYPE})
    @PutMapping(AssessmentTypePriv.UPDATE_ASSESSMENT_TYPE_PATH)
    @Operation(summary = "Update an assessment type",
            description = "Updates an existing assessment type by its unique ID.")
    public ResponseEntity<String> updateAssessmentType(@PathVariable String id, @Valid @RequestBody AssessmentTypeRequest request) {
       return assessmentTypeService.updateAssessmentType(id,request);
    }

    @Secured({AssessmentTypePriv.DELETE_ASSESSMENT_TYPE,SuperPrivilege.SUPER_ADMIN})
    @DeleteMapping(AssessmentTypePriv.DELETE_ASSESSMENT_TYPE_PATH)
    @Operation(summary = "Delete an assessment type",
            description = "Deletes an existing assessment type by its unique ID.")
    public ResponseEntity<String> deleteAssessmentType(@PathVariable String id) {
        return assessmentTypeService.deleteAssessmentType(id);
    }

//    Get mapping
    @Secured({SuperPrivilege.SUPER_ADMIN,AssessmentTypePriv.GET_ALL_ASSESSMENT_TYPES})
    @GetMapping(AssessmentTypePriv.GET_ALL_ASSESSMENT_TYPES_PATH)
    @Operation(summary = "Get all assessment types",
            description = "Retrieves a list of all assessment types.")
    public ResponseEntity<List<AssessmentTypeResponse>> getAllAssessmentTypes(@RequestParam(defaultValue = "active")String display) {
        return assessmentTypeService.getAllAssessmentType(display);
    }

    @Secured({SuperPrivilege.SUPER_ADMIN,AssessmentTypePriv.GET_ASSESSMENT_TYPE_BY_ID})
    @GetMapping(AssessmentTypePriv.GET_ASSESSMENT_TYPE_BY_ID_PATH)
    @Operation(summary = "Get an assessment type by ID",
            description = "Retrieves an assessment type by its unique ID.")
    public ResponseEntity<AssessmentTypeResponse> getAssessmentTypeById(@PathVariable String id) {
        return assessmentTypeService.findAssessmentById(id);
    }

    @Secured({SuperPrivilege.SUPER_ADMIN,AssessmentTypePriv.GET_ASSESSMENT_TYPE_BY_ASSESSMENT_GROUP})
    @GetMapping(AssessmentTypePriv.GET_ASSESSMENT_TYPE_BY_ASSESSMENT_GROUP_PATH)
    @Operation(summary = "Get an assessment type by assessment group id",
            description = "Retrieves all assessment type found in referenced assessment group id.")
    public  ResponseEntity<List<AssessmentTypeResponse>> getAssessmentTypeByAssessmentGroupId(
            @PathVariable String assessmentGroupId
            ,@RequestParam(defaultValue = "active")String display
    ) {
        List<AssessmentTypeResponse> response = assessmentTypeService.findAllAssessmentTypeWithInAssessmentGroup(assessmentGroupId, display);
        return ResponseEntity.ok(response);
    }
}
