package rw.ac.ilpd.academicservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import rw.ac.ilpd.academicservice.model.sql.ProhibitedTransferCouple;
import rw.ac.ilpd.academicservice.model.sql.StudyModeSession;
import rw.ac.ilpd.academicservice.util.DateMapperFormatter;
import rw.ac.ilpd.sharedlibrary.dto.prohibitedtransfercouple.ProhibitedTransferCoupleRequest;
import rw.ac.ilpd.sharedlibrary.dto.prohibitedtransfercouple.ProhibitedTransferCoupleResponse;

@Mapper(componentModel = "spring",uses = {DateMapperFormatter.class})
public interface ProhibitedTransferCoupleMapper {

            @Mapping(source = "sms1",target ="fromStudyModeSession" )
            @Mapping(source = "sms2",target ="toStudyModeSession" )
            @Mapping(target = "deletedStatus",constant = "false")
            @Mapping(target = "createdAt",ignore = true)
            @Mapping(target = "updatedAt",ignore = true)
            @Mapping(target = "id",ignore = true)
    ProhibitedTransferCouple toProhibitedTransferCouple(StudyModeSession sms1, StudyModeSession sms2);

            @Mapping(source = "fromStudyModeSession.id", target = "fromStudyModeSessionId")
            @Mapping(source = "toStudyModeSession.id", target = "toStudyModeSessionId")
            @Mapping(target = "createdAt",qualifiedByName = "formatDateTime")
            @Mapping(target = "updatedAt",qualifiedByName = "formatDateTime")
            @Mapping(target = "deletedAt",qualifiedByName = "formatDateTime")
            @Mapping(target = "id", expression = "java(ptcSaved.getId() != null ? ptcSaved.getId().toString() : null)")
    ProhibitedTransferCoupleResponse fromProhibitedTransferCouple(ProhibitedTransferCouple ptcSaved);

            @Mapping(source = "sms1",target ="fromStudyModeSession" )
            @Mapping(source = "sms2",target ="toStudyModeSession" )
            @Mapping(target = "deletedStatus",constant = "false")
            @Mapping(target = "id",ignore = true)
            @Mapping(target = "createdAt",ignore = true)
            @Mapping(target = "updatedAt",ignore = true)
    ProhibitedTransferCouple toProhibitedTransferCoupleUpdate(@MappingTarget ProhibitedTransferCouple ptc, StudyModeSession sms1, StudyModeSession sms2);
}
