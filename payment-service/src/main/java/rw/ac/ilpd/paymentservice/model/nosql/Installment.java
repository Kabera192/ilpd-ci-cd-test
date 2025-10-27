package rw.ac.ilpd.paymentservice.model.nosql;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "payment_installments")
public class Installment {
    @Id
    private String id;
    private UUID fee_id;
    private int installmentNumber;
    private double amount;


    public Installment(String id, UUID fee_id, int installmentNumber, double amount) {
        this.id = id;
        this.fee_id = fee_id;
        this.installmentNumber = installmentNumber;
        this.amount = amount;
    }

    public Installment() {
    }

    public static InstallmentBuilder builder() {
        return new InstallmentBuilder();
    }

    public String getId() {
        return this.id;
    }

    public UUID getFee_id() {
        return this.fee_id;
    }

    public int getInstallmentNumber() {
        return this.installmentNumber;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFee_id(UUID fee_id) {
        this.fee_id = fee_id;
    }

    public void setInstallmentNumber(int installmentNumber) {
        this.installmentNumber = installmentNumber;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public static class InstallmentBuilder {
        private String id;
        private UUID fee_id;
        private int installmentNumber;
        private double amount;

        InstallmentBuilder() {
        }

        public InstallmentBuilder id(String id) {
            this.id = id;
            return this;
        }

        public InstallmentBuilder fee_id(UUID fee_id) {
            this.fee_id = fee_id;
            return this;
        }

        public InstallmentBuilder installmentNumber(int installmentNumber) {
            this.installmentNumber = installmentNumber;
            return this;
        }

        public InstallmentBuilder amount(double amount) {
            this.amount = amount;
            return this;
        }

        public Installment build() {
            return new Installment(this.id, this.fee_id, this.installmentNumber, this.amount);
        }

        public String toString() {
            return "Installment.InstallmentBuilder(id=" + this.id + ", fee_id=" + this.fee_id + ", installmentNumber=" + this.installmentNumber + ", amount=" + this.amount + ")";
        }
    }
}
