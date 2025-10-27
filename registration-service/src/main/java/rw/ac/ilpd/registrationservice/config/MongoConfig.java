package rw.ac.ilpd.registrationservice.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.UuidRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Minimal MongoDB configuration that avoids reflection issues.
 * Uses Spring Boot's auto-configuration for most components.
 */
@Configuration
public class MongoConfig {

    @Value("${spring.data.mongodb.uri:mongodb://localhost:27017/registration_db}")
    private String mongoUri;

    /**
     * Only customize the MongoClient for UUID representation.
     * Let Spring Boot handle the rest automatically.
     */
    @Bean
    public MongoClient mongoClient() {
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new com.mongodb.ConnectionString(mongoUri))
                .uuidRepresentation(UuidRepresentation.JAVA_LEGACY)
                .build();

        return MongoClients.create(settings);
    }
}