package rw.ac.ilpd.notificationservice.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.ilpd.notificationservice.mapper.DocumentTypeMapper;
import rw.ac.ilpd.notificationservice.model.nosql.document.DocumentType;
import rw.ac.ilpd.notificationservice.repository.nosql.DocumentTypeRepository;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentResponse;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentTypeRequest;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentTypeResponse;
import rw.ac.ilpd.sharedlibrary.dto.document.ObjectStorageRequest;
import rw.ac.ilpd.sharedlibrary.dto.minioobject.BucketDetailResponse;
import rw.ac.ilpd.sharedlibrary.dto.minioobject.BucketStatisticsResponse;
import rw.ac.ilpd.sharedlibrary.dto.minioobject.ObjectDetailResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.util.ResponseDetailWrapper;
import rw.ac.ilpd.sharedlibrary.util.TextSanitizer;

import java.util.*;

/**
 * Service class for managing DocumentType entities.
 */
@Service
@RequiredArgsConstructor
public class DocumentTypeService {

    // Repository for DocumentType persistence operations
    private final DocumentTypeRepository documentTypeRepository;

    // Mapper for converting between DTOs and entities
    private final DocumentTypeMapper documentTypeMapper;
    private final MinioService minioService;

    /**
     * Creates a new DocumentType from the provided request.
     *
     * @param documentTypeRequest DTO containing document type data
     * @return ResponseEntity with the created DocumentTypeResponse
     */
    public ResponseEntity<DocumentTypeResponse> createDocumentType(@Valid DocumentTypeRequest documentTypeRequest) {
        TextSanitizer textSanitizer = new TextSanitizer();
        String sanitizedPath=textSanitizer.sanitizePath(documentTypeRequest.getPath());
        String omitForwardSlash=sanitizedPath.startsWith("/") ? sanitizedPath.substring(1) : sanitizedPath;
        documentTypeRequest.setPath(omitForwardSlash);
        documentTypeRepository.findAllByNameContainingIgnoreCaseAndPathContainingIgnoreCase(documentTypeRequest.getName(),documentTypeRequest.getPath())
                .stream().findFirst().ifPresent(entity -> {throw new EntityExistsException("Document type already exists");});
        minioService.createBucket(documentTypeRequest.getPath());
        DocumentType documentType = documentTypeRepository.save(
            documentTypeMapper.toDocumentType(documentTypeRequest)
        );
        return ResponseEntity.ok(documentTypeMapper.fromDocumentType(documentType));
    }

    /**
     * Finds a DocumentType by its ID and returns a response.
     *
     * @param id DocumentType ID
     * @return ResponseEntity with DocumentTypeResponse if found, otherwise 404
     */
    public ResponseEntity<DocumentTypeResponse> findDocumentType(@NotBlank String id) {
        return findById(id)
            .map(docType -> ResponseEntity.ok(documentTypeMapper.fromDocumentType(docType)))
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Retrieves a DocumentType entity by its ID.
     *
     * @param id DocumentType ID
     * @return Optional containing DocumentType if found
     */
    public Optional<DocumentType> findById(String id) {
        return documentTypeRepository.findById(id);
    }
    public List<DocumentType> findById(List<String> id) {
        return documentTypeRepository.findAllByIdIn(id);
    }

    /**
     * Deletes a DocumentType by its ID.
     *
     * @param id DocumentType ID
     * @return String with actionable result
     */
    public String deleteById(String id) {

        DocumentType documentType=findById(id).orElseThrow(()->new EntityNotFoundException("Document type not found"));
        try {
            minioService.deleteBucket(documentType.getPath());
        }catch (Exception e) {
            throw new EntityNotFoundException("Document type not found");
        }
        documentTypeRepository.delete(documentType);
    return  documentType.getName()+" folder deleted successfully";
    }

    /**
     * Updates an existing DocumentType with new data.
     *
     * @param id DocumentType ID
     * @param documentTypeRequest DTO containing updated data
     * @return ResponseEntity with updated DocumentTypeResponse
     * @throws EntityNotFoundException if DocumentType not found
     */
    public ResponseEntity<DocumentTypeResponse> updateDocumentType(
        @NotBlank(message = "Document type can't be blank") String id,
        @Valid DocumentTypeRequest documentTypeRequest
    ) {
        DocumentType documentType = findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Document type not found"));

        DocumentType updatedDocumentType = documentTypeRepository.save(
            documentTypeMapper.toDocumentTypeUpdate(documentType, documentTypeRequest)
        );

        return ResponseEntity.ok(documentTypeMapper.fromDocumentType(updatedDocumentType));
    }

    public PagedResponse<DocumentTypeResponse> getPagedDocumentType(int page, int size, String sort, String search) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        List<DocumentTypeResponse> content = new ArrayList<>();
        Page<DocumentType> modulePage;

            modulePage = search.isBlank()
                    ? documentTypeRepository.findAll(pageable)
                    : documentTypeRepository.findByNameContainingIgnoreCaseOrPathContainingIgnoreCase(
                    search, search, pageable);
        // Map modules to response DTOs
        content = modulePage.getContent().stream()
                .map(documentTypeMapper::fromDocumentType)
                .toList();

        // Return paged response
        return new PagedResponse<>(
                content,
                modulePage.getNumber(),
                modulePage.getSize(),
                modulePage.getTotalElements(),
                modulePage.getTotalPages(),
                modulePage.isLast()
        );
    }
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public DocumentType createObjectPropertiesIfNotExist(@Valid ObjectStorageRequest request) {
        DocumentTypeRequest documentTypeRequest=DocumentTypeRequest.builder()
                .path(request.getObjectPath().toLowerCase())
                .name(request.getBucketName())
                .build();
      return documentTypeRepository.findFirstByPath(request.getObjectPath())
               .orElse(documentTypeRepository.save(documentTypeMapper.toDocumentType(documentTypeRequest)));
    }
    public ResponseDetailWrapper<DocumentTypeResponse, BucketDetailResponse> getBucketDetails(String id) {
       try {
           // Fetch document type entity by path or throw if not found
           DocumentType documentType = findById(id)
                   .orElseThrow(() -> new EntityNotFoundException("Document type not found"));

           // Map entity to response DTO
           DocumentTypeResponse documentTypeResponse = documentTypeMapper.fromDocumentType(documentType);
           // Get folder info from MinIO service by folder path from document type entity
           BucketDetailResponse bucketDetail = minioService.getBucketDetails(documentTypeResponse.getPath());

           ResponseDetailWrapper<DocumentTypeResponse, BucketDetailResponse>responseDetailWrapper=new ResponseDetailWrapper<>();
           responseDetailWrapper.setData(bucketDetail);
           responseDetailWrapper.setContent(documentTypeResponse);
           // Create a new HashMap and put the documentTypeResponse as key and folderInfo as value

           return responseDetailWrapper;
       }catch (Exception e) {
           return  null;
       }
    }
    public  ResponseDetailWrapper<DocumentTypeResponse,List<ObjectDetailResponse>> getAllObjectWithInBucket(String id){
        try {
        DocumentType documentType=findById(id)
                .orElseThrow(()->new EntityNotFoundException("Specified bucket found"));
        ResponseDetailWrapper<DocumentTypeResponse,List<ObjectDetailResponse>> responseDetailWrapper = new ResponseDetailWrapper<>();
            List<ObjectDetailResponse> allObjectWithInBucket = minioService.getAllObjectWithInBucket(documentType.getPath());
            responseDetailWrapper.setContent(documentTypeMapper.fromDocumentType(documentType));
            responseDetailWrapper.setData(allObjectWithInBucket);
            return  responseDetailWrapper;
        }catch (Exception e) {
           return new ResponseDetailWrapper<>();
       }
    }
    public BucketStatisticsResponse calculateBucketStats(String id){
        try {
            DocumentType documentType=findById(id).orElseThrow(() -> new EntityNotFoundException("Document type not found"));
            return minioService.calculateBucketStats(documentType.getPath());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
