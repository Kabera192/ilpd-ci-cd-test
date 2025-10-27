package rw.ac.ilpd.registrationservice.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import rw.ac.ilpd.mis.shared.dto.user.UserAuth;
import rw.ac.ilpd.registrationservice.service.CachedAuthService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Simplified JWT Authentication filter following the same pattern as auth-service
 * Uses CachedAuthService for token validation but follows standard Spring Security approach
 */
@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Autowired
    private CachedAuthService cachedAuthService;

    @Autowired
    private AuthEntryPointJwt authenticationEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        logger.debug("AuthTokenFilter -> {} {}", request.getMethod(), request.getRequestURI());

        try {
            // If already authenticated, continue
            Authentication existing = SecurityContextHolder.getContext().getAuthentication();
            if (existing != null && existing.isAuthenticated()) {
                chain.doFilter(request, response);
                return;
            }

            // Get Authorization header
            String authHeader = request.getHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                logger.debug("Processing JWT token for request");

                try {
                    // Validate token using CachedAuthService
                    UserAuth userAuth = cachedAuthService.validateToken(authHeader);

                    if (userAuth != null && userAuth.getUsername() != null) {
                        logger.info("AuthTokenFilter authenticated username: {}", userAuth.getUsername());

                        // Create authorities from UserAuth
                        List<SimpleGrantedAuthority> authorities = userAuth.getAuthorities() != null
                                ? userAuth.getAuthorities().stream()
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList())
                                : List.of();

                        // Create standard Spring Security authentication
                        var authentication = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                                userAuth.getUsername(),
                                null,
                                authorities
                        );

                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);

                        logger.info("Authorities: {}", authorities);
                        logger.debug("Security context set for user: {} with {} authorities",
                                userAuth.getUsername(), authorities.size());

                        // Log validation method if it was local validation
                        if ("local-validation".equals(userAuth.getId())) {
                            logger.info("Note: Token was validated locally (auth-service may be experiencing issues)");
                        }

                    } else {
                        logger.warn("Token validation failed - invalid or expired token");
                        throw new BadCredentialsException("Invalid JWT token");
                    }

                } catch (BadCredentialsException bce) {
                    // Re-throw authentication exceptions
                    throw bce;
                } catch (Exception ex) {
                    // Convert any other exception to authentication exception
                    logger.error("Unexpected error during token validation: {}", ex.getMessage());

                    // Check for specific error patterns
                    String errorMessage = ex.getMessage();
                    if (errorMessage != null) {
                        if (errorMessage.contains("Connection refused") || errorMessage.contains("ConnectException")) {
                            logger.error("Cannot connect to auth-service");
                            throw new BadCredentialsException("Authentication service temporarily unavailable");
                        } else if (errorMessage.contains("500") || errorMessage.contains("Internal Server Error")) {
                            logger.error("Auth-service returned internal server error");
                            throw new BadCredentialsException("Authentication service experiencing issues");
                        }
                    }

                    throw new BadCredentialsException("Token validation failed");
                }
            } else {
                logger.debug("No valid Authorization header found in request");
            }

            // Continue filter chain
            chain.doFilter(request, response);

        } catch (AuthenticationException ae) {
            // Only auth problems → handled here (401)
            SecurityContextHolder.clearContext();
            logger.warn("Authentication failure: {}", ae.getMessage());
            authenticationEntryPoint.commence(request, response, ae);

        } catch (Exception ex) {
            // NOT an auth problem → let MVC/ControllerAdvice handle it (400/422/500 etc.)
            SecurityContextHolder.clearContext();
            logger.error("Error during authentication process: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();

        // Skip authentication for public endpoints
        return path.startsWith("/public/") ||
                path.startsWith("/health") ||
                path.startsWith("/actuator/") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger-ui") ||
                path.startsWith("/api-docs") ||
                path.startsWith("/webjars/") ||
                path.equals("/favicon.ico");
    }
}