package rw.ac.ilpd.registrationservice.client.fallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import rw.ac.ilpd.mis.shared.util.helpers.MisResponse;
import rw.ac.ilpd.registrationservice.client.AuthServiceClient;

import java.util.Map;

/**
 * Fixed fallback implementation that only handles endpoints that exist.
 * Provides default responses when the authentication service is unavailable.
 */
@Component
public class AuthServiceFallback implements AuthServiceClient {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceFallback.class);

    /**
     * Fallback for token validation when auth-service is unavailable.
     *
     * @param token the authorization token to be validated
     * @return a MisResponse object containing a failure status and error message
     */
    @Override
    public MisResponse<Map<String, String>> validateToken(String token) {
        logger.error("Auth service is unavailable - token validation failed");
        return new MisResponse<>(false, "Authentication service unavailable", null);
    }

    /**
     * Fallback for logout when auth-service is unavailable.
     *
     * @param token the authentication token of the user to be logged out
     * @return a {@code MisResponse<String>} containing a failure status and error message
     */
    @Override
    public MisResponse<String> logout(String token) {
        logger.error("Auth service is unavailable - logout failed");
        return new MisResponse<>(false, "Authentication service unavailable for logout", null);
    }
}