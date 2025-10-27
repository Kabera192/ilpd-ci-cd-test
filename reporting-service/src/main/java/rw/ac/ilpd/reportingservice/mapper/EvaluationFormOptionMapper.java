package rw.ac.ilpd.reportingservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import rw.ac.ilpd.reportingservice.model.nosql.embedding.EvaluationFormOption;
import rw.ac.ilpd.sharedlibrary.dto.evaluation.EvaluationFormOptionRequest;
import rw.ac.ilpd.sharedlibrary.dto.evaluation.EvaluationFormOptionResponse;

@Mapper(componentModel = "spring")
public interface EvaluationFormOptionMapper
{
    EvaluationFormOption toEvaluationFormOption(EvaluationFormOptionRequest evaluationFormOptionRequest);

    EvaluationFormOptionResponse fromEvaluationFormOption(EvaluationFormOption evaluationFormOption);
}
