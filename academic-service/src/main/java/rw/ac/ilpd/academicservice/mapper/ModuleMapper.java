package rw.ac.ilpd.academicservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import rw.ac.ilpd.academicservice.model.sql.Module;
import rw.ac.ilpd.sharedlibrary.dto.module.ModuleRequest;
import rw.ac.ilpd.sharedlibrary.dto.module.ModuleResponse;

@Mapper(componentModel = "spring")
public interface ModuleMapper {
    @Mapping(target = "deleteStatus",constant = "false")
     Module toModule(ModuleRequest moduleRequest) ;
     Module toModuleUpdate(@MappingTarget  Module module, ModuleRequest moduleRequest);
     ModuleResponse fromModule(Module module);
}
