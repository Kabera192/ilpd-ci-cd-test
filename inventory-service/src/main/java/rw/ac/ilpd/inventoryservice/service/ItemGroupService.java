package rw.ac.ilpd.inventoryservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.inventoryservice.exception.EntityAlreadyExists;
import rw.ac.ilpd.inventoryservice.mapper.ItemGroupMapper;
import rw.ac.ilpd.inventoryservice.model.sql.ItemGroup;
import rw.ac.ilpd.inventoryservice.repository.sql.ItemGroupRepository;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.inventory.ItemGroupRequest;
import rw.ac.ilpd.sharedlibrary.dto.inventory.ItemGroupResponse;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemGroupService {
    private final ItemGroupRepository itemGroupRepository;
    private final ItemGroupMapper itemGroupMapper;

    public PagedResponse<ItemGroupResponse> getAll(int page, int size, String sortBy, String order) {
        Pageable pageable;
        if (order.equals("desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        }

        Page<ItemGroup> itemGroupPage = itemGroupRepository.findAll(pageable);
        return new PagedResponse<>(
                itemGroupPage.getContent().stream().map(itemGroupMapper::fromItemGroup).toList(),
                itemGroupPage.getNumber(),
                itemGroupPage.getSize(),
                itemGroupPage.getTotalElements(),
                itemGroupPage.getTotalPages(),
                itemGroupPage.isLast()
        );
    }

    public ItemGroupResponse get(UUID id) {
        return itemGroupMapper.fromItemGroup(getEntity(id).orElseThrow(() -> new EntityNotFoundException("Item group not found")));
    }

    public ItemGroupResponse create(ItemGroupRequest request) {
        // Check if item group with same name or acronym already exists
        if (itemGroupRepository.existsByName(request.getName())) {
            throw new EntityAlreadyExists("Item group with name " + request.getName() + " already exists");
        }
        if (itemGroupRepository.existsByAcronym(request.getAcronym())) {
            throw new EntityAlreadyExists("Item group with acronym " + request.getAcronym() + " already exists");
        }

        ItemGroup itemGroup = itemGroupMapper.toItemGroup(request);
        return itemGroupMapper.fromItemGroup(itemGroupRepository.save(itemGroup));
    }

    public ItemGroupResponse edit(UUID id, ItemGroupRequest request) {
        ItemGroup existing = getEntity(id).orElseThrow(() -> new EntityNotFoundException("Item group not found"));

        // Check if another item group with the same name or acronym exists (excluding current one)
        Optional<ItemGroup> sameName = itemGroupRepository.findByNameAndIdNot(request.getName(), id);
        if (sameName.isPresent()) {
            throw new EntityAlreadyExists("Another item group with name " + request.getName() + " already exists");
        }

        Optional<ItemGroup> sameAcronym = itemGroupRepository.findByAcronymAndIdNot(request.getAcronym(), id);
        if (sameAcronym.isPresent()) {
            throw new EntityAlreadyExists("Another item group with acronym " + request.getAcronym() + " already exists");
        }

        ItemGroup updated = itemGroupMapper.updateItemGroup(existing, request);
        return itemGroupMapper.fromItemGroup(itemGroupRepository.save(updated));
    }

    public ItemGroupResponse patch(UUID id, Map<String, Object> updates) {
        ItemGroup existing = getEntity(id).orElseThrow(() -> new EntityNotFoundException("Item group not found"));

        // Patch name
        if (updates.containsKey("name")) {
            Object value = updates.get("name");
            if (value instanceof String name && !name.isBlank()) {
                // Check if another item group with this name exists
                if (itemGroupRepository.existsByNameAndIdNot(name, id)) {
                    throw new EntityAlreadyExists("Item group with name " + name + " already exists");
                }
                existing.setName(name);
            }
        }

        // Patch acronym
        if (updates.containsKey("acronym")) {
            Object value = updates.get("acronym");
            if (value instanceof String acronym && !acronym.isBlank()) {
                // Check if another item group with this acronym exists
                if (itemGroupRepository.existsByAcronymAndIdNot(acronym, id)) {
                    throw new EntityAlreadyExists("Item group with acronym " + acronym + " already exists");
                }
                existing.setAcronym(acronym);
            }
        }

        // Patch description
        if (updates.containsKey("description")) {
            Object value = updates.get("description");
            if (value instanceof String description && !description.isBlank()) {
                existing.setDescription(description);
            }
        }

        // createdAt should NOT be patched (auto-generated)
        return itemGroupMapper.fromItemGroup(itemGroupRepository.save(existing));
    }

    public Boolean delete(UUID id) {
        ItemGroup itemGroup = getEntity(id).orElseThrow(() -> new EntityNotFoundException("Item group not found"));
        itemGroupRepository.delete(itemGroup);
        return true;
    }

    public Optional<ItemGroup> getEntity(UUID id) {
        return itemGroupRepository.findById(id);
    }
}