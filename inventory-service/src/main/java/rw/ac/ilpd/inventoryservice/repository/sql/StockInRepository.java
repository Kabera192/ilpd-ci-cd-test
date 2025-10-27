package rw.ac.ilpd.inventoryservice.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.ilpd.inventoryservice.model.sql.StockIn;

import java.util.UUID;

public interface StockInRepository extends JpaRepository<StockIn, UUID> {
}
