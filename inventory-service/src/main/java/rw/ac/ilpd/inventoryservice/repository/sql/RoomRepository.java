package rw.ac.ilpd.inventoryservice.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.ilpd.inventoryservice.model.sql.Room;

import java.util.UUID;

public interface RoomRepository extends JpaRepository<Room, UUID> {
    boolean existsByCode(String code);
}
