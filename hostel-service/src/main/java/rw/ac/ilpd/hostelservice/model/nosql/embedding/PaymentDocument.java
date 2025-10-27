package rw.ac.ilpd.hostelservice.model.nosql.embedding;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import rw.ac.ilpd.sharedlibrary.enums.PaymentDocumentStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class PaymentDocument {
    private String id = UUID.randomUUID().toString();
    private String documentId;
    private String reservationRoomPaymentId;
    private UUID uploadedBy;
    private UUID verifiedBy;
    private PaymentDocumentStatus paymentDocumentStatus;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public PaymentDocument(String id, String documentId, String reservationRoomPaymentId, UUID uploadedBy, UUID verifiedBy, PaymentDocumentStatus paymentDocumentStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.documentId = documentId;
        this.reservationRoomPaymentId = reservationRoomPaymentId;
        this.uploadedBy = uploadedBy;
        this.verifiedBy = verifiedBy;
        this.paymentDocumentStatus = paymentDocumentStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public PaymentDocument() {
    }

    public static PaymentDocumentBuilder builder() {
        return new PaymentDocumentBuilder();
    }

    public String getId() {
        return this.id;
    }

    public String getDocumentId() {
        return this.documentId;
    }

    public String getReservationRoomPaymentId() {
        return this.reservationRoomPaymentId;
    }

    public UUID getUploadedBy() {
        return this.uploadedBy;
    }

    public UUID getVerifiedBy() {
        return this.verifiedBy;
    }

    public PaymentDocumentStatus getPaymentDocumentStatus() {
        return this.paymentDocumentStatus;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public void setReservationRoomPaymentId(String reservationRoomPaymentId) {
        this.reservationRoomPaymentId = reservationRoomPaymentId;
    }

    public void setUploadedBy(UUID uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public void setVerifiedBy(UUID verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public void setPaymentDocumentStatus(PaymentDocumentStatus paymentDocumentStatus) {
        this.paymentDocumentStatus = paymentDocumentStatus;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String toString() {
        return "PaymentDocument(id=" + this.getId() + ", documentId=" + this.getDocumentId() + ", reservationRoomPaymentId=" + this.getReservationRoomPaymentId() + ", uploadedBy=" + this.getUploadedBy() + ", verifiedBy=" + this.getVerifiedBy() + ", paymentDocumentStatus=" + this.getPaymentDocumentStatus() + ", createdAt=" + this.getCreatedAt() + ", updatedAt=" + this.getUpdatedAt() + ")";
    }

    public static class PaymentDocumentBuilder {
        private String id;
        private String documentId;
        private String reservationRoomPaymentId;
        private UUID uploadedBy;
        private UUID verifiedBy;
        private PaymentDocumentStatus paymentDocumentStatus;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        PaymentDocumentBuilder() {
        }

        public PaymentDocumentBuilder id(String id) {
            this.id = id;
            return this;
        }

        public PaymentDocumentBuilder documentId(String documentId) {
            this.documentId = documentId;
            return this;
        }

        public PaymentDocumentBuilder reservationRoomPaymentId(String reservationRoomPaymentId) {
            this.reservationRoomPaymentId = reservationRoomPaymentId;
            return this;
        }

        public PaymentDocumentBuilder uploadedBy(UUID uploadedBy) {
            this.uploadedBy = uploadedBy;
            return this;
        }

        public PaymentDocumentBuilder verifiedBy(UUID verifiedBy) {
            this.verifiedBy = verifiedBy;
            return this;
        }

        public PaymentDocumentBuilder paymentDocumentStatus(PaymentDocumentStatus paymentDocumentStatus) {
            this.paymentDocumentStatus = paymentDocumentStatus;
            return this;
        }

        public PaymentDocumentBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public PaymentDocumentBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public PaymentDocument build() {
            return new PaymentDocument(this.id, this.documentId, this.reservationRoomPaymentId, this.uploadedBy, this.verifiedBy, this.paymentDocumentStatus, this.createdAt, this.updatedAt);
        }

        public String toString() {
            return "PaymentDocument.PaymentDocumentBuilder(id=" + this.id + ", documentId=" + this.documentId + ", reservationRoomPaymentId=" + this.reservationRoomPaymentId + ", uploadedBy=" + this.uploadedBy + ", verifiedBy=" + this.verifiedBy + ", paymentDocumentStatus=" + this.paymentDocumentStatus + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + ")";
        }
    }
}
