package rw.ac.ilpd.academicservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.academicservice.model.nosql.document.DeliberationDistinctionGroup;
import rw.ac.ilpd.academicservice.model.nosql.embedding.DeliberationDistinction;
import rw.ac.ilpd.academicservice.service.DeliberationDistinctionGroupService;
import rw.ac.ilpd.sharedlibrary.dto.deliberationdistinction.DeliberationDistinctionGroupRequest;

import java.util.List;

@RestController
@RequestMapping("/distinction-groups")
@RequiredArgsConstructor
public class DeliberationDistinctionGroupController {
    private final DeliberationDistinctionGroupService service;

    @PostMapping("/save-list")
    public ResponseEntity<DeliberationDistinctionGroup> saveListOfDeliberationDistinctionGroup(@RequestBody DeliberationDistinctionGroupRequest request) {
        return service.saveListOfDeliberationDistinctionGroup(request);
    }

    @GetMapping("/all")
    public ResponseEntity<List<DeliberationDistinctionGroup>> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliberationDistinctionGroup> getById(@PathVariable String id) {
        return service.getDeliberationDistinctionGroup(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeliberationDistinctionGroup> update(@PathVariable String id,
                                                               @RequestBody DeliberationDistinctionGroupRequest request) {
        return  service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{groupId}/distinctions/{distinctionId}")
    public ResponseEntity<DeliberationDistinction> getEmbeddedDistinction(@PathVariable String groupId,
                                                                          @PathVariable String distinctionId) {
        return service.findEmbeddedDistinction(groupId, distinctionId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{groupId}/distinctions/{distinctionId}")
    public ResponseEntity<Void> deleteEmbeddedDistinction(@PathVariable String groupId,
                                                          @PathVariable String distinctionId) {
        service.removeEmbeddedDistinction(groupId, distinctionId);
        return ResponseEntity.noContent().build();
    }


}
