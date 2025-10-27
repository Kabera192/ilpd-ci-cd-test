package rw.ac.ilpd.academicservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.academicservice.service.StudentAssessmentSubmissionService;
import rw.ac.ilpd.sharedlibrary.dto.assessment.AssessmentSubmissionStats;
import rw.ac.ilpd.sharedlibrary.dto.assessment.StudentAssessmentSubmissionRequest;
import rw.ac.ilpd.sharedlibrary.dto.assessment.StudentAssessmentSubmissionResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;

import java.util.UUID;

/**
 * Controller that handles all endpoints that deal with the StudentAssessmentSubmission resource.
 */
@RestController
@RequestMapping("/assessment-submissions")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Student Assessment Submission", description = "Endpoints for managing student assessment submissions")
public class StudentAssessmentSubmissionController
{
    private final StudentAssessmentSubmissionService studentAssessmentSubmissionService;

    /**
     * Endpoint to create a new StudentAssessmentSubmission
     */
    @Operation(summary = "Create a new student assessment submission",
            description = "Creates a new student assessment submission.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Student assessment submission created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentAssessmentSubmissionResponse> createStudentAssessmentSubmission(
            @Parameter(description = "Request payload for creating an student assessment submission")
            @ModelAttribute @Valid @RequestBody StudentAssessmentSubmissionRequest submissionRequest)
    {
        log.debug("Create student assessment submission endpoint reached for request: {}"
                , submissionRequest);
        return new ResponseEntity<>(studentAssessmentSubmissionService
                .createStudentAssessmentSubmission(submissionRequest), HttpStatus.CREATED);
    }

    /**
     * Endpoint to get all student assessment submission
     */
    @Operation(summary = "Get all student assessment submission",
            description = "Retrieves a paginated list of student assessment submission with sorting options.")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Submissions retrieved successfully with pagination metadata"),
            @ApiResponse(responseCode = "400",
                    description = "Invalid pagination or sorting parameters")
    })
    @GetMapping
    public ResponseEntity<PagedResponse<StudentAssessmentSubmissionResponse>> getAllStudentAssessmentSubmission(
            @Parameter(description = "Page number (0-based, default: 0)")
            @RequestParam(required = false, defaultValue = "0") int page,
            @Parameter(description = "Number of items per page (default: 10)")
            @RequestParam(required = false, defaultValue = "10") int size,
            @Parameter(description = "Sort field (e.g., 'submittedAt', default: 'submittedAt')")
            @RequestParam(name = "sort-by", required = false, defaultValue = "submittedAt") String sortBy,
            @Parameter(description = "Sort direction (asc or desc, default: desc)")
            @RequestParam(required = false, defaultValue = "desc") String order
    )
    {
        log.info("Get all student assessment submission endpoint reached");
        return ResponseEntity.ok(studentAssessmentSubmissionService
                .getAllStudentAssessmentSubmissions(page, size, sortBy, order));
    }

    /**
     * Endpoint to get a student assessment submission by id
     */
    @Operation(summary = "Get an student assessment submission by ID",
            description = "Retrieves an student assessment submission by its unique ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "student assessment submission found"),
            @ApiResponse(responseCode = "404", description = "student assessment submission not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<StudentAssessmentSubmissionResponse> getStudentAssessmentSubmission(
            @Parameter(description = "ID of the student assessment submission to retrieve")
            @Valid @NotNull(message = "student assessment submission id must not be null")
            @NotBlank(message = "student assessment submission id must not be blank") @PathVariable String id)
    {
        log.debug("Get student assessment submission by id endpoint reached for request: {}", id);
        return ResponseEntity.ok(studentAssessmentSubmissionService.getStudentAssessmentSubmission(id));
    }

    /**
     * Endpoint to update the entire student assessment submission resource
     */
//    @Operation(summary = "Update an student assessment submission",
//            description = "Updates an existing student assessment submission by its ID.")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "student assessment submission updated successfully"),
//            @ApiResponse(responseCode = "400", description = "Invalid request data"),
//            @ApiResponse(responseCode = "404", description = "student assessment submission not found"),
//            @ApiResponse(responseCode = "500", description = "Internal server error")
//    })
//    @PutMapping("/{id}")
//    public ResponseEntity<StudentAssessmentSubmissionResponse> updateStudentAssessmentSubmission(
//            @Parameter(description = "ID of the student assessment submission to update")
//            @Valid @NotNull(message = "student assessment submission id must not be null")
//            @NotBlank(message = "student assessment submission id must not be blank") @PathVariable String id,
//            @Parameter(description = "Request payload for updating the student assessment submission")
//            @Valid @RequestBody StudentAssessmentSubmissionRequest submissionRequest
//    )
//    {
//        log.debug("Update student assessment submission endpoint reached with request: {}", submissionRequest);
//        return ResponseEntity.ok(studentAssessmentSubmissionService
//                .updateStudentAssessmentSubmission(id, submissionRequest));
//    }

    /**
     * Endpoint to permanently delete a student assessment submission entity
     */
    @Operation(summary = "Delete an student assessment submission",
            description = "Permanently deletes an student assessment submission by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "student assessment submission deleted successfully"),
            @ApiResponse(responseCode = "404", description = "student assessment submission not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteStudentAssessmentSubmission(
            @Parameter(description = "ID of the student assessment submission to delete")
            @Valid @NotNull(message = "student assessment submission id must not be null")
            @NotBlank(message = "student assessment submission id must not be blank") @PathVariable String id
    )
    {
        log.debug("permanently delete student assessment submission endpoint reached for request: {}", id);
        return new ResponseEntity<>(studentAssessmentSubmissionService
                .deleteStudentAssessmentSubmission(id), HttpStatus.OK);
    }

    /*
    * Endpoint to find all submissions by the student id
    * */
    @Operation(summary = "Find assessment submission by student id",
            description = "This endpoint returns a paged list of submissions by a student id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of submissions returned successfully"),
            @ApiResponse(responseCode = "404", description = "student not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/student/{studentId}")
    public ResponseEntity<PagedResponse<StudentAssessmentSubmissionResponse>> getSubmissionsByStudentId(
            @Parameter(description = "ID of the student for whom to get submissions") @PathVariable String studentId,
            @Parameter(description = "Page number (0-based, default: 0)")
            @RequestParam(required = false, defaultValue = "0") int page,
            @Parameter(description = "Number of items per page (default: 10)")
            @RequestParam(required = false, defaultValue = "10") int size,
            @Parameter(description = "Sort field (e.g., 'submittedAt', default: 'submittedAt')")
            @RequestParam(name = "sort-by", required = false, defaultValue = "submittedAt") String sortBy,
            @Parameter(description = "Sort direction (asc or desc, default: desc)")
            @RequestParam(required = false, defaultValue = "desc") String order
    )
    {
        log.debug("getSubmissionsByStudentId by student id endpoint reached: {}", studentId);
        return ResponseEntity.ok(studentAssessmentSubmissionService
                .getSubmissionsByStudent(studentId, page, size, sortBy, order));
    }

    /*
    * Endpoint to grade a particular assessment submission
    * */
    @Operation(summary = "Grade an assessment submission",
            description = "This endpoint enables the grading of a particular submission")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Submission graded successfully"),
            @ApiResponse(responseCode = "404", description = "submission not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PatchMapping("/{submissionId}")
    public ResponseEntity<StudentAssessmentSubmissionResponse> gradeSubmission(
            @Parameter(description = "ID of the submission to be graded")
            @Valid @NotBlank(message = "submission id is required") @PathVariable String submissionId,
            @Parameter(description = "grade that has been awarded to a submission")
            @RequestParam(name = "grade") Double grade
    )
    {
        log.debug("grade submission with id endpoint reached: {}", submissionId);
        return ResponseEntity.ok(studentAssessmentSubmissionService
                .gradeSubmission(submissionId, grade));
    }

    /*
     * Endpoint to get all student assessment submission by a particular
     * module component assessment id
     */
    @Operation(summary = "Get all student assessment submission by a particular module component assessment id",
            description = "Retrieves a paginated list of student assessment submission by assessment id" +
                    " with sorting options.")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Submissions retrieved successfully with pagination metadata"),
            @ApiResponse(responseCode = "400",
                    description = "Invalid pagination or sorting parameters")
    })
    @GetMapping("/{assessmentId}")
    public ResponseEntity<PagedResponse<StudentAssessmentSubmissionResponse>> getSubmissionsByAssessmentId(
            @Parameter(description = "ModuleComponentAssessment id whose submissions you need")
            @PathVariable String assessmentId,
            @Parameter(description = "Page number (0-based, default: 0)")
            @RequestParam(required = false, defaultValue = "0") int page,
            @Parameter(description = "Number of items per page (default: 10)")
            @RequestParam(required = false, defaultValue = "10") int size,
            @Parameter(description = "Sort field (e.g., 'submittedAt', default: 'submittedAt')")
            @RequestParam(name = "sort-by", required = false, defaultValue = "submittedAt") String sortBy,
            @Parameter(description = "Sort direction (asc or desc, default: desc)")
            @RequestParam(required = false, defaultValue = "desc") String order
    )
    {
        log.info("Get all student assessment submission by assessment id endpoint reached");
        return ResponseEntity.ok(studentAssessmentSubmissionService
                .getSubmissionsByComponentAssessment(assessmentId, page, size, sortBy, order));
    }

    /*
     * Endpoint to get statistics about the submissions that have been made.
     */
    @Operation(summary = "Get statistics for the submissions made.",
            description = "Retrieves statistics of the submissions made for a particular assessment.")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Statistics retrieved successfully"),
            @ApiResponse(responseCode = "404",
                    description = "Assessment requested not found.")
    })
    @GetMapping("/stats/{assessmentId}")
    public ResponseEntity<AssessmentSubmissionStats> getSubmissionsByModuleComponentId(
            @Parameter(description = "ModuleComponentAssessment id whose submissions you need")
            @PathVariable String assessmentId)
    {
        log.info("Get stats for student assessment submission by assessment id endpoint reached");
        return ResponseEntity.ok(studentAssessmentSubmissionService
                .getSubmissionStats(assessmentId));
    }

    /*
     * Endpoint to get all student assessment submission by a particular
     * module component assessment id that are not graded
     */
    @Operation(summary = "Get all student assessment submission by a particular module component assessment id" +
            " that are not graded.",
            description = "Retrieves a paginated list of student assessment submission by assessment id" +
                    " that are not graded with sorting options.")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Submissions retrieved successfully with pagination metadata"),
            @ApiResponse(responseCode = "400",
                    description = "Invalid pagination or sorting parameters")
    })
    @GetMapping("/ungraded/{assessmentId}")
    public ResponseEntity<PagedResponse<StudentAssessmentSubmissionResponse>> getUngradedSubmissionsByAssessmentId(
            @Parameter(description = "ModuleComponentAssessment id whose submissions you need")
            @PathVariable String assessmentId,
            @Parameter(description = "Page number (0-based, default: 0)")
            @RequestParam(required = false, defaultValue = "0") int page,
            @Parameter(description = "Number of items per page (default: 10)")
            @RequestParam(required = false, defaultValue = "10") int size,
            @Parameter(description = "Sort field (e.g., 'submittedAt', default: 'submittedAt')")
            @RequestParam(name = "sort-by", required = false, defaultValue = "submittedAt") String sortBy,
            @Parameter(description = "Sort direction (asc or desc, default: desc)")
            @RequestParam(required = false, defaultValue = "desc") String order
    )
    {
        log.info("Get all student assessment submission by assessment id that are not graded endpoint reached");
        return ResponseEntity.ok(studentAssessmentSubmissionService
                .getUngradedSubmissions(assessmentId, page, size, sortBy, order));
    }

    /*
     * Endpoint to get all student assessment submission by a particular
     * module component assessment id and student id
     */
    @Operation(summary = "Get all student assessment submission by a particular module component assessment id" +
            " and student id",
            description = "Retrieves a paginated list of student assessment submission by assessment id" +
                    " and student id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Submissions retrieved successfully"),
            @ApiResponse(responseCode = "400",
                    description = "Invalid pagination or sorting parameters")
    })
    @GetMapping("/student/{studentId}/assessment/{assessmentId}")
    public ResponseEntity<StudentAssessmentSubmissionResponse> getSubmissionsByAssessmentIdAndStudentId(
            @Parameter(description = "ModuleComponentAssessment id whose submissions you need")
            @PathVariable String assessmentId,
            @Parameter(description = "Student id whose submissions you need")
            @PathVariable String studentId)
    {
        log.info("Get all student assessment submission by assessment id and student id endpoint reached");
        return ResponseEntity.ok(studentAssessmentSubmissionService
                .getSubmissionsByStudentAndAssessment(assessmentId, studentId));
    }
}