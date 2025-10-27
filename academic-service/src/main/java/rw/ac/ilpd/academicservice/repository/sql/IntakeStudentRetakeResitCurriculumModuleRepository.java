package rw.ac.ilpd.academicservice.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.ilpd.academicservice.model.sql.CurriculumModule;
import rw.ac.ilpd.academicservice.model.sql.Intake;
import rw.ac.ilpd.academicservice.model.sql.IntakeStudentRetakeResitCurriculumModule;
import rw.ac.ilpd.academicservice.model.sql.Student;

import java.util.UUID;

public interface IntakeStudentRetakeResitCurriculumModuleRepository extends JpaRepository<IntakeStudentRetakeResitCurriculumModule, UUID> {
    boolean existsByStudentAndIntakeAndCurriculumModule(Student student, Intake intake, CurriculumModule curriculumModule);

    boolean existsByStudentAndIntakeAndCurriculumModuleAndIdNot(Student student, Intake intake, CurriculumModule curriculumModule, UUID id);
}
