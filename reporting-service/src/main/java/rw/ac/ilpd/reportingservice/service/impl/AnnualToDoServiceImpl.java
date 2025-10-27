package rw.ac.ilpd.reportingservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.ilpd.reportingservice.exception.EntityAlreadyExistsException;
import rw.ac.ilpd.reportingservice.exception.EntityNotFoundException;
import rw.ac.ilpd.reportingservice.mapper.AnnualToDoMapper;
import rw.ac.ilpd.reportingservice.model.nosql.document.AnnualToDo;
import rw.ac.ilpd.reportingservice.repository.nosql.AnnualToDoRepository;
import rw.ac.ilpd.reportingservice.service.AnnualToDoService;
import rw.ac.ilpd.sharedlibrary.dto.planning.AnnualToDoRequest;
import rw.ac.ilpd.sharedlibrary.dto.planning.AnnualToDoResponse;
import rw.ac.ilpd.sharedlibrary.dto.planning.AnnualToDoStatusSummary;
import rw.ac.ilpd.sharedlibrary.enums.ToDoStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Author: Michel Igiraneza
 * Created: 2025-08-14
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AnnualToDoServiceImpl implements AnnualToDoService {

    private final AnnualToDoRepository repository;
    private final AnnualToDoMapper mapper;

    private static final String ACTIVE_STATUS = "ACTIVE";
    private static final String DELETED_STATUS = "DELETED";

    @Override
    public AnnualToDoResponse create(AnnualToDoRequest request) {
        log.info("Creating new AnnualToDo with requestId: {}", request.getRequestId());

        // Check if requestId already exists
        if (repository.existsByRequestId(request.getRequestId().trim())) {
            throw new EntityAlreadyExistsException("Annual to do is already exists");
        }

        // Validate date range
        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new IllegalArgumentException("End date must be after or equal to start date");
        }

        AnnualToDo entity = mapper.toEntity(request);
        entity.setRequestId(request.getRequestId().trim());
        entity.setDescription(request.getDescription().trim());
        entity.setCreatedBy(getCurrentUser());
        entity.setUpdatedBy(getCurrentUser());

        AnnualToDo saved = repository.save(entity);
        log.info("Successfully created AnnualToDo with id: {}", saved.getId());

        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public AnnualToDoResponse getById(String id) {
        log.info("Fetching AnnualToDo with id: {}", id);

        AnnualToDo entity = repository.findByIdAndIsDeleted(id, false)
                .orElseThrow(() -> new EntityNotFoundException(id));

        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public AnnualToDoResponse getByRequestId(String requestId) {
        log.info("Fetching AnnualToDo with requestId: {}", requestId);

        AnnualToDo entity = repository.findByRequestId(requestId.trim())
                .filter(AnnualToDo::isDeleted)
                .orElseThrow(() -> new EntityNotFoundException("Annual to do not found"));

        return mapper.toResponse(entity);
    }

    @Override
    public AnnualToDoResponse update(String id, AnnualToDoRequest request) {
        log.info("Updating AnnualToDo with id: {}", id);

        AnnualToDo existing = repository.findByIdAndIsDeleted(id, false)
                .orElseThrow(() -> new EntityNotFoundException("Updated annual to do not found"));

        // Validate date range
        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new IllegalArgumentException("End date must be after or equal to start date");
        }

        mapper.updateEntityFromRequest(request, existing);
        existing.setDescription(request.getDescription().trim());
        existing.setUpdatedBy(getCurrentUser());

        AnnualToDo updated = repository.save(existing);
        log.info("Successfully updated AnnualToDo with id: {}", id);

        return mapper.toResponse(updated);
    }

    @Override
    public void delete(String id) {
        log.info("Hard deleting AnnualToDo with id: {}", id);

        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(id);
        }

        repository.deleteById(id);
        log.info("Successfully deleted AnnualToDo with id: {}", id);
    }

    @Override
    public void softDelete(String id) {
        log.info("Soft deleting AnnualToDo with id: {}", id);

        AnnualToDo entity = repository.findByIdAndIsDeleted(id, false)
                .orElseThrow(() -> new EntityNotFoundException("Annual to do not found"));

        entity.setDeleted(true);
        entity.setUpdatedBy(getCurrentUser());
        repository.save(entity);

        log.info("Successfully soft deleted AnnualToDo with id: {}", id);
    }

    @Override
    public void restore(String id) {
        log.info("Restoring AnnualToDo with id: {}", id);

        AnnualToDo entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Annual  to do not found"));

        if (!entity.isDeleted()) {
            throw new IllegalArgumentException("AnnualToDo is already active");
        }

        // Check if requestId conflicts with existing active todo
        if (repository.existsByRequestId(entity.getRequestId())) {
            throw new EntityAlreadyExistsException("Annual to do is already active");
        }

        entity.setDeleted(true);
        entity.setUpdatedBy(getCurrentUser());
        repository.save(entity);

        log.info("Successfully restored AnnualToDo with id: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnnualToDoResponse> getAll(Pageable pageable) {
        log.info("Fetching all AnnualToDos with pagination");

        Page<AnnualToDo> entities = repository.findAll(pageable);
        return entities.map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnnualToDoResponse> getAllActive(Pageable pageable) {
        log.info("Fetching all active AnnualToDos with pagination");

        Page<AnnualToDo> entities = repository.findByIsDeleted(false, pageable);
        return entities.map(mapper::toResponse);
    }

    @Override
    public Page<AnnualToDoResponse> getByAssignedTo(String assignedTo, Pageable pageable) {
        log.info("Fetching all assigned AnnualToDos with pagination");
        Page<AnnualToDo> entities = repository.findByAssignedTo(assignedTo, pageable);
        return entities.map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnnualToDoResponse> searchByDescription(String description, Pageable pageable) {
        log.info("Searching AnnualToDos by description: {}", description);

        Page<AnnualToDo> entities = repository.findByDescriptionContainingIgnoreCaseAndIsDeleted(description, false, pageable);
        return entities.map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnnualToDoResponse> searchByRequestId(String requestId, Pageable pageable) {
        log.info("Searching AnnualToDos by requestId: {}", requestId);

        Page<AnnualToDo> entities = repository.findByRequestIdContainingIgnoreCaseAndIsDeleted(requestId, false, pageable);
        return entities.map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnnualToDoResponse> getByStatus(String status, Pageable pageable) {
        log.info("Fetching AnnualToDos by status: {}", status);

        ToDoStatus toDoStatus = ToDoStatus.fromString(status);
        Page<AnnualToDo> entities = repository.findByStatusAndIsDeleted(toDoStatus, false, pageable);
        return entities.map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnnualToDoResponse> getByUnitId(String unitId, Pageable pageable) {
        log.info("Fetching AnnualToDos by unitId: {}", unitId);

        UUID uuid = UUID.fromString(unitId);
        Page<AnnualToDo> entities = repository.findByUnitIdAndIsDeleted(uuid, false, pageable);
        return entities.map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnnualToDoResponse> getByCostRange(BigDecimal minCost, BigDecimal maxCost, Pageable pageable) {
        log.info("Fetching AnnualToDos by cost range: {} - {}", minCost, maxCost);

        Page<AnnualToDo> entities = repository.findByCostRangeAndIsDeleted(minCost, maxCost, false, pageable);
        return entities.map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnnualToDoResponse> getAllActiveList() {
        log.info("Fetching all active AnnualToDos as list");

        List<AnnualToDo> entities = repository.findByIsDeletedOrderByCreatedAtDesc(false);
        return mapper.toResponseList(entities);
    }

    @Override
    public List<AnnualToDoStatusSummary> getStatusSummaryByUnitId(UUID unitId) {
        return List.of();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnnualToDoResponse> getByDateRange(LocalDate startDate, LocalDate endDate) {
        log.info("Fetching AnnualToDos by date range: {} to {}", startDate, endDate);

        List<AnnualToDo> entities = repository.findByStartDateBetweenAndIsDeleted(startDate, endDate, false);
        return mapper.toResponseList(entities);
    }

    @Override
    public Page<AnnualToDoResponse> getByPriority(Integer priority, Pageable pageable) {
        Page<AnnualToDo> entities = repository.findByPriority(priority, pageable);

        return entities.map((mapper::toResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnnualToDoResponse> getOverdueTasks() {
        log.info("Fetching overdue AnnualToDos");

        List<AnnualToDo> entities = repository.findByEndDateBeforeAndIsDeleted(LocalDate.now(), false);
        // Filter out completed and cancelled tasks
        List<AnnualToDo> overdueTasks = entities.stream()
                .filter(todo -> !todo.getStatus().equals(ToDoStatus.COMPLETED) && !todo.getStatus().equals(ToDoStatus.CANCELLED))
                .collect(Collectors.toList());

        return mapper.toResponseList(overdueTasks);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnnualToDoResponse> getByMultipleStatuses(List<String> statuses) {
        log.info("Fetching AnnualToDos by multiple statuses: {}", statuses);

        List<ToDoStatus> toDoStatuses = statuses.stream()
                .map(ToDoStatus::fromString)
                .collect(Collectors.toList());

        List<AnnualToDo> entities = repository.findByStatusInAndIsDeleted(toDoStatuses, false);
        return mapper.toResponseList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(String id) {
        return repository.findByIdAndIsDeleted(id, false).isPresent();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByRequestId(String requestId) {
        return repository.existsByRequestId(requestId);
    }

    @Override
    @Transactional(readOnly = true)
    public long countActive() {
        return repository.countByIsDeleted(false);
    }

    @Override
    @Transactional(readOnly = true)
    public long countByStatus(String status) {
        ToDoStatus toDoStatus = ToDoStatus.fromString(status);
        return repository.countByStatusAndIsDeleted(toDoStatus, false);
    }

    @Override
    public AnnualToDoResponse updateStatus(String id, String newStatus) {
        log.info("Updating status for AnnualToDo with id: {} to status: {}", id, newStatus);

        AnnualToDo entity = repository.findByIdAndIsDeleted(id, false)
                .orElseThrow(() -> new EntityNotFoundException("Annual todo not found"));

        ToDoStatus status = ToDoStatus.fromString(newStatus);
        entity.setStatus(status);
        entity.setUpdatedBy(getCurrentUser());

        AnnualToDo updated = repository.save(entity);
        log.info("Successfully updated status for AnnualToDo with id: {}", id);

        return mapper.toResponse(updated);
    }

    private String getCurrentUser() {
        // TODO: Implement security context to get current user
        // For now, returning a placeholder
        return "system";
    }
}