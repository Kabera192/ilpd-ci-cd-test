package rw.ac.ilpd.notificationservice.model.nosql.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "notif_document_types")
public class DocumentType {
    @Id
    private String id;

    private String name; // Will store "REQUEST" or "TEMPLATE"
    private String path;

    public DocumentType(String id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
    }

    public DocumentType() {
    }

    public static DocumentTypeBuilder builder() {
        return new DocumentTypeBuilder();
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getPath() {
        return this.path;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String toString() {
        return "DocumentType(id=" + this.getId() + ", name=" + this.getName() + ", path=" + this.getPath() + ")";
    }

    public static class DocumentTypeBuilder {
        private String id;
        private String name;
        private String path;

        DocumentTypeBuilder() {
        }

        public DocumentTypeBuilder id(String id) {
            this.id = id;
            return this;
        }

        public DocumentTypeBuilder name(String name) {
            this.name = name;
            return this;
        }

        public DocumentTypeBuilder path(String path) {
            this.path = path;
            return this;
        }

        public DocumentType build() {
            return new DocumentType(this.id, this.name, this.path);
        }

        public String toString() {
            return "DocumentType.DocumentTypeBuilder(id=" + this.id + ", name=" + this.name + ", path=" + this.path + ")";
        }
    }
}
