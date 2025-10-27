package rw.ac.ilpd.academicservice.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.ilpd.academicservice.model.sql.ShortCourseTopicLecturer;

import java.util.UUID;

public interface ShortCourseTopicLecturerRepository extends JpaRepository<ShortCourseTopicLecturer, UUID>
{
}
