package rw.ac.ilpd.academicservice.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.ilpd.academicservice.model.sql.Curriculum;

import java.util.UUID;

public interface CurriculumRepository extends JpaRepository<Curriculum, UUID> {
    boolean existsByNameAndProgramId(String name, UUID id);
    boolean existsById(UUID id);
}
