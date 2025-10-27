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
package rw.ac.ilpd.mis.shared.dto.notification;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationTypeRequest {

    @NotNull(message = "Notification type name is required")
    @NotBlank(message = "Notification type name cannot be blank")
    private String name;
}