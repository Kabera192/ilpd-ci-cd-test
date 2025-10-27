package rw.ac.ilpd.registrationservice.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up Swagger/OpenAPI documentation for the application.
 * This class customizes the OpenAPI specification by specifying API metadata, server details,
 * and security configurations such as JWT bearer authentication.
 * <p>
 * The configuration dynamically determines the server URL and description based on
 * the active Spring profile (e.g., "prod" or "dev").
 */
@Configuration
public class SwaggerConfig {

    /**
     * Represents the active Spring profile currently in use by the application.
     * This variable is loaded from the `spring.profiles.active` property in the application configuration.
     * If the property is not set, it defaults to "dev".
     * <p>
     * The active Spring profile determines environment-specific configurations
     * such as database connection details, API endpoints, or security settings.
     */
    @Value("${spring.profiles.active:dev}")
    private String activeProfile;

    /**
     * Configures the OpenAPI documentation for the application, including API details, server information,
     * and security configurations. This method sets up a JWT bearer token-based authentication scheme
     * and defines security requirements and server details for the API specification.
     *
     * @return an instance of {@link OpenAPI} configured with API metadata, server settings, and security details.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        // Define JWT security scheme
        SecurityScheme securityScheme = new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer")
                .bearerFormat("JWT").description("Enter JWT Bearer token **_only_**");

        // Define security requirement
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("Bearer Authentication");

        return new OpenAPI().info(apiInfo()).addServersItem(getServer())
                .components(new Components().addSecuritySchemes("Bearer Authentication", securityScheme))
                .addSecurityItem(securityRequirement);
    }

    /**
     * Constructs the API metadata information for the OpenAPI specification.
     * This includes details such as the title, description, version, contact information,
     * and license for the API documentation.
     *
     * @return an instance of {@link Info} containing the metadata for the API.
     */
    private Info apiInfo() {
        return new Info().title("Registration Service API")
                .description("Registration Service API Documentation - JWT Secured").version("1.0.0")
                .contact(new Contact().name("ILPD MIS Team").email("support@ilpd.ac.rw").url("https://ilpd.ac.rw"))
                .license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0.html"));
    }

    /**
     * Determines the server configuration based on the active Spring profile.
     * If the active profile is "prod", it returns a server configured for production.
     * Otherwise, it defaults to a development server configuration.
     *
     * @return a Server instance representing the API's server configuration, with a URL
     * and description based on the current environment (production or development).
     */
    private Server getServer() {
        if ("prod".equals(activeProfile)) {
            return new Server().url("https://misapi.ilpd.ac.rw/api/v1/registration/")
                    .description("Production server via Gateway");
        }
        return new Server().url("http://localhost:8082/")
                .description("Development server via Gateway");
    }
}