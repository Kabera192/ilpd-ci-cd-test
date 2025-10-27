package rw.ac.ilpd.registrationservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentTypeResponse;

import java.util.Optional;

/**
 * Repository interface for document types.
 * This interface defines the standard CRUD operations for document types
 * as well as custom query methods.
 */
@Repository
public interface DocumentTypeRepository extends MongoRepository<DocumentTypeResponse, String> {

    /**
     * Find a document type by its name.
     *
     * @param name the name of the document type to find
     * @return an Optional containing the found DocumentTypeResponse, or empty if not found
     */
    Optional<DocumentTypeResponse> findByName(String name);
}