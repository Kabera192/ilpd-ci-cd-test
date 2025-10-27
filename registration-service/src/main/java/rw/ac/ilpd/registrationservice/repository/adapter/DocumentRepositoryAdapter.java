package rw.ac.ilpd.registrationservice.repository.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.registrationservice.client.NotificationDocumentClient;
import rw.ac.ilpd.registrationservice.model.nosql.document.DocumentEntity;
import rw.ac.ilpd.registrationservice.repository.DocumentRepository;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Adapter for DocumentRepository that delegates to NotificationDocumentClient.
 * Note: Some methods have limited functionality due to notification service limitations.
 */
@Repository
public class DocumentRepositoryAdapter implements DocumentRepository {

    private static final Logger log = LoggerFactory.getLogger(DocumentRepositoryAdapter.class);
    private final NotificationDocumentClient documentClient;

    public DocumentRepositoryAdapter(NotificationDocumentClient documentClient) {
        this.documentClient = documentClient;
    }

    @Override
    public List<DocumentEntity> findByTypeId(String typeId) {
        log.debug("Finding documents by typeId: {}", typeId);
        // Get all documents and filter by typeId
        ResponseEntity<List<DocumentResponse>> response = documentClient.getAllDocuments();
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody().stream().filter(doc -> typeId.equals(doc.getTypeId()))
                    .map(this::mapToDocumentEntity).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public List<DocumentEntity> findByUploadedBy(String uploadedBy) {
        log.info("Finding documents by uploadedBy: {} (using getAllDocuments and filtering)", uploadedBy);
        // Get all documents and filter by uploadedBy
        // This is a workaround since notification service doesn't support filtering by uploadedBy
        ResponseEntity<List<DocumentResponse>> response = documentClient.getAllDocuments();
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            // Since uploadedBy is not available in DocumentResponse, we can't filter by it
            // Return all documents with a warning
            log.warn("uploadedBy field is not available in DocumentResponse, returning all documents");
            return response.getBody().stream()
                    .map(this::mapToDocumentEntity)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<DocumentEntity> findByFileHash(String fileHash) {
        log.info("Finding document by fileHash: {} (not supported, using getAllDocuments)", fileHash);
        // Get all documents and try to match by fileHash
        // This is inefficient but necessary since notification service doesn't support filtering by fileHash
        ResponseEntity<List<DocumentResponse>> response = documentClient.getAllDocuments();
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            // Since fileHash is not available in DocumentResponse, we can't filter by it
            log.warn("fileHash field is not available in DocumentResponse, cannot find by fileHash");
        }
        return Optional.empty();
    }

    @Override
    public List<DocumentEntity> findByBucketNameAndObjectPathStartingWith(String bucketName, String objectPath) {
        log.info("Finding documents by bucketName: {} and objectPath: {} (using getAllDocuments and filtering)", 
                bucketName, objectPath);
        // Get all documents and filter by bucketName and objectPath
        // This is a workaround since notification service doesn't support this filtering
        ResponseEntity<List<DocumentResponse>> response = documentClient.getAllDocuments();
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            // Since bucketName and objectPath are not available in DocumentResponse, we can't filter by them
            log.warn("bucketName and objectPath fields are not available in DocumentResponse, returning all documents");
            return response.getBody().stream()
                    .map(this::mapToDocumentEntity)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public void deleteByFilePath(String filePath) {
        log.info("Deleting document by filePath: {} (not supported)", filePath);
        // Since filePath is not available in DocumentResponse, we can't delete by it
        log.warn("deleteByFilePath is not supported by the notification service - operation ignored");
        // No-op as notification service doesn't support this operation
    }

    @Override
    public <S extends DocumentEntity> S save(S entity) {
        log.info("Attempting to save document entity: {}", entity.getId());
        
        if (entity.getId() != null) {
            // If the entity has an ID, check if it exists in the notification service
            try {
                ResponseEntity<DocumentResponse> response = documentClient.findDocument(entity.getId());
                if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                    log.info("Document exists in notification service, but updates are not supported without the file");
                    // We can't update the document without the file, so just return the entity
                    return entity;
                }
            } catch (Exception e) {
                log.warn("Error checking if document exists: {}", e.getMessage());
            }
        }
        
        log.warn("Creating/updating documents requires the actual file, which is not available in this adapter");
        log.warn("To upload documents, use NotificationDocumentService.uploadSingleObject or uploadMultipleObjects");
        
        // Set a timestamp if it's not already set
        if (entity.getCreatedAt() == null) {
            entity.setCreatedAt(LocalDateTime.now());
        }
        
        return entity;
    }

    @Override
    public <S extends DocumentEntity> List<S> saveAll(Iterable<S> entities) {
        List<S> result = new ArrayList<>();
        for (S entity : entities) {
            result.add(save(entity));
        }
        return result;
    }

    @Override
    public Optional<DocumentEntity> findById(String id) {
        log.debug("Finding document by id: {}", id);
        try {
            ResponseEntity<DocumentResponse> response = documentClient.findDocument(id);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return Optional.of(mapToDocumentEntity(response.getBody()));
            }
        } catch (Exception e) {
            log.error("Error finding document by id: {}", id, e);
        }
        return Optional.empty();
    }

    @Override
    public boolean existsById(String id) {
        return findById(id).isPresent();
    }

    @Override
    public List<DocumentEntity> findAll() {
        log.debug("Finding all documents");
        try {
            ResponseEntity<List<DocumentResponse>> response = documentClient.getAllDocuments();
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody().stream().map(this::mapToDocumentEntity).collect(Collectors.toList());
            }
        } catch (Exception e) {
            log.error("Error finding all documents", e);
        }
        return Collections.emptyList();
    }

    @Override
    public List<DocumentEntity> findAll(Sort sort) {
        log.debug("Finding all documents with sort (sort ignored): {}", sort);
        return findAll(); // Sort ignored as notification service doesn't support it
    }

    @Override
    public Page<DocumentEntity> findAll(Pageable pageable) {
        log.debug("Finding all documents with pageable: {}", pageable);
        List<DocumentEntity> allDocuments = findAll();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), allDocuments.size());
        List<DocumentEntity> pageContent = start < allDocuments.size() ? allDocuments.subList(start,
                end) : Collections.emptyList();
        return new PageImpl<>(pageContent, pageable, allDocuments.size());
    }

    @Override
    public List<DocumentEntity> findAllById(Iterable<String> ids) {
        log.debug("Finding all documents by ids");
        try {
            List<String> idList = new ArrayList<>();
            ids.forEach(idList::add);

            if (idList.isEmpty()) {
                return Collections.emptyList();
            }

            ResponseEntity<List<DocumentResponse>> response = documentClient.findListDocumentDetailByDocumentIds(
                    new HashSet<>(idList));

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody().stream().map(this::mapToDocumentEntity).collect(Collectors.toList());
            }
        } catch (Exception e) {
            log.error("Error finding documents by ids", e);
        }
        return Collections.emptyList();
    }

    @Override
    public long count() {
        log.debug("Counting documents");
        try {
            ResponseEntity<List<DocumentResponse>> response = documentClient.getAllDocuments();
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody().size();
            }
        } catch (Exception e) {
            log.error("Error counting documents", e);
        }
        return 0;
    }

    @Override
    public void deleteById(String id) {
        log.debug("Deleting document by id: {}", id);
        try {
            documentClient.deleteDocument(id);
        } catch (Exception e) {
            log.error("Error deleting document by id: {}", id, e);
            throw new RuntimeException("Failed to delete document: " + id, e);
        }
    }

    @Override
    public void delete(DocumentEntity entity) {
        if (entity != null && entity.getId() != null) {
            deleteById(entity.getId());
        }
    }

    @Override
    public void deleteAllById(Iterable<? extends String> ids) {
        for (String id : ids) {
            try {
                deleteById(id);
            } catch (Exception e) {
                log.error("Error deleting document by id: {}", id, e);
            }
        }
    }

    @Override
    public void deleteAll(Iterable<? extends DocumentEntity> entities) {
        for (DocumentEntity entity : entities) {
            delete(entity);
        }
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Deleting all documents is not supported for safety reasons");
    }

    @Override
    public <S extends DocumentEntity> S insert(S entity) {
        return save(entity);
    }

    @Override
    public <S extends DocumentEntity> List<S> insert(Iterable<S> entities) {
        return saveAll(entities);
    }

    // Example-based operations with graceful handling
    @Override
    public <S extends DocumentEntity> Optional<S> findOne(Example<S> example) {
        log.info("findOne with Example called but not supported by notification service");
        log.warn("Example-based queries are not supported by the notification service");
        log.warn("Consider using findById, findByTypeId, or getAllDocuments instead");
        return Optional.empty();
    }

    @Override
    public <S extends DocumentEntity> List<S> findAll(Example<S> example) {
        log.info("findAll with Example called but not supported by notification service");
        log.warn("Example-based queries are not supported by the notification service");
        log.warn("Consider using findAll() or getAllDocuments() and filtering the results in memory");
        return Collections.emptyList();
    }

    @Override
    public <S extends DocumentEntity> List<S> findAll(Example<S> example, Sort sort) {
        log.info("findAll with Example and Sort called but not supported by notification service");
        log.warn("Example-based queries and sorting are not supported by the notification service");
        log.warn("Consider using findAll() or getAllDocuments() and sorting the results in memory");
        return Collections.emptyList();
    }

    @Override
    public <S extends DocumentEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
        log.info("findAll with Example and Pageable called but not supported by notification service");
        log.warn("Example-based queries and pagination are not supported by the notification service");
        log.warn("Consider using findAll(Pageable) and filtering the results in memory");
        return new PageImpl<>(Collections.emptyList(), pageable, 0);
    }

    @Override
    public <S extends DocumentEntity> long count(Example<S> example) {
        log.info("count with Example called but not supported by notification service");
        log.warn("Example-based queries are not supported by the notification service");
        log.warn("Consider using count() or getAllDocuments().size() instead");
        return 0;
    }

    @Override
    public <S extends DocumentEntity> boolean exists(Example<S> example) {
        log.info("exists with Example called but not supported by notification service");
        log.warn("Example-based queries are not supported by the notification service");
        log.warn("Consider using existsById or checking if findAll() contains a matching entity");
        return false;
    }

    @Override
    public <S extends DocumentEntity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>,
            R> queryFunction) {
        log.info("findBy with Example and Function called but not supported by notification service");
        log.warn("Example-based queries are not supported by the notification service");
        log.warn("Consider using other query methods and applying the function to the results");
        return null;
    }

    /**
     * Maps DocumentResponse from notification service to DocumentEntity.
     * Sets default values for fields not available in the notification service.
     */
    private DocumentEntity mapToDocumentEntity(DocumentResponse response) {
        DocumentEntity entity = new DocumentEntity();
        entity.setId(response.getId());
        entity.setUrl(response.getUrl());
        entity.setTypeId(response.getTypeId());

        // Parse createdAt from string if available
        if (response.getCreatedAt() != null) {
            try {
                entity.setCreatedAt(LocalDateTime.parse(response.getCreatedAt()));
            } catch (Exception e) {
                log.warn("Failed to parse createdAt: {} - using current time", response.getCreatedAt());
                entity.setCreatedAt(LocalDateTime.now());
            }
        } else {
            entity.setCreatedAt(LocalDateTime.now());
        }

        // Set default values for fields not available in DocumentResponse
        entity.setOriginalName("Unknown");
        entity.setFileName("Unknown");
        entity.setContentType("application/octet-stream");
        entity.setFileSize(0);
        entity.setFilePath("");
        entity.setFileHash("");
        entity.setBucketName("");
        entity.setObjectPath("");
        entity.setUploadedBy("");

        return entity;
    }
}