package rw.ac.ilpd.reportingservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import rw.ac.ilpd.reportingservice.model.nosql.document.EvaluationFormQuestion;
import rw.ac.ilpd.sharedlibrary.dto.evaluation.EvaluationFormQuestionRequest;
import rw.ac.ilpd.sharedlibrary.dto.evaluation.EvaluationFormQuestionResponse;

@Mapper(componentModel = "spring")
public interface EvaluationFormQuestionMapper
{
    @Mapping(target = "id", ignore = true)
    EvaluationFormQuestion toEvaluationFormQuestion(
            EvaluationFormQuestionRequest evaluationFormQuestionRequest);

    EvaluationFormQuestionResponse fromEvaluationFormQuestion(EvaluationFormQuestion evaluationFormQuestion);
}
