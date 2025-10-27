package rw.ac.ilpd.reportingservice.repository.nosql;

import org.springframework.data.mongodb.repository.MongoRepository;
import rw.ac.ilpd.reportingservice.model.nosql.document.EvaluatedUser;

import java.util.UUID;

public interface EvaluatedUserRepository extends MongoRepository<EvaluatedUser, String> {
}
