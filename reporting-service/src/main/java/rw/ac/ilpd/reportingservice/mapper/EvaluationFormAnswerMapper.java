package rw.ac.ilpd.reportingservice.mapper;

import org.springframework.stereotype.Component;
import rw.ac.ilpd.reportingservice.model.nosql.embedding.EvaluationFormAnswer;
import rw.ac.ilpd.sharedlibrary.dto.evaluation.EvaluationFormAnswerRequest;
import rw.ac.ilpd.sharedlibrary.dto.evaluation.EvaluationFormAnswerResponse;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class EvaluationFormAnswerMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public EvaluationFormAnswer fromEvaluationFormAnswerRequest(EvaluationFormAnswerRequest request) {
        if (request == null) return null;

        return EvaluationFormAnswer.builder()
                .id(UUID.randomUUID().toString())
                .questionId(request.getQuestionId())
                .selectedOptionId(request.getSelectedOptionId())
                .userAnsweredId(null) // set at runtime if needed
                .writtenAnswer(request.getWrittenAnswer())
                .awardedMarks(0.0) // default until grading
                .email(request.getEmail())
                .submittedAt(null) // set when submitted
                .build();
    }

    public EvaluationFormAnswerResponse toEvaluationFormAnswerResponse(EvaluationFormAnswer answer) {
        if (answer == null) return null;

        return EvaluationFormAnswerResponse.builder()
                .id(answer.getId())
                .questionId(answer.getQuestionId())
                .selectedOptionId(answer.getSelectedOptionId())
                .userAnsweredId(answer.getUserAnsweredId() != null ? answer.getUserAnsweredId().toString() : null)
                .writtenAnswer(answer.getWrittenAnswer())
                .awardedMarks((int) answer.getAwardedMarks())
                .email(answer.getEmail())
                .createdAt(answer.getSubmittedAt() != null ? FORMATTER.format(answer.getSubmittedAt()) : null)
                .build();
    }
}
