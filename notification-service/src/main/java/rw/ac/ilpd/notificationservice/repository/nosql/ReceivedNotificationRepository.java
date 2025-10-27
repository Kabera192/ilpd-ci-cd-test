package rw.ac.ilpd.notificationservice.repository.nosql;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import rw.ac.ilpd.notificationservice.model.nosql.document.ReceivedNotification;

import java.util.UUID;

public interface ReceivedNotificationRepository extends MongoRepository<ReceivedNotification, String>
{
    Page<ReceivedNotification> findByUserId(UUID userId, Pageable pageable);
    void deleteAllByUserId(UUID id);
}
