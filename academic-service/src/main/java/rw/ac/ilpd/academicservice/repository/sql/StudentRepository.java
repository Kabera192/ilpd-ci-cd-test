package rw.ac.ilpd.academicservice.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.ilpd.academicservice.model.sql.Student;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    public boolean existsByUserId(UUID userId);
}
