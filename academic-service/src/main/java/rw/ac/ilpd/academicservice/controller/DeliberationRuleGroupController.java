package rw.ac.ilpd.academicservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.academicservice.service.DeliberationRuleGroupService;
import rw.ac.ilpd.sharedlibrary.dto.deliberationrulegroup.DeliberationRuleGroupRequest;
import rw.ac.ilpd.sharedlibrary.dto.deliberationrulegroup.DeliberationRuleGroupResponse;
import rw.ac.ilpd.sharedlibrary.dto.deliberationrulegroupcurriculum.DeliberationRuleGroupCurriculumRequest;
import rw.ac.ilpd.sharedlibrary.dto.deliberationrulesthreshold.DeliberationRulesThresholdRequest;

import java.util.List;

@RestController
@RequestMapping("/deliberation-rule-groups")
@RequiredArgsConstructor
@Tag(name = "Deliberation Rule Groups", description = "Manage deliberation rule groups and their embedded entities")
public class DeliberationRuleGroupController {

    private final DeliberationRuleGroupService service;

    @PostMapping
    @Operation(summary = "Create a new deliberation rule group")
    public ResponseEntity<DeliberationRuleGroupResponse> create(@Valid @RequestBody DeliberationRuleGroupRequest request) {
        return new ResponseEntity<>(service.create(request), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all deliberation rule groups")
    public ResponseEntity<List<DeliberationRuleGroupResponse>> getAll(
            @RequestParam(defaultValue = "CURRENT") String status) {
        return ResponseEntity.ok(service.getAll(status));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a specific deliberation rule group by ID")
    public ResponseEntity<DeliberationRuleGroupResponse> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a deliberation rule group by ID")
    public ResponseEntity<DeliberationRuleGroupResponse> update(@PathVariable String id, @Valid @RequestBody DeliberationRuleGroupRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a deliberation rule group by ID")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    // ---------------- CURRICULUMS ----------------

    @PostMapping("/{id}/deliberation-rule-group-curriculums")
    @Operation(summary = "Add a deliberation rule group curriculum")
    public ResponseEntity<DeliberationRuleGroupResponse> addCurriculum(@PathVariable String id,
                                                                       @Valid @RequestBody DeliberationRuleGroupCurriculumRequest request) {
        return new ResponseEntity<>(service.addDeliberationRuleGroupCurriculum(id, request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/deliberation-rule-group-curriculums/{curriculumId}")
    @Operation(summary = "Remove a deliberation rule group curriculum")
    public ResponseEntity<Void> removeCurriculum(@PathVariable String id, @PathVariable String curriculumId) {
        service.removeDeliberationRuleGroupCurriculum(id, curriculumId);
        return ResponseEntity.ok().build();
    }

    // ---------------- THRESHOLDS ----------------

    @PostMapping("/{id}/deliberation-rules-thresholds")
    @Operation(summary = "Add a deliberation rules threshold")
    public ResponseEntity<DeliberationRuleGroupResponse> addThreshold(@PathVariable String id,
                                                                      @Valid @RequestBody DeliberationRulesThresholdRequest request) {
        return new ResponseEntity<>(service.addDeliberationRulesThreshold(id, request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/deliberation-rules-thresholds/{thresholdId}")
    @Operation(summary = "Remove a deliberation rules threshold")
    public ResponseEntity<Void> removeThreshold(@PathVariable String id, @PathVariable String thresholdId) {
        service.removeDeliberationRulesThreshold(id, thresholdId);
        return ResponseEntity.ok().build();
    }
}