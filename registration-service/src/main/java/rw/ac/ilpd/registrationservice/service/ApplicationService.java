package rw.ac.ilpd.registrationservice.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import rw.ac.ilpd.registrationservice.mapper.AcademicBackgroundMapper;
import rw.ac.ilpd.registrationservice.mapper.ApplicationDeferringCommentMapper;
import rw.ac.ilpd.registrationservice.mapper.ApplicationDocumentSubmissionMapper;
import rw.ac.ilpd.registrationservice.mapper.ApplicationMapper;
import rw.ac.ilpd.registrationservice.mapper.ApplicationSponsorMapper;
import rw.ac.ilpd.registrationservice.mapper.PaginationMapper;
import rw.ac.ilpd.registrationservice.model.nosql.document.AcademicBackground;
import rw.ac.ilpd.registrationservice.model.nosql.document.Application;
import rw.ac.ilpd.registrationservice.model.nosql.document.ApplicationDeferringComment;
import rw.ac.ilpd.registrationservice.model.nosql.document.ApplicationDocumentSubmission;
import rw.ac.ilpd.registrationservice.model.nosql.document.ApplicationSponsor;
import rw.ac.ilpd.registrationservice.projection.ApplicationDocumentStatistics;
import rw.ac.ilpd.registrationservice.projection.ApplicationStatusCount;
import rw.ac.ilpd.registrationservice.projection.DailyApplicationCount;
import rw.ac.ilpd.registrationservice.projection.IntakeApplicationCount;
import rw.ac.ilpd.registrationservice.repository.AcademicBackgroundRepository;
import rw.ac.ilpd.registrationservice.repository.ApplicationDeferringCommentRepository;
import rw.ac.ilpd.registrationservice.repository.ApplicationDocumentSubmissionRepository;
import rw.ac.ilpd.registrationservice.repository.ApplicationRepository;
import rw.ac.ilpd.registrationservice.repository.ApplicationSponsorRepository;
import rw.ac.ilpd.sharedlibrary.dto.application.AcademicBackgroundResponse;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationAnalyticsResponse;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationDeferringCommentResponse;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationDocumentSubmissionRequest;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationDocumentSubmissionResponse;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationRequest;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationResponse;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationSearchRequest;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationSponsorResponse;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationStatusUpdateRequest;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationSummaryResponse;
import rw.ac.ilpd.sharedlibrary.dto.application.BatchApplicationRequest;
import rw.ac.ilpd.sharedlibrary.dto.application.BatchError;
import rw.ac.ilpd.sharedlibrary.dto.application.BatchResponse;
import rw.ac.ilpd.sharedlibrary.dto.application.DocumentVerificationStatusUpdateRequest;
import rw.ac.ilpd.sharedlibrary.dto.application.DocumentVersion;
import rw.ac.ilpd.sharedlibrary.dto.application.PagedResponse;
import rw.ac.ilpd.sharedlibrary.enums.ApplicationStatus;
import rw.ac.ilpd.sharedlibrary.enums.DocumentVerificationStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service responsible for managing application-related operations. This includes creating,
 * updating, retrieving, and deleting applications, as well as managing related data such as
 * document submissions, sponsors, and academic backgrounds.
 * <p>
 * This class is a central point for application business logic, interacting with repositories
 * and mappers to perform the required operations.
 */
@Service
@Transactional
@Validated
public class ApplicationService {

    /**
     * A private final instance of ApplicationRepository used for accessing
     * and managing the data related to applications within the system.
     * This repository acts as a layer to perform data persistence
     * operations such as create, read, update, and delete (CRUD).
     */
    private final ApplicationRepository applicationRepository;
    private static final Logger logger = LoggerFactory.getLogger(ApplicationService.class);
    /**
     * An instance of ApplicationMapper used for mapping application data
     * between different layers or components within the application.
     * This is immutable and initialized as a final field.
     */
    private final ApplicationMapper applicationMapper;
    /**
     * A repository instance for managing and persisting comments.
     * This repository supports deferred operations, allowing the application to handle
     * comment-related data storage and retrieval efficiently.
     */
    private final ApplicationDeferringCommentRepository commentRepository;
    /**
     * This variable represents an instance of {@code ApplicationDeferringCommentMapper},
     * which is responsible for handling mapping or transformation of comments while deferring
     * certain application-specific logic or behavior.
     * <p>
     * It is declared as {@code final}, indicating that the reference cannot be reassigned
     * after initialization, ensuring its immutability within the containing class.
     */
    private final ApplicationDeferringCommentMapper commentMapper;
    /**
     * Repository responsible for handling operations related to document submissions
     * within the application. Provides an abstraction layer for database interactions
     * related to the management, retrieval, and storage of application documents.
     */
    private final ApplicationDocumentSubmissionRepository documentRepository;
    /**
     * An instance of ApplicationSponsorRepository used to access and manage sponsor-related data.
     * This variable is marked as final, indicating its reference cannot be changed after initialization.
     * It serves as the data repository for operations involving sponsors within the application.
     */
    private final ApplicationSponsorRepository sponsorRepository;
    /**
     * Repository interface for performing CRUD operations and queries
     * related to academic background entities.
     * <p>
     * This variable is used to interact with the persistence layer that manages
     * academic background data. It typically provides methods for saving, retrieving,
     * updating, and deleting academic background records in the database.
     */
    private final AcademicBackgroundRepository academicBackgroundRepository;
    /**
     * A mapper that is responsible for transforming and transferring
     * data related to application document submissions. This variable
     * provides an interface to handle data mapping between different
     * layers or modules within the application, ensuring consistency
     * and separation of concerns in the processing of document
     * submission data.
     */
    private final ApplicationDocumentSubmissionMapper documentMapper;
    /**
     * A final instance of ApplicationSponsorMapper used for mapping
     * ApplicationSponsor data objects between different layers or formats.
     * The sponsorMapper is responsible for handling the transformation
     * logic required to process ApplicationSponsor-related information.
     */
    private final ApplicationSponsorMapper sponsorMapper;
    /**
     * A final instance of AcademicBackgroundMapper used to map or transform academic background data
     * between different layers or representations in the application. The mapping process ensures consistency
     * and simplifies data handling when converting academic-related information.
     */
    private final AcademicBackgroundMapper academicMapper;
    /**
     * A final variable that holds an instance of the PaginationMapper class.
     * It is used for mapping pagination-related data between different layers or
     * converting pagination-specific information such as page numbers, sizes,
     * or offsets into a structured format.
     */
    private final PaginationMapper paginationMapper;

    /**
     * Constructs an instance of ApplicationService with the required dependencies.
     *
     * @param applicationRepository        the repository for managing applications
     * @param applicationMapper            the mapper for application entities and DTOs
     * @param commentRepository            the repository for managing deferring comments
     * @param commentMapper                the mapper for deferring comment entities and DTOs
     * @param documentRepository           the repository for managing document submissions
     * @param sponsorRepository            the repository for managing application sponsors
     * @param academicBackgroundRepository the repository for managing academic backgrounds
     * @param documentMapper               the mapper for document submission entities and DTOs
     * @param sponsorMapper                the mapper for sponsor entities and DTOs
     * @param academicMapper               the mapper for academic background entities and DTOs
     * @param paginationMapper             the mapper for pagination details
     */
    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository, ApplicationMapper applicationMapper,
                              ApplicationDeferringCommentRepository commentRepository,
                              ApplicationDeferringCommentMapper commentMapper,
                              ApplicationDocumentSubmissionRepository documentRepository,
                              ApplicationSponsorRepository sponsorRepository,
                              AcademicBackgroundRepository academicBackgroundRepository,
                              ApplicationDocumentSubmissionMapper documentMapper,
                              ApplicationSponsorMapper sponsorMapper, AcademicBackgroundMapper academicMapper,
                              PaginationMapper paginationMapper) {
        this.applicationRepository = applicationRepository;
        this.commentRepository = commentRepository;
        this.applicationMapper = applicationMapper;
        this.commentMapper = commentMapper;
        this.documentRepository = documentRepository;
        this.sponsorRepository = sponsorRepository;
        this.academicBackgroundRepository = academicBackgroundRepository;
        this.documentMapper = documentMapper;
        this.sponsorMapper = sponsorMapper;
        this.academicMapper = academicMapper;
        this.paginationMapper = paginationMapper;
    }

    /**
     * Creates a new application based on the provided ApplicationRequest.
     * Checks if the user already has an existing application for the specified intake,
     * and throws an exception if the application already exists. Otherwise, it maps
     * the request to an Application entity, saves it to the repository, and maps
     * the saved entity to an ApplicationResponse.
     *
     * @param request the application request containing the details of the application to be created
     * @return the created application represented as an ApplicationResponse
     * @throws IllegalArgumentException if the user already has an existing application for the specified intake
     */
    public ApplicationResponse create(@Valid ApplicationRequest request) {
        UUID userId = UUID.fromString(request.getUserId());
        UUID intakeId = UUID.fromString(request.getIntakeId());

        // Check if user already has an application for this intake
        if (applicationRepository.existsByUserIdAndIntakeId(userId, intakeId)) {
            throw new IllegalArgumentException("User already has an application for this intake");
        }

        Application application = applicationMapper.toEntity(request);
        Application saved = applicationRepository.save(application);
        return applicationMapper.toResponse(saved);
    }

    /**
     * Finds an application by its unique identifier and returns a basic response containing
     * essential application details without nested objects.
     *
     * @param id the unique identifier of the application to find
     * @return an {@code ApplicationResponse} object containing the basic details of the application
     */
    @Transactional(readOnly = true)
    public ApplicationResponse findById(String id) {
        Application application = findApplicationById(id);

        // Return basic response without nested objects
        return ApplicationResponse.builder().id(application.getId()).userId(application.getUserId().toString())
                .intakeId(application.getIntakeId().toString())
                .status(application.getStatus().name()).createdAt(application.getCreatedAt())
                .build();
    }

    /**
     * Retrieves an application by its ID along with additional details such as document submissions,
     * sponsors, academic background, and optionally comments.
     *
     * @param id              the unique identifier of the application to be retrieved
     * @param includeComments a flag indicating whether to include comments for the document submissions
     * @return an {@code ApplicationResponse} object containing the application data along with its nested details
     */
    @Transactional(readOnly = true)
    public ApplicationResponse findByIdWithDetails(String id, boolean includeComments) {
        Application application = findApplicationById(id);

        // Load document submissions by IDs from application's document list
        List<ApplicationDocumentSubmissionResponse> documentResponses = new ArrayList<>();
        if (application.getApplicationDocuments() != null && !application.getApplicationDocuments().isEmpty()) {
            List<ApplicationDocumentSubmission> submissions = documentRepository.findAllById(
                    application.getApplicationDocuments());

            documentResponses = submissions.stream().map(submission -> {
                List<ApplicationDeferringCommentResponse> commentResponses = new ArrayList<>();
                if (includeComments) {
                    List<ApplicationDeferringComment> comments =
                            commentRepository.findBySubmittedDocumentIdOrderByCreatedAtDesc(
                                    submission.getId());
                    commentResponses = commentMapper.toResponseList(comments);
                } else {
                    int commentCount = (int) commentRepository.countBySubmittedDocumentId(submission.getId());
                    // Set comment count but leave comments empty
                }

                // Use context mapper with application ID
                return documentMapper.toResponseWithContext(submission, id, commentResponses);
            }).collect(Collectors.toList());
        }

        // Load sponsors
        List<ApplicationSponsor> sponsors = sponsorRepository.findByApplicationId(id);
        List<ApplicationSponsorResponse> sponsorResponses = sponsorMapper.toResponseListWithContext(sponsors, id);

        // UPDATED: Load academic backgrounds for the user (not application)
        // Get all academic backgrounds for the user who owns this application
        List<AcademicBackground> academicBackgrounds = academicBackgroundRepository.findByUserId(application.getUserId());
        List<AcademicBackgroundResponse> academicResponses = academicBackgrounds.stream()
                .map(academicMapper::toResponse)
                .collect(Collectors.toList());

        // Calculate summary fields using primitive types to avoid null values
        int totalDocuments = documentResponses.size();
        int totalSponsors = sponsorResponses.size();
        boolean hasAcademicBackground = !academicResponses.isEmpty();
        int totalAcademicBackgrounds = academicResponses.size();

        // UPDATED: Return with multiple academic backgrounds instead of single one
        return ApplicationResponse.builder()
                .id(application.getId())
                .userId(application.getUserId().toString())
                .intakeId(application.getIntakeId().toString())
                .status(application.getStatus().name())
                .createdAt(application.getCreatedAt())
                .documentSubmissions(documentResponses) // Populated nested objects
                .sponsors(sponsorResponses)
                .academicBackgrounds(academicResponses) // CHANGED: Now returns list of academic backgrounds
                .totalDocuments(totalDocuments) // Summary fields
                .totalSponsors(totalSponsors)
                .hasAcademicBackground(hasAcademicBackground)
                .totalAcademicBackgrounds(totalAcademicBackgrounds) // NEW: Count of academic backgrounds
                .build();
    }
    /**
     * Updates an existing application with the provided details.
     *
     * @param id      the unique identifier of the application to be updated
     * @param request the new application data encapsulated in an {@code ApplicationRequest} object
     * @return an {@code ApplicationResponse} object representing the updated application information
     * @throws IllegalArgumentException if the user associated with the request already has an application for the
     * specified intake
     */
    public ApplicationResponse update(String id, @Valid ApplicationRequest request) {
        Application existing = findApplicationById(id);

        UUID userId = UUID.fromString(request.getUserId());
        UUID intakeId = UUID.fromString(request.getIntakeId());

        // Check if user already has another application for this intake
        Optional<Application> existingApplication = applicationRepository.findByUserIdAndIntakeId(userId, intakeId);
        if (existingApplication.isPresent() && !existingApplication.get().getId().equals(id)) {
            throw new IllegalArgumentException("User already has another application for this intake");
        }

        Application updated = applicationMapper.updateEntity(existing, request);
        Application saved = applicationRepository.save(updated);
        return applicationMapper.toResponse(saved);
    }

    /**
     * Updates the status of an existing application based on the provided application ID and status update request.
     *
     * @param id      the unique identifier of the application to update
     * @param request the object containing the new status details for the application
     * @return an ApplicationResponse object representing the updated application details
     */
    public ApplicationResponse updateStatus(String id, ApplicationStatusUpdateRequest request) {
        Application existing = findApplicationById(id);

        ApplicationStatus newStatus = ApplicationStatus.valueOf(request.getStatus().toUpperCase());
        existing.setStatus(newStatus);

        Application saved = applicationRepository.save(existing);
        return applicationMapper.toResponse(saved);
    }

    /**
     * Deletes an application and all related data including application documents,
     * comments associated with those documents, sponsors, and academic backgrounds.
     *
     * @param id The unique identifier of the application to be deleted.
     */
    public void delete(String id) {
        Application application = findApplicationById(id);

        // Delete document submissions referenced by this application
        if (application.getApplicationDocuments() != null) {
            for (String documentId : application.getApplicationDocuments()) {
                // Delete comments for each document first
                commentRepository.deleteBySubmittedDocumentId(documentId);
                // Then delete the document submission
                documentRepository.deleteById(documentId);
            }
        }

        // Delete other related data
        sponsorRepository.deleteByApplicationId(id);

        // Finally delete the application
        applicationRepository.deleteById(id);
    }

    /**
     * Adds a document submission to an existing application.
     * This method validates the application existence, creates a new document submission
     * based on the provided request, updates the application's document list with
     * the newly created document, and saves the updated application.
     *
     * @param applicationId the unique identifier of the application to which the document will be added
     * @param request       the details of the document submission to be added
     * @return the updated application response after adding the document submission
     */
    public ApplicationResponse addDocumentSubmission(String applicationId,
                                                     ApplicationDocumentSubmissionRequest request) {
        // 1. Validate application exists
        Application application = findApplicationById(applicationId);

        // 2. Create document submission
        ApplicationDocumentSubmission submission = documentMapper.toEntity(request);
        ApplicationDocumentSubmission savedSubmission = documentRepository.save(submission);

        // 3. Add document ID to application's document list
        List<String> documents = application.getApplicationDocuments();
        if (documents == null) {
            documents = new ArrayList<>();
        }
        documents.add(savedSubmission.getId());
        application.setApplicationDocuments(documents);

        // 4. Save updated application
        Application savedApplication = applicationRepository.save(application);

        return applicationMapper.toResponse(savedApplication);
    }

    /**
     * Removes a document submission from an existing application.
     * This method validates the application existence, checks if the document exists,
     * removes the document ID from the application's document list, deletes any related
     * comments, deletes the document submission, and saves the updated application.
     *
     * @param applicationId the unique identifier of the application from which the document will be removed
     * @param documentSubmissionId the unique identifier of the document submission to be removed
     * @throws EntityNotFoundException if the application or document submission is not found
     */
    @Transactional
    public void removeDocumentSubmission(String applicationId, String documentSubmissionId) {
        // 1. Get application
        Application application = findApplicationById(applicationId);

        // 2. Check if document exists
        boolean documentExists = documentRepository.existsById(documentSubmissionId);
        if (!documentExists) {
            throw new EntityNotFoundException("Document submission not found with id: " + documentSubmissionId);
        }

        // 3. Check if document is in application's list
        List<String> documents = application.getApplicationDocuments();
        if (documents == null || !documents.contains(documentSubmissionId)) {
            throw new IllegalArgumentException("Document submission with id: " + documentSubmissionId + 
                " is not associated with application: " + applicationId);
        }

        // 4. Remove document ID from application's list
        documents.remove(documentSubmissionId);
        application.setApplicationDocuments(documents);

        // 5. Delete related comments first
        commentRepository.deleteBySubmittedDocumentId(documentSubmissionId);

        // 6. Delete document submission
        documentRepository.deleteById(documentSubmissionId);

        // 7. Save updated application
        applicationRepository.save(application);
    }

    /**
     * Retrieves a list of all document submissions.
     *
     * @return a list of ApplicationDocumentSubmissionResponse representing all document submissions
     */
    // ✅ ADD - Get all document submissions
    @Transactional(readOnly = true)
    public List<ApplicationDocumentSubmissionResponse> getAllDocumentSubmissions() {
        List<ApplicationDocumentSubmission> submissions = documentRepository.findAll();
        return documentMapper.toResponseList(submissions);
    }

    /**
     * Retrieves a paginated and sorted list of document submissions.
     *
     * @param page          the page number to retrieve (zero-based index)
     * @param size          the number of items per page
     * @param sortBy        the field by which to sort the results
     * @param sortDirection the direction of sorting, either "asc" for ascending or "desc" for descending
     * @return a {@code PagedResponse} containing the paginated list of document submissions and metadata
     */
    @Transactional(readOnly = true)
    public PagedResponse<ApplicationDocumentSubmissionResponse> getAllDocumentSubmissionsPaged(int page, int size,
                                                                                               String sortBy,
                                                                                               String sortDirection) {
        Pageable pageable = createPageable(page, size, sortBy, sortDirection);
        Page<ApplicationDocumentSubmission> submissionPage = documentRepository.findAll(pageable);

        List<ApplicationDocumentSubmissionResponse> responses = documentMapper.toResponseList(
                submissionPage.getContent());
        return paginationMapper.toPagedResponse(responses, submissionPage.getTotalElements(), page, size);
    }

    /**
     * Retrieves a specific document submission by its unique identifier.
     *
     * @param documentId the unique identifier of the document submission to retrieve
     * @return an ApplicationDocumentSubmissionResponse containing the details of the document submission
     * @throws EntityNotFoundException if no document submission is found with the given ID
     */
    // ✅ ADD - Get document submission by ID
    @Transactional(readOnly = true)
    public ApplicationDocumentSubmissionResponse getDocumentSubmissionById(String documentId) {
        ApplicationDocumentSubmission submission = documentRepository.findById(documentId)
                .orElseThrow(() -> new EntityNotFoundException("Document submission not found with id: " + documentId));
        return documentMapper.toResponse(submission);
    }
    /**
     *
     */
    // ✅ ADD - Get documents by status
    @Transactional(readOnly = true)
    public List<ApplicationDocumentSubmissionResponse> getDocumentSubmissionsByStatus(String status) {
        DocumentVerificationStatus verificationStatus = DocumentVerificationStatus.valueOf(status.toUpperCase());
        List<ApplicationDocumentSubmission> submissions = documentRepository.findByVerificationStatus(
                verificationStatus);
        return documentMapper.toResponseList(submissions);
    }

    /**
     * Retrieves the list of application documents associated with the specified application ID.
     * Converts the retrieved documents into a response format.
     *
     * @param applicationId the ID of the application whose documents are to be retrieved
     * @return a list of ApplicationDocumentSubmissionResponse objects containing the details
     * of the application documents; returns an empty list if no documents are found
     */
    // ✅ ADD - Get application documents
    @Transactional(readOnly = true)
    public List<ApplicationDocumentSubmissionResponse> getApplicationDocuments(String applicationId) {
        Application application = findApplicationById(applicationId);

        if (application.getApplicationDocuments() == null || application.getApplicationDocuments().isEmpty()) {
            return new ArrayList<>();
        }

        List<ApplicationDocumentSubmission> submissions = documentRepository.findAllById(
                application.getApplicationDocuments());

        return submissions.stream()
                .map(submission -> documentMapper.toResponseWithContext(submission, applicationId, new ArrayList<>()))
                .collect(Collectors.toList());
    }

    /**
     *
     */
    @Transactional(readOnly = true)
    public List<ApplicationResponse> findAll() {
        List<Application> applications = applicationRepository.findAll();
        return applications.stream().map(app -> findById(app.getId())).collect(Collectors.toList());
    }

    /**
     * Retrieves a list of all applications along with their detailed information.
     *
     * @return a list of ApplicationResponse objects containing detailed information for each application.
     */
    @Transactional(readOnly = true)
    public List<ApplicationResponse> findAllWithDetails() {
        List<Application> applications = applicationRepository.findAll();
        return applications.stream().map(app -> findByIdWithDetails(app.getId(), false)).collect(Collectors.toList());
    }

    /**
     * Retrieves a list of application responses associated with a specific user identifier.
     * Depending on the flag, it may include detailed information.
     *
     * @param userId         the unique identifier of the user whose applications are to be retrieved
     * @param includeDetails a flag indicating whether to include detailed information in the response
     * @return a list of {@link ApplicationResponse} objects associated with the specified user
     */
    @Transactional(readOnly = true)
    public List<ApplicationResponse> findByUserId(String userId, boolean includeDetails) {
        UUID userUuid = UUID.fromString(userId);
        List<Application> applications = applicationRepository.findByUserId(userUuid);

        if (includeDetails) {
            return applications.stream().map(app -> findByIdWithDetails(app.getId(), false))
                    .collect(Collectors.toList());
        } else {
            return applications.stream().map(app -> findById(app.getId())).collect(Collectors.toList());
        }
    }

    /**
     * Finds and retrieves a list of applications based on their status.
     * If the includeDetails flag is true, the results include detailed information.
     * Otherwise, only basic application information is returned.
     *
     * @param status         the status of the applications to filter (e.g., "PENDING", "APPROVED").
     * @param includeDetails a flag indicating whether to include detailed application information in the results.
     * @return a list of {@code ApplicationResponse} objects matching the specified status, optionally including
     * details.
     */
    @Transactional(readOnly = true)
    public List<ApplicationResponse> findByStatus(String status, boolean includeDetails) {
        ApplicationStatus applicationStatus = ApplicationStatus.valueOf(status.toUpperCase());
        List<Application> applications = applicationRepository.findByStatus(applicationStatus);

        if (includeDetails) {
            return applications.stream().map(app -> findByIdWithDetails(app.getId(), false))
                    .collect(Collectors.toList());
        } else {
            return applications.stream().map(app -> findById(app.getId())).collect(Collectors.toList());
        }
    }

    /**
     * Retrieves a list of application responses based on the provided intake ID.
     * Optionally includes detailed information for each application response.
     *
     * @param intakeId       the intake ID as a String. Should be a valid UUID format.
     * @param includeDetails a boolean indicating whether detailed application responses should be included in the
     *                       result.
     * @return a list of ApplicationResponse objects corresponding to the applications associated with the specified
     * intake ID.
     */
    @Transactional(readOnly = true)
    public List<ApplicationResponse> findByIntakeId(String intakeId, boolean includeDetails) {
        UUID intakeUuid = UUID.fromString(intakeId);
        List<Application> applications = applicationRepository.findByIntakeId(intakeUuid);

        if (includeDetails) {
            return applications.stream().map(app -> findByIdWithDetails(app.getId(), false))
                    .collect(Collectors.toList());
        } else {
            return applications.stream().map(app -> findById(app.getId())).collect(Collectors.toList());
        }
    }

    /**
     * Retrieves a paginated list of applications, optionally including detailed information.
     *
     * @param page           the page number to retrieve (zero-based index)
     * @param size           the number of elements per page
     * @param sortBy         the attribute by which to sort the results
     * @param sortDirection  the direction of sorting, either "asc" for ascending or "desc" for descending
     * @param includeDetails whether to include detailed information for each application
     * @return a {@code PagedResponse<ApplicationResponse>} containing the paginated list of applications
     */
    @Transactional(readOnly = true)
    public PagedResponse<ApplicationResponse> findAllPaged(int page, int size, String sortBy, String sortDirection,
                                                           boolean includeDetails) {
        return findAllPaged(page, size, sortBy, sortDirection, includeDetails, false);
    }
    
    /**
     * Retrieves a paginated list of applications, optionally including detailed information and comments.
     *
     * @param page           the page number to retrieve (zero-based index)
     * @param size           the number of elements per page
     * @param sortBy         the attribute by which to sort the results
     * @param sortDirection  the direction of sorting, either "asc" for ascending or "desc" for descending
     * @param includeDetails whether to include detailed information for each application
     * @param includeComments whether to include comments for document submissions
     * @return a {@code PagedResponse<ApplicationResponse>} containing the paginated list of applications
     */
    @Transactional(readOnly = true)
    public PagedResponse<ApplicationResponse> findAllPaged(int page, int size, String sortBy, String sortDirection,
                                                           boolean includeDetails, boolean includeComments) {
        Pageable pageable = createPageable(page, size, sortBy, sortDirection);
        Page<Application> applicationPage = applicationRepository.findAll(pageable);

        List<ApplicationResponse> responses;
        if (includeDetails) {
            responses = applicationPage.getContent().stream().map(app -> findByIdWithDetails(app.getId(), includeComments))
                    .collect(Collectors.toList());
        } else {
            responses = applicationPage.getContent().stream().map(app -> findById(app.getId()))
                    .collect(Collectors.toList());
        }

        return paginationMapper.toPagedResponse(responses, applicationPage.getTotalElements(), page, size);
    }

    /**
     * Retrieves a paginated list of applications for the specified user ID.
     *
     * @param userId         the unique identifier of the user as a String
     * @param page           the page number to retrieve, starting from 0
     * @param size           the number of items per page
     * @param includeDetails determines whether detailed application information should be included
     * @return a paginated response containing the application data
     */
    @Transactional(readOnly = true)
    public PagedResponse<ApplicationResponse> findByUserIdPaged(String userId, int page, int size,
                                                                boolean includeDetails) {
        UUID userUuid = UUID.fromString(userId);
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Application> applicationPage = applicationRepository.findByUserId(userUuid, pageable);

        List<ApplicationResponse> responses;
        if (includeDetails) {
            responses = applicationPage.getContent().stream().map(app -> findByIdWithDetails(app.getId(), false))
                    .collect(Collectors.toList());
        } else {
            responses = applicationPage.getContent().stream().map(app -> findById(app.getId()))
                    .collect(Collectors.toList());
        }

        return paginationMapper.toPagedResponse(responses, applicationPage.getTotalElements(), page, size);
    }

    /**
     * Retrieves a paged list of applications filtered by their status.
     * The retrieved applications can include detailed information based on the includeDetails parameter.
     *
     * @param status         the status of the applications to filter by
     * @param page           the page number of the paged results to retrieve (0-based index)
     * @param size           the number of items per page
     * @param includeDetails whether to include detailed information in the response
     * @return a paged response containing the list of applications matching the specified status, along with
     * pagination metadata
     */
    @Transactional(readOnly = true)
    public PagedResponse<ApplicationResponse> findByStatusPaged(String status, int page, int size,
                                                                boolean includeDetails) {
        ApplicationStatus applicationStatus = ApplicationStatus.valueOf(status.toUpperCase());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Application> applicationPage = applicationRepository.findByStatus(applicationStatus, pageable);

        List<ApplicationResponse> responses;
        if (includeDetails) {
            responses = applicationPage.getContent().stream().map(app -> findByIdWithDetails(app.getId(), false))
                    .collect(Collectors.toList());
        } else {
            responses = applicationPage.getContent().stream().map(app -> findById(app.getId()))
                    .collect(Collectors.toList());
        }

        return paginationMapper.toPagedResponse(responses, applicationPage.getTotalElements(), page, size);
    }

    /**
     * Searches for applications based on the criteria provided in the search request. The method supports
     * optional filters such as user ID and application status, and includes a pageable result.
     *
     * @param searchRequest  an object containing the search criteria, including filters like user ID, status,
     *                       sort order, page number, and page size
     * @param includeDetails a flag indicating whether to include detailed information for each application
     *                       in the response
     * @return a pageable response containing a list of applications and metadata about the paginated results
     */
    @Transactional(readOnly = true)
    public PagedResponse<ApplicationResponse> searchApplications(ApplicationSearchRequest searchRequest,
                                                                 boolean includeDetails) {
        Pageable pageable = PageRequest.of(searchRequest.getPage(), searchRequest.getSize(),
                createSort(searchRequest.getSortBy(), searchRequest.getSortDirection()));

        Page<Application> applicationPage;

        if (searchRequest.getUserId() != null) {
            UUID userId = UUID.fromString(searchRequest.getUserId());
            if (searchRequest.getStatus() != null) {
                ApplicationStatus status = ApplicationStatus.valueOf(searchRequest.getStatus().toUpperCase());
                applicationPage = applicationRepository.findByUserIdAndStatus(userId, status, pageable);
            } else {
                applicationPage = applicationRepository.findByUserId(userId, pageable);
            }
        } else if (searchRequest.getStatus() != null) {
            ApplicationStatus status = ApplicationStatus.valueOf(searchRequest.getStatus().toUpperCase());
            applicationPage = applicationRepository.findByStatus(status, pageable);
        } else {
            applicationPage = applicationRepository.findAll(pageable);
        }

        List<ApplicationResponse> responses;
        if (includeDetails) {
            responses = applicationPage.getContent().stream().map(app -> findByIdWithDetails(app.getId(), false))
                    .collect(Collectors.toList());
        } else {
            responses = applicationPage.getContent().stream().map(app -> findById(app.getId()))
                    .collect(Collectors.toList());
        }

        return paginationMapper.toPagedResponse(responses, applicationPage.getTotalElements(), searchRequest.getPage(),
                searchRequest.getSize());
    }

    /**
     * Retrieves a list of all application summaries, ordered by creation date in descending order.
     *
     * @return a list of ApplicationSummaryResponse objects representing the summaries of all applications
     */
    @Transactional(readOnly = true)
    public List<ApplicationSummaryResponse> findAllSummary() {
        List<Application> applications = applicationRepository.findAllByOrderByCreatedAtDesc();
        return applications.stream().map(this::toSummaryResponse).collect(Collectors.toList());
    }

    /**
     * Retrieves a paginated list of application summaries.
     *
     * @param page          the current page number (zero-based index) to retrieve
     * @param size          the number of items per page
     * @param sortBy        the field by which the results should be sorted
     * @param sortDirection the direction of sorting (e.g., "asc" for ascending or "desc" for descending)
     * @return a paginated response containing application summary data
     */
    @Transactional(readOnly = true)
    public PagedResponse<ApplicationSummaryResponse> findAllSummaryPaged(int page, int size, String sortBy,
                                                                         String sortDirection) {
        Pageable pageable = createPageable(page, size, sortBy, sortDirection);
        Page<Application> applicationPage = applicationRepository.findAll(pageable);

        List<ApplicationSummaryResponse> responses = applicationPage.getContent().stream().map(this::toSummaryResponse)
                .collect(Collectors.toList());

        return paginationMapper.toPagedResponse(responses, applicationPage.getTotalElements(), page, size);
    }

    /**
     * Processes a batch of application requests and returns a response containing
     * successfully processed applications and any errors encountered during processing.
     *
     * @param batchRequest the batch request containing a list of application requests to be processed
     * @return a BatchResponse object containing the results of the batch processing, including
     * successfully processed applications and any errors that occurred
     */
    public BatchResponse<ApplicationResponse> createBatch(@Valid BatchApplicationRequest batchRequest) {
        List<ApplicationResponse> successful = new ArrayList<>();
        List<BatchError> errors = new ArrayList<>();

        for (int i = 0; i < batchRequest.getApplications().size(); i++) {
            try {
                ApplicationRequest request = batchRequest.getApplications().get(i);
                ApplicationResponse response = create(request);
                successful.add(response);
            } catch (Exception e) {
                BatchError error = BatchError.builder().index(i).message(e.getMessage())
                        .input(batchRequest.getApplications().get(i)).build();
                errors.add(error);
            }
        }

        return BatchResponse.<ApplicationResponse>builder().successful(successful).errors(errors)
                .totalProcessed(batchRequest.getApplications().size()).successCount(successful.size())
                .errorCount(errors.size()).build();
    }

    /**
     * Retrieves a list of application responses based on the provided document submission ID.
     * Optionally includes detailed information for each application response.
     *
     * @param documentSubmissionId the unique identifier of the document submission to search for
     * @param includeDetails       a flag indicating whether to include detailed information for each application
     *                             response
     * @return a list of {@code ApplicationResponse} objects matching the provided document submission ID
     */
    @Transactional(readOnly = true)
    public List<ApplicationResponse> findByDocumentSubmissionId(String documentSubmissionId, boolean includeDetails) {
        List<Application> applications = applicationRepository.findByApplicationDocumentsContaining(
                documentSubmissionId);

        if (includeDetails) {
            return applications.stream().map(app -> findByIdWithDetails(app.getId(), false))
                    .collect(Collectors.toList());
        } else {
            return applications.stream().map(app -> findById(app.getId())).collect(Collectors.toList());
        }
    }

    /**
     * Retrieves a list of applications that do not have associated documents.
     * The returned list can optionally include detailed information for each application.
     *
     * @param includeDetails a boolean flag indicating whether to include detailed information
     *                       for each application in the response. If true, additional details
     *                       are retrieved for each application; otherwise, only basic information
     *                       is included in the response.
     * @return a list of ApplicationResponse objects representing the applications without documents.
     * The content of each response depends on the value of the includeDetails parameter.
     */
    @Transactional(readOnly = true)
    public List<ApplicationResponse> findApplicationsWithoutDocuments(boolean includeDetails) {
        List<Application> applications = applicationRepository.findApplicationsWithNoDocuments();

        if (includeDetails) {
            return applications.stream().map(app -> findByIdWithDetails(app.getId(), false))
                    .collect(Collectors.toList());
        } else {
            return applications.stream().map(app -> findById(app.getId())).collect(Collectors.toList());
        }
    }

    /**
     * Finds applications that have a minimum number of documents and optionally includes detailed information.
     *
     * @param minCount       the minimum number of documents required for an application to be included in the result
     * @param includeDetails a flag indicating whether to include detailed information in the response
     * @return a list of ApplicationResponse objects matching the criteria
     */
    @Transactional(readOnly = true)
    public List<ApplicationResponse> findApplicationsWithMinimumDocuments(int minCount, boolean includeDetails) {
        List<Application> applications = applicationRepository.findApplicationsWithMinimumDocuments(minCount);

        if (includeDetails) {
            return applications.stream().map(app -> findByIdWithDetails(app.getId(), false))
                    .collect(Collectors.toList());
        } else {
            return applications.stream().map(app -> findById(app.getId())).collect(Collectors.toList());
        }
    }

    /**
     * Retrieves analytical data related to applications and documents, including counts
     * for various statuses, average documents per application, and average processing time.
     * The method performs read-only operations and consolidates the results into a response object.
     *
     * @return an {@link ApplicationAnalyticsResponse} object containing analytical information
     * about applications and documents, such as total counts for different statuses,
     * average documents per application, and average processing time in hours.
     */
    @Transactional(readOnly = true)
    public ApplicationAnalyticsResponse getAnalytics() {
        long totalApplications = applicationRepository.count();
        long pendingApplications = applicationRepository.countByStatus(ApplicationStatus.PENDING);
        long approvedApplications = applicationRepository.countByStatus(ApplicationStatus.ACCEPTED);
        long rejectedApplications = applicationRepository.countByStatus(ApplicationStatus.REJECTED);
        long deferredApplications = applicationRepository.countByStatus(ApplicationStatus.DEFERRED);

        long totalDocuments = documentRepository.count();
        long pendingDocuments = documentRepository.countByVerificationStatus(DocumentVerificationStatus.PENDING);
        long approvedDocuments = documentRepository.countByVerificationStatus(DocumentVerificationStatus.ACCEPTED);
        long rejectedDocuments = documentRepository.countByVerificationStatus(DocumentVerificationStatus.REJECTED);

        double avgDocsPerApp = totalApplications > 0 ? (double) totalDocuments / totalApplications : 0;
        double avgProcessingTime = calculateAverageProcessingTime();

        return ApplicationAnalyticsResponse.builder().totalApplications(totalApplications)
                .pendingApplications(pendingApplications).approvedApplications(approvedApplications)
                .rejectedApplications(rejectedApplications).deferredApplications(deferredApplications)
                .totalDocuments(totalDocuments).pendingDocuments(pendingDocuments).approvedDocuments(approvedDocuments)
                .rejectedDocuments(rejectedDocuments).averageDocumentsPerApplication(avgDocsPerApp)
                .averageProcessingTimeHours(avgProcessingTime).build();
    }

    /**
     * Retrieves a list of application counts grouped by intake.
     *
     * @return a list of IntakeApplicationCount objects,
     * where each object represents the count of applications for a specific intake
     */
    @Transactional(readOnly = true)
    public List<IntakeApplicationCount> getApplicationCountsByIntake() {
        return applicationRepository.countApplicationsByIntake();
    }

    /**
     * Retrieves a list of daily application counts within the specified date range.
     *
     * @param startDate the start date and time of the range for which the application counts are to be retrieved
     * @param endDate   the end date and time of the range for which the application counts are to be retrieved
     * @return a list of DailyApplicationCount objects representing the counts of applications for each day within
     * the specified date range
     */
    @Transactional(readOnly = true)
    public List<DailyApplicationCount> getApplicationCountsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return applicationRepository.countApplicationsByDateRange(startDate, endDate);
    }
    /**
     * Retrieves all applications and groups them by the count of associated documents.
     * Each group is represented with a key indicating the number of documents
     * and a value containing a list of application responses associated with that document count.
     *
     * @return a map where the key is the document count (Integer) and the value is a list of application responses
     * (List<ApplicationResponse>) that have that number of associated documents.
     */
    @Transactional(readOnly = true)
    public Map<Integer, List<ApplicationResponse>> getApplicationsGroupedByDocumentCount() {
        List<Application> applications = applicationRepository.findAll();

        return applications.stream().collect(Collectors.groupingBy(
                app -> app.getApplicationDocuments() != null ? app.getApplicationDocuments().size() : 0,
                Collectors.mapping(app -> findById(app.getId()), Collectors.toList())));
    }

    // Keep these for backward compatibility but delegate to new methods

    /**
     * Retrieves the detailed information of an entity by its unique identifier.
     * This method performs the operation in a read-only transaction.
     *
     * @param id the unique identifier of the entity to be retrieved
     * @return an ApplicationResponse object containing the entity details
     */
    @Transactional(readOnly = true)
    public ApplicationResponse findByIdWithDetails(String id) {
        return findByIdWithDetails(id, false);
    }

    /**
     * Retrieves a list of ApplicationResponse objects associated with the specified user ID.
     *
     * @param userId the unique identifier of the user for whom the applications are to be retrieved
     * @return a list of ApplicationResponse objects associated with the specified user ID
     */
    @Transactional(readOnly = true)
    public List<ApplicationResponse> findByUserId(String userId) {
        return findByUserId(userId, false);
    }

    /**
     * Finds and retrieves a list of ApplicationResponse objects filtered by their status.
     *
     * @param status the status to filter the applications by
     * @return a list of ApplicationResponse objects matching the specified status
     */
    @Transactional(readOnly = true)
    public List<ApplicationResponse> findByStatus(String status) {
        return findByStatus(status, false);
    }

    /**
     * Retrieves a list of ApplicationResponse objects associated with the specified intake ID.
     *
     * @param intakeId the identifier of the intake for which the ApplicationResponse objects are to be retrieved
     * @return a list of ApplicationResponse objects matching the provided intake ID
     */
    @Transactional(readOnly = true)
    public List<ApplicationResponse> findByIntakeId(String intakeId) {
        return findByIntakeId(intakeId, false);
    }

    /**
     * Retrieves a paginated list of applications associated with the given user ID.
     *
     * @param userId the unique identifier of the user whose applications are to be retrieved
     * @param page   the page number to retrieve (zero-based index)
     * @param size   the number of records per page
     * @return a PagedResponse containing a list of ApplicationResponse objects for the given user ID
     */
    @Transactional(readOnly = true)
    public PagedResponse<ApplicationResponse> findByUserIdPaged(String userId, int page, int size) {
        return findByUserIdPaged(userId, page, size, false);
    }

    /**
     * Retrieves a paginated list of applications filtered by the specified status.
     *
     * @param status the status of the applications to filter by
     * @param page   the page number to retrieve
     * @param size   the number of applications per page
     * @return a paginated response containing the applications matching the specified status
     */
    @Transactional(readOnly = true)
    public PagedResponse<ApplicationResponse> findByStatusPaged(String status, int page, int size) {
        return findByStatusPaged(status, page, size, false);
    }

    /**
     * Retrieves a list of ApplicationResponse objects associated with the provided document submission ID.
     *
     * @param documentSubmissionId the unique identifier of the document submission to find associated application
     *                             responses for
     * @return a list of ApplicationResponse objects corresponding to the given document submission ID
     */
    @Transactional(readOnly = true)
    public List<ApplicationResponse> findByDocumentSubmissionId(String documentSubmissionId) {
        return findByDocumentSubmissionId(documentSubmissionId, false);
    }

    /**
     * Retrieves a list of ApplicationResponse objects representing applications
     * that do not have associated documents.
     * <p>
     * The method operates in a read-only transactional context, ensuring no modifications
     * are made to the underlying data during its execution.
     *
     * @return a list of ApplicationResponse instances representing the applications
     * without associated documents. Returns an empty list if no such applications exist.
     */
    @Transactional(readOnly = true)
    public List<ApplicationResponse> findApplicationsWithoutDocuments() {
        return findApplicationsWithoutDocuments(false);
    }

    /**
     * Retrieves a paginated list of application summaries based on the provided page, size,
     * sorting field, and sorting direction.
     *
     * @param page          the page number to retrieve, starting from 0
     * @param size          the number of items per page
     * @param sortBy        the field used for sorting the results
     * @param sortDirection the direction of sorting, either "asc" for ascending or "desc" for descending
     * @return a paginated response containing a list of application summaries
     */
    @Transactional(readOnly = true)
    public PagedResponse<ApplicationSummaryResponse> findAllPaged(int page, int size, String sortBy,
                                                                  String sortDirection) {
        return findAllSummaryPaged(page, size, sortBy, sortDirection);
    }

    /**
     * Finds an application by its unique identifier.
     *
     * @param id the unique identifier of the application to retrieve
     * @return the application associated with the given identifier
     * @throws EntityNotFoundException if no application is found for the provided identifier
     */
    private Application findApplicationById(String id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Application not found with id: " + id));
    }

    /**
     * Converts the given Application entity into an ApplicationSummaryResponse, including additional
     * computed information such as document count, sponsor count, and whether the application has
     * an academic background.
     *
     * @param application the Application entity to be converted into a summary response
     * @return an ApplicationSummaryResponse containing the details of the application and additional computed
     * information
     */
    private ApplicationSummaryResponse toSummaryResponse(Application application) {
        int documentCount = application.getApplicationDocuments() != null ?
                application.getApplicationDocuments().size() : 0;
        int sponsorCount = Math.toIntExact(sponsorRepository.countByApplicationId(application.getId()));

        // UPDATED: Check if user has any academic backgrounds (not application-specific)
        boolean hasAcademicBackground = academicBackgroundRepository.existsByUserId(application.getUserId());
        long academicBackgroundCount = academicBackgroundRepository.countByUserId(application.getUserId());

        return applicationMapper.toSummaryResponse(application, documentCount, sponsorCount,
                hasAcademicBackground, (int) academicBackgroundCount);
    }

    /**
     * Creates a {@code Pageable} object to handle pagination and sorting based on the provided parameters.
     *
     * @param page          the page number to be retrieved (zero-based index)
     * @param size          the number of items per page
     * @param sortBy        the field name by which the data should be sorted
     * @param sortDirection the direction of sorting, either "asc" for ascending or "desc" for descending
     * @return a {@code Pageable} object representing the requested page, size, and sorting information
     */
    private Pageable createPageable(int page, int size, String sortBy, String sortDirection) {
        Sort sort = createSort(sortBy, sortDirection);
        return PageRequest.of(page, size, sort);
    }

    /**
     * Creates and returns a Sort object based on the given sort field and sort direction.
     *
     * @param sortBy        the field by which to sort the data
     * @param sortDirection the sort direction, either "ASC" for ascending or "DESC" for descending
     * @return a Sort object configured with the specified field and direction
     */
    private Sort createSort(String sortBy, String sortDirection) {
        return sortDirection.equalsIgnoreCase("DESC") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
    }

    /**
     * Calculates the average processing time based on predefined or computed metrics.
     *
     * @return the average processing time in hours as a double value
     */
    private double calculateAverageProcessingTime() {
        // This would typically calculate based on actual processing times
        // For now, return a placeholder value
        return 24.0; // 24 hours average
    }


    /**
     * Checks if the current user is the owner of the specified application.
     * Used for security authorization in controllers.
     *
     * @param applicationId the ID of the application
     * @param currentUserId the current authenticated user's ID
     * @return true if the user owns the application, false otherwise
     */
    public boolean isApplicationOwner(String applicationId, String currentUserId) {
        try {
            if (applicationId == null || currentUserId == null) {
                return false;
            }

            return applicationRepository.findById(applicationId)
                    .map(application -> currentUserId.equals(application.getUserId().toString()))
                    .orElse(false);
        } catch (Exception e) {
            logger.error("Error checking application ownership for applicationId: {} and userId: {}",
                    applicationId, currentUserId, e);
            return false;
        }
    }

    /**
     * Checks if the current user owns the application associated with the specified document.
     *
     * @param documentId the ID of the document
     * @param currentUserId the current authenticated user's ID
     * @return true if the user owns the application containing the document, false otherwise
     */
    public boolean isDocumentOwner(String documentId, String currentUserId) {
        try {
            if (documentId == null || currentUserId == null) {
                return false;
            }

            // Find applications that contain this document
            List<Application> applications = applicationRepository.findByApplicationDocumentsContaining(documentId);

            return applications.stream()
                    .anyMatch(app -> currentUserId.equals(app.getUserId().toString()));
        } catch (Exception e) {
            logger.error("Error checking document ownership for documentId: {} and userId: {}",
                    documentId, currentUserId, e);
            return false;
        }
    }

    // ================================ MISSING CONTROLLER METHODS ================================

    /**
     * Retrieves an application by its unique identifier, with optional nested details.
     * Maps to controller method: getApplicationById
     */
    @Transactional(readOnly = true)
    public ApplicationResponse getApplicationById(String id, boolean includeDocuments,
                                                  boolean includeAcademicBackground, boolean includeSponsors) {
        if (includeDocuments || includeAcademicBackground || includeSponsors) {
            return findByIdWithDetails(id, includeDocuments); // Pass includeDocuments as includeComments
        } else {
            return findById(id);
        }
    }

    /**
     * Retrieves all applications with optional pagination and details.
     * Maps to controller method: getAllApplications
     */
    @Transactional(readOnly = true)
    public List<ApplicationResponse> getAllApplications(int page, int size, boolean includeDocuments,
                                                        boolean includeAcademicBackground, boolean includeSponsors) {
        return getAllApplications(page, size, includeDocuments, includeAcademicBackground, includeSponsors, false);
    }
    
    /**
     * Retrieves all applications with optional pagination and details, with the option to include comments.
     * Maps to controller method: getAllApplicationsDetailed
     */
    @Transactional(readOnly = true)
    public List<ApplicationResponse> getAllApplications(int page, int size, boolean includeDocuments,
                                                        boolean includeAcademicBackground, boolean includeSponsors,
                                                        boolean includeComments) {
        if (includeDocuments || includeAcademicBackground || includeSponsors) {
            PagedResponse<ApplicationResponse> paged = findAllPaged(page, size, "createdAt", "DESC", true, includeComments);
            return paged.getContent();
        } else {
            PagedResponse<ApplicationResponse> paged = findAllPaged(page, size, "createdAt", "DESC", false, false);
            return paged.getContent();
        }
    }

    /**
     * Retrieves a paginated list of applications with optional details.
     * Maps to controller method: getPagedApplications
     */
    @Transactional(readOnly = true)
    public PagedResponse<ApplicationResponse> getPagedApplications(int page, int size, boolean includeDocuments,
                                                                   boolean includeAcademicBackground, boolean includeSponsors) {
        boolean includeDetails = includeDocuments || includeAcademicBackground || includeSponsors;
        return findAllPaged(page, size, "createdAt", "DESC", includeDetails);
    }

    /**
     * Retrieves applications for a specific user.
     * Maps to controller method: getApplicationsByUser
     */
    @Transactional(readOnly = true)
    public List<ApplicationResponse> getApplicationsByUser(String userId, boolean includeDocuments,
                                                           boolean includeAcademicBackground, boolean includeSponsors) {
        boolean includeDetails = includeDocuments || includeAcademicBackground || includeSponsors;
        return findByUserId(userId, includeDetails);
    }

    /**
     * Retrieves applications with a specific status.
     * Maps to controller method: getApplicationsByStatus
     */
    @Transactional(readOnly = true)
    public List<ApplicationResponse> getApplicationsByStatus(String status, boolean includeDocuments,
                                                             boolean includeAcademicBackground, boolean includeSponsors) {
        boolean includeDetails = includeDocuments || includeAcademicBackground || includeSponsors;
        return findByStatus(status, includeDetails);
    }

    /**
     * Searches for applications based on provided criteria.
     * Maps to controller method: searchApplications
     */
    @Transactional(readOnly = true)
    public List<ApplicationResponse> searchApplications(ApplicationSearchRequest searchRequest) {
        PagedResponse<ApplicationResponse> paged = searchApplications(searchRequest, false);
        return paged.getContent();
    }

    /**
     * Finds applications containing a specific document submission.
     * Maps to controller method: getApplicationsByDocument
     */
    @Transactional(readOnly = true)
    public List<ApplicationResponse> getApplicationsByDocument(String documentId, boolean includeDocuments,
                                                               boolean includeAcademicBackground, boolean includeSponsors) {
        boolean includeDetails = includeDocuments || includeAcademicBackground || includeSponsors;
        return findByDocumentSubmissionId(documentId, includeDetails);
    }

    /**
     * Retrieves applications that have no document submissions.
     * Maps to controller method: getApplicationsWithoutDocuments
     */
    @Transactional(readOnly = true)
    public List<ApplicationResponse> getApplicationsWithoutDocuments(boolean includeAcademicBackground, boolean includeSponsors) {
        boolean includeDetails = includeAcademicBackground || includeSponsors;
        return findApplicationsWithoutDocuments(includeDetails);
    }

    /**
     * Creates a new application.
     * Maps to controller method: createApplication
     */
    public ApplicationResponse createApplication(@Valid ApplicationRequest applicationRequest) {
        return create(applicationRequest);
    }

    /**
     * Creates multiple applications in a single request.
     * Maps to controller method: createApplicationsBatch
     */
    public BatchResponse<ApplicationResponse> createApplicationsBatch(@Valid BatchApplicationRequest batchRequest) {
        return createBatch(batchRequest);
    }

    /**
     * Updates an existing application.
     * Maps to controller method: updateApplication
     */
    public ApplicationResponse updateApplication(String id, @Valid ApplicationRequest applicationRequest) {
        return update(id, applicationRequest);
    }

    /**
     * Updates the status of an application.
     * Maps to controller method: updateApplicationStatus
     */
    public ApplicationResponse updateApplicationStatus(String id, ApplicationStatusUpdateRequest statusUpdateRequest) {
        return updateStatus(id, statusUpdateRequest);
    }

    /**
     * Deletes an application and all related data.
     * Maps to controller method: deleteApplication
     */
    public void deleteApplication(String id) {
        delete(id);
    }

    /**
     * Adds a document submission to a specific application.
     * Maps to controller method: addDocumentToApplication
     */
    public ApplicationDocumentSubmissionResponse addDocumentToApplication(String applicationId,
                                                                          @Valid ApplicationDocumentSubmissionRequest documentRequest) {
        ApplicationResponse updatedApp = addDocumentSubmission(applicationId, documentRequest);
        // Return the added document - we need to get it from the updated application
        List<ApplicationDocumentSubmissionResponse> docs = getApplicationDocuments(applicationId);
        return docs.get(docs.size() - 1); // Return the last added document
    }

    /**
     * Removes a document submission from an application.
     * This method is a wrapper for the removeDocumentSubmission method and is used by the controller.
     * It validates the application and document existence, removes the document from the application,
     * and handles all related cleanup operations.
     *
     * @param applicationId the unique identifier of the application from which the document will be removed
     * @param documentId the unique identifier of the document submission to be removed
     * @throws EntityNotFoundException if the application or document submission is not found
     * @throws IllegalArgumentException if the document is not associated with the application
     * Maps to controller method: removeDocumentFromApplication
     */
    @Transactional
    public void removeDocumentFromApplication(String applicationId, String documentId) {
        removeDocumentSubmission(applicationId, documentId);
    }

    /**
     * Retrieves all document submissions across all applications with optional pagination.
     * Maps to controller method: getAllDocuments
     */
    @Transactional(readOnly = true)
    public List<ApplicationDocumentSubmissionResponse> getAllDocuments(int page, int size) {
        PagedResponse<ApplicationDocumentSubmissionResponse> paged = getAllDocumentSubmissionsPaged(page, size, "createdAt", "DESC");
        return paged.getContent();
    }

    /**
     * Retrieves a specific document submission by ID.
     * Maps to controller method: getDocumentById
     */
    @Transactional(readOnly = true)
    public ApplicationDocumentSubmissionResponse getDocumentById(String documentId) {
        return getDocumentSubmissionById(documentId);
    }

    /**
     * Updates the verification status of a document submission.
     * Maps to controller method: updateDocumentStatus
     */
    public ApplicationDocumentSubmissionResponse updateDocumentStatus(String documentId,
                                                                      @Valid DocumentVerificationStatusUpdateRequest statusUpdateRequest) {
        // Find the document submission by ID
        ApplicationDocumentSubmission submission = documentRepository.findById(documentId)
                .orElseThrow(() -> new EntityNotFoundException("Document submission not found with id: " + documentId));
        
        // Convert the verification status string to enum value
        DocumentVerificationStatus newStatus = DocumentVerificationStatus.valueOf(statusUpdateRequest.getVerificationStatus().toUpperCase());
        
        // Set the new verification status
        submission.setVerificationStatus(newStatus);
        
        // Save the updated document submission
        ApplicationDocumentSubmission savedSubmission = documentRepository.save(submission);
        
        // Create a new comment if provided
        if (statusUpdateRequest.getComment() != null && !statusUpdateRequest.getComment().isEmpty()) {
            ApplicationDeferringComment comment = new ApplicationDeferringComment();
            comment.setComment(statusUpdateRequest.getComment());
            comment.setSubmittedDocumentId(documentId);
            comment.setCreatedAt(LocalDateTime.now());
            commentRepository.save(comment);
        }
        
        // Return the updated document submission response
        return documentMapper.toResponse(savedSubmission);
    }

    /**
     * Retrieves document submissions with a specific verification status.
     * Maps to controller method: getDocumentsByStatus
     */
    @Transactional(readOnly = true)
    public List<ApplicationDocumentSubmissionResponse> getDocumentsByStatus(String status) {
        return getDocumentSubmissionsByStatus(status);
    }

    /**
     * Retrieves comprehensive application analytics.
     * Maps to controller method: getApplicationAnalytics
     */
    @Transactional(readOnly = true)
    public ApplicationAnalyticsResponse getApplicationAnalytics() {
        return getAnalytics();
    }

    /**
     * Retrieves the number of applications per status.
     * Maps to controller method: getApplicationStatusCounts
     */
    @Transactional(readOnly = true)
    public List<ApplicationStatusCount> getApplicationStatusCounts() {
        return applicationRepository.countApplicationsByStatus(); 
    }

    /**
     * Retrieves the number of applications per intake.
     * Maps to controller method: getApplicationIntakeCounts
     */
    @Transactional(readOnly = true)
    public List<IntakeApplicationCount> getApplicationIntakeCounts() {
        return getApplicationCountsByIntake();
    }

    /**
     * Retrieves daily application counts for a date range.
     * Maps to controller method: getDailyApplicationCounts
     */
    @Transactional(readOnly = true)
    public List<DailyApplicationCount> getDailyApplicationCounts(LocalDateTime startDate, LocalDateTime endDate) {
        return getApplicationCountsByDateRange(startDate, endDate);
    }

    /**
     * Retrieves average document counts by application status.
     * Maps to controller method: getDocumentStatistics
     */
    @Transactional(readOnly = true)
    public List<ApplicationDocumentStatistics> getDocumentStatisticsByStatus() {
        return applicationRepository.getDocumentStatisticsByStatus(); 
    }

    @Transactional
    public ApplicationDocumentSubmissionResponse replaceDocumentInApplication(String documentSubmissionId,
                                                                              String newDocumentId,
                                                                              String replacementReason,
                                                                              String uploadedBy) {
        // Find document submission
        ApplicationDocumentSubmission submission = documentRepository.findById(documentSubmissionId)
                .orElseThrow(() -> new EntityNotFoundException("Document submission not found with id: " + documentSubmissionId));

        // Add new version
        submission.addNewVersion(
                newDocumentId,
                uploadedBy,
                replacementReason != null ? replacementReason : "Document replacement",
                null // Original filename will be set by upload service
        );

        // Save updated submission
        ApplicationDocumentSubmission saved = documentRepository.save(submission);

        logger.info("Replaced document submission {} with new document {}. New version: {}",
                documentSubmissionId, newDocumentId, saved.getCurrentVersion());

        return documentMapper.toResponse(saved);
    }

    /**
     * Check if a document can be replaced (NEW METHOD)
     */
    @Transactional(readOnly = true)
    public boolean canReplaceDocument(String documentSubmissionId) {
        try {
            ApplicationDocumentSubmission submission = documentRepository.findById(documentSubmissionId)
                    .orElseThrow(() -> new EntityNotFoundException("Document submission not found"));

            // Allow replacement if document is rejected or pending
            return submission.getVerificationStatus() == DocumentVerificationStatus.REJECTED ||
                    submission.getVerificationStatus() == DocumentVerificationStatus.PENDING;
        } catch (EntityNotFoundException e) {
            return false;
        }
    }

    /**
     * Get document version history (NEW METHOD)
     */
    @Transactional(readOnly = true)
    public List<DocumentVersion> getDocumentVersionHistory(String documentSubmissionId) {
        ApplicationDocumentSubmission submission = documentRepository.findById(documentSubmissionId)
                .orElseThrow(() -> new EntityNotFoundException("Document submission not found with id: " + documentSubmissionId));

        return submission.getVersions() != null ? submission.getVersions() : Collections.emptyList();
    }

}