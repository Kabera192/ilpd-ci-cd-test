package rw.ac.ilpd.notificationservice.notificationsender;

import org.springframework.stereotype.Component;
import rw.ac.ilpd.sharedlibrary.enums.NotificationTypeEnum;

import java.util.Map;

@Component
public class NotificationSenderFactory
{
    private final Map<String, NotificationSender> senders;

    public NotificationSenderFactory(Map<String, NotificationSender> senders)
    {
        this.senders = senders;
    }

    public NotificationSender getSender(NotificationTypeEnum type)
    {
        String key;
        switch (type)
        {
            case PUSH:
                key = "inAppNotificationSender";
                break;
            case EMAIL:
                key = "emailNotificationSender";
                break;
            case SMS:
                key = "smsNotificationSender";
                break;
            default:
                key = "compositeNotificationSender";
        }
        return senders.getOrDefault(key, senders.get("compositeNotificationSender"));
    }
}