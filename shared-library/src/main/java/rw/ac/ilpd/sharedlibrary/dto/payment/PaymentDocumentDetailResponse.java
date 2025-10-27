package rw.ac.ilpd.sharedlibrary.dto.payment;

import rw.ac.ilpd.sharedlibrary.dto.document.DocumentDetailResponse;
import rw.ac.ilpd.sharedlibrary.dto.user.UserResponse;

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
