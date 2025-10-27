package rw.ac.ilpd.inventoryservice.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.ilpd.inventoryservice.model.sql.Donation;

import java.util.UUID;

public interface DonationRepository extends JpaRepository<Donation, UUID> {
}
