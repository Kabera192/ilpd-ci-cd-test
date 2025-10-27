package rw.ac.ilpd.academicservice.repository.sql;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.ilpd.academicservice.model.sql.PossibleSessionCouple;

import java.util.Optional;
import java.util.UUID;

public interface PossibleSessionCoupleRepository extends JpaRepository<PossibleSessionCouple, UUID>
{
    Optional<PossibleSessionCouple> findByIdAndDeletedStatusFalse(UUID id);

    Page<PossibleSessionCouple> findByDeletedStatusFalse(Pageable pageable);
}
