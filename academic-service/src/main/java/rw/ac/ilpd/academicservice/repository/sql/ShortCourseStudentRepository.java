package rw.ac.ilpd.academicservice.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.ilpd.academicservice.model.sql.ShortCourseStudent;

import java.util.UUID;

public interface ShortCourseStudentRepository extends JpaRepository<ShortCourseStudent, UUID> {
}
