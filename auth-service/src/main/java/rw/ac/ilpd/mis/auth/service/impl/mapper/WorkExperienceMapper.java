package rw.ac.ilpd.mis.auth.service.impl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import rw.ac.ilpd.mis.auth.entity.mongo.WorkExperience;
import rw.ac.ilpd.mis.auth.util.DateMapperFormatter;
import rw.ac.ilpd.mis.shared.dto.userprofile.WorkExperienceRequest;
import rw.ac.ilpd.mis.shared.dto.userprofile.WorkExperienceResponse;

@Mapper(componentModel = "spring",uses = {DateMapperFormatter.class})
public interface WorkExperienceMapper {
    @Mapping(target = "userId",ignore = true)
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "jobLocation.country",source = "request.country")
    @Mapping(target = "jobLocation.city",source = "request.city")
    @Mapping(target = "jobLocation.addressLine",source = "request.addressLine")
    WorkExperience toWorkingExperience(WorkExperienceRequest request);

    @Mapping(target="id",ignore=true)
    @Mapping(target = "userId",ignore = true)
    @Mapping(target = "jobLocation.country",source = "request.country")
    @Mapping(target = "jobLocation.city",source = "request.city")
    @Mapping(target = "jobLocation.addressLine",source = "request.addressLine")
    WorkExperience toWorkingExperienceUpdate(@MappingTarget WorkExperience we, WorkExperienceRequest request);

    @Mapping(source = "we.jobLocation.country",target = "jobLocation.country")
    @Mapping(source = "we.jobLocation.city",target = "jobLocation.city")
    @Mapping(source = "we.jobLocation.addressLine", target= "jobLocation.addressLine")
    WorkExperienceResponse  toWorkingExperienceResponse(WorkExperience we);
}
