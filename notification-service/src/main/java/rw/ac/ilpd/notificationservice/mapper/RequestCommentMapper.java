package rw.ac.ilpd.notificationservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import rw.ac.ilpd.notificationservice.model.nosql.embedding.RequestComment;
import rw.ac.ilpd.sharedlibrary.dto.request.RequestCommentRequest;
import rw.ac.ilpd.sharedlibrary.dto.request.RequestCommentResponse;

@Mapper(componentModel = "spring")
public interface RequestCommentMapper
{
    @Mapping(target = "createdAt", ignore = true)
    RequestComment toRequestComment(RequestCommentRequest requestCommentRequest);

    RequestCommentResponse fromRequestComment(RequestComment requestComment);
}
