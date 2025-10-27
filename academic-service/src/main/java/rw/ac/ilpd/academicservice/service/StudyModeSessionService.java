package rw.ac.ilpd.academicservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.academicservice.exception.ConflictException;
import rw.ac.ilpd.academicservice.mapper.StudyModeSessionMapper;
import rw.ac.ilpd.academicservice.model.sql.AssessmentGroup;
import rw.ac.ilpd.academicservice.model.sql.StudyMode;
import rw.ac.ilpd.academicservice.model.sql.StudyModeSession;
import rw.ac.ilpd.academicservice.repository.sql.StudyModeSessionRepository;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.studymodesession.StudyModeSessionRequest;
import rw.ac.ilpd.sharedlibrary.dto.studymodesession.StudyModeSessionResponse;

import java.time.DayOfWeek;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudyModeSessionService {

    private final StudyModeSessionRepository repository;
    private final StudyModeService studyModeService;
    private final AssessmentGroupService assessmentGroupService;
    private final StudyModeSessionMapper mapper;

    public PagedResponse<StudyModeSessionResponse> getAll(int page, int size, String sortBy, String order) {
        Pageable pageable = order.equals("desc")
                ? PageRequest.of(page, size, Sort.by(sortBy).descending())
                : PageRequest.of(page, size, Sort.by(sortBy).ascending());

        Page<StudyModeSession> entityPage = repository.findAll(pageable);
        return new PagedResponse<>(
                entityPage.getContent().stream().map(mapper::fromStudyModeSession).toList(),
                entityPage.getNumber(),
                entityPage.getSize(),
                entityPage.getTotalElements(),
                entityPage.getTotalPages(),
                entityPage.isLast()
        );
    }

    public StudyModeSessionResponse get(UUID id) {
        return mapper.fromStudyModeSession(
                getEntity(id).orElseThrow(() -> new EntityNotFoundException("StudyModeSession not found"))
        );
    }

    public StudyModeSessionResponse create(StudyModeSessionRequest request) {
        StudyMode studyMode = studyModeService.getEntity(UUID.fromString(request.getStudyModeId()))
                .orElseThrow(() -> new EntityNotFoundException("StudyMode not found"));

        AssessmentGroup assessmentGroup = assessmentGroupService.getEntity(UUID.fromString(request.getAssessmentGroupId()))
                .orElseThrow(() -> new EntityNotFoundException("AssessmentGroup not found"));

        StudyModeSession sms = repository.findAll()
                .stream()
                .filter(x -> x.getName().equals(request.getName()) && Objects.equals(x.getStudyMode().getId().toString(), request.getStudyModeId()))
                .findFirst()
                .orElse(null);

        if(sms != null)
            throw new ConflictException("Already defined a same study mode session");

        StudyModeSession entity = mapper.toStudyModeSession(request, studyMode, assessmentGroup);
        return mapper.fromStudyModeSession(repository.save(entity));
    }

    public StudyModeSessionResponse edit(UUID id, StudyModeSessionRequest request) {
        StudyModeSession existing = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("StudyModeSession not found"));

        StudyMode studyMode = studyModeService.getEntity(UUID.fromString(request.getStudyModeId()))
                .orElseThrow(() -> new EntityNotFoundException("StudyMode not found"));

        AssessmentGroup assessmentGroup = assessmentGroupService.getEntity(UUID.fromString(request.getAssessmentGroupId()))
                .orElseThrow(() -> new EntityNotFoundException("AssessmentGroup not found"));

        StudyModeSession updated = mapper.toStudyModeSession(request, studyMode, assessmentGroup);
        updated.setId(existing.getId());
        updated.setCreatedAt(existing.getCreatedAt());
        return mapper.fromStudyModeSession(repository.save(updated));
    }

    public StudyModeSessionResponse patch(UUID id, Map<String, Object> updates) {
        StudyModeSession existing = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("StudyModeSession not found"));

        if (updates.containsKey("name")) {
            Object value = updates.get("name");
            if (value instanceof String name && !name.isBlank()) {
                existing.setName(name);
            }
        }

        if (updates.containsKey("studyModeId")) {
            Object value = updates.get("studyModeId");
            if (value instanceof String studyModeIdStr) {
                UUID studyModeId = UUID.fromString(studyModeIdStr);
                StudyMode studyMode = studyModeService.getEntity(studyModeId)
                        .orElseThrow(() -> new EntityNotFoundException("StudyMode not found"));
                existing.setStudyMode(studyMode);
            }
        }

        if (updates.containsKey("assessmentGroupId")) {
            Object value = updates.get("assessmentGroupId");
            if (value instanceof String assessmentGroupIdStr) {
                UUID assessmentGroupId = UUID.fromString(assessmentGroupIdStr);
                AssessmentGroup assessmentGroup = assessmentGroupService.getEntity(assessmentGroupId)
                        .orElseThrow(() -> new EntityNotFoundException("AssessmentGroup not found"));
                existing.setAssessmentGroup(assessmentGroup);
            }
        }

        if (updates.containsKey("startingDay")) {
            Object value = updates.get("startingDay");
            if (value instanceof DayOfWeek day) {
                existing.setStartingDay(day);
            } else if (value instanceof String str) {
                existing.setStartingDay(DayOfWeek.valueOf(str));
            }
        }

        if (updates.containsKey("endingDay")) {
            Object value = updates.get("endingDay");
            if (value instanceof DayOfWeek day) {
                existing.setEndingDay(day);
            } else if (value instanceof String str) {
                existing.setEndingDay(DayOfWeek.valueOf(str));
            }
        }

        return mapper.fromStudyModeSession(repository.save(existing));
    }

    public Boolean delete(UUID id) {
        StudyModeSession entity = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("StudyModeSession not found"));
        repository.delete(entity);
        return true;
    }

    public Optional<StudyModeSession> getEntity(UUID id) {
        return repository.findById(id);
    }
}
