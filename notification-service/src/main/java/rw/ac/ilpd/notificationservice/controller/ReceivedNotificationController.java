/*
 * File: ReceivedNotificationController.java
 *
 * Description: REST controller for handling received notification-related HTTP requests.
 *              Provides endpoints for creating, retrieving, and deleting received notification records.
 *              Supports pagination and sorting for listing received notifications.
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
import rw.ac.ilpd.notificationservice.service.ReceivedNotificationService;
import rw.ac.ilpd.sharedlibrary.dto.notification.ReceivedNotificationRequest;
import rw.ac.ilpd.sharedlibrary.dto.notification.ReceivedNotificationResponse;

import java.util.UUID;

@Tag(name = "Received Notifications", description = "API for managing received notifications in the MIS")
@RestController
@RequestMapping("/received-notifications")
@RequiredArgsConstructor
@Slf4j
public class ReceivedNotificationController
{

    private final ReceivedNotificationService receivedNotificationService;

    @Operation(
            summary = "Create a new received notification record",
            description = "Creates a record indicating a user has received a notification."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Received notification created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Referenced notification not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<ReceivedNotificationResponse> createReceivedNotification(
            @Valid @RequestBody ReceivedNotificationRequest request)
    {
        log.info("Received request to create received notification for notification ID: {}", request.getNotificationId());
        ReceivedNotificationResponse response = receivedNotificationService.createReceivedNotification(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Retrieve a received notification by ID",
            description = "Fetches a received notification record by its unique ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Received notification retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Received notification not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ReceivedNotificationResponse> getReceivedNotificationById(
            @Parameter(description = "ID of the received notification to retrieve") @PathVariable String id)
    {
        log.info("Received request to retrieve received notification with ID: {}", id);
        ReceivedNotificationResponse response = receivedNotificationService.getReceivedNotificationById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Retrieve received notifications for a user",
            description = "Fetches a paginated and sorted list of received notifications for a specific user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Received notifications retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination or sorting parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<ReceivedNotificationResponse>> getReceivedNotificationsByUserId(
            @Parameter(description = "ID of the user whose received notifications are to be retrieved")
            @PathVariable UUID userId,
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Field to sort by", example = "createdAt")
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Sort direction (asc or desc)", example = "desc")
            @RequestParam(defaultValue = "desc") String order)
    {
        log.info("Received request to retrieve received notifications for user ID: {} with page: {}, size: {}, sortBy: {}, order: {}",
                userId, page, size, sortBy, order);
        Page<ReceivedNotificationResponse> response = receivedNotificationService.getReceivedNotificationsByUserId(userId, page, size, sortBy, order);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Retrieve all received notifications",
            description = "Fetches a paginated and sorted list of all received notifications."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Received notifications retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination or sorting parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<Page<ReceivedNotificationResponse>> getAllReceivedNotifications(
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Field to sort by", example = "createdAt")
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Sort direction (asc or desc)", example = "desc")
            @RequestParam(defaultValue = "desc") String order)
    {
        log.info("Received request to retrieve all received notifications with page: {}, size: {}, sortBy: {}, order: {}",
                page, size, sortBy, order);
        Page<ReceivedNotificationResponse> response = receivedNotificationService
                .getAllReceivedNotifications(page, size, sortBy, order);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Delete a received notification",
            description = "Deletes a received notification record by its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Received notification deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Received notification not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReceivedNotification(
            @Parameter(description = "ID of the received notification to delete") @PathVariable String id)
    {
        log.info("Received request to delete received notification with ID: {}", id);
        receivedNotificationService.deleteReceivedNotification(id);
        return ResponseEntity.noContent().build();
    }
}