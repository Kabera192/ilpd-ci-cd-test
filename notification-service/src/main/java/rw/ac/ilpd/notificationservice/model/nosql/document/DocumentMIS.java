package rw.ac.ilpd.notificationservice.model.nosql.document;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "notif_documents")
public class DocumentMIS {
    @Id
    private String id = UUID.randomUUID().toString();
    private String url;
    private String typeId;
    @CreatedDate
    private LocalDateTime createdAt;

    public DocumentMIS(String id, String url, String typeId, LocalDateTime createdAt) {
        this.id = id;
        this.url = url;
        this.typeId = typeId;
        this.createdAt = createdAt;
    }

    public DocumentMIS() {
    }

    private static String $default$id() {
        return UUID.randomUUID().toString();
    }

    public static DocumentMISBuilder builder() {
        return new DocumentMISBuilder();
    }

    public String getId() {
        return this.id;
    }

    public String getUrl() {
        return this.url;
    }

    public String getTypeId() {
        return this.typeId;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static class DocumentMISBuilder {
        private String id$value;
        private boolean id$set;
        private String url;
        private String typeId;
        private LocalDateTime createdAt;

        DocumentMISBuilder() {
        }

        public DocumentMISBuilder id(String id) {
            this.id$value = id;
            this.id$set = true;
            return this;
        }

        public DocumentMISBuilder url(String url) {
            this.url = url;
            return this;
        }

        public DocumentMISBuilder typeId(String typeId) {
            this.typeId = typeId;
            return this;
        }

        public DocumentMISBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public DocumentMIS build() {
            String id$value = this.id$value;
            if (!this.id$set) {
                id$value = DocumentMIS.$default$id();
            }
            return new DocumentMIS(id$value, this.url, this.typeId, this.createdAt);
        }

        public String toString() {
            return "DocumentMIS.DocumentMISBuilder(id$value=" + this.id$value + ", url=" + this.url + ", typeId=" + this.typeId + ", createdAt=" + this.createdAt + ")";
        }
    }
}
