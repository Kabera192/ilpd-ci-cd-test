/*
 * File: RequestTypeMapper.java
 *
 * Description: Mapper interface for converting between RequestType entity and DTOs.
 *              Uses MapStruct for automated mapping between RequestType, RequestTypeRequest, and RequestTypeResponse.
 *              Includes mapping for embedded roles list.
 */
package rw.ac.ilpd.notificationservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import rw.ac.ilpd.notificationservice.model.nosql.document.RequestType;
import rw.ac.ilpd.sharedlibrary.dto.request.RequestTypeRequest;
import rw.ac.ilpd.sharedlibrary.dto.request.RequestTypeResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RequestTypeMapper
{
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deletedStatus", ignore = true)
    RequestType toRequestType(RequestTypeRequest request);

    RequestTypeResponse fromRequestType(RequestType entity);
}