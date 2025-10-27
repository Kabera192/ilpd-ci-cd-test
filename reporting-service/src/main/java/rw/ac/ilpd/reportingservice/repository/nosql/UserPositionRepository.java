package rw.ac.ilpd.reportingservice.repository.nosql;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import rw.ac.ilpd.reportingservice.model.nosql.document.UserPosition;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface UserPositionRepository  extends MongoRepository<UserPosition, String> {
    boolean existsByPositionId(String id);

    List<UserPosition> findByUserIdAndQuiteDateIsNull(String userId);

    List<UserPosition> findByQuiteDateIsNullOrQuiteDateIsBefore(LocalDateTime now);

    Page<UserPosition> findByQuiteDateIsNullOrQuiteDateIsBefore(LocalDateTime now, Pageable pageable);

    List<UserPosition> findByUserId( UUID uuid);
    @Query("{ '$expr': { '$lt': [ { '$dayOfMonth': '$createdAt' }, ?0 ] } }")
    List<UserPosition> findByQuittingDayBefore(int days);

    List<UserPosition> findByQuiteDateBefore(LocalDateTime now);

    List<UserPosition> findByPositionIdAndQuiteDateNotNullOrQuiteDateBefore(String positionId,LocalDateTime now);
}
