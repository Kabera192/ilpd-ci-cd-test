package rw.ac.ilpd.inventoryservice.mapper;

import org.springframework.stereotype.Component;
import rw.ac.ilpd.inventoryservice.model.sql.Item;
import rw.ac.ilpd.inventoryservice.model.sql.UserItemRequisition;
import rw.ac.ilpd.sharedlibrary.dto.inventory.UserItemRequisitionRequest;
import rw.ac.ilpd.sharedlibrary.dto.inventory.UserItemRequisitionResponse;

import java.util.UUID;

@Component
public class UserItemRequisitionMapper {
    public UserItemRequisition toUserItemRequisition(UserItemRequisitionRequest request, Item item) {
        return UserItemRequisition.builder()
                .userId(UUID.fromString(request.getUserId()))
                .requestId(request.getRequestId())
                .itemId(item)
                .proposedQuantity(request.getProposedQuantity())
                .deleteStatus(false) // Default to false when creating
                .build();
    }

    public UserItemRequisitionResponse fromUserItemRequisition(UserItemRequisition userItemRequisition) {
        return UserItemRequisitionResponse.builder()
                .id(userItemRequisition.getId() != null ? userItemRequisition.getId().toString() : null)
                .userId(userItemRequisition.getUserId() != null ? userItemRequisition.getUserId().toString() : null)
                .requestId(userItemRequisition.getRequestId())
                .itemId(userItemRequisition.getItemId() != null ? userItemRequisition.getItemId().getId().toString() : null)
                .proposedQuantity(userItemRequisition.getProposedQuantity())
                .deleteStatus(userItemRequisition.isDeleteStatus())
                .build();
    }
}