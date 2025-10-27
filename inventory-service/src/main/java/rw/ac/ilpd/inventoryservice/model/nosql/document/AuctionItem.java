package rw.ac.ilpd.inventoryservice.model.nosql.document;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * These are the items that are nolonger in the ILPD property and are to be auctioned away.
 */
@Document(collection = "inv_auction_items")
public class AuctionItem {
    @Id
    private String id;

    private UUID stockOutId;

    private UUID recordedBy;

    private Double quantity;

    @CreatedDate
    private LocalDateTime createdAt;

    public AuctionItem() {
    }

    public AuctionItem(String id, UUID stockOutId, UUID recordedBy, Double quantity, LocalDateTime createdAt) {
        this.id = id;
        this.stockOutId = stockOutId;
        this.recordedBy = recordedBy;
        this.quantity = quantity;
        this.createdAt = createdAt;
    }

    public static AuctionItemBuilder builder() {
        return new AuctionItemBuilder();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UUID getStockOutId() {
        return stockOutId;
    }

    public void setStockOutId(UUID stockOutId) {
        this.stockOutId = stockOutId;
    }

    public UUID getRecordedBy() {
        return recordedBy;
    }

    public void setRecordedBy(UUID recordedBy) {
        this.recordedBy = recordedBy;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "AuctionItem{" +
                "id: '" + id + '\'' +
                ", stockOutId: " + stockOutId +
                ", recordedBy: " + recordedBy +
                ", quantity: " + quantity +
                ", createdAt: " + createdAt +
                '}';
    }

    public static class AuctionItemBuilder {
        private String id;
        private UUID stockOutId;
        private UUID recordedBy;
        private Double quantity;
        private LocalDateTime createdAt;

        AuctionItemBuilder() {
        }

        public AuctionItemBuilder id(String id) {
            this.id = id;
            return this;
        }

        public AuctionItemBuilder stockOutId(UUID stockOutId) {
            this.stockOutId = stockOutId;
            return this;
        }

        public AuctionItemBuilder recordedBy(UUID recordedBy) {
            this.recordedBy = recordedBy;
            return this;
        }

        public AuctionItemBuilder quantity(Double quantity) {
            this.quantity = quantity;
            return this;
        }

        public AuctionItemBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public AuctionItem build() {
            return new AuctionItem(this.id, this.stockOutId, this.recordedBy, this.quantity, this.createdAt);
        }

        public String toString() {
            return "AuctionItem.AuctionItemBuilder(id=" + this.id + ", stockOutId=" + this.stockOutId + ", recordedBy=" + this.recordedBy + ", quantity=" + this.quantity + ", createdAt=" + this.createdAt + ")";
        }
    }
}
