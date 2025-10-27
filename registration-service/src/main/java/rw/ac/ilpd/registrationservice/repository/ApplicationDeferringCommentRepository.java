package rw.ac.ilpd.registrationservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.registrationservice.model.nosql.document.ApplicationDeferringComment;
import rw.ac.ilpd.registrationservice.projection.DailyCommentCount;
import rw.ac.ilpd.registrationservice.projection.DocumentCommentCount;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ApplicationDeferringCommentRepository extends MongoRepository<ApplicationDeferringComment, String> {

    // Basic queries
    List<ApplicationDeferringComment> findBySubmittedDocumentId(String submittedDocumentId);

    List<ApplicationDeferringComment> findBySubmittedDocumentIdOrderByCreatedAtDesc(String submittedDocumentId);

    List<ApplicationDeferringComment> findBySubmittedDocumentIdOrderByCreatedAtAsc(String submittedDocumentId);

    // Date range queries
    @Query("{ 'createdAt': { $gte: ?0, $lte: ?1 } }")
    List<ApplicationDeferringComment> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("{ 'createdAt': { $gte: ?0, $lte: ?1 }, 'submittedDocumentId': ?2 }")
    List<ApplicationDeferringComment> findByCreatedAtBetweenAndSubmittedDocumentId(LocalDateTime startDate,
                                                                                   LocalDateTime endDate,
                                                                                   String submittedDocumentId);

    // Search queries
    @Query("{ 'comment': { $regex: ?0, $options: 'i' } }")
    List<ApplicationDeferringComment> findByCommentContaining(String keyword);

    @Query("{ '$and': [" + "  { 'comment': { $regex: ?0, $options: 'i' } }," + "  { 'submittedDocumentId': ?1 }" +
            "]" + " }")
    List<ApplicationDeferringComment> findByCommentContainingAndSubmittedDocumentId(String keyword,
                                                                                    String submittedDocumentId);

    // Paginated queries
    Page<ApplicationDeferringComment> findBySubmittedDocumentId(String submittedDocumentId, Pageable pageable);

    Page<ApplicationDeferringComment> findByCommentContaining(String keyword, Pageable pageable);

    // Count queries
    long countBySubmittedDocumentId(String submittedDocumentId);

    @Query(value = "{ 'createdAt': { $gte: ?0, $lte: ?1 } }", count = true)
    long countByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "{ 'comment': { $regex: ?0, $options: 'i' } }", count = true)
    long countByCommentContaining(String keyword);

    // Aggregation queries
    @Aggregation(pipeline = {"{ $group: { _id: '$submittedDocumentId', count: { $sum: 1 } } }",
            "{ $sort: { count: " + "-1" + " } }"})
    List<DocumentCommentCount> countCommentsByDocument();

    @Aggregation(pipeline = {"{ $match: { 'createdAt': { $gte: ?0, $lte: ?1 } } }", "{ $group: { _id: { " +
            "$dateToString: { format: '%Y-%m-%d', date: '$createdAt' } }, count: { $sum: 1 } } }", "{ $sort: { " +
            "'_id':" + " 1 } }"})
    List<DailyCommentCount> countCommentsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    // Existence checks
    boolean existsBySubmittedDocumentId(String submittedDocumentId);

    // Batch operations
    List<ApplicationDeferringComment> findBySubmittedDocumentIdIn(List<String> submittedDocumentIds);

    // Custom delete operations
    void deleteBySubmittedDocumentId(String submittedDocumentId);

    void deleteByCreatedAtBefore(LocalDateTime date);

    // Sorting
    List<ApplicationDeferringComment> findAllByOrderByCreatedAtDesc();
}

