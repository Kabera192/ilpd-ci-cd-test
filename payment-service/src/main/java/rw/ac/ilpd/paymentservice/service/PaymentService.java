package rw.ac.ilpd.paymentservice.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.paymentservice.repository.sql.PaymentRepository;
import rw.ac.ilpd.sharedlibrary.dto.payment.PaymentDocumentDetailResponse;
import rw.ac.ilpd.sharedlibrary.dto.payment.PaymentRequest;
import rw.ac.ilpd.sharedlibrary.dto.payment.PaymentResponse;
import rw.ac.ilpd.sharedlibrary.dto.user.UserResponse;
import rw.ac.ilpd.sharedlibrary.util.TextValidator;

import java.util.List;

@Service
public class PaymentService {
    private final PaymentRepository  paymentRepository;
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
        
    }

    public ResponseEntity<PaymentResponse> createManualPayment(
            PaymentRequest paymentRequest
    ) {
        UserResponse userResponse=new UserResponse();
        TextValidator validator = new TextValidator();
        validator.uuidValidator(paymentRequest.getTransactionNumber());
                try {
//                    userResponse = userClient.findUserById(paymentRequest.getFeeUserId());
                }catch (Exception e){
                    throw new EntityNotFoundException("User not found");
                }
return null;
    }

    public ResponseEntity<PaymentResponse> createPayment(@Valid PaymentRequest paymentRequest) {
        return null;
    }


    public ResponseEntity<PaymentResponse> createPaymentRefund(String paymentId, PaymentRequest paymentRequest) {
        return null;
    }

    public ResponseEntity<List<PaymentDocumentDetailResponse>> getPaymentDocuments(String id) {
        return  null;
    }
}
