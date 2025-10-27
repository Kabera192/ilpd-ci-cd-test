package rw.ac.ilpd.academicservice.model.nosql.embedding;

import org.springframework.data.annotation.Id;
import rw.ac.ilpd.sharedlibrary.enums.ValidityStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class DeliberationRuleGroupCurriculum {
    @Id
    private String id;
    private UUID curriculumId;
    private LocalDateTime createdAt;
    ValidityStatus status;

    public DeliberationRuleGroupCurriculum(String id, UUID curriculumId, LocalDateTime createdAt, ValidityStatus status) {
        this.id = id;
        this.curriculumId = curriculumId;
        this.createdAt = createdAt;
        this.status = status;
    }

    public DeliberationRuleGroupCurriculum() {
    }

    public static DeliberationRuleGroupCurriculumBuilder builder() {
        return new DeliberationRuleGroupCurriculumBuilder();
    }

    public String getId() {
        return this.id;
    }

    public UUID getCurriculumId() {
        return this.curriculumId;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public ValidityStatus getStatus() {
        return this.status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCurriculumId(UUID curriculumId) {
        this.curriculumId = curriculumId;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setStatus(ValidityStatus status) {
        this.status = status;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof DeliberationRuleGroupCurriculum)) return false;
        final DeliberationRuleGroupCurriculum other = (DeliberationRuleGroupCurriculum) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$curriculumId = this.getCurriculumId();
        final Object other$curriculumId = other.getCurriculumId();
        if (this$curriculumId == null ? other$curriculumId != null : !this$curriculumId.equals(other$curriculumId))
            return false;
        final Object this$createdAt = this.getCreatedAt();
        final Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        final Object this$status = this.getStatus();
        final Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof DeliberationRuleGroupCurriculum;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $curriculumId = this.getCurriculumId();
        result = result * PRIME + ($curriculumId == null ? 43 : $curriculumId.hashCode());
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        final Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        return result;
    }

    public String toString() {
        return "DeliberationRuleGroupCurriculum(id=" + this.getId() + ", curriculumId=" + this.getCurriculumId() + ", createdAt=" + this.getCreatedAt() + ", status=" + this.getStatus() + ")";
    }

    public static class DeliberationRuleGroupCurriculumBuilder {
        private String id;
        private UUID curriculumId;
        private LocalDateTime createdAt;
        private ValidityStatus status;

        DeliberationRuleGroupCurriculumBuilder() {
        }

        public DeliberationRuleGroupCurriculumBuilder id(String id) {
            this.id = id;
            return this;
        }

        public DeliberationRuleGroupCurriculumBuilder curriculumId(UUID curriculumId) {
            this.curriculumId = curriculumId;
            return this;
        }

        public DeliberationRuleGroupCurriculumBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public DeliberationRuleGroupCurriculumBuilder status(ValidityStatus status) {
            this.status = status;
            return this;
        }

        public DeliberationRuleGroupCurriculum build() {
            return new DeliberationRuleGroupCurriculum(this.id, this.curriculumId, this.createdAt, this.status);
        }

        public String toString() {
            return "DeliberationRuleGroupCurriculum.DeliberationRuleGroupCurriculumBuilder(id=" + this.id + ", curriculumId=" + this.curriculumId + ", createdAt=" + this.createdAt + ", status=" + this.status + ")";
        }
    }
}
