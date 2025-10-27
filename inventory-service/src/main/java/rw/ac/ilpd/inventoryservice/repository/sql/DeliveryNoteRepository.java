package rw.ac.ilpd.inventoryservice.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.ilpd.inventoryservice.model.sql.DeliveryNote;

import java.util.UUID;

public interface DeliveryNoteRepository extends JpaRepository<DeliveryNote, UUID> {
}
