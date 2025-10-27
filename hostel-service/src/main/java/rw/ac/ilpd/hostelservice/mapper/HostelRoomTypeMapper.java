package rw.ac.ilpd.hostelservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import rw.ac.ilpd.hostelservice.model.nosql.document.HostelRoomType;
import rw.ac.ilpd.hostelservice.util.DateFormatterUtil;
import rw.ac.ilpd.sharedlibrary.dto.hostel.HostelRoomTypeRequest;
import rw.ac.ilpd.sharedlibrary.dto.hostel.HostelRoomTypeResponse;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,uses = {DateFormatterUtil.class,HostelRoomTypePricingMapper.class})
public interface HostelRoomTypeMapper {
    HostelRoomType toHostelRoomType(HostelRoomTypeRequest request);
    HostelRoomTypeResponse fromHostelRoomType(HostelRoomType savedHostelRoomType);

    HostelRoomType toHostelRoomTypeUpdate(@MappingTarget HostelRoomType existingHostelRoomType, HostelRoomTypeRequest request);
}
