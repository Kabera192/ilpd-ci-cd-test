package rw.ac.ilpd.academicservice.model.nosql.embedding;

import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

public class AttendanceMissing {
    private String id = UUID.randomUUID().toString();

    private UUID userId;

    @CreatedDate
    private LocalDateTime createdAt;


    public AttendanceMissing(String id, UUID userId, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public AttendanceMissing() {
    }

    public static AttendanceMissingBuilder builder() {
        return new AttendanceMissingBuilder();
    }

    public String getId() {
        return this.id;
    }

    public UUID getUserId() {
        return this.userId;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static class AttendanceMissingBuilder {
        private String id;
        private UUID userId;
        private LocalDateTime createdAt;

        AttendanceMissingBuilder() {
        }

        public AttendanceMissingBuilder id(String id) {
            this.id = id;
            return this;
        }

        public AttendanceMissingBuilder userId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public AttendanceMissingBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public AttendanceMissing build() {
            return new AttendanceMissing(this.id, this.userId, this.createdAt);
        }

        public String toString() {
            return "AttendanceMissing.AttendanceMissingBuilder(id=" + this.id + ", userId=" + this.userId + ", createdAt=" + this.createdAt + ")";
        }
    }
}
