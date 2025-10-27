package rw.ac.ilpd.paymentservice.integration.client;

import org.springframework.cloud.openfeign.FeignClient;
import rw.ac.ilpd.sharedlibrary.dto.user.UserResponse;

@FeignClient(name = "AUTH-SERVICE", path = "/users")
public interface UserClient {
    UserResponse findUserById(String feeUserId);
}
