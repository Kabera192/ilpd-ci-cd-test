package rw.ac.ilpd.academicservice.integration.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import rw.ac.ilpd.sharedlibrary.dto.location.LocationResponse;

@FeignClient(
        name = "INVENTORY-SERVICE",
        contextId = "locationClient",
        path = "/locations"
)
public interface LocationClient {
    @GetMapping("/{id}")
    public LocationResponse get(
            @PathVariable String id
    );
}
