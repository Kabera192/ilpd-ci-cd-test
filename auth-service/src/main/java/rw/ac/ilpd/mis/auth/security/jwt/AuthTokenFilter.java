package rw.ac.ilpd.mis.auth.security.jwt;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project MIS
 * @date 14/07/2024
 */
import rw.ac.ilpd.mis.auth.service.impl.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;


@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(AuthTokenFilter.class);

    private final JwtUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    public AuthTokenFilter(JwtUtils jwtUtils,
                           UserDetailsServiceImpl userDetailsService,
                           AuthEntryPointJwt authenticationEntryPoint) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {
        log.debug("AuthTokenFilter -> {} {}", request.getMethod(), request.getRequestURI());

        try {
            // If already authenticated, continue
            Authentication existing = SecurityContextHolder.getContext().getAuthentication();
            if (existing != null && existing.isAuthenticated()) {
                chain.doFilter(request, response);
                return;
            }

            String jwt = parseJwt(request);
            if (jwt != null) {
                if (!jwtUtils.validateJwtToken(jwt)) {
                    // Turn any validation failure into an AuthenticationException
                    throw new BadCredentialsException("Invalid JWT token");
                }

                String username = jwtUtils.getUserNameFromJwtToken(jwt);
                log.info("AuthTokenFilter authenticated username: {}", username);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                var authentication =
                        new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

                log.info("Authorities: {}", userDetails.getAuthorities());
            }

            chain.doFilter(request, response);

        } catch (AuthenticationException ae) {
            // Only auth problems → handled here (401)
            SecurityContextHolder.clearContext();
            log.warn("Authentication failure: {}", ae.getMessage());
            authenticationEntryPoint.commence(request, response, ae);

        } catch (Exception ex) {
            // NOT an auth problem → let MVC/ControllerAdvice handle it (400/422/500 etc.)
            SecurityContextHolder.clearContext();
            throw ex;
        }
    }

    private String parseJwt(HttpServletRequest request) {
        String jwt = jwtUtils.getJwtFromHeader(request);
        log.debug("JWT extracted: {}", jwt != null ? "[present]" : "[absent]");
        return jwt;
    }

}
