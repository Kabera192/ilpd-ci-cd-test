package rw.ac.ilpd.inventoryservice.repository.sql;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.ilpd.inventoryservice.model.sql.UserItemRequisitionModification;

import java.util.Optional;
import java.util.UUID;

public interface UserItemRequisitionModificationRepository extends JpaRepository<UserItemRequisitionModification, UUID> {
    Page<UserItemRequisitionModification> findAllByDeleteStatus(boolean b, Pageable pageable);

    Optional<UserItemRequisitionModification> findByIdAndDeleteStatus(UUID id, boolean b);
}
