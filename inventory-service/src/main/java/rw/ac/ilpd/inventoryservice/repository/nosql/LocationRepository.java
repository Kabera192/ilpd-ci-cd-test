package rw.ac.ilpd.inventoryservice.repository.nosql;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.inventoryservice.model.nosql.document.Location;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends MongoRepository<Location,String> {
    Page<Location> findByDeleteStatus(boolean b, Pageable pageable);

    boolean existsByNameAndDeleteStatus(String name, boolean b);

    Optional<Location> findByIdAndDeleteStatus(String id, boolean b);
    boolean existsByNameContainingIgnoreCaseAndTypeId(String lcName, String name);
    List<Location> findByTypeId(String locationTypeId);

    List<Location> findByTypeIdAndNameContainingIgnoreCase(String id,String name);
}
