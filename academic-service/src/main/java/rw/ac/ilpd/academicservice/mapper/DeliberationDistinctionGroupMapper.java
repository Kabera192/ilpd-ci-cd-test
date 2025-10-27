package rw.ac.ilpd.academicservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import rw.ac.ilpd.academicservice.model.nosql.document.DeliberationDistinctionGroup;
import rw.ac.ilpd.sharedlibrary.dto.deliberationdistinction.DeliberationDistinctionGroupRequest;
import rw.ac.ilpd.sharedlibrary.dto.deliberationdistinction.DeliberationDistinctionGroupResponse;
import rw.ac.ilpd.sharedlibrary.dto.deliberationdistinction.DeliberationDistinctionRequest;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DeliberationDistinctionGroupMapper {
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now(java.time.ZoneOffset.UTC))")
    @Mapping(target = "deliberationDistinctions",source = "list")
    DeliberationDistinctionGroup toDeliberationDistinctionGroup(DeliberationDistinctionGroupRequest request, List<DeliberationDistinctionRequest> list);
    DeliberationDistinctionGroup toDeliberationDistinctionGroupUpdate(@MappingTarget DeliberationDistinctionGroup ddg, DeliberationDistinctionGroupRequest request);
    DeliberationDistinctionGroupResponse fromDeliberationDistinctionGroup(DeliberationDistinctionGroupRequest deliberationDistinctionRequest);
}
