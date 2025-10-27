package rw.ac.ilpd.reportingservice.mapper;

import org.springframework.stereotype.Component;
import rw.ac.ilpd.reportingservice.model.nosql.document.EvaluationForm;
import rw.ac.ilpd.sharedlibrary.dto.evaluation.EvaluationFormRequest;
import rw.ac.ilpd.sharedlibrary.dto.evaluation.EvaluationFormResponse;
import rw.ac.ilpd.sharedlibrary.enums.EvaluationFormStatus;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class EvaluationFormMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public EvaluationForm fromEvaluationFormRequest(EvaluationFormRequest request) {
        if (request == null) return null;

        return EvaluationForm.builder()
                .tittle(request.getTitle())
                .description(request.getDescription())
                .typeId(request.getTypeId())
                .createdBy(UUID.fromString(request.getCreatedByUserId()))
                .evaluated_user(request.getEvaluatedUserId())
                .status(EvaluationFormStatus.valueOf(request.getStatus()))
                .questionsGroupId(request.getGroupId())
                .build();
    }

    public EvaluationFormResponse toEvaluationFormResponse(EvaluationForm form) {
        if (form == null) return null;

        return EvaluationFormResponse.builder()
                .id(form.getId())
                .title(form.getTittle())
                .description(form.getDescription())
                .typeId(form.getTypeId())
                .createdAt(form.getCreatedAt() != null ? FORMATTER.format(form.getCreatedAt()) : null)
                .createdByUserId(form.getCreatedBy() != null ? form.getCreatedBy().toString() : null)
                .evaluatedUser(form.getEvaluated_user())
                .status(form.getStatus() != null ? form.getStatus().name() : null)
                .groupId(form.getQuestionsGroupId())
                .build();
    }
}
