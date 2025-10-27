package rw.ac.ilpd.academicservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.academicservice.exception.ConflictException;
import rw.ac.ilpd.academicservice.mapper.DeliberationRuleGroupCurriculumMapper;
import rw.ac.ilpd.academicservice.mapper.DeliberationRuleGroupMapper;
import rw.ac.ilpd.academicservice.mapper.DeliberationRulesThresholdMapper;
import rw.ac.ilpd.academicservice.model.nosql.document.DeliberationRuleGroup;
import rw.ac.ilpd.academicservice.repository.nosql.DeliberationRuleGroupRepository;
import rw.ac.ilpd.sharedlibrary.dto.deliberationrulegroup.DeliberationRuleGroupRequest;
import rw.ac.ilpd.sharedlibrary.dto.deliberationrulegroup.DeliberationRuleGroupResponse;
import rw.ac.ilpd.sharedlibrary.dto.deliberationrulegroupcurriculum.DeliberationRuleGroupCurriculumRequest;
import rw.ac.ilpd.sharedlibrary.dto.deliberationrulesthreshold.DeliberationRulesThresholdRequest;
import rw.ac.ilpd.academicservice.model.nosql.embedding.DeliberationRuleGroupCurriculum;
import rw.ac.ilpd.academicservice.model.nosql.embedding.DeliberationRulesThreshold;
import rw.ac.ilpd.sharedlibrary.enums.ValidityStatus;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeliberationRuleGroupService {

    private final DeliberationRuleGroupRepository repository;
    private final DeliberationRulesThresholdMapper thresholdMapper;
    private final CurriculumService curriculumService;

    public Optional<DeliberationRuleGroup> getEntity(String id) {
        return repository.findById(id);
    }

    public DeliberationRuleGroupResponse create(DeliberationRuleGroupRequest request) {
        var entity = DeliberationRuleGroupMapper.toDeliberationRuleGroup(request);
        String name = repository.findAll()
                .stream()
                .map(DeliberationRuleGroup::getName)
                .filter(x -> Objects.equals(x, request.getName()))
                .findFirst()
                .orElse(null);
        if(name != null)
            throw new ConflictException("Group with same name already exists");
        return DeliberationRuleGroupMapper.fromDeliberationRuleGroup(repository.save(entity));
    }

    public DeliberationRuleGroupResponse getById(String id) {
        return getEntity(id)
                .map(DeliberationRuleGroupMapper::fromDeliberationRuleGroup)
                .orElseThrow(() -> new RuntimeException("Deliberation rule group not found"));
    }

    public List<DeliberationRuleGroupResponse> getAll(String status) {
        return repository.findByStatus(ValidityStatus.valueOf(status)).stream()
                .map(DeliberationRuleGroupMapper::fromDeliberationRuleGroup)
                .collect(Collectors.toList());
    }

    public DeliberationRuleGroupResponse update(String id, DeliberationRuleGroupRequest request) {
        var group = getEntity(id)
                .orElseThrow(() -> new RuntimeException("Group not found"));
        group.setName(request.getName());
        group.setStatus(request.getStatus());
        return DeliberationRuleGroupMapper.fromDeliberationRuleGroup(repository.save(group));
    }

    public void delete(String id)  {
        getEntity(id).orElseThrow(()-> new EntityNotFoundException(("Deliberation group not found")));
        repository.deleteById(id);
    }

    // DeliberationRuleGroupCurriculum
    public DeliberationRuleGroupResponse addDeliberationRuleGroupCurriculum(String groupId, DeliberationRuleGroupCurriculumRequest request) {
        var group = getEntity(groupId).orElseThrow(() -> new EntityNotFoundException("Group not found"));
        curriculumService.getEntity(UUID.fromString(request.getCurriculumId()))
                .orElseThrow(() -> new EntityNotFoundException("Curriculum not found"));

        DeliberationRuleGroupCurriculum newCurriculum = DeliberationRuleGroupCurriculumMapper.toDeliberationRuleGroupCurriculum(request);

        if (group.getCurriculumList() == null) {
            group.setCurriculumList(new ArrayList<>());
        }

        boolean alreadyExists = group.getCurriculumList().stream()
                .anyMatch(c -> c.getCurriculumId().toString().equals(request.getCurriculumId()));

        if (alreadyExists) {
            throw new IllegalArgumentException("Curriculum is already assigned to this group");
        }

        group.getCurriculumList().add(newCurriculum);
        return DeliberationRuleGroupMapper.fromDeliberationRuleGroup(repository.save(group));
    }


    public void removeDeliberationRuleGroupCurriculum(String groupId, String curriculumId) {
        var group = getEntity(groupId).orElseThrow(() -> new RuntimeException("Group not found"));
        if (group.getCurriculumList() != null) {
            group.getCurriculumList().removeIf(c -> c.getCurriculumId().toString().equals(curriculumId));
        }
        repository.save(group);
    }

    // DeliberationRulesThreshold
    public DeliberationRuleGroupResponse addDeliberationRulesThreshold(String groupId, DeliberationRulesThresholdRequest request) {
        var group = getEntity(groupId).orElseThrow(() -> new RuntimeException("Group not found"));
        DeliberationRulesThreshold newThreshold = thresholdMapper.toDeliberationRulesThreshold(request);

        if (group.getThresholdList() == null) {
            group.setThresholdList(new ArrayList<>());
        }

        boolean alreadyExists = group.getThresholdList().stream()
                .anyMatch(t -> t.getKey().equalsIgnoreCase(newThreshold.getKey()));

        if (alreadyExists) {
            throw new IllegalArgumentException("Threshold with the same key already exists");
        }

        group.getThresholdList().add(newThreshold);
        return DeliberationRuleGroupMapper.fromDeliberationRuleGroup(repository.save(group));
    }


    public void removeDeliberationRulesThreshold(String groupId, String thresholdId) {
        var group = getEntity(groupId).orElseThrow(() -> new RuntimeException("Group not found"));
        var x = group.getThresholdList().stream()
                .filter(t -> t.getId().equals(thresholdId))
                .findFirst();
        if(x.isEmpty())
            throw new EntityNotFoundException("Threshold not found!");
        if (group.getThresholdList() != null) {
            group.getThresholdList().removeIf(t -> t.getId().equals(thresholdId));
        }
        repository.save(group);
    }
}
