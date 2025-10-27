package rw.ac.ilpd.inventoryservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.inventoryservice.exception.EntityAlreadyExists;
import rw.ac.ilpd.inventoryservice.mapper.SourceOfFundMapper;
import rw.ac.ilpd.inventoryservice.model.sql.SourceOfFund;
import rw.ac.ilpd.inventoryservice.repository.sql.SourceOfFundRepository;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.inventory.SourceOfFundRequest;
import rw.ac.ilpd.sharedlibrary.dto.inventory.SourceOfFundResponse;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SourceOfFundService {
    private final SourceOfFundRepository sourceOfFundRepository;
    private final SourceOfFundMapper sourceOfFundMapper;

    public PagedResponse<SourceOfFundResponse> getAll(int page, int size, String sortBy, String order, boolean includeDeleted) {
        Pageable pageable;
        if (order.equals("desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        }

        Page<SourceOfFund> sourceOfFundPage;
        if (includeDeleted) {
            sourceOfFundPage = sourceOfFundRepository.findAll(pageable);
        } else {
            sourceOfFundPage = sourceOfFundRepository.findAllByIsDeletedFalse(pageable);
        }

        return new PagedResponse<>(
                sourceOfFundPage.getContent().stream().map(sourceOfFundMapper::fromSourceOfFund).toList(),
                sourceOfFundPage.getNumber(),
                sourceOfFundPage.getSize(),
                sourceOfFundPage.getTotalElements(),
                sourceOfFundPage.getTotalPages(),
                sourceOfFundPage.isLast()
        );
    }

    public SourceOfFundResponse get(UUID id) {
        return sourceOfFundMapper.fromSourceOfFund(
                getEntity(id, false).orElseThrow(() -> new EntityNotFoundException("Source of fund not found"))
        );
    }

    public SourceOfFundResponse create(SourceOfFundRequest request) {
        // Check if source with same name already exists
        if (sourceOfFundRepository.existsByNameAndIsDeletedFalse(request.getName())) {
            throw new EntityAlreadyExists("Source of fund with name '" + request.getName() + "' already exists");
        }

        SourceOfFund sourceOfFund = sourceOfFundMapper.toSourceOfFund(request);
        return sourceOfFundMapper.fromSourceOfFund(sourceOfFundRepository.save(sourceOfFund));
    }

    public SourceOfFundResponse edit(UUID id, SourceOfFundRequest request) {
        SourceOfFund existing = getEntity(id, false)
                .orElseThrow(() -> new EntityNotFoundException("Source of fund not found"));

        // Check if another source with the same name already exists
        if (!existing.getName().equals(request.getName()) &&
                sourceOfFundRepository.existsByNameAndIsDeletedFalse(request.getName())) {
            throw new EntityAlreadyExists("Source of fund with name '" + request.getName() + "' already exists");
        }

        existing.setName(request.getName());
        existing.setDescription(request.getDescription());
        existing.setPhone(request.getPhone());
        existing.setEmail(request.getEmail());

        return sourceOfFundMapper.fromSourceOfFund(sourceOfFundRepository.save(existing));
    }

    public SourceOfFundResponse patch(UUID id, Map<String, Object> updates) {
        SourceOfFund existing = getEntity(id, false)
                .orElseThrow(() -> new EntityNotFoundException("Source of fund not found"));

        // Patch name
        if (updates.containsKey("name")) {
            Object value = updates.get("name");
            if (value instanceof String name && !name.isBlank()) {
                if (!existing.getName().equals(name) &&
                        sourceOfFundRepository.existsByNameAndIsDeletedFalse(name)) {
                    throw new EntityAlreadyExists("Source of fund with name '" + name + "' already exists");
                }
                existing.setName(name);
            }
        }

        // Patch description
        if (updates.containsKey("description")) {
            Object value = updates.get("description");
            if (value instanceof String description && !description.isBlank()) {
                existing.setDescription(description);
            }
        }

        // Patch phone
        if (updates.containsKey("phone")) {
            Object value = updates.get("phone");
            if (value instanceof String phone && !phone.isBlank()) {
                existing.setPhone(phone);
            }
        }

        // Patch email
        if (updates.containsKey("email")) {
            Object value = updates.get("email");
            if (value instanceof String email && !email.isBlank()) {
                existing.setEmail(email);
            }
        }

        // createdAt should NOT be patched (auto-generated)

        return sourceOfFundMapper.fromSourceOfFund(sourceOfFundRepository.save(existing));
    }

    public Boolean delete(UUID id) {
        SourceOfFund existing = getEntity(id, false)
                .orElseThrow(() -> new EntityNotFoundException("Source of fund not found"));
        existing.setDeleted(true);
        sourceOfFundRepository.save(existing);
        return true;
    }

    public SourceOfFundResponse restore(UUID id) {
        SourceOfFund existing = getEntity(id, true)
                .orElseThrow(() -> new EntityNotFoundException("Source of fund not found"));
        existing.setDeleted(false);
        return sourceOfFundMapper.fromSourceOfFund(sourceOfFundRepository.save(existing));
    }

    public Optional<SourceOfFund> getEntity(UUID id) {
        return sourceOfFundRepository.findById(id);
    }

    public Optional<SourceOfFund> getEntity(UUID id, boolean includeDeleted) {
        if (includeDeleted) {
            return sourceOfFundRepository.findById(id);
        }
        return sourceOfFundRepository.findByIdAndIsDeletedFalse(id);
    }
}