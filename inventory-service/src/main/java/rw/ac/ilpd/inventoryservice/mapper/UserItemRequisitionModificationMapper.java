package rw.ac.ilpd.inventoryservice.mapper;

import org.springframework.stereotype.Component;
import rw.ac.ilpd.inventoryservice.model.sql.UserItemRequisition;
import rw.ac.ilpd.inventoryservice.model.sql.UserItemRequisitionModification;
import rw.ac.ilpd.sharedlibrary.dto.inventory.UserItemRequisitionModificationRequest;
import rw.ac.ilpd.sharedlibrary.dto.inventory.UserItemRequisitionModificationResponse;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class UserItemRequisitionModificationMapper {

    public UserItemRequisitionModification toUserItemRequisitionModification(
            UserItemRequisitionModificationRequest request,
            UserItemRequisition userItemRequisition) {
        return UserItemRequisitionModification.builder()
                .userItemRequisition(userItemRequisition)
                .updatedBy(UUID.fromString(request.getUpdatedBy()))
                .updatedQuantity(request.getUpdatedQuantity())
                .deleteStatus(false) // default to false when creating
                .build();
    }

    public UserItemRequisitionModificationResponse fromUserItemRequisitionModification(
            UserItemRequisitionModification modification) {
        return UserItemRequisitionModificationResponse.builder()
                .id(modification.getId() != null ? modification.getId().toString() : null)
                .userItemRequisitionId(modification.getUserItemRequisition() != null ?
                        modification.getUserItemRequisition().getId().toString() : null)
                .updatedBy(modification.getUpdatedBy() != null ?
                        modification.getUpdatedBy().toString() : null)
                .updatedQuantity(modification.getUpdatedQuantity())
                .deleteStatus(modification.isDeleteStatus())
                .createdAt(modification.getCreatedAt() != null ?
                        modification.getCreatedAt().toString() : null)
                .build();
    }
}