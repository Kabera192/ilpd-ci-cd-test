/*
 * File: NotificationTypeRequest.java
 * 
 * Description: Data Transfer Object representing a Notification Type.
 *              Contains information about the notification type, including its unique identifier and name.
 *              The 'name' field is required and cannot be blank.
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified Date: 2025-07-05
 */
package rw.ac.ilpd.sharedlibrary.dto.notification;

import lombok.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import rw.ac.ilpd.sharedlibrary.dto.validation.RestrictedString;
import rw.ac.ilpd.sharedlibrary.enums.NotificationTypeEnum;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationTypeRequest {

    @NotNull(message = "Notification type name is required")
    @NotBlank(message = "Notification type name cannot be blank")
    @RestrictedString
    private NotificationTypeEnum name;
}