package rw.ac.ilpd.mis.auth.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 11/08/2025
 */

@Configuration
public class SwaggerConfig {
    @Value("${spring.profiles.active}")
    private String PROFILE_MODE;
    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Auth Service API")
                        .version("1.0.0")
                        .description("API documentation for Auth Service")
                        .contact(new Contact()
                                .name("API Support")
                                .email("zigdidier@gmail.com")
                                .url("https://misdev.ilpd.ac.rw"))
                )
                .servers(List.of(
                        new Server()
                                .url(PROFILE_MODE.equals("dev")?"http://localhost:8082/"
                                        :"https://misapi.ilpd.ac.rw/")
                                .description("Development server via Gateway"),
                        new Server()
                                .url("http://10.0.130.104:8082/")
                                .description("Development server via Gateway")
                ));
    }
}