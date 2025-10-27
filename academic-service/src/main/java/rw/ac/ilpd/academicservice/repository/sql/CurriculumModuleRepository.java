package rw.ac.ilpd.academicservice.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rw.ac.ilpd.academicservice.model.sql.Curriculum;
import rw.ac.ilpd.academicservice.model.sql.CurriculumModule;
import rw.ac.ilpd.academicservice.model.sql.Module;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CurriculumModuleRepository extends JpaRepository<CurriculumModule, UUID> {
    boolean existsByCurriculumAndModule(Curriculum curriculum, Module module);
    boolean existsByCurriculumAndModuleOrder(Curriculum curriculum, Integer order);

    Optional<CurriculumModule> findByModuleId(UUID moduleId);

    List<CurriculumModule> findAllByCurriculum_Id(UUID uuid);

    @Query("SELECT cm.module FROM CurriculumModule cm WHERE cm.curriculum.id = :curriculumId ORDER BY cm.moduleOrder")
    List<Module> findModulesByCurriculumId(UUID curriculumId);
}
