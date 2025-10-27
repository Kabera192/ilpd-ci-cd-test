package rw.ac.ilpd.paymentservice.model.nosql;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import rw.ac.ilpd.sharedlibrary.enums.PaymentDocumentStatus;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Document(collection = "payment_payment_documents")
public class PaymentDocument {
    private String id;
    private String documentId;
    private UUID paymentId;
    private UUID uploadedBy;
    private UUID verifiedBy;
    private PaymentDocumentStatus status;
    @CreatedDate
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public PaymentDocument(String id, String documentId, UUID paymentId, UUID uploadedBy, UUID verifiedBy, PaymentDocumentStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.documentId = documentId;
        this.paymentId = paymentId;
        this.uploadedBy = uploadedBy;
        this.verifiedBy = verifiedBy;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public PaymentDocument() {
    }

    public static PaymentDocumentBuilder builder() {
        return new PaymentDocumentBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PaymentDocument that = (PaymentDocument) o;
        return Objects.equals(id, that.id) && Objects.equals(documentId, that.documentId) && Objects.equals(paymentId, that.paymentId) && Objects.equals(uploadedBy, that.uploadedBy) && Objects.equals(verifiedBy, that.verifiedBy) && status == that.status && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, documentId, paymentId, uploadedBy, verifiedBy, status, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "PaymentDocument{" +
                "id='" + id + '\'' +
                ", documentId='" + documentId + '\'' +
                ", paymentId=" + paymentId +
                ", uploadedBy=" + uploadedBy +
                ", verifiedBy=" + verifiedBy +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public String getId() {
        return this.id;
    }

    public String getDocumentId() {
        return this.documentId;
    }

    public UUID getPaymentId() {
        return this.paymentId;
    }

    public UUID getUploadedBy() {
        return this.uploadedBy;
    }

    public UUID getVerifiedBy() {
        return this.verifiedBy;
    }

    public PaymentDocumentStatus getStatus() {
        return this.status;
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

    public void setPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
    }

    public void setUploadedBy(UUID uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public void setVerifiedBy(UUID verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public void setStatus(PaymentDocumentStatus status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static class PaymentDocumentBuilder {
        private String id;
        private String documentId;
        private UUID paymentId;
        private UUID uploadedBy;
        private UUID verifiedBy;
        private PaymentDocumentStatus status;
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

        public PaymentDocumentBuilder paymentId(UUID paymentId) {
            this.paymentId = paymentId;
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

        public PaymentDocumentBuilder status(PaymentDocumentStatus status) {
            this.status = status;
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
            return new PaymentDocument(this.id, this.documentId, this.paymentId, this.uploadedBy, this.verifiedBy, this.status, this.createdAt, this.updatedAt);
        }

        public String toString() {
            return "PaymentDocument.PaymentDocumentBuilder(id=" + this.id + ", documentId=" + this.documentId + ", paymentId=" + this.paymentId + ", uploadedBy=" + this.uploadedBy + ", verifiedBy=" + this.verifiedBy + ", status=" + this.status + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + ")";
        }
    }
}
