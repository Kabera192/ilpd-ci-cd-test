package rw.ac.ilpd.reportingservice.model.nosql.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import rw.ac.ilpd.reportingservice.model.nosql.embedding.EvaluationFormOption;
import rw.ac.ilpd.sharedlibrary.enums.QuestionType;

import java.util.List;

@Document(collection = "report_evaluation_form_questions")
public class EvaluationFormQuestion
{
    @Id
    private String id;
    private String groupId;
    private String questionText;
    private double weight;
    private String sectionId;
    private QuestionType type;
    private List<EvaluationFormOption> options;

    public EvaluationFormQuestion(String id, String groupId, String questionText, double weight, String sectionId, QuestionType type, List<EvaluationFormOption> options)
    {
        this.id = id;
        this.groupId = groupId;
        this.questionText = questionText;
        this.weight = weight;
        this.sectionId = sectionId;
        this.type = type;
        this.options = options;
    }

    public EvaluationFormQuestion()
    {
    }

    public static EvaluationFormQuestionBuilder builder()
    {
        return new EvaluationFormQuestionBuilder();
    }

    public String getId()
    {
        return this.id;
    }

    public String getGroupId()
    {
        return this.groupId;
    }

    public String getQuestionText()
    {
        return this.questionText;
    }

    public double getWeight()
    {
        return this.weight;
    }

    public String getSectionId()
    {
        return this.sectionId;
    }

    public QuestionType getType()
    {
        return this.type;
    }

    public List<EvaluationFormOption> getOptions()
    {
        return this.options;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public void setGroupId(String groupId)
    {
        this.groupId = groupId;
    }

    public void setQuestionText(String questionText)
    {
        this.questionText = questionText;
    }

    public void setWeight(double weight)
    {
        this.weight = weight;
    }

    public void setSectionId(String sectionId)
    {
        this.sectionId = sectionId;
    }

    public void setType(QuestionType type)
    {
        this.type = type;
    }

    public void setOptions(List<EvaluationFormOption> options)
    {
        this.options = options;
    }

    public String toString()
    {
        return "EvaluationFormQuestion(id=" + this.getId() + ", groupId=" + this.getGroupId() + ", questionText=" + this.getQuestionText() + ", weight=" + this.getWeight() + ", sectionId=" + this.getSectionId() + ", type=" + this.getType() + ", options=" + this.getOptions() + ")";
    }

    public static class EvaluationFormQuestionBuilder
    {
        private String id;
        private String groupId;
        private String questionText;
        private double weight;
        private String sectionId;
        private QuestionType type;
        private List<EvaluationFormOption> options;

        EvaluationFormQuestionBuilder()
        {
        }

        public EvaluationFormQuestionBuilder id(String id)
        {
            this.id = id;
            return this;
        }

        public EvaluationFormQuestionBuilder groupId(String groupId)
        {
            this.groupId = groupId;
            return this;
        }

        public EvaluationFormQuestionBuilder questionText(String questionText)
        {
            this.questionText = questionText;
            return this;
        }

        public EvaluationFormQuestionBuilder weight(double weight)
        {
            this.weight = weight;
            return this;
        }

        public EvaluationFormQuestionBuilder sectionId(String sectionId)
        {
            this.sectionId = sectionId;
            return this;
        }

        public EvaluationFormQuestionBuilder type(QuestionType type)
        {
            this.type = type;
            return this;
        }

        public EvaluationFormQuestionBuilder options(List<EvaluationFormOption> options)
        {
            this.options = options;
            return this;
        }

        public EvaluationFormQuestion build()
        {
            return new EvaluationFormQuestion(this.id, this.groupId, this.questionText, this.weight, this.sectionId, this.type, this.options);
        }

        public String toString()
        {
            return "EvaluationFormQuestion.EvaluationFormQuestionBuilder(id=" + this.id + ", groupId=" + this.groupId + ", questionText=" + this.questionText + ", weight=" + this.weight + ", sectionId=" + this.sectionId + ", type=" + this.type + ", options=" + this.options + ")";
        }
    }
}
