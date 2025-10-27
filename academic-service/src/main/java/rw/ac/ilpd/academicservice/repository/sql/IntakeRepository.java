package rw.ac.ilpd.academicservice.repository.sql;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.academicservice.model.sql.Intake;
import rw.ac.ilpd.academicservice.model.sql.Program;

import java.util.UUID;

@Repository
public interface IntakeRepository extends JpaRepository<Intake, UUID>
{
    boolean existsByNameAndProgram(String name, Program program);

    boolean existsByNameAndProgramAndIdNot(@NotBlank(message = "Name cannot be blank") @Size(max = 30, message = "Name cannot exceed 30 characters") String name, Program program, UUID uuid);
}
