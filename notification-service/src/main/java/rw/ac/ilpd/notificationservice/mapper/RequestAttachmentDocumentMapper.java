package rw.ac.ilpd.notificationservice.mapper;

import org.mapstruct.Mapper;
import rw.ac.ilpd.notificationservice.model.nosql.embedding.RequestAttachmentDocument;
import rw.ac.ilpd.sharedlibrary.dto.request.RequestAttachmentDocumentRequest;
import rw.ac.ilpd.sharedlibrary.dto.request.RequestAttachmentDocumentResponse;

@Mapper(componentModel = "spring")
public interface RequestAttachmentDocumentMapper
{
    RequestAttachmentDocument toRequestAttachmentDocument(
            RequestAttachmentDocumentRequest requestAttachmentDocumentRequest);

    RequestAttachmentDocumentResponse fromRequestAttachmentDocument(
            RequestAttachmentDocument requestAttachmentDocument);
}
