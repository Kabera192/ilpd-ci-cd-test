package rw.ac.ilpd.registrationservice;

import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = {"rw.ac.ilpd.registrationservice", "rw.ac.ilpd.mis.shared"})
@EnableDiscoveryClient
@EnableFeignClients
@EnableMongoRepositories
public class RegistrationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegistrationServiceApplication.class, args);
    }

    @Bean
    public Gson gson() {
        return new Gson();
    }
}