package rw.ac.ilpd.academicservice.model.sql;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import rw.ac.ilpd.sharedlibrary.enums.EmploymentStatus;
import rw.ac.ilpd.sharedlibrary.enums.EngagementType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "aca_lecturers")
@EntityListeners(AuditingEntityListener.class)
public class Lecturer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID userId;

    @Enumerated(EnumType.STRING)
    private EngagementType engagementType;
    @Enumerated(EnumType.STRING)
    private EmploymentStatus activeStatus;
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;
    private LocalDate endDate;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Lecturer(UUID id, UUID userId, EngagementType engagementType, EmploymentStatus activeStatus, LocalDateTime createdAt, LocalDate endDate, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.engagementType = engagementType;
        this.activeStatus = activeStatus;
        this.createdAt = createdAt;
        this.endDate = endDate;
        this.updatedAt = updatedAt;
    }

    public Lecturer() {
    }

    public static LecturerBuilder builder() {
        return new LecturerBuilder();
    }

    public UUID getId() {
        return this.id;
    }

    public UUID getUserId() {
        return this.userId;
    }

    public EngagementType getEngagementType() {
        return this.engagementType;
    }

    public EmploymentStatus getActiveStatus() {
        return this.activeStatus;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setEngagementType(EngagementType engagementType) {
        this.engagementType = engagementType;
    }

    public void setActiveStatus(EmploymentStatus activeStatus) {
        this.activeStatus = activeStatus;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String toString() {
        return "Lecturer(id=" + this.getId() + ", userId=" + this.getUserId() + ", engagementType=" + this.getEngagementType() + ", activeStatus=" + this.getActiveStatus() + ", createdAt=" + this.getCreatedAt() + ", endDate=" + this.getEndDate() + ", updatedAt=" + this.getUpdatedAt() + ")";
    }

    public static class LecturerBuilder {
        private UUID id;
        private UUID userId;
        private EngagementType engagementType;
        private EmploymentStatus activeStatus;
        private LocalDateTime createdAt;
        private LocalDate endDate;
        private LocalDateTime updatedAt;

        LecturerBuilder() {
        }

        public LecturerBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public LecturerBuilder userId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public LecturerBuilder engagementType(EngagementType engagementType) {
            this.engagementType = engagementType;
            return this;
        }

        public LecturerBuilder activeStatus(EmploymentStatus activeStatus) {
            this.activeStatus = activeStatus;
            return this;
        }

        public LecturerBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public LecturerBuilder endDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public LecturerBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Lecturer build() {
            return new Lecturer(this.id, this.userId, this.engagementType, this.activeStatus, this.createdAt, this.endDate, this.updatedAt);
        }

        public String toString() {
            return "Lecturer.LecturerBuilder(id=" + this.id + ", userId=" + this.userId + ", engagementType=" + this.engagementType + ", activeStatus=" + this.activeStatus + ", createdAt=" + this.createdAt + ", endDate=" + this.endDate + ", updatedAt=" + this.updatedAt + ")";
        }
    }
}
