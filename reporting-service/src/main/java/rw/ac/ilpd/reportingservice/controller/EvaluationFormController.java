package rw.ac.ilpd.reportingservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.reportingservice.service.EvaluationFormService;
import rw.ac.ilpd.sharedlibrary.dto.evaluation.*;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/evaluation-forms")
@RequiredArgsConstructor
@Tag(name = "Evaluation Forms", description = "Manage evaluation forms, user fillers, and answers")
public class EvaluationFormController {

    private final EvaluationFormService service;

    // ------------------ FORM ENDPOINTS ------------------

    @Operation(summary = "Create a new evaluation form")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Form created successfully")
    })
    @PostMapping
    public ResponseEntity<EvaluationFormResponse> createForm(@RequestBody EvaluationFormRequest request) {
        return ResponseEntity.ok(service.createForm(request));
    }

    @Operation(summary = "Get an evaluation form by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Form found"),
            @ApiResponse(responseCode = "404", description = "Form not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EvaluationFormResponse> getForm(@PathVariable String id) {
        Optional<EvaluationFormResponse> response = service.getForm(id);
        return response.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Get all evaluation forms (paginated and sorted)",
            description = "Retrieve all evaluation forms with pagination and sorting options"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Forms retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<PagedResponse<EvaluationFormResponse>> getAllForms(
            @Parameter(description = "Page number (0-based index)")
            @RequestParam(name = "page", defaultValue = "0") int page,

            @Parameter(description = "Number of items per page")
            @RequestParam(name = "size", defaultValue = "10") int size,

            @Parameter(description = "Field name to sort by")
            @RequestParam(name = "sort-by", defaultValue = "id") String sortBy,

            @Parameter(description = "Sort order: asc or desc")
            @RequestParam(name = "order", defaultValue = "asc") String order
    ) {
        return ResponseEntity.ok(service.getAllForms(page, size, sortBy, order));
    }


    @Operation(summary = "Delete an evaluation form by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Form deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Form not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteForm(@PathVariable String id) {
        return service.deleteForm(id) ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    // ------------------ USER FILLER ENDPOINTS ------------------

    @Operation(summary = "Add a user filler to a form")
    @PostMapping("/{formId}/user-fillers")
    public ResponseEntity<EvaluationFormUserFillerResponse> addUserFiller(
            @PathVariable String formId,
            @RequestBody EvaluationFormUserFillerRequest request) {
        return service.addUserFiller(formId, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update a user filler inside a form")
    @PutMapping("/{formId}/user-fillers/{fillerId}")
    public ResponseEntity<EvaluationFormUserFillerResponse> updateUserFiller(
            @PathVariable String formId,
            @PathVariable String fillerId,
            @RequestBody EvaluationFormUserFillerRequest request) {
        return service.updateUserFiller(formId, fillerId, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a user filler inside a form")
    @DeleteMapping("/{formId}/user-fillers/{fillerId}")
    public ResponseEntity<Void> deleteUserFiller(
            @PathVariable String formId,
            @PathVariable String fillerId) {
        return service.deleteUserFiller(formId, fillerId) ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @Operation(summary = "List all user fillers for a form")
    @GetMapping("/{formId}/user-fillers")
    public ResponseEntity<List<EvaluationFormUserFillerResponse>> listUserFillers(
            @PathVariable String formId) {
        return ResponseEntity.ok(service.listUserFillers(formId));
    }

    // ------------------ ANSWER ENDPOINTS ------------------

    @Operation(summary = "Add an answer to a form")
    @PostMapping("/{formId}/answers")
    public ResponseEntity<EvaluationFormAnswerResponse> addAnswer(
            @PathVariable String formId,
            @RequestBody EvaluationFormAnswerRequest request) {
        return service.addAnswer(formId, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update an answer inside a form")
    @PutMapping("/{formId}/answers/{answerId}")
    public ResponseEntity<EvaluationFormAnswerResponse> updateAnswer(
            @PathVariable String formId,
            @PathVariable String answerId,
            @RequestBody EvaluationFormAnswerRequest request) {
        return service.updateAnswer(formId, answerId, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete an answer inside a form")
    @DeleteMapping("/{formId}/answers/{answerId}")
    public ResponseEntity<Void> deleteAnswer(
            @PathVariable String formId,
            @PathVariable String answerId) {
        return service.deleteAnswer(formId, answerId) ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @Operation(summary = "List all answers for a form")
    @GetMapping("/{formId}/answers")
    public ResponseEntity<List<EvaluationFormAnswerResponse>> listAnswers(
            @PathVariable String formId) {
        return ResponseEntity.ok(service.listAnswers(formId));
    }
}
