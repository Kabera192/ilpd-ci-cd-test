package rw.ac.ilpd.academicservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import rw.ac.ilpd.academicservice.model.sql.*;
import rw.ac.ilpd.academicservice.util.DateMapperFormatter;
import rw.ac.ilpd.sharedlibrary.dto.intake.ApplicationRequiredDocIntakeResponse;
import rw.ac.ilpd.sharedlibrary.dto.intake.IntakeRequest;
import rw.ac.ilpd.sharedlibrary.dto.intake.IntakeResponse;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProgramMapper.class,
        StudyModeSessionMapper.class, CurriculumMapper.class, InstitutionShortCourseSponsorMapper.class, DateMapperFormatter.class})
public interface IntakeMapper
{
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "updatedAt", ignore = true)
//    @Mapping(target = "createdBy", ignore = true)
//    @Mapping(target = "name", source = "intakeRequest.name")
//    @Mapping(target = "status", constant = "OPEN")
//    @Mapping(target = "applicationClosingDate", source = "intakeRequest.applicationDueDate")
//    @Mapping(source = "intakeRequest.deliberationGroupId", target = "deliberationRuleGroupId")
//    @Mapping(source = "program", target = "program")
//    @Mapping(source = "studyModeSession", target = "studyModeSession")
//    @Mapping(source = "curriculum", target = "curriculum")
//    @Mapping(source = "institution", target = "institution")
//    Intake toIntake(IntakeRequest intakeRequest, Program program,
//                    StudyModeSession studyModeSession, Curriculum curriculum,
//                    InstitutionShortCourseSponsor institution);
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "updatedAt", ignore = true)
//    @Mapping(target = "createdBy", ignore = true)
//    @Mapping(target = "name", source = "intakeRequest.name")
//    @Mapping(target = "status", constant = "OPEN")
//    @Mapping(target = "applicationClosingDate", source = "intakeRequest.applicationDueDate")
//    @Mapping(source = "intakeRequest.deliberationGroupId", target = "deliberationRuleGroupId")
//    @Mapping(source = "program", target = "program")
//    @Mapping(source = "studyModeSession", target = "studyModeSession")
//    @Mapping(source = "curriculum", target = "curriculum")
////    Applicable for CLE
//    Intake toIntake(IntakeRequest intakeRequest, Program program,
//                    StudyModeSession studyModeSession, Curriculum curriculum);

    @Mapping(target = "applicationDueDate", source = "intake.applicationClosingDate")
    @Mapping(target = "deliberationGroupId", source = "intake.deliberationRuleGroupId")
    @Mapping(source = "applicationRequiredDocs", target = "applicationRequiredDocs")
    @Mapping(target = "createdAt", qualifiedByName = "formatDateTime")
    @Mapping(target = "updatedAt", qualifiedByName = "formatDateTime")
    IntakeResponse toIntakeResponse(Intake intake,
                                    List<ApplicationRequiredDocIntakeResponse> applicationRequiredDocs);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "name", source = "intakeRequest.name")
    @Mapping(target = "status", constant = "OPEN")
    @Mapping(source = "program", target = "program")
    @Mapping(source = "studyModeSession", target = "studyModeSession")
    @Mapping(target = "applicationClosingDate", source = "intakeRequest.applicationDueDate")
    @Mapping(source = "curriculum", target = "curriculum")
    @Mapping(target = "institution",ignore = true)
    Intake toIntake(IntakeRequest intakeRequest, Program program, StudyModeSession studyModeSession, Curriculum curriculum, String deliberationRuleGroupId, String deliberationDistinctionGrpId);

    @Mapping(target = "status", constant = "OPEN")
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "name", source = "intakeRequest.name")
    @Mapping(target = "applicationClosingDate", source = "intakeRequest.applicationDueDate")
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    Intake toIntakeUpdate(@MappingTarget()Intake intake, IntakeRequest intakeRequest, Program program, StudyModeSession studyModeSession, Curriculum curriculum, String deliberationRuleGroupId, String deliberationDistinctionGrpId);
    @Mapping(target = "name", source = "intakeRequest.name")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "status", constant = "OPEN")
    @Mapping(target = "applicationClosingDate", source = "intakeRequest.applicationDueDate")
    Intake toCleIntake(IntakeRequest intakeRequest, Program program, InstitutionShortCourseSponsor institution);
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "status", constant = "OPEN")
    @Mapping(target = "name", source = "intakeRequest.name")
    Intake toCleIntakeUpdate(@MappingTarget Intake intake, IntakeRequest intakeRequest, Program program, InstitutionShortCourseSponsor institution);
}
