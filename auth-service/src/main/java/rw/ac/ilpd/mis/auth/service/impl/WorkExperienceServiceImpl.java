package rw.ac.ilpd.mis.auth.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rw.ac.ilpd.mis.auth.client.DocumentClient;
import rw.ac.ilpd.mis.auth.entity.mongo.WorkExperience;
import rw.ac.ilpd.mis.auth.repository.mongo.WorkExperienceRepository;
import rw.ac.ilpd.mis.auth.service.UserService;
import rw.ac.ilpd.mis.auth.service.WorkExperienceService;
import rw.ac.ilpd.mis.auth.service.impl.mapper.WorkExperienceMapper;
import rw.ac.ilpd.mis.shared.dto.pagination.PagedResponse;
import rw.ac.ilpd.mis.shared.dto.user.User;
import rw.ac.ilpd.mis.shared.dto.userprofile.WorkExperienceRequest;
import rw.ac.ilpd.mis.shared.dto.userprofile.WorkExperienceResponse;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentResponse;
import rw.ac.ilpd.sharedlibrary.dto.document.ObjectStorageRequest;
import rw.ac.ilpd.sharedlibrary.util.ObjectUploadPath;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class WorkExperienceServiceImpl implements WorkExperienceService {

    private final WorkExperienceRepository repository;
    private final WorkExperienceMapper mapper;
    private final DocumentClient documentClient;
    private final UserService userService;

    @Override
    public WorkExperienceResponse create(WorkExperienceRequest request) {
        WorkExperience entity = mapper.toWorkingExperience(request);
        if(!request.getAttachment().isEmpty()){
            DocumentResponse documentResponses = uploadDocuments(request.getAttachment());
            assert documentResponses != null;
            String docIds=documentResponses.getId();
            entity.setDocumentId(docIds);
        }
        WorkExperience savedEntity = repository.save(entity);
        return mapper.toWorkingExperienceResponse(savedEntity);
    }

    @Override
    public WorkExperienceResponse update(String id, WorkExperienceRequest request) {
        WorkExperience existingEntity = getWorkExperienceByIdAndUserId(id,getCurrentUserId())
                .orElseThrow(()->{
                    log.error("WorkExperience not found with id {}", id);
                    return new EntityNotFoundException("Work Experience not found");
                });
        WorkExperience workingExperienceUpdate = mapper.toWorkingExperienceUpdate(existingEntity, request);
        if(!request.getAttachment().isEmpty()){
            DocumentResponse documentResponses = uploadDocuments(request.getAttachment());
            assert documentResponses != null;
            String docIds=documentResponses.getId();
            workingExperienceUpdate.setDocumentId(docIds);
        }
        WorkExperience updatedEntity = repository.save(workingExperienceUpdate);
            return mapper.toWorkingExperienceResponse(updatedEntity);
    }



    @Override
    public void deleteById(String id) {
        getWorkExperienceByIdAndUserId(id, getCurrentUserId())
                .ifPresent(workExperience -> {
                    // delete attached documents if exist
                    documentClient.deleteDocument(workExperience.getDocumentId());
                    // delete the work experience itself
                    repository.delete(workExperience);
                });
    }


    @Override
    public PagedResponse<WorkExperienceResponse> getPrincipleUserWorkExperience(Pageable pageable,String search) {

        Page<WorkExperience> workingExperiencePage;
        if(!search.isBlank()){
            workingExperiencePage=repository.findByCompanyNameContainingIgnoreCaseOrPositionTitleContainingIgnoreCaseOrWorkModeContainingIgnoreCaseAndUserId(search,search,search,getCurrentUserId(),pageable);
        }else {
            workingExperiencePage=repository.findByUserId(getCurrentUserId(),pageable);
        }

        return getPagedResult(workingExperiencePage);
    }

    public  Optional<WorkExperience> getWorkExperienceById(String id) {
        return repository.findById(id);
    }
    //helper
    private Optional<WorkExperience> getWorkExperienceByIdAndUserId(String id, String currentUserId) {
        return repository.findByIdAndUserId(id,currentUserId);
    }

    private PagedResponse<WorkExperienceResponse>getPagedResult(Page<WorkExperience> workingExperiencePage) {
        List<WorkExperienceResponse> result=workingExperiencePage.getContent().stream().map(mapper::toWorkingExperienceResponse).toList();
        return    PagedResponse.<WorkExperienceResponse>builder()
                .content(result)
                .pageNumber(workingExperiencePage.getNumber())
                .pageSize(workingExperiencePage.getSize())
                .totalElements(workingExperiencePage.getTotalElements())
                .last(workingExperiencePage.isLast())
                .totalPages(workingExperiencePage.getTotalPages())
                .build();
    }
    private String getCurrentUserId(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(authentication.getName());
        return  user.getId().toString();
    }
    private DocumentResponse uploadDocuments(MultipartFile file) {
        try {
            ResponseEntity<DocumentResponse> doc = documentClient.uploadSingleObject(ObjectStorageRequest.builder()
                    .bucketName(ObjectUploadPath.User.BASE)
                    .objectPath(ObjectUploadPath.User.WORKING_EXPERIENCE)
                    .attachedFile(file)
                    .build());

            return doc.getBody();

        }catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }
}