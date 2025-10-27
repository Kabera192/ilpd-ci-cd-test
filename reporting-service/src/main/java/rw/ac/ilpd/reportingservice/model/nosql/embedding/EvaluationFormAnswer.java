package rw.ac.ilpd.reportingservice.model.nosql.embedding;

import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

public class EvaluationFormAnswer {
    private String id = UUID.randomUUID().toString();
    private String questionId;
    private String selectedOptionId;
    private UUID userAnsweredId;
    private String writtenAnswer;
    private double awardedMarks;
    private String email;
    @CreatedDate
    private LocalDateTime submittedAt;

    public EvaluationFormAnswer(String id, String questionId, String selectedOptionId, UUID userAnsweredId, String writtenAnswer, double awardedMarks, String email, LocalDateTime submittedAt) {
        this.id = id;
        this.questionId = questionId;
        this.selectedOptionId = selectedOptionId;
        this.userAnsweredId = userAnsweredId;
        this.writtenAnswer = writtenAnswer;
        this.awardedMarks = awardedMarks;
        this.email = email;
        this.submittedAt = submittedAt;
    }

    public EvaluationFormAnswer() {
    }

    public static EvaluationFormAnswerBuilder builder() {
        return new EvaluationFormAnswerBuilder();
    }

    public String getId() {
        return this.id;
    }

    public String getQuestionId() {
        return this.questionId;
    }

    public String getSelectedOptionId() {
        return this.selectedOptionId;
    }

    public UUID getUserAnsweredId() {
        return this.userAnsweredId;
    }

    public String getWrittenAnswer() {
        return this.writtenAnswer;
    }

    public double getAwardedMarks() {
        return this.awardedMarks;
    }

    public String getEmail() {
        return this.email;
    }

    public LocalDateTime getSubmittedAt() {
        return this.submittedAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public void setSelectedOptionId(String selectedOptionId) {
        this.selectedOptionId = selectedOptionId;
    }

    public void setUserAnsweredId(UUID userAnsweredId) {
        this.userAnsweredId = userAnsweredId;
    }

    public void setWrittenAnswer(String writtenAnswer) {
        this.writtenAnswer = writtenAnswer;
    }

    public void setAwardedMarks(double awardedMarks) {
        this.awardedMarks = awardedMarks;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof EvaluationFormAnswer)) return false;
        final EvaluationFormAnswer other = (EvaluationFormAnswer) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$questionId = this.getQuestionId();
        final Object other$questionId = other.getQuestionId();
        if (this$questionId == null ? other$questionId != null : !this$questionId.equals(other$questionId))
            return false;
        final Object this$selectedOptionId = this.getSelectedOptionId();
        final Object other$selectedOptionId = other.getSelectedOptionId();
        if (this$selectedOptionId == null ? other$selectedOptionId != null : !this$selectedOptionId.equals(other$selectedOptionId))
            return false;
        final Object this$userAnsweredId = this.getUserAnsweredId();
        final Object other$userAnsweredId = other.getUserAnsweredId();
        if (this$userAnsweredId == null ? other$userAnsweredId != null : !this$userAnsweredId.equals(other$userAnsweredId))
            return false;
        final Object this$writtenAnswer = this.getWrittenAnswer();
        final Object other$writtenAnswer = other.getWrittenAnswer();
        if (this$writtenAnswer == null ? other$writtenAnswer != null : !this$writtenAnswer.equals(other$writtenAnswer))
            return false;
        if (Double.compare(this.getAwardedMarks(), other.getAwardedMarks()) != 0) return false;
        final Object this$email = this.getEmail();
        final Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        final Object this$submittedAt = this.getSubmittedAt();
        final Object other$submittedAt = other.getSubmittedAt();
        if (this$submittedAt == null ? other$submittedAt != null : !this$submittedAt.equals(other$submittedAt))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof EvaluationFormAnswer;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $questionId = this.getQuestionId();
        result = result * PRIME + ($questionId == null ? 43 : $questionId.hashCode());
        final Object $selectedOptionId = this.getSelectedOptionId();
        result = result * PRIME + ($selectedOptionId == null ? 43 : $selectedOptionId.hashCode());
        final Object $userAnsweredId = this.getUserAnsweredId();
        result = result * PRIME + ($userAnsweredId == null ? 43 : $userAnsweredId.hashCode());
        final Object $writtenAnswer = this.getWrittenAnswer();
        result = result * PRIME + ($writtenAnswer == null ? 43 : $writtenAnswer.hashCode());
        final long $awardedMarks = Double.doubleToLongBits(this.getAwardedMarks());
        result = result * PRIME + (int) ($awardedMarks >>> 32 ^ $awardedMarks);
        final Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final Object $submittedAt = this.getSubmittedAt();
        result = result * PRIME + ($submittedAt == null ? 43 : $submittedAt.hashCode());
        return result;
    }

    public String toString() {
        return "EvaluationFormAnswer(id=" + this.getId() + ", questionId=" + this.getQuestionId() + ", selectedOptionId=" + this.getSelectedOptionId() + ", userAnsweredId=" + this.getUserAnsweredId() + ", writtenAnswer=" + this.getWrittenAnswer() + ", awardedMarks=" + this.getAwardedMarks() + ", email=" + this.getEmail() + ", submittedAt=" + this.getSubmittedAt() + ")";
    }

    public static class EvaluationFormAnswerBuilder {
        private String id;
        private String questionId;
        private String selectedOptionId;
        private UUID userAnsweredId;
        private String writtenAnswer;
        private double awardedMarks;
        private String email;
        private LocalDateTime submittedAt;

        EvaluationFormAnswerBuilder() {
        }

        public EvaluationFormAnswerBuilder id(String id) {
            this.id = id;
            return this;
        }

        public EvaluationFormAnswerBuilder questionId(String questionId) {
            this.questionId = questionId;
            return this;
        }

        public EvaluationFormAnswerBuilder selectedOptionId(String selectedOptionId) {
            this.selectedOptionId = selectedOptionId;
            return this;
        }

        public EvaluationFormAnswerBuilder userAnsweredId(UUID userAnsweredId) {
            this.userAnsweredId = userAnsweredId;
            return this;
        }

        public EvaluationFormAnswerBuilder writtenAnswer(String writtenAnswer) {
            this.writtenAnswer = writtenAnswer;
            return this;
        }

        public EvaluationFormAnswerBuilder awardedMarks(double awardedMarks) {
            this.awardedMarks = awardedMarks;
            return this;
        }

        public EvaluationFormAnswerBuilder email(String email) {
            this.email = email;
            return this;
        }

        public EvaluationFormAnswerBuilder submittedAt(LocalDateTime submittedAt) {
            this.submittedAt = submittedAt;
            return this;
        }

        public EvaluationFormAnswer build() {
            return new EvaluationFormAnswer(this.id, this.questionId, this.selectedOptionId, this.userAnsweredId, this.writtenAnswer, this.awardedMarks, this.email, this.submittedAt);
        }

        public String toString() {
            return "EvaluationFormAnswer.EvaluationFormAnswerBuilder(id=" + this.id + ", questionId=" + this.questionId + ", selectedOptionId=" + this.selectedOptionId + ", userAnsweredId=" + this.userAnsweredId + ", writtenAnswer=" + this.writtenAnswer + ", awardedMarks=" + this.awardedMarks + ", email=" + this.email + ", submittedAt=" + this.submittedAt + ")";
        }
    }
}
