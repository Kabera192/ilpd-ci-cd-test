package rw.ac.ilpd.notificationservice.repository.nosql;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import rw.ac.ilpd.notificationservice.model.nosql.document.Request;

import java.util.Optional;
import java.util.UUID;

public interface RequestRepository extends MongoRepository<Request, String>
{
    Page<Request> findByIsDeletedFalse(Pageable pageable);
    Page<Request> findByCreatedByAndIsDeletedFalse(UUID createdBy, Pageable pageable);
    Optional<Request> findByIdAndIsDeletedFalse(String id);
    void deleteAllByCreatedBy(UUID id);
    void deleteAllByModuleId(UUID id);
}
