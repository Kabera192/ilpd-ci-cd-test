/*
 * File: NotificationService.java
 *
 * Description: Service class for handling business logic related to notifications.
 *              Provides methods for creating, retrieving, updating, and deleting notifications.
 *              Includes pagination and sorting for listing notifications.
 */
package rw.ac.ilpd.notificationservice.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.notificationservice.model.nosql.document.Notification;
import rw.ac.ilpd.notificationservice.repository.nosql.NotificationRepository;
import rw.ac.ilpd.notificationservice.mapper.NotificationMapper;
import rw.ac.ilpd.sharedlibrary.dto.notification.NotificationRequest;
import rw.ac.ilpd.sharedlibrary.dto.notification.NotificationResponse;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService
{
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    /**
     * Creates a new notification from the provided request DTO.
     *
     * @param request The notification request DTO
     * @return The created notification response DTO
     * @throws IllegalArgumentException if the request is invalid
     */
    public NotificationResponse createNotification(@Valid NotificationRequest request)
    {
        log.info("Creating notification with title: {}", request.getTitle());

        // TODO: VALIDATE WHETHER THE NOTIFICATION DESTINATIONS REALLY EXIST

        Notification notification = notificationMapper.toNotification(request);
        Notification savedNotification = notificationRepository.save(notification);

        // log.debug("Notification created successfully with ID: {}", savedNotification.getId());
        return notificationMapper.fromNotification(savedNotification);
    }

    /**
     * Retrieves a notification by its ID.
     *
     * @param id The ID of the notification
     * @return The notification response DTO
     * @throws EntityNotFoundException if the notification is not found
     */
    public NotificationResponse getNotificationById(String id)
    {
        log.info("Retrieving notification with ID: {}", id);

        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() ->
                {
                    log.error("Notification with ID {} not found", id);
                    return new EntityNotFoundException("Notification with ID " + id + " not found");
                });

        return notificationMapper.fromNotification(notification);
    }

    /**
     * Retrieves a paginated and sorted list of notifications.
     *
     * @param page    The page number (0-based)
     * @param size    The page size
     * @param sortBy  The field to sort by
     * @param sortDir The sort direction (asc or desc)
     * @return A page of notification response DTOs
     */
    public Page<NotificationResponse> getAllNotifications(int page, int size, String sortBy,
                                                          String sortDir)
    {
        log.info("Retrieving notifications with page: {}, size: {}, sortBy: {}, sortDir: {}"
                , page, size, sortBy, sortDir);

        Sort sort = Sort.by(sortDir.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC, sortBy);

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Notification> notifications = notificationRepository.findAll(pageable);
        return notifications.map(notificationMapper::fromNotification);
    }

    /**
     * Updates an existing notification.
     *
     * @param id      The ID of the notification to update
     * @param request The updated notification request DTO
     * @return The updated notification response DTO
     * @throws EntityNotFoundException if the notification is not found
     */
    public NotificationResponse updateNotification(String id, NotificationRequest request)
    {
        log.info("Updating notification with ID: {}", id);

        Notification existingNotification = notificationRepository.findById(id)
                .orElseThrow(() ->
                {
                    log.error("Notification with ID {} not found", id);
                    return new EntityNotFoundException("Notification with ID " + id + " not found");
                });

        existingNotification.setTitle(request.getTitle());
        existingNotification.setContent(request.getContent());
        existingNotification.setSenderId(request.getSenderId());
        existingNotification.setNotificationType(request.getNotificationType());

        // TODO: CHECK IF DESTINATIONS HAVE CHANGED & VALIDATE WHETHER THEY REALLY EXIST

        Notification updatedNotification = notificationRepository.save(existingNotification);
        log.debug("Notification updated successfully with ID: {}", updatedNotification.getId());
        return notificationMapper.fromNotification(updatedNotification);
    }

    /**
     * Deletes a notification by its ID.
     *
     * @param id The ID of the notification to delete
     * @throws EntityNotFoundException if the notification is not found
     */
    public boolean deleteNotification(String id)
    {
        log.info("Deleting notification with ID: {}", id);

        if (!notificationRepository.existsById(id))
        {
            log.error("Notification with ID {} not found", id);
            throw new EntityNotFoundException("Notification with ID " + id + " not found");
        }

        notificationRepository.deleteById(id);
        log.debug("Notification deleted successfully with ID: {}", id);
        return true;
    }

    /*
    * Utility function to be used by other service classes to retrieve a notification by id.
    */
    public Optional<Notification> getEntity(String id)
    {
        log.info("Retrieving notification with ID: {}", id);
        return notificationRepository.findById(id);
    }

    public void deleteWhereSenderIdIs(UUID senderId){
        notificationRepository.deleteAllBySenderId(senderId);
    }
}