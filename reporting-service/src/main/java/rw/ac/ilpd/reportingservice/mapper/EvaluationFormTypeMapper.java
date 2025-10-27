package rw.ac.ilpd.reportingservice.mapper;

import org.mapstruct.*;
import rw.ac.ilpd.reportingservice.model.nosql.document.EvaluationFormType;
import rw.ac.ilpd.sharedlibrary.dto.evaluation.EvaluationFormTypeRequest;
import rw.ac.ilpd.sharedlibrary.dto.evaluation.EvaluationFormTypeResponse;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EvaluationFormTypeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isDeleted", constant = "false")
    EvaluationFormType toEntity(EvaluationFormTypeRequest request);

    EvaluationFormTypeResponse toResponse(EvaluationFormType entity);

    List<EvaluationFormTypeResponse> toResponseList(List<EvaluationFormType> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntityFromRequest(EvaluationFormTypeRequest request, @MappingTarget EvaluationFormType entity);
}
