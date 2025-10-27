package rw.ac.ilpd.academicservice.model.sql;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "aca_short_course_topic_lecturers")
public class ShortCourseTopicLecturer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private ShortCourseTopic shortCourseTopic;

    @ManyToOne(fetch = FetchType.LAZY)
    private Lecturer lecturer;

    private UUID roomId;

    @CreatedDate
    private LocalDateTime createdAt;

    public ShortCourseTopicLecturer(UUID id, ShortCourseTopic shortCourseTopic, Lecturer lecturer, UUID roomId, LocalDateTime createdAt) {
        this.id = id;
        this.shortCourseTopic = shortCourseTopic;
        this.lecturer = lecturer;
        this.roomId = roomId;
        this.createdAt = createdAt;
    }

    public ShortCourseTopicLecturer() {
    }

    public static ShortCourseTopicLecturerBuilder builder() {
        return new ShortCourseTopicLecturerBuilder();
    }

    public UUID getId() {
        return this.id;
    }

    public ShortCourseTopic getShortCourseTopic() {
        return this.shortCourseTopic;
    }

    public Lecturer getLecturer() {
        return this.lecturer;
    }

    public UUID getRoomId() {
        return this.roomId;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setShortCourseTopic(ShortCourseTopic shortCourseTopic) {
        this.shortCourseTopic = shortCourseTopic;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public void setRoomId(UUID roomId) {
        this.roomId = roomId;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ShortCourseTopicLecturer)) return false;
        final ShortCourseTopicLecturer other = (ShortCourseTopicLecturer) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$shortCourseTopic = this.getShortCourseTopic();
        final Object other$shortCourseTopic = other.getShortCourseTopic();
        if (this$shortCourseTopic == null ? other$shortCourseTopic != null : !this$shortCourseTopic.equals(other$shortCourseTopic))
            return false;
        final Object this$lecturer = this.getLecturer();
        final Object other$lecturer = other.getLecturer();
        if (this$lecturer == null ? other$lecturer != null : !this$lecturer.equals(other$lecturer)) return false;
        final Object this$roomId = this.getRoomId();
        final Object other$roomId = other.getRoomId();
        if (this$roomId == null ? other$roomId != null : !this$roomId.equals(other$roomId)) return false;
        final Object this$createdAt = this.getCreatedAt();
        final Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ShortCourseTopicLecturer;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $shortCourseTopic = this.getShortCourseTopic();
        result = result * PRIME + ($shortCourseTopic == null ? 43 : $shortCourseTopic.hashCode());
        final Object $lecturer = this.getLecturer();
        result = result * PRIME + ($lecturer == null ? 43 : $lecturer.hashCode());
        final Object $roomId = this.getRoomId();
        result = result * PRIME + ($roomId == null ? 43 : $roomId.hashCode());
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        return result;
    }

    public String toString() {
        return "ShortCourseTopicLecturer(id=" + this.getId() + ", shortCourseTopic=" + this.getShortCourseTopic() + ", lecturer=" + this.getLecturer() + ", roomId=" + this.getRoomId() + ", createdAt=" + this.getCreatedAt() + ")";
    }

    public static class ShortCourseTopicLecturerBuilder {
        private UUID id;
        private ShortCourseTopic shortCourseTopic;
        private Lecturer lecturer;
        private UUID roomId;
        private LocalDateTime createdAt;

        ShortCourseTopicLecturerBuilder() {
        }

        public ShortCourseTopicLecturerBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public ShortCourseTopicLecturerBuilder shortCourseTopic(ShortCourseTopic shortCourseTopic) {
            this.shortCourseTopic = shortCourseTopic;
            return this;
        }

        public ShortCourseTopicLecturerBuilder lecturer(Lecturer lecturer) {
            this.lecturer = lecturer;
            return this;
        }

        public ShortCourseTopicLecturerBuilder roomId(UUID roomId) {
            this.roomId = roomId;
            return this;
        }

        public ShortCourseTopicLecturerBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ShortCourseTopicLecturer build() {
            return new ShortCourseTopicLecturer(this.id, this.shortCourseTopic, this.lecturer, this.roomId, this.createdAt);
        }

        public String toString() {
            return "ShortCourseTopicLecturer.ShortCourseTopicLecturerBuilder(id=" + this.id + ", shortCourseTopic=" + this.shortCourseTopic + ", lecturer=" + this.lecturer + ", roomId=" + this.roomId + ", createdAt=" + this.createdAt + ")";
        }
    }
}
