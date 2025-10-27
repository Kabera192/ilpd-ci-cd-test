package rw.ac.ilpd.reportingservice.repository.nosql;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import rw.ac.ilpd.reportingservice.model.nosql.document.EvaluationFormQuestion;
import rw.ac.ilpd.sharedlibrary.dto.evaluation.EvaluationFormQuestionResponse;

import java.util.List;
import java.util.UUID;

@Repository
public interface EvaluationFormQuestionRepository extends
        MongoRepository<EvaluationFormQuestion, String>
{
    boolean existsByGroupId(String id);

    List<EvaluationFormQuestion> findBySectionId(String id);

    List<EvaluationFormQuestion> findByGroupId(String groupId);
    boolean existsBySectionId(String id);

}
