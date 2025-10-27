package rw.ac.ilpd.academicservice.model.sql;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "aca_course_materials")
public class CourseMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    @Lob
    @Column(columnDefinition = "text")
    private String description;

    private String documentId;
    @CreatedDate
    private LocalDateTime createdAt;

    private boolean isDeleted;
    @LastModifiedDate
    private LocalDateTime updatedAt;


    public CourseMaterial(UUID id, String title, String description, String documentId,LocalDateTime createdAt, boolean isDeleted, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
        this.description = description;
        this.documentId = documentId;
        this.isDeleted = isDeleted;
        this.updatedAt = updatedAt;
    }

    public CourseMaterial() {
    }

    public static CourseMaterialBuilder builder() {
        return new CourseMaterialBuilder();
    }

    public UUID getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getDocumentId() {
        return this.documentId;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public boolean isDeleted() {
        return this.isDeleted;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof CourseMaterial)) return false;
        final CourseMaterial other = (CourseMaterial) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$title = this.getTitle();
        final Object other$title = other.getTitle();
        if (this$title == null ? other$title != null : !this$title.equals(other$title)) return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description))
            return false;
        final Object this$documentId = this.getDocumentId();
        final Object other$documentId = other.getDocumentId();
        if (this$documentId == null ? other$documentId != null : !this$documentId.equals(other$documentId))
            return false;
        final Object this$createdAt = this.getCreatedAt();
        final Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        if (this.isDeleted() != other.isDeleted()) return false;
        final Object this$updatedAt = this.getUpdatedAt();
        final Object other$updatedAt = other.getUpdatedAt();
        if (this$updatedAt == null ? other$updatedAt != null : !this$updatedAt.equals(other$updatedAt)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CourseMaterial;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $title = this.getTitle();
        result = result * PRIME + ($title == null ? 43 : $title.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        final Object $documentId = this.getDocumentId();
        result = result * PRIME + ($documentId == null ? 43 : $documentId.hashCode());
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        result = result * PRIME + (this.isDeleted() ? 79 : 97);
        final Object $updatedAt = this.getUpdatedAt();
        result = result * PRIME + ($updatedAt == null ? 43 : $updatedAt.hashCode());
        return result;
    }

    public String toString() {
        return "CourseMaterial(id=" + this.getId() + ", title=" + this.getTitle() + ", description=" + this.getDescription() + ", documentId=" + this.getDocumentId() + ", createdAt=" + this.getCreatedAt() + ", isDeleted=" + this.isDeleted() + ", updatedAt=" + this.getUpdatedAt() + ")";
    }

    public static class CourseMaterialBuilder {
        private UUID id;
        private String title;
        private String description;
        private String documentId;
        private boolean isDeleted;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        CourseMaterialBuilder() {
        }

        public CourseMaterialBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public CourseMaterialBuilder title(String title) {
            this.title = title;
            return this;
        }

        public CourseMaterialBuilder description(String description) {
            this.description = description;
            return this;
        }

        public CourseMaterialBuilder documentId(String documentId) {
            this.documentId = documentId;
            return this;
        }

        public CourseMaterialBuilder isDeleted(boolean isDeleted) {
            this.isDeleted = isDeleted;
            return this;
        }
        public CourseMaterialBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }
        public CourseMaterialBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public CourseMaterial build() {
            return new CourseMaterial(this.id, this.title, this.description, this.documentId,this.createdAt, this.isDeleted, this.updatedAt);
        }

        public String toString() {
            return "CourseMaterial.CourseMaterialBuilder(id=" + this.id + ", title=" + this.title + ", description=" + this.description + ", documentId=" + this.documentId + ", createdAt=" + this.createdAt + ", isDeleted=" + this.isDeleted + ", updatedAt=" + this.updatedAt + ")";
        }
    }
}
