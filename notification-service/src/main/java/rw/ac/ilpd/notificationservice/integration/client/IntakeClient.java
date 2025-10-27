package rw.ac.ilpd.notificationservice.integration.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

/**
 * Feign client to access related to the intake in the academic-service.
 * */
@FeignClient(name = "ACADEMIC-SERVICE", path = "/intakes")
public interface IntakeClient
{
    @GetMapping("/users/{intakeId}/{componentId}")
    List<UUID> findUsersByIntakeAndComponentId(@PathVariable UUID intakeId, @PathVariable UUID componentId);
}
