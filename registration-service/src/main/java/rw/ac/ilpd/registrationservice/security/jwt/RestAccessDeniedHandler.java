package rw.ac.ilpd.registrationservice.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles authorization errors when authenticated users lack required permissions
 * This is triggered when an authenticated user tries to access a resource they don't have permission for
 */
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestAccessDeniedHandler.class);
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        // Log the access denied attempt
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            logger.warn("User: {} attempted to access protected resource: {}",
                    auth.getName(), request.getRequestURI());
        }

        // Set response status
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // Build error response body
        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_FORBIDDEN);
        body.put("error", "Forbidden");
        body.put("message", "You do not have permission to access this resource");
        body.put("path", request.getServletPath());
        body.put("timestamp", System.currentTimeMillis());

        // Add details about what permission might be needed (be careful not to expose too much)
        if (request.getMethod() != null && request.getRequestURI() != null) {
            body.put("required_permission", getRequiredPermissionHint(request));
        }

        // Write JSON response
        mapper.writeValue(response.getOutputStream(), body);
    }

    /**
     * Provide a hint about what permission might be needed based on your actual endpoints
     */
    private String getRequiredPermissionHint(HttpServletRequest request) {
        String method = request.getMethod();
        String path = request.getRequestURI();

        // Applications endpoints
        if (path.contains("/applications")) {
            if (path.contains("/analytics")) {
                return "ADMIN or ANALYST role required";
            }
            return switch (method) {
                case "POST" -> "CREATE_APPLICATION permission required";
                case "PUT", "PATCH" -> "UPDATE_APPLICATION permission required";
                case "DELETE" -> "DELETE_APPLICATION permission required";
                case "GET" -> "VIEW_APPLICATION permission required";
                default -> "Appropriate permission required";
            };
        }

        // Academic backgrounds endpoints
        if (path.contains("/academic-backgrounds")) {
            return switch (method) {
                case "POST" -> "CREATE_ACADEMIC_BACKGROUND permission required";
                case "PUT", "PATCH" -> "UPDATE_ACADEMIC_BACKGROUND permission required";
                case "DELETE" -> "DELETE_ACADEMIC_BACKGROUND permission required";
                case "GET" -> "VIEW_ACADEMIC_BACKGROUND permission required";
                default -> "Appropriate permission required";
            };
        }

        // Universities endpoints
        if (path.contains("/universities")) {
            return "MANAGE_UNIVERSITIES permission required";
        }

        // Application sponsors endpoints
        if (path.contains("/application-sponsors")) {
            return switch (method) {
                case "POST" -> "CREATE_APPLICATION_SPONSOR permission required";
                case "PUT", "PATCH" -> "UPDATE_APPLICATION_SPONSOR permission required";
                case "DELETE" -> "DELETE_APPLICATION_SPONSOR permission required";
                case "GET" -> "VIEW_APPLICATION_SPONSOR permission required";
                default -> "Appropriate permission required";
            };
        }

        // Admin endpoints
        if (path.contains("/admin")) {
            return "ADMIN role required";
        }

        return "Appropriate role or permission required";
    }
}