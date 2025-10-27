package rw.ac.ilpd.academicservice.model.sql;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import rw.ac.ilpd.sharedlibrary.enums.ResultStatus;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * This entity stores details of a student's grade in a particular
 * module for a given intake and whether or not the student has passed or failed said module.
 */

@Entity
@Table(name = "aca_student_grade_curriculum_modules")
@EntityListeners(AuditingEntityListener.class)
public class StudentGradeCurriculumModule {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    private CurriculumModule curriculumModule;

    @ManyToOne(fetch = FetchType.LAZY)
    private Intake intake;

    private Double grade;

    @Enumerated(EnumType.STRING)
    private ResultStatus resultStatus;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public StudentGradeCurriculumModule(UUID id, Student student, CurriculumModule curriculumModule, Intake intake, Double grade, ResultStatus resultStatus) {
        this.id = id;
        this.student = student;
        this.curriculumModule = curriculumModule;
        this.intake = intake;
        this.grade = grade;
        this.resultStatus = resultStatus;
    }

    public StudentGradeCurriculumModule() {
    }

    public static StudentGradeCurriculumModuleBuilder builder() {
        return new StudentGradeCurriculumModuleBuilder();
    }

    public UUID getId() {
        return this.id;
    }

    public Student getStudent() {
        return this.student;
    }

    public CurriculumModule getCurriculumModule() {
        return this.curriculumModule;
    }

    public Intake getIntake() {
        return this.intake;
    }

    public Double getGrade() {
        return this.grade;
    }

    public ResultStatus getResultStatus() {
        return this.resultStatus;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setCurriculumModule(CurriculumModule curriculumModule) {
        this.curriculumModule = curriculumModule;
    }

    public void setIntake(Intake intake) {
        this.intake = intake;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public void setResultStatus(ResultStatus resultStatus) {
        this.resultStatus = resultStatus;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof StudentGradeCurriculumModule)) return false;
        final StudentGradeCurriculumModule other = (StudentGradeCurriculumModule) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$student = this.getStudent();
        final Object other$student = other.getStudent();
        if (this$student == null ? other$student != null : !this$student.equals(other$student)) return false;
        final Object this$curriculumModule = this.getCurriculumModule();
        final Object other$curriculumModule = other.getCurriculumModule();
        if (this$curriculumModule == null ? other$curriculumModule != null : !this$curriculumModule.equals(other$curriculumModule))
            return false;
        final Object this$intake = this.getIntake();
        final Object other$intake = other.getIntake();
        if (this$intake == null ? other$intake != null : !this$intake.equals(other$intake)) return false;
        final Object this$grade = this.getGrade();
        final Object other$grade = other.getGrade();
        if (this$grade == null ? other$grade != null : !this$grade.equals(other$grade)) return false;
        final Object this$resultStatus = this.getResultStatus();
        final Object other$resultStatus = other.getResultStatus();
        if (this$resultStatus == null ? other$resultStatus != null : !this$resultStatus.equals(other$resultStatus))
            return false;
        final Object this$createdAt = this.getCreatedAt();
        final Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        final Object this$updatedAt = this.getUpdatedAt();
        final Object other$updatedAt = other.getUpdatedAt();
        if (this$updatedAt == null ? other$updatedAt != null : !this$updatedAt.equals(other$updatedAt)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof StudentGradeCurriculumModule;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $student = this.getStudent();
        result = result * PRIME + ($student == null ? 43 : $student.hashCode());
        final Object $curriculumModule = this.getCurriculumModule();
        result = result * PRIME + ($curriculumModule == null ? 43 : $curriculumModule.hashCode());
        final Object $intake = this.getIntake();
        result = result * PRIME + ($intake == null ? 43 : $intake.hashCode());
        final Object $grade = this.getGrade();
        result = result * PRIME + ($grade == null ? 43 : $grade.hashCode());
        final Object $resultStatus = this.getResultStatus();
        result = result * PRIME + ($resultStatus == null ? 43 : $resultStatus.hashCode());
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        final Object $updatedAt = this.getUpdatedAt();
        result = result * PRIME + ($updatedAt == null ? 43 : $updatedAt.hashCode());
        return result;
    }

    public String toString() {
        return "StudentGradeCurriculumModule(id=" + this.getId() + ", student=" + this.getStudent() + ", curriculumModule=" + this.getCurriculumModule() + ", intake=" + this.getIntake() + ", grade=" + this.getGrade() + ", resultStatus=" + this.getResultStatus() + ", createdAt=" + this.getCreatedAt() + ", updatedAt=" + this.getUpdatedAt() + ")";
    }

    public static class StudentGradeCurriculumModuleBuilder {
        private UUID id;
        private Student student;
        private CurriculumModule curriculumModule;
        private Intake intake;
        private Double grade;
        private ResultStatus resultStatus;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        StudentGradeCurriculumModuleBuilder() {
        }

        public StudentGradeCurriculumModuleBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public StudentGradeCurriculumModuleBuilder student(Student student) {
            this.student = student;
            return this;
        }

        public StudentGradeCurriculumModuleBuilder curriculumModule(CurriculumModule curriculumModule) {
            this.curriculumModule = curriculumModule;
            return this;
        }

        public StudentGradeCurriculumModuleBuilder intake(Intake intake) {
            this.intake = intake;
            return this;
        }

        public StudentGradeCurriculumModuleBuilder grade(Double grade) {
            this.grade = grade;
            return this;
        }

        public StudentGradeCurriculumModuleBuilder resultStatus(ResultStatus resultStatus) {
            this.resultStatus = resultStatus;
            return this;
        }

        public StudentGradeCurriculumModuleBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public StudentGradeCurriculumModuleBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public StudentGradeCurriculumModule build() {
            return new StudentGradeCurriculumModule(this.id, this.student, this.curriculumModule, this.intake, this.grade, this.resultStatus);
        }

        public String toString() {
            return "StudentGradeCurriculumModule.StudentGradeCurriculumModuleBuilder(id=" + this.id + ", student=" + this.student + ", curriculumModule=" + this.curriculumModule + ", intake=" + this.intake + ", grade=" + this.grade + ", resultStatus=" + this.resultStatus + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + ")";
        }
    }
}
