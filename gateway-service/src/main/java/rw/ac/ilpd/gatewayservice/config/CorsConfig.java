package rw.ac.ilpd.gatewayservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // Set allow credentials
        config.setAllowCredentials(true);

        // Set allowed origins
        config.setAllowedOrigins(Arrays.asList(
                "http://localhost:4200",
                "http://localhost:8082",
                "http://127.0.0.1:8082",
                "https://misdev.ilpd.ac.rw",
                "https://misapi.ilpd.ac.rw",
                "http://154.68.65.32:8082",
                "http://10.0.130.104:8082",
                "http://10.0.139.37:8082"
        ));

        // Set allowed methods
        config.setAllowedMethods(Arrays.asList(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "OPTIONS",
                "PATCH"
        ));

        // Set allowed headers
        config.addAllowedHeader("*");

        // Set max age
        config.setMaxAge(3600L);

        // Create source and register configuration
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }
}
