package rw.ac.ilpd.academicservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import rw.ac.ilpd.academicservice.integration.client.DocumentClient;
import rw.ac.ilpd.academicservice.integration.client.UserClient;
import rw.ac.ilpd.academicservice.mapper.StudentAssessmentSubmissionMapper;
import rw.ac.ilpd.academicservice.model.nosql.document.StudentAssessmentSubmission;
import rw.ac.ilpd.academicservice.repository.nosql.StudentAssessmentSubmissionRepository;
import rw.ac.ilpd.sharedlibrary.dto.assessment.*;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentResponse;
import rw.ac.ilpd.sharedlibrary.dto.document.ObjectListStorageRequest;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.enums.AssessmentMode;
import rw.ac.ilpd.sharedlibrary.util.ObjectUploadPath;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class StudentAssessmentSubmissionService
{

    private final StudentAssessmentSubmissionRepository studentAssessmentSubmissionRepository;
    private final ModuleComponentAssessmentService moduleComponentAssessmentService;
    private final StudentService studentService;
    private final UserClient userClient;
    private final DocumentClient documentClient;
    private final StudentAssessmentSubmissionMapper studentAssessmentSubmissionMapper;
    private final IntakeAssessmentGroupService intakeAssessmentGroupService;

    private boolean isLate = false;
    private LocalDateTime lateTime;

    /**
     * Create and persist a StudentAssessmentSubmission entity to the database.
     * This method also handles re-submission of an assessment and group submissions.
     */
    public StudentAssessmentSubmissionResponse createStudentAssessmentSubmission(
            StudentAssessmentSubmissionRequest request)
    {
        log.debug("Creating new studentAssessmentSubmission: {}", request);

        // Validate the submission request
        ModuleComponentAssessmentResponse assessment = validateAndGetAssessment(request);

        // check the due date and mark the assignment as late
        if (assessment.getDueDate().isBefore(LocalDateTime.now()))
        {
            lateTime = LocalDateTime.now();
            isLate = true;
        }

        // Handle re-submission scenario
        if (isResubmission(request))
        {
            log.debug("Student assessment re-submission detected");
            return handleResubmission(request, isLate);
        }

        // Handle group assessment
        if (isGroupAssessment(assessment))
        {
            return handleGroupSubmission(request, assessment);
        }

        // Handle individual submission
        return createIndividualSubmission(request, isLate);
    }

    /**
     * Handle group assessment submissions by creating submissions for all group members
     */
    private StudentAssessmentSubmissionResponse handleGroupSubmission(
            StudentAssessmentSubmissionRequest request,
            ModuleComponentAssessmentResponse assessment)
    {

        log.debug("Processing group assessment submission for group: {}", request.getSubjectId());

        List<IntakeAssessmentGroupStudentResponse> groupMembers =
                intakeAssessmentGroupService.getStudentsByGroup(request.getSubjectId());

        if (groupMembers.isEmpty())
        {
            throw new EntityNotFoundException("No students found in assessment group");
        }

        List<String> documentIds = saveAttachmentsIfPresent(request.getDocuments());

        // Create submissions for all group members
        List<StudentAssessmentSubmission> groupSubmissions = groupMembers.stream()
                .map(student -> createSubmissionForStudent(request,
                        student.getStudentId().toString(), documentIds))
                .map(studentAssessmentSubmissionRepository::save)
                .toList();

        log.info("Created {} group submissions for assessment: {}",
                groupSubmissions.size(), request.getModuleComponentAssessmentId());

        // Return the first submission as representative (or you might want to return a different response)
        return studentAssessmentSubmissionMapper.fromStudentAssessmentSubmission(groupSubmissions.get(0));
    }

    /**
     * Create individual assessment submission
     */
    private StudentAssessmentSubmissionResponse createIndividualSubmission(StudentAssessmentSubmissionRequest request,
                                                                           boolean isLate)
    {
        log.debug("Processing individual assessment submission");

        List<String> documentIds = saveAttachmentsIfPresent(request.getDocuments());

        StudentAssessmentSubmission submission = studentAssessmentSubmissionMapper
                .toStudentAssessmentSubmission(request);
        submission.setDocuments(documentIds);

        if (isLate)
        {
            submission.setLate(isLate);
            submission.setLateTime(lateTime);
        }

        StudentAssessmentSubmission savedSubmission = studentAssessmentSubmissionRepository.save(submission);

        return studentAssessmentSubmissionMapper.fromStudentAssessmentSubmission(savedSubmission);
    }

    /**
     * Create a submission entity for a specific student with given document IDs
     */
    private StudentAssessmentSubmission createSubmissionForStudent(
            StudentAssessmentSubmissionRequest request,
            String studentId,
            List<String> documentIds)
    {

        StudentAssessmentSubmission submission = studentAssessmentSubmissionMapper
                .toStudentAssessmentSubmission(request);
        submission.setSubjectId(studentId);
        submission.setDocuments(documentIds);
        submission.setIsCurrent(true);

        return submission;
    }

    /**
     * Handle assessment resubmission by invalidating old submission and creating new one
     */
    private StudentAssessmentSubmissionResponse handleResubmission(StudentAssessmentSubmissionRequest request,
                                                                   boolean isLate)
    {
        log.debug("Processing assessment resubmission");

        // Invalidate existing submission
        invalidateCurrentSubmission(request.getSubjectId(), request.getModuleComponentAssessmentId());

        // Create new submission
        return createIndividualSubmission(request, isLate);
    }

    /**
     * Invalidate the current submission for a student/group and assessment
     */
    private void invalidateCurrentSubmission(String subjectId, String assessmentId)
    {
        StudentAssessmentSubmission existingSubmission = studentAssessmentSubmissionRepository
                .findBySubjectIdAndModuleComponentAssessmentIdAndIsCurrentTrue(subjectId, assessmentId);

        if (existingSubmission != null)
        {
            existingSubmission.setIsCurrent(false);
            studentAssessmentSubmissionRepository.save(existingSubmission);
            log.debug("Invalidated previous submission for subject: {} and assessment: {}", subjectId, assessmentId);
        }
    }

    /**
     * Save attachments and return document IDs, or empty list if no attachments
     */
    private List<String> saveAttachmentsIfPresent(List<MultipartFile> documents)
    {
        if (documents == null || documents.isEmpty())
        {
            return List.of();
        }
        return saveAttachments(documents);
    }

    /**
     * Save submission attached documents to storage and return the list of generated IDs
     */
    private List<String> saveAttachments(List<MultipartFile> documents)
    {
        ObjectListStorageRequest storageRequest = ObjectListStorageRequest.builder()
                .bucketName(ObjectUploadPath.Course.Assignment.BASE)
                .objectPath(ObjectUploadPath.Course.Assignment.SUBMISSION_INDIVIDUAL)
                .attachedFiles(documents)
                .build();

        List<DocumentResponse> responses = documentClient.uploadMultipleObject(storageRequest).getBody();

        if (responses == null || responses.isEmpty())
        {
            log.error("Failed to upload files for submission");
            throw new RuntimeException("File upload failed");
        }

        return responses.stream()
                .map(DocumentResponse::getId)
                .collect(Collectors.toList());
    }

    /**
     * Check if this is a resubmission (existing submission exists)
     */
    private boolean isResubmission(StudentAssessmentSubmissionRequest request)
    {
        return studentAssessmentSubmissionRepository
                .existsByModuleComponentAssessmentIdAndSubjectIdAndIsCurrentTrue(
                        request.getModuleComponentAssessmentId(),
                        request.getSubjectId());
    }

    /**
     * Check if the assessment is a group assessment
     */
    private boolean isGroupAssessment(ModuleComponentAssessmentResponse assessment)
    {
        return AssessmentMode.GROUP.toString().equals(assessment.getMode());
    }

    /**
     * Validate submission request and return the assessment
     */
    private ModuleComponentAssessmentResponse validateAndGetAssessment(StudentAssessmentSubmissionRequest request)
    {
        // Validate assessment exists
        ModuleComponentAssessmentResponse assessment = moduleComponentAssessmentService
                .getById(request.getModuleComponentAssessmentId());

        if (assessment == null)
        {
            log.error("ModuleComponentAssessment {} not found", request.getModuleComponentAssessmentId());
            throw new EntityNotFoundException("Assessment to submit to not found");
        }

        // Validate subject (student or group) exists based on assessment mode
        validateSubjectExists(request.getSubjectId(), assessment);

        return assessment;
    }

    /**
     * Validate that the subject (student or group) exists based on assessment mode
     */
    private void validateSubjectExists(String subjectId, ModuleComponentAssessmentResponse assessment)
    {
        if (AssessmentMode.INDIVIDUAL.toString().equals(assessment.getMode()))
        {
            validateStudentExists(subjectId);
        }
        else if (AssessmentMode.GROUP.toString().equals(assessment.getMode()))
        {
            validateGroupExists(subjectId);
        }
        else
        {
            throw new IllegalArgumentException("Unknown assessment mode: " + assessment.getMode());
        }
    }

    /**
     * Validate that a student exists
     */
    private void validateStudentExists(String studentId)
    {
        if (studentService.getEntity(UUID.fromString(studentId)).isEmpty())
        {
            log.error("Student {} not found", studentId);
            throw new EntityNotFoundException("Student making submission not found");
        }
        log.debug("Student {} validated successfully", studentId);
    }

    /**
     * Validate that a group exists
     */
    private void validateGroupExists(String groupId)
    {
        if (intakeAssessmentGroupService.getEntity(groupId).isEmpty())
        {
            log.error("Group {} not found", groupId);
            throw new EntityNotFoundException("Group making submission not found");
        }
        log.debug("Group {} validated successfully", groupId);
    }

    /**
     * Create pageable object with sorting
     */
    private Pageable createPageable(int page, int size, String sortBy, String order)
    {
        Sort sort = "desc".equalsIgnoreCase(order)
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        return PageRequest.of(page, size, sort);
    }

    /**
     * Convert page of entities to PagedResponse
     */
    private PagedResponse<StudentAssessmentSubmissionResponse> convertToPagedResponse(
            Page<StudentAssessmentSubmission> submissions)
    {

        List<StudentAssessmentSubmissionResponse> responses = submissions.getContent().stream()
                .map(studentAssessmentSubmissionMapper::fromStudentAssessmentSubmission)
                .collect(Collectors.toList());

        return new PagedResponse<>(
                responses,
                submissions.getNumber(),
                submissions.getSize(),
                submissions.getTotalElements(),
                submissions.getTotalPages(),
                submissions.isLast()
        );
    }

    /**
     * Fetch all student assessment submissions with pagination and sorting
     */
    public PagedResponse<StudentAssessmentSubmissionResponse> getAllStudentAssessmentSubmissions(
            int page, int size, String sortBy, String order)
    {

        log.debug("Getting all studentAssessmentSubmissions");

        Pageable pageable = createPageable(page, size, sortBy, order);
        Page<StudentAssessmentSubmission> submissions = studentAssessmentSubmissionRepository.findAll(pageable);

        return convertToPagedResponse(submissions);
    }

    /**
     * Fetch a student assessment submission by ID
     */
    public StudentAssessmentSubmissionResponse getStudentAssessmentSubmission(String submissionId)
    {
        log.debug("Finding studentAssessmentSubmission by id: {}", submissionId);

        StudentAssessmentSubmission submission = getEntity(submissionId)
                .orElseThrow(() ->
                {
                    log.warn("Could not find submission: {}", submissionId);
                    return new EntityNotFoundException("Student assessment submission not found");
                });

        log.debug("Successfully found student assessment submission: {}", submission.getId());
        return studentAssessmentSubmissionMapper.fromStudentAssessmentSubmission(submission);
    }

    /**
     * Delete a student assessment submission permanently
     */
    public boolean deleteStudentAssessmentSubmission(String submissionId)
    {
        log.warn("Permanently deleting student assessment submission: {}", submissionId);

        StudentAssessmentSubmission submission = getEntity(submissionId)
                .orElseThrow(() ->
                {
                    log.warn("Could not find submission to delete: {}", submissionId);
                    return new EntityNotFoundException("Submission to delete not found");
                });

        studentAssessmentSubmissionRepository.delete(submission);
        log.info("Successfully deleted submission: {}", submissionId);
        return true;
    }

    /**
     * Utility function for retrieving a student assessment submission entity
     */
    public Optional<StudentAssessmentSubmission> getEntity(String submissionId)
    {
        return studentAssessmentSubmissionRepository.findById(submissionId);
    }

    /**
     * Fetch all submissions made by a particular student
     */
    public PagedResponse<StudentAssessmentSubmissionResponse> getSubmissionsByStudent(
            String subjectId, int page, int size, String sortBy, String order)
    {

        log.debug("Getting submissions by student id: {}", subjectId);

        // Validate student exists
        validateStudentExists(subjectId);

        Pageable pageable = createPageable(page, size, sortBy, order);
        Page<StudentAssessmentSubmission> submissions = studentAssessmentSubmissionRepository
                .findBySubjectId(subjectId, pageable);

        return convertToPagedResponse(submissions);
    }

    /**
     * Fetch all submissions related to a particular component assessment
     */
    public PagedResponse<StudentAssessmentSubmissionResponse> getSubmissionsByComponentAssessment(
            String componentAssessmentId, int page, int size, String sortBy, String order)
    {

        log.debug("Getting submissions by component assessment id: {}", componentAssessmentId);

        // Validate assessment exists
        validateAssessmentExists(componentAssessmentId);

        Pageable pageable = createPageable(page, size, sortBy, order);
        Page<StudentAssessmentSubmission> submissions = studentAssessmentSubmissionRepository
                .findByModuleComponentAssessmentIdAndIsCurrentTrue(componentAssessmentId, pageable);

        return convertToPagedResponse(submissions);
    }

    /**
     * Fetch submission of a particular component assessment made by a particular student
     */
    public StudentAssessmentSubmissionResponse getSubmissionsByStudentAndAssessment(
            String subjectId, String assessmentId)
    {

        log.debug("Getting submission by student: {} and assessment: {}", subjectId, assessmentId);

        // Validate both student and assessment exist
        validateStudentExists(subjectId);
        validateAssessmentExists(assessmentId);

        StudentAssessmentSubmission submission = studentAssessmentSubmissionRepository
                .findBySubjectIdAndModuleComponentAssessmentId(subjectId, assessmentId);

        if (submission == null)
        {
            throw new EntityNotFoundException(
                    "No submission found for student: " + subjectId + " and assessment: " + assessmentId);
        }

        return studentAssessmentSubmissionMapper.fromStudentAssessmentSubmission(submission);
    }

    /**
     * Grade a particular assessment submission
     */
    public StudentAssessmentSubmissionResponse gradeSubmission(String submissionId, Double grade)
    {
        log.debug("Grading submission: {} with grade: {}", submissionId, grade);

        StudentAssessmentSubmission submission = studentAssessmentSubmissionRepository
                .findByIdAndIsCurrentTrue(submissionId)
                .orElseThrow(() ->
                {
                    log.error("Current submission {} not found", submissionId);
                    return new EntityNotFoundException("Current submission not found");
                });

        // Update submission with grade and corrector
        submission.setGrade(grade);

        // the correctedBy will be populated automatically

        StudentAssessmentSubmission savedSubmission = studentAssessmentSubmissionRepository.save(submission);

        log.info("Successfully graded submission: {} with grade: {}", submissionId, grade);
        return studentAssessmentSubmissionMapper.fromStudentAssessmentSubmission(savedSubmission);
    }

    /**
     * Get all submissions for a specific assessment that need grading
     */
    public PagedResponse<StudentAssessmentSubmissionResponse> getUngradedSubmissions(
            String componentAssessmentId, int page, int size, String sortBy, String order)
    {

        log.debug("Getting ungraduated submissions for assessment: {}", componentAssessmentId);

        validateAssessmentExists(componentAssessmentId);

        Pageable pageable = createPageable(page, size, sortBy, order);
        Page<StudentAssessmentSubmission> submissions = studentAssessmentSubmissionRepository
                .findByModuleComponentAssessmentIdAndIsCurrentTrueAndGradeIsNull(componentAssessmentId, pageable);

        return convertToPagedResponse(submissions);
    }

    /**
     * Validate that an assessment exists
     */
    private void validateAssessmentExists(String assessmentId)
    {
        ModuleComponentAssessmentResponse assessment = moduleComponentAssessmentService.getById(assessmentId);
        if (assessment == null)
        {
            log.error("Component assessment {} not found", assessmentId);
            throw new EntityNotFoundException("Component assessment not found");
        }
        log.debug("Assessment {} validated successfully", assessmentId);
    }

    /**
     * Get submission statistics for an assessment
     */
    public AssessmentSubmissionStats getSubmissionStats(String componentAssessmentId)
    {
        log.debug("Getting submission statistics for assessment: {}", componentAssessmentId);

        validateAssessmentExists(componentAssessmentId);

        long totalSubmissions = studentAssessmentSubmissionRepository
                .countByModuleComponentAssessmentIdAndIsCurrentTrue(componentAssessmentId);

        long gradedSubmissions = studentAssessmentSubmissionRepository
                .countByModuleComponentAssessmentIdAndIsCurrentTrueAndGradeIsNotNull(componentAssessmentId);

        long pendingSubmissions = totalSubmissions - gradedSubmissions;

        // Calculate average grade if there are graded submissions
        Double averageGrade = null;
        if (gradedSubmissions > 0)
        {
            averageGrade = studentAssessmentSubmissionRepository
                    .getAverageGradeByModuleComponentAssessmentId(componentAssessmentId);
        }

        return AssessmentSubmissionStats.builder()
                .totalSubmissions(totalSubmissions)
                .gradedSubmissions(gradedSubmissions)
                .pendingSubmissions(pendingSubmissions)
                .averageGrade(averageGrade)
                .build();
    }
}