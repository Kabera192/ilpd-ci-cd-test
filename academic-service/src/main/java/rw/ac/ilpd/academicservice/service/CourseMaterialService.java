package rw.ac.ilpd.academicservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.academicservice.exception.RemoteDependencyException;
import rw.ac.ilpd.academicservice.integration.client.DocumentClient;
import rw.ac.ilpd.academicservice.mapper.CourseMaterialMapper;
import rw.ac.ilpd.academicservice.model.sql.CourseMaterial;
import rw.ac.ilpd.academicservice.repository.sql.CourseMaterialRepository;
import rw.ac.ilpd.sharedlibrary.dto.coursematerial.CourseMaterialRequest;
import rw.ac.ilpd.sharedlibrary.dto.coursematerial.CourseMaterialResponse;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentResponse;
import rw.ac.ilpd.sharedlibrary.dto.document.ObjectStorageRequest;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.util.ResponseDetailWrapper;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Service class for managing course materials and their associated documents.
 * Handles creation, update, retrieval, and pagination of course materials.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CourseMaterialService {
    // Repository for accessing CourseMaterial entities in the database
    private final CourseMaterialRepository courseMaterialRepository;
    // Mapper for converting between CourseMaterial entities and DTOs
    private final CourseMaterialMapper courseMaterialMapper;
    // Client for interacting with the document service
    private final DocumentClient documentClient;
    /**
     * Creates a new course material and saves its associated document.
     * @param request The request containing course material and document data.
     * @return ResponseEntity containing the saved CourseMaterialResponse.
     * @throws RemoteDependencyException if the document cannot be saved.
     */
    @Transactional(readOnly = false)
    public CourseMaterialResponse createCourseMaterial(CourseMaterialRequest request) {
        CourseMaterial courseMaterial = courseMaterialMapper.toCourseMaterial(request);
        if(request.getAttachment()!=null) {
            ObjectStorageRequest objectStorageRequest = ObjectStorageRequest.builder()
                    .bucketName(request.getBucketName())
                    .objectPath(request.getObjectPath())
                    .attachedFile(request.getAttachment())
                    .build();
            ResponseEntity<DocumentResponse> saveDocumentResponse = documentClient.uploadSingleObject(objectStorageRequest);
            DocumentResponse documentResponse=new DocumentResponse();
            if(saveDocumentResponse.getStatusCode().is2xxSuccessful()) {
                documentResponse=saveDocumentResponse.getBody();
            }else{
                throw new EntityNotFoundException("Something went wrong");
            }
            courseMaterial.setDocumentId(documentResponse.getId());
        }
         return
                    courseMaterialMapper.toCourseMaterialResponse(
                           addCourseMaterial(courseMaterial));
    }

    @Transactional(readOnly = true)
    public CourseMaterial addCourseMaterial(CourseMaterial courseMaterial) {
        return   courseMaterialRepository.save(courseMaterial);
    }

    /**
     * Updates the summary of an existing course material.
     * @param id The ID of the course material to update.
     * @param updateCourseMaterialRequest The request containing updated data.
     * @return ResponseEntity containing the updated CourseMaterialResponse.
     * @throws EntityNotFoundException if the document or course material is not found.
     */
    @Transactional(readOnly = false)
    public CourseMaterialResponse updateCourseMaterialSummary(String id, @Valid CourseMaterialRequest updateCourseMaterialRequest) {
//        ResponseEntity<DocumentResponse> documentResponse = documentClient.findById(updateCourseMaterialRequest.getDocumentId());
//        if (documentResponse == null)
//            throw new EntityNotFoundException("Document not found");
        CourseMaterial courseMaterial = findByIdAndIsDeleted(id, false)
                .stream().findFirst().orElseThrow(() -> new EntityNotFoundException("Course material to update is not found"));
        return courseMaterialMapper.toCourseMaterialResponse(courseMaterialRepository
                .save(courseMaterialMapper.toCourseMaterialUpdate(courseMaterial, updateCourseMaterialRequest)));
    }

    /**
     * Finds a course material by its ID and deletion status.
     * @param id The ID of the course material.
     * @param isDeleted The deletion status to filter by.
     * @return Optional containing the found CourseMaterial, if any.
     */
    @Transactional(readOnly = true)
    public Optional<CourseMaterial> findByIdAndIsDeleted(String id, boolean isDeleted) {
        return courseMaterialRepository.findByIdAndIsDeleted(UUID.fromString(id), isDeleted);
    }
    public Optional<CourseMaterial> getEntity(String id) {
        return courseMaterialRepository.findById(UUID.fromString(id));
    }
    @Transactional(readOnly = true)
    public List<CourseMaterial> findByIdAndIsDeleted(List<String> ids, boolean isDeleted) {
        List<UUID>generatedId=ids.stream().map(UUID::fromString).toList();
        return courseMaterialRepository.findByIdInAndIsDeleted(generatedId, isDeleted);
    }

    /**
     * Retrieves a paginated list of course material summaries, optionally filtered by search and display status.
     * @param page The page number (must be positive).
     * @param size The page size (must be positive).
     * @param sort The field to sort by.
     * @param search The search keyword for title or description.
     * @param display The display status ("all" or "archive").
     * @return PagedResponse containing CourseMaterialResponse objects.
     */
    @Transactional(readOnly = true)
    public PagedResponse<CourseMaterialResponse> getAllModulePageSummary(
            @Positive(message = "Page number must be positive") int page,
            @Positive(message = "Page size must be positive") int size,
            String sort, String search, String display
    ) {
        boolean deleteStatus = display.equals("archive");
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        List<CourseMaterialResponse> content = new ArrayList<>();
        Page<CourseMaterial> courseMaterialPage=search(page,size,sort,search,display);
        // Map course materials to response DTOs
        content = courseMaterialPage.getContent().stream()
                .map(courseMaterialMapper::toCourseMaterialResponse)
                .toList();

        // Return paged response
        return new PagedResponse<>(
                content,
                courseMaterialPage.getNumber(),
                courseMaterialPage.getSize(),
                courseMaterialPage.getTotalElements(),
                courseMaterialPage.getTotalPages(),
                courseMaterialPage.isLast()
        );
    }

    /**
     * Retrieves a paginated list of course material details, including associated documents.
     * @param page The page number (must be positive).
     * @param size The page size (must be positive).
     * @param sort The field to sort by.
     * @param search The search keyword for title or description.
     * @param display The display status ("all" or "archive").
     * @return PagedResponse containing CourseMaterialDocumentResponse objects.
     */
@Transactional(readOnly = true)
public Page<CourseMaterial> search( @Positive(message = "Page number must be positive") int page,
                                     @Positive(message = "Page size must be positive") int size,
                                     String sort, String search, String display) {
    boolean deleteStatus = display.equals("archive");
    Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
    List<CourseMaterialResponse> content = new ArrayList<>();
    Page<CourseMaterial> courseMaterialPage;

    // Fetch course materials based on display and search criteria
    if (display.equals("all")) {
        courseMaterialPage = search.isBlank()
                ? courseMaterialRepository.findAll(pageable)
                : courseMaterialRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(search, search, pageable);
    } else {
        courseMaterialPage = search.isBlank()
                ? courseMaterialRepository.findByIsDeleted(deleteStatus, pageable)
                : courseMaterialRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndIsDeleted(
                search, search, deleteStatus, pageable);
    }
    return  courseMaterialPage;
}

    public PagedResponse<ResponseDetailWrapper<CourseMaterialResponse, DocumentResponse>>getPagedCourseMaterials(int page, int size, String sort, String search, String display, String order) {

        Sort orderBy = order.equalsIgnoreCase("desc")
                ? Sort.by(sort).descending()
                : Sort.by(sort).ascending();

        Pageable pageable = PageRequest.of(page, size, orderBy);

        Page<CourseMaterial> courseMaterials = courseMaterialRepository.findAll( pageable);

        // Get document IDs and fetch document responses
        Set<String> documentIds = courseMaterials.getContent().stream()
                .map(CourseMaterial::getDocumentId)
                .collect(Collectors.toSet());

        List<DocumentResponse> documentResponses = documentClient.findListDocumentDetailByDocumentIds(documentIds).getBody();

        // Create a map for quick lookup of documents by ID
        Map<String, DocumentResponse> documentMap = documentResponses.stream()
                .collect(Collectors.toMap(DocumentResponse::getId, Function.identity()));

        // Map to CourseMaterialDetailResponse with URL from documents
        List<ResponseDetailWrapper<CourseMaterialResponse, DocumentResponse>> courseMaterialDetailResponses = courseMaterials.getContent().stream()
                .map(courseMaterial -> {
                    DocumentResponse document = documentMap.get(courseMaterial.getDocumentId());
                   return new ResponseDetailWrapper<>(courseMaterialMapper.toCourseMaterialResponse(courseMaterial), document);
                })
                .toList();

        return new PagedResponse<>(
                courseMaterialDetailResponses,
                courseMaterials.getNumber(),
                courseMaterials.getSize(),
                courseMaterials.getTotalElements(),
                courseMaterials.getTotalPages(),
                courseMaterials.isLast()
        );
    }
@Transactional(readOnly = false,rollbackFor = Exception.class)
    public CourseMaterialResponse updateCourseMaterial(String id,@Valid CourseMaterialRequest request) {
        CourseMaterial courseMaterial = findByIdAndIsDeleted(id, false).orElseThrow(() -> new EntityNotFoundException("Specified Course material not found"));
        CourseMaterial courseMaterialMapped=courseMaterialMapper.toCourseMaterialUpdate(courseMaterial,request);
        if(request.getAttachment()!=null) {
        ObjectStorageRequest objectStorageRequest = ObjectStorageRequest.builder()
                .bucketName(request.getBucketName())
                .objectPath(request.getObjectPath())
                .attachedFile(request.getAttachment())
                .build();
        ResponseEntity<DocumentResponse>documentResponseResponseEntity=documentClient.updateSingleObject(courseMaterial.getDocumentId(),objectStorageRequest);
            DocumentResponse documentResponse=new DocumentResponse();
        if(documentResponseResponseEntity.getStatusCode().is2xxSuccessful()&&documentResponseResponseEntity.getBody()!=null) {
            courseMaterialMapped.setDocumentId(documentResponseResponseEntity.getBody().getId());
        }else {
            throw new EntityNotFoundException(documentResponseResponseEntity.getHeaders().getFirst("error"));
        }
        }
        CourseMaterial savedCourseMaterial = courseMaterialRepository.save(courseMaterialMapped);
        return courseMaterialMapper.toCourseMaterialResponse(savedCourseMaterial);
    }
    @Transactional(readOnly = true)
    public ResponseDetailWrapper<CourseMaterialResponse, DocumentResponse> getCourseMaterialById(String id) {
    CourseMaterial courseMaterial=getEntity(id).orElseThrow(() -> new EntityNotFoundException("Specified Course material not found"));
    ResponseDetailWrapper<CourseMaterialResponse, DocumentResponse> detailWrapper=new ResponseDetailWrapper<>();
    if(courseMaterial.getDocumentId()==null){
        detailWrapper.setContent(courseMaterialMapper.toCourseMaterialResponse(courseMaterial));
        return detailWrapper;
    }
        ResponseEntity<DocumentResponse> documentResponseEntity=documentClient.findDocument(courseMaterial.getDocumentId());
        DocumentResponse documentResponse=new DocumentResponse();
        if(documentResponseEntity.getStatusCode().is2xxSuccessful()) {
            documentResponse=documentResponseEntity.getBody();
        }
        detailWrapper.setContent(courseMaterialMapper.toCourseMaterialResponse(courseMaterial));
        detailWrapper.setData(documentResponse);
        return detailWrapper;
    }
    @Transactional(readOnly = true)
    public List<ResponseDetailWrapper<CourseMaterialResponse, DocumentResponse>> getListOfCourseMaterial(Set<String> ids) {
        // Convert incoming string IDs to UUID
        List<UUID> generatedIds = ids.stream()
                .map(UUID::fromString)
                .toList();

        // Fetch course materials and sort by createdAt descending
        List<CourseMaterial> courseMaterials = courseMaterialRepository.findAllByIdIn(generatedIds).stream()
                .sorted(Comparator.comparing(CourseMaterial::getCreatedAt).reversed())
                .toList();

        // Extract and fetch related documents
        Set<String> documentIds = courseMaterials.stream()
                .map(CourseMaterial::getDocumentId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Map<String, DocumentResponse> documentMap = Objects.requireNonNull(documentClient.findListDocumentDetailByDocumentIds(documentIds).getBody()).stream()
                .collect(Collectors.toMap(DocumentResponse::getId, dr -> dr));

        // Map course materials to response wrappers
        return courseMaterials.stream()
                .map(cm -> {
                    CourseMaterialResponse cmResponse = courseMaterialMapper.toCourseMaterialResponse(cm);
                    DocumentResponse docResponse = documentMap.get(cm.getDocumentId());
                    return new ResponseDetailWrapper<>(cmResponse, docResponse);
                })
                .toList();
    }

    protected void deleteDocument(String documentId) {
        documentClient.deleteDocument(documentId);

    }
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public boolean deleteCourseMaterial(CourseMaterial courseMaterial) {
        try {
            if(courseMaterial.getDocumentId()!=null) {
                ResponseEntity<String> del_response = documentClient.deleteDocument(courseMaterial.getDocumentId());
                if (del_response.getStatusCode().is2xxSuccessful() && del_response.getBody() != null) {
                    courseMaterialRepository.delete(courseMaterial);
                    return true;
                } else {
                    throw new EntityNotFoundException(del_response.getHeaders().getFirst("error"));
                }
            }else {
                courseMaterialRepository.delete(courseMaterial);
                return true;
            }
        }catch (Exception e) {
            log.info("Delete course material fail {}", e.getMessage());
            throw new EntityNotFoundException("Unable to delete the course material");
        }
    }
}
