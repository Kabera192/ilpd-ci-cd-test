/*
 * File: NotificationRequest.java
 * 
 * Description: Data Transfer Object representing a notification. 
 *              Encapsulates notification details such as title, content, sender, type, 
 *              and timestamps for creation and last update. 
 *              Includes validation constraints to ensure required fields and length restrictions.
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified: 2025-07-05
 */
package rw.ac.ilpd.mis.shared.dto.notification;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.UUID;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationRequest
{
    @NotNull(message = "Title is required")
    @NotBlank(message = "Title cannot be blank")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    @NotNull(message = "Content is required")
    @NotBlank(message = "Content cannot be blank")
    private String content;

    @NotNull(message = "Sender ID is required")
    private UUID senderId;

    @NotBlank(message = "Notification type ID is required")
    private String notificationTypeId;

    @NotNull(message = "Notification destinations cannot be null")
    private List<NotificationDestinationRequest> destinations;
}