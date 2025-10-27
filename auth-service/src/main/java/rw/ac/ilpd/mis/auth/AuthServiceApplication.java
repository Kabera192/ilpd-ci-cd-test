package rw.ac.ilpd.mis.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication(scanBasePackages = {
        "rw.ac.ilpd.mis.auth",      // your app
        "rw.ac.ilpd.mis.shared"     // the shared module
})
@EnableMongoAuditing
@EnableFeignClients()
public class AuthServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }
}

