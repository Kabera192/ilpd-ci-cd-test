package rw.ac.ilpd.notificationservice.mapper;

import org.mapstruct.Mapper;
import rw.ac.ilpd.notificationservice.model.nosql.embedding.NotificationDestination;
import rw.ac.ilpd.sharedlibrary.dto.notification.NotificationDestinationRequest;
import rw.ac.ilpd.sharedlibrary.dto.notification.NotificationDestinationResponse;

@Mapper(componentModel = "spring")
public interface NotificationDestinationMapper
{
    NotificationDestination toNotificationDestination(
            NotificationDestinationRequest notificationDestinationRequest);

    NotificationDestinationResponse fromNotificationDestination(NotificationDestination notificationDestination);
}
