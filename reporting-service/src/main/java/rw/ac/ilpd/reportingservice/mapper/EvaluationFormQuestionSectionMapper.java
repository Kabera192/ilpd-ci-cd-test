package rw.ac.ilpd.reportingservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import rw.ac.ilpd.reportingservice.model.nosql.document.EvaluationFormQuestionSection;
import rw.ac.ilpd.sharedlibrary.dto.evaluation.EvaluationFormQuestionSectionRequest;
import rw.ac.ilpd.sharedlibrary.dto.evaluation.EvaluationFormQuestionSectionResponse;

@Mapper(componentModel = "spring")
public interface EvaluationFormQuestionSectionMapper
{
    @Mapping(target = "id", ignore = true)
    EvaluationFormQuestionSection toEvaluationFormQuestionSection(
            EvaluationFormQuestionSectionRequest evaluationFormQuestionSectionRequest);

    EvaluationFormQuestionSectionResponse fromEvaluationFormQuestionSection(
            EvaluationFormQuestionSection evaluationFormQuestionSection);
}
