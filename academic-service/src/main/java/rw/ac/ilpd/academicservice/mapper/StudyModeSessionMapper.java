package rw.ac.ilpd.academicservice.mapper;

import org.springframework.stereotype.Component;
import rw.ac.ilpd.academicservice.model.sql.AssessmentGroup;
import rw.ac.ilpd.academicservice.model.sql.StudyMode;
import rw.ac.ilpd.academicservice.model.sql.StudyModeSession;
import rw.ac.ilpd.sharedlibrary.dto.studymodesession.StudyModeSessionRequest;
import rw.ac.ilpd.sharedlibrary.dto.studymodesession.StudyModeSessionResponse;

@Component
public class StudyModeSessionMapper {

    public StudyModeSession toStudyModeSession(
            StudyModeSessionRequest request,
            StudyMode studyMode,
            AssessmentGroup assessmentGroup
    ) {
        return StudyModeSession.builder()
                .name(request.getName())
                .studyMode(studyMode)
                .startingDay(request.getStartingDay())
                .endingDay(request.getEndingDay())
                .assessmentGroup(assessmentGroup)
                .build();
    }

    public StudyModeSessionResponse fromStudyModeSession(StudyModeSession entity) {
        if (entity == null)
            return null;
        return StudyModeSessionResponse.builder()
                .id(entity.getId().toString())
                .name(entity.getName())
                .studyModeId(entity.getStudyMode().getId().toString())
                .startingDay(entity.getStartingDay())
                .endingDay(entity.getEndingDay())
                .createdAt(entity.getCreatedAt().toString())
                .assessmentGroupId(entity.getAssessmentGroup().getId().toString())
                .build();
    }
}
