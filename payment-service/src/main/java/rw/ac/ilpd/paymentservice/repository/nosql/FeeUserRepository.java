package rw.ac.ilpd.paymentservice.repository.nosql;

import org.springframework.data.mongodb.repository.MongoRepository;
import rw.ac.ilpd.paymentservice.model.nosql.FeeUser;

public interface FeeUserRepository extends MongoRepository<FeeUser,String> {
}
