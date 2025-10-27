package rw.ac.ilpd.mis.shared.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rw.ac.ilpd.mis.shared.enums.PaymentDocumentStatus;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PaymentDocumentResponse {
    private String id;
    private  String documentId;
    private  String paymentId;
    private  String uploadedBy;
    private  String verifiedBy;
    private PaymentDocumentStatus status;
    private LocalDateTime createdAt;
}
