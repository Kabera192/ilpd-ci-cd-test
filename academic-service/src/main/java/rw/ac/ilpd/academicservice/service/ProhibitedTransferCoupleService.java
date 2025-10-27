package rw.ac.ilpd.academicservice.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.ilpd.academicservice.exception.EntityAlreadyExists;
import rw.ac.ilpd.academicservice.mapper.ProhibitedTransferCoupleMapper;
import rw.ac.ilpd.academicservice.model.sql.ProhibitedTransferCouple;
import rw.ac.ilpd.academicservice.model.sql.StudyModeSession;
import rw.ac.ilpd.academicservice.repository.sql.ProhibitedTransferCoupleRepository;
import rw.ac.ilpd.sharedlibrary.dto.prohibitedtransfercouple.ProhibitedTransferCoupleRequest;
import rw.ac.ilpd.sharedlibrary.dto.prohibitedtransfercouple.ProhibitedTransferCoupleResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProhibitedTransferCoupleService {
    private final ProhibitedTransferCoupleRepository ptCoupleRepository;
    private final StudyModeSessionService smsService;
    private final ProhibitedTransferCoupleMapper ptcMapper;

    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public ResponseEntity<ProhibitedTransferCoupleResponse> createProhibitedTransferCouple(@Valid ProhibitedTransferCoupleRequest request) {
        if(request.getFromStudyModeSessionId().equals(request.getToStudyModeSessionId())){
            throw new EntityNotFoundException("The prohibited  transfer couple between same session is  not valid");
        }
        StudyModeSession sms1=smsService
                .getEntity(UUID.fromString(request.getFromStudyModeSessionId()))
                .orElseThrow(()->new EntityNotFoundException("The first Specified study mode session is not found"));

        StudyModeSession sms2=smsService
                .getEntity(UUID.fromString(request.getToStudyModeSessionId()))
                .orElseThrow(()->new EntityNotFoundException("The second Specified study session mode is not found"));

        validateExistenceOfProhibitedTransferCoupleBtnTheStudyModeSessions(sms1,sms2,false);

        ProhibitedTransferCouple ptcSaved=ptCoupleRepository.
                save(ptcMapper.toProhibitedTransferCouple(sms1,sms2));

        return new ResponseEntity<>(ptcMapper.fromProhibitedTransferCouple(ptcSaved), HttpStatus.CREATED);
    }

    public Optional<ProhibitedTransferCouple>getEntity(String id) {
        return ptCoupleRepository.findById(UUID.fromString(id));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ProhibitedTransferCoupleResponse> getProhibitedTransferCoupleById(String id) {

        ProhibitedTransferCouple ptc=getEntity(id)
                .orElseThrow(()->new EntityNotFoundException("Prohibited Transfer Couple not found"));

        return new ResponseEntity<>(ptcMapper
                .fromProhibitedTransferCouple(ptc),HttpStatus.OK);
    }


    @Transactional(readOnly = true)
    public ResponseEntity<List<ProhibitedTransferCoupleResponse>> getAllProhibitedTransferCouples(boolean isArchived) {

        List<ProhibitedTransferCoupleResponse>ptcList= ptCoupleRepository
                .findAllByDeletedStatus(isArchived).stream()
                .map(ptcMapper::fromProhibitedTransferCouple).toList();

        return new ResponseEntity<>(ptcList, HttpStatus.OK);
    }

    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public ResponseEntity<ProhibitedTransferCoupleResponse> updateProhibitedTransferCouple(String id, @Valid ProhibitedTransferCoupleRequest request) {
        ProhibitedTransferCouple ptc=getEntity(id)
                .filter(ptcf->!ptcf.getDeletedStatus())
                .orElseThrow(()->new EntityNotFoundException("Prohibited Transfer Couple is archived. undo archive to update the session"));

        StudyModeSession sms1=smsService
                .getEntity(UUID.fromString(request.getFromStudyModeSessionId()))
                .orElseThrow(()->new EntityNotFoundException("The First Specified session is not found"));

        StudyModeSession sms2=smsService
                .getEntity(UUID.fromString(request.getToStudyModeSessionId()))
                .orElseThrow(()->new EntityNotFoundException("Second Specified session is not found"));
        validateExistenceOfProhibitedTransferCoupleBtnTheStudyModeSessions(sms1,sms2,true);
//        validate if the updated session mode is already exist and not deleted
        validateExistenceOfProhibitedTransferCoupleBtnTheStudyModeSessions(sms1,sms2,false);

        ProhibitedTransferCouple savedPtc=ptCoupleRepository.save(ptcMapper.toProhibitedTransferCoupleUpdate(ptc,sms1,sms2));
        return new ResponseEntity<>(ptcMapper.fromProhibitedTransferCouple(savedPtc),HttpStatus.OK);
    }

    @Transactional(rollbackFor = Exception.class,readOnly = false)
    public ResponseEntity<ProhibitedTransferCoupleResponse> updateDeleteStatusOfProhibitedTransferCouple(String id, boolean delete) {
        ProhibitedTransferCouple prohibitedTransferCouple=getEntity(id)
                .orElseThrow(()->new EntityNotFoundException("Specified prohibited Transfer Couple not found"));
        if (delete) {
        if (prohibitedTransferCouple.getDeletedStatus()){
            throw new EntityAlreadyExists("Prohibited Transfer Couple already archived");
        }else{
            prohibitedTransferCouple.setDeletedStatus(true);
            prohibitedTransferCouple.setDeletedAt(LocalDateTime.now());
        }
    }else {
            if (prohibitedTransferCouple.getDeletedStatus()){
                throw new EntityAlreadyExists("Prohibited Transfer Couple already archived");
            }else{
                prohibitedTransferCouple.setDeletedStatus(false);
                prohibitedTransferCouple.setDeletedAt(null);
            }
    }
        ProhibitedTransferCouple savedPtc=ptCoupleRepository.save(prohibitedTransferCouple);

        return ResponseEntity.ok(ptcMapper.fromProhibitedTransferCouple(savedPtc));
    }
//    Helper
    private void validateExistenceOfProhibitedTransferCoupleBtnTheStudyModeSessions(StudyModeSession sms1, StudyModeSession sms2,boolean isDeleted) {
        boolean isProhibitedStatusExist=ptCoupleRepository
                .existsByFromStudyModeSessionAndToStudyModeSessionAndDeletedStatus(sms1,sms2,isDeleted);
        if(isDeleted){
            if(isProhibitedStatusExist){
                throw new EntityAlreadyExists("Prohibited Transfer Couple need to be unarchive first");
            }
        }
        if(isProhibitedStatusExist){
            throw new EntityAlreadyExists("Prohibited Transfer Couple already exists");
        }
    }

}
