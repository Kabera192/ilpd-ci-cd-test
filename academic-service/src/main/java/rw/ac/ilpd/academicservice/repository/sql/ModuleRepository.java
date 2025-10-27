package rw.ac.ilpd.academicservice.repository.sql;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.academicservice.model.sql.Module;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface ModuleRepository extends JpaRepository<Module, UUID> {
    Page<Module> findByNameContainingIgnoreCaseOrCodeContainingIgnoreCase(String search, String search1, Pageable pageable);
    Page<Module> findByNameContainingIgnoreCaseOrCodeContainingIgnoreCaseAndDeleteStatus(String search, String search1,boolean deleteStatus, Pageable pageable);
    Page<Module> findByDeleteStatus(boolean deleteStatus, Pageable pageable);

    Optional<Module> findByNameIgnoreCaseAndCodeIgnoreCaseAndDeleteStatus(@NotBlank(message = "Name cannot be blank") String name, @NotBlank(message = "Code cannot be blank") String code, Boolean deleteStatus);

    Optional<Module> findByCodeIgnoreCaseAndDeleteStatus(@NotBlank(message = "Name cannot be blank") String name,  Boolean deleteStatus);

    Optional<Module> findByIdAndDeleteStatus(UUID id, Boolean deleteStatus);

    List<Module> findAllByIdAndCodeNotContainingIgnoreCase(UUID uuid, String courseCode);
}
