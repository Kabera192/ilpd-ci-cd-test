/*
 * File: NotificationController.java
 *
 * Description: REST controller for handling notification-related HTTP requests.
 *              Provides endpoints for creating, retrieving, updating, and deleting notifications.
 *              Supports pagination and sorting for listing notifications.
 *              Includes Swagger annotations for API documentation.
 */
package rw.ac.ilpd.notificationservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.notificationservice.service.NotificationService;
import rw.ac.ilpd.sharedlibrary.dto.notification.NotificationRequest;
import rw.ac.ilpd.sharedlibrary.dto.notification.NotificationResponse;

@Tag(name = "Notifications", description = "API for managing notifications in the MIS")
@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
@Slf4j
public class NotificationController
{

    private final NotificationService notificationService;

    @Operation(
            summary = "Create a new notification",
            description = "Creates a new notification with the provided details."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Notification created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<NotificationResponse> createNotification(
            @Valid @RequestBody NotificationRequest request)
    {
        log.info("Received request to create notification with title: {}", request.getTitle());
        NotificationResponse response = notificationService.createNotification(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Retrieve a notification by ID",
            description = "Fetches a notification by its unique ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notification retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Notification not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponse> getNotificationById(
            @Parameter(description = "ID of the notification to retrieve") @PathVariable String id)
    {
        log.info("Received request to retrieve notification with ID: {}", id);
        NotificationResponse response = notificationService.getNotificationById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Retrieve a paginated list of notifications",
            description = "Fetches a paginated and sorted list of notifications."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notifications retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination or sorting parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<Page<NotificationResponse>> getAllNotifications(
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Field to sort by", example = "createdAt")
            @RequestParam(name = "sort-by", defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Sort direction (asc or desc)", example = "desc")
            @RequestParam(defaultValue = "desc") String order)
    {
        log.info("Received request to retrieve notifications with page: {}, size: {}, sortBy: {}, order: {}",
                page, size, sortBy, order);

        Page<NotificationResponse> response = notificationService.getAllNotifications(page, size, sortBy, order);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Update an existing notification",
            description = "Updates the details of an existing notification by its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notification updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Notification not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<NotificationResponse> updateNotification(
            @Parameter(description = "ID of the notification to update") @PathVariable String id,
            @Valid @RequestBody NotificationRequest request)
    {
        log.info("Received request to update notification with ID: {}", id);
        NotificationResponse response = notificationService.updateNotification(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Delete a notification",
            description = "Deletes a notification by its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Notification deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Notification not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteNotification(
            @Parameter(description = "ID of the notification to delete") @PathVariable String id)
    {
        log.info("Received request to delete notification with ID: {}", id);
        return new ResponseEntity<>(notificationService.deleteNotification(id), HttpStatus.NO_CONTENT);
    }
}