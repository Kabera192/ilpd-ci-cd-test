package rw.ac.ilpd.registrationservice.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import rw.ac.ilpd.mis.shared.security.MisAuthentication;
import rw.ac.ilpd.registrationservice.util.TokenExtractor;

/**
 * Specialized configuration for AuthService Feign client.
 * This provides enhanced token forwarding capabilities specifically for auth-service communication.
 */
@Configuration
public class AuthServiceFeignConfig {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceFeignConfig.class);
    
    @Autowired
    private TokenExtractor tokenExtractor;

    /**
     * Creates a specialized request interceptor for AuthService calls.
     * This interceptor ensures proper token forwarding for authentication service requests.
     *
     * @return a configured request interceptor for AuthService
     */
    @Bean
    public RequestInterceptor authServiceRequestInterceptor() {
        return new AuthServiceRequestInterceptor();
    }

    /**
     * Custom request interceptor specifically designed for AuthService communication.
     * This interceptor handles token forwarding with enhanced logging and error handling.
     */
    public class AuthServiceRequestInterceptor implements RequestInterceptor {

        private static final Logger log = LoggerFactory.getLogger(AuthServiceRequestInterceptor.class);

        @Override
        public void apply(RequestTemplate template) {
            log.debug("AuthServiceInterceptor: Processing request to {}", template.url());

            // Use TokenExtractor to get the authorization token, which includes fallback to system token
            String authToken = tokenExtractor.getAuthorizationToken();

            if (authToken != null && !authToken.trim().isEmpty()) {
                template.header("Authorization", authToken);

                log.debug("AuthServiceInterceptor: Successfully added Authorization header");
                log.trace("AuthServiceInterceptor: Token format: {}",
                        authToken.substring(0, Math.min(20, authToken.length())) + "...");
            } else {
                log.warn("AuthServiceInterceptor: No authorization token found for request to {}", template.url());

                // For debugging: log what's available in contexts
                logAvailableContexts();
            }

            // Add standard headers for AuthService communication
            template.header("Content-Type", "application/json");
            template.header("Accept", "application/json");
            template.header("User-Agent", "Registration-Service-Feign-Client/1.0");
        }

        // All token extraction logic is now handled by TokenExtractor

        /**
         * Logs available contexts for debugging purposes.
         */
        private void logAvailableContexts() {
            if (log.isTraceEnabled()) {
                try {
                    // Log security context info
                    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                    log.trace("Security Context - Authentication: {}",
                            auth != null ? auth.getClass().getSimpleName() : "null");
                    log.trace("Security Context - Principal: {}", auth != null ? auth.getPrincipal() : "null");
                    log.trace("Security Context - Authenticated: {}", auth != null ? auth.isAuthenticated() : "null");

                    // Log request context info
                    ServletRequestAttributes attributes =
                            (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                    log.trace("Request Context - Available: {}", attributes != null);

                    if (attributes != null) {
                        HttpServletRequest request = attributes.getRequest();
                        log.trace("HTTP Request - Method: {}", request.getMethod());
                        log.trace("HTTP Request - URI: {}", request.getRequestURI());
                        log.trace("HTTP Request - Has Auth Header: {}", request.getHeader("Authorization") != null);
                    }
                } catch (Exception e) {
                    log.trace("Error logging context information: {}", e.getMessage());
                }
            }
        }
    }
}