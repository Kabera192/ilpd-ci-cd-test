package rw.ac.ilpd.academicservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import rw.ac.ilpd.academicservice.model.sql.HeadOfModule;
import rw.ac.ilpd.academicservice.util.DateMapperFormatter;
import rw.ac.ilpd.sharedlibrary.dto.headofmodule.HeadOfModuleRequest;
import rw.ac.ilpd.sharedlibrary.dto.headofmodule.HeadOfModuleResponse;

@Mapper(componentModel = "spring",uses = {DateMapperFormatter.class})
public interface HeadOfModuleMapper {

    @Mapping(target = "from", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    HeadOfModule toHeadOfModule(HeadOfModuleRequest headOfModuleRequest);

    HeadOfModule toHeadOfModuleUpdate(@MappingTarget HeadOfModule headOfModule, HeadOfModuleRequest headOfModuleRequest);

    @Mapping(target = "moduleId",source = "headOfModule.module.id")
    @Mapping(target = "lecturerId",source = "headOfModule.lecturer.id")
    @Mapping(target = "createdAt",qualifiedByName = "formatDateTime")
    @Mapping(target = "from",qualifiedByName = "formatDateTime")
    @Mapping(target = "to",qualifiedByName = "formatDateTime")
    HeadOfModuleResponse fromHeadOfModule(HeadOfModule headOfModule);
}
