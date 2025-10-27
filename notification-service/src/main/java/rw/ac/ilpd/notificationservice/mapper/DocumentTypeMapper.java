package rw.ac.ilpd.notificationservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import rw.ac.ilpd.notificationservice.model.nosql.document.DocumentType;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentTypeRequest;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentTypeResponse;

@Mapper(componentModel = "spring")
public interface DocumentTypeMapper {
    DocumentType toDocumentType(DocumentTypeRequest documentTypeRequest);
    DocumentTypeResponse fromDocumentType(DocumentType documentType);
    DocumentType toDocumentTypeUpdate(@MappingTarget  DocumentType documentType, DocumentTypeRequest documentTypeRequest);
}
