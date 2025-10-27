package rw.ac.ilpd.reportingservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.ilpd.reportingservice.exception.EntityNotFoundException;
import rw.ac.ilpd.reportingservice.mapper.SettingMapper;
import rw.ac.ilpd.reportingservice.model.nosql.document.Setting;
import rw.ac.ilpd.reportingservice.repository.nosql.SettingRepository;
import rw.ac.ilpd.reportingservice.service.SettingService;
import rw.ac.ilpd.sharedlibrary.dto.setting.SettingRequest;
import rw.ac.ilpd.sharedlibrary.dto.setting.SettingResponse;
import rw.ac.ilpd.sharedlibrary.dto.setting.SettingUpdateRequest;
import rw.ac.ilpd.sharedlibrary.enums.SettingCategories;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Michel Igiraneza
 * Created: 2025-08-14
 */
@Service
@Transactional

@Slf4j
public  class SettingServiceImpl implements SettingService {
    private final SettingRepository repository;
    private final SettingMapper mapper;

    public SettingServiceImpl(SettingRepository repository, SettingMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = false,rollbackFor =  Exception.class)
    public List<SettingResponse> createList(List<SettingRequest> requests) {
//        checking that list of unsaved settings
        List<SettingRequest>remainingUnSavedSettings=requests.stream()
                .filter(settings->!repository.
                        existsByCategoryIgnoreCaseAndAcronymIgnoreCaseAndKeyIgnoreCase(settings.getCategory(),settings.getAcronym(),settings.getKey()))
                .toList();
//        Save list of remaining un saved settings
        List<Setting> setting=new ArrayList<>();
        if(!remainingUnSavedSettings.isEmpty()) {
            setting = repository
                    .saveAll(mapper.toEntityList(remainingUnSavedSettings));
        }
        return mapper.toResponseList(setting);
    }

    @Override
    @Transactional(readOnly = true)
    public SettingResponse getById(String id) {
        log.info("Fetching Setting with id: {}", id);

        Setting entity = repository.findByIdAndIsDeleted(id, false)
                .orElseThrow(() -> new EntityNotFoundException("Setting not found"));

        return mapper.toResponse(entity);
    }

    @Override
    public SettingResponse update(String id, SettingUpdateRequest request) {
        log.info("Updating Setting with id: {}", id);

        Setting existing = repository.findByIdAndIsDeleted(id, false)
                .orElseThrow(() -> new EntityNotFoundException("Setting to update not found"));
        Setting setting=mapper.toEntityUpdate(existing,request);
        Setting updated = repository.save(setting);
        log.info("Successfully updated Setting with id: {}", id);

        return mapper.toResponse(updated);
    }

    @Override
    public void delete(String id) {
        log.info("Soft deleting Setting with id: {}", id);

        Setting entity = repository.findByIdAndIsDeleted(id, false)
                .orElseThrow(() -> new EntityNotFoundException("Setting not found"));
        entity.setDeleted(true);
        entity.setEnabled(false);
        repository.save(entity);

        log.info("Successfully soft deleted Setting with id: {}", id);
    }

    @Override
    public void restore(String id) {
        log.info("Restoring Setting with id: {}", id);

        Setting entity = repository.findByIdAndIsDeleted(id,true)
                .orElseThrow(() -> new EntityNotFoundException("Setting to restore not found"));
        entity.setDeleted(false);
        entity.setEnabled(true);
        repository.save(entity);

        log.info("Successfully restored Setting with id: {}", id);
    }


    @Override
    @Transactional(readOnly = true)
    public List<SettingResponse> getAllActiveList() {
        log.info("Fetching all active Settings as list");

        List<Setting> entities = repository.findByIsDeletedOrderByNameAsc(false);
        return mapper.toResponseList(entities);
    }



    @Override
    @Transactional(readOnly = true)
    public long countActive() {
        return repository.countByIsDeleted(false);
    }

    @Override
    @Transactional(readOnly = true)
    public long countDeleted() {
        return repository.countByIsDeleted(true);
    }

    @Override
    public List<SettingResponse> getSettingByCategory(SettingCategories settingCategories, String activeStatus, String search) {
        boolean isDeleted=activeStatus.equals("archive");
        List<Setting>getAll=new ArrayList<>();
       if( search.isBlank()) {
            getAll = repository.findByCategoryIgnoreCaseAndIsDeleted(settingCategories.name(), isDeleted);
        }else{
            getAll = repository.findByCategoryIgnoreCaseAndIsDeletedAndNameContainingIgnoreCase(settingCategories.name(), isDeleted, search);
        }
        return getAll.stream().map(mapper::toResponse).toList();
    }
}