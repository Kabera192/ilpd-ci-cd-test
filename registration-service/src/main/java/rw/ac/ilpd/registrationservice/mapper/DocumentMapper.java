package rw.ac.ilpd.registrationservice.mapper;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import rw.ac.ilpd.registrationservice.model.nosql.document.Template;
import rw.ac.ilpd.sharedlibrary.dto.document.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Adapter for DocumentMapper that works with DTOs directly instead of entities.
 * This adapter maintains compatibility with existing code while eliminating the
 * dependency on DocumentEntity and DocumentType.
 */
@Component
public class DocumentMapper {

    /**
     * Converts a DocumentResponse to itself (pass-through).
     * This method exists for compatibility with code that expects a mapper.
     *
     * @param document the DocumentResponse to convert
     * @return the same DocumentResponse
     */
    public DocumentResponse toResponse(DocumentResponse document) {
        // Simply return the document as it's already a DTO
        return document;
    }

    /**
     * Converts a list of DocumentResponse objects to itself (pass-through).
     * This method exists for compatibility with code that expects a mapper.
     *
     * @param documents the list of DocumentResponse objects to convert
     * @return the same list of DocumentResponse objects
     */
    public List<DocumentResponse> toResponseList(List<DocumentResponse> documents) {
        // Simply return the documents as they're already DTOs
        return documents;
    }

    /**
     * Converts a DocumentTypeResponse to itself (pass-through).
     * This method exists for compatibility with code that expects a mapper.
     *
     * @param documentType the DocumentTypeResponse to convert
     * @return the same DocumentTypeResponse
     */
    public DocumentTypeResponse toResponse(DocumentTypeResponse documentType) {
        // Simply return the documentType as it's already a DTO
        return documentType;
    }

    /**
     * Converts a list of DocumentTypeResponse objects to itself (pass-through).
     * This method exists for compatibility with code that expects a mapper.
     *
     * @param documentTypes the list of DocumentTypeResponse objects to convert
     * @return the same list of DocumentTypeResponse objects
     */
    public List<DocumentTypeResponse> toDocumentTypeResponseList(List<DocumentTypeResponse> documentTypes) {
        // Simply return the documentTypes as they're already DTOs
        return documentTypes;
    }

    /**
     * Creates a TemplateResponse from a Template and a DocumentResponse.
     *
     * @param template the Template entity
     * @param document the DocumentResponse DTO
     * @return a TemplateResponse DTO
     */
    public TemplateResponse toResponse(Template template, DocumentResponse document) {
        if (template == null) {
            return null;
        }

        TemplateResponse response = new TemplateResponse();
        response.setId(template.getId());
        response.setName(template.getName());
        response.setDocument(document);
        response.setIsActive(template.getIsActive());

        return response;
    }

    /**
     * Creates a DocumentDetailResponse from a DocumentResponse and a DocumentTypeResponse.
     *
     * @param document the DocumentResponse DTO
     * @param documentType the DocumentTypeResponse DTO
     * @return a DocumentDetailResponse DTO
     */
    public DocumentDetailResponse toDetailResponse(DocumentResponse document, DocumentTypeResponse documentType) {
        if (document == null) {
            return null;
        }

        DocumentDetailResponse response = new DocumentDetailResponse();
        response.setDocument(document);
        response.setDocumentType(documentType);

        return response;
    }
}