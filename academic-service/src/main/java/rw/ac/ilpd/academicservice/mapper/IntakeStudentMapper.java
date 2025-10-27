package rw.ac.ilpd.academicservice.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import rw.ac.ilpd.academicservice.model.sql.Intake;
import rw.ac.ilpd.academicservice.model.sql.IntakeStudent;
import rw.ac.ilpd.academicservice.model.sql.Student;
import rw.ac.ilpd.sharedlibrary.dto.intakestudent.IntakeStudentRequest;
import rw.ac.ilpd.sharedlibrary.dto.intakestudent.IntakeStudentResponse;
import rw.ac.ilpd.sharedlibrary.enums.IntakeStudentStatus;

/**
 * This class handles logic to map an IntakeStudentRequest to an IntakeStudent
 * entity and map the IntakeStudent entity from the DB to an IntakeStudentResponse object.
 * */
@Component
@Slf4j
public class IntakeStudentMapper
{
    /**
     * Converts an IntakeStudentRequest obj to a IntakeStudent entity.
     *
     * Parameter:
     *      IntakeStudentRequest -> Object to be converted into a IntakeStudent entity.
     *
     * Returns:
     *      IntakeStudent entity object or null in case of errors in the conversion
     *      process.
     */
    public IntakeStudent toIntakeStudent(
            IntakeStudentRequest intakeStudentRequest,
            Student student, Intake intake)
    {
        if (intakeStudentRequest == null)
        {
            log.warn("Attempted to map null IntakeStudentRequest object to IntakeStudent");
            return null;
        }

        log.debug("Mapping IntakeStudentRequest obj: {} to IntakeStudent", intakeStudentRequest);

        return IntakeStudent.builder()
                .student(student)
                .intake(intake)
                .isEnforced(intakeStudentRequest.getIsEnforced())
                .enforcementComment(intakeStudentRequest.getEnforcementComment())
                .intakeStudentStatus(IntakeStudentStatus.fromString(intakeStudentRequest.getStatus()))
                .isRetaking(intakeStudentRequest.getIsRetaking())
                .build();
    }

    /**
     * Converts a IntakeStudent entity to a IntakeStudentResponse object
     *
     * Parameter:
     *      IntakeStudent -> Object of the IntakeStudent entity
     *      to be converted into a IntakeStudentResponse DTO
     *
     * Returns:
     *      IntakeStudentResponse object to the caller or null if an error is
     *      encountered during the mapping process.
     */
    public IntakeStudentResponse fromIntakeStudent(
            IntakeStudent intakeStudent)
    {
        if (intakeStudent == null)
        {
            log.warn("Attempted to map null IntakeStudent object");
            return null;
        }

        log.debug("Mapping Lecturer: {} to IntakeStudentResponse", intakeStudent);

        return IntakeStudentResponse.builder()
                .id(intakeStudent.getId().toString())
                .studentId(intakeStudent.getStudent().getId().toString())
                .intakeId(intakeStudent.getIntake().getId().toString())
                .isEnforced(intakeStudent.isEnforced())
                .enforcementComment(intakeStudent.getEnforcementComment())
                .isRetaking(intakeStudent.isRetaking())
                .status(intakeStudent.getIntakeStudentStatus().toString())
                .createdAt(intakeStudent.getCreatedAt().toString())
                .build();
    }
}
