package rw.ac.ilpd.inventoryservice.model.sql;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

/**
 * This entity stores details of user's request for an item from the stock of ILPD.
 * This table therefore maps a particular user to a particular request for an item in the stock.
 */
@Entity
@Table(name = "inv_user_item_requisitions")
public class UserItemRequisition {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private UUID id;

    // References the user table and the user who made
    // the user item requisition
    private UUID userId;

    // References the request table, the request which the
    // user item requisition belongs to
    private String requestId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Item itemId;

    private Integer proposedQuantity;

    private boolean deleteStatus;


    public UserItemRequisition(UUID id, UUID userId, String requestId, Item itemId, Integer proposedQuantity, boolean deleteStatus) {
        this.id = id;
        this.userId = userId;
        this.requestId = requestId;
        this.itemId = itemId;
        this.proposedQuantity = proposedQuantity;
        this.deleteStatus = deleteStatus;
    }

    public UserItemRequisition() {
    }

    public static UserItemRequisitionBuilder builder() {
        return new UserItemRequisitionBuilder();
    }

    public UUID getId() {
        return this.id;
    }

    public UUID getUserId() {
        return this.userId;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public Item getItemId() {
        return this.itemId;
    }

    public Integer getProposedQuantity() {
        return this.proposedQuantity;
    }

    public boolean isDeleteStatus() {
        return this.deleteStatus;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setItemId(Item itemId) {
        this.itemId = itemId;
    }

    public void setProposedQuantity(Integer proposedQuantity) {
        this.proposedQuantity = proposedQuantity;
    }

    public void setDeleteStatus(boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public static class UserItemRequisitionBuilder {
        private UUID id;
        private UUID userId;
        private String requestId;
        private Item itemId;
        private Integer proposedQuantity;
        private boolean deleteStatus;

        UserItemRequisitionBuilder() {
        }

        public UserItemRequisitionBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public UserItemRequisitionBuilder userId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public UserItemRequisitionBuilder requestId(String requestId) {
            this.requestId = requestId;
            return this;
        }

        public UserItemRequisitionBuilder itemId(Item itemId) {
            this.itemId = itemId;
            return this;
        }

        public UserItemRequisitionBuilder proposedQuantity(Integer proposedQuantity) {
            this.proposedQuantity = proposedQuantity;
            return this;
        }

        public UserItemRequisitionBuilder deleteStatus(boolean deleteStatus) {
            this.deleteStatus = deleteStatus;
            return this;
        }

        public UserItemRequisition build() {
            return new UserItemRequisition(this.id, this.userId, this.requestId, this.itemId, this.proposedQuantity, this.deleteStatus);
        }

        public String toString() {
            return "UserItemRequisition.UserItemRequisitionBuilder(id=" + this.id + ", userId=" + this.userId + ", requestId=" + this.requestId + ", itemId=" + this.itemId + ", proposedQuantity=" + this.proposedQuantity + ", deleteStatus=" + this.deleteStatus + ")";
        }
    }
}
