package rw.ac.ilpd.paymentservice.model.nosql;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import rw.ac.ilpd.sharedlibrary.enums.PaymentMethod;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "payment_hostel_reservation_room_payments")
public class HostelReservationRoomPayment {
    @Id
    private String id;
    private UUID reservationRoomId;
    private double amount;
    private UUID proofOfPaymentDocId;
    private PaymentMethod paymentMethod;
    private LocalDateTime createdAt;


    public HostelReservationRoomPayment(String id, UUID reservationRoomId, double amount, UUID proofOfPaymentDocId, PaymentMethod paymentMethod, LocalDateTime createdAt) {
        this.id = id;
        this.reservationRoomId = reservationRoomId;
        this.amount = amount;
        this.proofOfPaymentDocId = proofOfPaymentDocId;
        this.paymentMethod = paymentMethod;
        this.createdAt = createdAt;
    }

    public HostelReservationRoomPayment() {
    }

    public static HostelReservationRoomPaymentBuilder builder() {
        return new HostelReservationRoomPaymentBuilder();
    }

    public String getId() {
        return this.id;
    }

    public UUID getReservationRoomId() {
        return this.reservationRoomId;
    }

    public double getAmount() {
        return this.amount;
    }

    public UUID getProofOfPaymentDocId() {
        return this.proofOfPaymentDocId;
    }

    public PaymentMethod getPaymentMethod() {
        return this.paymentMethod;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setReservationRoomId(UUID reservationRoomId) {
        this.reservationRoomId = reservationRoomId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setProofOfPaymentDocId(UUID proofOfPaymentDocId) {
        this.proofOfPaymentDocId = proofOfPaymentDocId;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String toString() {
        return "HostelReservationRoomPayment(id=" + this.getId() + ", reservationRoomId=" + this.getReservationRoomId() + ", amount=" + this.getAmount() + ", proofOfPaymentDocId=" + this.getProofOfPaymentDocId() + ", paymentMethod=" + this.getPaymentMethod() + ", createdAt=" + this.getCreatedAt() + ")";
    }

    public static class HostelReservationRoomPaymentBuilder {
        private String id;
        private UUID reservationRoomId;
        private double amount;
        private UUID proofOfPaymentDocId;
        private PaymentMethod paymentMethod;
        private LocalDateTime createdAt;

        HostelReservationRoomPaymentBuilder() {
        }

        public HostelReservationRoomPaymentBuilder id(String id) {
            this.id = id;
            return this;
        }

        public HostelReservationRoomPaymentBuilder reservationRoomId(UUID reservationRoomId) {
            this.reservationRoomId = reservationRoomId;
            return this;
        }

        public HostelReservationRoomPaymentBuilder amount(double amount) {
            this.amount = amount;
            return this;
        }

        public HostelReservationRoomPaymentBuilder proofOfPaymentDocId(UUID proofOfPaymentDocId) {
            this.proofOfPaymentDocId = proofOfPaymentDocId;
            return this;
        }

        public HostelReservationRoomPaymentBuilder paymentMethod(PaymentMethod paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }

        public HostelReservationRoomPaymentBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public HostelReservationRoomPayment build() {
            return new HostelReservationRoomPayment(this.id, this.reservationRoomId, this.amount, this.proofOfPaymentDocId, this.paymentMethod, this.createdAt);
        }

        public String toString() {
            return "HostelReservationRoomPayment.HostelReservationRoomPaymentBuilder(id=" + this.id + ", reservationRoomId=" + this.reservationRoomId + ", amount=" + this.amount + ", proofOfPaymentDocId=" + this.proofOfPaymentDocId + ", paymentMethod=" + this.paymentMethod + ", createdAt=" + this.createdAt + ")";
        }
    }
}
