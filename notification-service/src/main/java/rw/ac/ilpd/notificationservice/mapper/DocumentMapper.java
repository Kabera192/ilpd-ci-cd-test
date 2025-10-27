package rw.ac.ilpd.notificationservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import rw.ac.ilpd.notificationservice.model.nosql.document.DocumentMIS;
import rw.ac.ilpd.notificationservice.model.nosql.document.DocumentType;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentDetailResponse;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentRequest;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentResponse;

@Mapper(componentModel = "spring")
public interface DocumentMapper {
    DocumentMIS toDocumentMIS(DocumentRequest documentRequest);
    DocumentMIS toDocumentMISUpdate(@MappingTarget DocumentMIS document, DocumentRequest documentRequest);
    DocumentResponse fromDocumentMis(DocumentMIS document);
    @Mappings({
            @Mapping(target = "document",source ="document"),
            @Mapping(target = "documentType",source = "documentType")
    })
    DocumentDetailResponse fromDocumentDetailResponse(DocumentMIS document,DocumentType documentType);
}
