package rw.ac.ilpd.academicservice.mapper;

import org.springframework.stereotype.Component;
import rw.ac.ilpd.academicservice.model.sql.*;
import rw.ac.ilpd.sharedlibrary.dto.intakestudentretakerestcurriculummodule.IntakeStudentRetakeRestCurriculumModuleRequest;
import rw.ac.ilpd.sharedlibrary.dto.intakestudentretakerestcurriculummodule.IntakeStudentRetakeRestCurriculumModuleResponse;
import rw.ac.ilpd.sharedlibrary.enums.ModuleRetakeResitType;

import java.util.UUID;

@Component
public class IntakeStudentRetakeResitCurriculumModuleMapper {

    public IntakeStudentRetakeResitCurriculumModule toIntakeStudentRetakeResitCurriculumModule(
            IntakeStudentRetakeRestCurriculumModuleRequest request,
            Student student,
            Intake intake,
            CurriculumModule curriculumModule
    ) {
        return IntakeStudentRetakeResitCurriculumModule.builder()
                .student(student)
                .intake(intake)
                .curriculumModule(curriculumModule)
                .moduleRetakeResitType(ModuleRetakeResitType.valueOf(request.getType()))
                .build();
    }

    public IntakeStudentRetakeRestCurriculumModuleResponse fromIntakeStudentRetakeResitCurriculumModule(
            IntakeStudentRetakeResitCurriculumModule entity
    ) {
        return IntakeStudentRetakeRestCurriculumModuleResponse.builder()
                .id(entity.getId() != null ? entity.getId().toString() : null)
                .studentId(entity.getStudent() != null ? entity.getStudent().getId().toString() : null)
                .intakeId(entity.getIntake() != null ? entity.getIntake().getId().toString() : null)
                .curriculumModuleId(entity.getCurriculumModule() != null ? entity.getCurriculumModule().getId().toString() : null)
                .type(entity.getModuleRetakeResitType() != null ? entity.getModuleRetakeResitType().name() : null)
                .build();
    }
}