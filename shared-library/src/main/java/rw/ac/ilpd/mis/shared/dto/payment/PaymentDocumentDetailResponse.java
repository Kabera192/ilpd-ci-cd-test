package rw.ac.ilpd.mis.shared.dto.payment;

import rw.ac.ilpd.mis.shared.dto.document.DocumentDetailResponse;
import rw.ac.ilpd.mis.shared.dto.user.UserResponse;

import java.time.LocalDateTime;

public class PaymentDocumentDetailResponse {
    private String id;
    private DocumentDetailResponse document;
    private  String paymentId;
    private UserResponse uploadedBy;
    private UserResponse verifiedBy;
    private  boolean isAccepted;
    private LocalDateTime createdAt;
}
