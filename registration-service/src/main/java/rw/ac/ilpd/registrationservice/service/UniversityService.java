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
import rw.ac.ilpd.registrationservice.mapper.PaginationMapper;
import rw.ac.ilpd.registrationservice.mapper.UniversityMapper;
import rw.ac.ilpd.registrationservice.model.nosql.embedding.University;
import rw.ac.ilpd.registrationservice.projection.CountryUniversityCount;
import rw.ac.ilpd.registrationservice.repository.AcademicBackgroundRepository;
import rw.ac.ilpd.registrationservice.repository.UniversityRepository;
import rw.ac.ilpd.sharedlibrary.dto.application.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.application.UniversityRequest;
import rw.ac.ilpd.sharedlibrary.dto.application.UniversityResponse;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Service class providing operations for managing universities.
 * It handles creating, reading, updating, and deleting universities as well as other
 * functionalities like searching and pagination. The class includes data validation,
 * uniqueness checks, and transactional guarantees for data consistency.
 * <p>
 * The class utilizes `UniversityRepository` for data persistence,
 * `UniversityMapper` for entity-DTO conversion, and
 * `AcademicBackgroundRepository` for checking references to academic backgrounds.
 * <p>
 * Annotations:
 * - {@code @Service}: Marks this class as a Spring service component.
 * - {@code @Transactional}: Ensures transactional support for the operations.
 * - {@code @Validated}: Enables validation for method parameters.
 */
@Service
@Transactional
@Validated
public class UniversityService {

    /**
     * Repository interface for performing CRUD operations on the `University` entity.
     * It provides methods for data access and manipulation tailored to the requirements
     * of the `UniversityService` class, including finding, searching, and filtering universities.
     * <p>
     * This field is automatically injected by Spring and ensures interaction with
     * the persistence layer. Used throughout the `UniversityService` class for
     * managing universities in the data store.
     */
    private final UniversityRepository universityRepository;
    private final PaginationMapper paginationMapper;
    /**
     * A mapper used for transforming University-related data between different layers
     * of the application, such as converting entities to DTOs and vice versa.
     * <p>
     * This component simplifies the data transformation logic and ensures consistency
     * between the University domain model and its corresponding representations in other layers.
     * <p>
     * It is primarily utilized in the context of the UniversityService class for operations
     * such as creating, updating, and retrieving university information.
     * <p>
     * The mapper helps maintain clean separation of concerns by isolating mapping logic from
     * the service and persistence layers.
     */
    private final UniversityMapper universityMapper;
    // Add at the top of your class
    private static final Logger logger = LoggerFactory.getLogger(UniversityService.class);
    /**
     * Repository for managing and accessing academic background data.
     * This field is used to perform operations related to academic backgrounds,
     * such as verifying references, checking the existence of entries, and retrieving
     * data associated with universities.
     * <p>
     * It interacts with the persistence layer to ensure data consistency and is utilized
     * by various methods in the service to enforce business rules, such as preventing the
     * deletion of universities that are referenced by academic backgrounds.
     */
    private final AcademicBackgroundRepository academicBackgroundRepository;

    /**
     * Constructs an instance of UniversityService with the provided dependencies.
     *
     * @param universityRepository         the repository responsible for University entity persistence and query
     *                                     operations
     * @param universityMapper             the mapper used for converting between University entities and DTOs
     * @param academicBackgroundRepository the repository for managing academic background data and verifying references
     */
    @Autowired
    public UniversityService(UniversityRepository universityRepository, PaginationMapper paginationMapper, UniversityMapper universityMapper,
                             AcademicBackgroundRepository academicBackgroundRepository) {
        this.universityRepository = universityRepository;
        this.paginationMapper = paginationMapper;
        this.universityMapper = universityMapper;
        this.academicBackgroundRepository = academicBackgroundRepository;
    }

    /**
     * Creates a new university based on the provided request data, saves it to the repository,
     * and returns the corresponding response object.
     *
     * @param request the {@code UniversityRequest} object containing the details of the university to be created,
     *                including attributes such as name and country.
     * @return the {@code UniversityResponse} object representing the saved university,
     * including its generated identifier and other attributes.
     * @throws IllegalArgumentException if a university with the same name and country already exists.
     */
    public UniversityResponse create(@Valid UniversityRequest request) {
        validateUniversityUniqueness(request.getName(), request.getCountry());

        University university = universityMapper.toEntity(request);
        University saved = universityRepository.save(university);
        return universityMapper.toResponse(saved);
    }

    /**
     * Finds a university by its unique identifier and maps it to a response DTO.
     * The method ensures a read-only transactional context.
     *
     * @param id the unique identifier of the university to be retrieved
     * @return a {@code UniversityResponse} containing the mapped information
     * of the university corresponding to the provided identifier
     * @throws EntityNotFoundException if no university is found with the given identifier
     */
    @Transactional(readOnly = true)
    public UniversityResponse findById(String id) {
        University university = findUniversityById(id);
        return universityMapper.toResponse(university);
    }

    /**
     * Retrieves a list of all universities and maps them to response objects.
     *
     * @return a list of {@code UniversityResponse} objects representing all universities.
     */
    @Transactional(readOnly = true)
    public List<UniversityResponse> findAll() {
        List<University> universities = universityRepository.findAll();
        return universityMapper.toResponseList(universities);
    }

    /**
     * Retrieves a paginated list of universities with optional sorting.
     * Allows for controlled retrieval of a subset of university records
     * based on pagination and sorting parameters.
     *
     * @param page          the page number to retrieve, starting from 0
     * @param size          the number of items per page
     * @param sortBy        the property name to sort by
     * @param sortDirection the direction of sorting, either "ASC" for ascending or "DESC" for descending
     * @return a PagedResponse containing a list of UniversityResponse objects and pagination metadata
     */
    @Transactional(readOnly = true)
    public PagedResponse<UniversityResponse> findAllPaged(int page, int size, String sortBy, String sortDirection) {
        Pageable pageable = createPageable(page, size, sortBy, sortDirection);
        Page<University> universityPage = universityRepository.findAll(pageable);

        List<UniversityResponse> responses = universityMapper.toResponseList(universityPage.getContent());
        return createPagedResponse(responses, universityPage);
    }

    /**
     * Retrieves a list of universities located in the specified country.
     *
     * @param country the name of the country for which the universities should be fetched
     * @return a list of {@code UniversityResponse} objects representing universities located in the specified country
     */
    @Transactional(readOnly = true)
    public List<UniversityResponse> findByCountry(String country) {
        List<University> universities = universityRepository.findByCountry(country);
        return universityMapper.toResponseList(universities);
    }

    /**
     * Retrieves a paginated list of universities filtered by the specified country.
     * The results are sorted by university name in ascending order.
     *
     * @param country the country by which to filter universities
     * @param page    the page number to retrieve, starting from 0
     * @param size    the number of universities per page
     * @return a paginated response containing university details matching the specified country
     */
    @Transactional(readOnly = true)
    public PagedResponse<UniversityResponse> findByCountryPaged(String country, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<University> universityPage = universityRepository.findByCountry(country, pageable);

        List<UniversityResponse> responses = universityMapper.toResponseList(universityPage.getContent());
        return createPagedResponse(responses, universityPage);
    }

    /**
     * Searches for universities whose names contain the specified string, ignoring case.
     * This method is read-only and returns a list of matched university details wrapped in a response object.
     *
     * @param name the partial or full name of the university to search for
     * @return a list of universities matching the specified name, mapped to response objects
     */
    @Transactional(readOnly = true)
    public List<UniversityResponse> searchByName(String name) {
        List<University> universities = universityRepository.findByNameContainingIgnoreCase(name);
        return universityMapper.toResponseList(universities);
    }

    /**
     * Searches for universities based on a search term that can either match the name
     * or the country of the university.
     *
     * @param searchTerm the search term used to find universities. This can match either
     *                   the name or country of a university.
     * @return a list of {@code UniversityResponse} objects representing the universities
     * matching the given search term. If no matches are found, returns an empty list.
     */
    @Transactional(readOnly = true)
    public List<UniversityResponse> searchByNameOrCountry(String searchTerm) {
        List<University> universities = universityRepository.searchByNameOrCountry(searchTerm);
        return universityMapper.toResponseList(universities);
    }


    /**
     * Updates an existing university identified by its ID with the new details provided in the request.
     * Performs a uniqueness check on the university's `name` and `country` fields if they are modified.
     * If the university is successfully updated, it is saved to the repository, and the updated information
     * is returned as a response.
     *
     * @param id      the unique identifier of the university to be updated
     * @param request the {@code UniversityRequest} object containing updated university details
     * @return a {@code UniversityResponse} object representing the updated university
     * @throws EntityNotFoundException  if no university is found with the given ID
     * @throws IllegalArgumentException if another university with the same name and country already exists
     */
    public UniversityResponse update(String id, @Valid UniversityRequest request) {
        University existing = findUniversityById(id);

        // Check uniqueness only if name or country changed
        if (!existing.getName().equals(request.getName()) || !existing.getCountry().equals(request.getCountry())) {
            validateUniversityUniqueness(request.getName(), request.getCountry());
        }

        University updated = universityMapper.updateEntity(existing, request);
        University saved = universityRepository.save(updated);
        return universityMapper.toResponse(saved);
    }

    /**
     * Deletes a university by its unique identifier.
     * Performs checks to ensure that the university is not
     * referenced by any academic backgrounds before deletion.
     *
     * @param id the unique identifier of the university to be deleted
     * @throws EntityNotFoundException if no university exists with the given identifier
     * @throws IllegalStateException   if the university is referenced by one or more academic backgrounds
     */
    public void delete(String id) {
        University university = findUniversityById(id);

        // Check if university is referenced by academic backgrounds
        long academicBackgroundCount = academicBackgroundRepository.countByUniversityId_Id(id);
        if (academicBackgroundCount > 0) {
            throw new IllegalStateException(
                    "Cannot delete university. It is referenced by " + academicBackgroundCount + " academic " +
                            "background(s)");
        }

        universityRepository.deleteById(id);
    }

    /**
     * Validates the uniqueness of a university based on its name and country. This method ensures that
     * no duplicate university entries exist in the system with the same name and country combination.
     * <p>
     * If a university already exists with the provided name and country, an exception is thrown.
     *
     * @param name    the name of the university to validate
     * @param country the country of the university to validate
     * @throws IllegalArgumentException if a university with the given name and country already exists
     */
    // Helper methods
    private void validateUniversityUniqueness(String name, String country) {
        if (universityRepository.existsByNameAndCountry(name, country)) {
            throw new IllegalArgumentException("University already exists with name: " + name + " in " + country);
        }
    }

    /**
     * Retrieves a university by its unique identifier.
     * If the university is not found, an exception is thrown.
     *
     * @param id the unique identifier of the university to retrieve.
     * @return the university entity matching the given id.
     * @throws EntityNotFoundException if no university is found with the given id.
     */
    private University findUniversityById(String id) {
        return universityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("University not found with id: " + id));
    }

    /**
     * Creates a {@link Pageable} instance with the given page number, page size, sorting field, and sorting direction.
     *
     * @param page          the page number (zero-based index) to retrieve
     * @param size          the size of each page
     * @param sortBy        the field by which the results should be sorted
     * @param sortDirection the direction of sorting (e.g., "ASC" for ascending or "DESC" for descending)
     * @return a {@link Pageable} instance configured with the specified page number, size, sorting field, and direction
     */
    private Pageable createPageable(int page, int size, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("DESC") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        return PageRequest.of(page, size, sort);
    }

    /**
     * Creates a {@link PagedResponse} containing the provided content and pagination details extracted from the
     * given page.
     *
     * @param <T>     the type of the content included in the paged response
     * @param content the list of content items to be included in the paged response
     * @param page    the {@link Page} object from which pagination details are retrieved
     * @return a {@link PagedResponse} containing the provided content and pagination metadata
     */
    private <T> PagedResponse<T> createPagedResponse(List<T> content, Page<?> page) {
        return PagedResponse.<T>builder().content(content).totalElements((int) page.getTotalElements())
                .totalPages(page.getTotalPages()).currentPage(page.getNumber()).pageSize(page.getSize())
                .hasNext(page.hasNext()).hasPrevious(page.hasPrevious()).build();
    }

    @Transactional(readOnly = true)
    public UniversityResponse getUniversityById(String id) {
        University university = universityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("University not found with id: " + id));
        return universityMapper.toResponse(university);
    }

    /**
     * Retrieves all universities with optional pagination.
     * Maps to controller method: getAllUniversities
     */
    @Transactional(readOnly = true)
    public List<UniversityResponse> getAllUniversities(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<University> universityPage = universityRepository.findAll(pageable);
        return universityMapper.toResponseList(universityPage.getContent());
    }

    /**
     * Retrieves universities with pagination support.
     * Maps to controller method: getPagedUniversities
     */
    @Transactional(readOnly = true)
    public PagedResponse<UniversityResponse> getPagedUniversities(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<University> universityPage = universityRepository.findAll(pageable);

        List<UniversityResponse> responses = universityMapper.toResponseList(universityPage.getContent());
        return paginationMapper.toPagedResponse(responses, universityPage.getTotalElements(), page, size);
    }

    /**
     * Searches universities by name.
     * Maps to controller method: searchUniversitiesByName
     */
    @Transactional(readOnly = true)
    public List<UniversityResponse> searchUniversitiesByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<University> universityPage = universityRepository.findByNameContainingIgnoreCase(name, pageable);
        return universityMapper.toResponseList(universityPage.getContent());
    }

    /**
     * Retrieves universities by country.
     * Maps to controller method: getUniversitiesByCountry
     */
    @Transactional(readOnly = true)
    public List<UniversityResponse> getUniversitiesByCountry(String country, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<University> universityPage = universityRepository.findByCountryIgnoreCase(country, pageable);
        return universityMapper.toResponseList(universityPage.getContent());
    }

    /**
     * Creates a new university.
     * Maps to controller method: createUniversity
     */
    public UniversityResponse createUniversity(@Valid UniversityRequest universityRequest) {
        University university = universityMapper.toEntity(universityRequest);
        University saved = universityRepository.save(university);
        return universityMapper.toResponse(saved);
    }

    /**
     * Updates an existing university.
     * Maps to controller method: updateUniversity
     */
    public UniversityResponse updateUniversity(String id, @Valid UniversityRequest universityRequest) {
        University existing = universityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("University not found with id: " + id));

        University updated = universityMapper.updateEntity(existing, universityRequest);
        University saved = universityRepository.save(updated);
        return universityMapper.toResponse(saved);
    }

    /**
     * Deletes a university by its unique identifier.
     * Maps to controller method: deleteUniversity
     */
    public void deleteUniversity(String id) {
        if (!universityRepository.existsById(id)) {
            throw new EntityNotFoundException("University not found with id: " + id);
        }
        universityRepository.deleteById(id);
    }

    /**
     * Retrieves university counts by country.
     * Maps to controller method: getUniversityCountsByCountry
     */
    @Transactional(readOnly = true)
    public List<CountryUniversityCount> getUniversityCountsByCountry() {
        // If this method doesn't exist, implement it using your repository
        return universityRepository.countByCountry();
    }
}

