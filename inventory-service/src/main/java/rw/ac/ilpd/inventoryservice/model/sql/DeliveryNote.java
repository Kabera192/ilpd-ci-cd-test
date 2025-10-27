package rw.ac.ilpd.inventoryservice.model.sql;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Document given by the supplier after a transaction
 */
@Entity
@Table(name = "inv_delivery_notes")
public class DeliveryNote {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private UUID id;

    // References the external user table and uses the string
    // id from mongo
    private String supplierId;

    // references the Document table and uses the string id from
    // mongo
    private String documentId;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public DeliveryNote() {
    }

    public DeliveryNote(UUID id, String supplierId, String documentId, LocalDateTime createdAt) {
        this.id = id;
        this.supplierId = supplierId;
        this.documentId = documentId;
        this.createdAt = createdAt;
    }

    public static DeliveryNoteBuilder builder() {
        return new DeliveryNoteBuilder();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "DeliveryNote{" +
                "id: " + id +
                ", supplierId: '" + supplierId + '\'' +
                ", documentId: '" + documentId + '\'' +
                ", createdAt: " + createdAt +
                '}';
    }

    public static class DeliveryNoteBuilder {
        private UUID id;
        private String supplierId;
        private String documentId;
        private LocalDateTime createdAt;

        DeliveryNoteBuilder() {
        }

        public DeliveryNoteBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public DeliveryNoteBuilder supplierId(String supplierId) {
            this.supplierId = supplierId;
            return this;
        }

        public DeliveryNoteBuilder documentId(String documentId) {
            this.documentId = documentId;
            return this;
        }

        public DeliveryNoteBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public DeliveryNote build() {
            return new DeliveryNote(this.id, this.supplierId, this.documentId, this.createdAt);
        }

        public String toString() {
            return "DeliveryNote.DeliveryNoteBuilder(id=" + this.id + ", supplierId=" + this.supplierId + ", documentId=" + this.documentId + ", createdAt=" + this.createdAt + ")";
        }
    }
}
