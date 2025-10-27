package rw.ac.ilpd.academicservice.model.sql;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import rw.ac.ilpd.sharedlibrary.enums.IntakeStatus;
import rw.ac.ilpd.sharedlibrary.enums.IntakeStudentStatus;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * This entity maps a student to a particular intake. This is a separate table because a
 * student can belong to multiple intakes and at the same time, an intake can have multiple students.
 */

@Entity
@Table(name = "aca_intake_students")
public class IntakeStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    private Intake intake;

    private boolean isEnforced;

    private String enforcementComment;

    @Enumerated(EnumType.STRING)
    private IntakeStudentStatus intakeStudentStatus;

    private boolean isRetaking;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public IntakeStudent(UUID id, Student student, Intake intake, boolean isEnforced,
                         String enforcementComment, IntakeStudentStatus intakeStudentStatus, boolean isRetaking,
                         LocalDateTime createdAt) {
        this.id = id;
        this.student = student;
        this.intake = intake;
        this.isEnforced = isEnforced;
        this.enforcementComment = enforcementComment;
        this.intakeStudentStatus = intakeStudentStatus;
        this.isRetaking = isRetaking;
        this.createdAt = createdAt;
    }

    public IntakeStudent() {
    }

    public static IntakeStudentBuilder builder() {
        return new IntakeStudentBuilder();
    }

    public UUID getId() {
        return this.id;
    }

    public Student getStudent() {
        return this.student;
    }

    public Intake getIntake() {
        return this.intake;
    }

    public boolean isEnforced() {
        return this.isEnforced;
    }

    public String getEnforcementComment() {
        return this.enforcementComment;
    }

    public IntakeStudentStatus getIntakeStudentStatus() {
        return this.intakeStudentStatus;
    }

    public boolean isRetaking() {
        return this.isRetaking;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setIntake(Intake intake) {
        this.intake = intake;
    }

    public void setEnforced(boolean isEnforced) {
        this.isEnforced = isEnforced;
    }

    public void setEnforcementComment(String enforcementComment) {
        this.enforcementComment = enforcementComment;
    }

    public void setIntakeStatus(IntakeStudentStatus intakeStudentStatus) {
        this.intakeStudentStatus = intakeStudentStatus;
    }

    public void setRetaking(boolean isRetaking) {
        this.isRetaking = isRetaking;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof IntakeStudent)) return false;
        final IntakeStudent other = (IntakeStudent) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$student = this.getStudent();
        final Object other$student = other.getStudent();
        if (this$student == null ? other$student != null : !this$student.equals(other$student)) return false;
        final Object this$intake = this.getIntake();
        final Object other$intake = other.getIntake();
        if (this$intake == null ? other$intake != null : !this$intake.equals(other$intake)) return false;
        if (this.isEnforced() != other.isEnforced()) return false;
        final Object this$enforcementComment = this.getEnforcementComment();
        final Object other$enforcementComment = other.getEnforcementComment();
        if (this$enforcementComment == null ? other$enforcementComment != null : !this$enforcementComment.equals(other$enforcementComment))
            return false;
        final Object this$intakeStudentStatus = this.getIntakeStudentStatus();
        final Object other$intakeStudentStatus = other.getIntakeStudentStatus();
        if (this$intakeStudentStatus == null ? other$intakeStudentStatus != null : !this$intakeStudentStatus.equals(other$intakeStudentStatus))
            return false;
        if (this.isRetaking() != other.isRetaking()) return false;
        final Object this$createdAt = this.getCreatedAt();
        final Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof IntakeStudent;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $student = this.getStudent();
        result = result * PRIME + ($student == null ? 43 : $student.hashCode());
        final Object $intake = this.getIntake();
        result = result * PRIME + ($intake == null ? 43 : $intake.hashCode());
        result = result * PRIME + (this.isEnforced() ? 79 : 97);
        final Object $enforcementComment = this.getEnforcementComment();
        result = result * PRIME + ($enforcementComment == null ? 43 : $enforcementComment.hashCode());
        final Object $intakeStudentStatus = this.getIntakeStudentStatus();
        result = result * PRIME + ($intakeStudentStatus == null ? 43 : $intakeStudentStatus.hashCode());
        result = result * PRIME + (this.isRetaking() ? 79 : 97);
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        return result;
    }

    public String toString() {
        return "IntakeStudent(id=" + this.getId() + ", student=" + this.getStudent() + ", intake=" + this.getIntake() + ", isEnforced=" + this.isEnforced() + ", enforcementComment=" + this.getEnforcementComment() + ", intakeStudentStatus=" + this.getIntakeStudentStatus() + ", isRetaking=" + this.isRetaking() + ", createdAt=" + this.getCreatedAt() + ")";
    }

    public static class IntakeStudentBuilder {
        private UUID id;
        private Student student;
        private Intake intake;
        private boolean isEnforced;
        private String enforcementComment;
        private IntakeStudentStatus intakeStudentStatus;
        private boolean isRetaking;
        private LocalDateTime createdAt;

        IntakeStudentBuilder() {
        }

        public IntakeStudentBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public IntakeStudentBuilder student(Student student) {
            this.student = student;
            return this;
        }

        public IntakeStudentBuilder intake(Intake intake) {
            this.intake = intake;
            return this;
        }

        public IntakeStudentBuilder isEnforced(boolean isEnforced) {
            this.isEnforced = isEnforced;
            return this;
        }

        public IntakeStudentBuilder enforcementComment(String enforcementComment) {
            this.enforcementComment = enforcementComment;
            return this;
        }

        public IntakeStudentBuilder intakeStudentStatus(IntakeStudentStatus intakeStudentStatus) {
            this.intakeStudentStatus = intakeStudentStatus;
            return this;
        }

        public IntakeStudentBuilder isRetaking(boolean isRetaking) {
            this.isRetaking = isRetaking;
            return this;
        }

        public IntakeStudentBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public IntakeStudent build() {
            return new IntakeStudent(this.id, this.student, this.intake, this.isEnforced, this.enforcementComment, this.intakeStudentStatus, this.isRetaking, this.createdAt);
        }

        public String toString() {
            return "IntakeStudent.IntakeStudentBuilder(id=" + this.id + ", student=" + this.student + ", intake=" + this.intake + ", isEnforced=" + this.isEnforced + ", enforcementComment=" + this.enforcementComment + ", intakeStudentStatus=" + this.intakeStudentStatus + ", isRetaking=" + this.isRetaking + ", createdAt=" + this.createdAt + ")";
        }
    }
}
