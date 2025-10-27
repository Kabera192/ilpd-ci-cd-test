package rw.ac.ilpd.inventoryservice;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication(scanBasePackages = {
		"rw.ac.ilpd.inventoryservice",      // your app
		"rw.ac.ilpd.mis.shared"     // the shared module
})
@EnableMongoAuditing
@JsonDeserialize
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

}
