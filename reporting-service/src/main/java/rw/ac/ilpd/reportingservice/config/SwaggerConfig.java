package rw.ac.ilpd.reportingservice.config;

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

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${spring.profiles.active}")
    private String PROFILE_MODE;

    @Bean
    public OpenAPI customOpenAPI() {
        // Define JWT security scheme
        SecurityScheme securityScheme = new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer")
                .bearerFormat("JWT").description("Enter JWT Bearer token **_only_**");

        // Define security requirement
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("Bearer Authentication");

        return new OpenAPI().info(apiInfo()).addServersItem(getServer())
                .addServersItem(optServer())
                .components(new Components().addSecuritySchemes("Bearer Authentication", securityScheme))
                .addSecurityItem(securityRequirement);
    }
    private Info apiInfo() {
        return new Info().title("Reporting Service API")
                .description("Academic Service API Documentation - JWT Secured").version("1.0.0")
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
        if (PROFILE_MODE.equals("prod")) {
            return new Server().url("https://misapi.ilpd.ac.rw/api/v1/reporting/")
                    .description("Production server via Gateway");
        }
        return new Server().url("http://localhost:8082/api/v1/reporting/")
                .description("Development server via Gateway");

    }
    private Server optServer() {
        return new Server().url("http://10.0.139.42:8082/api/v1/reporting/")
                .description("Development server via Gateway");
    }
}