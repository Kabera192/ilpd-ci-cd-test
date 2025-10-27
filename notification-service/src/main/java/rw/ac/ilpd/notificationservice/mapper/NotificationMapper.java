/*
 * File: NotificationMapper.java
 *
 * Description: Mapper interface for converting between Notification entity and DTOs.
 * Uses MapStruct for automated mapping between Notification, NotificationRequest,
 * and NotificationResponse.
 */
package rw.ac.ilpd.notificationservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import rw.ac.ilpd.notificationservice.model.nosql.document.Notification;
import rw.ac.ilpd.sharedlibrary.dto.notification.NotificationRequest;
import rw.ac.ilpd.sharedlibrary.dto.notification.NotificationResponse;

@Mapper(componentModel = "spring")
public interface NotificationMapper
{
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Notification toNotification(NotificationRequest request);

    NotificationResponse fromNotification(Notification entity);
}