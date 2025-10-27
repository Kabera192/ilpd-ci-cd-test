package rw.ac.ilpd.reportingservice.model.nosql.document;

import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import rw.ac.ilpd.reportingservice.model.nosql.embedding.EvaluationFormAnswer;
import rw.ac.ilpd.reportingservice.model.nosql.embedding.EvaluationFormUserFiller;
import rw.ac.ilpd.sharedlibrary.enums.EvaluationFormStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Document("report_evaluation_forms")
public class EvaluationForm {
    @Id
    private String id;
    private String tittle;
    private String description;
    private String typeId;
    private UUID evaluated_user;
    @CreatedDate
    private LocalDateTime createdAt;
    private UUID createdBy;
    private EvaluationFormStatus status;
    private String questionsGroupId;
    private List<EvaluationFormUserFiller> userFillers;
    private List<EvaluationFormAnswer> answers;

    public EvaluationForm(String id, String tittle, String description, String typeId, UUID evaluated_user, LocalDateTime createdAt, UUID createdBy, EvaluationFormStatus status, String questionsGroupId, List<EvaluationFormUserFiller> userFillers, List<EvaluationFormAnswer> answers) {
        this.id = id;
        this.tittle = tittle;
        this.description = description;
        this.typeId = typeId;
        this.evaluated_user = evaluated_user;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.status = status;
        this.questionsGroupId = questionsGroupId;
        this.userFillers = userFillers;
        this.answers = answers;
    }

    public EvaluationForm() {
    }

    public static EvaluationFormBuilder builder() {
        return new EvaluationFormBuilder();
    }

    public String getId() {
        return this.id;
    }

    public String getTittle() {
        return this.tittle;
    }

    public String getDescription() {
        return this.description;
    }

    public String getTypeId() {
        return this.typeId;
    }

    public UUID getEvaluated_user() {
        return this.evaluated_user;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public UUID getCreatedBy() {
        return this.createdBy;
    }

    public EvaluationFormStatus getStatus() {
        return this.status;
    }

    public String getQuestionsGroupId() {
        return this.questionsGroupId;
    }

    public List<EvaluationFormUserFiller> getUserFillers() {
        return this.userFillers;
    }

    public List<EvaluationFormAnswer> getAnswers() {
        return this.answers;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public void setEvaluated_user(UUID evaluated_user) {
        this.evaluated_user = evaluated_user;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setCreatedBy(UUID createdBy) {
        this.createdBy = createdBy;
    }

    public void setStatus(EvaluationFormStatus status) {
        this.status = status;
    }

    public void setQuestionsGroupId(String questionsGroupId) {
        this.questionsGroupId = questionsGroupId;
    }

    public void setUserFillers(List<EvaluationFormUserFiller> userFillers) {
        this.userFillers = userFillers;
    }

    public void setAnswers(List<EvaluationFormAnswer> answers) {
        this.answers = answers;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof EvaluationForm)) return false;
        final EvaluationForm other = (EvaluationForm) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$tittle = this.getTittle();
        final Object other$tittle = other.getTittle();
        if (this$tittle == null ? other$tittle != null : !this$tittle.equals(other$tittle)) return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description))
            return false;
        final Object this$typeId = this.getTypeId();
        final Object other$typeId = other.getTypeId();
        if (this$typeId == null ? other$typeId != null : !this$typeId.equals(other$typeId)) return false;
        final Object this$evaluated_user = this.getEvaluated_user();
        final Object other$evaluated_user = other.getEvaluated_user();
        if (this$evaluated_user == null ? other$evaluated_user != null : !this$evaluated_user.equals(other$evaluated_user))
            return false;
        final Object this$createdAt = this.getCreatedAt();
        final Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        final Object this$createdBy = this.getCreatedBy();
        final Object other$createdBy = other.getCreatedBy();
        if (this$createdBy == null ? other$createdBy != null : !this$createdBy.equals(other$createdBy)) return false;
        final Object this$status = this.getStatus();
        final Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final Object this$questionsGroupId = this.getQuestionsGroupId();
        final Object other$questionsGroupId = other.getQuestionsGroupId();
        if (this$questionsGroupId == null ? other$questionsGroupId != null : !this$questionsGroupId.equals(other$questionsGroupId))
            return false;
        final Object this$userFillers = this.getUserFillers();
        final Object other$userFillers = other.getUserFillers();
        if (this$userFillers == null ? other$userFillers != null : !this$userFillers.equals(other$userFillers))
            return false;
        final Object this$answers = this.getAnswers();
        final Object other$answers = other.getAnswers();
        if (this$answers == null ? other$answers != null : !this$answers.equals(other$answers)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof EvaluationForm;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $tittle = this.getTittle();
        result = result * PRIME + ($tittle == null ? 43 : $tittle.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        final Object $typeId = this.getTypeId();
        result = result * PRIME + ($typeId == null ? 43 : $typeId.hashCode());
        final Object $evaluated_user = this.getEvaluated_user();
        result = result * PRIME + ($evaluated_user == null ? 43 : $evaluated_user.hashCode());
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        final Object $createdBy = this.getCreatedBy();
        result = result * PRIME + ($createdBy == null ? 43 : $createdBy.hashCode());
        final Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final Object $questionsGroupId = this.getQuestionsGroupId();
        result = result * PRIME + ($questionsGroupId == null ? 43 : $questionsGroupId.hashCode());
        final Object $userFillers = this.getUserFillers();
        result = result * PRIME + ($userFillers == null ? 43 : $userFillers.hashCode());
        final Object $answers = this.getAnswers();
        result = result * PRIME + ($answers == null ? 43 : $answers.hashCode());
        return result;
    }

    public String toString() {
        return "EvaluationForm(id=" + this.getId() + ", tittle=" + this.getTittle() + ", description=" + this.getDescription() + ", typeId=" + this.getTypeId() + ", evaluated_user=" + this.getEvaluated_user() + ", createdAt=" + this.getCreatedAt() + ", createdBy=" + this.getCreatedBy() + ", status=" + this.getStatus() + ", questionsGroupId=" + this.getQuestionsGroupId() + ", userFillers=" + this.getUserFillers() + ", answers=" + this.getAnswers() + ")";
    }

    public static class EvaluationFormBuilder {
        private String id;
        private String tittle;
        private String description;
        private String typeId;
        private UUID evaluated_user;
        private LocalDateTime createdAt;
        private UUID createdBy;
        private EvaluationFormStatus status;
        private String questionsGroupId;
        private List<EvaluationFormUserFiller> userFillers;
        private List<EvaluationFormAnswer> answers;

        EvaluationFormBuilder() {
        }

        public EvaluationFormBuilder id(String id) {
            this.id = id;
            return this;
        }

        public EvaluationFormBuilder tittle(String tittle) {
            this.tittle = tittle;
            return this;
        }

        public EvaluationFormBuilder description(String description) {
            this.description = description;
            return this;
        }

        public EvaluationFormBuilder typeId(String typeId) {
            this.typeId = typeId;
            return this;
        }

        public EvaluationFormBuilder evaluated_user(UUID evaluated_user) {
            this.evaluated_user = evaluated_user;
            return this;
        }

        public EvaluationFormBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public EvaluationFormBuilder createdBy(UUID createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public EvaluationFormBuilder status(EvaluationFormStatus status) {
            this.status = status;
            return this;
        }

        public EvaluationFormBuilder questionsGroupId(String questionsGroupId) {
            this.questionsGroupId = questionsGroupId;
            return this;
        }

        public EvaluationFormBuilder userFillers(List<EvaluationFormUserFiller> userFillers) {
            this.userFillers = userFillers;
            return this;
        }

        public EvaluationFormBuilder answers(List<EvaluationFormAnswer> answers) {
            this.answers = answers;
            return this;
        }

        public EvaluationForm build() {
            return new EvaluationForm(this.id, this.tittle, this.description, this.typeId, this.evaluated_user, this.createdAt, this.createdBy, this.status, this.questionsGroupId, this.userFillers, this.answers);
        }

        public String toString() {
            return "EvaluationForm.EvaluationFormBuilder(id=" + this.id + ", tittle=" + this.tittle + ", description=" + this.description + ", typeId=" + this.typeId + ", evaluated_user=" + this.evaluated_user + ", createdAt=" + this.createdAt + ", createdBy=" + this.createdBy + ", status=" + this.status + ", questionsGroupId=" + this.questionsGroupId + ", userFillers=" + this.userFillers + ", answers=" + this.answers + ")";
        }
    }
}
