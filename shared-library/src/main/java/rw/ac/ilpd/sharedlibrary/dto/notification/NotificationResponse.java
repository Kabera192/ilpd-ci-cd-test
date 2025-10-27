/*
 * File: NotificationResponse.java
 * 
 * Description: Data Transfer Object representing a notification. 
 *              Encapsulates notification details such as title, content, sender, type, 
 *              and timestamps for creation and last update. 
 *              Includes validation constraints to ensure required fields and length restrictions.
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified: 2025-07-05
 */
package rw.ac.ilpd.sharedlibrary.dto.notification;

import lombok.*;
import rw.ac.ilpd.sharedlibrary.enums.NotificationTypeEnum;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationResponse {
    private String id;
    private String title;
    private String content;
    private UUID senderId;
    private NotificationTypeEnum notificationType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<NotificationDestinationResponse> destinations;
}