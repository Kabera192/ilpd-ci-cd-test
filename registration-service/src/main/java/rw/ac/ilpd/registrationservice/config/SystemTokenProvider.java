package rw.ac.ilpd.registrationservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Provides system-level authentication tokens for initialization and background tasks.
 * This component handles service-to-service authentication when no user context exists.
 */
@Component
public class SystemTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(SystemTokenProvider.class);

    @Value("${app.system.token:}")
    private String systemToken;

    @Value("${app.system.service-account.username:system}")
    private String systemUsername;

    @Value("${app.system.service-account.password:}")
    private String systemPassword;

    /**
     * Gets a system token for service-to-service communication.
     * This should be used only for system-level operations like data initialization.
     *
     * @return system authentication token with Bearer prefix
     */
    public String getSystemToken() {
        // Option 1: Use pre-configured system token
        if (systemToken != null && !systemToken.trim().isEmpty()) {
            logger.debug("Using pre-configured system token");
            return ensureBearerFormat(systemToken);
        }

        // Option 2: Generate token using system service account (if you have this endpoint)
        // This would require calling your auth service to get a token for a system account
        logger.warn("No system token configured - system operations may fail");
        return null;
    }

    /**
     * Ensures the token has proper Bearer format.
     */
    private String ensureBearerFormat(String token) {
        if (token == null || token.trim().isEmpty()) {
            return null;
        }
        String trimmed = token.trim();
        return trimmed.startsWith("Bearer ") ? trimmed : "Bearer " + trimmed;
    }

    /**
     * Checks if system token is available.
     */
    public boolean hasSystemToken() {
        return systemToken != null && !systemToken.trim().isEmpty();
    }
}