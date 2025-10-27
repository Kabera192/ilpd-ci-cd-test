package rw.ac.ilpd.registrationservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.registrationservice.model.nosql.document.ApplicationDocumentSubmission;
import rw.ac.ilpd.registrationservice.projection.DailyDocumentCount;
import rw.ac.ilpd.registrationservice.projection.DocumentStatusCount;
import rw.ac.ilpd.registrationservice.projection.DocumentTypeCount;
import rw.ac.ilpd.sharedlibrary.enums.DocumentVerificationStatus;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ApplicationDocumentSubmissionRepository extends
        MongoRepository<ApplicationDocumentSubmission, String>
{

    // Basic queries - CORRECTED field names and types
    List<ApplicationDocumentSubmission> findByDocumentId(String documentId);

    List<ApplicationDocumentSubmission> findByRequiredDocNameId(String requiredDocNameId);

    List<ApplicationDocumentSubmission> findByVerificationStatus(DocumentVerificationStatus status);

    // NEW: Version-related queries
    @Query("{ 'versions.documentId': ?0 }")
    List<ApplicationDocumentSubmission> findByVersionsContainingDocumentId(String documentId);

    @Query("{ 'currentVersion': ?0 }")
    List<ApplicationDocumentSubmission> findByCurrentVersion(Integer currentVersion);

    // Advanced search queries - CORRECTED field names
    @Query("{ '$and': [" + "  { 'requiredDocNameId': ?0 }," + "  { 'verificationStatus': ?1 }" + "] }")
    List<ApplicationDocumentSubmission> findByRequiredDocNameIdAndStatus(String requiredDocNameId,
                                                                         DocumentVerificationStatus status);

    @Query("{ 'createdAt': { $gte: ?0, $lte: ?1 } }")
    List<ApplicationDocumentSubmission> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("{ 'createdAt': { $gte: ?0, $lte: ?1 }, 'verificationStatus': ?2 }")
    List<ApplicationDocumentSubmission> findByCreatedAtBetweenAndStatus(LocalDateTime startDate,
                                                                        LocalDateTime endDate,
                                                                        DocumentVerificationStatus status);

    // Paginated queries - CORRECTED field names
    Page<ApplicationDocumentSubmission> findByVerificationStatus(DocumentVerificationStatus status, Pageable pageable);

    Page<ApplicationDocumentSubmission> findByRequiredDocNameId(String requiredDocNameId, Pageable pageable);

    Page<ApplicationDocumentSubmission> findByDocumentId(String documentId, Pageable pageable);

    // NEW: Paginated version queries
    @Query("{ 'versions.documentId': ?0 }")
    Page<ApplicationDocumentSubmission> findByVersionsContainingDocumentId(String documentId, Pageable pageable);

    // Count queries - CORRECTED field names
    long countByVerificationStatus(DocumentVerificationStatus status);

    long countByRequiredDocNameId(String requiredDocNameId);

    long countByDocumentId(String documentId);

    @Query(value = "{ 'createdAt': { $gte: ?0, $lte: ?1 } }", count = true)
    long countByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "{ 'createdAt': { $gte: ?0, $lte: ?1 }, 'verificationStatus': ?2 }", count = true)
    long countByCreatedAtBetweenAndStatus(LocalDateTime startDate, LocalDateTime endDate,
                                          DocumentVerificationStatus status);

    // NEW: Count queries for versioning
    @Query(value = "{ 'versions.documentId': ?0 }", count = true)
    long countByVersionsContainingDocumentId(String documentId);

    // Aggregation queries for analytics
    @Aggregation(pipeline = {"{ $group: { _id: '$verificationStatus', count: { $sum: 1 } } }", "{ $sort: { count: -1 " +
            "} }"})
    List<DocumentStatusCount> countDocumentsByStatus();

    @Aggregation(pipeline = {"{ $group: { _id: '$requiredDocNameId', count: { $sum: 1 } } }", "{ $sort: { " +
            "count:" + " -1 } }"})
    List<DocumentTypeCount> countDocumentsByType();

    @Aggregation(pipeline = {"{ $match: { 'createdAt': { $gte: ?0, $lte: ?1 } } }", "{ $group: { _id: { " +
            "$dateToString: { format: '%Y-%m-%d', date: '$createdAt' } }, count: { $sum: 1 } } }", "{ $sort: { " +
            "'_id':" + " 1 } }"})
    List<DailyDocumentCount> countDocumentsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    // Existence checks - CORRECTED field names
    boolean existsByDocumentId(String documentId);

    boolean existsByRequiredDocNameId(String requiredDocNameId);

    // Sorting - CORRECTED field names
    List<ApplicationDocumentSubmission> findAllByOrderByCreatedAtDesc();

    List<ApplicationDocumentSubmission> findByVerificationStatusOrderByCreatedAtDesc(DocumentVerificationStatus status);

    List<ApplicationDocumentSubmission> findByRequiredDocNameIdOrderByCreatedAtDesc(String requiredDocNameId);

    // Custom delete operations - CORRECTED field names
    void deleteByDocumentId(String documentId);

    void deleteByRequiredDocNameId(String requiredDocNameId);

    // BACKWARD COMPATIBILITY: Keep old method names as deprecated for smooth migration
    /**
     * @deprecated Use findByRequiredDocNameId(String) instead
     */
    @Deprecated
    default List<ApplicationDocumentSubmission> findByDocumentRequiredNameId(String requiredDocNameId) {
        return findByRequiredDocNameId(requiredDocNameId);
    }

    /**
     * @deprecated Use findByRequiredDocNameId(String, Pageable) instead
     */
    @Deprecated
    default Page<ApplicationDocumentSubmission> findByDocumentRequiredNameId(String requiredDocNameId, Pageable pageable) {
        return findByRequiredDocNameId(requiredDocNameId, pageable);
    }

    /**
     * @deprecated Use countByRequiredDocNameId(String) instead
     */
    @Deprecated
    default long countByDocumentRequiredNameId(String requiredDocNameId) {
        return countByRequiredDocNameId(requiredDocNameId);
    }

    /**
     * @deprecated Use findByRequiredDocNameIdAndStatus(String, DocumentVerificationStatus) instead
     */
    @Deprecated
    default List<ApplicationDocumentSubmission> findByDocumentRequiredNameIdAndStatus(String requiredDocNameId,
                                                                                      DocumentVerificationStatus status) {
        return findByRequiredDocNameIdAndStatus(requiredDocNameId, status);
    }

    /**
     * @deprecated Use findByRequiredDocNameIdOrderByCreatedAtDesc(String) instead
     */
    @Deprecated
    default List<ApplicationDocumentSubmission> findByDocumentRequiredNameIdOrderByCreatedAtDesc(String requiredDocNameId) {
        return findByRequiredDocNameIdOrderByCreatedAtDesc(requiredDocNameId);
    }

    /**
     * @deprecated Use deleteByRequiredDocNameId(String) instead
     */
    @Deprecated
    default void deleteByDocumentRequiredNameId(String requiredDocNameId) {
        deleteByRequiredDocNameId(requiredDocNameId);
    }

    /**
     * @deprecated Use existsByRequiredDocNameId(String) instead
     */
    @Deprecated
    default boolean existsByDocumentRequiredNameId(String requiredDocNameId) {
        return existsByRequiredDocNameId(requiredDocNameId);
    }
}