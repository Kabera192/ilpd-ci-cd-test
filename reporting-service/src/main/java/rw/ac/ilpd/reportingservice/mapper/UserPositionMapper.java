package rw.ac.ilpd.reportingservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import rw.ac.ilpd.reportingservice.model.nosql.document.UserPosition;
import rw.ac.ilpd.sharedlibrary.dto.position.UserPositionsRequest;
import rw.ac.ilpd.sharedlibrary.dto.position.UserPositionsResponse;

import java.util.UUID;

@Mapper(componentModel = "spring",imports = {UUID.class})
public interface UserPositionMapper {
    UserPosition toUserPosition(UserPositionsRequest request);

    UserPositionsResponse fromUserPositions(UserPosition saved);
    @Mapping(target = "createdAt",ignore = true)
    @Mapping(target = "id",ignore = true)
//    @Mapping(target ="userId",source = "UUID.fromString(request.userId)")
    UserPosition toUserPositionUpdate(@MappingTarget UserPosition userPosition, UserPositionsRequest request);
}
