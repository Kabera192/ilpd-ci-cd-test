package rw.ac.ilpd.academicservice.model.sql;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "aca_study_mode_sessions")
public class StudyModeSession {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private StudyMode studyMode;

    private DayOfWeek startingDay;

    private DayOfWeek endingDay;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private AssessmentGroup assessmentGroup;


    public StudyModeSession(UUID id, String name, StudyMode studyMode, DayOfWeek startingDay, DayOfWeek endingDay, LocalDateTime createdAt, AssessmentGroup assessmentGroup) {
        this.id = id;
        this.name = name;
        this.studyMode = studyMode;
        this.startingDay = startingDay;
        this.endingDay = endingDay;
        this.createdAt = createdAt;
        this.assessmentGroup = assessmentGroup;
    }

    public StudyModeSession() {
    }

    public static StudyModeSessionBuilder builder() {
        return new StudyModeSessionBuilder();
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public StudyMode getStudyMode() {
        return this.studyMode;
    }

    public DayOfWeek getStartingDay() {
        return this.startingDay;
    }

    public DayOfWeek getEndingDay() {
        return this.endingDay;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public AssessmentGroup getAssessmentGroup() {
        return this.assessmentGroup;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStudyMode(StudyMode studyMode) {
        this.studyMode = studyMode;
    }

    public void setStartingDay(DayOfWeek startingDay) {
        this.startingDay = startingDay;
    }

    public void setEndingDay(DayOfWeek endingDay) {
        this.endingDay = endingDay;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setAssessmentGroup(AssessmentGroup assessmentGroup) {
        this.assessmentGroup = assessmentGroup;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof StudyModeSession)) return false;
        final StudyModeSession other = (StudyModeSession) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$studyMode = this.getStudyMode();
        final Object other$studyMode = other.getStudyMode();
        if (this$studyMode == null ? other$studyMode != null : !this$studyMode.equals(other$studyMode)) return false;
        final Object this$startingDay = this.getStartingDay();
        final Object other$startingDay = other.getStartingDay();
        if (this$startingDay == null ? other$startingDay != null : !this$startingDay.equals(other$startingDay))
            return false;
        final Object this$endingDay = this.getEndingDay();
        final Object other$endingDay = other.getEndingDay();
        if (this$endingDay == null ? other$endingDay != null : !this$endingDay.equals(other$endingDay)) return false;
        final Object this$createdAt = this.getCreatedAt();
        final Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        final Object this$assessmentGroup = this.getAssessmentGroup();
        final Object other$assessmentGroup = other.getAssessmentGroup();
        if (this$assessmentGroup == null ? other$assessmentGroup != null : !this$assessmentGroup.equals(other$assessmentGroup))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof StudyModeSession;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $studyMode = this.getStudyMode();
        result = result * PRIME + ($studyMode == null ? 43 : $studyMode.hashCode());
        final Object $startingDay = this.getStartingDay();
        result = result * PRIME + ($startingDay == null ? 43 : $startingDay.hashCode());
        final Object $endingDay = this.getEndingDay();
        result = result * PRIME + ($endingDay == null ? 43 : $endingDay.hashCode());
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        final Object $assessmentGroup = this.getAssessmentGroup();
        result = result * PRIME + ($assessmentGroup == null ? 43 : $assessmentGroup.hashCode());
        return result;
    }

    public String toString() {
        return "StudyModeSession(id=" + this.getId() + ", name=" + this.getName() + ", studyMode=" + this.getStudyMode() + ", startingDay=" + this.getStartingDay() + ", endingDay=" + this.getEndingDay() + ", createdAt=" + this.getCreatedAt() + ", assessmentGroup=" + this.getAssessmentGroup() + ")";
    }

    public static class StudyModeSessionBuilder {
        private UUID id;
        private String name;
        private StudyMode studyMode;
        private DayOfWeek startingDay;
        private DayOfWeek endingDay;
        private LocalDateTime createdAt;
        private AssessmentGroup assessmentGroup;

        StudyModeSessionBuilder() {
        }

        public StudyModeSessionBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public StudyModeSessionBuilder name(String name) {
            this.name = name;
            return this;
        }

        public StudyModeSessionBuilder studyMode(StudyMode studyMode) {
            this.studyMode = studyMode;
            return this;
        }

        public StudyModeSessionBuilder startingDay(DayOfWeek startingDay) {
            this.startingDay = startingDay;
            return this;
        }

        public StudyModeSessionBuilder endingDay(DayOfWeek endingDay) {
            this.endingDay = endingDay;
            return this;
        }

        public StudyModeSessionBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public StudyModeSessionBuilder assessmentGroup(AssessmentGroup assessmentGroup) {
            this.assessmentGroup = assessmentGroup;
            return this;
        }

        public StudyModeSession build() {
            return new StudyModeSession(this.id, this.name, this.studyMode, this.startingDay, this.endingDay, this.createdAt, this.assessmentGroup);
        }

        public String toString() {
            return "StudyModeSession.StudyModeSessionBuilder(id=" + this.id + ", name=" + this.name + ", studyMode=" + this.studyMode + ", startingDay=" + this.startingDay + ", endingDay=" + this.endingDay + ", createdAt=" + this.createdAt + ", assessmentGroup=" + this.assessmentGroup + ")";
        }
    }
}
