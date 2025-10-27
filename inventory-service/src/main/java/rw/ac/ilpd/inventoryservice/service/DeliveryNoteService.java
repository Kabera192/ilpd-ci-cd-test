package rw.ac.ilpd.inventoryservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rw.ac.ilpd.inventoryservice.mapper.DeliveryNoteMapper;
import rw.ac.ilpd.inventoryservice.model.sql.DeliveryNote;
import rw.ac.ilpd.inventoryservice.repository.sql.DeliveryNoteRepository;
import rw.ac.ilpd.sharedlibrary.dto.inventory.DeliveryNoteRequest;
import rw.ac.ilpd.sharedlibrary.dto.inventory.DeliveryNoteResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryNoteService {

    private final DeliveryNoteRepository deliveryNoteRepository;
    private final DeliveryNoteMapper deliveryNoteMapper;

    public PagedResponse<DeliveryNoteResponse> getAll(
            int page, int size, String sortBy, String order) {
        Pageable pageable;
        if (order.equals("desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        }

        Page<DeliveryNote> deliveryNotePage = deliveryNoteRepository.findAll(pageable);

        return new PagedResponse<>(
                deliveryNotePage.getContent().stream()
                        .map(deliveryNoteMapper::fromDeliveryNote)
                        .toList(),
                deliveryNotePage.getNumber(),
                deliveryNotePage.getSize(),
                deliveryNotePage.getTotalElements(),
                deliveryNotePage.getTotalPages(),
                deliveryNotePage.isLast()
        );
    }

    public DeliveryNoteResponse get(UUID id) {
        return deliveryNoteMapper.fromDeliveryNote(
                getEntity(id).orElseThrow(() ->
                        new EntityNotFoundException("Delivery note not found")));
    }

    public DeliveryNoteResponse create(DeliveryNoteRequest request) {
        // TODO check if document exists
        // TODO integrate these document dto stuffs
        // TODO check supplier_id
        DeliveryNote deliveryNote = deliveryNoteMapper.toDeliveryNote(request);
        return deliveryNoteMapper.fromDeliveryNote(
                deliveryNoteRepository.save(deliveryNote));
    }

    public DeliveryNoteResponse edit(UUID id, DeliveryNoteRequest request) {
        DeliveryNote existing = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("Delivery note not found"));

        // TODO check if document exists
        // TODO integrate these document dto stuffs
        // TODO check supplier_id

        existing.setSupplierId(request.getSupplierId());
        existing.setDocumentId(request.getDocumentId());

        return deliveryNoteMapper.fromDeliveryNote(
                deliveryNoteRepository.save(existing));
    }

    public DeliveryNoteResponse patch(UUID id, Map<String, Object> updates) {
        DeliveryNote existing = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("Delivery note not found"));

        // Patch supplierId
        if (updates.containsKey("supplierId")) {
            // TODO check supplier_id
            Object value = updates.get("supplierId");
            if (value instanceof String supplierId && !supplierId.isBlank()) {
                existing.setSupplierId(supplierId);
            }
        }

        // Patch documentId
        if (updates.containsKey("documentId")) {
            // TODO check if document exists
            // TODO integrate these document dto stuffs
            Object value = updates.get("documentId");
            if (value instanceof String documentId) {
                existing.setDocumentId(documentId);
            }
        }

        // createdAt should NOT be patched (auto-generated)

        return deliveryNoteMapper.fromDeliveryNote(
                deliveryNoteRepository.save(existing));
    }

    public Boolean delete(UUID id) {
        DeliveryNote deliveryNote = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("Delivery note not found"));
        deliveryNoteRepository.delete(deliveryNote);
        return true;
    }

    public Optional<DeliveryNote> getEntity(UUID id) {
        return deliveryNoteRepository.findById(id);
    }
}