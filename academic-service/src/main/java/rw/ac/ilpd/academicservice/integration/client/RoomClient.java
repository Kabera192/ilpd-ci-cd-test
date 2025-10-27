package rw.ac.ilpd.academicservice.integration.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import rw.ac.ilpd.sharedlibrary.dto.room.RoomResponse;

import java.util.UUID;

@FeignClient(
        name = "INVENTORY-SERVICE",
        contextId = "roomClient",
        path = "/rooms"
)
public interface RoomClient {
    @GetMapping("/{id}")
    public RoomResponse get(
            @PathVariable UUID id
    );
}
