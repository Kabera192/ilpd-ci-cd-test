package rw.ac.ilpd.mis.auth.repository.mongo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import rw.ac.ilpd.mis.auth.entity.mongo.WorkExperience;
import java.util.Optional;

public interface WorkExperienceRepository  extends MongoRepository<WorkExperience, String> {


    Page<WorkExperience> findByUserId(String currentUserId, Pageable pageable);

    Page<WorkExperience> findByCompanyNameContainingIgnoreCaseOrPositionTitleContainingIgnoreCaseOrWorkModeContainingIgnoreCaseAndUserId(String search, String search1, String search2, String currentUserId, Pageable pageable);

    Optional<WorkExperience> findByIdAndUserId(String id, String currentUserId);
}
