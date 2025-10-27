package rw.ac.ilpd.reportingservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.ilpd.reportingservice.exception.EvaluationFormTypeDuplicateException;
import rw.ac.ilpd.reportingservice.exception.EvaluationFormTypeNotFoundException;
import rw.ac.ilpd.reportingservice.mapper.EvaluationFormTypeMapper;
import rw.ac.ilpd.reportingservice.model.nosql.document.EvaluationFormType;
import rw.ac.ilpd.reportingservice.repository.nosql.EvaluationFormTypeRepository;
import rw.ac.ilpd.reportingservice.service.EvaluationFormTypeService;
import rw.ac.ilpd.sharedlibrary.dto.evaluation.EvaluationFormTypeRequest;
import rw.ac.ilpd.sharedlibrary.dto.evaluation.EvaluationFormTypeResponse;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EvaluationFormTypeServiceImpl implements EvaluationFormTypeService {

    private final EvaluationFormTypeRepository repository;
    private final EvaluationFormTypeMapper mapper;

    @Override
    public EvaluationFormTypeResponse create(EvaluationFormTypeRequest request) {
        log.info("Creating new EvaluationFormType with name: {}", request.getName());

        // Check if name already exists
        if (repository.existsByName(request.getName().trim())) {
            throw new EvaluationFormTypeDuplicateException(request.getName());
        }

        EvaluationFormType entity = mapper.toEntity(request);
        entity.setName(request.getName().trim());
        entity.setCreatedBy(getCurrentUser());
        entity.setUpdatedBy(getCurrentUser());

        EvaluationFormType saved = repository.save(entity);
        log.info("Successfully created EvaluationFormType with id: {}", saved.getId());

        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public EvaluationFormTypeResponse getById(String id) {
        log.info("Fetching EvaluationFormType with id: {}", id);

        EvaluationFormType entity = repository.findById(id)
                .orElseThrow(() -> new EvaluationFormTypeNotFoundException(id));

        return mapper.toResponse(entity);
    }

    @Override
    public EvaluationFormTypeResponse update(String id, EvaluationFormTypeRequest request) {
        log.info("Updating EvaluationFormType with id: {}", id);

        EvaluationFormType existing = repository.findById(id)
                .orElseThrow(() -> new EvaluationFormTypeNotFoundException(id));

        // Check if name already exists (excluding current entity)
        if (repository.existsByNameAndIdNot(request.getName().trim(), id)) {
            throw new EvaluationFormTypeDuplicateException(request.getName());
        }

        mapper.updateEntityFromRequest(request, existing);
        existing.setName(request.getName().trim());
        existing.setUpdatedBy(getCurrentUser());

        EvaluationFormType updated = repository.save(existing);
        log.info("Successfully updated EvaluationFormType with id: {}", id);

        return mapper.toResponse(updated);
    }

    @Override
    public void delete(String id) {
        log.info("Hard deleting EvaluationFormType with id: {}", id);

        if (!repository.existsById(id)) {
            throw new EvaluationFormTypeNotFoundException(id);
        }

        repository.deleteById(id);
        log.info("Successfully deleted EvaluationFormType with id: {}", id);
    }

    @Override
    public void softDelete(String id) {
        log.info("Soft deleting EvaluationFormType with id: {}", id);

        EvaluationFormType entity = repository.findById(id)
                .orElseThrow(() -> new EvaluationFormTypeNotFoundException(id));

        entity.setDeleted(false);
        entity.setUpdatedBy(getCurrentUser());
        repository.save(entity);

        log.info("Successfully soft deleted EvaluationFormType with id: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EvaluationFormTypeResponse> getAll(Pageable pageable) {
        log.info("Fetching all EvaluationFormTypes with pagination");

        Page<EvaluationFormType> entities = repository.findAll(pageable);
        return entities.map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EvaluationFormTypeResponse> getAllActive(Pageable pageable) {
        log.info("Fetching all active EvaluationFormTypes with pagination");

        Page<EvaluationFormType> entities = repository.findByIsDeletedFalseOrderByNameAsc(pageable);
        return entities.map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EvaluationFormTypeResponse> searchByName(String name, Pageable pageable) {
        log.info("Searching EvaluationFormTypes by name: {}", name);

        Page<EvaluationFormType> entities;
        if (name.trim().isEmpty()) {
            entities = repository.findByIsDeletedFalseOrderByNameAsc(pageable);
        }else{
                entities = repository.findByNameContainingIgnoreCaseAndIsDeletedFalse(name, pageable);
    }
        return entities.map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EvaluationFormTypeResponse> getAllActiveList() {
        log.info("Fetching all active EvaluationFormTypes as list");

        List<EvaluationFormType> entities = repository.findByIsDeletedFalseOrderByNameAsc();
        return mapper.toResponseList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(String id) {
        return repository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public long countActive() {
        return repository.countByIsDeletedFalse();
    }

    private String getCurrentUser() {
        // TODO: Implement security context to get current user
        // For now, returning a placeholder
        return "system";
    }


}
