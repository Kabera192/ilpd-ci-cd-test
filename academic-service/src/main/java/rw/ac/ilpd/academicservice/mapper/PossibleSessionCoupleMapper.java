package rw.ac.ilpd.academicservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import rw.ac.ilpd.academicservice.model.sql.PossibleSessionCouple;
import rw.ac.ilpd.academicservice.model.sql.StudyModeSession;
import rw.ac.ilpd.sharedlibrary.dto.possiblesessioncouple.PossibleSessionCoupleRequest;
import rw.ac.ilpd.sharedlibrary.dto.possiblesessioncouple.PossibleSessionCoupleResponse;
import rw.ac.ilpd.sharedlibrary.dto.studymodesession.StudyModeSessionResponse;

/**
 * Mapper interface for converting between PossibleSessionCouple entities and DTOs using MapStruct.
 */
@Mapper(componentModel = "spring")
public abstract class PossibleSessionCoupleMapper
{
    /**
     * Converts a PossibleSessionCoupleRequest to a PossibleSessionCouple entity.
     *
     * @param request  The PossibleSessionCoupleRequest DTO.
     * @param session1 The StudyModeSession entity for session1Id.
     * @param session2 The StudyModeSession entity for session2Id.
     * @return PossibleSessionCouple entity.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deletedStatus", ignore = true)
    @Mapping(target = "session1Id", source = "session1")
    @Mapping(target = "session2Id", source = "session2")
    public abstract PossibleSessionCouple toPossibleSessionCouple(PossibleSessionCoupleRequest request,
                                                   StudyModeSession session1, StudyModeSession session2);

    /**
     * Converts a PossibleSessionCouple entity to a PossibleSessionCoupleResponse DTO.
     *
     * @param sessionCouple The PossibleSessionCouple entity.
     * @return PossibleSessionCoupleResponse DTO.
     */
    @Mapping(target = "id", expression = "java(sessionCouple.getId().toString())")
    @Mapping(target = "session1Id", source = "session1Id", qualifiedByName = "studyModeSessionToResponse")
    @Mapping(target = "session2Id", source = "session2Id", qualifiedByName = "studyModeSessionToResponse")
    public abstract PossibleSessionCoupleResponse fromPossibleSessionCouple(PossibleSessionCouple sessionCouple);

    /**
     * Converts a StudyModeSession entity to a StudyModeSessionResponse DTO.
     *
     * @param session The StudyModeSession entity.
     * @return StudyModeSessionResponse DTO.
     */
    @Named("studyModeSessionToResponse")
    protected abstract StudyModeSessionResponse studyModeSessionToResponse(StudyModeSession session);
}