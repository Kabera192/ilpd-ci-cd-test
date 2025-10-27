package rw.ac.ilpd.academicservice.model.sql;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "aca_short_course_topic_lecturer_evaluations")
public class ShortCourseTopicLecturerEvaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private ShortCourseTopicLecturer shortCourseTopicLecturer;

    private UUID priorEvaluationFormId;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public ShortCourseTopicLecturerEvaluation(UUID id, ShortCourseTopicLecturer shortCourseTopicLecturer, UUID priorEvaluationFormId) {
        this.id = id;
        this.shortCourseTopicLecturer = shortCourseTopicLecturer;
        this.priorEvaluationFormId = priorEvaluationFormId;
    }

    public ShortCourseTopicLecturerEvaluation() {
    }

    public static ShortCourseTopicLecturerEvaluationBuilder builder() {
        return new ShortCourseTopicLecturerEvaluationBuilder();
    }

    public UUID getId() {
        return this.id;
    }

    public ShortCourseTopicLecturer getShortCourseTopicLecturer() {
        return this.shortCourseTopicLecturer;
    }

    public UUID getPriorEvaluationFormId() {
        return this.priorEvaluationFormId;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setShortCourseTopicLecturer(ShortCourseTopicLecturer shortCourseTopicLecturer) {
        this.shortCourseTopicLecturer = shortCourseTopicLecturer;
    }

    public void setPriorEvaluationFormId(UUID priorEvaluationFormId) {
        this.priorEvaluationFormId = priorEvaluationFormId;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ShortCourseTopicLecturerEvaluation)) return false;
        final ShortCourseTopicLecturerEvaluation other = (ShortCourseTopicLecturerEvaluation) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$shortCourseTopicLecturer = this.getShortCourseTopicLecturer();
        final Object other$shortCourseTopicLecturer = other.getShortCourseTopicLecturer();
        if (this$shortCourseTopicLecturer == null ? other$shortCourseTopicLecturer != null : !this$shortCourseTopicLecturer.equals(other$shortCourseTopicLecturer))
            return false;
        final Object this$priorEvaluationFormId = this.getPriorEvaluationFormId();
        final Object other$priorEvaluationFormId = other.getPriorEvaluationFormId();
        if (this$priorEvaluationFormId == null ? other$priorEvaluationFormId != null : !this$priorEvaluationFormId.equals(other$priorEvaluationFormId))
            return false;
        final Object this$createdAt = this.getCreatedAt();
        final Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ShortCourseTopicLecturerEvaluation;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $shortCourseTopicLecturer = this.getShortCourseTopicLecturer();
        result = result * PRIME + ($shortCourseTopicLecturer == null ? 43 : $shortCourseTopicLecturer.hashCode());
        final Object $priorEvaluationFormId = this.getPriorEvaluationFormId();
        result = result * PRIME + ($priorEvaluationFormId == null ? 43 : $priorEvaluationFormId.hashCode());
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        return result;
    }

    public String toString() {
        return "ShortCourseTopicLecturerEvaluation(id=" + this.getId() + ", shortCourseTopicLecturer=" + this.getShortCourseTopicLecturer() + ", priorEvaluationFormId=" + this.getPriorEvaluationFormId() + ", createdAt=" + this.getCreatedAt() + ")";
    }

    public static class ShortCourseTopicLecturerEvaluationBuilder {
        private UUID id;
        private ShortCourseTopicLecturer shortCourseTopicLecturer;
        private UUID priorEvaluationFormId;
        private LocalDateTime createdAt;

        ShortCourseTopicLecturerEvaluationBuilder() {
        }

        public ShortCourseTopicLecturerEvaluationBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public ShortCourseTopicLecturerEvaluationBuilder shortCourseTopicLecturer(ShortCourseTopicLecturer shortCourseTopicLecturer) {
            this.shortCourseTopicLecturer = shortCourseTopicLecturer;
            return this;
        }

        public ShortCourseTopicLecturerEvaluationBuilder priorEvaluationFormId(UUID priorEvaluationFormId) {
            this.priorEvaluationFormId = priorEvaluationFormId;
            return this;
        }

        public ShortCourseTopicLecturerEvaluationBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ShortCourseTopicLecturerEvaluation build() {
            return new ShortCourseTopicLecturerEvaluation(this.id, this.shortCourseTopicLecturer, this.priorEvaluationFormId);
        }

        public String toString() {
            return "ShortCourseTopicLecturerEvaluation.ShortCourseTopicLecturerEvaluationBuilder(id=" + this.id + ", shortCourseTopicLecturer=" + this.shortCourseTopicLecturer + ", priorEvaluationFormId=" + this.priorEvaluationFormId + ")";
        }
    }
}
