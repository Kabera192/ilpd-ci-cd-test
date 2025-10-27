package rw.ac.ilpd.inventoryservice.mapper;

import org.springframework.stereotype.Component;
import rw.ac.ilpd.inventoryservice.model.nosql.document.LocationType;
import rw.ac.ilpd.sharedlibrary.dto.locationtype.LocationTypeRequest;
import rw.ac.ilpd.sharedlibrary.dto.locationtype.LocationTypeResponse;

import java.time.LocalDateTime;

@Component
public class LocationTypeMapper {
    public LocationType toLocationType(LocationTypeRequest request) {
        return LocationType.builder()
                .name(request.getName())
                .deleteStatus(false) // New entities are not deleted by default
                .build();
    }

    public LocationTypeResponse fromLocationType(LocationType locationType) {
        return LocationTypeResponse.builder()
                .id(locationType.getId())
                .name(locationType.getName())
                .deleteStatus(locationType.isDeleteStatus())
                .createdAt(locationType.getCreatedAt() != null ? locationType.getCreatedAt().toString() : null)
                .build();
    }
}