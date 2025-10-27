package rw.ac.ilpd.academicservice.repository.sql;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.ilpd.academicservice.model.sql.Program;

import java.util.Optional;
import java.util.UUID;

public interface ProgramRepository extends JpaRepository<Program, UUID>
{
    Page<Program> findByIsDeleted(Boolean isDeleted, Pageable pageable);

    Optional<Program> findByIdAndIsDeleted(UUID id, boolean deleted);
    Optional<Program> findByNameOrCodeOrAcronymAndIsDeleted(String programName, String code,
                                                       String acronym, boolean isDeleted);

    boolean existsByIdNotAndCode(UUID id, @NotBlank(message = "Code cannot be null or blank") @Size(min = 2, max = 50, message = "Input provided is too long. " +
            "It should be between 2 and 50 characters.") String code);
}
