package rw.ac.ilpd.notificationservice.model.nosql.embedding;

import org.springframework.data.annotation.CreatedDate;
import rw.ac.ilpd.sharedlibrary.enums.RequestApprovalStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserRequestApproval {
    private UUID userApprovalId;

    private RequestApprovalStatus status;  // Enum for APPROVED/REJECTED

    @CreatedDate
    private LocalDateTime createdAt;

    public UserRequestApproval(UUID userApprovalId, RequestApprovalStatus status, LocalDateTime createdAt) {
        this.userApprovalId = userApprovalId;
        this.status = status;
        this.createdAt = createdAt;
    }

    public UserRequestApproval() {
    }

    public static UserRequestApprovalBuilder builder() {
        return new UserRequestApprovalBuilder();
    }

    public UUID getUserApprovalId() {
        return this.userApprovalId;
    }

    public RequestApprovalStatus getStatus() {
        return this.status;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setUserApprovalId(UUID userApprovalId) {
        this.userApprovalId = userApprovalId;
    }

    public void setStatus(RequestApprovalStatus status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static class UserRequestApprovalBuilder {
        private UUID userApprovalId;
        private RequestApprovalStatus status;
        private LocalDateTime createdAt;

        UserRequestApprovalBuilder() {
        }

        public UserRequestApprovalBuilder userApprovalId(UUID userApprovalId) {
            this.userApprovalId = userApprovalId;
            return this;
        }

        public UserRequestApprovalBuilder status(RequestApprovalStatus status) {
            this.status = status;
            return this;
        }

        public UserRequestApprovalBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public UserRequestApproval build() {
            return new UserRequestApproval(this.userApprovalId, this.status, this.createdAt);
        }

        public String toString() {
            return "UserRequestApproval.UserRequestApprovalBuilder(userApprovalId=" + this.userApprovalId + ", status=" + this.status + ", createdAt=" + this.createdAt + ")";
        }
    }
}
