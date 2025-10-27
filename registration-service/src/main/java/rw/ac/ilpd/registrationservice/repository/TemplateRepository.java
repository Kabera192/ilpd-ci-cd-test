package rw.ac.ilpd.registrationservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.registrationservice.model.nosql.document.Template;

import java.util.List;

@Repository
public interface TemplateRepository extends MongoRepository<Template, String> {

    List<Template> findByIsActive(Boolean isActive);

    List<Template> findByNameContainingIgnoreCase(String name);
}
