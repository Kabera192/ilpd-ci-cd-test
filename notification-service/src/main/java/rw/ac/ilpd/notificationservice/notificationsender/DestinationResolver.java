package rw.ac.ilpd.notificationservice.notificationsender;

import rw.ac.ilpd.notificationservice.model.nosql.embedding.NotificationDestination;
import rw.ac.ilpd.sharedlibrary.dto.notification.NotificationDestinationResponse;

import java.util.List;

public interface DestinationResolver
{
    List<String> resolveDestination(List<NotificationDestinationResponse> destinations);
}
