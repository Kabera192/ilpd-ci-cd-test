package rw.ac.ilpd.academicservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.academicservice.service.ModuleComponentAssessmentService;
import rw.ac.ilpd.sharedlibrary.dto.assessment.ModuleComponentAssessmentRequest;
import rw.ac.ilpd.sharedlibrary.dto.assessment.ModuleComponentAssessmentResponse;
import rw.ac.ilpd.sharedlibrary.dto.assessmentattachment.AssessmentAttachmentRequest;
import rw.ac.ilpd.sharedlibrary.dto.assessmentattachment.AssessmentAttachmentResponse;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/module-component-assessment")
@RequiredArgsConstructor
@Tag(name = "Module Component Assessments", description = "Operations related to module component assessments")
public class ModuleComponentAssessmentController {

    private final ModuleComponentAssessmentService assessmentService;

    // ========== ASSESSMENT CRUD ==========

    @Operation(summary = "Create a new module component assessment", responses = {
            @ApiResponse(responseCode = "201", description = "Assessment created successfully")
    })
    @PostMapping
    public ResponseEntity<ModuleComponentAssessmentResponse> create(
            @Valid @RequestBody ModuleComponentAssessmentRequest request) {
        ModuleComponentAssessmentResponse response = assessmentService.create(request);
        return ResponseEntity.created(URI.create("/module-component-assessment/" + response.getId())).body(response);
    }

    @Operation(summary = "Get assessment by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ModuleComponentAssessmentResponse> getById(
            @Parameter(description = "Assessment ID") @PathVariable String id) {
        return ResponseEntity.ok(assessmentService.getById(id));
    }

    @Operation(summary = "List all assessments")
    @GetMapping
    public ResponseEntity<List<ModuleComponentAssessmentResponse>> getAll() {
        return ResponseEntity.ok(assessmentService.getAll());
    }

    @Operation(summary = "Delete assessment by ID", responses = {
            @ApiResponse(responseCode = "204", description = "Assessment deleted successfully")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Assessment ID") @PathVariable String id) {
        assessmentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ========== ATTACHMENTS ==========

    @Operation(summary = "Add attachment to assessment", responses = {
            @ApiResponse(responseCode = "201", description = "Attachment created successfully")
    })
    @PostMapping("/{id}/attachments")
    public ResponseEntity<AssessmentAttachmentResponse> addAttachment(
            @Parameter(description = "Assessment ID") @PathVariable String id,
            @Valid @RequestBody AssessmentAttachmentRequest request) {
        AssessmentAttachmentResponse response = assessmentService.addAttachment(id, request);
        return ResponseEntity.created(URI.create("/module-component-assessment/" + id + "/attachments/" + response.getId()))
                .body(response);
    }

    @Operation(summary = "List attachments for an assessment")
    @GetMapping("/{id}/attachments")
    public ResponseEntity<List<AssessmentAttachmentResponse>> getAttachments(
            @Parameter(description = "Assessment ID") @PathVariable String id) {
        return ResponseEntity.ok(assessmentService.getAttachments(id));
    }

    @Operation(summary = "Delete an attachment from an assessment", responses = {
            @ApiResponse(responseCode = "204", description = "Attachment deleted successfully")
    })
    @DeleteMapping("/{id}/attachments/{attachmentId}")
    public ResponseEntity<Void> deleteAttachment(
            @Parameter(description = "Assessment ID") @PathVariable String id,
            @Parameter(description = "Attachment ID") @PathVariable String attachmentId) {
        assessmentService.deleteAttachment(id, attachmentId);
        return ResponseEntity.noContent().build();
    }
}
