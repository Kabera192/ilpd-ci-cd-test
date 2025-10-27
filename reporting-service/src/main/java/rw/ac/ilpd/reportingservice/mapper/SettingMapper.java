package rw.ac.ilpd.reportingservice.mapper;

import org.mapstruct.*;
import rw.ac.ilpd.reportingservice.model.nosql.document.Setting;
import rw.ac.ilpd.sharedlibrary.dto.setting.SettingRequest;
import rw.ac.ilpd.sharedlibrary.dto.setting.SettingResponse;
import rw.ac.ilpd.sharedlibrary.dto.setting.SettingUpdateRequest;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SettingMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "isEnabled", constant = "true")
    Setting toEntity(SettingRequest request);

    SettingResponse toResponse(Setting entity);

    List<SettingResponse> toResponseList(List<Setting> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "key", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy",ignore = true)
    @Mapping(target = "deleted", ignore = true)
    void updateEntityFromRequest(SettingUpdateRequest request, @MappingTarget Setting entity, String updatedBy);

    @Named("toBasicResponse")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    SettingResponse toBasicResponse(Setting entity);

    List<Setting> toEntityList(List<SettingRequest> settingRequests);
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    Setting toEntityUpdate(@MappingTarget Setting existing, SettingUpdateRequest request);
}