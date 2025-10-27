package rw.ac.ilpd.registrationservice.model.nosql.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * This entity stores the comments that a registrar makes on deferred application
 * documents whereby one document submission can have multiple comments.
 */
@Document(collection = "reg_application_defer_comments")
public class ApplicationDeferringComment {
    @Id
    private String id;

    private String comment;

    private String submittedDocumentId;

    private LocalDateTime createdAt;

    public ApplicationDeferringComment() {
    }

    public ApplicationDeferringComment(String id, String comment, String submittedDocumentId, LocalDateTime createdAt)
    {
        this.id = id;
        this.comment = comment;
        this.submittedDocumentId = submittedDocumentId;
        this.createdAt = createdAt;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public String getSubmittedDocumentId()
    {
        return submittedDocumentId;
    }

    public void setSubmittedDocumentId(String submittedDocumentId)
    {
        this.submittedDocumentId = submittedDocumentId;
    }

    public LocalDateTime getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt)
    {
        this.createdAt = createdAt;
    }

    @Override
    public String toString()
    {
        return "ApplicationDeferringComment{" + "id: '" + id + '\'' + ", comment: '" + comment + '\'' + ", " +
                "submittedDocumentId: '" + submittedDocumentId + '\'' + ", createdAt: " + createdAt + '}';
    }
}
