package rw.ac.ilpd.registrationservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import rw.ac.ilpd.registrationservice.model.nosql.document.DocumentEntity;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Document entities.
 * This interface defines the data access operations for DocumentEntity objects.
 */
@Repository
public interface DocumentRepository extends MongoRepository<DocumentEntity, String> {

    /**
     * Finds documents by their type ID.
     *
     * @param typeId the type ID to search for
     * @return a list of documents with the specified type ID
     */
    List<DocumentEntity> findByTypeId(String typeId);

    /**
     * Finds documents by the user who uploaded them.
     *
     * @param uploadedBy the username of the uploader
     * @return a list of documents uploaded by the specified user
     */
    List<DocumentEntity> findByUploadedBy(String uploadedBy);

    /**
     * Finds a document by its file hash.
     *
     * @param fileHash the hash of the file
     * @return an Optional containing the document if found, or empty if not found
     */
    Optional<DocumentEntity> findByFileHash(String fileHash);

    /**
     * Finds documents by bucket name and object path prefix.
     *
     * @param bucketName the name of the bucket
     * @param objectPath the prefix of the object path
     * @return a list of documents matching the bucket name and object path prefix
     */
    List<DocumentEntity> findByBucketNameAndObjectPathStartingWith(String bucketName, String objectPath);

    /**
     * Deletes a document by its file path.
     *
     * @param filePath the path of the file to delete
     */
    void deleteByFilePath(String filePath);
}