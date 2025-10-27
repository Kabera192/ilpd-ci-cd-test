package rw.ac.ilpd.academicservice.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.ilpd.academicservice.exception.EntityAlreadyExists;
import rw.ac.ilpd.academicservice.mapper.AssessmentTypeMapper;
import rw.ac.ilpd.academicservice.model.sql.AssessmentGroup;
import rw.ac.ilpd.academicservice.model.sql.AssessmentType;
import rw.ac.ilpd.academicservice.repository.sql.AssessmentTypeRepository;
import rw.ac.ilpd.mis.shared.util.errors.InvalidInputException;
import rw.ac.ilpd.sharedlibrary.dto.assessmenttype.AssessmentTypeRequest;
import rw.ac.ilpd.sharedlibrary.dto.assessmenttype.AssessmentTypeResponse;
import rw.ac.ilpd.sharedlibrary.dto.validation.uuid.ValidUuid;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssessmentTypeService {
    private final AssessmentTypeRepository assessmentTypeRepository;
    private final AssessmentGroupService assessmentGroupService;
    private final ModuleComponentAssessmentService moduleComponentAssessmentService;
    private final AssessmentTypeMapper assessmentTypeMapper;

    @Transactional(readOnly = false)
    public ResponseEntity<AssessmentTypeResponse> createAssessmentType(@Valid AssessmentTypeRequest request) {
        AssessmentGroup assessmentGroup=assessmentGroupService
                .getEntity(UUID.fromString(request.getAssessmentGroupId()))
                .orElseThrow(()->new EntityNotFoundException("A specified assessment group can not found"));

//      Find the assessment type is not registered twice with in a specified group
        assessmentTypeRepository.findByAssessmentGroupAndTitleContainingIgnoreCaseAndIsDeletedOrderByCreatedAtDesc(assessmentGroup,request.getTitle(),false)
                .stream().findFirst().ifPresent(last->{
                    throw new EntityAlreadyExists("Assessment specified is already exist");
                });

//      map assessment type
        AssessmentType assessmentType=assessmentTypeMapper.toAssessmentType(assessmentGroup, request);
        return  new ResponseEntity<>(assessmentTypeMapper
                .fromAssessmentType(assessmentTypeRepository
                        .save(assessmentType)), HttpStatus.CREATED);

    }

    @Transactional(readOnly = false)
    public ResponseEntity<String> updateAssessmentType(String id, @Valid AssessmentTypeRequest request) {
        AssessmentType assessmentType=getEntity(id).orElseThrow(()->new EntityNotFoundException("A specified assessment type not found make sure you provide a correct assessment type"));
//        check whether the assessment group exist
        AssessmentGroup assessmentGroup=assessmentGroupService
                .getEntity(UUID.fromString(request.getAssessmentGroupId()))
                .orElseThrow(()->new EntityNotFoundException("Assessment group can not found"));

        boolean idNotAndTitle = assessmentTypeRepository.existsByIdNotAndAssessmentGroupAndTitle(assessmentType.getId(),assessmentGroup, request.getTitle());
        if (idNotAndTitle) throw new InvalidInputException("Assessment type title has already been taken");

        assessmentTypeRepository
                .save(assessmentTypeMapper
                        .toAssessmentTypeUpdate(assessmentType,assessmentGroup,request));
        return   new ResponseEntity<>("Assessment has updated successful", HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<AssessmentTypeResponse> findAssessmentById(String id) {
        AssessmentType assessmentType=getEntity(id).orElseThrow(()->new EntityNotFoundException("Assessment type not found"));
        return  ResponseEntity.ok(assessmentTypeMapper.fromAssessmentType(assessmentType));
    }

    @Transactional(readOnly = true)
    public Optional<AssessmentType>getEntity(String id) {
        return assessmentTypeRepository.findById(UUID.fromString(id));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<AssessmentTypeResponse>> getAllAssessmentType(String display) {
        boolean active=display.equals("active");
        if(active) {
           List<AssessmentType>activeList= assessmentTypeRepository.findByIsDeletedFalse();
           return ResponseEntity.ok(activeList.stream().map(assessmentTypeMapper::fromAssessmentType).toList());
        }
    return ResponseEntity.ok(assessmentTypeRepository
            .findAll().stream().map(assessmentTypeMapper::fromAssessmentType)
            .toList());
    }
//    public ResponseEntity<List<AssessmentTypeResponse>> findAllAssessmentTypeWithInAssessmentGroup(String assessmentGroupId,@NotBlank(message = "Display type can not be null")  String display) {
//        boolean isDeleted=display.equals("archive");
//        return ResponseEntity.ok(assessmentTypeRepository
//                .findAllByAssessmentGroupIdAndIsDeleted(UUID.fromString(assessmentGroupId),isDeleted).stream().map(assessmentTypeMapper::fromAssessmentType)
//                .toList());
//    }

    @Transactional(readOnly = false)
    public ResponseEntity<String> deleteAssessmentType(String id) {
        AssessmentType assessmentType=getEntity(id).orElseThrow(()->new EntityNotFoundException("Assessment type not found"));
        boolean exists=moduleComponentAssessmentService.existsByAssessmentTypeId(UUID.fromString(id));
//     make soft delete if the assessment type is already exist in module component assessment table
        if(exists){
            assessmentType.setDeleted(true);
            assessmentTypeRepository.save(assessmentType);
            return ResponseEntity.ok("Assessment type has been archived");
        }
//        make hard delete if assessment type has no reference in module component assessment table
        assessmentTypeRepository.delete(assessmentType);
        return  new ResponseEntity<>("Assessment has been deleted", HttpStatus.OK);
    }

    public List<AssessmentTypeResponse> findAllAssessmentTypeWithInAssessmentGroup(
            @ValidUuid(message = "Invalid format of provided assessment group identifier") String groupId,
            String display) {
            boolean isDeleted=display.equals("archive");
            if(display.equals("all")){
                List<AssessmentType>allAssessmentType=  assessmentTypeRepository.findByAssessmentGroupId(UUID.fromString(groupId));
                return allAssessmentType.stream().map(assessmentTypeMapper::fromAssessmentType).toList();
            }
        List<AssessmentType> AssessmentTypeList = assessmentTypeRepository.findByAssessmentGroupIdAndIsDeletedOrderByCreatedAtDesc(UUID.fromString(groupId), isDeleted);

        return AssessmentTypeList.stream().map(assessmentTypeMapper::fromAssessmentType).toList();
    }
}
