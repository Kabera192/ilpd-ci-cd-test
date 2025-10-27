/*
* This entity defines what various requests in the ILPD MIS
* should look like. Example requests include, requests to suspend,
* request to transfer to another intake, item requisition among others.
* */
package rw.ac.ilpd.notificationservice.model.nosql.document;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import rw.ac.ilpd.notificationservice.model.nosql.embedding.RequestAttachmentDocument;
import rw.ac.ilpd.notificationservice.model.nosql.embedding.RequestComment;
import rw.ac.ilpd.notificationservice.model.nosql.embedding.UserRequestApproval;
import rw.ac.ilpd.sharedlibrary.enums.RequestStatus;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Document(collection = "notif_requests")
public class Request {
    @Id
    private String id;

    private String content;

    private String requestTypeId;

    private UUID createdBy;

    private RequestStatus status;

    private UUID intakeToId;

    private UUID intakeFromId;

    private UUID moduleId;

    private UUID intakeId;

    private Boolean isDeleted;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedDate
    private LocalDateTime createdAt;

    private List<RequestComment> comments = new LinkedList<>();
    private List<RequestAttachmentDocument> attachmentDocuments = new LinkedList<>();
    private List<UserRequestApproval> requestApprovals = new LinkedList<>();

    public Request(String id, String content, String requestTypeId, UUID createdBy, RequestStatus status, UUID intakeToId, UUID intakeFromId, UUID moduleId, UUID intakeId, Boolean isDeleted, LocalDateTime updatedAt, LocalDateTime createdAt, List<RequestComment> comments, List<RequestAttachmentDocument> attachmentDocuments, List<UserRequestApproval> requestApprovals) {
        this.id = id;
        this.content = content;
        this.requestTypeId = requestTypeId;
        this.createdBy = createdBy;
        this.status = status;
        this.intakeToId = intakeToId;
        this.intakeFromId = intakeFromId;
        this.moduleId = moduleId;
        this.intakeId = intakeId;
        this.isDeleted = isDeleted;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.comments = comments;
        this.attachmentDocuments = attachmentDocuments;
        this.requestApprovals = requestApprovals;
    }

    public Request() {
    }

    public static RequestBuilder builder() {
        return new RequestBuilder();
    }

    public String getId() {
        return this.id;
    }

    public String getContent() {
        return this.content;
    }

    public String getRequestTypeId() {
        return this.requestTypeId;
    }

    public UUID getCreatedBy() {
        return this.createdBy;
    }

    public RequestStatus getStatus() {
        return this.status;
    }

    public UUID getIntakeToId() {
        return this.intakeToId;
    }

    public UUID getIntakeFromId() {
        return this.intakeFromId;
    }

    public UUID getModuleId() {
        return this.moduleId;
    }

    public UUID getIntakeId() {
        return this.intakeId;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public List<RequestComment> getComments() {
        return this.comments;
    }

    public List<RequestAttachmentDocument> getAttachmentDocuments() {
        return this.attachmentDocuments;
    }

    public List<UserRequestApproval> getRequestApprovals() {
        return this.requestApprovals;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRequestTypeId(String requestTypeId) {
        this.requestTypeId = requestTypeId;
    }

    public void setCreatedBy(UUID createdBy) {
        this.createdBy = createdBy;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public void setIntakeToId(UUID intakeToId) {
        this.intakeToId = intakeToId;
    }

    public void setIntakeFromId(UUID intakeFromId) {
        this.intakeFromId = intakeFromId;
    }

    public void setModuleId(UUID moduleId) {
        this.moduleId = moduleId;
    }

    public void setIntakeId(UUID intakeId) {
        this.intakeId = intakeId;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setComments(List<RequestComment> comments) {
        this.comments = comments;
    }

    public void setAttachmentDocuments(List<RequestAttachmentDocument> attachmentDocuments) {
        this.attachmentDocuments = attachmentDocuments;
    }

    public void setRequestApprovals(List<UserRequestApproval> requestApprovals) {
        this.requestApprovals = requestApprovals;
    }

    public static class RequestBuilder {
        private String id;
        private String content;
        private String requestTypeId;
        private UUID createdBy;
        private RequestStatus status;
        private UUID intakeToId;
        private UUID intakeFromId;
        private UUID moduleId;
        private UUID intakeId;
        private Boolean isDeleted;
        private LocalDateTime updatedAt;
        private LocalDateTime createdAt;
        private List<RequestComment> comments;
        private List<RequestAttachmentDocument> attachmentDocuments;
        private List<UserRequestApproval> requestApprovals;

        RequestBuilder() {
        }

        public RequestBuilder id(String id) {
            this.id = id;
            return this;
        }

        public RequestBuilder content(String content) {
            this.content = content;
            return this;
        }

        public RequestBuilder requestTypeId(String requestTypeId) {
            this.requestTypeId = requestTypeId;
            return this;
        }

        public RequestBuilder createdBy(UUID createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public RequestBuilder status(RequestStatus status) {
            this.status = status;
            return this;
        }

        public RequestBuilder intakeToId(UUID intakeToId) {
            this.intakeToId = intakeToId;
            return this;
        }

        public RequestBuilder intakeFromId(UUID intakeFromId) {
            this.intakeFromId = intakeFromId;
            return this;
        }

        public RequestBuilder moduleId(UUID moduleId) {
            this.moduleId = moduleId;
            return this;
        }

        public RequestBuilder intakeId(UUID intakeId) {
            this.intakeId = intakeId;
            return this;
        }

        public RequestBuilder isDeleted(Boolean isDeleted) {
            this.isDeleted = isDeleted;
            return this;
        }

        public RequestBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public RequestBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public RequestBuilder comments(List<RequestComment> comments) {
            this.comments = comments;
            return this;
        }

        public RequestBuilder attachmentDocuments(List<RequestAttachmentDocument> attachmentDocuments) {
            this.attachmentDocuments = attachmentDocuments;
            return this;
        }

        public RequestBuilder requestApprovals(List<UserRequestApproval> requestApprovals) {
            this.requestApprovals = requestApprovals;
            return this;
        }

        public Request build() {
            return new Request(this.id, this.content, this.requestTypeId, this.createdBy, this.status, this.intakeToId, this.intakeFromId, this.moduleId, this.intakeId, this.isDeleted, this.updatedAt, this.createdAt, this.comments, this.attachmentDocuments, this.requestApprovals);
        }

        public String toString() {
            return "Request.RequestBuilder(id=" + this.id + ", content=" + this.content + ", requestTypeId=" + this.requestTypeId + ", createdBy=" + this.createdBy + ", status=" + this.status + ", intakeToId=" + this.intakeToId + ", intakeFromId=" + this.intakeFromId + ", moduleId=" + this.moduleId + ", intakeId=" + this.intakeId + ", isDeleted=" + this.isDeleted + ", updatedAt=" + this.updatedAt + ", createdAt=" + this.createdAt + ", comments=" + this.comments + ", attachmentDocuments=" + this.attachmentDocuments + ", requestApprovals=" + this.requestApprovals + ")";
        }
    }
}
