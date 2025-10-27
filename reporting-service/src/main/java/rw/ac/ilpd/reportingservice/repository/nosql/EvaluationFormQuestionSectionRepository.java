package rw.ac.ilpd.reportingservice.repository.nosql;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.reportingservice.model.nosql.document.EvaluationFormQuestionSection;

import java.util.UUID;

@Repository
public interface EvaluationFormQuestionSectionRepository extends
        MongoRepository<EvaluationFormQuestionSection, String> {
    boolean existsByTitle(String title);
}
