package rw.ac.ilpd.mis.auth.security.jwt;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 24/07/2024
 */
import com.fasterxml.jackson.databind.ObjectMapper;
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

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    private static final Logger log = LoggerFactory.getLogger(AuthEntryPointJwt.class);
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException ex) throws IOException {
        // This will only be invoked for AuthenticationException
        log.error("Authentication failure on {} {}: {}", request.getMethod(), request.getRequestURI(), ex.getMessage());

        // Default status
        int status = HttpServletResponse.SC_UNAUTHORIZED;
        String error = "Unauthorized";
        String errorDescription = ex.getMessage();

        // Optional: return WWW-Authenticate header according to RFC 6750
        // Customize messages for common cases
        String wwwAuthenticate = "Bearer";

        if (ex instanceof org.springframework.security.authentication.BadCredentialsException) {
            errorDescription = "Invalid credentials";
            wwwAuthenticate = "Bearer error=\"invalid_token\", error_description=\"Invalid credentials\"";
        } else if (ex instanceof org.springframework.security.authentication.CredentialsExpiredException) {
            errorDescription = "Credentials expired";
            wwwAuthenticate = "Bearer error=\"invalid_token\", error_description=\"Credentials expired\"";
        } else if (ex instanceof org.springframework.security.authentication.AccountExpiredException) {
            errorDescription = "Account expired";
        } else if (ex instanceof org.springframework.security.authentication.DisabledException) {
            errorDescription = "Account disabled";
        } else if (ex instanceof org.springframework.security.authentication.LockedException) {
            errorDescription = "Account locked";
        } else if (ex instanceof org.springframework.security.authentication.InsufficientAuthenticationException) {
            // Missing/absent token, anonymous access to protected route, etc.
            errorDescription = "Full authentication is required to access this resource";
            wwwAuthenticate = "Bearer error=\"invalid_token\", error_description=\"Authentication required\"";
        }

        response.setHeader("WWW-Authenticate", wwwAuthenticate);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status);

        Map<String, Object> body = new HashMap<>();
        body.put("status", status);
        body.put("error", error);
        body.put("message", errorDescription);
        body.put("path", request.getServletPath());

        mapper.writeValue(response.getOutputStream(), body);
    }
}

