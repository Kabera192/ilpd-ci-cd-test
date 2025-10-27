package rw.ac.ilpd.academicservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import rw.ac.ilpd.academicservice.model.nosql.embedding.DeliberationDistinction;
import rw.ac.ilpd.sharedlibrary.dto.deliberationdistinction.DeliberationDistinctionRequest;
import rw.ac.ilpd.sharedlibrary.dto.deliberationdistinction.DeliberationDistinctionResponse;

@Mapper(componentModel = "spring")
public interface DeliberationDistinctionMapper {
    @Mapping(target = "id",ignore = true)
    DeliberationDistinction toDeliberationDistinction(DeliberationDistinctionRequest request);
    DeliberationDistinction toDeliberationDistinctionUpdate(@MappingTarget  DeliberationDistinction request,DeliberationDistinctionRequest deliberationDistinctionRequest);
    DeliberationDistinctionResponse fromDeliberationDistinction( DeliberationDistinctionRequest deliberationDistinctionRequest);
}
