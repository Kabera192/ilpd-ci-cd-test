package rw.ac.ilpd.notificationservice.repository.nosql;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import rw.ac.ilpd.notificationservice.model.nosql.document.Template;

import java.util.Optional;

public interface TemplateRepository extends MongoRepository<Template, String>
{
    Page<Template> findByIsActiveTrue(Pageable pageable);
    Optional<Template> findByIdAndIsActiveTrue(String id);
}
