package rw.ac.ilpd.registrationservice.config;

import feign.Logger;
import feign.Request;
import feign.RequestInterceptor;
import feign.Response;
import feign.Retryer;
import feign.Util;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.form.spring.SpringFormEncoder;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import rw.ac.ilpd.registrationservice.exception.DownstreamServiceException;
import rw.ac.ilpd.registrationservice.util.TokenExtractor;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * Global Feign client configuration.
 * Provides default configurations for all Feign clients in the application.
 */
@Configuration
public class FeignConfig {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(FeignConfig.class);

    @Autowired
    private TokenExtractor tokenExtractor;

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public Request.Options requestOptions() {
        return new Request.Options(5000, TimeUnit.MILLISECONDS,
                10000, TimeUnit.MILLISECONDS,
                true);
    }

    @Bean
    public Retryer retryer() {
        return new Retryer.Default(100, 1000, 3);
    }

    /**
     * Default encoder for most Feign clients
     * This encoder supports multipart form data for file uploads
     */
    @Bean
    @Primary
    public Encoder feignFormEncoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        return new SpringFormEncoder(new SpringEncoder(messageConverters));
    }

    /**
     * Basic JSON encoder specifically for AUTH-SERVICE (no multipart support needed)
     * This resolves the "No bean found of type interface feign.codec.Encoder for AUTH-SERVICE" error
     */
    @Bean("authServiceEncoder")
    public Encoder authServiceEncoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        return new SpringEncoder(messageConverters);
    }

    /**
     * Ensure this bean is available for all Feign clients
     * This is needed for the NotificationDocumentClient to handle multipart form data
     */
    @Bean
    public feign.form.FormEncoder formEncoder() {
        return new feign.form.FormEncoder();
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            try {
                String token = tokenExtractor.getAuthorizationToken();
                if (token != null && !token.isEmpty()) {
                    logger.debug("Adding Authorization header to Feign request");
                    // Check if token already has Bearer prefix to avoid duplication
                    if (token.startsWith("Bearer ")) {
                        requestTemplate.header("Authorization", token);
                    } else {
                        requestTemplate.header("Authorization", "Bearer " + token);
                    }
                }
            } catch (Exception e) {
                logger.warn("Failed to extract token for Feign request", e);
            }
        };
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }

    private static class CustomErrorDecoder implements ErrorDecoder {
        private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CustomErrorDecoder.class);

        @Override
        public Exception decode(String methodKey, Response response) {
            String responseBody = "";
            String serviceName = extractServiceName(methodKey);

            try {
                if (response.body() != null) {
                    responseBody = Util.toString(response.body().asReader(StandardCharsets.UTF_8));
                }
            } catch (Exception e) {
                logger.warn("Failed to read response body from {}: {}", serviceName, e.getMessage());
                responseBody = "Unable to read error response";
            }

            logger.error("Error from {}: HTTP {} - {}", serviceName, response.status(), responseBody);

            return new DownstreamServiceException(
                    response.status(),
                    responseBody,
                    serviceName
            );
        }

        private String extractServiceName(String methodKey) {
            if (methodKey.contains("Client#")) {
                return methodKey.substring(0, methodKey.indexOf("Client#"));
            }
            return "Unknown";
        }
    }
}