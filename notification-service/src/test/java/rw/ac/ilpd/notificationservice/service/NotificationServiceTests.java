package rw.ac.ilpd.notificationservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.bind.MethodArgumentNotValidException;
import rw.ac.ilpd.notificationservice.mapper.NotificationMapper;
import rw.ac.ilpd.notificationservice.model.nosql.document.Notification;
import rw.ac.ilpd.notificationservice.model.nosql.embedding.NotificationDestination;
import rw.ac.ilpd.notificationservice.repository.nosql.NotificationRepository;
import rw.ac.ilpd.sharedlibrary.dto.notification.NotificationDestinationRequest;
import rw.ac.ilpd.sharedlibrary.dto.notification.NotificationDestinationResponse;
import rw.ac.ilpd.sharedlibrary.dto.notification.NotificationRequest;
import rw.ac.ilpd.sharedlibrary.dto.notification.NotificationResponse;
import rw.ac.ilpd.sharedlibrary.enums.NotificationTypeEnum;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTests
{
    @Mock
    private NotificationRepository notificationRepo;

    @Mock
    private NotificationMapper notificationMapper;

    @InjectMocks
    private NotificationService notificationService;

    private Notification notification;
    private NotificationRequest request;
    private NotificationResponse response;

    @BeforeEach
    public void setUp() {
        // Create test data
        request = NotificationRequest.builder()
                .title("Test Title")
                .content("Test Content")
                .senderId(UUID.randomUUID())
                .notificationType(NotificationTypeEnum.EMAIL)
                .destinations(Collections.singletonList(
                        NotificationDestinationRequest.builder()
                                .userId(UUID.randomUUID())
                                .build()
                ))
                .build();

        notification = Notification.builder()
                .title("Test Title")
                .content("Test Content")
                .senderId(UUID.randomUUID())
                .notificationType(NotificationTypeEnum.SMS)
                .destinations(Collections.singletonList(
                        NotificationDestination.builder()
                                .userId(UUID.randomUUID())
                                .build()
                ))
                .build();

        response = NotificationResponse.builder()
                .title("Test Title")
                .content("Test Content")
                .senderId(UUID.randomUUID())
                .notificationType(NotificationTypeEnum.EMAIL)
                .destinations(Collections.singletonList(
                        NotificationDestinationResponse.builder()
                                .userId(UUID.randomUUID())
                                .build()
                ))
                .build();
    }

    @Test
    void NotificationService_SaveNotification_ReturnsNotificationResponse()
    {
        when(notificationMapper.toNotification(request)).thenReturn(notification);
        when(notificationRepo.save(notification)).thenReturn(notification);
        when(notificationMapper.fromNotification(notification)).thenReturn(response);

        NotificationResponse result = notificationService.createNotification(request);

        assertNotNull(result);
    }
}
