package rw.ac.ilpd.registrationservice.repository.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.registrationservice.client.NotificationDocumentTypeClient;
import rw.ac.ilpd.registrationservice.repository.DocumentTypeRepository;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentTypeRequest;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentTypeResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageImpl;

/**
 * Adapter for DocumentTypeRepository that delegates to NotificationDocumentTypeClient.
 * This adapter implements the MongoRepository interface to maintain compatibility with existing code,
 * but delegates all operations to the notification service's document type API.
 */
@Repository
public class DocumentTypeRepositoryAdapter implements DocumentTypeRepository {

    private static final Logger log = LoggerFactory.getLogger(DocumentTypeRepositoryAdapter.class);
    private final NotificationDocumentTypeClient documentTypeClient;

    public DocumentTypeRepositoryAdapter(NotificationDocumentTypeClient documentTypeClient) {
        this.documentTypeClient = documentTypeClient;
    }

    @Override
    public Optional<DocumentTypeResponse> findByName(String name) {
        log.debug("Finding document type by name: {}", name);
        // Get all document types and filter by name
        PagedResponse<DocumentTypeResponse> response = documentTypeClient.getPagedDocumentType(0, 100, "name", name);
        if (response != null && response.getContent() != null) {
            return response.getContent().stream()
                    .filter(dt -> dt.getName().equals(name))
                    .findFirst();
        }
        return Optional.empty();
    }

    @Override
    public <S extends DocumentTypeResponse> S save(S entity) {
        log.debug("Saving document type: {}", entity);
        DocumentTypeRequest request = new DocumentTypeRequest();
        request.setName(entity.getName());
        request.setPath(entity.getPath());

        if (entity.getId() == null) {
            // Create new document type
            ResponseEntity<DocumentTypeResponse> response = documentTypeClient.createDocumentType(request);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                entity.setId(response.getBody().getId());
                return entity;
            }
        } else {
            // Update existing document type
            ResponseEntity<DocumentTypeResponse> response = documentTypeClient.updateDocumentType(entity.getId(), request);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return entity;
            }
        }
        throw new RuntimeException("Failed to save document type");
    }

    @Override
    public Optional<DocumentTypeResponse> findById(String id) {
        log.debug("Finding document type by id: {}", id);
        ResponseEntity<DocumentTypeResponse> response = documentTypeClient.findDocumentType(id);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return Optional.of(response.getBody());
        }
        return Optional.empty();
    }

    @Override
    public boolean existsById(String id) {
        log.debug("Checking if document type exists by id: {}", id);
        return findById(id).isPresent();
    }

    @Override
    public List<DocumentTypeResponse> findAll() {
        log.debug("Finding all document types");
        PagedResponse<DocumentTypeResponse> response = documentTypeClient.getPagedDocumentType(0, 1000, "name", "");
        if (response != null && response.getContent() != null) {
            return response.getContent();
        }
        return new ArrayList<>();
    }

    @Override
    public List<DocumentTypeResponse> findAll(Sort sort) {
        log.debug("Finding all document types with sort: {}", sort);
        // Sort is ignored as the notification service API doesn't support it
        return findAll();
    }

    @Override
    public Page<DocumentTypeResponse> findAll(Pageable pageable) {
        log.debug("Finding all document types with pageable: {}", pageable);
        // Convert to Page
        PagedResponse<DocumentTypeResponse> response = documentTypeClient.getPagedDocumentType(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                "name", // Default sort
                ""
        );
        
        if (response != null && response.getContent() != null) {
            return new org.springframework.data.domain.PageImpl<>(
                    response.getContent(),
                    pageable,
                    response.getTotalElements()
            );
        }
        
        return Page.empty();
    }

    @Override
    public List<DocumentTypeResponse> findAllById(Iterable<String> ids) {
        log.debug("Finding document types by ids: {}", ids);
        List<DocumentTypeResponse> results = new ArrayList<>();
        for (String id : ids) {
            findById(id).ifPresent(results::add);
        }
        return results;
    }

    @Override
    public long count() {
        log.debug("Counting document types");
        PagedResponse<DocumentTypeResponse> response = documentTypeClient.getPagedDocumentType(0, 1, "name", "");
        if (response != null) {
            return response.getTotalElements();
        }
        return 0;
    }

    @Override
    public void deleteById(String id) {
        log.debug("Deleting document type by id: {}", id);
        documentTypeClient.deleteDocumentType(id);
    }

    @Override
    public void delete(DocumentTypeResponse entity) {
        log.debug("Deleting document type: {}", entity);
        if (entity.getId() != null) {
            deleteById(entity.getId());
        }
    }

    @Override
    public void deleteAllById(Iterable<? extends String> ids) {
        log.debug("Deleting document types by ids: {}", ids);
        for (String id : ids) {
            deleteById(id);
        }
    }

    @Override
    public void deleteAll(Iterable<? extends DocumentTypeResponse> entities) {
        log.debug("Deleting document types: {}", entities);
        for (DocumentTypeResponse entity : entities) {
            delete(entity);
        }
    }

    @Override
    public void deleteAll() {
        log.debug("Deleting all document types - OPERATION NOT SUPPORTED");
        // This operation is not supported as it would delete all document types in the system
        throw new UnsupportedOperationException("Deleting all document types is not supported");
    }

    @Override
    public <S extends DocumentTypeResponse> List<S> saveAll(Iterable<S> entities) {
        log.debug("Saving all document types: {}", entities);
        List<S> savedEntities = new ArrayList<>();
        for (S entity : entities) {
            savedEntities.add(save(entity));
        }
        return savedEntities;
    }

    @Override
    public <S extends DocumentTypeResponse> S insert(S entity) {
        log.debug("Inserting document type: {}", entity);
        return save(entity);
    }

    @Override
    public <S extends DocumentTypeResponse> List<S> insert(Iterable<S> entities) {
        log.debug("Inserting document types: {}", entities);
        return saveAll(entities);
    }

    @Override
    public <S extends DocumentTypeResponse> Optional<S> findOne(Example<S> example) {
        log.info("findOne with Example called but not supported by notification service");
        log.warn("Example-based queries are not supported by the notification service");
        log.warn("Consider using findById, findByName, or getPagedDocumentType instead");
        return Optional.empty();
    }

    @Override
    public <S extends DocumentTypeResponse> List<S> findAll(Example<S> example) {
        log.info("findAll with Example called but not supported by notification service");
        log.warn("Example-based queries are not supported by the notification service");
        log.warn("Consider using findAll() or getPagedDocumentType() and filtering the results in memory");
        return Collections.emptyList();
    }

    @Override
    public <S extends DocumentTypeResponse> List<S> findAll(Example<S> example, Sort sort) {
        log.info("findAll with Example and Sort called but not supported by notification service");
        log.warn("Example-based queries and sorting are not supported by the notification service");
        log.warn("Consider using findAll() or getPagedDocumentType() and sorting the results in memory");
        return Collections.emptyList();
    }

    @Override
    public <S extends DocumentTypeResponse> Page<S> findAll(Example<S> example, Pageable pageable) {
        log.info("findAll with Example and Pageable called but not supported by notification service");
        log.warn("Example-based queries and pagination are not supported by the notification service");
        log.warn("Consider using findAll(Pageable) and filtering the results in memory");
        return new PageImpl<>(Collections.emptyList(), pageable, 0);
    }

    @Override
    public <S extends DocumentTypeResponse> long count(Example<S> example) {
        log.info("count with Example called but not supported by notification service");
        log.warn("Example-based queries are not supported by the notification service");
        log.warn("Consider using count() or getPagedDocumentType().getTotalElements() instead");
        return 0;
    }

    @Override
    public <S extends DocumentTypeResponse> boolean exists(Example<S> example) {
        log.info("exists with Example called but not supported by notification service");
        log.warn("Example-based queries are not supported by the notification service");
        log.warn("Consider using existsById or checking if findAll() contains a matching entity");
        return false;
    }

    @Override
    public <S extends DocumentTypeResponse, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        log.info("findBy with Example and Function called but not supported by notification service");
        log.warn("Example-based queries are not supported by the notification service");
        log.warn("Consider using other query methods and applying the function to the results");
        return null;
    }
}