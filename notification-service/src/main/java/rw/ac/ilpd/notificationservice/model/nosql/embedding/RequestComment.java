package rw.ac.ilpd.notificationservice.model.nosql.embedding;

import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

public class RequestComment {
    private String content;

    private UUID createdBy;

    @CreatedDate
    private LocalDateTime createdAt;

    public RequestComment(String content, UUID createdBy, LocalDateTime createdAt) {
        this.content = content;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    public RequestComment() {
    }

    public static RequestCommentBuilder builder() {
        return new RequestCommentBuilder();
    }

    public String getContent() {
        return this.content;
    }

    public UUID getCreatedBy() {
        return this.createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedBy(UUID createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static class RequestCommentBuilder {
        private String content;
        private UUID createdBy;
        private LocalDateTime createdAt;

        RequestCommentBuilder() {
        }

        public RequestCommentBuilder content(String content) {
            this.content = content;
            return this;
        }

        public RequestCommentBuilder createdBy(UUID createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public RequestCommentBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public RequestComment build() {
            return new RequestComment(this.content, this.createdBy, this.createdAt);
        }

        public String toString() {
            return "RequestComment.RequestCommentBuilder(content=" + this.content + ", createdBy=" + this.createdBy + ", createdAt=" + this.createdAt + ")";
        }
    }
}
