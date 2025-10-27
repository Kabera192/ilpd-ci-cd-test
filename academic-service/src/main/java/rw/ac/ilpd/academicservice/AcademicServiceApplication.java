package rw.ac.ilpd.academicservice;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
@SpringBootApplication(scanBasePackages = {
		"rw.ac.ilpd.academicservice",      // your app
		"rw.ac.ilpd.mis.shared"     // the shared module
})
@EnableMongoAuditing
@EnableFeignClients
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@JsonDeserialize
public class AcademicServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AcademicServiceApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void testRabbitConnection() {
		System.out.println("=== Application started, testing RabbitMQ connection ===");
	}

}
