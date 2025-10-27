package rw.ac.ilpd.paymentservice.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.paymentservice.service.PaymentService;
import rw.ac.ilpd.sharedlibrary.dto.payment.PaymentDocumentDetailResponse;
import rw.ac.ilpd.sharedlibrary.dto.payment.PaymentRequest;
import rw.ac.ilpd.sharedlibrary.dto.payment.PaymentResponse;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {
private final PaymentService  paymentService;
public PaymentController(PaymentService paymentService) {
    this.paymentService = paymentService;
}
@PostMapping("/process/online")
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody @Valid PaymentRequest paymentRequest, Principal principal) {
    return paymentService.createPayment(paymentRequest);
}
@PostMapping("/process/manual")
public ResponseEntity<PaymentResponse> createPaymentManual(@RequestBody PaymentRequest paymentRequest) {
    return paymentService.createManualPayment(paymentRequest);
}
@PostMapping("/process/{paymentId}/refund")
public ResponseEntity<PaymentResponse> createPaymentRefund(@PathVariable String paymentId, @RequestBody PaymentRequest paymentRequest) {
        return paymentService.createPaymentRefund(paymentId,paymentRequest);
}
@GetMapping("/{id}/payment-documents")
    public ResponseEntity<List<PaymentDocumentDetailResponse>> getPaymentDocuments(@PathVariable String id) {
    return  paymentService.getPaymentDocuments(id);
}

}
