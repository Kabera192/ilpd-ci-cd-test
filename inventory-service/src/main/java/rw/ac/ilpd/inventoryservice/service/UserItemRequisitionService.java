package rw.ac.ilpd.inventoryservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.inventoryservice.exception.EntityAlreadyExists;
import rw.ac.ilpd.inventoryservice.mapper.UserItemRequisitionMapper;
import rw.ac.ilpd.inventoryservice.model.sql.Item;
import rw.ac.ilpd.inventoryservice.model.sql.UserItemRequisition;
import rw.ac.ilpd.inventoryservice.repository.sql.UserItemRequisitionRepository;
import rw.ac.ilpd.sharedlibrary.dto.inventory.UserItemRequisitionRequest;
import rw.ac.ilpd.sharedlibrary.dto.inventory.UserItemRequisitionResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserItemRequisitionService {
    private final UserItemRequisitionRepository userItemRequisitionRepository;
    private final UserItemRequisitionMapper userItemRequisitionMapper;
    private final ItemService itemService;

    public PagedResponse<UserItemRequisitionResponse> getAll(int page, int size, String sortBy, String order) {
        Pageable pageable;
        if (order.equals("desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        }

        Page<UserItemRequisition> requisitionPage = userItemRequisitionRepository.findAllByDeleteStatus(false, pageable);
        return new PagedResponse<>(
                requisitionPage.getContent().stream().map(userItemRequisitionMapper::fromUserItemRequisition).toList(),
                requisitionPage.getNumber(),
                requisitionPage.getSize(),
                requisitionPage.getTotalElements(),
                requisitionPage.getTotalPages(),
                requisitionPage.isLast()
        );
    }

    public UserItemRequisitionResponse get(UUID id) {
        return userItemRequisitionMapper.fromUserItemRequisition(
                getEntity(id).orElseThrow(() -> new EntityNotFoundException("User item requisition not found"))
        );
    }

    public UserItemRequisitionResponse create(UserItemRequisitionRequest request) {
        // Fetch the Item entity
        Item item = itemService.getEntity(UUID.fromString(request.getItemId()))
                .orElseThrow(() -> new EntityNotFoundException("Item not found"));

        // check if there is enough
        if(request.getProposedQuantity() > item.getRemainingQuantity())
            throw new IllegalArgumentException("Not enough items!");

        // Check if a similar requisition already exists
        if (userItemRequisitionRepository.existsByUserIdAndRequestIdAndItemIdAndDeleteStatus(
                UUID.fromString(request.getUserId()),
                request.getRequestId(),
                item,
                false
        )) {
            throw new EntityAlreadyExists("User item requisition already exists");
        }

        UserItemRequisition requisition = userItemRequisitionMapper.toUserItemRequisition(request, item);
        return userItemRequisitionMapper.fromUserItemRequisition(userItemRequisitionRepository.save(requisition));
    }

    public UserItemRequisitionResponse edit(UUID id, UserItemRequisitionRequest request) {
        UserItemRequisition existing = getEntity(id).orElseThrow(() -> new EntityNotFoundException("User item requisition not found"));
        Item item = itemService.getEntity(UUID.fromString(request.getItemId()))
                .orElseThrow(() -> new EntityNotFoundException("Item not found"));

        existing.setUserId(UUID.fromString(request.getUserId()));
        existing.setRequestId(request.getRequestId());
        existing.setItemId(item);
        existing.setProposedQuantity(request.getProposedQuantity());

        return userItemRequisitionMapper.fromUserItemRequisition(userItemRequisitionRepository.save(existing));
    }

    public UserItemRequisitionResponse patch(UUID id, Map<String, Object> updates) {
        UserItemRequisition existing = getEntity(id).orElseThrow(() -> new EntityNotFoundException("User item requisition not found"));

        // Patch userId
        if (updates.containsKey("userId")) {
            Object value = updates.get("userId");
            if (value instanceof String str) {
                try {
                    existing.setUserId(UUID.fromString(str));
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Invalid UUID format for userId");
                }
            }
        }

        // Patch requestId
        if (updates.containsKey("requestId")) {
            Object value = updates.get("requestId");
            if (value instanceof String str && !str.isBlank()) {
                existing.setRequestId(str);
            }
        }

        // Patch itemId
        if (updates.containsKey("itemId")) {
            Object value = updates.get("itemId");
            if (value instanceof String str) {
                try {
                    Item item = itemService.getEntity(UUID.fromString(str))
                            .orElseThrow(() -> new EntityNotFoundException("Item not found"));
                    existing.setItemId(item);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Invalid UUID format for itemId");
                }
            }
        }

        // Patch proposedQuantity
        if (updates.containsKey("proposedQuantity")) {
            Object value = updates.get("proposedQuantity");
            if (value instanceof Integer quantity && quantity > 0) {
                existing.setProposedQuantity(quantity);
            }
        }

        // deleteStatus should NOT be patched through this endpoint

        return userItemRequisitionMapper.fromUserItemRequisition(userItemRequisitionRepository.save(existing));
    }

    public Boolean delete(UUID id) {
        UserItemRequisition requisition = getEntity(id).orElseThrow(() -> new EntityNotFoundException("User item requisition not found"));
        requisition.setDeleteStatus(true);
        userItemRequisitionRepository.save(requisition);
        return true;
    }

    public Optional<UserItemRequisition> getEntity(UUID id) {
        return userItemRequisitionRepository.findByIdAndDeleteStatus(id, false);
    }

    public Optional<UserItemRequisition> findByIdAndIsDeleted(UUID id, boolean isDeleted) {
        return userItemRequisitionRepository.findByIdAndDeleteStatus(id, isDeleted);
    }
}