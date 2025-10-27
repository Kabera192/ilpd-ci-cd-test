package rw.ac.ilpd.registrationservice.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import rw.ac.ilpd.mis.shared.security.MisAuthentication;
import rw.ac.ilpd.mis.shared.dto.user.UserAuth;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Utility class for working with MisAuthentication and extracting tokens.
 * This class provides methods to safely extract authentication tokens from various contexts.
 */
@Component
public class MisAuthenticationHelper {

    private static final Logger logger = LoggerFactory.getLogger(MisAuthenticationHelper.class);

    /**
     * Extracts the authentication token from the current context.
     * Tries multiple sources in order of preference.
     *
     * @return the authorization header with Bearer prefix, or null if not found
     */
    public String getAuthorizationHeader() {
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

        logger.debug("No authorization token found in any context");
        return null;
    }

    /**
     * Gets the UserAuth object from the current MisAuthentication context.
     *
     * @return UserAuth object if available, null otherwise
     */
    public UserAuth getCurrentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication instanceof MisAuthentication) {
                MisAuthentication misAuth = (MisAuthentication) authentication;
                // Note: The UserAuth object is stored during authentication creation
                // We need to reconstruct it from available data
                return reconstructUserAuthFromMisAuthentication(misAuth);
            }
        } catch (Exception e) {
            logger.debug("Error getting current user: {}", e.getMessage());
        }
        return null;
    }

    /**
     * Gets the authentication token from the current HTTP request.
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
     * Gets the authentication token from the Spring Security context.
     * Specifically handles MisAuthentication where the token is stored in details.
     *
     * @return the authorization token, or null if not available
     */
    private String getTokenFromSecurityContext() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null) {
                logger.debug("No authentication in security context");
                return null;
            }

            // Handle MisAuthentication specifically
            if (authentication instanceof MisAuthentication) {
                MisAuthentication misAuth = (MisAuthentication) authentication;

                // The auth header is stored in the details field based on the constructor:
                // public MisAuthentication(UserAuth userAuth, boolean secure, String authHeader) {
                //     ...
                //     this.details = authHeader;
                // }
                Object details = misAuth.getDetails();
                if (details instanceof String) {
                    String token = (String) details;
                    if (token != null && !token.trim().isEmpty()) {
                        logger.trace("Found token in MisAuthentication details");
                        return token;
                    }
                }
            }

            // Fallback for other authentication types
            Object credentials = authentication.getCredentials();
            if (credentials != null) {
                String credString = credentials.toString();
                if (credString != null && !credString.trim().isEmpty() &&
                        (credString.startsWith("Bearer ") || credString.length() > 20)) {
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
     * Reconstructs UserAuth from MisAuthentication data.
     * This is necessary because the original UserAuth object isn't directly stored.
     * Note: We can only access public methods from MisAuthentication.
     *
     * @param misAuth the MisAuthentication object
     * @return reconstructed UserAuth object
     */
    private UserAuth reconstructUserAuthFromMisAuthentication(MisAuthentication misAuth) {
        try {
            UserAuth userAuth = new UserAuth();

            // Get username from principal
            if (misAuth.getPrincipal() != null) {
                userAuth.setUsername(misAuth.getName());
            }

            // Note: We cannot access private fields 'roles' and 'permissions' directly
            // They are private in MisAuthentication and we're not allowed to change it
            // We can only get authorities which includes both roles and permissions
            // as GrantedAuthority objects

            // Extract roles and permissions from authorities
            if (misAuth.getAuthorities() != null) {
                java.util.List<String> roles = new java.util.ArrayList<>();
                java.util.List<String> permissions = new java.util.ArrayList<>();

                for (org.springframework.security.core.GrantedAuthority authority : misAuth.getAuthorities()) {
                    String authorityName = authority.getAuthority();
                    // This is a simple heuristic - you might need to adjust based on your naming convention
                    if (authorityName.startsWith("ROLE_") || authorityName.contains("_ROLE")) {
                        roles.add(authorityName);
                    } else {
                        permissions.add(authorityName);
                    }
                }

                userAuth.setRoles(roles);
                userAuth.setPermissions(permissions);
            }

            // Note: Some fields like userId, email, firstName, lastName
            // are not available in MisAuthentication and would need
            // to be fetched from the auth service if needed

            return userAuth;

        } catch (Exception e) {
            logger.error("Error reconstructing UserAuth: {}", e.getMessage());
            return null;
        }
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
     * Checks if the current user is authenticated.
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

    /**
     * Checks if the current user has a specific role.
     * Since we cannot access private fields, we check the authorities.
     *
     * @param role the role to check
     * @return true if user has the role, false otherwise
     */
    public boolean hasRole(String role) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getAuthorities() != null) {
                return authentication.getAuthorities().stream()
                        .anyMatch(authority -> authority.getAuthority().equals(role));
            }
        } catch (Exception e) {
            logger.debug("Error checking role: {}", e.getMessage());
        }
        return false;
    }

    /**
     * Checks if the current user has a specific permission.
     * Since we cannot access private fields, we check the authorities.
     *
     * @param permission the permission to check
     * @return true if user has the permission, false otherwise
     */
    public boolean hasPermission(String permission) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getAuthorities() != null) {
                return authentication.getAuthorities().stream()
                        .anyMatch(authority -> authority.getAuthority().equals(permission));
            }
        } catch (Exception e) {
            logger.debug("Error checking permission: {}", e.getMessage());
        }
        return false;
    }
}