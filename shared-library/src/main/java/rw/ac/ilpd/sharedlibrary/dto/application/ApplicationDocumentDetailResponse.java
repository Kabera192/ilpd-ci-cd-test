package rw.ac.ilpd.sharedlibrary.dto.application;

import rw.ac.ilpd.sharedlibrary.dto.document.DocumentResponse;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentTypeResponse;

/**
 * Enhanced response for application documents with rich details
 */
public class ApplicationDocumentDetailResponse {

    private ApplicationDocumentSubmissionResponse submission;
    private DocumentResponse document;
    private DocumentTypeResponse documentType;
    private String downloadUrl;
    private String fileName;
    private long fileSize;
    private String contentType;

    public ApplicationDocumentDetailResponse() {
    }

    public ApplicationDocumentDetailResponse(ApplicationDocumentSubmissionResponse submission,
                                             DocumentResponse document,
                                             DocumentTypeResponse documentType,
                                             String downloadUrl,
                                             String fileName,
                                             long fileSize,
                                             String contentType) {
        this.submission = submission;
        this.document = document;
        this.documentType = documentType;
        this.downloadUrl = downloadUrl;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.contentType = contentType;
    }

    public ApplicationDocumentSubmissionResponse getSubmission() {
        return submission;
    }

    public void setSubmission(ApplicationDocumentSubmissionResponse submission) {
        this.submission = submission;
    }

    public DocumentResponse getDocument() {
        return document;
    }

    public void setDocument(DocumentResponse document) {
        this.document = document;
    }

    public DocumentTypeResponse getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentTypeResponse documentType) {
        this.documentType = documentType;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return "ApplicationDocumentDetailResponse{" +
                "submission=" + submission +
                ", document=" + document +
                ", documentType=" + documentType +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileSize=" + fileSize +
                ", contentType='" + contentType + '\'' +
                '}';
    }
}
