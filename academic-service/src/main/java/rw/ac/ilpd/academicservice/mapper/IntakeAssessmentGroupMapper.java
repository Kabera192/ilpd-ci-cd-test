package rw.ac.ilpd.academicservice.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import rw.ac.ilpd.academicservice.model.nosql.document.IntakeAssessmentGroup;
import rw.ac.ilpd.academicservice.model.nosql.embedding.IntakeAssessmentGroupStudent;
import rw.ac.ilpd.sharedlibrary.dto.assessment.IntakeAssessmentGroupStudentResponse;
import rw.ac.ilpd.sharedlibrary.dto.intakeassessmentgroup.IntakeAssessmentGroupRequest;
import rw.ac.ilpd.sharedlibrary.dto.assessment.IntakeAssessmentGroupStudentRequest;
import rw.ac.ilpd.sharedlibrary.dto.intakeassessmentgroup.IntakeAssessmentGroupResponse;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
/**
 * This class handles logic to map an IntakeAssessmentGroupRequest to an
 * IntakeAssessmentGroup entity and map the IntakeAssessmentGroup entity
 * from the DB to an IntakeAssessmentGroupResponse object.
 */
@Component
@Slf4j
public class IntakeAssessmentGroupMapper
{

    /**
     * Converts an IntakeAssessmentGroupRequest obj to an IntakeAssessmentGroup entity.
     *
     * @param request The IntakeAssessmentGroupRequest to be converted.
     * @return IntakeAssessmentGroup entity object or null in case of errors in the conversion process.
     */
    public IntakeAssessmentGroup toIntakeAssessmentGroup(IntakeAssessmentGroupRequest request)
    {
        if (request == null)
        {
            log.warn("Attempted to map null IntakeAssessmentGroupRequest object to IntakeAssessmentGroup");
            return null;
        }

        log.debug("Mapping IntakeAssessmentGroupRequest obj: {} to IntakeAssessmentGroup", request);

        // Map the students list from IntakeAssessmentGroupStudentRequest to IntakeAssessmentGroupStudent
        List<IntakeAssessmentGroupStudent> students = request.getStudents() != null
                ? request.getStudents().stream()
                .map(this::toIntakeAssessmentGroupStudent)
                .toList()
                : List.of();

        // Validate that there are no duplicate studentId's in the students list
        Set<UUID> uniqueStudentIds = new HashSet<>();

        for (IntakeAssessmentGroupStudent student : students)
        {
            if (!uniqueStudentIds.contains(student.getStudentId()))
            {
                uniqueStudentIds.add(student.getStudentId());
            }
            else
            {
                log.error("Duplicate studentId: {} detected", student.getStudentId());
                throw new IllegalArgumentException("Duplicate studentId: " + student.getStudentId());
            }
        }

        // Enforce one student to be the leader of the group if none is set as leader
        if (students.stream().noneMatch(IntakeAssessmentGroupStudent::isLeader))
        {
            log.warn("No leader assigned in group name: {}", request.getName());
            log.info("Enforcing one of the students to be the leader!");
            students.getFirst().setLeader(true);
        }

        return IntakeAssessmentGroup.builder()
                .componentId(UUID.fromString(request.getComponentId()))
                .intakeId(UUID.fromString(request.getIntakeId()))
                .name(request.getName())
                .students(students.stream().toList())
                .build();
    }

    /**
     * Converts an IntakeAssessmentGroup entity to its response DTO.
     *
     * @param group The IntakeAssessmentGroup entity to convert.
     * @return IntakeAssessmentGroupResponse with the group's details, including students.
     */
    public IntakeAssessmentGroupResponse fromIntakeAssessmentGroup(IntakeAssessmentGroup group)
    {
        List<IntakeAssessmentGroupStudentResponse> studentResponses = group.getStudents().stream()
                .map(student -> IntakeAssessmentGroupStudentResponse.builder()
                        .id(student.getId())
                        .studentId(student.getStudentId())
                        .isLeader(student.isLeader())
                        .createdAt(student.getCreatedAt() != null ? student.getCreatedAt() : null)
                        .joinedAt(student.getJoinedAt() != null ? student.getJoinedAt() : null)
                        .leftAt(student.getLeftAt() != null ? student.getLeftAt() : null)
                        .build())
                .toList();

        return IntakeAssessmentGroupResponse.builder()
                .id(group.getId())
                .componentId(group.getComponentId().toString())
                .intakeId(group.getIntakeId().toString())
                .name(group.getName())
                .createdAt(group.getCreatedAt().toString())
                .students(studentResponses)
                .build();
    }

    /**
     * Converts an IntakeAssessmentGroupStudentRequest to an IntakeAssessmentGroupStudent entity.
     *
     * @param request The IntakeAssessmentGroupStudentRequest to be converted.
     * @return IntakeAssessmentGroupStudent entity.
     */
    public IntakeAssessmentGroupStudent toIntakeAssessmentGroupStudent(IntakeAssessmentGroupStudentRequest request)
    {
        if (request == null)
        {
            log.warn("Attempted to map null IntakeAssessmentGroupStudentRequest");
            return null;
        }

        return IntakeAssessmentGroupStudent.builder()
                .id(UUID.randomUUID().toString())
                .studentId(UUID.fromString(request.getStudentId()))
                .isLeader(request.getIsLeader())
                .joinedAt(request.getJoinedAt() != null ? LocalDateTime.parse(request.getJoinedAt()) : LocalDateTime.now())
                .leftAt(request.getLeftAt() != null ? LocalDateTime.parse(request.getLeftAt()) : null)
                .build();
    }
}