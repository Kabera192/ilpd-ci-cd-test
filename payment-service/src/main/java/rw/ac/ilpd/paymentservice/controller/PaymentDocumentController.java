package rw.ac.ilpd.paymentservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.paymentservice.service.PaymentDocumentService;
import rw.ac.ilpd.sharedlibrary.dto.payment.PaymentDocumentRequest;
import rw.ac.ilpd.sharedlibrary.dto.payment.PaymentDocumentResponse;

@RestController
@RequestMapping("/payment-documents")
@RequiredArgsConstructor
public class PaymentDocumentController {
    private final PaymentDocumentService  paymentDocumentService;
    @PostMapping()
    public ResponseEntity<PaymentDocumentResponse> createPaymentDocument(@RequestBody PaymentDocumentRequest paymentDocument) {
        return  paymentDocumentService.createPaymentDocument(paymentDocument);
    }
    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentDocumentResponse> getPaymentDocument(@PathVariable String paymentId) {
        return paymentDocumentService.getPaymentDocument(paymentId);
    }

}
