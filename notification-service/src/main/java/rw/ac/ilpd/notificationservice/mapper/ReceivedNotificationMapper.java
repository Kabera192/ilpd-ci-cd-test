/*
 * File: ReceivedNotificationMapper.java
 *
 * Description: Mapper interface for converting between ReceivedNotification entity and DTOs.
 *              Uses MapStruct for automated mapping between ReceivedNotification,
 *              ReceivedNotificationRequest, and ReceivedNotificationResponse.
 */
package rw.ac.ilpd.notificationservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import rw.ac.ilpd.notificationservice.model.nosql.document.ReceivedNotification;
import rw.ac.ilpd.sharedlibrary.dto.notification.ReceivedNotificationRequest;
import rw.ac.ilpd.sharedlibrary.dto.notification.ReceivedNotificationResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReceivedNotificationMapper
{

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    ReceivedNotification toReceivedNotification(ReceivedNotificationRequest request);

    ReceivedNotificationResponse fromReceivedNotification(ReceivedNotification entity);
}