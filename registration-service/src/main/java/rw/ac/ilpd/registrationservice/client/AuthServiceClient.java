package rw.ac.ilpd.registrationservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import rw.ac.ilpd.mis.shared.util.helpers.MisResponse;
import rw.ac.ilpd.registrationservice.client.fallback.AuthServiceFallback;
import rw.ac.ilpd.registrationservice.config.FeignConfig;

import java.util.Map;

/**
 * Clean Feign client that only uses endpoints that actually exist in auth-service.
 * Removed the non-existent /verify-token endpoint to prevent errors.
 */
@FeignClient(
        name = "AUTH-SERVICE",
        configuration = FeignConfig.class,
        fallback = AuthServiceFallback.class
)
public interface AuthServiceClient {

    /**
     * Validates the authentication token by calling the /username endpoint.
     * This endpoint exists in auth-service and returns the username for valid tokens.
     *
     * Note: This endpoint depends on Spring Security's authentication context,
     * so it may occasionally fail if there are issues with the auth-service's
     * internal authentication processing.
     *
     * @param token the authorization token provided in the request header
     * @return a {@code MisResponse} containing the username if token is valid
     */
    @GetMapping("/api/v1/auth/users/username")
    MisResponse<Map<String, String>> validateToken(@RequestHeader("Authorization") String token);

    /**
     * Logs out a user by invalidating their authentication token.
     * This endpoint exists in the auth-service.
     *
     * @param token the authentication token of the user to be logged out
     * @return a {@code MisResponse<String>} object containing the logout result
     */
    @PostMapping("/api/v1/auth/logout")
    MisResponse<String> logout(@RequestHeader("Authorization") String token);
}