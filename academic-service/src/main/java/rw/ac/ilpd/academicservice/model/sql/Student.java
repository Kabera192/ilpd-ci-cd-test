package rw.ac.ilpd.academicservice.model.sql;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * This table stores details about students of ILPD in its diploma programs.
 * This does not store short course students
 */

@Entity
@Table(name = "aca_students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID userId;

    private String registrationNumber;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Student(UUID id, UUID userId, String registrationNumber, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.registrationNumber = registrationNumber;
        this.createdAt = createdAt;
    }

    public Student() {
    }

    public static StudentBuilder builder() {
        return new StudentBuilder();
    }

    public UUID getId() {
        return this.id;
    }

    public UUID getUserId() {
        return this.userId;
    }

    public String getRegistrationNumber() {
        return this.registrationNumber;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Student)) return false;
        final Student other = (Student) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$userId = this.getUserId();
        final Object other$userId = other.getUserId();
        if (this$userId == null ? other$userId != null : !this$userId.equals(other$userId)) return false;
        final Object this$registrationNumber = this.getRegistrationNumber();
        final Object other$registrationNumber = other.getRegistrationNumber();
        if (this$registrationNumber == null ? other$registrationNumber != null : !this$registrationNumber.equals(other$registrationNumber))
            return false;
        final Object this$createdAt = this.getCreatedAt();
        final Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Student;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $userId = this.getUserId();
        result = result * PRIME + ($userId == null ? 43 : $userId.hashCode());
        final Object $registrationNumber = this.getRegistrationNumber();
        result = result * PRIME + ($registrationNumber == null ? 43 : $registrationNumber.hashCode());
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        return result;
    }

    public String toString() {
        return "Student(id=" + this.getId() + ", userId=" + this.getUserId() + ", registrationNumber=" + this.getRegistrationNumber() + ", createdAt=" + this.getCreatedAt() + ")";
    }

    public static class StudentBuilder {
        private UUID id;
        private UUID userId;
        private String registrationNumber;
        private LocalDateTime createdAt;

        StudentBuilder() {
        }

        public StudentBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public StudentBuilder userId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public StudentBuilder registrationNumber(String registrationNumber) {
            this.registrationNumber = registrationNumber;
            return this;
        }

        public StudentBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Student build() {
            return new Student(this.id, this.userId, this.registrationNumber, this.createdAt);
        }

        public String toString() {
            return "Student.StudentBuilder(id=" + this.id + ", userId=" + this.userId + ", registrationNumber=" + this.registrationNumber + ", createdAt=" + this.createdAt + ")";
        }
    }
}
