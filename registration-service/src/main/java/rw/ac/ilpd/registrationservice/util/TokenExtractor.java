package rw.ac.ilpd.registrationservice.util;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import rw.ac.ilpd.mis.shared.security.MisAuthentication;
import rw.ac.ilpd.registrationservice.config.SystemTokenProvider;

/**
 * Simple utility class focused solely on extracting authorization tokens for Feign interceptors.
 * This class works with the existing MisAuthentication without needing access to private fields.
 */
@Component
public class TokenExtractor {

    private static final Logger logger = LoggerFactory.getLogger(TokenExtractor.class);

    @Autowired
    private SystemTokenProvider systemTokenProvider;

    /**
     * Extracts the authorization token from the current context.
     * This is the main method used by Feign interceptors.
     *
     * @return the authorization header with Bearer prefix, or null if not found
     */
    public String getAuthorizationToken() {
        logger.trace("Extracting authorization token from context");

        // Priority 1: Current HTTP request
        String token = getTokenFromHttpRequest();
        if (token != null) {
            logger.debug("Token found in HTTP request");
            return ensureBearerFormat(token);
        }

        // Priority 2: Spring Security Context (MisAuthentication)
        token = getTokenFromSecurityContext();
        if (token != null) {
            logger.debug("Token found in Security Context");
            return ensureBearerFormat(token);
        }

        // Priority 3: System token for initialization and background tasks
        token = getSystemToken();
        if (token != null) {
            logger.debug("Using system token for service-to-service communication");
            return token; // Already formatted by SystemTokenProvider
        }

        logger.debug("No authorization token found in any context");
        return null;
    }

    /**
     * Gets the authorization token from the current HTTP request.
     *
     * @return the authorization token, or null if not available
     */
    private String getTokenFromHttpRequest() {
        try {
            ServletRequestAttributes attributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                String authHeader = request.getHeader("Authorization");

                if (authHeader != null && !authHeader.trim().isEmpty()) {
                    logger.trace("Found Authorization header in HTTP request");
                    return authHeader;
                }
            }
        } catch (Exception e) {
            logger.debug("Error accessing HTTP request context: {}", e.getMessage());
        }
        return null;
    }

    /**
     * Gets the authorization token from the Spring Security context.
     * This method specifically handles MisAuthentication where the token is stored in details.
     *
     * @return the authorization token, or null if not available
     */
    private String getTokenFromSecurityContext() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null) {
                logger.trace("No authentication in security context");
                return null;
            }

            // Handle MisAuthentication specifically
            if (authentication instanceof MisAuthentication) {
                MisAuthentication misAuth = (MisAuthentication) authentication;

                // Based on the MisAuthentication constructor:
                // public MisAuthentication(UserAuth userAuth, boolean secure, String authHeader) {
                //     ...
                //     this.details = authHeader; // Token is stored here
                // }
                Object details = misAuth.getDetails();
                if (details instanceof String token) {
                    if (!token.trim().isEmpty()) {
                        logger.trace("Found token in MisAuthentication details");
                        return token;
                    }
                }
            }

            // Fallback for other authentication types
            Object credentials = authentication.getCredentials();
            if (credentials != null) {
                String credString = credentials.toString();
                if (credString != null && !credString.trim().isEmpty() && (credString.startsWith(
                        "Bearer ") || credString.length() > 20))
                {
                    logger.trace("Found token in authentication credentials");
                    return credString;
                }
            }

        } catch (Exception e) {
            logger.debug("Error accessing security context: {}", e.getMessage());
        }
        return null;
    }

    /**
     * Gets the system token for initialization and background operations.
     *
     * @return system token, or null if not available
     */
    private String getSystemToken() {
        try {
            if (systemTokenProvider.hasSystemToken()) {
                return systemTokenProvider.getSystemToken();
            }
        } catch (Exception e) {
            logger.debug("Error getting system token: {}", e.getMessage());
        }
        return null;
    }

    /**
     * Ensures the token has proper Bearer format.
     *
     * @param token the token to format
     * @return properly formatted Bearer token
     */
    private String ensureBearerFormat(String token) {
        if (token == null || token.trim().isEmpty()) {
            return null;
        }

        String trimmedToken = token.trim();

        // If it already starts with Bearer, return as-is
        if (trimmedToken.startsWith("Bearer ")) {
            return trimmedToken;
        }

        // Add Bearer prefix
        return "Bearer " + trimmedToken;
    }

    /**
     * Simple check if the current user is authenticated.
     *
     * @return true if authenticated, false otherwise
     */
    public boolean isAuthenticated() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return authentication != null && authentication.isAuthenticated();
        } catch (Exception e) {
            logger.debug("Error checking authentication status: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Gets the current username from the security context.
     *
     * @return current username, or null if not authenticated
     */
    public String getCurrentUsername() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                return authentication.getName();
            }
        } catch (Exception e) {
            logger.debug("Error getting current username: {}", e.getMessage());
        }
        return null;
    }
}

