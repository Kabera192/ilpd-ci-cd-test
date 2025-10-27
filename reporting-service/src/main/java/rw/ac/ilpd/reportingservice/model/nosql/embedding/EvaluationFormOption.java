package rw.ac.ilpd.reportingservice.model.nosql.embedding;

public class EvaluationFormOption
{
    private String optionText;
    private Boolean isCorrect;

    public EvaluationFormOption(String optionText, Boolean isCorrect)
    {
        this.optionText = optionText;
        this.isCorrect = isCorrect;
    }

    public EvaluationFormOption()
    {
    }

    public static EvaluationFormOptionBuilder builder()
    {
        return new EvaluationFormOptionBuilder();
    }

    public String getOptionText()
    {
        return this.optionText;
    }

    public Boolean getIsCorrect()
    {
        return this.isCorrect;
    }

    public void setOptionText(String optionText)
    {
        this.optionText = optionText;
    }

    public void setIsCorrect(Boolean isCorrect)
    {
        this.isCorrect = isCorrect;
    }

    public String toString()
    {
        return "EvaluationFormOption(optionText=" + this.getOptionText() + ", isCorrect=" + this.getIsCorrect() + ")";
    }

    public static class EvaluationFormOptionBuilder
    {
        private String optionText;
        private Boolean isCorrect;

        EvaluationFormOptionBuilder()
        {
        }

        public EvaluationFormOptionBuilder optionText(String optionText)
        {
            this.optionText = optionText;
            return this;
        }

        public EvaluationFormOptionBuilder isCorrect(Boolean isCorrect)
        {
            this.isCorrect = isCorrect;
            return this;
        }

        public EvaluationFormOption build()
        {
            return new EvaluationFormOption(this.optionText, this.isCorrect);
        }

        public String toString()
        {
            return "EvaluationFormOption.EvaluationFormOptionBuilder(optionText=" + this.optionText + ", isCorrect=" + this.isCorrect + ")";
        }
    }
}
