package rw.ac.ilpd.inventoryservice.repository.sql;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.ilpd.inventoryservice.model.sql.Item;
import rw.ac.ilpd.inventoryservice.model.sql.UserItemRequisition;

import java.util.Optional;
import java.util.UUID;

public interface UserItemRequisitionRepository extends JpaRepository<UserItemRequisition, UUID> {
    Page<UserItemRequisition> findAllByDeleteStatus(boolean b, Pageable pageable);

    boolean existsByUserIdAndRequestIdAndItemIdAndDeleteStatus(UUID uuid, String requestId, Item item, boolean b);

    Optional<UserItemRequisition> findByIdAndDeleteStatus(UUID id, boolean b);
}
