package rw.ac.ilpd.gatewayservice.config;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;

@Configuration
public class CorsDebugConfig {

    @Bean
    @Order(-1) // Execute before other filters
    public GlobalFilter corsDebugFilter() {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String origin = request.getHeaders().getFirst("Origin");
            String method = request.getMethod().name();
            String path = request.getPath().value();

            System.out.println("=== CORS DEBUG ===");
            System.out.println("Request Method: " + method);
            System.out.println("Request Path: " + path);
            System.out.println("Origin Header: " + origin);
            System.out.println("All Headers: ");
            request.getHeaders().forEach((key, value) ->
                    System.out.println("  " + key + ": " + value));
            System.out.println("==================");

            return chain.filter(exchange);
        };
    }
}
