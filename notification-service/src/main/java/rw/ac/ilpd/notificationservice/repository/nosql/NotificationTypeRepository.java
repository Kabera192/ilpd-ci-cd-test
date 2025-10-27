package rw.ac.ilpd.notificationservice.repository.nosql;

import org.springframework.data.mongodb.repository.MongoRepository;
import rw.ac.ilpd.notificationservice.model.nosql.document.NotificationType;

public interface NotificationTypeRepository extends MongoRepository<NotificationType, String>
{
}
