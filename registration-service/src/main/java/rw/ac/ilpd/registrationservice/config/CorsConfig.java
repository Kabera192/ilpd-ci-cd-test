package rw.ac.ilpd.registrationservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * Provides CORS (Cross-Origin Resource Sharing) configuration for the application.
 * This configuration determines how the application handles requests from different origins.
 * It allows specifying allowed origins, methods, headers, credentials, and preflight request cache duration.
 * The configuration is constructed based on properties provided in application configuration files.
 */
@Configuration
public class CorsConfig {

    @Value("${security.cors.allowed-origins}")
    private String allowedOrigins;

    @Value("${security.cors.allowed-methods}")
    private String allowedMethods;

    @Value("${security.cors.allowed-headers}")
    private String allowedHeaders;

    @Value("${security.cors.allow-credentials}")
    private boolean allowCredentials;

    @Value("${security.cors.max-age}")
    private long maxAge;

    /**
     * Configures the CORS (Cross-Origin Resource Sharing) behavior for the application.
     * This method creates and returns a CorsConfigurationSource that defines rules
     * for allowed origins, methods, headers, credentials, and preflight request cache duration.
     * It supports parsing these configurations from application properties.
     *
     * @return a configured instance of CorsConfigurationSource for managing CORS policies.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Parse and set allowed origins
        if ("*".equals(allowedOrigins)) {
            configuration.setAllowedOriginPatterns(List.of("*"));
        } else {
            configuration.setAllowedOrigins(Arrays.asList(allowedOrigins.split(",")));
        }

        // Parse and set allowed methods
        configuration.setAllowedMethods(Arrays.asList(allowedMethods.split(",")));

        // Parse and set allowed headers
        if ("*".equals(allowedHeaders)) {
            configuration.setAllowedHeaders(List.of("*"));
        } else {
            configuration.setAllowedHeaders(Arrays.asList(allowedHeaders.split(",")));
        }

        // Set exposed headers (headers that the browser can access)
        configuration.setExposedHeaders(Arrays.asList(
                "Authorization",
                "Content-Type",
                "X-Total-Count",
                "X-Page-Number",
                "X-Page-Size"
        ));

        // Set credentials support
        configuration.setAllowCredentials(allowCredentials);

        // Set max age for preflight requests
        configuration.setMaxAge(maxAge);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}