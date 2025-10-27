package rw.ac.ilpd.inventoryservice.mapper;

import org.springframework.stereotype.Component;
import rw.ac.ilpd.inventoryservice.model.sql.DeliveryNote;
import rw.ac.ilpd.sharedlibrary.dto.inventory.DeliveryNoteRequest;
import rw.ac.ilpd.sharedlibrary.dto.inventory.DeliveryNoteResponse;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class DeliveryNoteMapper {

    public DeliveryNote toDeliveryNote(DeliveryNoteRequest request) {
        return DeliveryNote.builder()
                .supplierId(request.getSupplierId())
                .documentId(request.getDocumentId())
                .build();
    }

    public DeliveryNoteResponse fromDeliveryNote(DeliveryNote deliveryNote) {
        return DeliveryNoteResponse.builder()
                .id(deliveryNote.getId() != null ? deliveryNote.getId().toString() : null)
                .supplierId(deliveryNote.getSupplierId())
                .documentId(deliveryNote.getDocumentId())
                .createdAt(deliveryNote.getCreatedAt() != null ?
                        deliveryNote.getCreatedAt().toString() : null)
                .build();
    }
}