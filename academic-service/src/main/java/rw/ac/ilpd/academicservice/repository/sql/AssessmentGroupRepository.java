package rw.ac.ilpd.academicservice.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.ilpd.academicservice.model.sql.AssessmentGroup;

import java.util.UUID;

public interface AssessmentGroupRepository extends JpaRepository<AssessmentGroup, UUID>
{
    boolean existsByNameIgnoreCase(String name);
}
