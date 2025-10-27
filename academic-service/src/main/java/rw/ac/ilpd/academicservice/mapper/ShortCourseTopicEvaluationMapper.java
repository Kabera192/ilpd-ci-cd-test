package rw.ac.ilpd.academicservice.mapper;

import org.springframework.stereotype.Component;
import rw.ac.ilpd.academicservice.model.sql.ShortCourseTopicEvaluation;
import rw.ac.ilpd.academicservice.model.sql.ShortCourseTopic;
import rw.ac.ilpd.academicservice.model.sql.Student;
import rw.ac.ilpd.sharedlibrary.dto.shortcoursetopicevaluation.ShortCourseTopicEvaluationRequest;
import rw.ac.ilpd.sharedlibrary.dto.shortcoursetopicevaluation.ShortCourseTopicEvaluationResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class ShortCourseTopicEvaluationMapper {

    public ShortCourseTopicEvaluation toShortCourseTopicEvaluation(
            ShortCourseTopicEvaluationRequest request,
            ShortCourseTopic topic,
            Student student
    ) {
        return ShortCourseTopicEvaluation.builder()
                .topic(topic)
                .student(student)
                .quizzId(UUID.fromString(request.getQuizId()))
                .grade(request.getGrade())
                .build();
    }

    public ShortCourseTopicEvaluationResponse fromShortCourseTopicEvaluation(ShortCourseTopicEvaluation evaluation) {
        return ShortCourseTopicEvaluationResponse.builder()
                .id(evaluation.getId() != null ? evaluation.getId().toString() : null)
                .topicId(evaluation.getTopic() != null ? evaluation.getTopic().getId().toString() : null)
                .studentId(evaluation.getStudent() != null ? evaluation.getStudent().getId().toString() : null)
                .quizId(evaluation.getQuizzId() != null ? evaluation.getQuizzId().toString() : null)
                .grade(evaluation.getGrade())
                .build();
    }
}