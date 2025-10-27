package rw.ac.ilpd.hostelservice.model.nosql.document;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import rw.ac.ilpd.hostelservice.model.nosql.embedding.PaymentDocument;
import rw.ac.ilpd.sharedlibrary.enums.PaymentMethod;
import rw.ac.ilpd.sharedlibrary.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Document(collection = "hostel_hostel_reservation_room_payments")
public class HostelReservationRoomPayment {
    @Id
    private String id;
    private BigDecimal amount;
    private String transactionNumber;
    private UUID creditAccountId; //referencing setting table
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    private List<PaymentDocument> paymentDocuments;

    public HostelReservationRoomPayment(String id, BigDecimal amount, String transactionNumber, UUID creditAccountId, PaymentMethod paymentMethod, PaymentStatus paymentStatus, LocalDateTime createdAt, LocalDateTime updatedAt, List<PaymentDocument> paymentDocuments) {
        this.id = id;
        this.amount = amount;
        this.transactionNumber = transactionNumber;
        this.creditAccountId = creditAccountId;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.paymentDocuments = paymentDocuments;
    }

    public HostelReservationRoomPayment() {
    }

    public static HostelReservationRoomPaymentBuilder builder() {
        return new HostelReservationRoomPaymentBuilder();
    }

    public String getId() {
        return this.id;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public String getTransactionNumber() {
        return this.transactionNumber;
    }

    public UUID getCreditAccountId() {
        return this.creditAccountId;
    }

    public PaymentMethod getPaymentMethod() {
        return this.paymentMethod;
    }

    public PaymentStatus getPaymentStatus() {
        return this.paymentStatus;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public List<PaymentDocument> getPaymentDocuments() {
        return this.paymentDocuments;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public void setCreditAccountId(UUID creditAccountId) {
        this.creditAccountId = creditAccountId;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setPaymentDocuments(List<PaymentDocument> paymentDocuments) {
        this.paymentDocuments = paymentDocuments;
    }

    public static class HostelReservationRoomPaymentBuilder {
        private String id;
        private BigDecimal amount;
        private String transactionNumber;
        private UUID creditAccountId;
        private PaymentMethod paymentMethod;
        private PaymentStatus paymentStatus;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private List<PaymentDocument> paymentDocuments;

        HostelReservationRoomPaymentBuilder() {
        }

        public HostelReservationRoomPaymentBuilder id(String id) {
            this.id = id;
            return this;
        }

        public HostelReservationRoomPaymentBuilder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public HostelReservationRoomPaymentBuilder transactionNumber(String transactionNumber) {
            this.transactionNumber = transactionNumber;
            return this;
        }

        public HostelReservationRoomPaymentBuilder creditAccountId(UUID creditAccountId) {
            this.creditAccountId = creditAccountId;
            return this;
        }

        public HostelReservationRoomPaymentBuilder paymentMethod(PaymentMethod paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }

        public HostelReservationRoomPaymentBuilder paymentStatus(PaymentStatus paymentStatus) {
            this.paymentStatus = paymentStatus;
            return this;
        }

        public HostelReservationRoomPaymentBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public HostelReservationRoomPaymentBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public HostelReservationRoomPaymentBuilder paymentDocuments(List<PaymentDocument> paymentDocuments) {
            this.paymentDocuments = paymentDocuments;
            return this;
        }

        public HostelReservationRoomPayment build() {
            return new HostelReservationRoomPayment(this.id, this.amount, this.transactionNumber, this.creditAccountId, this.paymentMethod, this.paymentStatus, this.createdAt, this.updatedAt, this.paymentDocuments);
        }

        public String toString() {
            return "HostelReservationRoomPayment.HostelReservationRoomPaymentBuilder(id=" + this.id + ", amount=" + this.amount + ", transactionNumber=" + this.transactionNumber + ", creditAccountId=" + this.creditAccountId + ", paymentMethod=" + this.paymentMethod + ", paymentStatus=" + this.paymentStatus + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + ", paymentDocuments=" + this.paymentDocuments + ")";
        }
    }
}
