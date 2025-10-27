package rw.ac.ilpd.registrationservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.registrationservice.model.nosql.embedding.University;
import rw.ac.ilpd.registrationservice.projection.CountryUniversityCount;

import java.util.List;
import java.util.Optional;

@Repository
public interface UniversityRepository extends MongoRepository<University, String> {

    // Basic queries
    Optional<University> findByNameAndCountry(String name, String country);

    List<University> findByCountry(String country);

    List<University> findByNameContainingIgnoreCase(String name);

    Page<University> findByCountry(String country, Pageable pageable);

    // Advanced search queries
    @Query("{ 'name': { $regex: ?0, $options: 'i' } }")
    List<University> findByNameRegex(String namePattern);

    @Query("{ '$or': [" + "  { 'name': { $regex: ?0, $options: 'i' } }," + "  { 'country': { $regex: ?0, $options: " + "'i' } }" + "] }")
    List<University> searchByNameOrCountry(String searchTerm);

    @Query("{ '$and': [" + "  { 'name': { $regex: ?0, $options: 'i' } }," + "  { 'country': ?1 }" + "] }")
    List<University> findByNameContainingAndCountry(String name, String country);

    // Aggregation queries
    @Aggregation(pipeline = {"{ $group: { _id: '$country', count: { $sum: 1 } } }", "{ $sort: { count: -1 } }"})
    List<CountryUniversityCount> countUniversitiesByCountry();

    @Query(value = "{ 'country': ?0 }", count = true)
    long countByCountry(String country);

    // Custom projections
    @Query(value = "{ 'country': ?0 }", fields = "{ 'name': 1, 'country': 1 }")
    List<University> findBasicInfoByCountry(String country);

    // Existence checks
    boolean existsByNameAndCountry(String name, String country);

    boolean existsByName(String name);

    // Batch operations
    List<University> findByIdIn(List<String> ids);

    List<University> findByCountryIn(List<String> countries);

    // Sorting
    List<University> findAllByOrderByNameAsc();

    List<University> findByCountryOrderByNameAsc(String country);
    // If these don't exist, add them:
    Page<University> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<University> findByCountryIgnoreCase(String country, Pageable pageable);

    // Analytics method
    @Aggregation(pipeline = {
            "{ $group: { _id: '$country', count: { $sum: 1 } } }",
            "{ $project: { id: '$_id', count: '$count', _id: 0 } }"
    })
    List<CountryUniversityCount> countByCountry();
}

