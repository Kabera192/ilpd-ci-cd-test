package rw.ac.ilpd.academicservice.repository.sql;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.academicservice.model.sql.CourseMaterial;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface CourseMaterialRepository extends JpaRepository<CourseMaterial, UUID> {
    @Query("select a from CourseMaterial a where a.id =:id and a.isDeleted =:isDeleted")
    Optional<CourseMaterial> findByIdAndIsDeleted(UUID id, boolean isDeleted);

    Page<CourseMaterial> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String search, String search1, Pageable pageable);

    Page<CourseMaterial> findByIsDeleted(boolean deleteStatus, Pageable pageable);

    Page<CourseMaterial> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndIsDeleted(String search, String search1, boolean deleteStatus, Pageable pageable);

    Page<CourseMaterial> findAllByIsDeleted(boolean b, Pageable pageable);

    List<CourseMaterial> findByIdInAndIsDeleted(List<UUID> generatedId, boolean isDeleted);

    List<CourseMaterial> findAllByIdIn(List<UUID> generatedIds);
}
