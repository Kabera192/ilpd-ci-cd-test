package rw.ac.ilpd.academicservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.academicservice.mapper.IntakeApplicationRequiredDocNameMapper;
import rw.ac.ilpd.academicservice.model.sql.IntakeApplicationRequiredDocName;
import rw.ac.ilpd.academicservice.repository.sql.IntakeApplicationRequiredDocNameRepository;
import rw.ac.ilpd.sharedlibrary.dto.application.IntakeApplicationRequiredDocNameRequest;
import rw.ac.ilpd.sharedlibrary.dto.application.IntakeApplicationRequiredDocNameResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;

import java.util.*;

@Service
@RequiredArgsConstructor
public class IntakeApplicationRequiredDocNameService {

    private final IntakeApplicationRequiredDocNameRepository repository;
    private final IntakeApplicationRequiredDocNameMapper mapper;

    public PagedResponse<IntakeApplicationRequiredDocNameResponse> getAll(int page, int size, String sortBy, String order) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                order.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending()
        );

        Page<IntakeApplicationRequiredDocName> resultPage = repository.findAll(pageable);

        return new PagedResponse<>(
                resultPage.getContent().stream().map(mapper::fromIntakeApplicationRequiredDocName).toList(),
                resultPage.getNumber(),
                resultPage.getSize(),
                resultPage.getTotalElements(),
                resultPage.getTotalPages(),
                resultPage.isLast()
        );
    }

    public IntakeApplicationRequiredDocNameResponse get(UUID id) {
        IntakeApplicationRequiredDocName entity = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("IntakeApplicationRequiredDocName not found"));
        return mapper.fromIntakeApplicationRequiredDocName(entity);
    }

    public IntakeApplicationRequiredDocNameResponse create(IntakeApplicationRequiredDocNameRequest request) {
        IntakeApplicationRequiredDocName entity = mapper.toIntakeApplicationRequiredDocName(request);
        // TODO check if student.userId exists
        return mapper.fromIntakeApplicationRequiredDocName(repository.save(entity));
    }

    public IntakeApplicationRequiredDocNameResponse edit(UUID id, IntakeApplicationRequiredDocNameRequest request) {
        IntakeApplicationRequiredDocName existing = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("IntakeApplicationRequiredDocName not found"));
        // TODO check if student.userId exists

        existing.setName(request.getName());
        return mapper.fromIntakeApplicationRequiredDocName(repository.save(existing));
    }

    public Boolean delete(UUID id) {
        IntakeApplicationRequiredDocName existing = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("IntakeApplicationRequiredDocName not found"));
        repository.delete(existing);
        return true;
    }

    public Optional<IntakeApplicationRequiredDocName> getEntity(UUID id) {
        return repository.findById(id);
    }

    /*
     * Utility function that returns whether an IntakeApplicationRequiredDocName exists or not
     * */
    public boolean requiredDocNameExists(UUID id)
    {
        return repository.existsById(id);
    }
}
