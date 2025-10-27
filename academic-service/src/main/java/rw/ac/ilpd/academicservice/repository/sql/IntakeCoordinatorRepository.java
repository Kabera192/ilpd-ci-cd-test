package rw.ac.ilpd.academicservice.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.ilpd.academicservice.model.sql.IntakeCoordinator;

import java.util.UUID;

public interface IntakeCoordinatorRepository extends JpaRepository<IntakeCoordinator, UUID>
{
}
