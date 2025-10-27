package rw.ac.ilpd.academicservice.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.academicservice.model.sql.StudyModeSession;

import java.util.UUID;
@Repository
public interface StudyModeSessionRepository extends JpaRepository<StudyModeSession, UUID> {
}
