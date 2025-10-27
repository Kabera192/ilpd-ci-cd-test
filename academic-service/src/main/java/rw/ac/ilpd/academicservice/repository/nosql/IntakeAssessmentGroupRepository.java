package rw.ac.ilpd.academicservice.repository.nosql;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import rw.ac.ilpd.academicservice.model.nosql.document.IntakeAssessmentGroup;

import java.util.List;
import java.util.UUID;

public interface IntakeAssessmentGroupRepository extends MongoRepository<IntakeAssessmentGroup, String>
{
    List<IntakeAssessmentGroup> findByIntakeIdAndComponentId(UUID intakeId, UUID componentId);
    Page<IntakeAssessmentGroup> findByIntakeIdAndComponentId(UUID intakeId, UUID componentId, Pageable pageable);
}
