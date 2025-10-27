package rw.ac.ilpd.reportingservice.repository.nosql;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import rw.ac.ilpd.reportingservice.model.nosql.document.EvaluationFormQuestionGroup;


@Repository
public interface EvaluationFormQuestionGroupRepository extends
        MongoRepository<EvaluationFormQuestionGroup, String>
{
    boolean existsByName(String name);
}
