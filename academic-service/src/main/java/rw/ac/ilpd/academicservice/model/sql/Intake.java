package rw.ac.ilpd.academicservice.model.sql;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import rw.ac.ilpd.sharedlibrary.enums.IntakeStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "aca_intakes")
@EntityListeners(AuditingEntityListener.class)
public class Intake {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @ManyToOne
    private Program program;

    @ManyToOne
    private StudyModeSession studyModeSession;

    private String locationId; // Campus

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private LocalDateTime applicationOpeningDate;

    private LocalDateTime applicationClosingDate;

    private LocalDateTime graduationDate;

    private Integer maxNumberOfStudents;

    @Enumerated(EnumType.STRING)
    private IntakeStatus status;

    @ManyToOne
    private Curriculum curriculum;

    private String deliberationRuleGroupId;

    @ManyToOne
    private InstitutionShortCourseSponsor institution;

    private String deliberationDistinctionGrpId;
    @CreatedBy
    private String createdBy;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Intake(UUID id, String name, Program program, StudyModeSession studyModeSession, String locationId, LocalDateTime startDate, LocalDateTime endDate, LocalDateTime applicationOpeningDate, LocalDateTime applicationClosingDate, LocalDateTime graduationDate, Integer maxNumberOfStudents, IntakeStatus status, Curriculum curriculum, String deliberationRuleGroupId, InstitutionShortCourseSponsor institution, String deliberationDistinctionGrpId, String createdBy, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.program = program;
        this.studyModeSession = studyModeSession;
        this.locationId = locationId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.applicationOpeningDate = applicationOpeningDate;
        this.applicationClosingDate = applicationClosingDate;
        this.graduationDate = graduationDate;
        this.maxNumberOfStudents = maxNumberOfStudents;
        this.status = status;
        this.curriculum = curriculum;
        this.deliberationRuleGroupId = deliberationRuleGroupId;
        this.institution = institution;
        this.deliberationDistinctionGrpId = deliberationDistinctionGrpId;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Intake() {
    }

    public static IntakeBuilder builder() {
        return new IntakeBuilder();
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Program getProgram() {
        return this.program;
    }

    public StudyModeSession getStudyModeSession() {
        return this.studyModeSession;
    }

    public String getLocationId() {
        return this.locationId;
    }

    public LocalDateTime getStartDate() {
        return this.startDate;
    }

    public LocalDateTime getEndDate() {
        return this.endDate;
    }

    public LocalDateTime getApplicationOpeningDate() {
        return this.applicationOpeningDate;
    }

    public LocalDateTime getApplicationClosingDate() {
        return this.applicationClosingDate;
    }

    public LocalDateTime getGraduationDate() {
        return this.graduationDate;
    }

    public Integer getMaxNumberOfStudents() {
        return this.maxNumberOfStudents;
    }

    public IntakeStatus getStatus() {
        return this.status;
    }

    public Curriculum getCurriculum() {
        return this.curriculum;
    }

    public String getDeliberationRuleGroupId() {
        return this.deliberationRuleGroupId;
    }

    public InstitutionShortCourseSponsor getInstitution() {
        return this.institution;
    }

    public String getDeliberationDistinctionGrpId() {
        return this.deliberationDistinctionGrpId;
    }

    public String getCreatedBy() {
        return this.createdBy;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public void setStudyModeSession(StudyModeSession studyModeSession) {
        this.studyModeSession = studyModeSession;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setApplicationOpeningDate(LocalDateTime applicationOpeningDate) {
        this.applicationOpeningDate = applicationOpeningDate;
    }

    public void setApplicationClosingDate(LocalDateTime applicationClosingDate) {
        this.applicationClosingDate = applicationClosingDate;
    }

    public void setGraduationDate(LocalDateTime graduationDate) {
        this.graduationDate = graduationDate;
    }

    public void setMaxNumberOfStudents(Integer maxNumberOfStudents) {
        this.maxNumberOfStudents = maxNumberOfStudents;
    }

    public void setStatus(IntakeStatus status) {
        this.status = status;
    }

    public void setCurriculum(Curriculum curriculum) {
        this.curriculum = curriculum;
    }

    public void setDeliberationRuleGroupId(String deliberationRuleGroupId) {
        this.deliberationRuleGroupId = deliberationRuleGroupId;
    }

    public void setInstitution(InstitutionShortCourseSponsor institution) {
        this.institution = institution;
    }

    public void setDeliberationDistinctionGrpId(String deliberationDistinctionGrpId) {
        this.deliberationDistinctionGrpId = deliberationDistinctionGrpId;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static class IntakeBuilder {
        private UUID id;
        private String name;
        private Program program;
        private StudyModeSession studyModeSession;
        private String locationId;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private LocalDateTime applicationOpeningDate;
        private LocalDateTime applicationClosingDate;
        private LocalDateTime graduationDate;
        private Integer maxNumberOfStudents;
        private IntakeStatus status;
        private Curriculum curriculum;
        private String deliberationRuleGroupId;
        private InstitutionShortCourseSponsor institution;
        private String deliberationDistinctionGrpId;
        private String createdBy;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        IntakeBuilder() {
        }

        public IntakeBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public IntakeBuilder name(String name) {
            this.name = name;
            return this;
        }

        public IntakeBuilder program(Program program) {
            this.program = program;
            return this;
        }

        public IntakeBuilder studyModeSession(StudyModeSession studyModeSession) {
            this.studyModeSession = studyModeSession;
            return this;
        }

        public IntakeBuilder locationId(String locationId) {
            this.locationId = locationId;
            return this;
        }

        public IntakeBuilder startDate(LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }

        public IntakeBuilder endDate(LocalDateTime endDate) {
            this.endDate = endDate;
            return this;
        }

        public IntakeBuilder applicationOpeningDate(LocalDateTime applicationOpeningDate) {
            this.applicationOpeningDate = applicationOpeningDate;
            return this;
        }

        public IntakeBuilder applicationClosingDate(LocalDateTime applicationClosingDate) {
            this.applicationClosingDate = applicationClosingDate;
            return this;
        }

        public IntakeBuilder graduationDate(LocalDateTime graduationDate) {
            this.graduationDate = graduationDate;
            return this;
        }

        public IntakeBuilder maxNumberOfStudents(Integer maxNumberOfStudents) {
            this.maxNumberOfStudents = maxNumberOfStudents;
            return this;
        }

        public IntakeBuilder status(IntakeStatus status) {
            this.status = status;
            return this;
        }

        public IntakeBuilder curriculum(Curriculum curriculum) {
            this.curriculum = curriculum;
            return this;
        }

        public IntakeBuilder deliberationRuleGroupId(String deliberationRuleGroupId) {
            this.deliberationRuleGroupId = deliberationRuleGroupId;
            return this;
        }

        public IntakeBuilder institution(InstitutionShortCourseSponsor institution) {
            this.institution = institution;
            return this;
        }

        public IntakeBuilder deliberationDistinctionGrpId(String deliberationDistinctionGrpId) {
            this.deliberationDistinctionGrpId = deliberationDistinctionGrpId;
            return this;
        }

        public IntakeBuilder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public IntakeBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public IntakeBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Intake build() {
            return new Intake(this.id, this.name, this.program, this.studyModeSession, this.locationId, this.startDate, this.endDate, this.applicationOpeningDate, this.applicationClosingDate, this.graduationDate, this.maxNumberOfStudents, this.status, this.curriculum, this.deliberationRuleGroupId, this.institution, this.deliberationDistinctionGrpId, this.createdBy, this.createdAt, this.updatedAt);
        }

        public String toString() {
            return "Intake.IntakeBuilder(id=" + this.id + ", name=" + this.name + ", program=" + this.program + ", studyModeSession=" + this.studyModeSession + ", locationId=" + this.locationId + ", startDate=" + this.startDate + ", endDate=" + this.endDate + ", applicationOpeningDate=" + this.applicationOpeningDate + ", applicationClosingDate=" + this.applicationClosingDate + ", graduationDate=" + this.graduationDate + ", maxNumberOfStudents=" + this.maxNumberOfStudents + ", status=" + this.status + ", curriculum=" + this.curriculum + ", deliberationRuleGroupId=" + this.deliberationRuleGroupId + ", institution=" + this.institution + ", deliberationDistinctionGrpId=" + this.deliberationDistinctionGrpId + ", createdBy=" + this.createdBy + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + ")";
        }
    }
}
