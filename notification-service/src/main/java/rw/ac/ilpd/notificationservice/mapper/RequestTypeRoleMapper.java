package rw.ac.ilpd.notificationservice.mapper;

import org.mapstruct.Mapper;
import rw.ac.ilpd.notificationservice.model.nosql.embedding.RequestTypeRole;
import rw.ac.ilpd.sharedlibrary.dto.request.RequestTypeRoleRequest;
import rw.ac.ilpd.sharedlibrary.dto.request.RequestTypeRoleResponse;

@Mapper(componentModel = "spring")
public interface RequestTypeRoleMapper
{
    RequestTypeRole toRequestTypeRole(RequestTypeRoleRequest requestTypeRoleRequest);

    RequestTypeRoleResponse fromRequestTypeRole(RequestTypeRole requestTypeRole);
}