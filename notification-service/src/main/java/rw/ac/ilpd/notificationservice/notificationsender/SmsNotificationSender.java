package rw.ac.ilpd.notificationservice.notificationsender;

import org.springframework.stereotype.Component;
import rw.ac.ilpd.sharedlibrary.dto.notification.NotificationResponse;
import rw.ac.ilpd.sharedlibrary.enums.NotificationTypeEnum;

/*
* Concrete implementation of the NotificationSender interface that
* handles sending SMS notifications.
* */
@Component
public class SmsNotificationSender implements NotificationSender
{
    @Override
    public void sendNotification(NotificationResponse notification)
    {
        // TODO: LOGIC TO SEND SMS NOTIFICATIONS
    }

    @Override
    public boolean supports(NotificationTypeEnum type)
    {
        return type.equals(NotificationTypeEnum.SMS) ||
                type.equals(NotificationTypeEnum.SMS_PUSH) ||
                type.equals(NotificationTypeEnum.SMS_EMAIL) ||
                type.equals(NotificationTypeEnum.SMS_EMAIL_PUSH);
    }
}
