package rw.ac.ilpd.academicservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import rw.ac.ilpd.academicservice.model.sql.CurriculumModule;
import rw.ac.ilpd.academicservice.model.sql.Intake;
import rw.ac.ilpd.academicservice.model.sql.Student;
import rw.ac.ilpd.academicservice.model.sql.StudentGradeCurriculumModule;
import rw.ac.ilpd.sharedlibrary.dto.studentgradecurriculummodule.StudentGradeCurriculumModuleRequest;
import rw.ac.ilpd.sharedlibrary.dto.studentgradecurriculummodule.StudentGradeCurriculumModuleResponse;

@Mapper(componentModel = "spring")
public interface StudentGradeCurriculumModuleMapper {
    @Mapping(target = "studentId",source = "sgcm.student.id")
    @Mapping(target = "curriculumModuleId",source = "sgcm.curriculumModule.id")
    @Mapping(target = "intakeId",source = "sgcm.intake.id")
     StudentGradeCurriculumModuleResponse fromStudentGradeCurriculumModule(StudentGradeCurriculumModule sgcm);
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "student",source = "student")
    @Mapping(target = "curriculumModule",source = "cModule")
    @Mapping(target = "intake",source = "intake")
    @Mapping(target = "resultStatus",constant = "PASS")
    @Mapping(target = "createdAt",ignore = true)
    @Mapping(target = "updatedAt",ignore = true)
    StudentGradeCurriculumModule toStudentGradeCurriculumModuleUpdate(@MappingTarget StudentGradeCurriculumModule studentGradeCurriculumModule,Student student, CurriculumModule cModule, Intake intake, StudentGradeCurriculumModuleRequest request);

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "student",source = "student")
    @Mapping(target = "curriculumModule",source = "cModule")
    @Mapping(target = "intake",source = "intake")
    @Mapping(target = "resultStatus",constant = "PASS")
    @Mapping(target = "createdAt",ignore = true)
    @Mapping(target = "updatedAt",ignore = true)
    StudentGradeCurriculumModule toStudentGradeCurriculumModule(Student student, CurriculumModule cModule, Intake intake, StudentGradeCurriculumModuleRequest request);
}
