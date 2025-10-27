package rw.ac.ilpd.paymentservice.model.nosql;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import rw.ac.ilpd.sharedlibrary.enums.FeeUserStatus;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Document(collection = "payment_fee_users")
public class FeeUser {
    @Id
    private String id;
    private UUID feeId;
    private UUID userId; //referencing to user table found in auth service
    private double amount;
    private FeeUserStatus status;
    private UUID requestId;
    private UUID applicationId;
    private LocalDateTime createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UUID getFeeId() {
        return feeId;
    }

    public void setFeeId(UUID feeId) {
        this.feeId = feeId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public FeeUserStatus getStatus() {
        return status;
    }

    public void setStatus(FeeUserStatus status) {
        this.status = status;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }

    public UUID getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(UUID applicationId) {
        this.applicationId = applicationId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public FeeUser(String id, UUID feeId, UUID userId, double amount, FeeUserStatus status, UUID requestId, UUID applicationId, LocalDateTime createdAt) {
        this.id = id;
        this.feeId = feeId;
        this.userId = userId;
        this.amount = amount;
        this.status = status;
        this.requestId = requestId;
        this.applicationId = applicationId;
        this.createdAt = createdAt;
    }

    public FeeUser() {
    }

    public static FeeUserBuilder builder() {
        return new FeeUserBuilder();
    }
    @Override
    public String toString() {
        return "FeeUser(id=" + this.id + ", feeId=" + this.feeId + ", userId=" + this.userId + ", amount=" + this.amount + ", status=" + this.status + ", requestId=" + this.requestId + ", applicationId=" + this.applicationId + ", createdAt=" + this.createdAt + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FeeUser feeUser = (FeeUser) o;
        return Double.compare(amount, feeUser.amount) == 0 && Objects.equals(id, feeUser.id) && Objects.equals(feeId, feeUser.feeId) && Objects.equals(userId, feeUser.userId) && status == feeUser.status && Objects.equals(requestId, feeUser.requestId) && Objects.equals(applicationId, feeUser.applicationId) && Objects.equals(createdAt, feeUser.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, feeId, userId, amount, status, requestId, applicationId, createdAt);
    }

    public static class FeeUserBuilder {
        private String id;
        private UUID feeId;
        private UUID userId;
        private double amount;
        private FeeUserStatus status;
        private UUID requestId;
        private UUID applicationId;
        private LocalDateTime createdAt;

        FeeUserBuilder() {
        }

        public FeeUserBuilder id(String id) {
            this.id = id;
            return this;
        }

        public FeeUserBuilder feeId(UUID feeId) {
            this.feeId = feeId;
            return this;
        }

        public FeeUserBuilder userId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public FeeUserBuilder amount(double amount) {
            this.amount = amount;
            return this;
        }

        public FeeUserBuilder status(FeeUserStatus status) {
            this.status = status;
            return this;
        }

        public FeeUserBuilder requestId(UUID requestId) {
            this.requestId = requestId;
            return this;
        }

        public FeeUserBuilder applicationId(UUID applicationId) {
            this.applicationId = applicationId;
            return this;
        }

        public FeeUserBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public FeeUser build() {
            return new FeeUser(this.id, this.feeId, this.userId, this.amount, this.status, this.requestId, this.applicationId, this.createdAt);
        }

        public String toString() {
            return "FeeUser.FeeUserBuilder(id=" + this.id + ", feeId=" + this.feeId + ", userId=" + this.userId + ", amount=" + this.amount + ", status=" + this.status + ", requestId=" + this.requestId + ", applicationId=" + this.applicationId + ", createdAt=" + this.createdAt + ")";
        }
    }
}
