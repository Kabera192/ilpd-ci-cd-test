package rw.ac.ilpd.academicservice.repository.sql;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.academicservice.model.sql.StudentGradeCurriculumModule;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface StudentGradeCurriculumModuleRepository extends JpaRepository<StudentGradeCurriculumModule, UUID> {
    Optional<StudentGradeCurriculumModule> findByStudentId(UUID studentId);

    List<StudentGradeCurriculumModule> findByCurriculumModuleModuleNameContainingIgnoreCase(String search1);

    Page<StudentGradeCurriculumModule> findByCurriculumModuleModuleNameContainingIgnoreCase(String search, Pageable pageable);
}
