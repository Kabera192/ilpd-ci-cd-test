package rw.ac.ilpd.registrationservice.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;
import rw.ac.ilpd.registrationservice.client.NotificationDocumentClient;
import rw.ac.ilpd.registrationservice.mapper.ApplicationSponsorMapper;
import rw.ac.ilpd.registrationservice.mapper.PaginationMapper;
import rw.ac.ilpd.registrationservice.model.nosql.document.ApplicationSponsor;
import rw.ac.ilpd.registrationservice.projection.ApplicationSponsorCount;
import rw.ac.ilpd.registrationservice.projection.SponsorApplicationCount;
import rw.ac.ilpd.registrationservice.repository.ApplicationRepository;
import rw.ac.ilpd.registrationservice.repository.ApplicationSponsorRepository;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationSponsorRequest;
import rw.ac.ilpd.sharedlibrary.dto.application.ApplicationSponsorResponse;
import rw.ac.ilpd.sharedlibrary.dto.application.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentResponse;
import rw.ac.ilpd.sharedlibrary.dto.document.ObjectStorageRequest;
import rw.ac.ilpd.sharedlibrary.util.ObjectUploadPath;

import java.util.List;
import java.util.Optional;

/**
 * Adapter for ApplicationSponsorService that uses the notification service's document API
 * instead of directly using DocumentEntity.
 */
@Service
@Transactional
@Validated
public class ApplicationSponsorService {

    private static final Logger log = LoggerFactory.getLogger(ApplicationSponsorService.class);
    
    private final ApplicationSponsorRepository sponsorRepository;
    private final ApplicationSponsorMapper sponsorMapper;
    private final ApplicationRepository applicationRepository;
    private final PaginationMapper paginationMapper;
    private final NotificationDocumentClient documentClient;

    @Autowired
    public ApplicationSponsorService(
            ApplicationSponsorRepository sponsorRepository,
            ApplicationSponsorMapper sponsorMapper,
            ApplicationRepository applicationRepository,
            PaginationMapper paginationMapper,
            NotificationDocumentClient documentClient) {
        this.sponsorRepository = sponsorRepository;
        this.sponsorMapper = sponsorMapper;
        this.applicationRepository = applicationRepository;
        this.paginationMapper = paginationMapper;
        this.documentClient = documentClient;
    }

    /**
     * Creates a new application sponsor with required recommendation letter validation.
     */
    public ApplicationSponsorResponse create(@Valid ApplicationSponsorRequest request) {
        log.debug("Creating application sponsor: {}", request);
        
        // Validate that application exists
        if (!applicationRepository.existsById(request.getApplicationId())) {
            throw new EntityNotFoundException("Application not found with id: " + request.getApplicationId());
        }

        // Check if sponsor already exists for this application
        if (sponsorRepository.existsByApplicationIdAndSponsorId(request.getApplicationId(), request.getSponsorId())) {
            throw new IllegalArgumentException("Sponsor already exists for this application");
        }

        // NEW VALIDATION: Require recommendation letter when adding sponsor
        if (request.getRecommendationLetterDocId() == null || request.getRecommendationLetterDocId().trim().isEmpty()) {
            throw new IllegalArgumentException("Recommendation letter is required when adding a sponsor");
        }

        // NEW VALIDATION: Verify the recommendation letter document exists
        try {
            // This will throw an exception if the document doesn't exist
            ResponseEntity<DocumentResponse> response = documentClient.findDocument(request.getRecommendationLetterDocId());
            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                throw new EntityNotFoundException(
                        "Recommendation letter document not found with id: " + request.getRecommendationLetterDocId());
            }
        } catch (Exception e) {
            throw new EntityNotFoundException(
                    "Recommendation letter document not found with id: " + request.getRecommendationLetterDocId());
        }

        ApplicationSponsor sponsor = sponsorMapper.toEntity(request);
        ApplicationSponsor saved = sponsorRepository.save(sponsor);
        return sponsorMapper.toResponse(saved);
    }

    /**
     * Creates a sponsor relationship with recommendation letter upload in one step
     */
    public ApplicationSponsorResponse createSponsorWithRecommendationLetter(
            String applicationId,
            String sponsorId,
            MultipartFile recommendationFile,
            String uploadedBy) {
        log.debug("Creating sponsor with recommendation letter for application: {} and sponsor: {}", applicationId, sponsorId);

        // Validate that application exists
        if (!applicationRepository.existsById(applicationId)) {
            throw new EntityNotFoundException("Application not found with id: " + applicationId);
        }

        // Check if sponsor already exists for this application
        if (sponsorRepository.existsByApplicationIdAndSponsorId(applicationId, sponsorId)) {
            throw new IllegalArgumentException("Sponsor already exists for this application");
        }

        // Upload recommendation letter using notification service
        String bucketName = ObjectUploadPath.StudentApplication.BASE;
        String objectPath = ObjectUploadPath.StudentApplication.OTHERS + "/" + applicationId + "/recommendations";

        // Upload document using notification service - UPDATED CALL
        ResponseEntity<DocumentResponse> response = documentClient.uploadSingleObject(
            recommendationFile,
            bucketName,
            objectPath
        );

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new RuntimeException("Failed to upload recommendation letter to notification service");
        }

        DocumentResponse document = response.getBody();

        // Create sponsor with recommendation letter
        ApplicationSponsorRequest request = new ApplicationSponsorRequest();
        request.setApplicationId(applicationId);
        request.setSponsorId(sponsorId);
        request.setRecommendationLetterDocId(document.getId());

        ApplicationSponsor sponsor = sponsorMapper.toEntity(request);
        ApplicationSponsor saved = sponsorRepository.save(sponsor);
        return sponsorMapper.toResponse(saved);
    }
    /**
     * Retrieves the application sponsor identified by the specified ID.
     */
    @Transactional(readOnly = true)
    public ApplicationSponsorResponse findById(String id) {
        log.debug("Finding application sponsor by id: {}", id);
        
        ApplicationSponsor sponsor = findSponsorById(id);
        return sponsorMapper.toResponse(sponsor);
    }

    /**
     * Retrieves all application sponsors and maps them to response DTOs.
     */
    @Transactional(readOnly = true)
    public List<ApplicationSponsorResponse> findAll() {
        log.debug("Finding all application sponsors");
        
        List<ApplicationSponsor> sponsors = sponsorRepository.findAll();
        return sponsorMapper.toResponseList(sponsors);
    }

    /**
     * Retrieves a paginated list of ApplicationSponsorResponse objects sorted based on the provided parameters.
     */
    @Transactional(readOnly = true)
    public PagedResponse<ApplicationSponsorResponse> findAllPaged(int page, int size, String sortBy,
                                                                  String sortDirection) {
        log.debug("Finding all application sponsors paged: page={}, size={}, sortBy={}, sortDirection={}", 
                page, size, sortBy, sortDirection);
        
        Pageable pageable = createPageable(page, size, sortBy, sortDirection);
        Page<ApplicationSponsor> sponsorPage = sponsorRepository.findAll(pageable);

        List<ApplicationSponsorResponse> responses = sponsorMapper.toResponseList(sponsorPage.getContent());
        return paginationMapper.toPagedResponse(responses, sponsorPage.getTotalElements(), page, size);
    }

    /**
     * Finds and retrieves a list of sponsors associated with the given application ID.
     */
    @Transactional(readOnly = true)
    public List<ApplicationSponsorResponse> findByApplicationId(String applicationId) {
        log.debug("Finding application sponsors by application id: {}", applicationId);
        
        List<ApplicationSponsor> sponsors = sponsorRepository.findByApplicationId(applicationId);
        return sponsorMapper.toResponseList(sponsors);
    }

    /**
     * Retrieves a paginated list of sponsors associated with a specific application ID.
     */
    @Transactional(readOnly = true)
    public PagedResponse<ApplicationSponsorResponse> findByApplicationIdPaged(String applicationId, int page,
                                                                              int size) {
        log.debug("Finding application sponsors by application id paged: applicationId={}, page={}, size={}", 
                applicationId, page, size);
        
        Pageable pageable = PageRequest.of(page, size);
        Page<ApplicationSponsor> sponsorPage = sponsorRepository.findByApplicationId(applicationId, pageable);

        List<ApplicationSponsorResponse> responses = sponsorMapper.toResponseList(sponsorPage.getContent());
        return paginationMapper.toPagedResponse(responses, sponsorPage.getTotalElements(), page, size);
    }

    /**
     * Finds the list of `ApplicationSponsorResponse` objects associated with the given sponsor ID.
     */
    @Transactional(readOnly = true)
    public List<ApplicationSponsorResponse> findBySponsorId(String sponsorId) {
        log.debug("Finding application sponsors by sponsor id: {}", sponsorId);
        
        List<ApplicationSponsor> sponsors = sponsorRepository.findBySponsorId(sponsorId);
        return sponsorMapper.toResponseList(sponsors);
    }

    /**
     * Retrieves a paginated list of application sponsors associated with a specific sponsor ID.
     */
    @Transactional(readOnly = true)
    public PagedResponse<ApplicationSponsorResponse> findBySponsorIdPaged(String sponsorId, int page, int size) {
        log.debug("Finding application sponsors by sponsor id paged: sponsorId={}, page={}, size={}", 
                sponsorId, page, size);
        
        Pageable pageable = PageRequest.of(page, size);
        Page<ApplicationSponsor> sponsorPage = sponsorRepository.findBySponsorId(sponsorId, pageable);

        List<ApplicationSponsorResponse> responses = sponsorMapper.toResponseList(sponsorPage.getContent());
        return paginationMapper.toPagedResponse(responses, sponsorPage.getTotalElements(), page, size);
    }

    /**
     * Updates an existing ApplicationSponsor entity with the provided details.
     */
    public ApplicationSponsorResponse update(String id, @Valid ApplicationSponsorRequest request) {
        log.debug("Updating application sponsor: id={}, request={}", id, request);
        
        ApplicationSponsor existing = findSponsorById(id);

        // Validate that application exists
        if (!applicationRepository.existsById(request.getApplicationId())) {
            throw new EntityNotFoundException("Application not found with id: " + request.getApplicationId());
        }

        // Check if sponsor already exists for this application (only if changed)
        if (!existing.getApplicationId().equals(request.getApplicationId()) || !existing.getSponsorId()
                .equals(request.getSponsorId()))
        {
            if (sponsorRepository.existsByApplicationIdAndSponsorId(request.getApplicationId(),
                    request.getSponsorId()))
            {
                throw new IllegalArgumentException("Sponsor already exists for this application");
            }
        }

        ApplicationSponsor updated = sponsorMapper.updateEntity(existing, request);
        ApplicationSponsor saved = sponsorRepository.save(updated);
        return sponsorMapper.toResponse(saved);
    }

    /**
     * Deletes an application sponsor by its unique identifier.
     */
    public void delete(String id) {
        log.debug("Deleting application sponsor: {}", id);
        
        ApplicationSponsor sponsor = findSponsorById(id);
        sponsorRepository.deleteById(id);
    }

    /**
     * Deletes all application sponsor records associated with the specified application ID.
     */
    public void deleteByApplicationId(String applicationId) {
        log.debug("Deleting application sponsors by application id: {}", applicationId);
        
        sponsorRepository.deleteByApplicationId(applicationId);
    }

    /**
     * Deletes all records associated with the specified sponsor ID.
     */
    public void deleteBySponsorId(String sponsorId) {
        log.debug("Deleting application sponsors by sponsor id: {}", sponsorId);
        
        sponsorRepository.deleteBySponsorId(sponsorId);
    }

    /**
     * Counts the number of sponsors associated with a specific application.
     */
    @Transactional(readOnly = true)
    public long countByApplicationId(String applicationId) {
        log.debug("Counting application sponsors by application id: {}", applicationId);
        
        return sponsorRepository.countByApplicationId(applicationId);
    }

    /**
     * Counts the number of application sponsors associated with the specified sponsor ID.
     */
    @Transactional(readOnly = true)
    public long countBySponsorId(String sponsorId) {
        log.debug("Counting application sponsors by sponsor id: {}", sponsorId);
        
        return sponsorRepository.countBySponsorId(sponsorId);
    }

    /**
     * Retrieves an application sponsor by their unique identifier.
     */
    private ApplicationSponsor findSponsorById(String id) {
        return sponsorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Application sponsor not found with id: " + id));
    }

    /**
     * Creates a {@link Pageable} instance using the provided pagination and sorting parameters.
     */
    private Pageable createPageable(int page, int size, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("DESC") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        return PageRequest.of(page, size, sort);
    }

    /**
     * Checks if the current user owns the application sponsor record.
     */
    public boolean isApplicationSponsorOwner(String applicationSponsorId, String currentUserId) {
        log.debug("Checking if user owns application sponsor: applicationSponsorId={}, currentUserId={}", 
                applicationSponsorId, currentUserId);
        
        try {
            if (applicationSponsorId == null || currentUserId == null) {
                return false;
            }

            return sponsorRepository.findById(applicationSponsorId).flatMap(
                            applicationSponsor -> applicationRepository.findById(applicationSponsor.getApplicationId()))
                    .map(application -> currentUserId.equals(application.getUserId().toString())).orElse(false);
        } catch (Exception e) {
            log.error("Error checking application sponsor ownership for applicationSponsorId: {} and userId: {}",
                    applicationSponsorId, currentUserId, e);
            return false;
        }
    }

    /**
     * Retrieves an application sponsor by its unique identifier.
     */
    @Transactional(readOnly = true)
    public ApplicationSponsorResponse getApplicationSponsorById(String id) {
        log.debug("Getting application sponsor by id: {}", id);
        
        ApplicationSponsor applicationSponsor = sponsorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Application sponsor not found with id: " + id));
        return sponsorMapper.toResponse(applicationSponsor);
    }

    /**
     * Retrieves all application sponsors with optional pagination.
     */
    @Transactional(readOnly = true)
    public List<ApplicationSponsorResponse> getAllApplicationSponsors(int page, int size) {
        log.debug("Getting all application sponsors: page={}, size={}", page, size);
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<ApplicationSponsor> sponsorPage = sponsorRepository.findAll(pageable);
        return sponsorMapper.toResponseList(sponsorPage.getContent());
    }

    /**
     * Retrieves application sponsors with pagination support.
     */
    @Transactional(readOnly = true)
    public PagedResponse<ApplicationSponsorResponse> getPagedApplicationSponsors(int page, int size) {
        log.debug("Getting paged application sponsors: page={}, size={}", page, size);
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<ApplicationSponsor> sponsorPage = sponsorRepository.findAll(pageable);

        List<ApplicationSponsorResponse> responses = sponsorMapper.toResponseList(sponsorPage.getContent());
        return paginationMapper.toPagedResponse(responses, sponsorPage.getTotalElements(), page, size);
    }

    /**
     * Retrieves sponsors for a specific application.
     */
    @Transactional(readOnly = true)
    public List<ApplicationSponsorResponse> getApplicationSponsorsByApplication(String applicationId, int page,
                                                                                int size) {
        log.debug("Getting application sponsors by application: applicationId={}, page={}, size={}", 
                applicationId, page, size);
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<ApplicationSponsor> sponsorPage = sponsorRepository.findByApplicationId(applicationId, pageable);
        return sponsorMapper.toResponseList(sponsorPage.getContent());
    }

    /**
     * Retrieves applications for a specific sponsor.
     */
    @Transactional(readOnly = true)
    public List<ApplicationSponsorResponse> getApplicationSponsorsBySponsor(String sponsorId, int page, int size) {
        log.debug("Getting application sponsors by sponsor: sponsorId={}, page={}, size={}", 
                sponsorId, page, size);
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<ApplicationSponsor> sponsorPage = sponsorRepository.findBySponsorId(sponsorId, pageable);
        return sponsorMapper.toResponseList(sponsorPage.getContent());
    }

    /**
     * Creates a new application sponsor relationship.
     */
    public ApplicationSponsorResponse createApplicationSponsor(@Valid ApplicationSponsorRequest applicationSponsorRequest) {
        log.debug("Creating application sponsor: {}", applicationSponsorRequest);
        
        ApplicationSponsor applicationSponsor = sponsorMapper.toEntity(applicationSponsorRequest);
        ApplicationSponsor saved = sponsorRepository.save(applicationSponsor);
        return sponsorMapper.toResponse(saved);
    }

    /**
     * Updates an existing application sponsor.
     */
    public ApplicationSponsorResponse updateApplicationSponsor(String id,
                                                               @Valid ApplicationSponsorRequest applicationSponsorRequest) {
        log.debug("Updating application sponsor: id={}, request={}", id, applicationSponsorRequest);
        
        ApplicationSponsor existing = sponsorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Application sponsor not found with id: " + id));

        ApplicationSponsor updated = sponsorMapper.updateEntity(existing, applicationSponsorRequest);
        ApplicationSponsor saved = sponsorRepository.save(updated);
        return sponsorMapper.toResponse(saved);
    }

    /**
     * Deletes an application sponsor by its unique identifier.
     */
    public void deleteApplicationSponsor(String id) {
        log.debug("Deleting application sponsor: {}", id);
        
        if (!sponsorRepository.existsById(id)) {
            throw new EntityNotFoundException("Application sponsor not found with id: " + id);
        }
        sponsorRepository.deleteById(id);
    }

    /**
     * Deletes all sponsors for a specific application.
     */
    public void deleteApplicationSponsorsByApplication(String applicationId) {
        log.debug("Deleting application sponsors by application: {}", applicationId);
        
        sponsorRepository.deleteByApplicationId(applicationId);
    }

    /**
     * Deletes all applications for a specific sponsor.
     */
    public void deleteApplicationSponsorsBySponsor(String sponsorId) {
        log.debug("Deleting application sponsors by sponsor: {}", sponsorId);
        
        sponsorRepository.deleteBySponsorId(sponsorId);
    }

    /**
     * Gets sponsor application counts.
     */
    @Transactional(readOnly = true)
    public List<SponsorApplicationCount> getSponsorApplicationCounts() {
        log.debug("Getting sponsor application counts");
        
        return sponsorRepository.countApplicationsBySponsor();
    }

    /**
     * Gets application sponsor counts.
     */
    @Transactional(readOnly = true)
    public List<ApplicationSponsorCount> getApplicationSponsorCounts() {
        log.debug("Getting application sponsor counts");
        
        return sponsorRepository.countSponsorsByApplication();
    }
}