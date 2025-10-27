package rw.ac.ilpd.notificationservice.notificationsender;

import rw.ac.ilpd.notificationservice.model.nosql.document.Notification;
import rw.ac.ilpd.sharedlibrary.dto.notification.NotificationResponse;
import rw.ac.ilpd.sharedlibrary.enums.NotificationTypeEnum;

public interface NotificationSender
{
    /**
     * Method that handles sending of notifications for the various
     * classes that implement this interface.
     *
     * @param notification the notification to be sent by the method
     * */
    void sendNotification(NotificationResponse notification);

    /**
     * Method that helps determine which class should be used to
     * send the notification in question based on the type
     *
     * @param type the enum that represents the type of notification such as:
     *            EMAIL, PUSH, SMS and so on...
     * */
    boolean supports(NotificationTypeEnum type);
}
