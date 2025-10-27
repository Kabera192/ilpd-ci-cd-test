package rw.ac.ilpd.academicservice.mapper;


import rw.ac.ilpd.sharedlibrary.dto.deliberationrulegroup.DeliberationRuleGroupRequest;
import rw.ac.ilpd.sharedlibrary.dto.deliberationrulegroup.DeliberationRuleGroupResponse;
import rw.ac.ilpd.academicservice.model.nosql.document.DeliberationRuleGroup;
import java.util.Collections;

public class DeliberationRuleGroupMapper {

    public static DeliberationRuleGroup toDeliberationRuleGroup(DeliberationRuleGroupRequest request) {
        DeliberationRuleGroup entity = new DeliberationRuleGroup();
        entity.setName(request.getName());
        entity.setStatus(request.getStatus());

        return entity;
    }

    public static DeliberationRuleGroupResponse fromDeliberationRuleGroup(DeliberationRuleGroup entity) {
        return DeliberationRuleGroupResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .status(entity.getStatus().name())
                .createdAt(entity.getCreatedAt() != null ? entity.getCreatedAt().toString() : null)
                .curriculumList(entity.getCurriculumList() != null ?
                        entity.getCurriculumList().stream()
                                .map(DeliberationRuleGroupCurriculumMapper::fromDeliberationRuleGroupCurriculum)
                                 .toList() : Collections.emptyList())
                .thresholdList(entity.getThresholdList() != null ?
                        entity.getThresholdList().stream()
                                .map(x -> (new DeliberationRulesThresholdMapper()).fromDeliberationRulesThreshold(x))
                                .toList() : Collections.emptyList())
                .build();
    }
}

