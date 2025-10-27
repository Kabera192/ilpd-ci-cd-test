package rw.ac.ilpd.academicservice.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.ilpd.academicservice.model.sql.IntakeApplicationRequiredDocName;

import java.util.UUID;

public interface IntakeApplicationRequiredDocNameRepository extends JpaRepository<IntakeApplicationRequiredDocName, UUID> {
}
