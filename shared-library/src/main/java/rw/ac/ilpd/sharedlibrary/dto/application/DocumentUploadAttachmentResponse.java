package rw.ac.ilpd.sharedlibrary.dto.application;

import lombok.Getter;
import lombok.Setter;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentResponse;

@Setter
@Getter
public class DocumentUploadAttachmentResponse {
    // getters and setters
    private DocumentResponse document;
    private ApplicationDocumentSubmissionResponse attachment;

}