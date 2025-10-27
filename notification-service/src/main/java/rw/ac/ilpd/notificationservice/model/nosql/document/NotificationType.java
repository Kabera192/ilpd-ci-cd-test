/*
*
* Classifies notifications into various categories.
* */
package rw.ac.ilpd.notificationservice.model.nosql.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import rw.ac.ilpd.sharedlibrary.enums.NotificationTypeEnum;

@Document(collection = "notif_notification_types")
public class NotificationType {
    @Id
    private String id;

    private NotificationTypeEnum name;

    public NotificationType(String id, NotificationTypeEnum name) {
        this.id = id;
        this.name = name;
    }

    public NotificationType() {
    }

    public static NotificationTypeBuilder builder() {
        return new NotificationTypeBuilder();
    }

    public String getId() {
        return this.id;
    }

    public NotificationTypeEnum getName() {
        return this.name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(NotificationTypeEnum name) {
        this.name = name;
    }

    public static class NotificationTypeBuilder {
        private String id;
        private NotificationTypeEnum name;

        NotificationTypeBuilder() {
        }

        public NotificationTypeBuilder id(String id) {
            this.id = id;
            return this;
        }

        public NotificationTypeBuilder name(NotificationTypeEnum name) {
            this.name = name;
            return this;
        }

        public NotificationType build() {
            return new NotificationType(this.id, this.name);
        }

        public String toString() {
            return "NotificationType.NotificationTypeBuilder(id=" + this.id + ", name=" + this.name + ")";
        }
    }
}
