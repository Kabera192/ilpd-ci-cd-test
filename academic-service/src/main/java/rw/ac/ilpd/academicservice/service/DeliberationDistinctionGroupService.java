package rw.ac.ilpd.academicservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.academicservice.mapper.DeliberationDistinctionGroupMapper;
import rw.ac.ilpd.academicservice.mapper.DeliberationDistinctionMapper;
import rw.ac.ilpd.academicservice.model.nosql.document.DeliberationDistinctionGroup;
import rw.ac.ilpd.academicservice.model.nosql.embedding.DeliberationDistinction;
import rw.ac.ilpd.academicservice.repository.nosql.DeliberationDistinctionGroupRepository;
import rw.ac.ilpd.sharedlibrary.dto.deliberationdistinction.DeliberationDistinctionGroupRequest;
import rw.ac.ilpd.sharedlibrary.dto.deliberationdistinction.DeliberationDistinctionRequest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeliberationDistinctionGroupService {
    private final DeliberationDistinctionGroupRepository repository;
    private final DeliberationDistinctionMapper mapper;
    private final DeliberationDistinctionGroupMapper groupMapper;

    public ResponseEntity<DeliberationDistinctionGroup> saveListOfDeliberationDistinctionGroup(DeliberationDistinctionGroupRequest request) {
        List<DeliberationDistinctionRequest> distinctions = request.getDistinctions();
        DeliberationDistinctionGroup group = groupMapper.toDeliberationDistinctionGroup(request,distinctions);

        return new ResponseEntity<>(repository.save(group), HttpStatus.CREATED);
    }

    public ResponseEntity<List<DeliberationDistinctionGroup>> getAll() {
        return new ResponseEntity<>(repository.findAll(),HttpStatus.OK);
    }

    public Optional<DeliberationDistinctionGroup> getById(String id) {
        return repository.findById(id);
    }
public ResponseEntity<DeliberationDistinctionGroup> getDeliberationDistinctionGroup(String id) {
        return  getById(id).map(ResponseEntity::ok)
            .orElseThrow(()->new EntityNotFoundException("Deliberation distinction group not found"));
}
    public void delete(String id) {
        repository.deleteById(id);
    }

    public ResponseEntity<DeliberationDistinctionGroup> update(String id, DeliberationDistinctionGroupRequest request) {
        return new ResponseEntity<>(repository.findById(id).map(existing -> {
            existing.setName(request.getName());
            existing.setStatus(request.getStatus());
            existing.setDeliberationDistinctions(request.getDistinctions().stream().map(dist ->
                    DeliberationDistinction.builder()
                            .name(dist.getName())
                            .minScore(dist.getMinScore())
                            .maxScore(dist.getMaxScore())
                            .build()
            ).collect(Collectors.toList()));
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Group not found with id " + id)),HttpStatus.OK);
    }

    public Optional<DeliberationDistinction> findEmbeddedDistinction(String groupId, String distinctionId) {
        return repository.findById(groupId)
                .flatMap(group -> group.getDeliberationDistinctions()
                        .stream()
                        .filter(d -> d.getId().equals(distinctionId))
                        .findFirst());
    }

    public DeliberationDistinctionGroup removeEmbeddedDistinction(String groupId, String distinctionId) {
        return repository.findById(groupId).map(group -> {
            boolean removed = group.getDeliberationDistinctions().removeIf(d -> d.getId().equals(distinctionId));
            if (!removed) {
                throw new RuntimeException("Distinction not found in group");
            }
            return repository.save(group);
        }).orElseThrow(() -> new RuntimeException("Group not found"));
    }

}

