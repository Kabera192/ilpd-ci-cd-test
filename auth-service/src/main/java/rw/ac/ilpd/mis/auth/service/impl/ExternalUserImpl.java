package rw.ac.ilpd.mis.auth.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rw.ac.ilpd.mis.auth.client.DocumentClient;
import rw.ac.ilpd.mis.auth.entity.mongo.ExternalUserEntity;
import rw.ac.ilpd.mis.auth.repository.mongo.ExternalUserRepository;
import rw.ac.ilpd.mis.auth.service.ExternalUserService;
import rw.ac.ilpd.mis.auth.service.UserService;
import rw.ac.ilpd.mis.auth.service.impl.mapper.AcademicExternalUser;
import rw.ac.ilpd.mis.shared.dto.user.*;
import rw.ac.ilpd.mis.shared.dto.pagination.PagedResponse;
import rw.ac.ilpd.mis.shared.util.errors.InvalidInputException;
import rw.ac.ilpd.sharedlibrary.dto.document.DocumentResponse;
import rw.ac.ilpd.sharedlibrary.dto.document.ObjectStorageRequest;
import rw.ac.ilpd.sharedlibrary.util.ObjectUploadPath;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExternalUserImpl extends ExternalUserService {
    private final ExternalUserRepository externalUserRepository;
    private final UserService userService;
    private final AcademicExternalUser aeUserMapper;
    private final DocumentClient documentClient;

    @Override
    public ResponseEntity<AcademicExternalUserResponse> createAcademicExternalUser(AcademicExternalUserRequest request) {
        ExternalUserEntity  externalUserEntity = aeUserMapper.toExternalUser(request);
           if(request.getSponsorType().equals("ORGANIZATION")){
               if(request.getRecommendationFile().isEmpty()){
                   throw new InvalidInputException("Attachment file is required");
               }
               if(!request.getRecommendationFile().isEmpty()){
                   DocumentResponse documentResponse = uploadDocument(request.getRecommendationFile());
                   if(documentResponse == null){
                       throw new InvalidInputException("Unable to upload document");
                   }
                   externalUserEntity.setDocumentId(documentResponse.getId());
               }
           }

        ExternalUserEntity eUEntity = saveOrUpdateExternalUser(externalUserEntity);
        return new ResponseEntity<>(aeUserMapper.toNextKinResponse(eUEntity), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<AcademicExternalUserResponse> updateAcademicExternalUser(String id, AcademicExternalUserRequest request) {
        ExternalUserEntity externalUserEntity = getExternalUser(id).orElseThrow(()->{
            log.info("External user with id {} not found", id);
            return new EntityNotFoundException("User not found");
        });
        ExternalUserEntity mappedExtUserUpdate=aeUserMapper.toExternalUserFromUpdate(externalUserEntity,request);
        if(!request.getRecommendationFile().isEmpty()){
            DocumentResponse documentResponse = uploadDocument(request.getRecommendationFile());
            if(documentResponse == null){
                throw new InvalidInputException("Unable to upload document");
            }
            externalUserEntity.setDocumentId(documentResponse.getId());
        }
        ExternalUserEntity eUEntity = saveOrUpdateExternalUser(mappedExtUserUpdate);
        return new ResponseEntity<>(aeUserMapper.toNextKinResponse(eUEntity), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AcademicExternalUserResponse> deleteAcademicExternalUser(String id) {
        return getExternalUser(id)
                .map(entity -> {
                    externalUserRepository.delete(entity);
                    AcademicExternalUserResponse response = aeUserMapper.toNextKinResponse(entity);
                    return ResponseEntity.ok(response);
                })
                .orElseThrow(()->new EntityNotFoundException("User not found") );
    }

    @Override
    public ResponseEntity<AcademicExternalUserResponse> findById(String id) {
        return getExternalUser(id)
                .map(entity -> {
                    AcademicExternalUserResponse response = aeUserMapper.toNextKinResponse(entity);
                    return ResponseEntity.ok(response);
                })
                .orElseThrow(()->new EntityNotFoundException("User not found") );
    }

    @Override
    public ResponseEntity<PagedResponse<AcademicExternalUserResponse>> getPagedAcademicExternalUserResponse( int page,
                                                                                                             int size,
                                                                                                             String sortBy,
                                                                                                             String sortDirection,
                                                                                                             String search) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<ExternalUserEntity>externalUserPage;
        if(search.isBlank()){
            externalUserPage=externalUserRepository.findAll(pageable);
        }else {
            externalUserPage=externalUserRepository.findByNameOrEmailOrPhoneNumber(search,search,search,pageable);
        }
        return getPagedNextKinResult(externalUserPage);
    }

    @Override
    public ResponseEntity<PagedResponse<AcademicExternalUserResponse>> getPrincipalAcademicExternalUserUsers(
             int page,
             int size,
             String sortBy,
             String sortDirection
            , String search,String type) {
        Pageable pageable= PageRequest.of(page, size, Sort.by(sortBy));
        Page<ExternalUserEntity>externalUserPage;
        if(search.isBlank()){
            externalUserPage=externalUserRepository.findByCreatedByAndType(getCurrentUserId(),type.toUpperCase(),pageable);
        }else {
            externalUserPage=externalUserRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrPhoneNumberContainingIgnoreCaseAndCreatedByAndType(search,search,search,getCurrentUserId(),type.toUpperCase(),pageable);
        }
        return getPagedNextKinResult(externalUserPage);
    }

    @Override
    public ResponseEntity<AcademicExternalUserResponse> getPrincipalAcademicExternalUserById(String id) {
        ExternalUserEntity externalUser =externalUserRepository.findByIdAndCreatedBy(id,getCurrentUserId()).orElse(null);
        return new ResponseEntity<>(aeUserMapper.toNextKinResponse(externalUser), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deletePrincipalAcademicExternalUserById(String id) {
        log.info(getCurrentUserId());
        externalUserRepository
                .findByIdAndCreatedBy(id,getCurrentUserId())
                .map(entity -> {
                    AcademicExternalUserResponse response = aeUserMapper.toNextKinResponse(entity);
                    return ResponseEntity.ok(response);
                })
                .orElseThrow(()-> {
                    return new EntityNotFoundException("User not found");
                } );
        return ResponseEntity.ok().build();
    }
    //Helper
    private ExternalUserEntity saveOrUpdateExternalUser(ExternalUserEntity externalUserEntity) {
        return   externalUserRepository.save(externalUserEntity);
    }
    private Optional<ExternalUserEntity> getExternalUser(String id) {
        return externalUserRepository.findById(id);
    }
    private ResponseEntity<PagedResponse<AcademicExternalUserResponse>>getPagedNextKinResult(Page<ExternalUserEntity>externalUserPage){
        List<AcademicExternalUserResponse> result=externalUserPage.getContent().stream().map(aeUserMapper::toNextKinResponse).toList();
        return   new ResponseEntity<>(PagedResponse.<AcademicExternalUserResponse>builder()
                .content(result)
                .pageNumber(externalUserPage.getNumber())
                .pageSize(externalUserPage.getSize())
                .totalElements(externalUserPage.getTotalElements())
                .last(externalUserPage.isLast())
                .totalPages(externalUserPage.getTotalPages())
                .build(),HttpStatus.OK);
    }
    private String getCurrentUserId(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(authentication.getName());
        return  user.getId().toString();
    }
    private DocumentResponse uploadDocument(MultipartFile multipartFile) {
        try {
            ResponseEntity<DocumentResponse> doc = documentClient.uploadSingleObject(ObjectStorageRequest.builder()
                    .bucketName(ObjectUploadPath.Academic.BASE)
                    .objectPath(ObjectUploadPath.Academic.ACADEMIC_APPLICATION_DOCUMENT_PATH)
                    .attachedFile(multipartFile)
                    .build());
            return doc.getBody();

        }catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }
}