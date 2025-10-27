package rw.ac.ilpd.hostelservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.ilpd.hostelservice.mapper.HostelReservationRoomPaymentMapper;
import rw.ac.ilpd.hostelservice.model.nosql.document.HostelReservationRoomPayment;
import rw.ac.ilpd.hostelservice.model.nosql.embedding.PaymentDocument;
import rw.ac.ilpd.hostelservice.repository.nosql.HostelReservationRoomPaymentRepository;
import rw.ac.ilpd.sharedlibrary.dto.hostel.HostelReservationRoomPaymentRequest;
import rw.ac.ilpd.sharedlibrary.dto.hostel.HostelReservationRoomPaymentResponse;
import rw.ac.ilpd.sharedlibrary.dto.payment.PaymentDocumentRequest;
import rw.ac.ilpd.sharedlibrary.enums.PaymentStatus;

import java.util.*;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class HostelReservationRoomPaymentService {

    private final HostelReservationRoomPaymentRepository paymentRepository;
    private final HostelReservationRoomPaymentMapper paymentMapper;

    public HostelReservationRoomPaymentResponse createPayment(HostelReservationRoomPaymentRequest request) {
        log.info("Creating new payment with amount: {}", request.getAmount());

        HostelReservationRoomPayment payment = paymentMapper.toEntity(request);

        // Set payment document parent reference
        if (payment.getPaymentDocuments() != null) {
            payment.getPaymentDocuments().forEach(doc ->
                    doc.setReservationRoomPaymentId(payment.getId()));
        }

        HostelReservationRoomPayment savedPayment = paymentRepository.save(payment);
        log.info("Payment created successfully with ID: {}", savedPayment.getId());

        return paymentMapper.toResponse(savedPayment);
    }

    @Transactional(readOnly = true)
    public List<HostelReservationRoomPaymentResponse> getAllPayments() {
        log.info("Retrieving all payments");
        List<HostelReservationRoomPayment> payments = paymentRepository.findAll();
        return payments.stream()
                .map(paymentMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<HostelReservationRoomPaymentResponse> getPaymentById(String id) {
        log.info("Retrieving payment by ID: {}", id);
        return paymentRepository.findById(id)
                .map(paymentMapper::toResponse);
    }

    public HostelReservationRoomPaymentResponse updatePayment(String id, HostelReservationRoomPaymentRequest request) {
        log.info("Updating payment with ID: {}", id);

        HostelReservationRoomPayment existingPayment = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with ID: " + id));

        paymentMapper.updateEntityFromRequest(request, existingPayment);

        // Update payment documents parent reference
        if (existingPayment.getPaymentDocuments() != null) {
            existingPayment.getPaymentDocuments().forEach(doc ->
                    doc.setReservationRoomPaymentId(id));
        }

        HostelReservationRoomPayment updatedPayment = paymentRepository.save(existingPayment);
        log.info("Payment updated successfully with ID: {}", id);

        return paymentMapper.toResponse(updatedPayment);
    }

    public void deletePayment(String id) {
        log.info("Deleting payment with ID: {}", id);
        if (!paymentRepository.existsById(id)) {
            throw new EntityNotFoundException("Payment not found with ID: " + id);
        }
        paymentRepository.deleteById(id);
        log.info("Payment deleted successfully with ID: {}", id);
    }

    @Transactional(readOnly = true)
    public List<HostelReservationRoomPaymentResponse> getPaymentsByStatus(PaymentStatus status) {
        log.info("Retrieving payments by status: {}", status);
        List<HostelReservationRoomPayment> payments = paymentRepository.findByPaymentStatus(status);
        return payments.stream()
                .map(paymentMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<HostelReservationRoomPaymentResponse> getPaymentByTransactionNumber(String transactionNumber) {
        log.info("Retrieving payment by transaction number: {}", transactionNumber);
        return paymentRepository.findByTransactionNumber(transactionNumber)
                .map(paymentMapper::toResponse);
    }

    public HostelReservationRoomPaymentResponse updatePaymentStatus(String id, PaymentStatus newStatus) {
        log.info("Updating payment status for ID: {} to {}", id, newStatus);

        HostelReservationRoomPayment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with ID: " + id));

        payment.setPaymentStatus(newStatus);
        HostelReservationRoomPayment updatedPayment = paymentRepository.save(payment);

        log.info("Payment status updated successfully for ID: {}", id);
        return paymentMapper.toResponse(updatedPayment);
    }

    // Payment Document Operations
    public HostelReservationRoomPaymentResponse addPaymentDocument(String paymentId, PaymentDocumentRequest documentRequest) {
        log.info("Adding payment document to payment ID: {}", paymentId);

        HostelReservationRoomPayment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with ID: " + paymentId));

        PaymentDocument document = paymentMapper.toPaymentDocumentEntity(documentRequest);
        document.setReservationRoomPaymentId(paymentId);

        if (payment.getPaymentDocuments() == null) {
            payment.setPaymentDocuments(new ArrayList<>());
        }
        payment.getPaymentDocuments().add(document);

        HostelReservationRoomPayment updatedPayment = paymentRepository.save(payment);
        log.info("Payment document added successfully to payment ID: {}", paymentId);

        return paymentMapper.toResponse(updatedPayment);
    }

    public HostelReservationRoomPaymentResponse removePaymentDocument(String paymentId, String documentId) {
        log.info("Removing payment document {} from payment ID: {}", documentId, paymentId);

        HostelReservationRoomPayment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with ID: " + paymentId));

        if (payment.getPaymentDocuments() != null) {
            payment.getPaymentDocuments().removeIf(doc -> doc.getId().equals(documentId));
            HostelReservationRoomPayment updatedPayment = paymentRepository.save(payment);
            log.info("Payment document removed successfully from payment ID: {}", paymentId);
            return paymentMapper.toResponse(updatedPayment);
        }

        throw new EntityNotFoundException("Payment document not found with ID: " + documentId);
    }
}
