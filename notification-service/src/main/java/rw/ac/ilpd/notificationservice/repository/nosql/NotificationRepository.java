package rw.ac.ilpd.notificationservice.repository.nosql;

import org.springframework.data.mongodb.repository.MongoRepository;
import rw.ac.ilpd.notificationservice.model.nosql.document.Notification;

import java.util.UUID;

public interface NotificationRepository extends MongoRepository<Notification, String>
{
    void deleteAllBySenderId(UUID id);
}
