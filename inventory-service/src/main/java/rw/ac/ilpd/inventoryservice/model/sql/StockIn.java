package rw.ac.ilpd.inventoryservice.model.sql;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Contains the information on how many items have been added in the stock and when.
 */
@Entity
@Table(name = "inv_stock_ins")
public class StockIn {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Item itemId;

    private Double unitPrice;

    private Integer quantity;

    private LocalDate acquisitionDate;

    private LocalDate expirationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private DeliveryNote deliveryNote;

    private Integer remainingQuantity;

    @CreationTimestamp
    private LocalDateTime createdAt;


    public StockIn(UUID id, Item itemId, Double unitPrice, Integer quantity, LocalDate acquisitionDate, LocalDate expirationDate, DeliveryNote deliveryNote, Integer remainingQuantity, LocalDateTime createdAt) {
        this.id = id;
        this.itemId = itemId;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.acquisitionDate = acquisitionDate;
        this.expirationDate = expirationDate;
        this.deliveryNote = deliveryNote;
        this.remainingQuantity = remainingQuantity;
        this.createdAt = createdAt;
    }

    public StockIn() {
    }

    public static StockInBuilder builder() {
        return new StockInBuilder();
    }

    public UUID getId() {
        return this.id;
    }

    public Item getItemId() {
        return this.itemId;
    }

    public Double getUnitPrice() {
        return this.unitPrice;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public LocalDate getAcquisitionDate() {
        return this.acquisitionDate;
    }

    public LocalDate getExpirationDate() {
        return this.expirationDate;
    }

    public DeliveryNote getDeliveryNote() {
        return this.deliveryNote;
    }

    public Integer getRemainingQuantity() {
        return this.remainingQuantity;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setItemId(Item itemId) {
        this.itemId = itemId;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setAcquisitionDate(LocalDate acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setDeliveryNote(DeliveryNote deliveryNote) {
        this.deliveryNote = deliveryNote;
    }

    public void setRemainingQuantity(Integer remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof StockIn)) return false;
        final StockIn other = (StockIn) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$itemId = this.getItemId();
        final Object other$itemId = other.getItemId();
        if (this$itemId == null ? other$itemId != null : !this$itemId.equals(other$itemId)) return false;
        final Object this$unitPrice = this.getUnitPrice();
        final Object other$unitPrice = other.getUnitPrice();
        if (this$unitPrice == null ? other$unitPrice != null : !this$unitPrice.equals(other$unitPrice)) return false;
        final Object this$quantity = this.getQuantity();
        final Object other$quantity = other.getQuantity();
        if (this$quantity == null ? other$quantity != null : !this$quantity.equals(other$quantity)) return false;
        final Object this$acquisitionDate = this.getAcquisitionDate();
        final Object other$acquisitionDate = other.getAcquisitionDate();
        if (this$acquisitionDate == null ? other$acquisitionDate != null : !this$acquisitionDate.equals(other$acquisitionDate))
            return false;
        final Object this$expirationDate = this.getExpirationDate();
        final Object other$expirationDate = other.getExpirationDate();
        if (this$expirationDate == null ? other$expirationDate != null : !this$expirationDate.equals(other$expirationDate))
            return false;
        final Object this$deliveryNote = this.getDeliveryNote();
        final Object other$deliveryNote = other.getDeliveryNote();
        if (this$deliveryNote == null ? other$deliveryNote != null : !this$deliveryNote.equals(other$deliveryNote))
            return false;
        final Object this$remainingQuantity = this.getRemainingQuantity();
        final Object other$remainingQuantity = other.getRemainingQuantity();
        if (this$remainingQuantity == null ? other$remainingQuantity != null : !this$remainingQuantity.equals(other$remainingQuantity))
            return false;
        final Object this$createdAt = this.getCreatedAt();
        final Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof StockIn;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $itemId = this.getItemId();
        result = result * PRIME + ($itemId == null ? 43 : $itemId.hashCode());
        final Object $unitPrice = this.getUnitPrice();
        result = result * PRIME + ($unitPrice == null ? 43 : $unitPrice.hashCode());
        final Object $quantity = this.getQuantity();
        result = result * PRIME + ($quantity == null ? 43 : $quantity.hashCode());
        final Object $acquisitionDate = this.getAcquisitionDate();
        result = result * PRIME + ($acquisitionDate == null ? 43 : $acquisitionDate.hashCode());
        final Object $expirationDate = this.getExpirationDate();
        result = result * PRIME + ($expirationDate == null ? 43 : $expirationDate.hashCode());
        final Object $deliveryNote = this.getDeliveryNote();
        result = result * PRIME + ($deliveryNote == null ? 43 : $deliveryNote.hashCode());
        final Object $remainingQuantity = this.getRemainingQuantity();
        result = result * PRIME + ($remainingQuantity == null ? 43 : $remainingQuantity.hashCode());
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        return result;
    }

    public String toString() {
        return "StockIn(id=" + this.getId() + ", itemId=" + this.getItemId() + ", unitPrice=" + this.getUnitPrice() + ", quantity=" + this.getQuantity() + ", acquisitionDate=" + this.getAcquisitionDate() + ", expirationDate=" + this.getExpirationDate() + ", deliveryNote=" + this.getDeliveryNote() + ", remainingQuantity=" + this.getRemainingQuantity() + ", createdAt=" + this.getCreatedAt() + ")";
    }

    public static class StockInBuilder {
        private UUID id;
        private Item itemId;
        private Double unitPrice;
        private Integer quantity;
        private LocalDate acquisitionDate;
        private LocalDate expirationDate;
        private DeliveryNote deliveryNote;
        private Integer remainingQuantity;
        private LocalDateTime createdAt;

        StockInBuilder() {
        }

        public StockInBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public StockInBuilder itemId(Item itemId) {
            this.itemId = itemId;
            return this;
        }

        public StockInBuilder unitPrice(Double unitPrice) {
            this.unitPrice = unitPrice;
            return this;
        }

        public StockInBuilder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public StockInBuilder acquisitionDate(LocalDate acquisitionDate) {
            this.acquisitionDate = acquisitionDate;
            return this;
        }

        public StockInBuilder expirationDate(LocalDate expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }

        public StockInBuilder deliveryNote(DeliveryNote deliveryNote) {
            this.deliveryNote = deliveryNote;
            return this;
        }

        public StockInBuilder remainingQuantity(Integer remainingQuantity) {
            this.remainingQuantity = remainingQuantity;
            return this;
        }

        public StockInBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public StockIn build() {
            return new StockIn(this.id, this.itemId, this.unitPrice, this.quantity, this.acquisitionDate, this.expirationDate, this.deliveryNote, this.remainingQuantity, this.createdAt);
        }

        public String toString() {
            return "StockIn.StockInBuilder(id=" + this.id + ", itemId=" + this.itemId + ", unitPrice=" + this.unitPrice + ", quantity=" + this.quantity + ", acquisitionDate=" + this.acquisitionDate + ", expirationDate=" + this.expirationDate + ", deliveryNote=" + this.deliveryNote + ", remainingQuantity=" + this.remainingQuantity + ", createdAt=" + this.createdAt + ")";
        }
    }
}
