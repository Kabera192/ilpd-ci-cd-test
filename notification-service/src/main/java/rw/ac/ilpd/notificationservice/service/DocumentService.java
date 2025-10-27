package rw.ac.ilpd.notificationservice.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.ilpd.notificationservice.exception.RemoteDependencyException;
import rw.ac.ilpd.notificationservice.mapper.DocumentMapper;
import rw.ac.ilpd.notificationservice.model.nosql.document.DocumentMIS;
import rw.ac.ilpd.notificationservice.model.nosql.document.DocumentType;
import rw.ac.ilpd.notificationservice.repository.nosql.DocumentRepository;
import rw.ac.ilpd.notificationservice.util.FileDownloadHelperObj;
import rw.ac.ilpd.sharedlibrary.dto.document.*;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentService {
    @Value("${minio.client-access.end-point}")
    private  String MINIO_CLIENT_ACCESS_END_POINT;
    // Repository for Document persistence operations
    private final DocumentRepository documentRepository;

    // Mapper for converting between DTOs and entities
    private final DocumentMapper documentMapper;

    // Service for file storage operations
    private final MinioService minioService;

    // Service for DocumentType business logic
    private final DocumentTypeService documentTypeService;

    /**
     * Finds a DocumentMIS entity by its ID.
     *
     * @param id Document ID
     * @return Optional containing DocumentMIS if found
     */
    @Transactional(readOnly = true)
    public Optional<DocumentMIS> getEntity(String id) {
        return documentRepository.findById(id);
    }

    /**
     * Finds a document by its ID and returns its response.
     *
     * @param id Document ID
     * @return ResponseEntity with DocumentResponse if found, otherwise throws exception
     */
    public Optional<DocumentResponse> findDocument(@NotBlank(message = "Specify a document you're in need of") String id) {
        return documentRepository.findById(id)
                .stream().peek(dm->dm.setUrl(MINIO_CLIENT_ACCESS_END_POINT + dm.getUrl()))
                .map(documentMapper::fromDocumentMis)
                .findAny();

    }

    /**
     * Finds all documents by a list of IDs and returns their responses.
     *
     * @param ids List of Document IDs
     * @return ResponseEntity with list of DocumentResponse
     */
    public List<DocumentResponse>findDocumentWithInsByIds(Set<String> ids) {
        return  documentRepository.findAllByIdIn(
                ids.stream()
                .toList()).stream().map(documentMapper::fromDocumentMis)
                .peek(response -> response.setUrl(MINIO_CLIENT_ACCESS_END_POINT + response.getUrl()))
                .toList();
    }


    private  String savedMinioDocument(ObjectStorageRequest request) {
        // Upload the file and set its URL
        String url;
        try {
             url = minioService.uploadObject(request);
        } catch (Exception e) {
            log.error("File upload failed", e);
            throw new RemoteDependencyException("Unable to connect to file server");
        }
        return url;
    }


    public DocumentResponse uploadObject(@Valid ObjectStorageRequest request) {
        try {
            DocumentType documentType=documentTypeService.createObjectPropertiesIfNotExist(request);
           String url= minioService.uploadObject(request);
           DocumentMIS documentMIS=DocumentMIS.builder()
                   .url(url)
                   .createdAt(LocalDateTime.now())
                   .typeId(documentType.getId())
                   .build();
           DocumentMIS savedDocumentMIS=documentRepository.save(documentMIS);
           return documentMapper.fromDocumentMis(savedDocumentMIS);
        }catch (Exception e){
            log.error("File upload failed", e);
            throw new RemoteDependencyException("Unable to connect to file server");
        }
    }

    public List<DocumentResponse> uploadMultipleObject(@Valid ObjectListStorageRequest request) {
        // Find the DocumentType for the given typeId
        DocumentType documentType=documentTypeService.createObjectPropertiesIfNotExist(ObjectStorageRequest.builder()
                        .objectPath(request.getObjectPath())
                        .bucketName(request.getBucketName())
                        .build());

        //  save document each url in it particular document
        List<DocumentMIS> savedDocuments = request.getAttachedFiles().stream()
                .map(file -> DocumentMIS.builder()
                        .typeId(documentType.getId())
                        .createdAt(LocalDateTime.now())
                        .url(savedMinioDocument(ObjectStorageRequest.builder()
                                .bucketName(request.getBucketName())
                                .attachedFile(file)
                                .objectPath(request.getObjectPath())
                                .build()))
                        .build())
                .collect(Collectors.toList());
        List<DocumentMIS> documents = documentRepository.saveAll(savedDocuments);
        return documents.stream().map(documentMapper::fromDocumentMis).toList();
    }

    @Transactional(readOnly = false)
    public String deleteDocumentById(String id)  {
        log.info("Find document by id {}",id);
        DocumentMIS document=getEntity(id).orElseThrow(()->new EntityNotFoundException("Document not found"));
        try {
            log.info("Delete document from minio {}",document.getUrl());
            minioService.deleteObject(document.getUrl());
        }catch (Exception e){
            log.error("File delete failed", e);
            throw new RemoteDependencyException("Unable to connect to file server");
        }
        documentRepository.delete(document);
        return "Document deleted successful";
    }

    public List<DocumentResponse> getAll() {
        return documentRepository.findAll().stream()
                .peek(dm-> {
                    dm.setUrl(MINIO_CLIENT_ACCESS_END_POINT + dm.getUrl());
                })
                .map(documentMapper::fromDocumentMis)
                .toList();
    }
    
    /**
     * Downloads a document file by its ID.
     *
     * @param id Document ID
     * @return Resource containing the document file
     * @throws EntityNotFoundException if document not found
     * @throws RemoteDependencyException if file server is unavailable
     */
    public FileDownloadHelperObj downloadDocumentFile(String id) {
        log.info("Downloading document with id: {}", id);

        // Find the document in the database
        DocumentMIS document = getEntity(id)
                .orElseThrow(() -> new EntityNotFoundException("Document not found with id: " + id));

        try {
            // Get the document content from MinIO
            String URL=document.getUrl();
            URL=URL.substring(URL.indexOf("/mis")+5);
            log.info("Downloading document file {}",URL);
            InputStream fileStream = minioService.downloadFile(URL);
           String fileName= extractFileName(URL);
            // Return the content as a Resource
           Resource resource= new InputStreamResource(fileStream);
           return new FileDownloadHelperObj(fileName,resource);
        } catch (Exception e) {
            log.error("Failed to download document: {}", e.getMessage(), e);
            throw new RemoteDependencyException("Unable to download document from file server: " + e.getMessage());
        }
    }
    private String extractFileName(String objectPath) {
        if (objectPath == null || objectPath.isEmpty()) {
            return "unknown-file";
        }
        return objectPath.substring(objectPath.lastIndexOf("/") + 1);
    }

@Transactional(rollbackFor = Exception.class,readOnly = false)
    public DocumentResponse updateSingleObject(String id, ObjectStorageRequest request) {
//        Find if document to update is already exist
        try {
            DocumentMIS document = getEntity(id).orElseThrow(() -> new EntityNotFoundException("Document not found"));
            DocumentType documentType = documentTypeService.createObjectPropertiesIfNotExist(request);
            String url = minioService.uploadObject(request);

            DocumentMIS documentMIS = documentMapper.toDocumentMISUpdate(document, DocumentRequest.builder()
                    .file(request.getAttachedFile())
                    .typeId(documentType.getId())
                    .url(url)
                    .build());
            DocumentMIS savedDocumentMIS = documentRepository.save(documentMIS);
            //     Remove document from minio
            minioService.deleteObject(document.getUrl());
            return documentMapper.fromDocumentMis(savedDocumentMIS);
        }catch (Exception e){
            log.error("File update failed", e);
            throw new RemoteDependencyException("Unable to connect to file server");
        }
    }
}
