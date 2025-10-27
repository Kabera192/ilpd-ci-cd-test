package rw.ac.ilpd.academicservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.ilpd.academicservice.mapper.IntakeStudentMapper;
import rw.ac.ilpd.academicservice.model.sql.Intake;
import rw.ac.ilpd.academicservice.model.sql.IntakeStudent;
import rw.ac.ilpd.academicservice.model.sql.Student;
import rw.ac.ilpd.academicservice.repository.sql.IntakeRepository;
import rw.ac.ilpd.academicservice.repository.sql.IntakeStudentRepository;
import rw.ac.ilpd.academicservice.repository.sql.StudentRepository;
import rw.ac.ilpd.sharedlibrary.dto.intakestudent.IntakeStudentRequest;
import rw.ac.ilpd.sharedlibrary.dto.intakestudent.IntakeStudentResponse;
import rw.ac.ilpd.sharedlibrary.enums.IntakeStudentStatus;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class for managing IntakeStudent entities.
 * Provides business logic for creating, retrieving, updating, and deleting student-intake associations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class IntakeStudentService
{
    private final IntakeStudentRepository intakeStudentRepository;
    private final IntakeService intakeService;
    private final StudentService studentService;
    private final IntakeStudentMapper intakeStudentMapper;

    /**
     * Creates a new student-intake association.
     *
     * @param request The IntakeStudentRequest containing student and intake IDs and other details.
     * @return IntakeStudentResponse with the created association details.
     * @throws EntityNotFoundException  if the student or intake is not found.
     * @throws IllegalArgumentException if the request is invalid or the association already exists.
     */
    @Transactional
    public IntakeStudentResponse createIntakeStudent(IntakeStudentRequest request)
    {
        log.info("Creating new intake-student association for studentId: {} and intakeId: {}",
                request.getStudentId(), request.getIntakeId());

        // Validate student existence
        Student student = studentService.getEntity(UUID.fromString(request.getStudentId()))
                .orElseThrow(() ->
                {
                    log.error("Student not found for ID: {}", request.getStudentId());
                    return new EntityNotFoundException("Student not found with ID: " + request.getStudentId());
                });

        // Validate intake existence
        Intake intake = intakeService.getEntity(UUID.fromString(request.getIntakeId()))
                .orElseThrow(() ->
                {
                    log.error("Intake not found for ID: {}", request.getIntakeId());
                    return new EntityNotFoundException("Intake not found with ID: " + request.getIntakeId());
                });

        // Check if the association already exists
        if (intakeStudentRepository.existsByStudentAndIntake(intake, student))
        {
            log.warn("Intake-student association already exists for studentId: {} and intakeId: {}",
                    request.getStudentId(), request.getIntakeId());
            throw new IllegalArgumentException("Intake-student association already exists");
        }

        // Map request to entity
        IntakeStudent intakeStudent = intakeStudentMapper.toIntakeStudent(request, student, intake);
        if (intakeStudent == null)
        {
            log.error("Failed to map IntakeStudentRequest to IntakeStudent entity");
            throw new IllegalArgumentException("Failed to create intake-student association");
        }

        // Save the entity
        IntakeStudent savedIntakeStudent = intakeStudentRepository.save(intakeStudent);
        log.debug("Successfully saved intake-student association with ID: {}", savedIntakeStudent.getId());

        // Map to response DTO
        IntakeStudentResponse response = intakeStudentMapper.fromIntakeStudent(savedIntakeStudent);
        if (response == null)
        {
            log.error("Failed to map saved IntakeStudent to IntakeStudentResponse");
            throw new IllegalStateException("Failed to map saved intake-student to response");
        }

        log.info("Successfully created intake-student association with ID: {}", response.getId());
        return response;
    }

    /**
     * Retrieves an intake-student association by its ID.
     *
     * @param id The UUID of the intake-student association.
     * @return IntakeStudentResponse containing the association details.
     * @throws EntityNotFoundException() if the association is not found.
     */
    @Transactional(readOnly = true)
    public IntakeStudentResponse getIntakeStudentById(String id)
    {
        log.info("Retrieving intake-student association with ID: {}", id);

        // Validate ID format
        UUID uuid;
        try
        {
            uuid = UUID.fromString(id);
        }
        catch (IllegalArgumentException e)
        {
            log.error("Invalid UUID format for ID: {}", id);
            throw new IllegalArgumentException("Invalid intake-student ID format");
        }

        // Find the intake-student association
        IntakeStudent intakeStudent = intakeStudentRepository.findById(uuid)
                .orElseThrow(() ->
                {
                    log.error("Intake-student association not found for ID: {}", id);
                    return new EntityNotFoundException("Intake-student association not found with ID: " + id);
                });

        // Map to response DTO
        IntakeStudentResponse response = intakeStudentMapper.fromIntakeStudent(intakeStudent);
        if (response == null)
        {
            log.error("Failed to map IntakeStudent to IntakeStudentResponse for ID: {}", id);
            throw new IllegalStateException("Failed to map intake-student to response");
        }

        log.debug("Successfully retrieved intake-student association with ID: {}", id);
        return response;
    }

    /**
     * Retrieves all intake-student associations with pagination and sorting support.
     *
     * @return Page of IntakeStudentResponse containing the list of associations.
     */
    @Transactional(readOnly = true)
    public Page<IntakeStudentResponse> findAllIntakeStudents(int page, int size, String sortBy, String sortDir)
    {
        log.info("Retrieving possible session couples with page: {}, size: {}, sortBy: {}, sortDir: {}",
                page, size, sortBy, sortDir);

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        // Fetch paginated intake students
        Page<IntakeStudent> intakeStudents = intakeStudentRepository.findAll(pageable);
        log.debug("Retrieved {} intake-student associations for page: {}",
                intakeStudents.getTotalElements(), pageable);

        // Map entities to response DTOs
        List<IntakeStudentResponse> responses = intakeStudents.getContent().stream()
                .map(intakeStudentMapper::fromIntakeStudent)
                .filter(response ->
                {
                    if (response == null)
                    {
                        log.warn("Failed to map an IntakeStudent entity to IntakeStudentResponse");
                        return false;
                    }
                    return true;
                })
                .collect(Collectors.toList());

        log.info("Successfully retrieved {} intake-student associations for page: {}",
                responses.size(), pageable);
        return new PageImpl<>(responses, pageable, intakeStudents.getTotalElements());
    }

    /**
     * Updates an existing intake-student association.
     *
     * @param id      The UUID of the intake-student association to update.
     * @param request The IntakeStudentRequest containing updated details.
     * @return IntakeStudentResponse with the updated association details.
     * @throws EntityNotFoundException() if the association, student, or intake is not found.
     * @throws IllegalArgumentException  if the request is invalid or the new association already exists.
     */
    @Transactional
    public IntakeStudentResponse updateIntakeStudent(String id, IntakeStudentRequest request)
    {
        log.info("Updating intake-student association with ID: {}", id);

        // Validate ID format
        UUID uuid;
        try
        {
            uuid = UUID.fromString(id);
        }
        catch (IllegalArgumentException e)
        {
            log.error("Invalid UUID format for ID: {}", id);
            throw new IllegalArgumentException("Invalid intake-student ID format");
        }

        // Validate request
        if (request.getStudentId() == null || request.getIntakeId() == null || request.getStatus() == null)
        {
            log.error("Invalid request: studentId, intakeId, or status is null");
            throw new IllegalArgumentException("Student ID, Intake ID, and Status must not be null");
        }

        // Find existing intake-student
        IntakeStudent intakeStudent = intakeStudentRepository.findById(uuid)
                .orElseThrow(() ->
                {
                    log.error("Intake-student association not found for ID: {}", id);
                    return new EntityNotFoundException("Intake-student association not found with ID: " + id);
                });

        // Validate student existence
        Student student = studentService.getEntity(UUID.fromString(request.getStudentId()))
                .orElseThrow(() ->
                {
                    log.error("Student not found for ID: {}", request.getStudentId());
                    return new EntityNotFoundException("Student not found with ID: " + request.getStudentId());
                });

        // Validate intake existence
        Intake intake = intakeService.getEntity(UUID.fromString(request.getIntakeId()))
                .orElseThrow(() ->
                {
                    log.error("Intake not found for ID: {}", request.getIntakeId());
                    return new EntityNotFoundException("Intake not found with ID: " + request.getIntakeId());
                });

        // Check if the new association already exists
        if (!intakeStudent.getStudent().getId().equals(student.getId()) ||
                !intakeStudent.getIntake().getId().equals(intake.getId()))
        {
            if (intakeStudentRepository.existsByStudentAndIntake(intake, student))
            {
                log.warn("Intake-student association already exists for studentId: {} and intakeId: {}",
                        request.getStudentId(), request.getIntakeId());
                throw new IllegalArgumentException("Intake-student association already exists");
            }
        }

        // Update fields
        try
        {
            intakeStudent.setStudent(student);
            intakeStudent.setIntake(intake);
            intakeStudent.setEnforced(request.getIsEnforced() != null ? request.getIsEnforced() : false);
            intakeStudent.setEnforcementComment(request.getEnforcementComment());
            intakeStudent.setIntakeStatus(IntakeStudentStatus.valueOf(request.getStatus()));
            intakeStudent.setRetaking(request.getIsRetaking() != null ? request.getIsRetaking() : false);
            log.debug("Updated intake-student association with ID: {} with new details", id);
        }
        catch (IllegalArgumentException e)
        {
            log.error("Invalid IntakeStudentStatus: {}", request.getStatus());
            throw new IllegalArgumentException("Invalid status value: " + request.getStatus());
        }

        // Save updated entity
        IntakeStudent updatedIntakeStudent = intakeStudentRepository.save(intakeStudent);
        log.debug("Successfully saved updated intake-student association with ID: {}", id);

        // Map to response DTO
        IntakeStudentResponse response = intakeStudentMapper.fromIntakeStudent(updatedIntakeStudent);
        if (response == null)
        {
            log.error("Failed to map updated IntakeStudent to IntakeStudentResponse for ID: {}", id);
            throw new IllegalStateException("Failed to map updated intake-student to response");
        }

        log.info("Successfully updated intake-student association with ID: {}", id);
        return response;
    }

    /**
     * Deletes an intake-student association by its ID.
     *
     * @param id The UUID of the intake-student association to delete.
     * @throws EntityNotFoundException() if the association is not found.
     */
    @Transactional
    public void deleteIntakeStudent(String id)
    {
        log.info("Deleting intake-student association with ID: {}", id);

        // Validate ID format
        UUID uuid;
        try
        {
            uuid = UUID.fromString(id);
        }
        catch (IllegalArgumentException e)
        {
            log.error("Invalid UUID format for ID: {}", id);
            throw new IllegalArgumentException("Invalid intake-student ID format");
        }

        // Check if association exists
        if (!intakeStudentRepository.existsById(uuid))
        {
            log.error("Intake-student association not found for ID: {}", id);
            throw new EntityNotFoundException("Intake-student association not found with ID: " + id);
        }

        // Delete the association
        intakeStudentRepository.deleteById(uuid);
        log.info("Successfully deleted intake-student association with ID: {}", id);
    }
}