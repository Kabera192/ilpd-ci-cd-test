package rw.ac.ilpd.notificationservice.model.nosql.document;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import rw.ac.ilpd.notificationservice.model.nosql.embedding.NotificationDestination;
import rw.ac.ilpd.sharedlibrary.enums.NotificationTypeEnum;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * This entity stores details of the various notifications sent across the MIS
 * such as who created the notification, the target, the content and so on.
 * */
@Document(collection = "notif_notifications")
public class Notification {
    @Id
    private String id;

    private String title;

    private String content;

    private UUID senderId;

    private NotificationTypeEnum notificationType;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private List<NotificationDestination> destinations;

    public Notification(String id, String title, String content, UUID senderId, NotificationTypeEnum notificationType, LocalDateTime createdAt, LocalDateTime updatedAt, List<NotificationDestination> destinations) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.senderId = senderId;
        this.notificationType = notificationType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.destinations = destinations;
    }

    public Notification() {
    }

    public static NotificationBuilder builder() {
        return new NotificationBuilder();
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public UUID getSenderId() {
        return this.senderId;
    }

    public NotificationTypeEnum getNotificationType() {
        return this.notificationType;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public List<NotificationDestination> getDestinations() {
        return this.destinations;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSenderId(UUID senderId) {
        this.senderId = senderId;
    }

    public void setNotificationType(NotificationTypeEnum notificationType) {
        this.notificationType = notificationType;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setDestinations(List<NotificationDestination> destinations) {
        this.destinations = destinations;
    }

    public static class NotificationBuilder {
        private String id;
        private String title;
        private String content;
        private UUID senderId;
        private NotificationTypeEnum notificationType;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private List<NotificationDestination> destinations;

        NotificationBuilder() {
        }

        public NotificationBuilder id(String id) {
            this.id = id;
            return this;
        }

        public NotificationBuilder title(String title) {
            this.title = title;
            return this;
        }

        public NotificationBuilder content(String content) {
            this.content = content;
            return this;
        }

        public NotificationBuilder senderId(UUID senderId) {
            this.senderId = senderId;
            return this;
        }

        public NotificationBuilder notificationType(NotificationTypeEnum notificationType) {
            this.notificationType = notificationType;
            return this;
        }

        public NotificationBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public NotificationBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public NotificationBuilder destinations(List<NotificationDestination> destinations) {
            this.destinations = destinations;
            return this;
        }

        public Notification build() {
            return new Notification(this.id, this.title, this.content, this.senderId, this.notificationType, this.createdAt, this.updatedAt, this.destinations);
        }

        public String toString() {
            return "Notification.NotificationBuilder(id=" + this.id + ", title=" + this.title + ", content=" + this.content + ", senderId=" + this.senderId + ", notificationType=" + this.notificationType + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + ", destinations=" + this.destinations + ")";
        }
    }
}
