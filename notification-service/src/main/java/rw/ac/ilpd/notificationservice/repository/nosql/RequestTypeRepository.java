package rw.ac.ilpd.notificationservice.repository.nosql;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import rw.ac.ilpd.notificationservice.model.nosql.document.RequestType;

import java.util.Optional;

public interface RequestTypeRepository extends MongoRepository<RequestType, String>
{
    Optional<RequestType> findByIdAndDeletedStatusFalse(String id);

    Page<RequestType> findByDeletedStatusFalse(Pageable pageable);
}
