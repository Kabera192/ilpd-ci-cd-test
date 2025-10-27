package rw.ac.ilpd.academicservice.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.ilpd.academicservice.model.sql.ShortCourseTopicMaterial;

import java.util.UUID;

public interface ShortCourseTopicMaterialRepository extends JpaRepository<ShortCourseTopicMaterial, UUID>
{
}
