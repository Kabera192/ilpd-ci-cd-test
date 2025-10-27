package rw.ac.ilpd.academicservice.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.ilpd.academicservice.model.sql.ShortCourseTopic;
import rw.ac.ilpd.academicservice.model.sql.ShortCourseTopicEvaluation;
import rw.ac.ilpd.academicservice.model.sql.Student;

import java.util.UUID;

public interface ShortCourseTopicEvaluationRepository extends JpaRepository<ShortCourseTopicEvaluation, UUID> {
    boolean existsByStudentAndTopic(Student student, ShortCourseTopic topic);
}
