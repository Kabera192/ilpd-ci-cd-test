package rw.ac.ilpd.inventoryservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.inventoryservice.exception.EntityAlreadyExists;
import rw.ac.ilpd.inventoryservice.mapper.ItemMapper;
import rw.ac.ilpd.sharedlibrary.enums.ItemCategory;
import rw.ac.ilpd.inventoryservice.model.sql.Item;
import rw.ac.ilpd.inventoryservice.model.sql.ItemGroup;
import rw.ac.ilpd.inventoryservice.repository.sql.ItemRepository;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.dto.inventory.ItemRequest;
import rw.ac.ilpd.sharedlibrary.dto.inventory.ItemResponse;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final ItemGroupService itemGroupService;

    public PagedResponse<ItemResponse> getAll(int page, int size, String sortBy, String order) {
        Pageable pageable = order.equals("desc")
                ? PageRequest.of(page, size, Sort.by(sortBy).descending())
                : PageRequest.of(page, size, Sort.by(sortBy).ascending());

        Page<Item> itemPage = itemRepository.findAll(pageable);
        return new PagedResponse<>(
                itemPage.getContent().stream().map(itemMapper::fromItem).toList(),
                itemPage.getNumber(),
                itemPage.getSize(),
                itemPage.getTotalElements(),
                itemPage.getTotalPages(),
                itemPage.isLast()
        );
    }

    public ItemResponse get(UUID id) {
        return itemMapper.fromItem(getEntity(id).orElseThrow(() -> new EntityNotFoundException("Item not found")));
    }

    public ItemResponse create(ItemRequest request) {
        // Check if item with same name or acronym already exists
        if (itemRepository.existsByName(request.getName())) {
            throw new EntityAlreadyExists("Item with name " + request.getName() + " already exists");
        }
        if (itemRepository.existsByAcronym(request.getAcronym())) {
            throw new EntityAlreadyExists("Item with acronym " + request.getAcronym() + " already exists");
        }

        // Get the item group
        ItemGroup itemGroup = itemGroupService.getEntity(UUID.fromString(request.getGroupId()))
                .orElseThrow(() -> new EntityNotFoundException("Item group not found"));

        Item item = itemMapper.toItem(request, itemGroup);
        return itemMapper.fromItem(itemRepository.save(item));
    }

    public ItemResponse edit(UUID id, ItemRequest request) {
        Item existing = getEntity(id).orElseThrow(() -> new EntityNotFoundException("Item not found"));

        // Check if another item with the same name or acronym exists (excluding current one)
        if (itemRepository.existsByNameAndIdNot(request.getName(), id)) {
            throw new EntityAlreadyExists("Another item with name " + request.getName() + " already exists");
        }
        if (itemRepository.existsByAcronymAndIdNot(request.getAcronym(), id)) {
            throw new EntityAlreadyExists("Another item with acronym " + request.getAcronym() + " already exists");
        }

        // Get the item group
        ItemGroup itemGroup = itemGroupService.getEntity(UUID.fromString(request.getGroupId()))
                .orElseThrow(() -> new EntityNotFoundException("Item group not found"));

        Item updated = itemMapper.updateItem(existing, request, itemGroup);
        return itemMapper.fromItem(itemRepository.save(updated));
    }

    public ItemResponse patch(UUID id, Map<String, Object> updates) {
        Item existing = getEntity(id).orElseThrow(() -> new EntityNotFoundException("Item not found"));

        // Patch name
        if (updates.containsKey("name")) {
            String name = (String) updates.get("name");
            if (name != null && !name.isBlank()) {
                if (itemRepository.existsByNameAndIdNot(name, id)) {
                    throw new EntityAlreadyExists("Item with name " + name + " already exists");
                }
                existing.setName(name);
            }
        }

        // Patch acronym
        if (updates.containsKey("acronym")) {
            String acronym = (String) updates.get("acronym");
            if (acronym != null && !acronym.isBlank()) {
                if (itemRepository.existsByAcronymAndIdNot(acronym, id)) {
                    throw new EntityAlreadyExists("Item with acronym " + acronym + " already exists");
                }
                existing.setAcronym(acronym);
            }
        }

        // Patch description
        if (updates.containsKey("description")) {
            String description = (String) updates.get("description");
            if (description != null && !description.isBlank()) {
                existing.setDescription(description);
            }
        }

        // Patch category
        if (updates.containsKey("category")) {
            String category = (String) updates.get("category");
            if (category != null && !category.isBlank()) {
                existing.setCategory(ItemCategory.valueOf(category));
            }
        }

        // Patch groupId
        if (updates.containsKey("groupId")) {
            String groupId = (String) updates.get("groupId");
            if (groupId != null && !groupId.isBlank()) {
                ItemGroup itemGroup = itemGroupService.getEntity(UUID.fromString(groupId))
                        .orElseThrow(() -> new EntityNotFoundException("Item group not found"));
                existing.setItemGroup(itemGroup);
            }
        }

        // Patch unitMeasure
        if (updates.containsKey("unitMeasure")) {
            String unitMeasure = (String) updates.get("unitMeasure");
            if (unitMeasure != null && !unitMeasure.isBlank()) {
                existing.setUnitMeasure(unitMeasure);
            }
        }

        // Patch depreciationRate
        if (updates.containsKey("depreciationRate")) {
            Object rate = updates.get("depreciationRate");
            if (rate instanceof Number) {
                existing.setDepreciationRate(((Number) rate).doubleValue());
            }
        }

        return itemMapper.fromItem(itemRepository.save(existing));
    }

    public Boolean delete(UUID id) {
        Item item = getEntity(id).orElseThrow(() -> new EntityNotFoundException("Item not found"));
        itemRepository.delete(item);
        return true;
    }

    public Optional<Item> getEntity(UUID id) {
        return itemRepository.findById(id);
    }
}