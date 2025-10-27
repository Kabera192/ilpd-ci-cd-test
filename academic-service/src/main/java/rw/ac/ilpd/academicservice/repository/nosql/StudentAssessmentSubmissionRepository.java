package rw.ac.ilpd.academicservice.repository.nosql;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.academicservice.model.nosql.document.StudentAssessmentSubmission;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentAssessmentSubmissionRepository extends MongoRepository<StudentAssessmentSubmission, String>
{
    Page<StudentAssessmentSubmission> findBySubjectId(String subjectId, Pageable pageable);

    Page<StudentAssessmentSubmission> findByModuleComponentAssessmentIdAndIsCurrentTrue(String componentAssessmentId, Pageable pageable);

    StudentAssessmentSubmission findBySubjectIdAndModuleComponentAssessmentId(String subjectId, String assessmentId);

    boolean existsByModuleComponentAssessmentIdAndSubjectIdAndIsCurrentTrue(String moduleComponentAssessmentId, String subjectId);

    StudentAssessmentSubmission findBySubjectIdAndModuleComponentAssessmentIdAndIsCurrentTrue(String subjectId, String moduleComponentAssessmentId);

    Optional<StudentAssessmentSubmission> findByIdAndIsCurrentTrue(String submissionId);

    Page<StudentAssessmentSubmission> findByModuleComponentAssessmentIdAndIsCurrentTrueAndGradeIsNull(String componentAssessmentId, Pageable pageable);

    long countByModuleComponentAssessmentIdAndIsCurrentTrue(String componentAssessmentId);

    long countByModuleComponentAssessmentIdAndIsCurrentTrueAndGradeIsNotNull(String componentAssessmentId);

    Double getAverageGradeByModuleComponentAssessmentId(String componentAssessmentId);
}