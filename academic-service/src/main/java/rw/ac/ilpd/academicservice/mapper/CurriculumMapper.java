package rw.ac.ilpd.academicservice.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import rw.ac.ilpd.academicservice.model.sql.Curriculum;
import rw.ac.ilpd.academicservice.model.sql.CurriculumModule;
import rw.ac.ilpd.academicservice.model.sql.Module;
import rw.ac.ilpd.academicservice.model.sql.Program;
import rw.ac.ilpd.sharedlibrary.dto.curriculum.CurriculumRequest;
import rw.ac.ilpd.sharedlibrary.dto.curriculum.CurriculumResponse;
import rw.ac.ilpd.sharedlibrary.dto.module.ModuleResponse;
import rw.ac.ilpd.sharedlibrary.enums.CurriculumStatus;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CurriculumMapper {
    private final CurriculumModuleMapper curriculumModuleMapper;
    public Curriculum toCurriculum(CurriculumRequest request, Program program) {
        return Curriculum.builder()
                .name(request.getName())
                .description(request.getDescription())
                .status(CurriculumStatus.ACTIVE)
                .endedAt(null)
                .program(program)
                .build();
    }

    public CurriculumResponse fromCurriculum(Curriculum curriculum, List<CurriculumModule> curriculumMods) {
        if(curriculum == null){
            return null;
        }
        return CurriculumResponse.builder()
                .id(curriculum.getId() != null ? curriculum.getId().toString() : null)
                .name(curriculum.getName())
                .description(curriculum.getDescription())
                .status(curriculum.getStatus().toString())
                .endedAt(curriculum.getEndedAt() != null ? curriculum.getEndedAt().toString() : null)
                .programId(curriculum.getProgram() != null ? curriculum.getProgram().getId().toString() : null)
                .createdBy(curriculum.getCreatedById() != null ? curriculum.getCreatedById().toString() : null)
                .createdAt(curriculum.getCreatedAt() != null ? curriculum.getCreatedAt().toString() : null)
                .curriculumModules(curriculumMods.stream()
                        .map(curriculumModuleMapper::toCurriculumModuleCurriculumResponse)
                        .toList())
                .build();
    }
}