package rw.ac.ilpd.registrationservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.registrationservice.model.nosql.document.AcademicBackground;
import rw.ac.ilpd.registrationservice.projection.AverageDuration;
import rw.ac.ilpd.registrationservice.projection.CountryAcademicCount;
import rw.ac.ilpd.registrationservice.projection.DegreeCount;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AcademicBackgroundRepository extends MongoRepository<AcademicBackground, String> {

    // User-centric queries (PRIMARY APPROACH)

    /**
     * Find all academic backgrounds for a specific user
     */
    List<AcademicBackground> findByUserId(UUID userId);

    /**
     * Find academic backgrounds by user ID with pagination
     */
    Page<AcademicBackground> findByUserId(UUID userId, Pageable pageable);

    /**
     * Check if a user has any academic backgrounds
     */
    boolean existsByUserId(UUID userId);

    /**
     * Count academic backgrounds for a user
     */
    long countByUserId(UUID userId);

    // University-based queries
    List<AcademicBackground> findByUniversityId_Id(String universityId);

    Page<AcademicBackground> findByUniversityId_Id(String universityId, Pageable pageable);

    long countByUniversityId_Id(String universityId);

    boolean existsByUniversityId_Id(String universityId);

    // Degree-based queries
    List<AcademicBackground> findByDegreeContainingIgnoreCase(String degree);

    Page<AcademicBackground> findByDegreeContainingIgnoreCase(String degree, Pageable pageable);

    long countByDegreeContainingIgnoreCase(String degree);

    // Date range queries
    @Query("{ 'startDate': { $gte: ?0 }, 'endDate': { $lte: ?1 } }")
    List<AcademicBackground> findByDateRange(LocalDate startDate, LocalDate endDate);

    @Query("{ 'startDate': { $gte: ?0 } }")
    List<AcademicBackground> findByStartDateAfter(LocalDate startDate);

    @Query("{ 'endDate': { $lte: ?0 } }")
    List<AcademicBackground> findByEndDateBefore(LocalDate endDate);

    @Query(value = "{ 'startDate': { $gte: ?0 }, 'endDate': { $lte: ?1 } }", count = true)
    long countByDateRange(LocalDate startDate, LocalDate endDate);

    // Embedded recommender queries
    @Query("{ 'recommenders.email': ?0 }")
    List<AcademicBackground> findByRecommenderEmail(String email);

    @Query("{ '$or': [" +
            "  { 'recommenders.firstName': { $regex: ?0, $options: 'i' } }," +
            "  { 'recommenders.lastName': { $regex: ?0, $options: 'i' } }" +
            "] }")
    List<AcademicBackground> findByRecommenderName(String name);

    @Query("{ 'recommenders.phoneNumber': ?0 }")
    List<AcademicBackground> findByRecommenderPhone(String phoneNumber);

    // Advanced search queries
    @Query("{ '$and': [" +
            "  { 'universityId.country': ?0 }," +
            "  { 'degree': { $regex: ?1, $options: 'i' } }" +
            "]" +
            " }")
    List<AcademicBackground> findByUniversityCountryAndDegree(String country, String degree);

    @Query("{ '$or': [" +
            "  { 'degree': { $regex: ?0, $options: 'i' } }," +
            "  { 'universityId.name': { $regex: ?0, $options: 'i' } }" +
            "] }")
    List<AcademicBackground> searchByDegreeOrUniversityName(String searchTerm);

    // User-specific advanced queries
    @Query("{ '$and': [" +
            "  { 'userId': ?0 }," +
            "  { '$or': [" +
            "    { 'degree': { $regex: ?1, $options: 'i' } }," +
            "    { 'universityId.name': { $regex: ?1, $options: 'i' } }" +
            "  ] }" +
            "] }")
    List<AcademicBackground> searchUserAcademicBackgrounds(UUID userId, String searchTerm);

    @Query("{ '$and': [" +
            "  { 'userId': ?0 }," +
            "  { 'universityId.country': ?1 }," +
            "  { 'degree': { $regex: ?2, $options: 'i' } }" +
            "] }")
    List<AcademicBackground> findByUserIdAndUniversityCountryAndDegree(UUID userId, String country, String degree);

    // Aggregation queries for statistics
    @Aggregation(pipeline = {
            "{ $group: { _id: '$universityId.country', count: { $sum: 1 } } }",
            "{ $sort: { count: -1 } }"
    })
    List<CountryAcademicCount> countByUniversityCountry();

    @Aggregation(pipeline = {
            "{ $group: { _id: '$degree', count: { $sum: 1 } } }",
            "{ $sort: { count: -1 } }"
    })
    List<DegreeCount> countByDegree();

    // User-specific aggregation queries
    @Aggregation(pipeline = {
            "{ $match: { 'userId': ?0 } }",
            "{ $group: { _id: '$degree', count: { $sum: 1 } } }",
            "{ $sort: { count: -1 } }"
    })
    List<DegreeCount> countByDegreeForUser(UUID userId);

    @Aggregation(pipeline = {
            "{ $match: { 'userId': ?0 } }",
            "{ $group: { _id: '$universityId.country', count: { $sum: 1 } } }",
            "{ $sort: { count: -1 } }"
    })
    List<CountryAcademicCount> countByUniversityCountryForUser(UUID userId);

    @Aggregation(pipeline = {
            "{ $project: { duration: { $subtract: ['$endDate', '$startDate'] } } }",
            "{ $group: { _id: null, avgDuration: { $avg: '$duration' } } }"
    })
    AverageDuration calculateAverageStudyDuration();

    @Aggregation(pipeline = {
            "{ $match: { 'userId': ?0 } }",
            "{ $project: { duration: { $subtract: ['$endDate', '$startDate'] } } }",
            "{ $group: { _id: null, avgDuration: { $avg: '$duration' } } }"
    })
    AverageDuration calculateAverageStudyDurationForUser(UUID userId);

    // Statistics queries
    @Aggregation(pipeline = {
            "{ $group: { _id: null, totalUsers: { $addToSet: '$userId' } } }",
            "{ $project: { _id: 0, uniqueUsersCount: { $size: '$totalUsers' } } }"
    })
    Optional<Long> countUniqueUsers();

    @Query(value = "{ 'userId': ?0 }", count = true)
    long countAcademicBackgroundsByUser(UUID userId);

    // Sorting queries
    List<AcademicBackground> findAllByOrderByStartDateDesc();

    List<AcademicBackground> findByUserIdOrderByStartDateDesc(UUID userId);

    List<AcademicBackground> findByUniversityId_IdOrderByStartDateDesc(String universityId);

    // Delete operations
    void deleteByUserId(UUID userId);

    void deleteByUniversityId_Id(String universityId);

    @Query(value = "{ '$and': [ { 'userId': ?0 }, { 'universityId._id': ?1 } ] }")
    List<AcademicBackground> findByUserIdAndUniversityId(UUID userId, String universityId);

    // Analytics methods
    @Aggregation(pipeline = {
            "{ $group: { _id: '$universityId.country', count: { $sum: 1 } } }",
            "{ $project: { id: '$_id', count: '$count', _id: 0 } }"
    })
    List<CountryAcademicCount> countByCountry();
}