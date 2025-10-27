/*
 * File: RequestMapper.java
 *
 * Description: Mapper interface for converting between Request entity and DTOs.
 *              Uses MapStruct for automated mapping between Request, RequestRequest, and RequestResponse.
 *              Includes mapping for embedded lists (comments, attachmentDocuments, requestApprovals).
 */
package rw.ac.ilpd.notificationservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import rw.ac.ilpd.notificationservice.model.nosql.document.Request;
import rw.ac.ilpd.sharedlibrary.dto.request.RequestRequest;
import rw.ac.ilpd.sharedlibrary.dto.request.RequestResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RequestMapper
{
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    Request toRequest(RequestRequest request);

    RequestResponse fromRequest(Request entity);
}