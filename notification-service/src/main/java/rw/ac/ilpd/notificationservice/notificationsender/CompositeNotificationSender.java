package rw.ac.ilpd.notificationservice.notificationsender;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import rw.ac.ilpd.sharedlibrary.dto.notification.NotificationResponse;
import rw.ac.ilpd.sharedlibrary.enums.NotificationTypeEnum;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CompositeNotificationSender implements NotificationSender
{
    private final List<NotificationSender> senders;

    @PostConstruct
    public void init()
    {
        // remove the CompositeNotificationSender from the list to avoid an infinite loop.
        senders.removeIf(sender -> sender instanceof CompositeNotificationSender);
    }

    @Override
    public void sendNotification(NotificationResponse notification)
    {
        for (NotificationSender sender : senders)
        {
            if (sender.supports(notification.getNotificationType()))
            {
                sender.sendNotification(notification);
            }
        }
    }

    @Override
    public boolean supports(NotificationTypeEnum type)
    {
        return true;
    }
}
