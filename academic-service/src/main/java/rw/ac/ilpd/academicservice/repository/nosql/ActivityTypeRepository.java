package rw.ac.ilpd.academicservice.repository.nosql;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.academicservice.model.nosql.document.ActivityType;

import java.util.Optional;

@Repository
public interface ActivityTypeRepository extends MongoRepository<ActivityType, String> {
    Optional<ActivityType> findByIdAndIsDeleted(String id, boolean isDeleted);

    Optional<ActivityType> findByNameAndIsDeleted(String name, boolean isDeleted);

    Page<ActivityType> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String search, String search1, Pageable pageable);

    Page<ActivityType> findByIsDeleted(boolean deleteStatus, Pageable pageable);

    Page<ActivityType> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndIsDeleted(String search, String search1, boolean deleteStatus, Pageable pageable);

    boolean existsByName(String name);
}
