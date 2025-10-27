package rw.ac.ilpd.registrationservice.config;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationDocumentClientConfig {

    @Bean
    Encoder feignFormEncoder() {
        return new SpringFormEncoder();
    }
}