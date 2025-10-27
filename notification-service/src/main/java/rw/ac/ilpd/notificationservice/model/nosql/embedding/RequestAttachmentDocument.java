package rw.ac.ilpd.notificationservice.model.nosql.embedding;

import rw.ac.ilpd.notificationservice.model.nosql.document.DocumentMIS;

public class RequestAttachmentDocument {
    private DocumentMIS document;

    public RequestAttachmentDocument(DocumentMIS document) {
        this.document = document;
    }

    public RequestAttachmentDocument() {
    }

    public static RequestAttachmentDocumentBuilder builder() {
        return new RequestAttachmentDocumentBuilder();
    }

    public DocumentMIS getDocument() {
        return this.document;
    }

    public void setDocument(DocumentMIS document) {
        this.document = document;
    }

    public static class RequestAttachmentDocumentBuilder {
        private DocumentMIS document;

        RequestAttachmentDocumentBuilder() {
        }

        public RequestAttachmentDocumentBuilder document(DocumentMIS document) {
            this.document = document;
            return this;
        }

        public RequestAttachmentDocument build() {
            return new RequestAttachmentDocument(this.document);
        }

        public String toString() {
            return "RequestAttachmentDocument.RequestAttachmentDocumentBuilder(document=" + this.document + ")";
        }
    }
}
