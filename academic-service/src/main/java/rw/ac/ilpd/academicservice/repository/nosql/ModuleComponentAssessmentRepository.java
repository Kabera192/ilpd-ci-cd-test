package rw.ac.ilpd.academicservice.repository.nosql;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.academicservice.model.nosql.document.ModuleComponentAssessment;

import java.util.UUID;

@Repository
public interface ModuleComponentAssessmentRepository extends MongoRepository<ModuleComponentAssessment, String> {
    boolean existsByAssessmentTypeId(UUID assessmentTypeId);
}
