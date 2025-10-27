package rw.ac.ilpd.registrationservice.service;

import org.springframework.core.io.Resource;
import rw.ac.ilpd.sharedlibrary.dto.document.*;

import java.util.List;
import java.util.Set;

/**
 * Interface defining document service operations
 * This interface allows for multiple implementations of document services
 * (e.g., local storage, notification service, etc.)
 */
public interface IDocumentService {

    /**
     * Upload single document object
     *
     * @param request   the object storage request containing the file and metadata
     * @param uploadedBy the user who uploaded the document
     * @return the document response
     */
    DocumentResponse uploadSingleObject(ObjectStorageRequest request, String uploadedBy);

    /**
     * Upload multiple document objects
     *
     * @param request   the object list storage request containing the files and metadata
     * @param uploadedBy the user who uploaded the documents
     * @return a list of document responses
     */
    List<DocumentResponse> uploadMultipleObjects(ObjectListStorageRequest request, String uploadedBy);

    /**
     * Find document by ID
     *
     * @param id the ID of the document to find
     * @return the document response
     */
    DocumentResponse findDocument(String id);

    /**
     * Get all documents
     *
     * @return a list of all document responses
     */
    List<DocumentResponse> getAllDocuments();

    /**
     * Find documents by IDs
     *
     * @param ids the set of document IDs to find
     * @return a list of document responses
     */
    List<DocumentResponse> findListDocumentDetailByDocumentIds(Set<String> ids);

    /**
     * Delete document
     *
     * @param id the ID of the document to delete
     * @return a success message
     */
    String deleteDocument(String id);

    /**
     * Create document type
     *
     * @param request the document type request
     * @return the document type response
     */
    DocumentTypeResponse createDocumentType(DocumentTypeRequest request);

    /**
     * Get all document types
     *
     * @return a list of all document type responses
     */
    List<DocumentTypeResponse> getAllDocumentTypes();

    /**
     * Create template
     *
     * @param request   the template request
     * @param uploadedBy the user who created the template
     * @return the template response
     */
    TemplateResponse createTemplate(TemplateRequest request, String uploadedBy);

    /**
     * Get all templates
     *
     * @return a list of all template responses
     */
    List<TemplateResponse> getAllTemplates();
}