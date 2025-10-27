package rw.ac.ilpd.academicservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.academicservice.service.StudentGradeCurriculumModuleService;
import rw.ac.ilpd.mis.shared.config.privilege.academic.StudentGradeCurriculumModulePriv;
import rw.ac.ilpd.mis.shared.config.privilege.auth.SuperPrivilege;
import rw.ac.ilpd.mis.shared.security.Secured;
import rw.ac.ilpd.sharedlibrary.dto.studentgradecurriculummodule.StudentGradeCurriculumModuleRequest;
import rw.ac.ilpd.sharedlibrary.dto.studentgradecurriculummodule.StudentGradeCurriculumModuleResponse;
import java.util.List;

/**
 * Controller for managing StudentGradeCurriculumModule endpoints.
 */
@RestController
@RequestMapping(StudentGradeCurriculumModulePriv.BASE_PATH)
@RequiredArgsConstructor
@Tag(name = "Student grade curriculum Apis", description = "APIs for managing Student grade curriculum (Author) Michael")
public class StudentGradeCurriculumModuleController {

    private final StudentGradeCurriculumModuleService service;

    @Operation(summary = "Create StudentGradeCurriculumModule", description = "Creates a new student grade curriculum module")
    @Secured({StudentGradeCurriculumModulePriv.CREATE_STUDENT_GRADE_CURRICULUM_MODULE, SuperPrivilege.SUPER_ADMIN})
    @PostMapping(StudentGradeCurriculumModulePriv.CREATE_STUDENT_GRADE_CURRICULUM_MODULE_PATH)
    public ResponseEntity<StudentGradeCurriculumModuleResponse> create(
            @Valid @RequestBody StudentGradeCurriculumModuleRequest request
    ) {
        return service.createStudentGradeCurriculumModule(request);
    }
    /**
     * Create a new StudentGradeCurriculumModule.
     */
    @Operation(summary = "Create Student Grade Curriculum Module list", description = "Creates a new student grade curriculum module list")
    @Secured({StudentGradeCurriculumModulePriv.CREATE_STUDENT_GRADE_CURRICULUM_MODULE_LIST, SuperPrivilege.SUPER_ADMIN})
    @PostMapping(StudentGradeCurriculumModulePriv.CREATE_STUDENT_GRADE_CURRICULUM_MODULE_LIST_PATH)
    public ResponseEntity<List<StudentGradeCurriculumModuleResponse>> createList(
            @Valid @RequestBody List<StudentGradeCurriculumModuleRequest> request
    ) {
        return service.createStudentGradeCurriculumModuleList(request);
    }
    /**
     * Get a StudentGradeCurriculumModule by ID.
     */
    @Operation(summary = "Get Student Grade Curriculum Module by ID", description = "Retrieves a student grade curriculum module by its ID")
    @Secured({StudentGradeCurriculumModulePriv.GET_STUDENT_GRADE_CURRICULUM_MODULE_BY_ID, SuperPrivilege.SUPER_ADMIN})
    @GetMapping(StudentGradeCurriculumModulePriv.GET_STUDENT_GRADE_CURRICULUM_MODULE_BY_ID_PATH)
    public ResponseEntity<StudentGradeCurriculumModuleResponse> getById(
            @Parameter(description = "Student Grade Curriculum Module ID") @PathVariable String id
    ) {
        return service.getStudentGradeCurriculumModule(id);
    }

    /**
     * Get all StudentGradeCurriculumModules.
     */
    @Operation(summary = "Get all StudentGradeCurriculumModules", description = "Retrieves all student grade curriculum modules")
    @Secured({StudentGradeCurriculumModulePriv.GET_ALL_STUDENT_GRADE_CURRICULUM_MODULES, SuperPrivilege.SUPER_ADMIN})
    @GetMapping(StudentGradeCurriculumModulePriv.GET_ALL_STUDENT_GRADE_CURRICULUM_MODULES_PATH)
    public ResponseEntity<List<StudentGradeCurriculumModuleResponse>> getAll(@RequestParam(defaultValue = "", required = false) String search) {
        return service.getAllStudentGradeCurriculumModules(search);
    }

    /**
     * Update a StudentGradeCurriculumModule by ID.
     */
    @Operation(summary = "Update StudentGradeCurriculumModule", description = "Updates an existing student grade curriculum module")
    @Secured({StudentGradeCurriculumModulePriv.UPDATE_STUDENT_GRADE_CURRICULUM_MODULE, SuperPrivilege.SUPER_ADMIN})
    @PutMapping(StudentGradeCurriculumModulePriv.UPDATE_STUDENT_GRADE_CURRICULUM_MODULE_PATH)
    public ResponseEntity<StudentGradeCurriculumModuleResponse> update(
            @Parameter(description = "Student Grade Curriculum Module ID") @PathVariable String id,
            @Valid @RequestBody StudentGradeCurriculumModuleRequest request
    ) {
        return service.updateStudentGradeCurriculumModule(id, request);
    }

    /**
     * Delete a StudentGradeCurriculumModule by ID.
     */
    @Operation(summary = "Delete StudentGradeCurriculumModule", description = "Deletes a student grade curriculum module by its ID")
    @Secured({StudentGradeCurriculumModulePriv.DELETE_STUDENT_GRADE_CURRICULUM_MODULE, SuperPrivilege.SUPER_ADMIN})
    @DeleteMapping(StudentGradeCurriculumModulePriv.DELETE_STUDENT_GRADE_CURRICULUM_MODULE_PATH)
    public ResponseEntity<String> delete(
            @Parameter(description = "Student Grade Curriculum Module ID") @PathVariable String id
    ) {
        return service.deleteStudentGradeCurriculumModule(id);
    }
}
