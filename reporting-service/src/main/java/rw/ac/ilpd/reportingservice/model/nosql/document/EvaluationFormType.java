package rw.ac.ilpd.reportingservice.model.nosql.document;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "report_evaluation_form_types")
public class EvaluationFormType {
    @Id
    private String id;
    private String name;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private boolean isDeleted;

    public EvaluationFormType(String id, String name, LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy, String updatedBy, boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.isDeleted = isDeleted;
    }

    public EvaluationFormType() {
    }

    public static EvaluationFormTypeBuilder builder() {
        return new EvaluationFormTypeBuilder();
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public boolean isDeleted() {
        return this.isDeleted;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public static class EvaluationFormTypeBuilder {
        private String id;
        private String name;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String createdBy;
        private String updatedBy;
        private boolean isDeleted;

        EvaluationFormTypeBuilder() {
        }

        public EvaluationFormTypeBuilder id(String id) {
            this.id = id;
            return this;
        }

        public EvaluationFormTypeBuilder name(String name) {
            this.name = name;
            return this;
        }

        public EvaluationFormTypeBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public EvaluationFormTypeBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public EvaluationFormTypeBuilder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public EvaluationFormTypeBuilder updatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
            return this;
        }

        public EvaluationFormTypeBuilder isDeleted(boolean isDeleted) {
            this.isDeleted = isDeleted;
            return this;
        }

        public EvaluationFormType build() {
            return new EvaluationFormType(this.id, this.name, this.createdAt, this.updatedAt, this.createdBy, this.updatedBy, this.isDeleted);
        }

        public String toString() {
            return "EvaluationFormType.EvaluationFormTypeBuilder(id=" + this.id + ", name=" + this.name + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + ", createdBy=" + this.createdBy + ", updatedBy=" + this.updatedBy + ", isDeleted=" + this.isDeleted + ")";
        }
    }
}
