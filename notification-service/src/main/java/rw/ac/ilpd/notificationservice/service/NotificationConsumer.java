package rw.ac.ilpd.notificationservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.notificationservice.config.RabbitMQConfig;
import rw.ac.ilpd.notificationservice.notificationsender.NotificationSender;
import rw.ac.ilpd.notificationservice.notificationsender.NotificationSenderFactory;
import rw.ac.ilpd.sharedlibrary.dto.notification.NotificationRequest;
import rw.ac.ilpd.sharedlibrary.dto.notification.NotificationResponse;
import rw.ac.ilpd.sharedlibrary.event.DeletedEvent;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer
{
    // We list all the entities corresponding to all the foreign keys we have in this microservice's tables
    // The name should match the filename coz when we publish we check the filename of the guy we want to delete, then we will not have conflicts
    // No need to list an entity if the parent is using soft delete
    Set<String> foreignEntitiesInThisMicroservice = Set.of(
            "UserEntity",
            "DocumentType",
            "Notification",
            "NotificationType",
            "RequestType",
            "Intake",
            "Module"
    );

    private final NotificationSenderFactory notificationSenderFactory;

    // Services for deletion
    private final NotificationService notificationService;
    private final ReceivedNotificationService receivedNotificationService;
    private final RequestService requestService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void handleNotificationEvent(NotificationRequest notificationRequest)
    {
        log.info("Received notification event: {}", notificationRequest);
        NotificationResponse response = notificationService.createNotification(notificationRequest);
        NotificationSender sender = notificationSenderFactory.getSender(response.getNotificationType());
        sender.sendNotification(response);
    }

    @RabbitListener(queues = RabbitMQConfig.DELETED_QUEUE_NAME)
    public void handleDeletedEvent(DeletedEvent deletedEvent)
    {
        log.info("Received Deleted event: {}", deletedEvent);

        String tableName = deletedEvent.getTableName();
        String uuid = deletedEvent.getId(); // we will map it to uuid if needed

        // check if the deleted event someone has published concerns us
        if (foreignEntitiesInThisMicroservice.contains(tableName)) {
            switch (tableName) {
                case "UserEntity":
                    // for all the tables that reference UserEntity, delete or set null the corresponding row
                    notificationService.deleteWhereSenderIdIs(UUID.fromString(uuid));
                    receivedNotificationService.deleteWhereUserIdIs(UUID.fromString(uuid));
                    requestService.deleteWhereCreatedByIs(UUID.fromString(uuid));
                    break;
                case "DocumentType":
                    // for all the tables that reference DocumentType, delete or set null the corresponding row
                    break;
                case "Notification":
                    // for all the tables that reference Notification, delete or set null the corresponding row
                    break;
                case "NotificationType":
                    // for all the tables that reference NotificationType, delete or set null the corresponding row
                    break;
                case "RequestType":
                    // for all the tables that reference RequestType, delete or set null the corresponding row
                    break;
                case "Intake":
                    // for all the tables that reference Intake, delete or set null the corresponding row
                    break;
                case "Module":
                    // for all the tables that reference Module, delete or set null the corresponding row
                    requestService.deleteWhereModuleIdIs(UUID.fromString(uuid));
                    break;
                default:
                    break;
            }
        }
    }
}
