/*
*
* This table maps a user to a notification and it is populated whenever a user reads a notification.
* This helps us to know if a user has read a notification.
* */
package rw.ac.ilpd.notificationservice.model.nosql.document;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "notif_received_notifications")
public class ReceivedNotification {
    @Id
    private String id;

    private String notificationId;

    private UUID userId;  // Will be null when unspecified

    @CreatedDate
    private LocalDateTime createdAt;

    public ReceivedNotification(String id, String notificationId, UUID userId, LocalDateTime createdAt) {
        this.id = id;
        this.notificationId = notificationId;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public ReceivedNotification() {
    }

    public static ReceivedNotificationBuilder builder() {
        return new ReceivedNotificationBuilder();
    }

    public String getId() {
        return this.id;
    }

    public String getNotificationId() {
        return this.notificationId;
    }

    public UUID getUserId() {
        return this.userId;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static class ReceivedNotificationBuilder {
        private String id;
        private String notificationId;
        private UUID userId;
        private LocalDateTime createdAt;

        ReceivedNotificationBuilder() {
        }

        public ReceivedNotificationBuilder id(String id) {
            this.id = id;
            return this;
        }

        public ReceivedNotificationBuilder notificationId(String notificationId) {
            this.notificationId = notificationId;
            return this;
        }

        public ReceivedNotificationBuilder userId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public ReceivedNotificationBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ReceivedNotification build() {
            return new ReceivedNotification(this.id, this.notificationId, this.userId, this.createdAt);
        }

        public String toString() {
            return "ReceivedNotification.ReceivedNotificationBuilder(id=" + this.id + ", notificationId=" + this.notificationId + ", userId=" + this.userId + ", createdAt=" + this.createdAt + ")";
        }
    }
}
