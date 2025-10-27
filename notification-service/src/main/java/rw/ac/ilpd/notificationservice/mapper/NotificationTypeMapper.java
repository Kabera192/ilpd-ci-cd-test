/*
 * File: NotificationTypeMapper.java
 *
 * Description: Mapper interface for converting between NotificationType entity and DTOs.
 *              Uses MapStruct for automated mapping between NotificationType, NotificationTypeRequest, and NotificationTypeResponse.
 */
package rw.ac.ilpd.notificationservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import rw.ac.ilpd.notificationservice.model.nosql.document.NotificationType;
import rw.ac.ilpd.sharedlibrary.dto.notification.NotificationTypeRequest;
import rw.ac.ilpd.sharedlibrary.dto.notification.NotificationTypeResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NotificationTypeMapper
{
    @Mapping(target = "id", ignore = true)
    NotificationType toNotificationType(NotificationTypeRequest request);

    NotificationTypeResponse fromNotificationType(NotificationType entity);
}