package rw.ac.ilpd.inventoryservice.mapper;

import org.springframework.stereotype.Component;
import rw.ac.ilpd.inventoryservice.model.sql.RoomType;
import rw.ac.ilpd.sharedlibrary.dto.roomtype.RoomTypeRequest;
import rw.ac.ilpd.sharedlibrary.dto.roomtype.RoomTypeResponse;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class RoomTypeMapper {
    public RoomType toRoomType(RoomTypeRequest request) {
        return RoomType.builder()
                .name(request.getName())
                .build();
    }

    public RoomTypeResponse fromRoomType(RoomType roomType) {
        return RoomTypeResponse.builder()
                .id(roomType.getId() != null ? roomType.getId().toString() : null)
                .name(roomType.getName())
                .build();
    }
}