package rw.ac.ilpd.reportingservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import rw.ac.ilpd.reportingservice.model.nosql.document.EvaluationFormQuestionGroup;
import rw.ac.ilpd.sharedlibrary.dto.evaluation.EvaluationFormQuestionGroupRequest;
import rw.ac.ilpd.sharedlibrary.dto.evaluation.EvaluationFormQuestionGroupResponse;

@Mapper(componentModel = "spring")
public interface EvaluationFormQuestionGroupMapper
{
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    EvaluationFormQuestionGroup toEvaluationFormQuestionGroup(
            EvaluationFormQuestionGroupRequest evaluationFormQuestionGroupRequest);

    EvaluationFormQuestionGroupResponse fromEvaluationFormQuestionGroup(
            EvaluationFormQuestionGroup evaluationFormQuestionGroup);
}
