package rw.ac.ilpd.paymentservice.service;

import jakarta.persistence.EntityExistsException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.paymentservice.integration.client.DocumentClient;
import rw.ac.ilpd.paymentservice.mapper.PaymentDocumentMapper;
import rw.ac.ilpd.paymentservice.model.nosql.PaymentDocument;
import rw.ac.ilpd.paymentservice.repository.nosql.PaymentDocumentRepository;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentRequest;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentResponse;
import rw.ac.ilpd.sharedlibrary.dto.payment.PaymentDocumentRequest;
import rw.ac.ilpd.sharedlibrary.dto.payment.PaymentDocumentResponse;
import rw.ac.ilpd.sharedlibrary.enums.PaymentDocumentStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentDocumentService {
    private final PaymentDocumentRepository paymentDocumentRepository;
    private final PaymentDocumentMapper  paymentDocumentMapper;
//    private  final DocumentClient documentClient;
    public ResponseEntity<PaymentDocumentResponse> createPaymentDocument(@Valid PaymentDocumentRequest request) {
        PaymentDocument pd= paymentDocumentMapper.toPaymentDocument(request);
        List<PaymentDocument>paymentDocuments= paymentDocumentRepository.findByStatusOrderByCreatedAtDesc(PaymentDocumentStatus.PENDING);
        paymentDocuments.stream().findFirst().ifPresent(pd1->{throw new EntityExistsException("Please first solve the added pending document before adding a new one");});
//TODO find attachment uploaded user
//       DocumentResponse documentResponse= documentClient.createPaymentDocument(DocumentRequest.builder()
//                        .typeId(request.getDocumentTypeId())
//                .file(request.getFile())
//                .build());
       pd.setStatus(PaymentDocumentStatus.PENDING);
       return null;
    }
    public ResponseEntity<PaymentDocumentResponse> getPaymentDocument(String paymentId) {
       Optional<PaymentDocument>paymentDocument= paymentDocumentRepository.findByPaymentId(UUID.fromString(paymentId));
       return null;
    }
    public Optional<PaymentDocument>getEntity(String id){
        return paymentDocumentRepository.findById(id);
    }


}
