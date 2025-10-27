package rw.ac.ilpd.academicservice.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.academicservice.model.sql.StudyMode;

import java.util.UUID;
@Repository
public interface StudyModeRepository extends JpaRepository<StudyMode, UUID> {
    boolean existsByNameIgnoreCase(String name);
}
