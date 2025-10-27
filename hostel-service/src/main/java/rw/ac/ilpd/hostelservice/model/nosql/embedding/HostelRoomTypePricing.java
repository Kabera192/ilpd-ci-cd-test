package rw.ac.ilpd.hostelservice.model.nosql.embedding;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import rw.ac.ilpd.sharedlibrary.enums.ClientType;
import rw.ac.ilpd.sharedlibrary.enums.PaymentPeriod;
import rw.ac.ilpd.sharedlibrary.enums.PricingStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class HostelRoomTypePricing {
    private UUID id = UUID.randomUUID();
    private String roomTypeId;
    private int capacity;

    private BigDecimal price;

    private PaymentPeriod paymentPeriod;

    private ClientType clientType;

    private PricingStatus pricingStatus;

    private String description;
    @CreatedDate
    private LocalDateTime createdAt = LocalDateTime.now();
    @LastModifiedDate
    private LocalDateTime updatedAt = LocalDateTime.now();

    public HostelRoomTypePricing(UUID id, String roomTypeId, int capacity, BigDecimal price, PaymentPeriod paymentPeriod, ClientType clientType, PricingStatus pricingStatus, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.roomTypeId = roomTypeId;
        this.capacity = capacity;
        this.price = price;
        this.paymentPeriod = paymentPeriod;
        this.clientType = clientType;
        this.pricingStatus = pricingStatus;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public HostelRoomTypePricing() {
    }

    private static LocalDateTime $default$updatedAt() {
        return LocalDateTime.now();
    }

    public static HostelRoomTypePricingBuilder builder() {
        return new HostelRoomTypePricingBuilder();
    }

    /**
     * Checks if this pricing has the same criteria as another pricing
     */
    public boolean hasSameCriteriaAs(HostelRoomTypePricing other) {
        return this.capacity == other.capacity &&
                this.paymentPeriod == other.paymentPeriod &&
                this.clientType == other.clientType;
    }

    /**
     * Marks this pricing as inactive
     */
    public void deactivate() {
        this.pricingStatus = PricingStatus.INACTIVE;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Marks this pricing as active
     */
    public void activate() {
        this.pricingStatus = PricingStatus.ACTIVE;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Checks if this pricing is currently active
     */
    public boolean isActive() {
        return this.pricingStatus == PricingStatus.ACTIVE;
    }

    public UUID getId() {
        return this.id;
    }

    public String getRoomTypeId() {
        return this.roomTypeId;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public PaymentPeriod getPaymentPeriod() {
        return this.paymentPeriod;
    }

    public ClientType getClientType() {
        return this.clientType;
    }

    public PricingStatus getPricingStatus() {
        return this.pricingStatus;
    }

    public String getDescription() {
        return this.description;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setRoomTypeId(String roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setPaymentPeriod(PaymentPeriod paymentPeriod) {
        this.paymentPeriod = paymentPeriod;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public void setPricingStatus(PricingStatus pricingStatus) {
        this.pricingStatus = pricingStatus;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String toString() {
        return "HostelRoomTypePricing(id=" + this.getId() + ", roomTypeId=" + this.getRoomTypeId() + ", capacity=" + this.getCapacity() + ", price=" + this.getPrice() + ", paymentPeriod=" + this.getPaymentPeriod() + ", clientType=" + this.getClientType() + ", pricingStatus=" + this.getPricingStatus() + ", description=" + this.getDescription() + ", createdAt=" + this.getCreatedAt() + ", updatedAt=" + this.getUpdatedAt() + ")";
    }

    public static class HostelRoomTypePricingBuilder {
        private UUID id;
        private String roomTypeId;
        private int capacity;
        private BigDecimal price;
        private PaymentPeriod paymentPeriod;
        private ClientType clientType;
        private PricingStatus pricingStatus;
        private String description;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt$value;
        private boolean updatedAt$set;

        HostelRoomTypePricingBuilder() {
        }

        public HostelRoomTypePricingBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public HostelRoomTypePricingBuilder roomTypeId(String roomTypeId) {
            this.roomTypeId = roomTypeId;
            return this;
        }

        public HostelRoomTypePricingBuilder capacity(int capacity) {
            this.capacity = capacity;
            return this;
        }

        public HostelRoomTypePricingBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public HostelRoomTypePricingBuilder paymentPeriod(PaymentPeriod paymentPeriod) {
            this.paymentPeriod = paymentPeriod;
            return this;
        }

        public HostelRoomTypePricingBuilder clientType(ClientType clientType) {
            this.clientType = clientType;
            return this;
        }

        public HostelRoomTypePricingBuilder pricingStatus(PricingStatus pricingStatus) {
            this.pricingStatus = pricingStatus;
            return this;
        }

        public HostelRoomTypePricingBuilder description(String description) {
            this.description = description;
            return this;
        }

        public HostelRoomTypePricingBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public HostelRoomTypePricingBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt$value = updatedAt;
            this.updatedAt$set = true;
            return this;
        }

        public HostelRoomTypePricing build() {
            LocalDateTime updatedAt$value = this.updatedAt$value;
            if (!this.updatedAt$set) {
                updatedAt$value = HostelRoomTypePricing.$default$updatedAt();
            }
            return new HostelRoomTypePricing(this.id, this.roomTypeId, this.capacity, this.price, this.paymentPeriod, this.clientType, this.pricingStatus, this.description, this.createdAt, updatedAt$value);
        }

        public String toString() {
            return "HostelRoomTypePricing.HostelRoomTypePricingBuilder(id=" + this.id + ", roomTypeId=" + this.roomTypeId + ", capacity=" + this.capacity + ", price=" + this.price + ", paymentPeriod=" + this.paymentPeriod + ", clientType=" + this.clientType + ", pricingStatus=" + this.pricingStatus + ", description=" + this.description + ", createdAt=" + this.createdAt + ", updatedAt$value=" + this.updatedAt$value + ")";
        }
    }
}
