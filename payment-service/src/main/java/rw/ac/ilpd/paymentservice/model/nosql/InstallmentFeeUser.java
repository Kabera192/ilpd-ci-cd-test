package rw.ac.ilpd.paymentservice.model.nosql;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "payment_installment_fee_users")
public class InstallmentFeeUser {
    @Id
    private String id;
    private UUID feeUserId;
    private UUID installmentId;

    public InstallmentFeeUser(String id, UUID feeUserId, UUID installmentId) {
        this.id = id;
        this.feeUserId = feeUserId;
        this.installmentId = installmentId;
    }

    public InstallmentFeeUser() {
    }

    public static InstallmentFeeUserBuilder builder() {
        return new InstallmentFeeUserBuilder();
    }

    public String getId() {
        return this.id;
    }

    public UUID getFeeUserId() {
        return this.feeUserId;
    }

    public UUID getInstallmentId() {
        return this.installmentId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFeeUserId(UUID feeUserId) {
        this.feeUserId = feeUserId;
    }

    public void setInstallmentId(UUID installmentId) {
        this.installmentId = installmentId;
    }

    public String toString() {
        return "InstallmentFeeUser(id=" + this.getId() + ", feeUserId=" + this.getFeeUserId() + ", installmentId=" + this.getInstallmentId() + ")";
    }

    public static class InstallmentFeeUserBuilder {
        private String id;
        private UUID feeUserId;
        private UUID installmentId;

        InstallmentFeeUserBuilder() {
        }

        public InstallmentFeeUserBuilder id(String id) {
            this.id = id;
            return this;
        }

        public InstallmentFeeUserBuilder feeUserId(UUID feeUserId) {
            this.feeUserId = feeUserId;
            return this;
        }

        public InstallmentFeeUserBuilder installmentId(UUID installmentId) {
            this.installmentId = installmentId;
            return this;
        }

        public InstallmentFeeUser build() {
            return new InstallmentFeeUser(this.id, this.feeUserId, this.installmentId);
        }

        public String toString() {
            return "InstallmentFeeUser.InstallmentFeeUserBuilder(id=" + this.id + ", feeUserId=" + this.feeUserId + ", installmentId=" + this.installmentId + ")";
        }
    }
}
