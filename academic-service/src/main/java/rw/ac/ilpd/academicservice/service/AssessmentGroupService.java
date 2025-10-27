package rw.ac.ilpd.academicservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.ilpd.academicservice.exception.EntityAlreadyExists;
import rw.ac.ilpd.academicservice.mapper.AssessmentGroupMapper;
import rw.ac.ilpd.academicservice.model.sql.AssessmentGroup;
import rw.ac.ilpd.academicservice.repository.sql.AssessmentGroupRepository;
import rw.ac.ilpd.sharedlibrary.dto.assessmentgroup.AssessmentGroupRequest;
import rw.ac.ilpd.sharedlibrary.dto.assessmentgroup.AssessmentGroupResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AssessmentGroupService {

    private final AssessmentGroupRepository repository;
    private final AssessmentGroupMapper mapper;

    public PagedResponse<AssessmentGroupResponse> getAll(int page, int size, String sortBy, String order) {
        Pageable pageable = PageRequest.of(page, size,
                order.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending());

        Page<AssessmentGroup> assessmentGroupPage = repository.findAll(pageable);

        return PagedResponse.<AssessmentGroupResponse>builder()
                .content(assessmentGroupPage.getContent().stream().map(mapper::fromAssessmentGroup).toList())
                .pageNumber(assessmentGroupPage.getNumber())
                .pageSize(assessmentGroupPage.getSize())
                .totalElements(assessmentGroupPage.getTotalElements())
                .last(assessmentGroupPage.isLast())
                .totalPages(assessmentGroupPage.getTotalPages())
                .build();
    }

    public AssessmentGroupResponse get(UUID id) {
        AssessmentGroup group = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("AssessmentGroup not found"));
        return mapper.fromAssessmentGroup(group);
    }

    public AssessmentGroupResponse create(AssessmentGroupRequest request) {
        if (repository.existsByNameIgnoreCase(request.getName())) {
            throw new EntityAlreadyExists("AssessmentGroup already exists with name: " + request.getName());
        }

        AssessmentGroup entity = mapper.toAssessmentGroup(request);
        return mapper.fromAssessmentGroup(repository.save(entity));
    }

    public AssessmentGroupResponse edit(UUID id, AssessmentGroupRequest request) {
        AssessmentGroup existing = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("AssessmentGroup not found"));
        existing.setName(request.getName());
        return mapper.fromAssessmentGroup(repository.save(existing));
    }

    public AssessmentGroupResponse patch(UUID id, Map<String, Object> updates) {
        AssessmentGroup existing = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("AssessmentGroup not found"));

        if (updates.containsKey("name")) {
            Object nameValue = updates.get("name");
            if (nameValue instanceof String name && !name.isBlank()) {
                existing.setName(name);
            }
        }

        return mapper.fromAssessmentGroup(repository.save(existing));
    }

    public Boolean delete(UUID id) {
        AssessmentGroup entity = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("AssessmentGroup not found"));
        repository.delete(entity);
        return true;
    }
    @Transactional(readOnly = true)
    public Optional<AssessmentGroup> getEntity(UUID id) {
        return repository.findById(id);
    }
}
