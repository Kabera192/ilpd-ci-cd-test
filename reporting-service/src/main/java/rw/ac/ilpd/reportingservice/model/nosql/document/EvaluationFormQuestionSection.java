package rw.ac.ilpd.reportingservice.model.nosql.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "report_evaluation_form_question_sections")
public class EvaluationFormQuestionSection
{
    @Id
    private String id;
    private String title;

    public EvaluationFormQuestionSection(String id, String title)
    {
        this.id = id;
        this.title = title;
    }

    public EvaluationFormQuestionSection()
    {
    }

    public static EvaluationFormQuestionSectionBuilder builder()
    {
        return new EvaluationFormQuestionSectionBuilder();
    }

    public String getId()
    {
        return this.id;
    }

    public String getTitle()
    {
        return this.title;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String toString()
    {
        return "EvaluationFormQuestionSection(id=" + this.getId() + ", title=" + this.getTitle() + ")";
    }

    public static class EvaluationFormQuestionSectionBuilder
    {
        private String id;
        private String title;

        EvaluationFormQuestionSectionBuilder()
        {
        }

        public EvaluationFormQuestionSectionBuilder id(String id)
        {
            this.id = id;
            return this;
        }

        public EvaluationFormQuestionSectionBuilder title(String title)
        {
            this.title = title;
            return this;
        }

        public EvaluationFormQuestionSection build()
        {
            return new EvaluationFormQuestionSection(this.id, this.title);
        }

        public String toString()
        {
            return "EvaluationFormQuestionSection.EvaluationFormQuestionSectionBuilder(id=" + this.id + ", title=" + this.title + ")";
        }
    }
}
