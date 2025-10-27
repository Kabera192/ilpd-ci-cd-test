package rw.ac.ilpd.academicservice.model.nosql.document;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import rw.ac.ilpd.academicservice.model.nosql.embedding.DeliberationRuleGroupCurriculum;
import rw.ac.ilpd.academicservice.model.nosql.embedding.DeliberationRulesThreshold;
import rw.ac.ilpd.sharedlibrary.enums.ValidityStatus;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "aca_deliberation_rule_groups")
public class DeliberationRuleGroup {
    @Id
    private String id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ValidityStatus status;

    @CreatedDate
    private LocalDateTime createdAt;

    List<DeliberationRuleGroupCurriculum> curriculumList;
    List<DeliberationRulesThreshold> thresholdList;

    public DeliberationRuleGroup(String id, String name, ValidityStatus status, LocalDateTime createdAt, List<DeliberationRuleGroupCurriculum> curriculumList, List<DeliberationRulesThreshold> thresholdList) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.createdAt = createdAt;
        this.curriculumList = curriculumList;
        this.thresholdList = thresholdList;
    }

    public DeliberationRuleGroup() {
    }

    public static DeliberationRuleGroupBuilder builder() {
        return new DeliberationRuleGroupBuilder();
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public ValidityStatus getStatus() {
        return this.status;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public List<DeliberationRuleGroupCurriculum> getCurriculumList() {
        return this.curriculumList;
    }

    public List<DeliberationRulesThreshold> getThresholdList() {
        return this.thresholdList;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(ValidityStatus status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setCurriculumList(List<DeliberationRuleGroupCurriculum> curriculumList) {
        this.curriculumList = curriculumList;
    }

    public void setThresholdList(List<DeliberationRulesThreshold> thresholdList) {
        this.thresholdList = thresholdList;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof DeliberationRuleGroup)) return false;
        final DeliberationRuleGroup other = (DeliberationRuleGroup) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$status = this.getStatus();
        final Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final Object this$createdAt = this.getCreatedAt();
        final Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        final Object this$curriculumList = this.getCurriculumList();
        final Object other$curriculumList = other.getCurriculumList();
        if (this$curriculumList == null ? other$curriculumList != null : !this$curriculumList.equals(other$curriculumList))
            return false;
        final Object this$thresholdList = this.getThresholdList();
        final Object other$thresholdList = other.getThresholdList();
        if (this$thresholdList == null ? other$thresholdList != null : !this$thresholdList.equals(other$thresholdList))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof DeliberationRuleGroup;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        final Object $curriculumList = this.getCurriculumList();
        result = result * PRIME + ($curriculumList == null ? 43 : $curriculumList.hashCode());
        final Object $thresholdList = this.getThresholdList();
        result = result * PRIME + ($thresholdList == null ? 43 : $thresholdList.hashCode());
        return result;
    }

    public String toString() {
        return "DeliberationRuleGroup(id=" + this.getId() + ", name=" + this.getName() + ", status=" + this.getStatus() + ", createdAt=" + this.getCreatedAt() + ", curriculumList=" + this.getCurriculumList() + ", thresholdList=" + this.getThresholdList() + ")";
    }

    public static class DeliberationRuleGroupBuilder {
        private String id;
        private String name;
        private ValidityStatus status;
        private LocalDateTime createdAt;
        private List<DeliberationRuleGroupCurriculum> curriculumList;
        private List<DeliberationRulesThreshold> thresholdList;

        DeliberationRuleGroupBuilder() {
        }

        public DeliberationRuleGroupBuilder id(String id) {
            this.id = id;
            return this;
        }

        public DeliberationRuleGroupBuilder name(String name) {
            this.name = name;
            return this;
        }

        public DeliberationRuleGroupBuilder status(ValidityStatus status) {
            this.status = status;
            return this;
        }

        public DeliberationRuleGroupBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public DeliberationRuleGroupBuilder curriculumList(List<DeliberationRuleGroupCurriculum> curriculumList) {
            this.curriculumList = curriculumList;
            return this;
        }

        public DeliberationRuleGroupBuilder thresholdList(List<DeliberationRulesThreshold> thresholdList) {
            this.thresholdList = thresholdList;
            return this;
        }

        public DeliberationRuleGroup build() {
            return new DeliberationRuleGroup(this.id, this.name, this.status, this.createdAt, this.curriculumList, this.thresholdList);
        }

        public String toString() {
            return "DeliberationRuleGroup.DeliberationRuleGroupBuilder(id=" + this.id + ", name=" + this.name + ", status=" + this.status + ", createdAt=" + this.createdAt + ", curriculumList=" + this.curriculumList + ", thresholdList=" + this.thresholdList + ")";
        }
    }
}
