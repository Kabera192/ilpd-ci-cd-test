/*
* This entity stores a list of templates that are used throughout ILPD.
* Templates include; template for Certificate of Compeletion, template
* for Proforma Invoice among others.
* */
package rw.ac.ilpd.notificationservice.model.nosql.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "notif_templates")
public class Template {
    @Id
    private String id;

    private String name;

    private DocumentMIS document;

    private Boolean isActive;

    public Template(String id, String name, DocumentMIS document, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.document = document;
        this.isActive = isActive;
    }

    public Template() {
    }

    public static TemplateBuilder builder() {
        return new TemplateBuilder();
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public DocumentMIS getDocument() {
        return this.document;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDocument(DocumentMIS document) {
        this.document = document;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public static class TemplateBuilder {
        private String id;
        private String name;
        private DocumentMIS document;
        private Boolean isActive;

        TemplateBuilder() {
        }

        public TemplateBuilder id(String id) {
            this.id = id;
            return this;
        }

        public TemplateBuilder name(String name) {
            this.name = name;
            return this;
        }

        public TemplateBuilder document(DocumentMIS document) {
            this.document = document;
            return this;
        }

        public TemplateBuilder isActive(Boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public Template build() {
            return new Template(this.id, this.name, this.document, this.isActive);
        }

        public String toString() {
            return "Template.TemplateBuilder(id=" + this.id + ", name=" + this.name + ", document=" + this.document + ", isActive=" + this.isActive + ")";
        }
    }
}
