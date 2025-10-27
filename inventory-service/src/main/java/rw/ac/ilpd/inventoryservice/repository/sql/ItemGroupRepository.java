package rw.ac.ilpd.inventoryservice.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.ac.ilpd.inventoryservice.model.sql.ItemGroup;

import java.util.Optional;
import java.util.UUID;

public interface ItemGroupRepository extends JpaRepository<ItemGroup, UUID> {
    boolean existsByName(String name);

    boolean existsByAcronym(String acronym);

    Optional<ItemGroup> findByNameAndIdNot(String name, UUID id);

    Optional<ItemGroup> findByAcronymAndIdNot(String acronym, UUID id);

    boolean existsByNameAndIdNot(String name, UUID id);

    boolean existsByAcronymAndIdNot(String acronym, UUID id);
}
