package rw.ac.ilpd.notificationservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import rw.ac.ilpd.notificationservice.model.nosql.embedding.UserRequestApproval;
import rw.ac.ilpd.sharedlibrary.dto.request.UserRequestApprovalRequest;
import rw.ac.ilpd.sharedlibrary.dto.request.UserRequestApprovalResponse;

@Mapper(componentModel = "spring")
public interface UserRequestApprovalMapper
{
    @Mapping(target = "createdAt", ignore = true)
    UserRequestApproval toUserRequestApproval(UserRequestApprovalRequest userRequestApprovalRequest);

    UserRequestApprovalResponse fromUserRequestApproval(UserRequestApproval userRequestApproval);
}
