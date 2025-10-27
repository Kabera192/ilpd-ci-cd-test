package rw.ac.ilpd.academicservice.model.sql;

import jakarta.persistence.*;
import rw.ac.ilpd.sharedlibrary.enums.ModuleRetakeResitType;

import java.util.UUID;

/**
 * This entity is used to keep track of students who have module retakes and/or
 * module resits. Module retakes happen when a student fails a module and is
 * asked to re-do it from scratch while resits happen when a student does not
 * meet the pass mark and is given a second chance to re-do the exam.
 */

@Entity
@Table(name = "aca_intake_student_retake_resit_curriculum_modules")
public class IntakeStudentRetakeResitCurriculumModule {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    private Intake intake;

    @ManyToOne(fetch = FetchType.LAZY)
    private CurriculumModule curriculumModule;

    @Enumerated(EnumType.STRING)
    private ModuleRetakeResitType moduleRetakeResitType;

    public IntakeStudentRetakeResitCurriculumModule(UUID id, Student student, Intake intake, CurriculumModule curriculumModule, ModuleRetakeResitType moduleRetakeResitType) {
        this.id = id;
        this.student = student;
        this.intake = intake;
        this.curriculumModule = curriculumModule;
        this.moduleRetakeResitType = moduleRetakeResitType;
    }

    public IntakeStudentRetakeResitCurriculumModule() {
    }

    public static IntakeStudentRetakeResitCurriculumModuleBuilder builder() {
        return new IntakeStudentRetakeResitCurriculumModuleBuilder();
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

    public CurriculumModule getCurriculumModule() {
        return this.curriculumModule;
    }

    public ModuleRetakeResitType getModuleRetakeResitType() {
        return this.moduleRetakeResitType;
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

    public void setCurriculumModule(CurriculumModule curriculumModule) {
        this.curriculumModule = curriculumModule;
    }

    public void setModuleRetakeResitType(ModuleRetakeResitType moduleRetakeResitType) {
        this.moduleRetakeResitType = moduleRetakeResitType;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof IntakeStudentRetakeResitCurriculumModule)) return false;
        final IntakeStudentRetakeResitCurriculumModule other = (IntakeStudentRetakeResitCurriculumModule) o;
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
        final Object this$curriculumModule = this.getCurriculumModule();
        final Object other$curriculumModule = other.getCurriculumModule();
        if (this$curriculumModule == null ? other$curriculumModule != null : !this$curriculumModule.equals(other$curriculumModule))
            return false;
        final Object this$moduleRetakeResitType = this.getModuleRetakeResitType();
        final Object other$moduleRetakeResitType = other.getModuleRetakeResitType();
        if (this$moduleRetakeResitType == null ? other$moduleRetakeResitType != null : !this$moduleRetakeResitType.equals(other$moduleRetakeResitType))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof IntakeStudentRetakeResitCurriculumModule;
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
        final Object $curriculumModule = this.getCurriculumModule();
        result = result * PRIME + ($curriculumModule == null ? 43 : $curriculumModule.hashCode());
        final Object $moduleRetakeResitType = this.getModuleRetakeResitType();
        result = result * PRIME + ($moduleRetakeResitType == null ? 43 : $moduleRetakeResitType.hashCode());
        return result;
    }

    public String toString() {
        return "IntakeStudentRetakeResitCurriculumModule(id=" + this.getId() + ", student=" + this.getStudent() + ", intake=" + this.getIntake() + ", curriculumModule=" + this.getCurriculumModule() + ", moduleRetakeResitType=" + this.getModuleRetakeResitType() + ")";
    }

    public static class IntakeStudentRetakeResitCurriculumModuleBuilder {
        private UUID id;
        private Student student;
        private Intake intake;
        private CurriculumModule curriculumModule;
        private ModuleRetakeResitType moduleRetakeResitType;

        IntakeStudentRetakeResitCurriculumModuleBuilder() {
        }

        public IntakeStudentRetakeResitCurriculumModuleBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public IntakeStudentRetakeResitCurriculumModuleBuilder student(Student student) {
            this.student = student;
            return this;
        }

        public IntakeStudentRetakeResitCurriculumModuleBuilder intake(Intake intake) {
            this.intake = intake;
            return this;
        }

        public IntakeStudentRetakeResitCurriculumModuleBuilder curriculumModule(CurriculumModule curriculumModule) {
            this.curriculumModule = curriculumModule;
            return this;
        }

        public IntakeStudentRetakeResitCurriculumModuleBuilder moduleRetakeResitType(ModuleRetakeResitType moduleRetakeResitType) {
            this.moduleRetakeResitType = moduleRetakeResitType;
            return this;
        }

        public IntakeStudentRetakeResitCurriculumModule build() {
            return new IntakeStudentRetakeResitCurriculumModule(this.id, this.student, this.intake, this.curriculumModule, this.moduleRetakeResitType);
        }

        public String toString() {
            return "IntakeStudentRetakeResitCurriculumModule.IntakeStudentRetakeResitCurriculumModuleBuilder(id=" + this.id + ", student=" + this.student + ", intake=" + this.intake + ", curriculumModule=" + this.curriculumModule + ", moduleRetakeResitType=" + this.moduleRetakeResitType + ")";
        }
    }
}
