/*
 * File: ReceivedNotificationService.java
 *
 * Description: Service class for handling business logic related to received notifications.
 *              Provides methods for creating, retrieving, and listing received notification records.
 *              Includes pagination and sorting for listing received notifications.
 */
package rw.ac.ilpd.notificationservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.notificationservice.mapper.ReceivedNotificationMapper;
import rw.ac.ilpd.notificationservice.model.nosql.document.ReceivedNotification;
import rw.ac.ilpd.notificationservice.repository.nosql.ReceivedNotificationRepository;
import rw.ac.ilpd.sharedlibrary.dto.notification.ReceivedNotificationRequest;
import rw.ac.ilpd.sharedlibrary.dto.notification.ReceivedNotificationResponse;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReceivedNotificationService
{
    private final ReceivedNotificationRepository receivedNotificationRepository;
    private final ReceivedNotificationMapper receivedNotificationMapper;
    private final NotificationService notificationService;

    /**
     * Creates a new received notification record.
     *
     * @param request The received notification request DTO
     * @return The created received notification response DTO
     * @throws EntityNotFoundException if the referenced notification is not found
     * @throws IllegalArgumentException      if the request is invalid
     */
    public ReceivedNotificationResponse createReceivedNotification(ReceivedNotificationRequest request)
    {
        log.info("Creating received notification for notification ID: {} and user ID: {}",
                request.getNotificationId(), request.getUserId());

        // Validate that the referenced notification exists
        notificationService.getNotificationById(request.getNotificationId());

        ReceivedNotification receivedNotification = receivedNotificationMapper.toReceivedNotification(request);
        ReceivedNotification savedReceivedNotification = receivedNotificationRepository.save(receivedNotification);
        log.debug("Received notification created successfully with ID: {}", savedReceivedNotification.getId());
        return receivedNotificationMapper.fromReceivedNotification(savedReceivedNotification);
    }

    /**
     * Retrieves a received notification by its ID.
     *
     * @param id The ID of the received notification
     * @return The received notification response DTO
     * @throws EntityNotFoundException if the received notification is not found
     */
    public ReceivedNotificationResponse getReceivedNotificationById(String id)
    {
        log.info("Retrieving received notification with ID: {}", id);
        ReceivedNotification receivedNotification = receivedNotificationRepository.findById(id)
                .orElseThrow(() ->
                {
                    log.error("Received notification with ID {} not found", id);
                    return new EntityNotFoundException("Received notification with ID " + id + " not found");
                });
        return receivedNotificationMapper.fromReceivedNotification(receivedNotification);
    }

    /**
     * Retrieves a paginated and sorted list of received notifications for a specific user.
     *
     * @param userId  The ID of the user
     * @param page    The page number (0-based)
     * @param size    The page size
     * @param sortBy  The field to sort by
     * @param sortDir The sort direction (asc or desc)
     * @return A page of received notification response DTOs
     */
    public Page<ReceivedNotificationResponse> getReceivedNotificationsByUserId(
            UUID userId, int page, int size, String sortBy, String sortDir)
    {
        log.info("Retrieving received notifications for user ID: {} with page: {}, size: {}, sortBy: {}, sortDir: {}",
                userId, page, size, sortBy, sortDir);
        Sort sort = Sort.by(sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ReceivedNotification> receivedNotifications = receivedNotificationRepository.findByUserId(userId, pageable);
        return receivedNotifications.map(receivedNotificationMapper::fromReceivedNotification);
    }

    /**
     * Retrieves a paginated and sorted list of all received notifications.
     *
     * @param page    The page number (0-based)
     * @param size    The page size
     * @param sortBy  The field to sort by
     * @param sortDir The sort direction (asc or desc)
     * @return A page of received notification response DTOs
     */
    public Page<ReceivedNotificationResponse> getAllReceivedNotifications(int page, int size, String sortBy, String sortDir)
    {
        log.info("Retrieving all received notifications with page: {}, size: {}, sortBy: {}, sortDir: {}",
                page, size, sortBy, sortDir);
        Sort sort = Sort.by(sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ReceivedNotification> receivedNotifications = receivedNotificationRepository.findAll(pageable);
        return receivedNotifications.map(receivedNotificationMapper::fromReceivedNotification);
    }

    /**
     * Deletes a received notification by its ID.
     *
     * @param id The ID of the received notification to delete
     * @throws EntityNotFoundException if the received notification is not found
     */
    public void deleteReceivedNotification(String id)
    {
        log.info("Deleting received notification with ID: {}", id);
        if (!receivedNotificationRepository.existsById(id))
        {
            log.error("Received notification with ID {} not found", id);
            throw new EntityNotFoundException("Received notification with ID " + id + " not found");
        }
        receivedNotificationRepository.deleteById(id);
        log.debug("Received notification deleted successfully with ID: {}", id);
    }

    public void deleteWhereUserIdIs(UUID id){
        receivedNotificationRepository.deleteAllByUserId(id);
    }
}