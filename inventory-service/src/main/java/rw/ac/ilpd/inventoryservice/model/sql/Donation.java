package rw.ac.ilpd.inventoryservice.model.sql;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

/**
 * This table maps stock in items to the donor who donated them.
 * It is a product of the many-to-many relationship between the stock
 * in and source of funds entities since one donor can donate multiple
 * items and one item can be donated by multiple donors.
 */
@Entity
@Table(name = "inv_donations")
public class Donation {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private StockIn stockInId;

    @ManyToOne(fetch = FetchType.LAZY)
    private SourceOfFund donorId;

    private Double quantity;

    public Donation() {
    }

    public Donation(UUID id, StockIn stockInId, SourceOfFund donorId, Double quantity) {
        this.id = id;
        this.stockInId = stockInId;
        this.donorId = donorId;
        this.quantity = quantity;
    }

    public static DonationBuilder builder() {
        return new DonationBuilder();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public StockIn getStockInId() {
        return stockInId;
    }

    public void setStockInId(StockIn stockInId) {
        this.stockInId = stockInId;
    }

    public SourceOfFund getDonorId() {
        return donorId;
    }

    public void setDonorId(SourceOfFund donorId) {
        this.donorId = donorId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Donation{" +
                "id: " + id +
                ", stockInId: " + stockInId +
                ", donorId: " + donorId +
                ", quantity: " + quantity +
                '}';
    }

    public static class DonationBuilder {
        private UUID id;
        private StockIn stockInId;
        private SourceOfFund donorId;
        private Double quantity;

        DonationBuilder() {
        }

        public DonationBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public DonationBuilder stockInId(StockIn stockInId) {
            this.stockInId = stockInId;
            return this;
        }

        public DonationBuilder donorId(SourceOfFund donorId) {
            this.donorId = donorId;
            return this;
        }

        public DonationBuilder quantity(Double quantity) {
            this.quantity = quantity;
            return this;
        }

        public Donation build() {
            return new Donation(this.id, this.stockInId, this.donorId, this.quantity);
        }

        public String toString() {
            return "Donation.DonationBuilder(id=" + this.id + ", stockInId=" + this.stockInId + ", donorId=" + this.donorId + ", quantity=" + this.quantity + ")";
        }
    }
}
