package rw.ac.ilpd.paymentservice.model.sql;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.UuidGenerator;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payment_payments")
public class Payment {
    @Id @UuidGenerator(style = UuidGenerator.Style.AUTO)
    private UUID id;
    private UUID feeUserId; //referencing fwe user
    private double amount;
    private LocalDateTime created_at;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getFeeUserId() {
        return feeUserId;
    }

    public void setFeeUserId(UUID feeUserId) {
        this.feeUserId = feeUserId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public  Payment(){}
    public Payment(UUID feeUserId, double amount, LocalDateTime created_at) {
        this.feeUserId = feeUserId;
        this.amount = amount;
        this.created_at = created_at;
    }

    public Payment(UUID id, UUID feeUserId, double amount, LocalDateTime created_at) {
        this.id = id;
        this.feeUserId = feeUserId;
        this.amount = amount;
        this.created_at = created_at;
    }
}
