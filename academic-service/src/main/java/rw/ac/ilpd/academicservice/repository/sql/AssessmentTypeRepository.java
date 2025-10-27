package rw.ac.ilpd.academicservice.repository.sql;

import com.mongodb.RequestContext;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.academicservice.model.sql.AssessmentGroup;
import rw.ac.ilpd.academicservice.model.sql.AssessmentType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface AssessmentTypeRepository extends JpaRepository<AssessmentType, UUID> {

    @Query("SELECT at FROM AssessmentType at WHERE at.assessmentGroup = :assessmentGroup AND LOWER(at.title) LIKE LOWER(CONCAT('%', :title, '%')) AND at.isDeleted = :isDeleted ORDER BY at.createdAt DESC")
    Optional<AssessmentType> findByAssessmentGroupAndTitleContainingIgnoreCaseAndIsDeletedOrderByCreatedAtDesc(
            @Param("assessmentGroup") AssessmentGroup assessmentGroup,
            @Param("title") String title,
            @Param("isDeleted") boolean isDeleted
    );

    boolean existsByIdNotAndAssessmentGroupAndTitle(UUID id,AssessmentGroup assessmentGroup, @NotBlank(message = "Title cannot be blank") String title);
    
    List<AssessmentType> findByAssessmentGroupIdAndIsDeletedOrderByCreatedAtDesc(UUID uuid, boolean isDeleted);

    List<AssessmentType> findByAssessmentGroupId(UUID uuid);

    List<AssessmentType> findByIsDeletedFalse();
}
