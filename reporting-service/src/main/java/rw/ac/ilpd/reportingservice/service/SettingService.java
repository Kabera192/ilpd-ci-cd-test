package rw.ac.ilpd.reportingservice.service;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rw.ac.ilpd.sharedlibrary.dto.setting.SettingRequest;
import rw.ac.ilpd.sharedlibrary.dto.setting.SettingResponse;
import rw.ac.ilpd.sharedlibrary.dto.setting.SettingUpdateRequest;
import rw.ac.ilpd.sharedlibrary.enums.SettingCategories;

import java.util.List;

/**
 * Author: Michel Igiraneza
 * Created: 2025-08-14
 */
public   interface SettingService {

    List<SettingResponse> createList(List<SettingRequest> request);

    SettingResponse getById(String id);

    SettingResponse update(String id, SettingUpdateRequest request);

    void delete(String id);

    void restore(String id);

    List<SettingResponse> getAllActiveList();

    long countActive();

    long countDeleted();

    List<SettingResponse> getSettingByCategory(SettingCategories settingCategories, String activeStatus, String search);
}
