package rw.ac.ilpd.academicservice.mapper;

import org.springframework.stereotype.Component;
import rw.ac.ilpd.academicservice.model.sql.Curriculum;
import rw.ac.ilpd.academicservice.model.sql.CurriculumModule;
import rw.ac.ilpd.academicservice.model.sql.Module;
import rw.ac.ilpd.sharedlibrary.dto.curriculummodule.CurriculumModuleCurriculumResponse;
import rw.ac.ilpd.sharedlibrary.dto.curriculummodule.CurriculumModuleRequest;
import rw.ac.ilpd.sharedlibrary.dto.curriculummodule.CurriculumModuleResponse;

import java.util.UUID;

@Component
public class CurriculumModuleMapper {
    public CurriculumModule toCurriculumModule(CurriculumModuleRequest request, Curriculum curriculum, Module module) {
        return CurriculumModule.builder()
                .curriculum(curriculum)
                .module(module)
                .moduleOrder(request.getModuleOrder())
                .credits(request.getCredits())
                .build();
    }

    public CurriculumModuleResponse fromCurriculumModule(CurriculumModule curriculumModule) {
        return CurriculumModuleResponse.builder()
                .id(curriculumModule.getId() != null ? curriculumModule.getId().toString() : null)
                .curriculumId(curriculumModule.getCurriculum() != null ?
                        curriculumModule.getCurriculum().getId().toString() : null)
                .moduleId(curriculumModule.getModule() != null ?
                        curriculumModule.getModule().getId().toString() : null)
                .moduleOrder(curriculumModule.getModuleOrder())
                .credits(curriculumModule.getCredits())
                .build();
    }

    public CurriculumModuleCurriculumResponse toCurriculumModuleCurriculumResponse(CurriculumModule curriculumModule) {
        return CurriculumModuleCurriculumResponse.builder()
                .id(curriculumModule.getId() != null ? curriculumModule.getId().toString() : null)
                .moduleId(curriculumModule.getModule() != null ?
                        curriculumModule.getModule().getId().toString() : null)
                .moduleOrder(curriculumModule.getModuleOrder())
                .credits(curriculumModule.getCredits())
                .build();
    }
}