package rw.ac.ilpd.notificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication(scanBasePackages = {
		"rw.ac.ilpd.notificationservice",      // your app
		"rw.ac.ilpd.mis.shared"     // the shared module
})
@EnableMongoAuditing
public class NotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}

}
