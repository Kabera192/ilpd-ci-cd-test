package rw.ac.ilpd.hostelservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import rw.ac.ilpd.hostelservice.model.nosql.embedding.HostelReservationRoomTypeCount;
import rw.ac.ilpd.sharedlibrary.dto.hostel.HostelReservationRoomTypeCountRequest;
import rw.ac.ilpd.sharedlibrary.dto.hostel.HostelReservationRoomTypeCountResponse;

@Mapper(componentModel = "spring")
public interface HostelReservationRoomTypeCountMapper {
    HostelReservationRoomTypeCount toHostelReservationRoomTypeCount( HostelReservationRoomTypeCountRequest request);
    HostelReservationRoomTypeCount toHostelReservationRoomTypeCountUpdate(@MappingTarget HostelReservationRoomTypeCount entity, HostelReservationRoomTypeCountRequest request);
    HostelReservationRoomTypeCountResponse fromHostelReservationRoomTypeCount( HostelReservationRoomTypeCount request);
}
