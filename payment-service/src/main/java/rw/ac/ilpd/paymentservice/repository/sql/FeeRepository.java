package rw.ac.ilpd.paymentservice.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.paymentservice.model.sql.Fee;

import java.util.UUID;
@Repository
public interface FeeRepository extends JpaRepository<Fee, UUID> {
}
