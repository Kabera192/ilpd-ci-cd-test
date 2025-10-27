package rw.ac.ilpd.registrationservice.config;

import feign.codec.Encoder;
import feign.form.FormEncoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Specific configuration for the NotificationDocumentTypeClient.
 * This configuration is applied only to the NotificationDocumentTypeClient context.
 */
@Configuration
public class NotificationDocumentTypeClientConfig {

    /**
     * Encoder specifically for the NotificationDocumentTypeClient.
     * Handles multipart form data for file uploads.
     */
    @Bean
    @Primary
    public Encoder feignEncoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        return new SpringFormEncoder(new SpringEncoder(messageConverters));
    }
    
    /**
     * Form encoder for handling multipart form data
     */
    @Bean
    public FormEncoder formEncoder() {
        return new FormEncoder();
    }
    
    /**
     * Ensure the encoder bean is available with the expected name
     */
    @Bean
    public Encoder encoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        return new SpringFormEncoder(new SpringEncoder(messageConverters));
    }
}