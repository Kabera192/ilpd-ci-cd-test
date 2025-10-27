package rw.ac.ilpd.paymentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
		"rw.ac.ilpd.paymentservice",      // your app
		"rw.ac.ilpd.mis.shared"     // the shared module
})
public class PaymentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentServiceApplication.class, args);
	}

}
