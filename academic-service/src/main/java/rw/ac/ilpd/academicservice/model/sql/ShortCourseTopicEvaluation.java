package rw.ac.ilpd.academicservice.model.sql;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "aca_short_course_topic_evaluations")
public class ShortCourseTopicEvaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private ShortCourseTopic topic;

    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;

    private UUID quizzId;

    private BigDecimal grade;

    public ShortCourseTopicEvaluation(UUID id, ShortCourseTopic topic, Student student, UUID quizzId, BigDecimal grade) {
        this.id = id;
        this.topic = topic;
        this.student = student;
        this.quizzId = quizzId;
        this.grade = grade;
    }

    public ShortCourseTopicEvaluation() {
    }

    public static ShortCourseTopicEvaluationBuilder builder() {
        return new ShortCourseTopicEvaluationBuilder();
    }

    public UUID getId() {
        return this.id;
    }

    public ShortCourseTopic getTopic() {
        return this.topic;
    }

    public Student getStudent() {
        return this.student;
    }

    public UUID getQuizzId() {
        return this.quizzId;
    }

    public BigDecimal getGrade() {
        return this.grade;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setTopic(ShortCourseTopic topic) {
        this.topic = topic;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setQuizzId(UUID quizzId) {
        this.quizzId = quizzId;
    }

    public void setGrade(BigDecimal grade) {
        this.grade = grade;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ShortCourseTopicEvaluation)) return false;
        final ShortCourseTopicEvaluation other = (ShortCourseTopicEvaluation) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$topic = this.getTopic();
        final Object other$topic = other.getTopic();
        if (this$topic == null ? other$topic != null : !this$topic.equals(other$topic)) return false;
        final Object this$student = this.getStudent();
        final Object other$student = other.getStudent();
        if (this$student == null ? other$student != null : !this$student.equals(other$student)) return false;
        final Object this$quizzId = this.getQuizzId();
        final Object other$quizzId = other.getQuizzId();
        if (this$quizzId == null ? other$quizzId != null : !this$quizzId.equals(other$quizzId)) return false;
        final Object this$grade = this.getGrade();
        final Object other$grade = other.getGrade();
        if (this$grade == null ? other$grade != null : !this$grade.equals(other$grade)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ShortCourseTopicEvaluation;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $topic = this.getTopic();
        result = result * PRIME + ($topic == null ? 43 : $topic.hashCode());
        final Object $student = this.getStudent();
        result = result * PRIME + ($student == null ? 43 : $student.hashCode());
        final Object $quizzId = this.getQuizzId();
        result = result * PRIME + ($quizzId == null ? 43 : $quizzId.hashCode());
        final Object $grade = this.getGrade();
        result = result * PRIME + ($grade == null ? 43 : $grade.hashCode());
        return result;
    }

    public String toString() {
        return "ShortCourseTopicEvaluation(id=" + this.getId() + ", topic=" + this.getTopic() + ", student=" + this.getStudent() + ", quizzId=" + this.getQuizzId() + ", grade=" + this.getGrade() + ")";
    }

    public static class ShortCourseTopicEvaluationBuilder {
        private UUID id;
        private ShortCourseTopic topic;
        private Student student;
        private UUID quizzId;
        private BigDecimal grade;

        ShortCourseTopicEvaluationBuilder() {
        }

        public ShortCourseTopicEvaluationBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public ShortCourseTopicEvaluationBuilder topic(ShortCourseTopic topic) {
            this.topic = topic;
            return this;
        }

        public ShortCourseTopicEvaluationBuilder student(Student student) {
            this.student = student;
            return this;
        }

        public ShortCourseTopicEvaluationBuilder quizzId(UUID quizzId) {
            this.quizzId = quizzId;
            return this;
        }

        public ShortCourseTopicEvaluationBuilder grade(BigDecimal grade) {
            this.grade = grade;
            return this;
        }

        public ShortCourseTopicEvaluation build() {
            return new ShortCourseTopicEvaluation(this.id, this.topic, this.student, this.quizzId, this.grade);
        }

        public String toString() {
            return "ShortCourseTopicEvaluation.ShortCourseTopicEvaluationBuilder(id=" + this.id + ", topic=" + this.topic + ", student=" + this.student + ", quizzId=" + this.quizzId + ", grade=" + this.grade + ")";
        }
    }
}
