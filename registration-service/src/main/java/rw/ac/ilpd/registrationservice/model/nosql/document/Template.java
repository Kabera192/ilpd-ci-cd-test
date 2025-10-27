package rw.ac.ilpd.registrationservice.model.nosql.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Template entity for document templates
 */
@Document(collection = "templates")
public class Template {
    @Id
    private String id;

    private String name;
    private String documentId;
    private Boolean isActive;

    public Template() {
    }

    public Template(String id, String name, String documentId, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.documentId = documentId;
        this.isActive = isActive;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "Template{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", documentId='" + documentId + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}

