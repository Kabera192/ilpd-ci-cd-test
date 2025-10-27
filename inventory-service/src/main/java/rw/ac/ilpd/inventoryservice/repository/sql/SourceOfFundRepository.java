package rw.ac.ilpd.inventoryservice.repository.sql;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.ilpd.inventoryservice.model.sql.SourceOfFund;

import java.util.Optional;
import java.util.UUID;

public interface SourceOfFundRepository extends JpaRepository<SourceOfFund, UUID> {
    Page<SourceOfFund> findAllByIsDeletedFalse(Pageable pageable);

    boolean existsByNameAndIsDeletedFalse(String name);

    Optional<SourceOfFund> findByIdAndIsDeletedFalse(UUID id);
}
