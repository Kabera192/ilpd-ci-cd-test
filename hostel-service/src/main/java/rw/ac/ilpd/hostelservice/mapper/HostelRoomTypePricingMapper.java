package rw.ac.ilpd.hostelservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import rw.ac.ilpd.hostelservice.model.nosql.embedding.HostelRoomTypePricing;
import rw.ac.ilpd.hostelservice.util.DateFormatterUtil;
import rw.ac.ilpd.sharedlibrary.dto.hostel.HostelRoomTypePricingRequest;
import rw.ac.ilpd.sharedlibrary.dto.hostel.HostelRoomTypePricingResponse;

@Mapper(componentModel = "spring",uses = {DateFormatterUtil.class})
public interface HostelRoomTypePricingMapper {
    @Mapping(target = "pricingStatus" ,constant = "ACTIVE")
    HostelRoomTypePricing toHostelRoomTypePricing(HostelRoomTypePricingRequest pricingRequest);
    @Mapping(source = "pricing.createdAt", target = "createdAt", qualifiedByName = "formatDateTime")
    HostelRoomTypePricingResponse  toHostelRoomTypePricingResponse(HostelRoomTypePricing pricing);
}
