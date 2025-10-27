package rw.ac.ilpd.academicservice.repository.sql;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.ilpd.academicservice.model.sql.ProgramType;

import java.util.Optional;
import java.util.UUID;

public interface ProgramTypeRepository extends JpaRepository<ProgramType, UUID>
{
    Page<ProgramType> findByDeleteStatus(Boolean deleteStatus, Pageable pageable);
    Optional<ProgramType> findByNameAndDeleteStatus(String name, Boolean deleteStatus);

    boolean existsByNameContainingIgnoreCase(@NotBlank(message = "Name cannot be null or blank") @Size(max = 50, min = 2,
            message = "The program type name is too long or too short. " +
            "It should be between 2 and 50 characters") String name);
}
