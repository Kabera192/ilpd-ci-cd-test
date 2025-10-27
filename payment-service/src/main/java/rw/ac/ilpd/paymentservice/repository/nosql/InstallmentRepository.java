package rw.ac.ilpd.paymentservice.repository.nosql;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.paymentservice.model.nosql.Installment;
@Repository
public interface InstallmentRepository extends MongoRepository<Installment, String> {
}
