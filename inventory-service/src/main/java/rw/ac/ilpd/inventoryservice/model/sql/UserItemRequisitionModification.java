package rw.ac.ilpd.inventoryservice.model.sql;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * This entity keeps a record of all the modifications that happen to a
 * particular user item requisition request. This happens because when a
 * request for items is created it has to go through a chain of approvers
 * who have the right to modify the initial request.
 */
@Entity
@Table(name = "inv_user_item_requisition_modifications")
public class UserItemRequisitionModification {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserItemRequisition userItemRequisition;

    private UUID updatedBy;

    private Integer updatedQuantity;

    private boolean deleteStatus;

    @CreationTimestamp
    private LocalDateTime createdAt;


    public UserItemRequisitionModification(UUID id, UserItemRequisition userItemRequisition, UUID updatedBy, Integer updatedQuantity, boolean deleteStatus, LocalDateTime createdAt) {
        this.id = id;
        this.userItemRequisition = userItemRequisition;
        this.updatedBy = updatedBy;
        this.updatedQuantity = updatedQuantity;
        this.deleteStatus = deleteStatus;
        this.createdAt = createdAt;
    }

    public UserItemRequisitionModification() {
    }

    public static UserItemRequisitionModificationBuilder builder() {
        return new UserItemRequisitionModificationBuilder();
    }

    public UUID getId() {
        return this.id;
    }

    public UserItemRequisition getUserItemRequisition() {
        return this.userItemRequisition;
    }

    public UUID getUpdatedBy() {
        return this.updatedBy;
    }

    public Integer getUpdatedQuantity() {
        return this.updatedQuantity;
    }

    public boolean isDeleteStatus() {
        return this.deleteStatus;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setUserItemRequisition(UserItemRequisition userItemRequisition) {
        this.userItemRequisition = userItemRequisition;
    }

    public void setUpdatedBy(UUID updatedBy) {
        this.updatedBy = updatedBy;
    }

    public void setUpdatedQuantity(Integer updatedQuantity) {
        this.updatedQuantity = updatedQuantity;
    }

    public void setDeleteStatus(boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UserItemRequisitionModification)) return false;
        final UserItemRequisitionModification other = (UserItemRequisitionModification) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$userItemRequisition = this.getUserItemRequisition();
        final Object other$userItemRequisition = other.getUserItemRequisition();
        if (this$userItemRequisition == null ? other$userItemRequisition != null : !this$userItemRequisition.equals(other$userItemRequisition))
            return false;
        final Object this$updatedBy = this.getUpdatedBy();
        final Object other$updatedBy = other.getUpdatedBy();
        if (this$updatedBy == null ? other$updatedBy != null : !this$updatedBy.equals(other$updatedBy)) return false;
        final Object this$updatedQuantity = this.getUpdatedQuantity();
        final Object other$updatedQuantity = other.getUpdatedQuantity();
        if (this$updatedQuantity == null ? other$updatedQuantity != null : !this$updatedQuantity.equals(other$updatedQuantity))
            return false;
        if (this.isDeleteStatus() != other.isDeleteStatus()) return false;
        final Object this$createdAt = this.getCreatedAt();
        final Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserItemRequisitionModification;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $userItemRequisition = this.getUserItemRequisition();
        result = result * PRIME + ($userItemRequisition == null ? 43 : $userItemRequisition.hashCode());
        final Object $updatedBy = this.getUpdatedBy();
        result = result * PRIME + ($updatedBy == null ? 43 : $updatedBy.hashCode());
        final Object $updatedQuantity = this.getUpdatedQuantity();
        result = result * PRIME + ($updatedQuantity == null ? 43 : $updatedQuantity.hashCode());
        result = result * PRIME + (this.isDeleteStatus() ? 79 : 97);
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        return result;
    }

    public String toString() {
        return "UserItemRequisitionModification(id=" + this.getId() + ", userItemRequisition=" + this.getUserItemRequisition() + ", updatedBy=" + this.getUpdatedBy() + ", updatedQuantity=" + this.getUpdatedQuantity() + ", deleteStatus=" + this.isDeleteStatus() + ", createdAt=" + this.getCreatedAt() + ")";
    }

    public static class UserItemRequisitionModificationBuilder {
        private UUID id;
        private UserItemRequisition userItemRequisition;
        private UUID updatedBy;
        private Integer updatedQuantity;
        private boolean deleteStatus;
        private LocalDateTime createdAt;

        UserItemRequisitionModificationBuilder() {
        }

        public UserItemRequisitionModificationBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public UserItemRequisitionModificationBuilder userItemRequisition(UserItemRequisition userItemRequisition) {
            this.userItemRequisition = userItemRequisition;
            return this;
        }

        public UserItemRequisitionModificationBuilder updatedBy(UUID updatedBy) {
            this.updatedBy = updatedBy;
            return this;
        }

        public UserItemRequisitionModificationBuilder updatedQuantity(Integer updatedQuantity) {
            this.updatedQuantity = updatedQuantity;
            return this;
        }

        public UserItemRequisitionModificationBuilder deleteStatus(boolean deleteStatus) {
            this.deleteStatus = deleteStatus;
            return this;
        }

        public UserItemRequisitionModificationBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public UserItemRequisitionModification build() {
            return new UserItemRequisitionModification(this.id, this.userItemRequisition, this.updatedBy, this.updatedQuantity, this.deleteStatus, this.createdAt);
        }

        public String toString() {
            return "UserItemRequisitionModification.UserItemRequisitionModificationBuilder(id=" + this.id + ", userItemRequisition=" + this.userItemRequisition + ", updatedBy=" + this.updatedBy + ", updatedQuantity=" + this.updatedQuantity + ", deleteStatus=" + this.deleteStatus + ", createdAt=" + this.createdAt + ")";
        }
    }
}
