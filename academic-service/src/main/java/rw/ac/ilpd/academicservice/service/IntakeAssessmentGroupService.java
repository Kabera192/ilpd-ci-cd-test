package rw.ac.ilpd.academicservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.academicservice.mapper.IntakeAssessmentGroupMapper;
import rw.ac.ilpd.academicservice.model.nosql.document.IntakeAssessmentGroup;
import rw.ac.ilpd.academicservice.model.nosql.embedding.IntakeAssessmentGroupStudent;
import rw.ac.ilpd.academicservice.repository.nosql.IntakeAssessmentGroupRepository;
import rw.ac.ilpd.sharedlibrary.dto.assessment.IntakeAssessmentGroupStudentRequest;
import rw.ac.ilpd.sharedlibrary.dto.assessment.IntakeAssessmentGroupStudentResponse;
import rw.ac.ilpd.sharedlibrary.dto.intakeassessmentgroup.IntakeAssessmentGroupRequest;
import rw.ac.ilpd.sharedlibrary.dto.intakeassessmentgroup.IntakeAssessmentGroupResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class for managing IntakeAssessmentGroup entities.
 * Provides business logic for creating, retrieving, updating, and deleting intake assessment groups,
 * as well as managing students within those groups.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class IntakeAssessmentGroupService
{
    private final IntakeAssessmentGroupRepository repository;
    private final IntakeAssessmentGroupMapper mapper;

    /**
     * Creates a new IntakeAssessmentGroup based on the provided request.
     *
     * @param request The IntakeAssessmentGroupRequest containing the group details and students.
     * @return IntakeAssessmentGroupResponse with the created group's details.
     * @throws IllegalArgumentException if the request is invalid or mapping fails.
     */
    public IntakeAssessmentGroupResponse createIntakeAssessmentGroup(IntakeAssessmentGroupRequest request)
    {
        log.info("Creating new intake assessment group with name: {}", request.getName());

        try
        {
            IntakeAssessmentGroup group = mapper.toIntakeAssessmentGroup(request);
            if (group == null)
            {
                log.error("Failed to map IntakeAssessmentGroupRequest to IntakeAssessmentGroup");
                throw new IllegalArgumentException("Invalid request data for creating intake assessment group");
            }

            IntakeAssessmentGroup savedGroup = repository.save(group);
            log.debug("Successfully saved intake assessment group with ID: {}", savedGroup.getId());

            return mapper.fromIntakeAssessmentGroup(savedGroup);
        }
        catch (Exception e)
        {
            log.error("Error creating intake assessment group: {}", e.getMessage(), e);
            throw new IllegalStateException("Failed to create intake assessment group", e);
        }
    }

    /**
     * Retrieves an IntakeAssessmentGroup by its ID.
     *
     * @param id The ID of the group to retrieve.
     * @return IntakeAssessmentGroupResponse if found, otherwise null.
     */
    public IntakeAssessmentGroupResponse getIntakeAssessmentGroupById(String id)
    {
        log.info("Retrieving intake assessment group with ID: {}", id);

        Optional<IntakeAssessmentGroup> groupOptional = repository.findById(id);
        if (groupOptional.isPresent())
        {
            log.debug("Found intake assessment group with ID: {}", id);
            return mapper.fromIntakeAssessmentGroup(groupOptional.get());
        }
        else
        {
            log.warn("No intake assessment group found with ID: {}", id);
            return null;
        }
    }

    /**
     * Retrieves all IntakeAssessmentGroups for a given intake and component with pagination and sorting.
     *
     * @param intakeId    The UUID of the intake.
     * @param componentId The UUID of the component.
     * @param pageable    Pagination and sorting parameters (page number, page size, sort field, and direction).
     * @return Page of IntakeAssessmentGroupResponse objects with pagination metadata.
     */
    public Page<IntakeAssessmentGroupResponse> getGroupsByIntakeAndComponent(UUID intakeId, UUID componentId,
                                                                             Pageable pageable)
    {
        log.info("Retrieving groups for intake ID: {} and component ID: {} with pagination: {}", intakeId, componentId, pageable);

        Page<IntakeAssessmentGroup> groupsPage = repository.findByIntakeIdAndComponentId(intakeId, componentId, pageable);
        log.debug("Found {} groups for intake ID: {} and component ID: {} on page {}",
                groupsPage.getTotalElements(), intakeId, componentId, pageable.getPageNumber());

        List<IntakeAssessmentGroupResponse> responses = groupsPage.getContent().stream()
                .map(mapper::fromIntakeAssessmentGroup)
                .collect(Collectors.toList());

        return new PageImpl<>(responses, pageable, groupsPage.getTotalElements());
    }

    /**
     * Updates an existing IntakeAssessmentGroup.
     *
     * @param id      The ID of the group to update.
     * @param request The IntakeAssessmentGroupRequest with updated details.
     * @return IntakeAssessmentGroupResponse with the updated group's details.
     * @throws IllegalArgumentException if the group is not found or request is invalid.
     */
    public IntakeAssessmentGroupResponse updateIntakeAssessmentGroup(String id, IntakeAssessmentGroupRequest request)
    {
        log.info("Updating intake assessment group with ID: {}", id);

        Optional<IntakeAssessmentGroup> groupOptional = repository.findById(id);
        if (groupOptional.isEmpty())
        {
            log.error("No intake assessment group found with ID: {}", id);
            throw new IllegalArgumentException("Intake assessment group not found");
        }

        try
        {
            IntakeAssessmentGroup existingGroup = groupOptional.get();
            IntakeAssessmentGroup updatedGroup = mapper.toIntakeAssessmentGroup(request);
            if (updatedGroup == null)
            {
                log.error("Failed to map IntakeAssessmentGroupRequest to IntakeAssessmentGroup for update");
                throw new IllegalArgumentException("Invalid request data for updating intake assessment group");
            }

            // Preserve existing ID and creation date
            updatedGroup.setId(existingGroup.getId());
            updatedGroup.setCreatedAt(existingGroup.getCreatedAt());

            IntakeAssessmentGroup savedGroup = repository.save(updatedGroup);
            log.debug("Successfully updated intake assessment group with ID: {}", savedGroup.getId());

            return mapper.fromIntakeAssessmentGroup(savedGroup);
        }
        catch (Exception e)
        {
            log.error("Error updating intake assessment group: {}", e.getMessage(), e);
            throw new IllegalStateException("Failed to update intake assessment group", e);
        }
    }

    /**
     * Deletes an IntakeAssessmentGroup by its ID.
     *
     * @param id The ID of the group to delete.
     * @return true if deletion was successful, false if group not found.
     */
    public boolean deleteIntakeAssessmentGroup(String id)
    {
        log.info("Deleting intake assessment group with ID: {}", id);

        if (repository.existsById(id))
        {
            repository.deleteById(id);
            log.debug("Successfully deleted intake assessment group with ID: {}", id);
            return true;
        }
        else
        {
            log.warn("No intake assessment group found with ID: {} for deletion", id);
            return false;
        }
    }

    /**
     * Adds a student to an existing IntakeAssessmentGroup.
     *
     * @param groupId        The ID of the group to add the student to.
     * @param studentRequest The IntakeAssessmentGroupStudentRequest containing student details.
     * @return Updated IntakeAssessmentGroupResponse with the new student included.
     * @throws IllegalArgumentException if the group is not found or request is invalid.
     */
    public IntakeAssessmentGroupResponse addStudentToGroup(String groupId, IntakeAssessmentGroupStudentRequest studentRequest)
    {
        log.info("Adding student to intake assessment group with ID: {}", groupId);

        Optional<IntakeAssessmentGroup> groupOptional = repository.findById(groupId);
        if (groupOptional.isEmpty())
        {
            log.error("No intake assessment group found with ID: {}", groupId);
            throw new IllegalArgumentException("Intake assessment group not found");
        }

        try
        {
            IntakeAssessmentGroup group = groupOptional.get();
            IntakeAssessmentGroupStudent student = mapper.toIntakeAssessmentGroupStudent(studentRequest);
            if (student == null)
            {
                log.error("Failed to map IntakeAssessmentGroupStudentRequest to IntakeAssessmentGroupStudent");
                throw new IllegalArgumentException("Invalid student request data");
            }
            if (student.isLeader())
            {
                // Ensure only one leader by setting others to non-leaders
                group.getStudents().
                        forEach(existingStudent -> existingStudent.setLeader(false));
            }

            // check if the student to add to the group is not already in the group
            for (IntakeAssessmentGroupStudent s : group.getStudents())
            {
                if (s.getId().equals(student.getId()))
                {
                    log.error("Student with id: {} already exists", student.getId());
                    throw new IllegalArgumentException("Student with id: " + student.getId() + " already exists");
                }
            }

            // Add student to the group's students list
            group.getStudents().add(student);
            IntakeAssessmentGroup savedGroup = repository.save(group);
            log.debug("Successfully added student to group with ID: {}", groupId);

            return mapper.fromIntakeAssessmentGroup(savedGroup);
        }
        catch (Exception e)
        {
            log.error("Error adding student to group: {}", e.getMessage(), e);
            throw new IllegalStateException("Failed to add student to group", e);
        }
    }

    /**
     * Removes a student from an existing IntakeAssessmentGroup.
     *
     * @param groupId   The ID of the group.
     * @param studentId The UUID of the student to remove.
     * @return Updated IntakeAssessmentGroupResponse without the removed student.
     * @throws IllegalArgumentException if the group or student is not found.
     */
    public IntakeAssessmentGroupResponse removeStudentFromGroup(String groupId, UUID studentId)
    {
        log.info("Removing student with ID: {} from group with ID: {}", studentId, groupId);

        Optional<IntakeAssessmentGroup> groupOptional = repository.findById(groupId);
        if (groupOptional.isEmpty())
        {
            log.error("No intake assessment group found with ID: {}", groupId);
            throw new IllegalArgumentException("Intake assessment group not found");
        }

        IntakeAssessmentGroup group = groupOptional.get();

        IntakeAssessmentGroupStudent targetStudent = group.getStudents().stream()
                .filter(student -> student.getStudentId().equals(studentId))
                .findFirst()
                .orElseThrow(() ->
                {
                    log.warn("Student with ID: {} not found in group with ID: {}", studentId, groupId);
                    return new IllegalArgumentException("Student not found in group");
                });

        if (targetStudent.isLeader())
        {
            // Check if there are other students to assign as leader
            List<IntakeAssessmentGroupStudent> remainingStudents = group.getStudents().stream()
                    .filter(student -> !student.getStudentId().equals(studentId))
                    .toList();

            // Assign the first remaining student as the new leader
            remainingStudents.getFirst().setLeader(true);
            log.info("Assigned new leader with student ID: {} in group ID: {}"
                    , remainingStudents.getFirst().getStudentId(), groupId);
        }

        boolean removed = group.getStudents().removeIf(
                student -> student.getStudentId().equals(studentId));
        if (!removed)
        {
            log.warn("Student with ID: {} not found in group with ID: {}", studentId, groupId);
            throw new IllegalArgumentException("Student not found in group");
        }

        IntakeAssessmentGroup savedGroup = repository.save(group);
        log.debug("Successfully removed student from group with ID: {}", groupId);

        return mapper.fromIntakeAssessmentGroup(savedGroup);
    }

    /**
     * Updates the leadership status of a student in an intake assessment group.
     * Ensures only one student is the leader by setting all others to non-leaders.
     *
     * @param groupId   The ID of the group.
     * @param studentId The UUID of the student to update.
     * @param isLeader  The new leadership status.
     * @return Updated IntakeAssessmentGroupResponse with the modified group.
     * @throws IllegalArgumentException if the group or student is not found.
     */
    public IntakeAssessmentGroupResponse updateStudentLeadership(String groupId, UUID studentId, boolean isLeader)
    {
        log.info("""
                        Updating leadership status for student ID: {} in group ID: {} to isLeader: {}"""
                , studentId, groupId, isLeader);

        Optional<IntakeAssessmentGroup> groupOptional = repository.findById(groupId);
        if (groupOptional.isEmpty())
        {
            log.error("No intake assessment group found with ID: {}", groupId);
            throw new IllegalArgumentException("Intake assessment group not found");
        }

        IntakeAssessmentGroup group = groupOptional.get();
        IntakeAssessmentGroupStudent targetStudent = group.getStudents().stream()
                .filter(student -> student.getStudentId().equals(studentId))
                .findFirst()
                .orElseThrow(() ->
                {
                    log.error("Student with ID: {} not found in group with ID: {}", studentId, groupId);
                    return new IllegalArgumentException("Student not found in group");
                });

        if (targetStudent.isLeader() == isLeader)
        {
            log.debug("No change needed for student ID: {} in group ID: {}, isLeader already: {}", studentId, groupId, isLeader);
            return mapper.fromIntakeAssessmentGroup(group);
        }

        // If setting as leader, ensure only one leader exists
        if (isLeader)
        {
            group.getStudents().forEach(student -> student.setLeader(false));
        }
        targetStudent.setLeader(isLeader);

        IntakeAssessmentGroup savedGroup = repository.save(group);
        log.debug("Successfully updated leadership status for student ID: {} in group ID: {}", studentId, groupId);

        return mapper.fromIntakeAssessmentGroup(savedGroup);
    }

    /**
     * Fetches a list of students based on the group that has been
     * sent through the request.
     *
     * @param groupId   The ID of the group.
     * @return List of students that belong to a particular group.
     */
    public List<IntakeAssessmentGroupStudentResponse> getStudentsByGroup(String groupId)
    {
        log.info("Fetching the list of students that belong to group: {}", groupId);

        IntakeAssessmentGroup group = repository.findById(groupId).orElseThrow(() ->
                {
                    throw new EntityNotFoundException("Group requested not found");
                });

        return group.getStudents().stream().map(s ->
                IntakeAssessmentGroupStudentResponse.builder()
                        .id(s.getId())
                        .studentId(s.getStudentId())
                        .isLeader(s.isLeader())
                        .joinedAt(s.getJoinedAt())
                        .leftAt(s.getLeftAt())
                        .createdAt(s.getCreatedAt())
                        .build()).toList();
    }

    /**
     * Utility function to fetch an IntakeAssessmentGroup to be used by other service classes
     */
    public Optional<IntakeAssessmentGroup> getEntity(String id)
    {
        return repository.findById(id);
    }
}