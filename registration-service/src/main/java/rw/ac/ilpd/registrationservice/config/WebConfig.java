package rw.ac.ilpd.registrationservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import rw.ac.ilpd.mis.shared.config.MisConfig;
import rw.ac.ilpd.mis.shared.security.AuthenticationInterceptor;
import rw.ac.ilpd.mis.shared.security.AuthorizationInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    // Swagger endpoints to exclude from interceptors - USING SINGULAR FORM
    private static final String[] SWAGGER_WHITELIST = {
            // Direct access patterns
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/swagger-config",
            "/api-docs/**",
            "/webjars/**",
            "/favicon.ico",
            "/actuator/**",
            "/health",

            // Gateway forwarded patterns - SINGULAR FORM FOR HARMONIZATION
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

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Authentication interceptor - first priority
        registry.addInterceptor(authenticationInterceptor)
                .order(1)
                .addPathPatterns(MisConfig.REGISTRATION_PATH + "/**") // Only registration paths
                .excludePathPatterns(SWAGGER_WHITELIST);

        // Authorization interceptor - second priority
        registry.addInterceptor(new AuthorizationInterceptor())
                .order(2)
                .addPathPatterns(MisConfig.REGISTRATION_PATH + "/**") // Only registration paths
                .excludePathPatterns(SWAGGER_WHITELIST);
    }
}