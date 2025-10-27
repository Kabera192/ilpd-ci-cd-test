package rw.ac.ilpd.notificationservice.repository.nosql;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import rw.ac.ilpd.notificationservice.model.nosql.document.DocumentType;
import java.util.List;
import java.util.Optional;

public interface DocumentTypeRepository extends MongoRepository<DocumentType, String> {
    Page<DocumentType> findByNameContainingIgnoreCaseOrPathContainingIgnoreCase(String search, String search1, Pageable pageable);
    List<DocumentType> findAllByIdIn(List<String> id);
    List<DocumentType>findAllByNameContainingIgnoreCaseAndPathContainingIgnoreCase(String search, String search1);
    Optional<DocumentType> findFirstByPath(@NotBlank(message = "Object path is required") String objectPath);
}
