package rw.ac.ilpd.reportingservice.mapper;

import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import rw.ac.ilpd.reportingservice.model.nosql.document.Position;
import rw.ac.ilpd.reportingservice.util.DateMapperFormatter;
import rw.ac.ilpd.sharedlibrary.dto.position.PositionRequest;
import rw.ac.ilpd.sharedlibrary.dto.position.PositionResponse;

@Mapper(componentModel = "spring",uses = DateMapperFormatter.class)
public interface PositionMapper {
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "createdAt",ignore = true)
    @Mapping(target = "updatedAt",ignore = true)
    Position toPosition(@Valid PositionRequest request);
    @Mapping(target = "createdAt",qualifiedByName = "formatDateTime")
    @Mapping(target = "updatedAt",qualifiedByName = "formatDateTime")
    PositionResponse fromPosition(Position result);

    Position toPositionUpdate(@MappingTarget Position position, PositionRequest request);
}
