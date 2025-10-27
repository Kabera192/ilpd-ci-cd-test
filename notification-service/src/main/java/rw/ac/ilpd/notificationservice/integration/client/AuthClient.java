package rw.ac.ilpd.notificationservice.integration.client;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * Feign client to access data from the auth-service.
 * */
@FeignClient(name = "AUTH-SERVICE", path = "/auth")
public interface AuthClient
{
}
