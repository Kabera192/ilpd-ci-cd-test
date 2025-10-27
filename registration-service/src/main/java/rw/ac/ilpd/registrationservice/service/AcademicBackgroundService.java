package rw.ac.ilpd.registrationservice.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import rw.ac.ilpd.registrationservice.mapper.AcademicBackgroundMapper;
import rw.ac.ilpd.registrationservice.mapper.PaginationMapper;
import rw.ac.ilpd.registrationservice.model.nosql.document.AcademicBackground;
import rw.ac.ilpd.registrationservice.model.nosql.embedding.EmbeddedRecommender;
import rw.ac.ilpd.registrationservice.model.nosql.embedding.University;
import rw.ac.ilpd.registrationservice.projection.CountryAcademicCount;
import rw.ac.ilpd.registrationservice.projection.DegreeCount;
import rw.ac.ilpd.registrationservice.repository.AcademicBackgroundRepository;
import rw.ac.ilpd.registrationservice.repository.UniversityRepository;
import rw.ac.ilpd.sharedlibrary.dto.application.AcademicBackgroundRequest;
import rw.ac.ilpd.sharedlibrary.dto.application.AcademicBackgroundResponse;
import rw.ac.ilpd.sharedlibrary.dto.application.MultipleAcademicBackgroundRequest;
import rw.ac.ilpd.sharedlibrary.dto.application.MultipleAcademicBackgroundResponse;
import rw.ac.ilpd.sharedlibrary.dto.application.PagedResponse;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service for managing user-centric academic background information.
 * Multiple academic backgrounds can be associated with a single user.
 * Recommender data is embedded instead of referenced by ID.
 */
@Service
@Transactional
@Validated
public class AcademicBackgroundService {

    private static final Logger logger = LoggerFactory.getLogger(AcademicBackgroundService.class);

    private final AcademicBackgroundRepository academicBackgroundRepository;
    private final UniversityRepository universityRepository;
    private final PaginationMapper paginationMapper;
    private final AcademicBackgroundMapper academicBackgroundMapper;

    @Autowired
    public AcademicBackgroundService(
            AcademicBackgroundRepository academicBackgroundRepository,
            UniversityRepository universityRepository,
            PaginationMapper paginationMapper,
            AcademicBackgroundMapper academicBackgroundMapper) {
        this.academicBackgroundRepository = academicBackgroundRepository;
        this.universityRepository = universityRepository;
        this.paginationMapper = paginationMapper;
        this.academicBackgroundMapper = academicBackgroundMapper;
    }

    /**
     * Creates a single academic background for a user.
     */
    public AcademicBackgroundResponse create(@Valid AcademicBackgroundRequest request) {
        logger.info("Creating academic background for user: {}", request.getUserId());

        // Validate university exists
        University university = universityRepository.findById(request.getUniversityId())
                .orElseThrow(() -> new EntityNotFoundException("University not found with id: " + request.getUniversityId()));

        // Create embedded recommenders from request data
        List<EmbeddedRecommender> embeddedRecommenders = request.getRecommenders()
                .stream()
                .map(recommenderRequest -> new EmbeddedRecommender(
                        null, // ID will be generated
                        recommenderRequest.getFirstName(),
                        recommenderRequest.getLastName(),
                        recommenderRequest.getPhoneNumber(),
                        recommenderRequest.getEmail()
                ))
                .collect(Collectors.toList());

        // Create academic background entity
        AcademicBackground academicBackground = academicBackgroundMapper.toEntity(request, university, embeddedRecommenders);
        AcademicBackground saved = academicBackgroundRepository.save(academicBackground);

        logger.info("Successfully created academic background with ID: {} for user: {}", saved.getId(), request.getUserId());
        return academicBackgroundMapper.toResponse(saved);
    }

    /**
     * Creates multiple academic backgrounds for a user in a single transaction.
     */
    public MultipleAcademicBackgroundResponse createMultiple(@Valid MultipleAcademicBackgroundRequest request) {
        logger.info("Creating {} academic backgrounds", request.getAcademicBackgrounds().size());

        List<AcademicBackground> createdBackgrounds = request.getAcademicBackgrounds()
                .stream()
                .map(this::createSingleAcademicBackground)
                .collect(Collectors.toList());

        List<AcademicBackground> savedBackgrounds = academicBackgroundRepository.saveAll(createdBackgrounds);

        List<AcademicBackgroundResponse> responses = savedBackgrounds.stream()
                .map(academicBackgroundMapper::toResponse)
                .collect(Collectors.toList());

        String userId = savedBackgrounds.get(0).getUserId().toString();
        logger.info("Successfully created {} academic backgrounds for user: {}", savedBackgrounds.size(), userId);

        return MultipleAcademicBackgroundResponse.builder()
                .academicBackgrounds(responses)
                .totalCount(responses.size())
                .userId(userId)
                .build();
    }

    /**
     * Helper method to create a single academic background from request data.
     */
    private AcademicBackground createSingleAcademicBackground(AcademicBackgroundRequest request) {
        // Validate university exists
        University university = universityRepository.findById(request.getUniversityId())
                .orElseThrow(() -> new EntityNotFoundException("University not found with id: " + request.getUniversityId()));

        // Create embedded recommenders
        List<EmbeddedRecommender> embeddedRecommenders = request.getRecommenders()
                .stream()
                .map(recommenderRequest -> new EmbeddedRecommender(
                        null, // ID will be generated
                        recommenderRequest.getFirstName(),
                        recommenderRequest.getLastName(),
                        recommenderRequest.getPhoneNumber(),
                        recommenderRequest.getEmail()
                ))
                .collect(Collectors.toList());

        return academicBackgroundMapper.toEntity(request, university, embeddedRecommenders);
    }

    /**
     * Retrieves all academic backgrounds for a specific user.
     */
    @Transactional(readOnly = true)
    public MultipleAcademicBackgroundResponse findByUserId(String userId) {
        UUID userUuid = UUID.fromString(userId);
        List<AcademicBackground> academicBackgrounds = academicBackgroundRepository.findByUserId(userUuid);

        List<AcademicBackgroundResponse> responses = academicBackgrounds.stream()
                .map(academicBackgroundMapper::toResponse)
                .collect(Collectors.toList());

        logger.info("Found {} academic backgrounds for user: {}", responses.size(), userId);

        return MultipleAcademicBackgroundResponse.builder()
                .academicBackgrounds(responses)
                .totalCount(responses.size())
                .userId(userId)
                .build();
    }

    /**
     * Retrieves all academic backgrounds for a user with pagination.
     */
    @Transactional(readOnly = true)
    public PagedResponse<AcademicBackgroundResponse> findByUserIdPaginated(String userId, int page, int size) {
        UUID userUuid = UUID.fromString(userId);
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<AcademicBackground> academicBackgroundPage = academicBackgroundRepository.findByUserId(userUuid, pageable);

        List<AcademicBackgroundResponse> responses = academicBackgroundPage.getContent()
                .stream()
                .map(academicBackgroundMapper::toResponse)
                .collect(Collectors.toList());

        return paginationMapper.toPagedResponse(responses, academicBackgroundPage.getTotalElements(), page, size);
    }

    /**
     * Retrieves a single academic background by its ID.
     */
    @Transactional(readOnly = true)
    public AcademicBackgroundResponse findById(String id) {
        AcademicBackground academicBackground = findAcademicBackgroundById(id);
        return academicBackgroundMapper.toResponse(academicBackground);
    }

    /**
     * Retrieves all academic background records with pagination.
     */
    @Transactional(readOnly = true)
    public PagedResponse<AcademicBackgroundResponse> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<AcademicBackground> academicBackgroundPage = academicBackgroundRepository.findAll(pageable);

        List<AcademicBackgroundResponse> responses = academicBackgroundPage.getContent()
                .stream()
                .map(academicBackgroundMapper::toResponse)
                .collect(Collectors.toList());

        return paginationMapper.toPagedResponse(responses, academicBackgroundPage.getTotalElements(), page, size);
    }

    /**
     * Updates an existing academic background.
     */
    public AcademicBackgroundResponse update(String id, @Valid AcademicBackgroundRequest request) {
        AcademicBackground existing = findAcademicBackgroundById(id);

        // Validate university exists
        University university = universityRepository.findById(request.getUniversityId())
                .orElseThrow(() -> new EntityNotFoundException("University not found with id: " + request.getUniversityId()));

        // Create new embedded recommenders
        List<EmbeddedRecommender> embeddedRecommenders = request.getRecommenders()
                .stream()
                .map(recommenderRequest -> new EmbeddedRecommender(
                        null, // New ID will be generated
                        recommenderRequest.getFirstName(),
                        recommenderRequest.getLastName(),
                        recommenderRequest.getPhoneNumber(),
                        recommenderRequest.getEmail()
                ))
                .collect(Collectors.toList());

        // Update the entity
        AcademicBackground updated = academicBackgroundMapper.updateEntity(existing, request, university, embeddedRecommenders);
        AcademicBackground saved = academicBackgroundRepository.save(updated);

        logger.info("Updated academic background with ID: {}", id);
        return academicBackgroundMapper.toResponse(saved);
    }

    /**
     * Adds a new academic background to an existing user.
     */
    public AcademicBackgroundResponse addAcademicBackgroundToUser(String userId, @Valid AcademicBackgroundRequest request) {
        // Ensure the request has the correct user ID
        request.setUserId(userId);

        AcademicBackground newAcademicBackground = createSingleAcademicBackground(request);
        AcademicBackground saved = academicBackgroundRepository.save(newAcademicBackground);

        logger.info("Added new academic background for user: {}", userId);
        return academicBackgroundMapper.toResponse(saved);
    }

    /**
     * Deletes a single academic background by its ID.
     */
    public void delete(String id) {
        AcademicBackground academicBackground = findAcademicBackgroundById(id);
        academicBackgroundRepository.deleteById(id);
        logger.info("Deleted academic background with ID: {}", id);
    }

    /**
     * Deletes all academic backgrounds for a specific user.
     */
    public void deleteAllByUserId(String userId) {
        UUID userUuid = UUID.fromString(userId);
        List<AcademicBackground> userBackgrounds = academicBackgroundRepository.findByUserId(userUuid);

        if (!userBackgrounds.isEmpty()) {
            academicBackgroundRepository.deleteByUserId(userUuid);
            logger.info("Deleted {} academic backgrounds for user: {}", userBackgrounds.size(), userId);
        } else {
            logger.info("No academic backgrounds found to delete for user: {}", userId);
        }
    }

    // Search and Query Methods

    /**
     * Search academic backgrounds by degree or university name.
     */
    @Transactional(readOnly = true)
    public List<AcademicBackgroundResponse> searchByDegreeOrUniversity(String searchTerm) {
        List<AcademicBackground> results = academicBackgroundRepository.searchByDegreeOrUniversityName(searchTerm);
        return results.stream()
                .map(academicBackgroundMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Search a specific user's academic backgrounds.
     */
    @Transactional(readOnly = true)
    public MultipleAcademicBackgroundResponse searchUserAcademicBackgrounds(String userId, String searchTerm) {
        UUID userUuid = UUID.fromString(userId);
        List<AcademicBackground> results = academicBackgroundRepository.searchUserAcademicBackgrounds(userUuid, searchTerm);

        List<AcademicBackgroundResponse> responses = results.stream()
                .map(academicBackgroundMapper::toResponse)
                .collect(Collectors.toList());

        return MultipleAcademicBackgroundResponse.builder()
                .academicBackgrounds(responses)
                .totalCount(responses.size())
                .userId(userId)
                .build();
    }

    // Statistics and Analytics Methods

    /**
     * Gets degree distribution statistics.
     */
    @Transactional(readOnly = true)
    public List<DegreeCount> getDegreeDistribution() {
        return academicBackgroundRepository.countByDegree();
    }

    /**
     * Gets country-wise academic background distribution.
     */
    @Transactional(readOnly = true)
    public List<CountryAcademicCount> getCountryDistribution() {
        return academicBackgroundRepository.countByUniversityCountry();
    }

    /**
     * Gets degree distribution for a specific user.
     */
    @Transactional(readOnly = true)
    public List<DegreeCount> getUserDegreeDistribution(String userId) {
        UUID userUuid = UUID.fromString(userId);
        return academicBackgroundRepository.countByDegreeForUser(userUuid);
    }

    /**
     * Gets country distribution for a specific user.
     */
    @Transactional(readOnly = true)
    public List<CountryAcademicCount> getUserCountryDistribution(String userId) {
        UUID userUuid = UUID.fromString(userId);
        return academicBackgroundRepository.countByUniversityCountryForUser(userUuid);
    }

    // Authorization and Security Methods

    /**
     * Checks if the current user owns the academic background record.
     */
    public boolean isAcademicBackgroundOwner(String academicBackgroundId, String currentUserId) {
        try {
            if (academicBackgroundId == null || currentUserId == null) {
                return false;
            }

            return academicBackgroundRepository.findById(academicBackgroundId)
                    .map(academicBackground -> currentUserId.equals(academicBackground.getUserId().toString()))
                    .orElse(false);
        } catch (Exception e) {
            logger.error("Error checking academic background ownership for academicBackgroundId: {} and userId: {}",
                    academicBackgroundId, currentUserId, e);
            return false;
        }
    }

    // Helper Methods

    /**
     * Helper method to find academic background by ID or throw exception.
     */
    private AcademicBackground findAcademicBackgroundById(String id) {
        return academicBackgroundRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Academic background not found with id: " + id));
    }
}