package rw.ac.ilpd.registrationservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.registrationservice.model.nosql.document.Application;
import rw.ac.ilpd.registrationservice.model.nosql.document.ApplicationSponsor;
import rw.ac.ilpd.registrationservice.projection.ApplicationDocumentStatistics;
import rw.ac.ilpd.registrationservice.projection.ApplicationSponsorCount;
import rw.ac.ilpd.registrationservice.projection.ApplicationStatusCount;
import rw.ac.ilpd.registrationservice.projection.DailyApplicationCount;
import rw.ac.ilpd.registrationservice.projection.IntakeApplicationCount;
import rw.ac.ilpd.registrationservice.projection.SponsorApplicationCount;
import rw.ac.ilpd.sharedlibrary.enums.ApplicationStatus;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApplicationRepository extends MongoRepository<Application, String> {

    // Basic queries
    List<Application> findByUserId(UUID userId);

    List<Application> findByIntakeId(UUID intakeId);

    List<Application> findByStatus(ApplicationStatus status);

    List<Application> findByUserIdAndStatus(UUID userId, ApplicationStatus status);

    List<Application> findByIntakeIdAndStatus(UUID intakeId, ApplicationStatus status);

    Optional<Application> findByUserIdAndIntakeId(UUID userId, UUID intakeId);

    // Paginated queries
    Page<Application> findByUserId(UUID userId, Pageable pageable);

    Page<Application> findByIntakeId(UUID intakeId, Pageable pageable);

    Page<Application> findByStatus(ApplicationStatus status, Pageable pageable);

    Page<Application> findByUserIdAndStatus(UUID userId, ApplicationStatus status, Pageable pageable);

    // Date range queries
    @Query("{ 'createdAt': { $gte: ?0, $lte: ?1 } }")
    List<Application> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("{ 'createdAt': { $gte: ?0, $lte: ?1 }, 'status': ?2 }")
    List<Application> findByCreatedAtBetweenAndStatus(LocalDateTime startDate, LocalDateTime endDate,
                                                      ApplicationStatus status);

    @Query("{ 'createdAt': { $gte: ?0, $lte: ?1 }, 'intakeId': ?2 }")
    List<Application> findByCreatedAtBetweenAndIntakeId(LocalDateTime startDate, LocalDateTime endDate, UUID intakeId);

    // Advanced search queries
    @Query("{ '$and': [" + "  { 'userId': ?0 }," + "  { 'intakeId': ?1 }," + "  { 'status': ?2 }" + "] }")
    List<Application> findByUserIdAndIntakeIdAndStatus(UUID userId, UUID intakeId, ApplicationStatus status);

    @Query("{ '$or': [" + "  { 'userId': ?0 }," + "  { 'nextOfKinId': ?0 }" + "] }")
    List<Application> findByUserIdOrNextOfKinId(UUID userId);

    @Query("{ 'intakeId': { $in: ?0 }, 'status': ?1 }")
    List<Application> findByIntakeIdInAndStatus(List<UUID> intakeIds, ApplicationStatus status);

    List<Application> findByApplicationDocumentsContaining(String documentId);

    List<Application> findByApplicationDocumentsIn(Collection<List<String>> applicationDocuments);

    @Query("{ 'applicationDocuments': { $size: 0 } }")
    List<Application> findApplicationsWithNoDocuments();

    @Query("{ 'applicationDocuments': { $size: { $gte: ?0 } } }")
    List<Application> findApplicationsWithMinimumDocuments(int minCount);

    @Aggregation(pipeline = {"{ $project: { _id: 1, userId: 1, status: 1, documentCount: { $size: { $ifNull: " +
            "['$applicationDocuments', []] } } } }", "{ $group: { _id: '$status', avgDocuments: { $avg: " +
            "'$documentCount' }, totalApplications: { $sum: 1 } } }"})
    List<ApplicationDocumentStatistics> getDocumentStatisticsByStatus();

    // Count queries
    long countByStatus(ApplicationStatus status);

    long countByIntakeId(UUID intakeId);

    long countByUserIdAndStatus(UUID userId, ApplicationStatus status);

    long countByIntakeIdAndStatus(UUID intakeId, ApplicationStatus status);

    @Query(value = "{ 'createdAt': { $gte: ?0, $lte: ?1 } }", count = true)
    long countByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "{ 'createdAt': { $gte: ?0, $lte: ?1 }, 'status': ?2 }", count = true)
    long countByCreatedAtBetweenAndStatus(LocalDateTime startDate, LocalDateTime endDate, ApplicationStatus status);

    // Aggregation queries for analytics
    @Aggregation(pipeline = {"{ $group: { _id: '$status', count: { $sum: 1 } } }", "{ $sort: { count: -1 } }"})
    List<ApplicationStatusCount> countApplicationsByStatus();

    @Aggregation(pipeline = {"{ $group: { _id: '$intakeId', count: { $sum: 1 } } }", "{ $sort: { count: -1 } }"})
    List<IntakeApplicationCount> countApplicationsByIntake();

    @Aggregation(pipeline = {"{ $match: { 'createdAt': { $gte: ?0, $lte: ?1 } } }", "{ $group: { _id: { " +
            "$dateToString: { format: '%Y-%m-%d', date: '$createdAt' } }, count: { $sum: 1 } } }", "{ $sort: { " +
            "'_id':" + " 1 } }"})
    List<DailyApplicationCount> countApplicationsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    // Existence checks
    boolean existsByUserIdAndIntakeId(UUID userId, UUID intakeId);

    boolean existsByUserIdAndStatus(UUID userId, ApplicationStatus status);

    // Sorting
    List<Application> findAllByOrderByCreatedAtDesc();

    List<Application> findByStatusOrderByCreatedAtDesc(ApplicationStatus status);

    List<Application> findByIntakeIdOrderByCreatedAtDesc(UUID intakeId);

    // Custom delete operations
    void deleteByUserIdAndIntakeId(UUID userId, UUID intakeId);

    void deleteByIntakeId(UUID intakeId);

    // Analytics methods
    @Aggregation(pipeline = {
            "{ $group: { _id: '$sponsorId', count: { $sum: 1 } } }",
            "{ $project: { id: '$_id', count: '$count', _id: 0 } }"
    })
    List<SponsorApplicationCount> countApplicationsBySponsor();

    @Aggregation(pipeline = {
            "{ $group: { _id: '$applicationId', count: { $sum: 1 } } }",
            "{ $project: { id: '$_id', count: '$count', _id: 0 } }"
    })
    List<ApplicationSponsorCount> countSponsorsByApplication();

}
