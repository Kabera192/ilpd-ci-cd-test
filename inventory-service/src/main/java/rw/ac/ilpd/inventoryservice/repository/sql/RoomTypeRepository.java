package rw.ac.ilpd.inventoryservice.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.ilpd.inventoryservice.model.sql.RoomType;

import java.util.UUID;

public interface RoomTypeRepository extends JpaRepository<RoomType, UUID> {
    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, UUID id);
}
