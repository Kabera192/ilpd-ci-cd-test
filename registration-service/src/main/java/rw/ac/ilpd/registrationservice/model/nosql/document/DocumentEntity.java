package rw.ac.ilpd.registrationservice.model.nosql.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * This is a stub version of the original DocumentEntity class that has been deprecated.
 * It maintains the same structure as the original class to ensure compatibility with existing code,
 * but the actual document storage is delegated to the notification service.
 */
@Document(collection = "reg_document")
public class DocumentEntity {
    @Id
    private String id;
    private String url;
    private String originalName;
    private String fileName;
    private String contentType;
    private long fileSize;
    private String filePath;
    private String fileHash;
    private String bucketName;
    private String objectPath;
    private String typeId;
    private String uploadedBy;
    private LocalDateTime createdAt;

    public DocumentEntity() {
    }

    public DocumentEntity(String id, String url, String originalName, String fileName,
                          String contentType, long fileSize, String filePath, String fileHash,
                          String bucketName, String objectPath, String typeId, String uploadedBy,
                          LocalDateTime createdAt) {
        this.id = id;
        this.url = url;
        this.originalName = originalName;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.filePath = filePath;
        this.fileHash = fileHash;
        this.bucketName = bucketName;
        this.objectPath = objectPath;
        this.typeId = typeId;
        this.uploadedBy = uploadedBy;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getObjectPath() {
        return objectPath;
    }

    public void setObjectPath(String objectPath) {
        this.objectPath = objectPath;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "DocumentEntity{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", originalName='" + originalName + '\'' +
                ", fileName='" + fileName + '\'' +
                ", contentType='" + contentType + '\'' +
                ", fileSize=" + fileSize +
                ", filePath='" + filePath + '\'' +
                ", fileHash='" + fileHash + '\'' +
                ", bucketName='" + bucketName + '\'' +
                ", objectPath='" + objectPath + '\'' +
                ", typeId='" + typeId + '\'' +
                ", uploadedBy='" + uploadedBy + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}