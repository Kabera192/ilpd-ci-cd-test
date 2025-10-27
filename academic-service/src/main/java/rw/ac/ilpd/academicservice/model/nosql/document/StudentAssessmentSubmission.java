package rw.ac.ilpd.academicservice.model.nosql.document;


import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * This entity represents a single submission of a student for a particular assessment
 * assigned to them in a component. One record shows when the student submitted the assessment,
 * the grade of the assssment and any comments if any.
 */
@Document(collection = "aca_student_assessment_submissions")
public class StudentAssessmentSubmission
{
    @Id
    private String id;

    // References the assessment for which the student is making
    // a submission
    private String moduleComponentAssessmentId;

    // References the student or group that made the submission.
    private String subjectId;

    @CreatedDate
    private LocalDateTime submittedAt;

    // References the student who submitted the assessment
    // in individual assessments this is similar to the subjectId field
    // above. However, in group assessments one student can submit and this
    // should reflect for all other group members.
    @CreatedBy
    private UUID submittedBy;

    private boolean isLate;

    private LocalDateTime lateTime;

    // References the lecturer who grades the assessment.
    private UUID correctedBy;

    private Boolean isCurrent = true;

    private String comment;

    private Double grade;

    private List<String> documents;

    public StudentAssessmentSubmission(String id, String moduleComponentAssessmentId, String subjectId, LocalDateTime submittedAt, UUID submittedBy, boolean isLate, LocalDateTime lateTime, UUID correctedBy, Boolean isCurrent, String comment, Double grade, List<String> documents)
    {
        this.id = id;
        this.moduleComponentAssessmentId = moduleComponentAssessmentId;
        this.subjectId = subjectId;
        this.submittedAt = submittedAt;
        this.submittedBy = submittedBy;
        this.isLate = isLate;
        this.lateTime = lateTime;
        this.correctedBy = correctedBy;
        this.isCurrent = isCurrent;
        this.comment = comment;
        this.grade = grade;
        this.documents = documents;
    }

    public StudentAssessmentSubmission()
    {
    }

    public static StudentAssessmentSubmissionBuilder builder()
    {
        return new StudentAssessmentSubmissionBuilder();
    }

    public String getId()
    {
        return this.id;
    }

    public String getModuleComponentAssessmentId()
    {
        return this.moduleComponentAssessmentId;
    }

    public String getSubjectId()
    {
        return this.subjectId;
    }

    public LocalDateTime getSubmittedAt()
    {
        return this.submittedAt;
    }

    public UUID getSubmittedBy()
    {
        return this.submittedBy;
    }

    public boolean isLate()
    {
        return this.isLate;
    }

    public LocalDateTime getLateTime()
    {
        return this.lateTime;
    }

    public UUID getCorrectedBy()
    {
        return this.correctedBy;
    }

    public Boolean getIsCurrent()
    {
        return this.isCurrent;
    }

    public String getComment()
    {
        return this.comment;
    }

    public Double getGrade()
    {
        return this.grade;
    }

    public List<String> getDocuments()
    {
        return this.documents;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public void setModuleComponentAssessmentId(String moduleComponentAssessmentId)
    {
        this.moduleComponentAssessmentId = moduleComponentAssessmentId;
    }

    public void setSubjectId(String subjectId)
    {
        this.subjectId = subjectId;
    }

    public void setSubmittedAt(LocalDateTime submittedAt)
    {
        this.submittedAt = submittedAt;
    }

    public void setSubmittedBy(UUID submittedBy)
    {
        this.submittedBy = submittedBy;
    }

    public void setLate(boolean isLate)
    {
        this.isLate = isLate;
    }

    public void setLateTime(LocalDateTime lateTime)
    {
        this.lateTime = lateTime;
    }

    public void setCorrectedBy(UUID correctedBy)
    {
        this.correctedBy = correctedBy;
    }

    public void setIsCurrent(Boolean isCurrent)
    {
        this.isCurrent = isCurrent;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public void setGrade(Double grade)
    {
        this.grade = grade;
    }

    public void setDocuments(List<String> documents)
    {
        this.documents = documents;
    }

    public String toString()
    {
        return "StudentAssessmentSubmission(id=" + this.getId() + ", moduleComponentAssessmentId=" + this.getModuleComponentAssessmentId() + ", subjectId=" + this.getSubjectId() + ", submittedAt=" + this.getSubmittedAt() + ", submittedBy=" + this.getSubmittedBy() + ", isLate=" + this.isLate() + ", lateTime=" + this.getLateTime() + ", correctedBy=" + this.getCorrectedBy() + ", isCurrent=" + this.getIsCurrent() + ", comment=" + this.getComment() + ", grade=" + this.getGrade() + ", documents=" + this.getDocuments() + ")";
    }

    public static class StudentAssessmentSubmissionBuilder
    {
        private String id;
        private String moduleComponentAssessmentId;
        private String subjectId;
        private LocalDateTime submittedAt;
        private UUID submittedBy;
        private boolean isLate;
        private LocalDateTime lateTime;
        private UUID correctedBy;
        private Boolean isCurrent;
        private String comment;
        private Double grade;
        private List<String> documents;

        StudentAssessmentSubmissionBuilder()
        {
        }

        public StudentAssessmentSubmissionBuilder id(String id)
        {
            this.id = id;
            return this;
        }

        public StudentAssessmentSubmissionBuilder moduleComponentAssessmentId(String moduleComponentAssessmentId)
        {
            this.moduleComponentAssessmentId = moduleComponentAssessmentId;
            return this;
        }

        public StudentAssessmentSubmissionBuilder subjectId(String subjectId)
        {
            this.subjectId = subjectId;
            return this;
        }

        public StudentAssessmentSubmissionBuilder submittedAt(LocalDateTime submittedAt)
        {
            this.submittedAt = submittedAt;
            return this;
        }

        public StudentAssessmentSubmissionBuilder submittedBy(UUID submittedBy)
        {
            this.submittedBy = submittedBy;
            return this;
        }

        public StudentAssessmentSubmissionBuilder isLate(boolean isLate)
        {
            this.isLate = isLate;
            return this;
        }

        public StudentAssessmentSubmissionBuilder lateTime(LocalDateTime lateTime)
        {
            this.lateTime = lateTime;
            return this;
        }

        public StudentAssessmentSubmissionBuilder correctedBy(UUID correctedBy)
        {
            this.correctedBy = correctedBy;
            return this;
        }

        public StudentAssessmentSubmissionBuilder isCurrent(Boolean isCurrent)
        {
            this.isCurrent = isCurrent;
            return this;
        }

        public StudentAssessmentSubmissionBuilder comment(String comment)
        {
            this.comment = comment;
            return this;
        }

        public StudentAssessmentSubmissionBuilder grade(Double grade)
        {
            this.grade = grade;
            return this;
        }

        public StudentAssessmentSubmissionBuilder documents(List<String> documents)
        {
            this.documents = documents;
            return this;
        }

        public StudentAssessmentSubmission build()
        {
            return new StudentAssessmentSubmission(this.id, this.moduleComponentAssessmentId, this.subjectId, this.submittedAt, this.submittedBy, this.isLate, this.lateTime, this.correctedBy, this.isCurrent, this.comment, this.grade, this.documents);
        }

        public String toString()
        {
            return "StudentAssessmentSubmission.StudentAssessmentSubmissionBuilder(id=" + this.id + ", moduleComponentAssessmentId=" + this.moduleComponentAssessmentId + ", subjectId=" + this.subjectId + ", submittedAt=" + this.submittedAt + ", submittedBy=" + this.submittedBy + ", isLate=" + this.isLate + ", lateTime=" + this.lateTime + ", correctedBy=" + this.correctedBy + ", isCurrent=" + this.isCurrent + ", comment=" + this.comment + ", grade=" + this.grade + ", documents=" + this.documents + ")";
        }
    }
}
