package rw.ac.ilpd.academicservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.academicservice.exception.EntityAlreadyExists;
import rw.ac.ilpd.academicservice.mapper.StudyModeMapper;
import rw.ac.ilpd.academicservice.model.sql.StudyMode;
import rw.ac.ilpd.academicservice.repository.sql.StudyModeRepository;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.studymode.StudyModeRequest;
import rw.ac.ilpd.sharedlibrary.dto.studymode.StudyModeResponse;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudyModeService {

    private final StudyModeRepository repository;
    private final StudyModeMapper mapper;

    public PagedResponse<StudyModeResponse> getAll(int page, int size, String sortBy, String order) {
        Pageable pageable = order.equals("desc")
                ? PageRequest.of(page, size, Sort.by(sortBy).descending())
                : PageRequest.of(page, size, Sort.by(sortBy).ascending());

        Page<StudyMode> entityPage = repository.findAll(pageable);

        return new PagedResponse<>(
                entityPage.getContent().stream().map(mapper::fromStudyMode).toList(),
                entityPage.getNumber(),
                entityPage.getSize(),
                entityPage.getTotalElements(),
                entityPage.getTotalPages(),
                entityPage.isLast()
        );
    }

    public StudyModeResponse get(UUID id) {
        StudyMode entity = getEntity(id).orElseThrow(() -> new EntityNotFoundException("StudyMode not found"));
        return mapper.fromStudyMode(entity);
    }

    public StudyModeResponse create(StudyModeRequest request) {
        if (repository.existsByNameIgnoreCase(request.getName())) {
            throw new EntityAlreadyExists("StudyMode with name already exists: " + request.getName());
        }
        StudyMode entity = mapper.toStudyMode(request);
        return mapper.fromStudyMode(repository.save(entity));
    }

    public StudyModeResponse edit(UUID id, StudyModeRequest request) {
        StudyMode existing = getEntity(id).orElseThrow(() -> new EntityNotFoundException("StudyMode not found"));
        existing.setName(request.getName());
        existing.setAcronym(request.getAcronym());
        return mapper.fromStudyMode(repository.save(existing));
    }

    public StudyModeResponse patch(UUID id, Map<String, Object> updates) {
        StudyMode existing = getEntity(id).orElseThrow(() -> new EntityNotFoundException("StudyMode not found"));

        if (updates.containsKey("name")) {
            Object value = updates.get("name");
            if (value instanceof String name && !name.isBlank()) {
                if (repository.existsByNameIgnoreCase(name) && !existing.getName().equalsIgnoreCase(name)) {
                    throw new EntityAlreadyExists("StudyMode with name already exists: " + name);
                }
                existing.setName(name);
            }
        }

        if (updates.containsKey("acronym")) {
            Object value = updates.get("acronym");
            if (value instanceof String acronym && !acronym.isBlank()) {
                existing.setAcronym(acronym);
            }
        }

        return mapper.fromStudyMode(repository.save(existing));
    }

    public Boolean delete(UUID id) {
        StudyMode entity = getEntity(id).orElseThrow(() -> new EntityNotFoundException("StudyMode not found"));
        repository.delete(entity);
        return true;
    }

    public Optional<StudyMode> getEntity(UUID id) {
        return repository.findById(id);
    }
}
