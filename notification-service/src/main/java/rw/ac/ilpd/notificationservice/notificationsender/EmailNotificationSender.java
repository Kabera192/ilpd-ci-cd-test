package rw.ac.ilpd.notificationservice.notificationsender;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import rw.ac.ilpd.sharedlibrary.dto.notification.NotificationResponse;
import rw.ac.ilpd.sharedlibrary.enums.NotificationTypeEnum;

/*
 * Concrete implementation of the NotificationSender interface that
 * handles sending Email notifications.
 * */
@Component
@RequiredArgsConstructor
@Slf4j
public class EmailNotificationSender implements NotificationSender
{
    private final JavaMailSender mailSender;

    @Override
    public void sendNotification(NotificationResponse notification)
    {
        SimpleMailMessage message = new SimpleMailMessage();

        // TODO: I should be getting the user email from the auth service
        message.setTo("example@domain.com");
        message.setSubject(notification.getTitle());
        message.setText(notification.getContent());

        try
        {
            mailSender.send(message);
            log.info("Email sent successfully");
        }
        catch (MailException e)
        {
            log.error("Couldn't send email. Root cause: {}", e.getMessage());
        }
    }

    @Override
    public boolean supports(NotificationTypeEnum type)
    {
        return type.equals(NotificationTypeEnum.EMAIL) ||
                type.equals(NotificationTypeEnum.EMAIL_PUSH) ||
                type.equals(NotificationTypeEnum.SMS_EMAIL) ||
                type.equals(NotificationTypeEnum.SMS_EMAIL_PUSH);
    }
}
