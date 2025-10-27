package rw.ac.ilpd.hostelservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import rw.ac.ilpd.hostelservice.model.nosql.document.HostelReservation;
import rw.ac.ilpd.hostelservice.model.nosql.embedding.HostelReservationRoomTypeCount;
import rw.ac.ilpd.sharedlibrary.dto.hostel.HostelReservationRequest;
import rw.ac.ilpd.sharedlibrary.dto.hostel.HostelReservationResponse;
import rw.ac.ilpd.sharedlibrary.dto.hostel.HostelReservationRoomTypeCountRequest;
import rw.ac.ilpd.sharedlibrary.dto.hostel.HostelReservationRoomTypeCountResponse;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring",imports = {LocalDateTime.class})
public interface HostelReservationMapper {
    HostelReservationMapper INSTANCE = Mappers.getMapper(HostelReservationMapper.class);

    HostelReservationResponse toResponse(HostelReservation entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "updatedAt", ignore = true)
    HostelReservation toEntity(HostelReservationRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "randomQueryCode", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", expression = "java(LocalDateTime.now())")
    void updateEntityFromRequest(HostelReservationRequest request, @MappingTarget HostelReservation entity);

    HostelReservationRoomTypeCountResponse toRoomTypeCountResponse(HostelReservationRoomTypeCount entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hostelReservationId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    HostelReservationRoomTypeCount toRoomTypeCountEntity(HostelReservationRoomTypeCountRequest request);


}
