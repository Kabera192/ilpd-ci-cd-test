package rw.ac.ilpd.inventoryservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.inventoryservice.exception.EntityAlreadyExists;
import rw.ac.ilpd.inventoryservice.mapper.UserItemRequisitionModificationMapper;
import rw.ac.ilpd.inventoryservice.model.sql.UserItemRequisition;
import rw.ac.ilpd.inventoryservice.model.sql.UserItemRequisitionModification;
import rw.ac.ilpd.inventoryservice.repository.sql.UserItemRequisitionModificationRepository;
import rw.ac.ilpd.inventoryservice.repository.sql.UserItemRequisitionRepository;
import rw.ac.ilpd.sharedlibrary.dto.inventory.UserItemRequisitionModificationRequest;
import rw.ac.ilpd.sharedlibrary.dto.inventory.UserItemRequisitionModificationResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserItemRequisitionModificationService {

    private final UserItemRequisitionModificationRepository modificationRepository;
    private final UserItemRequisitionRepository userItemRequisitionRepository;
    private final UserItemRequisitionModificationMapper modificationMapper;

    public PagedResponse<UserItemRequisitionModificationResponse> getAll(
            int page, int size, String sortBy, String order) {
        Pageable pageable;
        if (order.equals("desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        }

        Page<UserItemRequisitionModification> modificationPage =
                modificationRepository.findAllByDeleteStatus(false, pageable);

        return new PagedResponse<>(
                modificationPage.getContent().stream()
                        .map(modificationMapper::fromUserItemRequisitionModification)
                        .toList(),
                modificationPage.getNumber(),
                modificationPage.getSize(),
                modificationPage.getTotalElements(),
                modificationPage.getTotalPages(),
                modificationPage.isLast()
        );
    }

    public UserItemRequisitionModificationResponse get(UUID id) {
        return modificationMapper.fromUserItemRequisitionModification(
                getEntity(id).orElseThrow(() ->
                        new EntityNotFoundException("Modification record not found")));
    }

    public UserItemRequisitionModificationResponse create(
            UserItemRequisitionModificationRequest request) {
        UserItemRequisition userItemRequisition = userItemRequisitionRepository
                .findById(UUID.fromString(request.getUserItemRequisitionId()))
                .orElseThrow(() -> new EntityNotFoundException(
                        "User item requisition not found"));

        UserItemRequisitionModification modification =
                modificationMapper.toUserItemRequisitionModification(
                        request, userItemRequisition);

        return modificationMapper.fromUserItemRequisitionModification(
                modificationRepository.save(modification));
    }

    public UserItemRequisitionModificationResponse edit(
            UUID id, UserItemRequisitionModificationRequest request) {
        UserItemRequisitionModification existing = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Modification record not found"));

        UserItemRequisition userItemRequisition = userItemRequisitionRepository
                .findById(UUID.fromString(request.getUserItemRequisitionId()))
                .orElseThrow(() -> new EntityNotFoundException(
                        "User item requisition not found"));

        existing.setUserItemRequisition(userItemRequisition);
        existing.setUpdatedBy(UUID.fromString(request.getUpdatedBy()));
        existing.setUpdatedQuantity(request.getUpdatedQuantity());

        return modificationMapper.fromUserItemRequisitionModification(
                modificationRepository.save(existing));
    }

    public UserItemRequisitionModificationResponse patch(
            UUID id, Map<String, Object> updates) {
        UserItemRequisitionModification existing = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Modification record not found"));

        // Patch userItemRequisition
        if (updates.containsKey("userItemRequisitionId")) {
            Object value = updates.get("userItemRequisitionId");
            if (value instanceof String str) {
                UserItemRequisition userItemRequisition = userItemRequisitionRepository
                        .findById(UUID.fromString(str))
                        .orElseThrow(() -> new EntityNotFoundException(
                                "User item requisition not found"));
                existing.setUserItemRequisition(userItemRequisition);
            }
        }

        // Patch updatedBy
        if (updates.containsKey("updatedBy")) {
            Object value = updates.get("updatedBy");
            if (value instanceof String str) {
                try {
                    existing.setUpdatedBy(UUID.fromString(str));
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Invalid UUID format for updatedBy");
                }
            }
        }

        // Patch updatedQuantity
        if (updates.containsKey("updatedQuantity")) {
            Object value = updates.get("updatedQuantity");
            if (value instanceof Integer quantity && quantity > 0) {
                existing.setUpdatedQuantity(quantity);
            }
        }

        // Patch deleteStatus
        if (updates.containsKey("deleteStatus")) {
            Object value = updates.get("deleteStatus");
            if (value instanceof Boolean status) {
                existing.setDeleteStatus(status);
            }
        }

        // createdAt should NOT be patched (auto-generated)

        return modificationMapper.fromUserItemRequisitionModification(
                modificationRepository.save(existing));
    }

    public Boolean delete(UUID id) {
        UserItemRequisitionModification modification = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Modification record not found"));
        modification.setDeleteStatus(true);
        modificationRepository.save(modification);
        return true;
    }

    public Optional<UserItemRequisitionModification> getEntity(UUID id) {
        return modificationRepository.findByIdAndDeleteStatus(id, false);
    }

    public Optional<UserItemRequisitionModification> findByIdAndIsDeleted(UUID id, boolean isDeleted) {
        return modificationRepository.findByIdAndDeleteStatus(id, isDeleted);
    }
}