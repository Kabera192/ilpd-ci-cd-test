package rw.ac.ilpd.academicservice.mapper;

import rw.ac.ilpd.sharedlibrary.dto.deliberationrulegroupcurriculum.DeliberationRuleGroupCurriculumRequest;
import rw.ac.ilpd.sharedlibrary.dto.deliberationrulegroupcurriculum.DeliberationRuleGroupCurriculumResponse;
import rw.ac.ilpd.academicservice.model.nosql.embedding.DeliberationRuleGroupCurriculum;
import rw.ac.ilpd.sharedlibrary.enums.ValidityStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class DeliberationRuleGroupCurriculumMapper {
    public static DeliberationRuleGroupCurriculum toDeliberationRuleGroupCurriculum(DeliberationRuleGroupCurriculumRequest request) {
        DeliberationRuleGroupCurriculum entity = new DeliberationRuleGroupCurriculum();
        entity.setId(UUID.randomUUID().toString());
        entity.setCurriculumId(UUID.fromString(request.getCurriculumId()));
        entity.setStatus(ValidityStatus.valueOf(request.getStatus()));
        entity.setCreatedAt(LocalDateTime.now());
        return entity;
    }

    public static DeliberationRuleGroupCurriculumResponse fromDeliberationRuleGroupCurriculum(DeliberationRuleGroupCurriculum entity) {
        return DeliberationRuleGroupCurriculumResponse.builder()
                .id(entity.getId())
                .curriculumId(entity.getCurriculumId().toString())
                .status(entity.getStatus().name())
                .createdAt(entity.getCreatedAt() != null ? entity.getCreatedAt().toString() : null)
                .build();
    }
}

