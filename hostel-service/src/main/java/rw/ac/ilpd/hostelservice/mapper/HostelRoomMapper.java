package rw.ac.ilpd.hostelservice.mapper;

import org.mapstruct.*;
import rw.ac.ilpd.hostelservice.model.nosql.document.HostelRoom;
import rw.ac.ilpd.hostelservice.util.DateFormatterUtil;
import rw.ac.ilpd.sharedlibrary.dto.hostel.HostelRoomRequest;
import rw.ac.ilpd.sharedlibrary.dto.hostel.HostelRoomResponse;

import java.time.LocalDateTime;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class, UUID.class},uses = {DateFormatterUtil.class})
public interface HostelRoomMapper {

    @Mapping(target = "id", expression = "java(UUID.randomUUID().toString())")
    @Mapping(target = "occupationStatus",constant = "FREE")
    @Mapping(target = "roomStatusState",constant = "ACTIVE")
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    HostelRoom toHostelRoom(HostelRoomRequest request);
    @Mapping(source = "entity.createdAt", target = "createdAt", qualifiedByName = "formatDateTime")
    HostelRoomResponse fromHostelRoom(HostelRoom entity);
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "createdAt",ignore = true)
    HostelRoom tomHostelRoomUpdate(@MappingTarget HostelRoom entity,HostelRoomRequest request);
}
