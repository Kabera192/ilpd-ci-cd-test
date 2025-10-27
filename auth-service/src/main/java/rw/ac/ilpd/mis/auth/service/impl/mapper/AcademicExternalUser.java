package rw.ac.ilpd.mis.auth.service.impl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import rw.ac.ilpd.mis.auth.entity.mongo.ExternalUserEntity;
import rw.ac.ilpd.mis.shared.dto.user.AcademicExternalUserRequest;
import rw.ac.ilpd.mis.shared.dto.user.AcademicExternalUserResponse;
import rw.ac.ilpd.mis.shared.enums.Relationship;

@Mapper(componentModel = "spring")
public interface AcademicExternalUser {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "relationShip", source = "relationship")
    @Mapping(target = "type",source = "request.externalUserType")
    ExternalUserEntity toExternalUser(AcademicExternalUserRequest request);

    @Mapping(target = "relationship", source = "relationShip")
    @Mapping(target = "externalUserType",source = "entity.type")
    @Mapping(target = "recommendationFileId",source = "entity.documentId")
    AcademicExternalUserResponse toNextKinResponse(ExternalUserEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "relationShip", source = "relationship")
    @Mapping(target = "type",source = "request.externalUserType")
    ExternalUserEntity toExternalUserFromUpdate(@MappingTarget ExternalUserEntity externalUserRequest, AcademicExternalUserRequest request);

//    @Named("stringToRelationship")
//    default Relationship stringToRelationship(String relationship) {
//        return relationship != null ? Relationship.valueOf(relationship) : null;
//    }
//
//    @Named("relationshipToString")
//    default String relationshipToString(Relationship relationship) {
//        return relationship != null ? relationship.name() : null;
//    }
}