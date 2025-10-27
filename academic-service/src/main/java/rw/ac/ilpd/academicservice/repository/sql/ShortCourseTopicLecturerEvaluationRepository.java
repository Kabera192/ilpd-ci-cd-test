package rw.ac.ilpd.academicservice.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.academicservice.model.sql.ShortCourseTopicLecturerEvaluation;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface ShortCourseTopicLecturerEvaluationRepository  extends JpaRepository<ShortCourseTopicLecturerEvaluation, UUID> {
    Optional<ShortCourseTopicLecturerEvaluation> findByShortCourseTopicLecturerId(UUID shortCourseTopicLecturerId);
}
