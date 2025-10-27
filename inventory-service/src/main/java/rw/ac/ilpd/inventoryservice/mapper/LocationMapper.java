package rw.ac.ilpd.inventoryservice.mapper;

import org.springframework.stereotype.Component;
import rw.ac.ilpd.inventoryservice.model.nosql.document.Location;
import rw.ac.ilpd.inventoryservice.model.nosql.document.LocationType;
import rw.ac.ilpd.sharedlibrary.dto.location.LocationRequest;
import rw.ac.ilpd.sharedlibrary.dto.location.LocationResponse;
import rw.ac.ilpd.sharedlibrary.enums.BlockType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class LocationMapper {
    public Location toLocation(LocationRequest request, LocationType locationType, Location parentLocation) {
        return Location.builder()
                .name(request.getName())
                .type(locationType)
                .parentLocationId(request.getParentLocationId())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .blockType(request.getBlocType() != null ?
                        BlockType.valueOf(request.getBlocType()) : null)
                .deleteStatus(false) // New entities are not deleted by default
                .build();
    }

    public LocationResponse fromLocation(Location location) {
        return LocationResponse.builder()
                .id(location.getId())
                .name(location.getName())
                .typeId(location.getType() != null ? location.getType().getId() : null)
                .parentLocationId(location.getParentLocationId())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .blocType(location.getBlockType() != null ? location.getBlockType().name() : null)
                .deleteStatus(location.isDeleteStatus())
                .createdAt(location.getCreatedAt() != null ? location.getCreatedAt().toString() : null)
                .build();
    }
}