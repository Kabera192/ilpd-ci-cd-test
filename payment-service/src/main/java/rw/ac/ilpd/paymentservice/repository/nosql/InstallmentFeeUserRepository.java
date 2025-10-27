package rw.ac.ilpd.paymentservice.repository.nosql;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.paymentservice.model.nosql.InstallmentFeeUser;

@Repository
public interface InstallmentFeeUserRepository extends MongoRepository<InstallmentFeeUser, String> {
}
