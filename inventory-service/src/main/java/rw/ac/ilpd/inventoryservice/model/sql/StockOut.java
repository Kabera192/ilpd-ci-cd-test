package rw.ac.ilpd.inventoryservice.model.sql;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * This stores the informations about the items out of stock and where they have to go
 */
@Entity
@Table(name = "inv_stock_outs")
public class StockOut {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private StockIn stockIn;

    @ManyToOne(fetch = FetchType.LAZY)
    private Room roomId;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserItemRequisition userItemRequisition;

    private Integer quantity;

    @CreationTimestamp
    private LocalDateTime createdAt;


    public StockOut(UUID id, StockIn stockIn, Room roomId, UserItemRequisition userItemRequisition, Integer quantity, LocalDateTime createdAt) {
        this.id = id;
        this.stockIn = stockIn;
        this.roomId = roomId;
        this.userItemRequisition = userItemRequisition;
        this.quantity = quantity;
        this.createdAt = createdAt;
    }

    public StockOut() {
    }

    public static StockOutBuilder builder() {
        return new StockOutBuilder();
    }

    public UUID getId() {
        return this.id;
    }

    public StockIn getStockIn() {
        return this.stockIn;
    }

    public Room getRoomId() {
        return this.roomId;
    }

    public UserItemRequisition getUserItemRequisition() {
        return this.userItemRequisition;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setStockIn(StockIn stockIn) {
        this.stockIn = stockIn;
    }

    public void setRoomId(Room roomId) {
        this.roomId = roomId;
    }

    public void setUserItemRequisition(UserItemRequisition userItemRequisition) {
        this.userItemRequisition = userItemRequisition;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof StockOut)) return false;
        final StockOut other = (StockOut) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$stockIn = this.getStockIn();
        final Object other$stockIn = other.getStockIn();
        if (this$stockIn == null ? other$stockIn != null : !this$stockIn.equals(other$stockIn)) return false;
        final Object this$roomId = this.getRoomId();
        final Object other$roomId = other.getRoomId();
        if (this$roomId == null ? other$roomId != null : !this$roomId.equals(other$roomId)) return false;
        final Object this$userItemRequisition = this.getUserItemRequisition();
        final Object other$userItemRequisition = other.getUserItemRequisition();
        if (this$userItemRequisition == null ? other$userItemRequisition != null : !this$userItemRequisition.equals(other$userItemRequisition))
            return false;
        final Object this$quantity = this.getQuantity();
        final Object other$quantity = other.getQuantity();
        if (this$quantity == null ? other$quantity != null : !this$quantity.equals(other$quantity)) return false;
        final Object this$createdAt = this.getCreatedAt();
        final Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof StockOut;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $stockIn = this.getStockIn();
        result = result * PRIME + ($stockIn == null ? 43 : $stockIn.hashCode());
        final Object $roomId = this.getRoomId();
        result = result * PRIME + ($roomId == null ? 43 : $roomId.hashCode());
        final Object $userItemRequisition = this.getUserItemRequisition();
        result = result * PRIME + ($userItemRequisition == null ? 43 : $userItemRequisition.hashCode());
        final Object $quantity = this.getQuantity();
        result = result * PRIME + ($quantity == null ? 43 : $quantity.hashCode());
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        return result;
    }

    public String toString() {
        return "StockOut(id=" + this.getId() + ", stockIn=" + this.getStockIn() + ", roomId=" + this.getRoomId() + ", userItemRequisition=" + this.getUserItemRequisition() + ", quantity=" + this.getQuantity() + ", createdAt=" + this.getCreatedAt() + ")";
    }

    public static class StockOutBuilder {
        private UUID id;
        private StockIn stockIn;
        private Room roomId;
        private UserItemRequisition userItemRequisition;
        private Integer quantity;
        private LocalDateTime createdAt;

        StockOutBuilder() {
        }

        public StockOutBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public StockOutBuilder stockIn(StockIn stockIn) {
            this.stockIn = stockIn;
            return this;
        }

        public StockOutBuilder roomId(Room roomId) {
            this.roomId = roomId;
            return this;
        }

        public StockOutBuilder userItemRequisition(UserItemRequisition userItemRequisition) {
            this.userItemRequisition = userItemRequisition;
            return this;
        }

        public StockOutBuilder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public StockOutBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public StockOut build() {
            return new StockOut(this.id, this.stockIn, this.roomId, this.userItemRequisition, this.quantity, this.createdAt);
        }

        public String toString() {
            return "StockOut.StockOutBuilder(id=" + this.id + ", stockIn=" + this.stockIn + ", roomId=" + this.roomId + ", userItemRequisition=" + this.userItemRequisition + ", quantity=" + this.quantity + ", createdAt=" + this.createdAt + ")";
        }
    }
}
