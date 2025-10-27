/*
 * File: NotificationDestinationRequest.java
 * 
 * Description: Data Transfer Object representing the destination of a notification.
 *              Contains information about the target entities for a notification,
 *              including references to intake, role, module, component, and user.
 *              All fields except 'id' are required and validated for non-null values.
 * 
 * Author: Kabera Clapton (ckabera6@gmail.com)
 * Last Modified: 2025-07-05
 */
package rw.ac.ilpd.mis.shared.dto.notification;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationDestinationRequest {
    private UUID intakeId;

    private UUID roleId;

    private UUID moduleId;

    private UUID componentId;

    private UUID userId;
}