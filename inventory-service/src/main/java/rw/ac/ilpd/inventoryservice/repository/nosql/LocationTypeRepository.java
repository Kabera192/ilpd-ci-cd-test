package rw.ac.ilpd.inventoryservice.repository.nosql;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import rw.ac.ilpd.inventoryservice.model.nosql.document.LocationType;

import java.util.Optional;

public interface LocationTypeRepository extends MongoRepository<LocationType, String> {
    Page<LocationType> findByDeleteStatus(boolean b, Pageable pageable);

    boolean existsByName(String name);

    boolean existsByNameAndDeleteStatus(String name, boolean b);

    Optional<LocationType> findByIdAndDeleteStatus(String id, boolean b);

    Optional<LocationType> findFirstByName(String locationTypeName);

    Optional<LocationType> findByNameContainingIgnoreCase(String locationTypeName);
}
