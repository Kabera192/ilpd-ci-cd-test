package rw.ac.ilpd.academicservice.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import rw.ac.ilpd.academicservice.model.sql.StudyMode;
import rw.ac.ilpd.sharedlibrary.dto.studymode.StudyModeRequest;
import rw.ac.ilpd.sharedlibrary.dto.studymode.StudyModeResponse;

@Component
public class StudyModeMapper {

    public StudyMode toStudyMode(StudyModeRequest request)
    {
        return StudyMode.builder()
                .name(request.getName())
                .acronym(request.getAcronym())
                .build();
    }

    public StudyModeResponse fromStudyMode(StudyMode studyMode)
    {
        return StudyModeResponse.builder()
                .id(studyMode.getId().toString())
                .name(studyMode.getName())
                .acronym(studyMode.getAcronym())
                .createdAt(studyMode.getCreatedAt().toString())
                .build();
    }
}
