package rw.ac.ilpd.academicservice.model.nosql.document;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import rw.ac.ilpd.academicservice.model.nosql.embedding.AssessmentAttachment;
import rw.ac.ilpd.sharedlibrary.enums.AssessmentMode;
import rw.ac.ilpd.sharedlibrary.enums.AssessmentStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * This entity defines an assessment in a particular component such as an assignment,
 * a quiz, a research exercise among others.
 */

@Document(collection = "aca_module_component_assessments")
public class ModuleComponentAssessment {
    @Id
    private String id;

    // references the component he assessment belongs to
    private UUID componentId;

    // this is populated when an assessment belongs to the entire module
    // and not a single component
    private UUID moduleId;

    // this references which intake an assessment belongs to
    private UUID intakeId;

    private String title;

    private String description;

    // References the user who created said assessment.
    private UUID postedById;

    private LocalDateTime dueDate;

    private boolean isLate;

    private LocalDateTime lateTime;

    // this references what type of assessment is being dealt with
    private UUID assessmentTypeId;

    private AssessmentStatus status;

    // This references the EvaluationForm table in cases of things
    // like quizes which have the same format as evaluation forms.
    private UUID evaluationFormId;

    private AssessmentMode mode;

    private double maxGrade;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private List<AssessmentAttachment> assessmentAttachments;

    public ModuleComponentAssessment(String id, UUID componentId, UUID moduleId, UUID intakeId, String title, String description, UUID postedById, LocalDateTime dueDate, UUID assessmentTypeId, AssessmentStatus status, UUID evaluationFormId, AssessmentMode mode, double maxGrade, LocalDateTime createdAt, LocalDateTime updatedAt, List<AssessmentAttachment> assessmentAttachments) {
        this.id = id;
        this.componentId = componentId;
        this.moduleId = moduleId;
        this.intakeId = intakeId;
        this.title = title;
        this.description = description;
        this.postedById = postedById;
        this.dueDate = dueDate;
        this.assessmentTypeId = assessmentTypeId;
        this.status = status;
        this.evaluationFormId = evaluationFormId;
        this.mode = mode;
        this.maxGrade = maxGrade;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.assessmentAttachments = assessmentAttachments;
    }

    public ModuleComponentAssessment() {
    }

    public static ModuleComponentAssessmentBuilder builder() {
        return new ModuleComponentAssessmentBuilder();
    }

    public String getId() {
        return this.id;
    }

    public UUID getComponentId() {
        return this.componentId;
    }

    public UUID getModuleId() {
        return this.moduleId;
    }

    public UUID getIntakeId() {
        return this.intakeId;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public UUID getPostedById() {
        return this.postedById;
    }

    public LocalDateTime getDueDate() {
        return this.dueDate;
    }

    public UUID getAssessmentTypeId() {
        return this.assessmentTypeId;
    }

    public AssessmentStatus getStatus() {
        return this.status;
    }

    public UUID getEvaluationFormId() {
        return this.evaluationFormId;
    }

    public AssessmentMode getMode() {
        return this.mode;
    }

    public double getMaxGrade() {
        return this.maxGrade;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public List<AssessmentAttachment> getAssessmentAttachments() {
        return this.assessmentAttachments;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setComponentId(UUID componentId) {
        this.componentId = componentId;
    }

    public void setModuleId(UUID moduleId) {
        this.moduleId = moduleId;
    }

    public void setIntakeId(UUID intakeId) {
        this.intakeId = intakeId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPostedById(UUID postedById) {
        this.postedById = postedById;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public void setAssessmentTypeId(UUID assessmentTypeId) {
        this.assessmentTypeId = assessmentTypeId;
    }

    public void setStatus(AssessmentStatus status) {
        this.status = status;
    }

    public void setEvaluationFormId(UUID evaluationFormId) {
        this.evaluationFormId = evaluationFormId;
    }

    public void setMode(AssessmentMode mode) {
        this.mode = mode;
    }

    public void setMaxGrade(double maxGrade) {
        this.maxGrade = maxGrade;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setAssessmentAttachments(List<AssessmentAttachment> assessmentAttachments) {
        this.assessmentAttachments = assessmentAttachments;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ModuleComponentAssessment)) return false;
        final ModuleComponentAssessment other = (ModuleComponentAssessment) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$componentId = this.getComponentId();
        final Object other$componentId = other.getComponentId();
        if (this$componentId == null ? other$componentId != null : !this$componentId.equals(other$componentId))
            return false;
        final Object this$moduleId = this.getModuleId();
        final Object other$moduleId = other.getModuleId();
        if (this$moduleId == null ? other$moduleId != null : !this$moduleId.equals(other$moduleId)) return false;
        final Object this$intakeId = this.getIntakeId();
        final Object other$intakeId = other.getIntakeId();
        if (this$intakeId == null ? other$intakeId != null : !this$intakeId.equals(other$intakeId)) return false;
        final Object this$title = this.getTitle();
        final Object other$title = other.getTitle();
        if (this$title == null ? other$title != null : !this$title.equals(other$title)) return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description))
            return false;
        final Object this$postedById = this.getPostedById();
        final Object other$postedById = other.getPostedById();
        if (this$postedById == null ? other$postedById != null : !this$postedById.equals(other$postedById))
            return false;
        final Object this$dueDate = this.getDueDate();
        final Object other$dueDate = other.getDueDate();
        if (this$dueDate == null ? other$dueDate != null : !this$dueDate.equals(other$dueDate)) return false;
        final Object this$assessmentTypeId = this.getAssessmentTypeId();
        final Object other$assessmentTypeId = other.getAssessmentTypeId();
        if (this$assessmentTypeId == null ? other$assessmentTypeId != null : !this$assessmentTypeId.equals(other$assessmentTypeId))
            return false;
        final Object this$status = this.getStatus();
        final Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final Object this$evaluationFormId = this.getEvaluationFormId();
        final Object other$evaluationFormId = other.getEvaluationFormId();
        if (this$evaluationFormId == null ? other$evaluationFormId != null : !this$evaluationFormId.equals(other$evaluationFormId))
            return false;
        final Object this$mode = this.getMode();
        final Object other$mode = other.getMode();
        if (this$mode == null ? other$mode != null : !this$mode.equals(other$mode)) return false;
        if (Double.compare(this.getMaxGrade(), other.getMaxGrade()) != 0) return false;
        final Object this$createdAt = this.getCreatedAt();
        final Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        final Object this$updatedAt = this.getUpdatedAt();
        final Object other$updatedAt = other.getUpdatedAt();
        if (this$updatedAt == null ? other$updatedAt != null : !this$updatedAt.equals(other$updatedAt)) return false;
        final Object this$assessmentAttachments = this.getAssessmentAttachments();
        final Object other$assessmentAttachments = other.getAssessmentAttachments();
        if (this$assessmentAttachments == null ? other$assessmentAttachments != null : !this$assessmentAttachments.equals(other$assessmentAttachments))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ModuleComponentAssessment;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $componentId = this.getComponentId();
        result = result * PRIME + ($componentId == null ? 43 : $componentId.hashCode());
        final Object $moduleId = this.getModuleId();
        result = result * PRIME + ($moduleId == null ? 43 : $moduleId.hashCode());
        final Object $intakeId = this.getIntakeId();
        result = result * PRIME + ($intakeId == null ? 43 : $intakeId.hashCode());
        final Object $title = this.getTitle();
        result = result * PRIME + ($title == null ? 43 : $title.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        final Object $postedById = this.getPostedById();
        result = result * PRIME + ($postedById == null ? 43 : $postedById.hashCode());
        final Object $dueDate = this.getDueDate();
        result = result * PRIME + ($dueDate == null ? 43 : $dueDate.hashCode());
        final Object $assessmentTypeId = this.getAssessmentTypeId();
        result = result * PRIME + ($assessmentTypeId == null ? 43 : $assessmentTypeId.hashCode());
        final Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final Object $evaluationFormId = this.getEvaluationFormId();
        result = result * PRIME + ($evaluationFormId == null ? 43 : $evaluationFormId.hashCode());
        final Object $mode = this.getMode();
        result = result * PRIME + ($mode == null ? 43 : $mode.hashCode());
        final long $maxGrade = Double.doubleToLongBits(this.getMaxGrade());
        result = result * PRIME + (int) ($maxGrade >>> 32 ^ $maxGrade);
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        final Object $updatedAt = this.getUpdatedAt();
        result = result * PRIME + ($updatedAt == null ? 43 : $updatedAt.hashCode());
        final Object $assessmentAttachments = this.getAssessmentAttachments();
        result = result * PRIME + ($assessmentAttachments == null ? 43 : $assessmentAttachments.hashCode());
        return result;
    }

    public String toString() {
        return "ModuleComponentAssessment(id=" + this.getId() + ", componentId=" + this.getComponentId() + ", moduleId=" + this.getModuleId() + ", intakeId=" + this.getIntakeId() + ", title=" + this.getTitle() + ", description=" + this.getDescription() + ", postedById=" + this.getPostedById() + ", dueDate=" + this.getDueDate() + ", assessmentTypeId=" + this.getAssessmentTypeId() + ", status=" + this.getStatus() + ", evaluationFormId=" + this.getEvaluationFormId() + ", mode=" + this.getMode() + ", maxGrade=" + this.getMaxGrade() + ", createdAt=" + this.getCreatedAt() + ", updatedAt=" + this.getUpdatedAt() + ", assessmentAttachments=" + this.getAssessmentAttachments() + ")";
    }

    public static class ModuleComponentAssessmentBuilder {
        private String id;
        private UUID componentId;
        private UUID moduleId;
        private UUID intakeId;
        private String title;
        private String description;
        private UUID postedById;
        private LocalDateTime dueDate;
        private UUID assessmentTypeId;
        private AssessmentStatus status;
        private UUID evaluationFormId;
        private AssessmentMode mode;
        private double maxGrade;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private List<AssessmentAttachment> assessmentAttachments;

        ModuleComponentAssessmentBuilder() {
        }

        public ModuleComponentAssessmentBuilder id(String id) {
            this.id = id;
            return this;
        }

        public ModuleComponentAssessmentBuilder componentId(UUID componentId) {
            this.componentId = componentId;
            return this;
        }

        public ModuleComponentAssessmentBuilder moduleId(UUID moduleId) {
            this.moduleId = moduleId;
            return this;
        }

        public ModuleComponentAssessmentBuilder intakeId(UUID intakeId) {
            this.intakeId = intakeId;
            return this;
        }

        public ModuleComponentAssessmentBuilder title(String title) {
            this.title = title;
            return this;
        }

        public ModuleComponentAssessmentBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ModuleComponentAssessmentBuilder postedById(UUID postedById) {
            this.postedById = postedById;
            return this;
        }

        public ModuleComponentAssessmentBuilder dueDate(LocalDateTime dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public ModuleComponentAssessmentBuilder assessmentTypeId(UUID assessmentTypeId) {
            this.assessmentTypeId = assessmentTypeId;
            return this;
        }

        public ModuleComponentAssessmentBuilder status(AssessmentStatus status) {
            this.status = status;
            return this;
        }

        public ModuleComponentAssessmentBuilder evaluationFormId(UUID evaluationFormId) {
            this.evaluationFormId = evaluationFormId;
            return this;
        }

        public ModuleComponentAssessmentBuilder mode(AssessmentMode mode) {
            this.mode = mode;
            return this;
        }

        public ModuleComponentAssessmentBuilder maxGrade(double maxGrade) {
            this.maxGrade = maxGrade;
            return this;
        }

        public ModuleComponentAssessmentBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ModuleComponentAssessmentBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public ModuleComponentAssessmentBuilder assessmentAttachments(List<AssessmentAttachment> assessmentAttachments) {
            this.assessmentAttachments = assessmentAttachments;
            return this;
        }

        public ModuleComponentAssessment build() {
            return new ModuleComponentAssessment(this.id, this.componentId, this.moduleId, this.intakeId, this.title, this.description, this.postedById, this.dueDate, this.assessmentTypeId, this.status, this.evaluationFormId, this.mode, this.maxGrade, this.createdAt, this.updatedAt, this.assessmentAttachments);
        }

        public String toString() {
            return "ModuleComponentAssessment.ModuleComponentAssessmentBuilder(id=" + this.id + ", componentId=" + this.componentId + ", moduleId=" + this.moduleId + ", intakeId=" + this.intakeId + ", title=" + this.title + ", description=" + this.description + ", postedById=" + this.postedById + ", dueDate=" + this.dueDate + ", assessmentTypeId=" + this.assessmentTypeId + ", status=" + this.status + ", evaluationFormId=" + this.evaluationFormId + ", mode=" + this.mode + ", maxGrade=" + this.maxGrade + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + ", assessmentAttachments=" + this.assessmentAttachments + ")";
        }
    }
}
