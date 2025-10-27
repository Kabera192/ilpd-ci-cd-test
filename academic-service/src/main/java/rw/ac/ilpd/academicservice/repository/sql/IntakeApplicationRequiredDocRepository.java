package rw.ac.ilpd.academicservice.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.academicservice.model.sql.Intake;
import rw.ac.ilpd.academicservice.model.sql.IntakeApplicationRequiredDoc;
import rw.ac.ilpd.academicservice.model.sql.IntakeApplicationRequiredDocName;

import java.util.List;
import java.util.UUID;
@Repository
public interface IntakeApplicationRequiredDocRepository  extends JpaRepository<IntakeApplicationRequiredDoc, UUID>
{
    List<IntakeApplicationRequiredDoc> findByIntake(Intake intake);
    boolean existsByIntakeAndDocumentRequiredName(Intake intake,
                                                  IntakeApplicationRequiredDocName documentRequiredName);
}
