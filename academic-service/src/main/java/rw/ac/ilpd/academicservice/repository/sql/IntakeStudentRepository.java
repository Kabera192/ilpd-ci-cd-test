package rw.ac.ilpd.academicservice.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.ilpd.academicservice.model.sql.Intake;
import rw.ac.ilpd.academicservice.model.sql.IntakeStudent;
import rw.ac.ilpd.academicservice.model.sql.Student;

import java.util.UUID;

public interface IntakeStudentRepository extends JpaRepository<IntakeStudent, UUID>
{
    boolean existsByStudentAndIntake(Intake intake, Student student);
}
