package rw.ac.ilpd.hostelservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class
}, scanBasePackages = {
        "rw.ac.ilpd.hostelservice",      // your app
        "rw.ac.ilpd.mis.shared"     // the shared module
})
@EnableMongoAuditing
@EnableFeignClients
public class HostelServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HostelServiceApplication.class, args);
    }

}
