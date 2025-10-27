package rw.ac.ilpd.hostelservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.hostelservice.service.HostelReservationRoomPaymentService;
import rw.ac.ilpd.sharedlibrary.dto.hostel.HostelReservationRoomPaymentRequest;
import rw.ac.ilpd.sharedlibrary.dto.hostel.HostelReservationRoomPaymentResponse;
import rw.ac.ilpd.sharedlibrary.dto.payment.PaymentDocumentRequest;
import rw.ac.ilpd.sharedlibrary.enums.PaymentStatus;

import java.util.List;

@RestController
@RequestMapping("/reservation-payments")
@Validated
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Hostel Payment Management", description = "APIs for managing hostel reservation room payments and associated documents")
public class HostelReservationRoomPaymentController {

    private final HostelReservationRoomPaymentService paymentService;

    // Parent Document CRUD Operations

    @PostMapping
    @Operation(
            summary = "Create a new payment",
            description = "Creates a new hostel reservation room payment with optional payment documents"
    )
    public ResponseEntity<HostelReservationRoomPaymentResponse> createPayment(
            @Valid @RequestBody HostelReservationRoomPaymentRequest request) {
        log.info("Creating new payment");

        HostelReservationRoomPaymentResponse response = paymentService.createPayment(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    @Operation(
            summary = "Get all payments",
            description = "Retrieves a list of all hostel reservation room payments"
    )
    public ResponseEntity<List<HostelReservationRoomPaymentResponse>> getAllPayments() {
        log.info("Retrieving all payments");

        List<HostelReservationRoomPaymentResponse> payments = paymentService.getAllPayments();

        return ResponseEntity.ok(payments);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get payment by ID",
            description = "Retrieves a specific payment by its unique identifier"
    )
    public ResponseEntity<HostelReservationRoomPaymentResponse> getPaymentById(
            @PathVariable String id) {
        log.info("Retrieving payment by ID: {}", id);

        return paymentService.getPaymentById(id)
                .map(payment -> new ResponseEntity<>(payment, HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update payment",
            description = "Updates an existing payment with new information"
    )
    public ResponseEntity<HostelReservationRoomPaymentResponse> updatePayment(
            @PathVariable String id,
            @Valid @RequestBody HostelReservationRoomPaymentRequest request) {
        log.info("Updating payment with ID: {}", id);

        HostelReservationRoomPaymentResponse response = paymentService.updatePayment(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete payment",
            description = "Deletes a payment and all associated documents"
    )
    public ResponseEntity<String> deletePayment(@PathVariable String id) {
        log.info("Deleting payment with ID: {}", id);

        paymentService.deletePayment(id);

        return ResponseEntity.ok("Payment has been deleted");
    }

    // Query Operations

    @GetMapping("/status/{status}")
    @Operation(
            summary = "Get payments by status",
            description = "Retrieves all payments with a specific status"
    )
    public ResponseEntity<List<HostelReservationRoomPaymentResponse>> getPaymentsByStatus(
            @PathVariable PaymentStatus status) {
        log.info("Retrieving payments by status: {}", status);

        List<HostelReservationRoomPaymentResponse> payments = paymentService.getPaymentsByStatus(status);

        return ResponseEntity.ok(payments);
    }

    @GetMapping("/transaction/{transactionNumber}")
    @Operation(
            summary = "Get payment by transaction number",
            description = "Retrieves a payment using its transaction number"
    )
    public ResponseEntity<HostelReservationRoomPaymentResponse> getPaymentByTransactionNumber(
            @PathVariable String transactionNumber) {
        log.info("Retrieving payment by transaction number: {}", transactionNumber);

        return paymentService.getPaymentByTransactionNumber(transactionNumber)
                .map(payment -> ResponseEntity.ok(payment))
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/status")
    @Operation(
            summary = "Update payment status",
            description = "Updates only the status of a specific payment"
    )
    public ResponseEntity<HostelReservationRoomPaymentResponse> updatePaymentStatus(
            @PathVariable String id,
            @RequestParam PaymentStatus status) {
        log.info("Updating payment status for ID: {} to {}", id, status);

        HostelReservationRoomPaymentResponse response = paymentService.updatePaymentStatus(id, status);

        return ResponseEntity.ok(response);
    }

    // Child Document Operations (Payment Documents)

    @PostMapping("/{paymentId}/documents")
    @Operation(
            summary = "Add payment document",
            description = "Adds a new document to an existing payment"
    )
    public ResponseEntity<HostelReservationRoomPaymentResponse> addPaymentDocument(
            @PathVariable String paymentId,
            @Valid @RequestBody PaymentDocumentRequest documentRequest) {
        log.info("Adding payment document to payment ID: {}", paymentId);

        HostelReservationRoomPaymentResponse response = paymentService.addPaymentDocument(paymentId, documentRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @DeleteMapping("/{paymentId}/documents/{documentId}")
    @Operation(
            summary = "Remove payment document",
            description = "Removes a specific document from a payment"
    )
    public ResponseEntity<HostelReservationRoomPaymentResponse> removePaymentDocument(
            @PathVariable String paymentId,
            @PathVariable String documentId) {
        log.info("Removing payment document {} from payment ID: {}", documentId, paymentId);

        HostelReservationRoomPaymentResponse response = paymentService.removePaymentDocument(paymentId, documentId);

        return ResponseEntity.ok(response);
    }
}
