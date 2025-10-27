package rw.ac.ilpd.academicservice.model.nosql.document;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import rw.ac.ilpd.academicservice.model.nosql.embedding.DeliberationDistinction;
import rw.ac.ilpd.sharedlibrary.enums.ValidityStatus;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "aca_deliberation_distinction_groups")
public class DeliberationDistinctionGroup {
    @Id
    private String id;
    private String name;
    @CreatedDate
    private LocalDateTime createdAt;
    private ValidityStatus status;
    private List<DeliberationDistinction> deliberationDistinctions;

    public DeliberationDistinctionGroup(String id, String name, ValidityStatus status, List<DeliberationDistinction> deliberationDistinctions) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.deliberationDistinctions = deliberationDistinctions;
    }

    public DeliberationDistinctionGroup() {
    }

    public static DeliberationDistinctionGroupBuilder builder() {
        return new DeliberationDistinctionGroupBuilder();
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

    public ValidityStatus getStatus() {
        return this.status;
    }

    public List<DeliberationDistinction> getDeliberationDistinctions() {
        return this.deliberationDistinctions;
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

    public void setStatus(ValidityStatus status) {
        this.status = status;
    }

    public void setDeliberationDistinctions(List<DeliberationDistinction> deliberationDistinctions) {
        this.deliberationDistinctions = deliberationDistinctions;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof DeliberationDistinctionGroup)) return false;
        final DeliberationDistinctionGroup other = (DeliberationDistinctionGroup) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$createdAt = this.getCreatedAt();
        final Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        final Object this$status = this.getStatus();
        final Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final Object this$deliberationDistinctions = this.getDeliberationDistinctions();
        final Object other$deliberationDistinctions = other.getDeliberationDistinctions();
        if (this$deliberationDistinctions == null ? other$deliberationDistinctions != null : !this$deliberationDistinctions.equals(other$deliberationDistinctions))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof DeliberationDistinctionGroup;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        final Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final Object $deliberationDistinctions = this.getDeliberationDistinctions();
        result = result * PRIME + ($deliberationDistinctions == null ? 43 : $deliberationDistinctions.hashCode());
        return result;
    }

    public String toString() {
        return "DeliberationDistinctionGroup(id=" + this.getId() + ", name=" + this.getName() + ", createdAt=" + this.getCreatedAt() + ", status=" + this.getStatus() + ", deliberationDistinctions=" + this.getDeliberationDistinctions() + ")";
    }

    public static class DeliberationDistinctionGroupBuilder {
        private String id;
        private String name;
        private LocalDateTime createdAt;
        private ValidityStatus status;
        private List<DeliberationDistinction> deliberationDistinctions;

        DeliberationDistinctionGroupBuilder() {
        }

        public DeliberationDistinctionGroupBuilder id(String id) {
            this.id = id;
            return this;
        }

        public DeliberationDistinctionGroupBuilder name(String name) {
            this.name = name;
            return this;
        }

        public DeliberationDistinctionGroupBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public DeliberationDistinctionGroupBuilder status(ValidityStatus status) {
            this.status = status;
            return this;
        }

        public DeliberationDistinctionGroupBuilder deliberationDistinctions(List<DeliberationDistinction> deliberationDistinctions) {
            this.deliberationDistinctions = deliberationDistinctions;
            return this;
        }

        public DeliberationDistinctionGroup build() {
            return new DeliberationDistinctionGroup(this.id, this.name,this.status, this.deliberationDistinctions);
        }

        public String toString() {
            return "DeliberationDistinctionGroup.DeliberationDistinctionGroupBuilder(id=" + this.id + ", name=" + this.name + ", createdAt=" + this.createdAt + ", status=" + this.status + ", deliberationDistinctions=" + this.deliberationDistinctions + ")";
        }
    }
}
