package rw.ac.ilpd.hostelservice.mapper;

import org.mapstruct.*;
import rw.ac.ilpd.hostelservice.model.nosql.document.HostelReservationRoom;
import rw.ac.ilpd.sharedlibrary.dto.hostel.HostelReservationRoomRequest;
import rw.ac.ilpd.sharedlibrary.dto.hostel.HostelReservationRoomResponse;
import rw.ac.ilpd.sharedlibrary.dto.hostel.HostelReservationRoomUpdateRequest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HostelReservationRoomMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
//    @Mapping(source = "reservedRoom", target = "reservedRoom", qualifiedByName = "stringToUUID")
//    @Mapping(source = "client", target = "clientId", qualifiedByName = "stringToUUID")
    @Mapping(source = "checkInTime", target = "checkInDateTime", qualifiedByName = "stringToLocalDateTime")
    @Mapping(source = "checkOutTime", target = "checkOutDateTime", qualifiedByName = "stringToLocalDateTime")
    HostelReservationRoom toEntity(HostelReservationRoomRequest request);

    @Mapping(source = "id", target = "id", qualifiedByName = "uuidToString")
//    @Mapping(source = "reservationId", target = "reservation", qualifiedByName = "uuidToString")
//    @Mapping(source = "roomId", target = "room", qualifiedByName = "uuidToString")
//    @Mapping(source = "clientId", target = "client", qualifiedByName = "uuidToString")
    @Mapping(source = "checkInDateTime", target = "checkInTime", qualifiedByName = "localDateTimeToString")
    @Mapping(source = "checkOutDateTime", target = "checkOutTime", qualifiedByName = "localDateTimeToString")
    @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "localDateTimeToString")
    @Mapping(source = "updatedAt", target = "updatedAt", qualifiedByName = "localDateTimeToString")
    HostelReservationRoomResponse toResponse(HostelReservationRoom entity);

    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "reservationId", ignore = true)
    @Mapping(target = "roomId", ignore = true)
    @Mapping(target = "clientId", ignore = true)
//    @Mapping(target = "reservationCode", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
//    @Mapping(source = "checkInTime", target = "checkInDateTime", qualifiedByName = "stringToLocalDateTime")
//    @Mapping(source = "checkOutTime", target = "checkOutDateTime", qualifiedByName = "stringToLocalDateTime")
    void updateEntityFromRequest(HostelReservationRoomUpdateRequest request, @MappingTarget HostelReservationRoom entity);

    List<HostelReservationRoomResponse> toResponseList(List<HostelReservationRoom> entities);

    // Custom mapping methods
    @Named("stringToUUID")
    default UUID stringToUUID(String value) {
        return value != null ? UUID.fromString(value) : null;
    }

    @Named("uuidToString")
    default String uuidToString(UUID value) {
        return value != null ? value.toString() : null;
    }

    @Named("stringToLocalDateTime")
    default LocalDateTime stringToLocalDateTime(String value) {
        if (value == null) return null;
        try {
            return LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (Exception e) {
            // Try parsing as ISO instant if the above fails
            return LocalDateTime.ofInstant(Instant.parse(value), ZoneId.systemDefault());
        }
    }

    @Named("localDateTimeToString")
    default String localDateTimeToString(LocalDateTime value) {
        return value != null ? value.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) : null;
    }
}
