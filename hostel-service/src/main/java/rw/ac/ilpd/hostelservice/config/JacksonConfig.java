package rw.ac.ilpd.hostelservice.config;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {
    @Bean
    public SimpleModule trimmingModule() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(String.class, new TrimmingDeserializer());
        return module;
    }
}
