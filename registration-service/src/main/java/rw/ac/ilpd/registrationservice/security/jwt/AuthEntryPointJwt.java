package rw.ac.ilpd.registrationservice.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles authentication errors and returns appropriate HTTP responses
 * This is triggered when an unauthenticated user tries to access a protected resource
 */
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        logger.error("Unauthorized error: {}", authException.getMessage());
        logger.debug("Request URI: {}", request.getRequestURI());

        // Set response status
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // Build error response body
        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "Unauthorized");
        body.put("path", request.getServletPath());

        // Customize message based on exception type
        String message = getDetailedMessage(authException);
        body.put("message", message);
        body.put("timestamp", System.currentTimeMillis());

        // Add WWW-Authenticate header for proper OAuth2/JWT compliance
        response.setHeader("WWW-Authenticate", "Bearer realm=\"Registration Service\", error=\"invalid_token\", error_description=\"" + message + "\"");

        // Write JSON response
        mapper.writeValue(response.getOutputStream(), body);
    }

    /**
     * Get detailed error message based on exception type
     */
    private String getDetailedMessage(AuthenticationException authException) {
        if (authException.getMessage() != null) {
            String message = authException.getMessage().toLowerCase();

            if (message.contains("expired")) {
                return "JWT token has expired. Please login again.";
            } else if (message.contains("malformed") || message.contains("invalid")) {
                return "Invalid JWT token format.";
            } else if (message.contains("signature")) {
                return "JWT signature validation failed.";
            } else if (message.contains("credentials")) {
                return "Invalid authentication credentials.";
            }
        }

        return "Full authentication is required to access this resource.";
    }
}