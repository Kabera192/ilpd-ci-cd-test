package rw.ac.ilpd.notificationservice.repository.nosql;

import org.springframework.data.mongodb.repository.MongoRepository;
import rw.ac.ilpd.notificationservice.model.nosql.document.DocumentMIS;
import java.util.List;

public interface DocumentRepository extends MongoRepository<DocumentMIS, String> {
    List<DocumentMIS> findAllByIdIn(List<String> ids);

    List<String> id(String id);
}
