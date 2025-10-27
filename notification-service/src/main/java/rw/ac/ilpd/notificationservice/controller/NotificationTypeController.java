/*
 * File: NotificationTypeController.java
 *
 * Description: REST controller for handling notification type-related HTTP requests.
 *              Provides endpoints for creating, retrieving, updating, and deleting notification types.
 *              Supports pagination and sorting for listing notification types.
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
import rw.ac.ilpd.notificationservice.service.NotificationTypeService;
import rw.ac.ilpd.sharedlibrary.dto.notification.NotificationTypeRequest;
import rw.ac.ilpd.sharedlibrary.dto.notification.NotificationTypeResponse;

@Tag(name = "Notification Types", description = "API for managing notification types in the MIS")
@RestController
@RequestMapping("/notification-types")
@RequiredArgsConstructor
@Slf4j
public class NotificationTypeController
{
    private final NotificationTypeService notificationTypeService;

    @Operation(
            summary = "Create a new notification type",
            description = "Creates a new notification type with the provided name."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Notification type created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<NotificationTypeResponse> createNotificationType(
            @Valid @RequestBody NotificationTypeRequest request)
    {
        log.info("Received request to create notification type with name: {}", request.getName());
        NotificationTypeResponse response = notificationTypeService.createNotificationType(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Retrieve a notification type by ID",
            description = "Fetches a notification type by its unique ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notification type retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Notification type not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<NotificationTypeResponse> getNotificationTypeById(
            @Parameter(description = "ID of the notification type to retrieve") @PathVariable String id)
    {
        log.info("Received request to retrieve notification type with ID: {}", id);
        NotificationTypeResponse response = notificationTypeService.getNotificationTypeById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Retrieve a paginated list of notification types",
            description = "Fetches a paginated and sorted list of notification types."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notification types retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination or sorting parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<Page<NotificationTypeResponse>> getAllNotificationTypes(
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Field to sort by", example = "name")
            @RequestParam(name = "sort-by", defaultValue = "name") String sortBy,
            @Parameter(description = "Sort direction (asc or desc)", example = "asc")
            @RequestParam(defaultValue = "asc") String order)
    {
        log.info("Received request to retrieve notification types with page: {}, size: {}, sortBy: {}, order: {}",
                page, size, sortBy, order);
        Page<NotificationTypeResponse> response = notificationTypeService.getAllNotificationTypes(page, size,
                sortBy, order);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Update an existing notification type",
            description = "Updates the details of an existing notification type by its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notification type updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Notification type not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<NotificationTypeResponse> updateNotificationType(
            @Parameter(description = "ID of the notification type to update") @PathVariable String id,
            @Valid @RequestBody NotificationTypeRequest request)
    {
        log.info("Received request to update notification type with ID: {}", id);
        NotificationTypeResponse response = notificationTypeService.updateNotificationType(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Delete a notification type",
            description = "Deletes a notification type by its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Notification type deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Notification type not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotificationType(
            @Parameter(description = "ID of the notification type to delete") @PathVariable String id)
    {
        log.info("Received request to delete notification type with ID: {}", id);
        notificationTypeService.deleteNotificationType(id);
        return ResponseEntity.noContent().build();
    }
}