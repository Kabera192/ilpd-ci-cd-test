package rw.ac.ilpd.academicservice.model.sql;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "aca_short_course_topic_materials")
public class ShortCourseTopicMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private CourseMaterial courseMaterial;

    @ManyToOne(fetch = FetchType.LAZY)
    private ShortCourseTopicLecturer topicLecturer;

    public ShortCourseTopicMaterial(UUID id, CourseMaterial courseMaterial, ShortCourseTopicLecturer topicLecturer) {
        this.id = id;
        this.courseMaterial = courseMaterial;
        this.topicLecturer = topicLecturer;
    }

    public ShortCourseTopicMaterial() {
    }

    public static ShortCourseTopicMaterialBuilder builder() {
        return new ShortCourseTopicMaterialBuilder();
    }

    public UUID getId() {
        return this.id;
    }

    public CourseMaterial getCourseMaterial() {
        return this.courseMaterial;
    }

    public ShortCourseTopicLecturer getTopicLecturer() {
        return this.topicLecturer;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setCourseMaterial(CourseMaterial courseMaterial) {
        this.courseMaterial = courseMaterial;
    }

    public void setTopicLecturer(ShortCourseTopicLecturer topicLecturer) {
        this.topicLecturer = topicLecturer;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ShortCourseTopicMaterial)) return false;
        final ShortCourseTopicMaterial other = (ShortCourseTopicMaterial) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$courseMaterial = this.getCourseMaterial();
        final Object other$courseMaterial = other.getCourseMaterial();
        if (this$courseMaterial == null ? other$courseMaterial != null : !this$courseMaterial.equals(other$courseMaterial))
            return false;
        final Object this$topicLecturer = this.getTopicLecturer();
        final Object other$topicLecturer = other.getTopicLecturer();
        if (this$topicLecturer == null ? other$topicLecturer != null : !this$topicLecturer.equals(other$topicLecturer))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ShortCourseTopicMaterial;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $courseMaterial = this.getCourseMaterial();
        result = result * PRIME + ($courseMaterial == null ? 43 : $courseMaterial.hashCode());
        final Object $topicLecturer = this.getTopicLecturer();
        result = result * PRIME + ($topicLecturer == null ? 43 : $topicLecturer.hashCode());
        return result;
    }

    public String toString() {
        return "ShortCourseTopicMaterial(id=" + this.getId() + ", courseMaterial=" + this.getCourseMaterial() + ", topicLecturer=" + this.getTopicLecturer() + ")";
    }

    public static class ShortCourseTopicMaterialBuilder {
        private UUID id;
        private CourseMaterial courseMaterial;
        private ShortCourseTopicLecturer topicLecturer;

        ShortCourseTopicMaterialBuilder() {
        }

        public ShortCourseTopicMaterialBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public ShortCourseTopicMaterialBuilder courseMaterial(CourseMaterial courseMaterial) {
            this.courseMaterial = courseMaterial;
            return this;
        }

        public ShortCourseTopicMaterialBuilder topicLecturer(ShortCourseTopicLecturer topicLecturer) {
            this.topicLecturer = topicLecturer;
            return this;
        }

        public ShortCourseTopicMaterial build() {
            return new ShortCourseTopicMaterial(this.id, this.courseMaterial, this.topicLecturer);
        }

        public String toString() {
            return "ShortCourseTopicMaterial.ShortCourseTopicMaterialBuilder(id=" + this.id + ", courseMaterial=" + this.courseMaterial + ", topicLecturer=" + this.topicLecturer + ")";
        }
    }
}
