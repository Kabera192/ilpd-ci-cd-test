package rw.ac.ilpd.mis.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.mis.auth.service.WorkExperienceService;
import rw.ac.ilpd.mis.shared.api.WorkExperienceApi;
import rw.ac.ilpd.mis.shared.dto.pagination.PagedResponse;
import rw.ac.ilpd.mis.shared.dto.userprofile.WorkExperienceRequest;
import rw.ac.ilpd.mis.shared.dto.userprofile.WorkExperienceResponse;
@RestController
@RequestMapping("/api/v1/auth/work-experiences")
@RequiredArgsConstructor
public class WorkExperienceController implements WorkExperienceApi {
private final WorkExperienceService service;

    @PostMapping(path = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
    public ResponseEntity<WorkExperienceResponse> create(@ModelAttribute @Valid  WorkExperienceRequest request) {
        WorkExperienceResponse response =service.create(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
    public ResponseEntity<WorkExperienceResponse> update(@PathVariable String id, @ModelAttribute @Valid  WorkExperienceRequest request) {
        WorkExperienceResponse response =service.update(id,request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Override
    public ResponseEntity<PagedResponse<WorkExperienceResponse>> getPrincipleUserWorkExperience(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int pageSize,
            @RequestParam(defaultValue = "startDate") String sortBy,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam(defaultValue = "") String search
            ) {
        Pageable pageable = PageRequest.of(page,pageSize, Sort.by(sortBy));
        return ResponseEntity.ok(service.getPrincipleUserWorkExperience(pageable,search));
    }
}
