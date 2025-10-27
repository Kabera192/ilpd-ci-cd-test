package rw.ac.ilpd.academicservice.model.sql;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "aca_short_course_topics")
public class ShortCourseTopic {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "intake_id")
    private Intake intake;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;


    private UUID createdById;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private boolean isDeleted = false; // Default to false
    @OneToMany(mappedBy = "shortCourseTopic",targetEntity = ShortCourseTopicLecturer.class,fetch = FetchType.LAZY)
    private List<ShortCourseTopicLecturer> shortCourseTopicLecturers;
    public ShortCourseTopic(UUID id, Intake intake, String name, String description, UUID createdById, LocalDateTime createdAt, LocalDateTime updatedAt, boolean isDeleted) {
        this.id = id;
        this.intake = intake;
        this.name = name;
        this.description = description;
        this.createdById = createdById;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDeleted = isDeleted;
    }

    public ShortCourseTopic() {
    }

    public static ShortCourseTopicBuilder builder() {
        return new ShortCourseTopicBuilder();
    }
    public List<ShortCourseTopicLecturer> getShortCourseTopicLecturers() {
        return shortCourseTopicLecturers;
    }

    public void setShortCourseTopicLecturers(List<ShortCourseTopicLecturer> shortCourseTopicLecturers) {
        this.shortCourseTopicLecturers = shortCourseTopicLecturers;
    }
    public UUID getId() {
        return this.id;
    }

    public Intake getIntake() {
        return this.intake;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public UUID getCreatedById() {
        return this.createdById;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public boolean isDeleted() {
        return this.isDeleted;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setIntake(Intake intake) {
        this.intake = intake;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedById(UUID createdById) {
        this.createdById = createdById;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ShortCourseTopic)) return false;
        final ShortCourseTopic other = (ShortCourseTopic) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$intake = this.getIntake();
        final Object other$intake = other.getIntake();
        if (this$intake == null ? other$intake != null : !this$intake.equals(other$intake)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description))
            return false;
        final Object this$createdById = this.getCreatedById();
        final Object other$createdById = other.getCreatedById();
        if (this$createdById == null ? other$createdById != null : !this$createdById.equals(other$createdById))
            return false;
        final Object this$createdAt = this.getCreatedAt();
        final Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        final Object this$updatedAt = this.getUpdatedAt();
        final Object other$updatedAt = other.getUpdatedAt();
        if (this$updatedAt == null ? other$updatedAt != null : !this$updatedAt.equals(other$updatedAt)) return false;
        if (this.isDeleted() != other.isDeleted()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ShortCourseTopic;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $intake = this.getIntake();
        result = result * PRIME + ($intake == null ? 43 : $intake.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        final Object $createdById = this.getCreatedById();
        result = result * PRIME + ($createdById == null ? 43 : $createdById.hashCode());
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        final Object $updatedAt = this.getUpdatedAt();
        result = result * PRIME + ($updatedAt == null ? 43 : $updatedAt.hashCode());
        result = result * PRIME + (this.isDeleted() ? 79 : 97);
        return result;
    }

    public String toString() {
        return "ShortCourseTopic(id=" + this.getId() + ", intake=" + this.getIntake() + ", name=" + this.getName() + ", description=" + this.getDescription() + ", createdById=" + this.getCreatedById() + ", createdAt=" + this.getCreatedAt() + ", updatedAt=" + this.getUpdatedAt() + ", isDeleted=" + this.isDeleted() + ")";
    }

    public static class ShortCourseTopicBuilder {
        private UUID id;
        private Intake intake;
        private String name;
        private String description;
        private UUID createdById;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private boolean isDeleted;

        ShortCourseTopicBuilder() {
        }

        public ShortCourseTopicBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public ShortCourseTopicBuilder intake(Intake intake) {
            this.intake = intake;
            return this;
        }

        public ShortCourseTopicBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ShortCourseTopicBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ShortCourseTopicBuilder createdById(UUID createdById) {
            this.createdById = createdById;
            return this;
        }

        public ShortCourseTopicBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ShortCourseTopicBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public ShortCourseTopicBuilder isDeleted(boolean isDeleted) {
            this.isDeleted = isDeleted;
            return this;
        }

        public ShortCourseTopic build() {
            return new ShortCourseTopic(this.id, this.intake, this.name, this.description, this.createdById, this.createdAt, this.updatedAt, this.isDeleted);
        }

        public String toString() {
            return "ShortCourseTopic.ShortCourseTopicBuilder(id=" + this.id + ", intake=" + this.intake + ", name=" + this.name + ", description=" + this.description + ", createdById=" + this.createdById + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + ", isDeleted=" + this.isDeleted + ")";
        }
    }
}
