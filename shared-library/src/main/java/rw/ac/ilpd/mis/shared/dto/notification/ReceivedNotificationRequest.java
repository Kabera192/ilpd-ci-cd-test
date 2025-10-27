/*
 * File: ReceivedNotificationRequest.java
 * 
 * Description: 
 *   Data Transfer Object representing a received notification.
 *   Contains information about a notification received by a user, including:
 *     - id: Unique identifier for the received notification record.
 *     - notificationId: Unique identifier of the notification (required).
 *     - userId: Unique identifier of the user who received the notification.
 *     - createdAt: Timestamp when the notification was received (required).
 *   Fields 'notificationId' and 'createdAt' are required and must not be null.
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified Date: 2025-07-05
 */
package rw.ac.ilpd.mis.shared.dto.notification;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReceivedNotificationRequest {

    @NotBlank(message = "Notification ID is required")
    private String notificationId;

    private UUID userId;
}