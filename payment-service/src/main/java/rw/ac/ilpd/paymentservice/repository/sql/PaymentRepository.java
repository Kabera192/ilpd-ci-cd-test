package rw.ac.ilpd.paymentservice.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.paymentservice.model.sql.Payment;

import java.util.UUID;
@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
}
