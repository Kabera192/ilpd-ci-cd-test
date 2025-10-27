package rw.ac.ilpd.reportingservice.model.nosql.document;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "report_evaluation_form_question__groups")
public class EvaluationFormQuestionGroup
{
    @Id
    private String id;
    private String name;
    private String description;
    @CreatedDate
    private LocalDateTime createdAt;

    public EvaluationFormQuestionGroup(String id, String name, String description, LocalDateTime createdAt)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
    }

    public EvaluationFormQuestionGroup()
    {
    }

    public static EvaluationFormQuestionGroupBuilder builder()
    {
        return new EvaluationFormQuestionGroupBuilder();
    }

    public String getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public String getDescription()
    {
        return this.description;
    }

    public LocalDateTime getCreatedAt()
    {
        return this.createdAt;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setCreatedAt(LocalDateTime createdAt)
    {
        this.createdAt = createdAt;
    }

    public String toString()
    {
        return "EvaluationFormQuestionGroup(id=" + this.getId() + ", name=" + this.getName() + ", description=" + this.getDescription() + ", createdAt=" + this.getCreatedAt() + ")";
    }

    public static class EvaluationFormQuestionGroupBuilder
    {
        private String id;
        private String name;
        private String description;
        private LocalDateTime createdAt;

        EvaluationFormQuestionGroupBuilder()
        {
        }

        public EvaluationFormQuestionGroupBuilder id(String id)
        {
            this.id = id;
            return this;
        }

        public EvaluationFormQuestionGroupBuilder name(String name)
        {
            this.name = name;
            return this;
        }

        public EvaluationFormQuestionGroupBuilder description(String description)
        {
            this.description = description;
            return this;
        }

        public EvaluationFormQuestionGroupBuilder createdAt(LocalDateTime createdAt)
        {
            this.createdAt = createdAt;
            return this;
        }

        public EvaluationFormQuestionGroup build()
        {
            return new EvaluationFormQuestionGroup(this.id, this.name, this.description, this.createdAt);
        }

        public String toString()
        {
            return "EvaluationFormQuestionGroup.EvaluationFormQuestionGroupBuilder(id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", createdAt=" + this.createdAt + ")";
        }
    }
}
