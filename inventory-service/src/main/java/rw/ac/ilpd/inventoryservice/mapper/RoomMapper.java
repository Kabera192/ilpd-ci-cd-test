package rw.ac.ilpd.inventoryservice.mapper;

import org.springframework.stereotype.Component;
import rw.ac.ilpd.inventoryservice.model.sql.Room;
import rw.ac.ilpd.inventoryservice.model.sql.RoomType;
import rw.ac.ilpd.sharedlibrary.dto.room.RoomRequest;
import rw.ac.ilpd.sharedlibrary.dto.room.RoomResponse;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class RoomMapper {
    public Room toRoom(RoomRequest request, RoomType roomType) {
        return Room.builder()
                .locationId(request.getLocationId())
                .name(request.getName())
                .code(request.getCode())
                .roomTypeId(roomType)
                .build();
    }

    public RoomResponse fromRoom(Room room) {
        return RoomResponse.builder()
                .id(room.getId() != null ? room.getId().toString() : null)
                .locationId(room.getLocationId() != null ? room.getLocationId() : null)
                .name(room.getName())
                .code(room.getCode())
                .typeId(room.getRoomTypeId() != null ? room.getRoomTypeId().getId().toString() : null)
                .createdAt(room.getCreatedAt() != null ? room.getCreatedAt().toString() : null)
                .build();
    }
}