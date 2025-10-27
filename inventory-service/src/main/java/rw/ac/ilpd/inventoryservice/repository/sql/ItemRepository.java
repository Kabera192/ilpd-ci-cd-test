package rw.ac.ilpd.inventoryservice.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.ilpd.inventoryservice.model.sql.Item;

import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {
    boolean existsByName(String name);

    boolean existsByAcronym(String acronym);

    boolean existsByNameAndIdNot(String name, UUID id);

    boolean existsByAcronymAndIdNot(String acronym, UUID id);
}
