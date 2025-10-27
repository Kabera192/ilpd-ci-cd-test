package rw.ac.ilpd.registrationservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.registrationservice.model.nosql.document.ApplicationSponsor;
import rw.ac.ilpd.registrationservice.projection.ApplicationSponsorCount;
import rw.ac.ilpd.registrationservice.projection.SponsorApplicationCount;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationSponsorRepository extends MongoRepository<ApplicationSponsor, String> {

    // Basic queries
    List<ApplicationSponsor> findByApplicationId(String applicationId);

    List<ApplicationSponsor> findBySponsorId(String sponsorId);

    Optional<ApplicationSponsor> findByApplicationIdAndSponsorId(String applicationId, String sponsorId);

    List<ApplicationSponsor> findByRecommendationLetterId(String recommendationLetterId);

    // Paginated queries
    Page<ApplicationSponsor> findByApplicationId(String applicationId, Pageable pageable);

    Page<ApplicationSponsor> findBySponsorId(String sponsorId, Pageable pageable);

    // Count queries
    long countByApplicationId(String applicationId);

    long countBySponsorId(String sponsorId);

    long countByRecommendationLetterId(String recommendationLetterId);

    // Aggregation queries
    @Aggregation(pipeline = {"{ $group: { _id: '$sponsorId', applications: { $push: '$applicationId' }, count: { " +
            "$sum: 1 } } }", "{ $sort: { count: -1 } }"})
    List<SponsorApplicationCount> countApplicationsBySponsor();

    @Aggregation(pipeline = {"{ $group: { _id: '$applicationId', sponsors: { $push: '$sponsorId' }, count: { $sum: 1 "
            + "} } }", "{ $sort: { count: -1 } }"})
    List<ApplicationSponsorCount> countSponsorsByApplication();

    // Existence checks
    boolean existsByApplicationIdAndSponsorId(String applicationId, String sponsorId);

    boolean existsByApplicationId(String applicationId);

    boolean existsBySponsorId(String sponsorId);

    boolean existsByRecommendationLetterId(String recommendationLetterId);

    // Batch operations
    List<ApplicationSponsor> findByApplicationIdIn(List<String> applicationIds);

    List<ApplicationSponsor> findBySponsorIdIn(List<String> sponsorIds);

    // Custom delete operations
    void deleteByApplicationId(String applicationId);

    void deleteBySponsorId(String sponsorId);

    void deleteByApplicationIdAndSponsorId(String applicationId, String sponsorId);

    void deleteByRecommendationLetterId(String recommendationLetterId);
}

