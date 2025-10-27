package rw.ac.ilpd.reportingservice.repository.nosql;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.reportingservice.model.nosql.document.EvaluationForm;

import java.util.UUID;

@Repository
public interface EvaluationFormRepository extends MongoRepository<EvaluationForm, String> {
}
