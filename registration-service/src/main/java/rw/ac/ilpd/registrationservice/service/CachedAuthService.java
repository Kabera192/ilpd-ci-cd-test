package rw.ac.ilpd.registrationservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import rw.ac.ilpd.mis.shared.dto.user.UserAuth;
import rw.ac.ilpd.mis.shared.util.helpers.MisResponse;
import rw.ac.ilpd.registrationservice.client.AuthServiceClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Reliable authentication service that works with existing auth-service
 * Uses multiple fallback strategies to handle auth-service reliability issues
 */
@Service
public class CachedAuthService {

    private static final Logger logger = LoggerFactory.getLogger(CachedAuthService.class);
    private static final String CACHE_NAME = "auth-tokens";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    @Autowired
    private AuthServiceClient authServiceClient;
    @Value("${auth.service.url:http://localhost:8085}")
    private String authServiceUrl;

    public CachedAuthService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Validate token using multiple fallback strategies
     */
    @Cacheable(value = CACHE_NAME, key = "#authHeader", unless = "#result == null")
    public UserAuth validateToken(String authHeader) {
        logger.debug("Validating token with auth-service using multiple strategies");

        // Strategy 1: Try Feign client first (cleanest approach)
        UserAuth result = tryFeignValidation(authHeader);
        if (result != null) {
            logger.debug("Token validated successfully using Feign client");
            return result;
        }

        // Strategy 2: Try direct HTTP call to /username endpoint
        result = tryDirectHttpValidation(authHeader);
        if (result != null) {
            logger.debug("Token validated successfully using direct HTTP");
            return result;
        }

        // Strategy 3: Local JWT validation as last resort (validates structure and extracts claims)
        result = tryLocalJwtValidation(authHeader);
        if (result != null) {
            logger.debug("Token validated using local JWT parsing (limited validation)");
            return result;
        }

        logger.warn("All token validation strategies failed");
        return null;
    }

    /**
     * Strategy 1: Use Feign client (preferred method)
     */
    private UserAuth tryFeignValidation(String authHeader) {
        try {
            MisResponse<Map<String, String>> response = authServiceClient.validateToken(authHeader);

            if (response != null && response.isSuccess() && response.getResult() != null) {
                String username = response.getResult().get("username");
                if (username != null && !username.trim().isEmpty()) {
                    return createUserAuthFromUsername(username, authHeader);
                }
            }
        } catch (Exception e) {
            logger.debug("Feign validation failed: {}", e.getMessage());
            // Check if it's a fallback (service unavailable) or actual error
            if (e.getMessage() != null && !e.getMessage().contains("Authentication service unavailable")) {
                logger.debug("Feign client encountered specific error (not service unavailable)");
            }
        }
        return null;
    }

    /**
     * Strategy 2: Direct HTTP call to /username endpoint
     */
    private UserAuth tryDirectHttpValidation(String authHeader) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authHeader);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            String url = authServiceUrl + "/api/v1/auth/users/username";

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                String responseBody = response.getBody();
                String username = extractUsernameFromResponse(responseBody);

                if (username != null && !username.trim().isEmpty()) {
                    return createUserAuthFromUsername(username, authHeader);
                }
            }
        } catch (HttpClientErrorException e) {
            logger.debug("HTTP validation failed with status {}: {}", e.getStatusCode(), e.getMessage());
            // Don't log 401/403 as errors - they're expected for invalid tokens
            if (e.getStatusCode() != HttpStatus.UNAUTHORIZED && e.getStatusCode() != HttpStatus.FORBIDDEN) {
                logger.warn("Unexpected HTTP error during token validation: {}", e.getStatusCode());
            }
        } catch (Exception e) {
            logger.debug("HTTP validation failed: {}", e.getMessage());
        }
        return null;
    }

    /**
     * Strategy 3: Local JWT validation (validates structure and extracts basic claims)
     * This doesn't verify signature but checks if token is well-formed and not expired
     */
    private UserAuth tryLocalJwtValidation(String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return null;
            }

            String token = authHeader.substring(7);
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                return null;
            }

            // Decode and parse payload
            String payload = new String(java.util.Base64.getUrlDecoder().decode(parts[1]));
            @SuppressWarnings("unchecked") Map<String, Object> claims = objectMapper.readValue(payload, Map.class);

            // Check expiration
            Object expObj = claims.get("exp");
            if (expObj != null) {
                long exp = ((Number) expObj).longValue();
                if (System.currentTimeMillis() / 1000 >= exp) {
                    logger.debug("Local validation: JWT token is expired");
                    return null;
                }
            }

            // Extract username
            Object subObj = claims.get("sub");
            if (subObj != null) {
                String username = subObj.toString().trim();
                if (!username.isEmpty()) {
                    logger.info("Local JWT validation: Token appears valid for user: {}", username);
                    UserAuth userAuth = createUserAuthFromUsername(username, authHeader);
                    // Add a note that this was locally validated
                    userAuth.setId("local-validation");
                    return userAuth;
                }
            }

        } catch (Exception e) {
            logger.debug("Local JWT validation failed: {}", e.getMessage());
        }
        return null;
    }

    /**
     * Create UserAuth object from username and extract JWT claims
     */
    private UserAuth createUserAuthFromUsername(String username, String authHeader) {
        UserAuth userAuth = new UserAuth();
        userAuth.setUsername(username);
        userAuth.setEnabled(true);
        userAuth.setAccountNonLocked(true);
        userAuth.setAccountNonExpired(true);
        userAuth.setCredentialsNonExpired(true);

        // Extract authorities from JWT token
        try {
            String token = authHeader.substring(7); // Remove "Bearer "
            String scope = extractScopeFromToken(token);
            String jwtId = extractJwtIdFromToken(token);

            // Set JWT ID as user ID if available
            userAuth.setId(jwtId != null ? jwtId : "");

            if (scope != null && !scope.trim().isEmpty()) {
                List<String> authorities = new ArrayList<>();
                List<String> roles = new ArrayList<>();
                List<String> permissions = new ArrayList<>();

                String[] scopeItems = scope.split("\\s+");
                for (String authority : scopeItems) {
                    authority = authority.trim();
                    if (!authority.isEmpty()) {
                        authorities.add(authority);

                        // Classify as role or permission based on naming patterns
                        if (authority.contains("Admin") || authority.contains("Manager") || authority.contains(
                                "User") || authority.startsWith("ROLE_"))
                        {
                            roles.add(authority);
                        } else {
                            permissions.add(authority);
                        }
                    }
                }

                userAuth.setAuthorities(authorities);
                userAuth.setRoles(roles);
                userAuth.setPermissions(permissions);
            } else {
                userAuth.setAuthorities(new ArrayList<>());
                userAuth.setRoles(new ArrayList<>());
                userAuth.setPermissions(new ArrayList<>());
            }

        } catch (Exception e) {
            logger.debug("Could not extract claims from token: {}", e.getMessage());
            userAuth.setId("");
            userAuth.setAuthorities(new ArrayList<>());
            userAuth.setRoles(new ArrayList<>());
            userAuth.setPermissions(new ArrayList<>());
        }

        return userAuth;
    }

    /**
     * Extract username from JSON response (fallback parsing)
     */
    private String extractUsernameFromResponse(String responseBody) {
        if (responseBody == null) return null;

        try {
            // Try proper JSON parsing first
            @SuppressWarnings("unchecked") MisResponse<Map<String, String>> misResponse = objectMapper.readValue(
                    responseBody, objectMapper.getTypeFactory().constructParametricType(MisResponse.class,
                            objectMapper.getTypeFactory().constructMapType(Map.class, String.class, String.class)));

            if (misResponse.isSuccess() && misResponse.getResult() != null) {
                return misResponse.getResult().get("username");
            }
        } catch (Exception e) {
            logger.debug("JSON parsing failed, trying string extraction: {}", e.getMessage());
        }

        // Fallback to string parsing
        try {
            if (responseBody.contains("\"username\":")) {
                int start = responseBody.indexOf("\"username\":\"") + 12;
                int end = responseBody.indexOf("\"", start);
                if (start > 11 && end > start) {
                    return responseBody.substring(start, end);
                }
            }
        } catch (Exception e) {
            logger.debug("String extraction failed: {}", e.getMessage());
        }

        return null;
    }

    /**
     * Extract scope from JWT token
     */
    private String extractScopeFromToken(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) return null;

            String payload = new String(java.util.Base64.getUrlDecoder().decode(parts[1]));
            if (payload.contains("\"scope\":")) {
                int scopeStart = payload.indexOf("\"scope\":\"") + 9;
                int scopeEnd = payload.indexOf("\"", scopeStart);
                if (scopeStart > 8 && scopeEnd > scopeStart) {
                    return payload.substring(scopeStart, scopeEnd);
                }
            }
        } catch (Exception e) {
            logger.debug("Error extracting scope: {}", e.getMessage());
        }
        return null;
    }

    /**
     * Extract JWT ID from token
     */
    private String extractJwtIdFromToken(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) return null;

            String payload = new String(java.util.Base64.getUrlDecoder().decode(parts[1]));
            if (payload.contains("\"jti\":")) {
                int jtiStart = payload.indexOf("\"jti\":\"") + 7;
                int jtiEnd = payload.indexOf("\"", jtiStart);
                if (jtiStart > 6 && jtiEnd > jtiStart) {
                    return payload.substring(jtiStart, jtiEnd);
                }
            }
        } catch (Exception e) {
            logger.debug("Error extracting JWT ID: {}", e.getMessage());
        }
        return null;
    }

    /**
     * Invalidate cached token
     */
    @CacheEvict(value = CACHE_NAME, key = "#authHeader")
    public void invalidateToken(String authHeader) {
        logger.debug("Invalidating cached token");

        try {
            // Try Feign client first
            MisResponse<String> response = authServiceClient.logout(authHeader);
            if (response != null && response.isSuccess()) {
                logger.debug("Logout successful via Feign");
                return;
            }
        } catch (Exception e) {
            logger.debug("Feign logout failed: {}", e.getMessage());
        }

        // Fallback to direct HTTP call
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authHeader);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            String url = authServiceUrl + "/api/v1/auth/logout";

            restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            logger.debug("Logout successful via direct HTTP");

        } catch (Exception e) {
            logger.warn("Logout failed: {}", e.getMessage());
            // Don't fail the cache eviction just because logout failed
        }
    }

    /**
     * Clear all cached tokens
     */
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public void clearAllCachedTokens() {
        logger.info("Clearing all cached tokens");
    }
}