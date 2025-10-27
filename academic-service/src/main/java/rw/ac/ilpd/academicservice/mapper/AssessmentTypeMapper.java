package rw.ac.ilpd.academicservice.mapper;

import org.mapstruct.*;
import rw.ac.ilpd.academicservice.model.sql.AssessmentGroup;
import rw.ac.ilpd.academicservice.model.sql.AssessmentType;
import rw.ac.ilpd.academicservice.util.DateMapperFormatter;
import rw.ac.ilpd.sharedlibrary.dto.assessmenttype.AssessmentTypeRequest;
import rw.ac.ilpd.sharedlibrary.dto.assessmenttype.AssessmentTypeResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring", uses = {DateMapperFormatter.class})
public interface AssessmentTypeMapper
{
    @Mapping(source = "assessmentGroup", target = "assessmentGroup")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "acronym", expression = "java(request.getAcronym() != null ? request.getAcronym().toUpperCase() : null)")
    @Mapping(target = "createdAt", ignore = true)
    AssessmentType toAssessmentType(AssessmentGroup assessmentGroup, AssessmentTypeRequest request);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "assessmentGroup",source = "assessmentGroup")
    AssessmentType toAssessmentTypeUpdate(@MappingTarget AssessmentType assessmentType, AssessmentGroup assessmentGroup, AssessmentTypeRequest request);

    @Mapping(source = "assessmentType.assessmentGroup.id", target = "assessmentGroupId")
    @Mapping(target = "createdAt", qualifiedByName = "formatDateTime")
    AssessmentTypeResponse fromAssessmentType(AssessmentType assessmentType);
}
