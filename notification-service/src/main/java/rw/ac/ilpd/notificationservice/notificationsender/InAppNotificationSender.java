package rw.ac.ilpd.notificationservice.notificationsender;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import rw.ac.ilpd.sharedlibrary.dto.notification.NotificationResponse;
import rw.ac.ilpd.sharedlibrary.enums.NotificationTypeEnum;

import java.util.List;

/*
 * Concrete implementation of the NotificationSender interface that
 * handles sending In-App notifications.
 * */
@Component
@RequiredArgsConstructor
public class InAppNotificationSender implements NotificationSender
{
    private final SimpMessagingTemplate template;
    private final DestinationResolver destinationResolver;

    @Override
    public void sendNotification(NotificationResponse notification)
    {
        List<String> userIds = destinationResolver.resolveDestination(notification.getDestinations());
        if (userIds != null)
        {
            for (String userId : userIds)
            {
                template.convertAndSendToUser(
                        userId,
                        "/queue/notifications",
                        notification.getContent()
                );
            }
        }
    }

    @Override
    public boolean supports(NotificationTypeEnum type)
    {
        return type.equals(NotificationTypeEnum.PUSH) ||
                type.equals(NotificationTypeEnum.EMAIL_PUSH) ||
                type.equals(NotificationTypeEnum.SMS_PUSH) ||
                type.equals(NotificationTypeEnum.SMS_EMAIL_PUSH);
    }
}
