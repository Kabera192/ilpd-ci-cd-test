package rw.ac.ilpd.registrationservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import rw.ac.ilpd.registrationservice.security.SuperAdminAuthorizationManager;
import rw.ac.ilpd.registrationservice.security.jwt.AuthEntryPointJwt;
import rw.ac.ilpd.registrationservice.security.jwt.AuthTokenFilter;
import rw.ac.ilpd.registrationservice.security.jwt.RestAccessDeniedHandler;

import java.util.Arrays;

/**
 * Security configuration class for managing authentication and authorization in the application.
 * Configures security filters, password encoding, exception handling, and access management.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    /**
     * Logger instance for capturing and managing log messages within the SecurityConfig class.
     * Used to log important events and debug information to facilitate monitoring, debugging,
     * and tracing of activities related to application security configuration.
     * <p>
     * This logger is statically initialized for consistent access across the class and is
     * configured with the class name (SecurityConfig) as its logging context.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);
    
    /**
     * List of Swagger/OpenAPI endpoints that should be publicly accessible without authentication.
     * These paths are used to configure security to allow unrestricted access to API documentation.
     */
    private static final String[] SWAGGER_WHITELIST = {
            // default (no context path)
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/swagger-config",
            // common extras
            "/api-docs/**",
            "/webjars/**",
            "/favicon.ico",
            "/actuator/**",
            "/health"
    };

    /**
     * A configuration property that holds the list of public endpoints in the application.
     * This property is populated from the value specified in the application's configuration file
     * using the key `security.public-endpoints`.
     * <p>
     * Public endpoints are typically URLs or patterns that do not require authentication or authorization
     * and are accessible to all users.
     */
    @Value("${security.public-endpoints}")
    private String publicEndpoints;

    /**
     * Handles unauthorized requests by delegating to the {@link AuthEntryPointJwt}.
     * This component is responsible for processing and responding to requests from users
     * who attempt to access secured resources without proper authentication credentials.
     * <p>
     * The `unauthorizedHandler` serves as the entry point that handles authentication errors
     * and returns appropriate HTTP responses, such as `401 Unauthorized`.
     * <p>
     * It is used in the security configuration to manage unauthenticated access to protected endpoints.
     */
    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    /**
     * A handler for processing access denial events in the Spring Security context.
     * This handler is used to manage situations where an authenticated user attempts
     * to access a resource without having the necessary permissions.
     * <p>
     * Utilizes the {@link RestAccessDeniedHandler} implementation to customize the behavior
     * when access is denied, such as logging unauthorized access attempts and constructing
     * detailed error responses in JSON format.
     */
    @Autowired
    private RestAccessDeniedHandler accessDeniedHandler;

    /**
     * An instance of {@link SuperAdminAuthorizationManager} used to handle the authorization
     * of requests specifically for users with the "super_admin" privilege.
     * This manager ensures that access is granted to super administrators while delegating
     * other authorization decisions to a standard AuthorizationManager if configured.
     * <p>
     * This component is utilized in the security configuration to enforce
     * super administrator-specific access rules across the system.
     * <p>
     * The {@link SuperAdminAuthorizationManager} checks access permissions by evaluating
     * the user's granted authorities and determining whether the "super_admin" privilege
     * is present. Additional authorization checks can be performed by a delegate
     * manager if specified.
     */
    @Autowired
    private SuperAdminAuthorizationManager superAdminAuthorizationManager;

    /**
     * Provides a bean for encoding passwords. This method returns an instance of
     * {@link BCryptPasswordEncoder}, which is a secure password hashing algorithm
     * designed to provide a computationally expensive way to encode passwords and
     * protect them against brute force attacks.
     *
     * @return an instance of {@link PasswordEncoder} configured with the BCrypt algorithm
     * for encoding passwords.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Provides an AuthenticationManager bean, which is used to handle authentication processes
     * within the application. This method retrieves the AuthenticationManager from the provided
     * AuthenticationConfiguration.
     *
     * @param authConfig the authentication configuration used to retrieve the AuthenticationManager
     * @return an instance of AuthenticationManager configured based on the application's authentication settings
     * @throws Exception if there is any issue while retrieving the AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Registers a JWT authentication filter bean for the application.
     * The filter is responsible for validating tokens on incoming requests, ensuring the user
     * is authenticated and authorized to access protected resources.
     *
     * @return an instance of {@link AuthTokenFilter} that acts as a filter for processing authentication tokens.
     */
    @Bean
    public AuthTokenFilter authTokenFilter() {
        return new AuthTokenFilter();
    }

    /**
     * Configures the {@link SecurityFilterChain} for the application, managing various security aspects such as
     * cross-origin requests, CSRF protection, session management, exception handling, and authorization rules.
     * The configuration includes handling of endpoints that need to be publicly accessible, options for specific HTTP
     * methods, and default authorization requirements for all other requests.
     *
     * @param http the {@link HttpSecurity} instance to be customized, providing a chain of configurations for web
     *             security.
     * @return a fully configured {@link SecurityFilterChain} instance, enforcing security policies as specified in
     * the method.
     * @throws Exception if an error occurs while building the security configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Parse public endpoints from properties
        String[] publicPaths = publicEndpoints.split(",");
        
        // Define gateway prefixed paths for Swagger
        String[] prefixedPaths = {
                "/api/v1/registration/v3/api-docs/**",
                "/api/v1/registration/v3/api-docs.yaml",
                "/api/v1/registration/swagger-ui.html",
                "/api/v1/registration/swagger-ui/**",
                "/api/v1/registration/v3/api-docs/swagger-config",
                "/api/v1/registration/api-docs/**",
                "/api/v1/registration/webjars/**",
                "/api/v1/registration/favicon.ico",
                "/api/v1/registration/actuator/**",
                "/api/v1/registration/health"
        };

        http.cors(cors -> cors.configure(http)).csrf(AbstractHttpConfigurer::disable).exceptionHandling(
                        exception -> exception.authenticationEntryPoint(unauthorizedHandler)
                                .accessDeniedHandler(accessDeniedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    // Public endpoints
                    Arrays.stream(publicPaths).forEach(path -> auth.requestMatchers(path.trim()).permitAll());

                    // OPTIONS requests for CORS
                    auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();

                    // Universities - public read access
                    auth.requestMatchers(HttpMethod.GET, "/universities/**").permitAll();

                    // Swagger/OpenAPI endpoints - direct access
                    auth.requestMatchers(SWAGGER_WHITELIST).permitAll();
                    
                    // Swagger/OpenAPI endpoints - gateway forwarded access
                    auth.requestMatchers(prefixedPaths).permitAll();
                    
                    // All other requests require authentication with superadmin override
                    auth.anyRequest().access(superAdminAuthorizationManager);
                });

        // Add JWT token filter
        http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}