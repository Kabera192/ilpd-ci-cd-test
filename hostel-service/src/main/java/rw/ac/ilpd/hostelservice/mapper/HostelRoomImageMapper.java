package rw.ac.ilpd.hostelservice.mapper;

import org.mapstruct.Mapper;
import rw.ac.ilpd.hostelservice.model.nosql.embedding.HostelRoomImage;
import rw.ac.ilpd.sharedlibrary.dto.hostel.HostelRoomImageRequest;

@Mapper(componentModel = "spring")
public interface HostelRoomImageMapper {
    HostelRoomImage toHostelRoomImage(HostelRoomImageRequest imageRequest);
}
