package rw.ac.ilpd.academicservice.model.sql;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import rw.ac.ilpd.sharedlibrary.enums.ShortCouseStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "aca_short_course_students")
public class ShortCourseStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Intake intake;

    private UUID userId;

    @Enumerated(EnumType.STRING)
    private ShortCouseStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public ShortCourseStudent(UUID id, Intake intake, UUID userId, ShortCouseStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.intake = intake;
        this.userId = userId;
        this.status = status;
        this.createdAt = createdAt;
    }

    public ShortCourseStudent() {
    }

    public static ShortCourseStudentBuilder builder() {
        return new ShortCourseStudentBuilder();
    }

    public UUID getId() {
        return this.id;
    }

    public Intake getIntake() {
        return this.intake;
    }

    public UUID getUserId() {
        return this.userId;
    }

    public ShortCouseStatus getStatus() {
        return this.status;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setIntake(Intake intake) {
        this.intake = intake;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setStatus(ShortCouseStatus status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ShortCourseStudent)) return false;
        final ShortCourseStudent other = (ShortCourseStudent) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$intake = this.getIntake();
        final Object other$intake = other.getIntake();
        if (this$intake == null ? other$intake != null : !this$intake.equals(other$intake)) return false;
        final Object this$userId = this.getUserId();
        final Object other$userId = other.getUserId();
        if (this$userId == null ? other$userId != null : !this$userId.equals(other$userId)) return false;
        final Object this$status = this.getStatus();
        final Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final Object this$createdAt = this.getCreatedAt();
        final Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ShortCourseStudent;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $intake = this.getIntake();
        result = result * PRIME + ($intake == null ? 43 : $intake.hashCode());
        final Object $userId = this.getUserId();
        result = result * PRIME + ($userId == null ? 43 : $userId.hashCode());
        final Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        return result;
    }

    public String toString() {
        return "ShortCourseStudent(id=" + this.getId() + ", intake=" + this.getIntake() + ", userId=" + this.getUserId() + ", status=" + this.getStatus() + ", createdAt=" + this.getCreatedAt() + ")";
    }

    public static class ShortCourseStudentBuilder {
        private UUID id;
        private Intake intake;
        private UUID userId;
        private ShortCouseStatus status;
        private LocalDateTime createdAt;

        ShortCourseStudentBuilder() {
        }

        public ShortCourseStudentBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public ShortCourseStudentBuilder intake(Intake intake) {
            this.intake = intake;
            return this;
        }

        public ShortCourseStudentBuilder userId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public ShortCourseStudentBuilder status(ShortCouseStatus status) {
            this.status = status;
            return this;
        }

        public ShortCourseStudentBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ShortCourseStudent build() {
            return new ShortCourseStudent(this.id, this.intake, this.userId, this.status, this.createdAt);
        }

        public String toString() {
            return "ShortCourseStudent.ShortCourseStudentBuilder(id=" + this.id + ", intake=" + this.intake + ", userId=" + this.userId + ", status=" + this.status + ", createdAt=" + this.createdAt + ")";
        }
    }
}
