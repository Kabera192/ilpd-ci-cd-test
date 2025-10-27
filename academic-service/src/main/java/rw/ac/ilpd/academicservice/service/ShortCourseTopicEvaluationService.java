package rw.ac.ilpd.academicservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.academicservice.exception.ConflictException;
import rw.ac.ilpd.academicservice.mapper.ShortCourseTopicEvaluationMapper;
import rw.ac.ilpd.academicservice.model.sql.ShortCourseTopic;
import rw.ac.ilpd.academicservice.model.sql.ShortCourseTopicEvaluation;
import rw.ac.ilpd.academicservice.model.sql.Student;
import rw.ac.ilpd.academicservice.repository.sql.ShortCourseTopicEvaluationRepository;
import rw.ac.ilpd.academicservice.repository.sql.ShortCourseTopicRepository;
import rw.ac.ilpd.academicservice.repository.sql.StudentRepository;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.shortcoursetopicevaluation.ShortCourseTopicEvaluationRequest;
import rw.ac.ilpd.sharedlibrary.dto.shortcoursetopicevaluation.ShortCourseTopicEvaluationResponse;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShortCourseTopicEvaluationService {
    private final ShortCourseTopicEvaluationRepository evaluationRepository;
    private final ShortCourseTopicRepository topicRepository;
    private final StudentRepository studentRepository;
    private final ShortCourseTopicEvaluationMapper evaluationMapper;

    public PagedResponse<ShortCourseTopicEvaluationResponse> getAll(int page, int size, String sortBy, String order) {
        Pageable pageable;
        if (order.equals("desc"))
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        else
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());

        Page<ShortCourseTopicEvaluation> evaluationPage = evaluationRepository.findAll(pageable);
        return new PagedResponse<>(
                evaluationPage.getContent().stream().map(evaluationMapper::fromShortCourseTopicEvaluation).toList(),
                evaluationPage.getNumber(),
                evaluationPage.getSize(),
                evaluationPage.getTotalElements(),
                evaluationPage.getTotalPages(),
                evaluationPage.isLast()
        );
    }

    public ShortCourseTopicEvaluationResponse get(UUID id) {
        return evaluationMapper.fromShortCourseTopicEvaluation(
                getEntity(id).orElseThrow(() -> new EntityNotFoundException("Short course topic evaluation not found"))
        );
    }

    public ShortCourseTopicEvaluationResponse create(ShortCourseTopicEvaluationRequest request) {
        // Fetch related entities
        ShortCourseTopic topic = topicRepository.findById(UUID.fromString(request.getTopicId()))
                .orElseThrow(() -> new EntityNotFoundException("Topic not found"));
        Student student = studentRepository.findById(UUID.fromString(request.getStudentId()))
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        // Check if evaluation already exists for this student and topic
        if (evaluationRepository.existsByStudentAndTopic(student, topic)) {
            throw new ConflictException("Evaluation already exists for this student and topic");
        }

        ShortCourseTopicEvaluation evaluation = evaluationMapper.toShortCourseTopicEvaluation(request, topic, student);
        return evaluationMapper.fromShortCourseTopicEvaluation(evaluationRepository.save(evaluation));
    }

    public ShortCourseTopicEvaluationResponse edit(UUID id, ShortCourseTopicEvaluationRequest request) {
        ShortCourseTopicEvaluation existing = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("Short course topic evaluation not found"));

        // Fetch related entities
        ShortCourseTopic topic = topicRepository.findById(UUID.fromString(request.getTopicId()))
                .orElseThrow(() -> new EntityNotFoundException("Topic not found"));
        Student student = studentRepository.findById(UUID.fromString(request.getStudentId()))
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        existing.setTopic(topic);
        existing.setStudent(student);
        existing.setQuizzId(UUID.fromString(request.getQuizId()));
        existing.setGrade(request.getGrade());

        return evaluationMapper.fromShortCourseTopicEvaluation(evaluationRepository.save(existing));
    }

    public ShortCourseTopicEvaluationResponse patch(UUID id, Map<String, Object> updates) {
        ShortCourseTopicEvaluation existing = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("Short course topic evaluation not found"));

        // Patch topic
        if (updates.containsKey("topicId")) {
            Object value = updates.get("topicId");
            if (value instanceof String topicId) {
                ShortCourseTopic topic = topicRepository.findById(UUID.fromString(topicId))
                        .orElseThrow(() -> new EntityNotFoundException("Topic not found"));
                existing.setTopic(topic);
            }
        }

        // Patch student
        if (updates.containsKey("studentId")) {
            Object value = updates.get("studentId");
            if (value instanceof String studentId) {
                Student student = studentRepository.findById(UUID.fromString(studentId))
                        .orElseThrow(() -> new EntityNotFoundException("Student not found"));
                existing.setStudent(student);
            }
        }

        // Patch quizId
        if (updates.containsKey("quizId")) {
            Object value = updates.get("quizId");
            if (value instanceof String quizId) {
                existing.setQuizzId(UUID.fromString(quizId));
            }
        }

        // Patch grade
        if (updates.containsKey("grade")) {
            Object value = updates.get("grade");
            if (value instanceof BigDecimal grade) {
                existing.setGrade(grade);
            }
        }

        return evaluationMapper.fromShortCourseTopicEvaluation(evaluationRepository.save(existing));
    }

    public Boolean delete(UUID id) {
        evaluationRepository.delete(getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("Short course topic evaluation not found")));
        return true;
    }

    public Optional<ShortCourseTopicEvaluation> getEntity(UUID id) {
        return evaluationRepository.findById(id);
    }
}