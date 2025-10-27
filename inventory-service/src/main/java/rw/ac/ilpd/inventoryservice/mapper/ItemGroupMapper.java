package rw.ac.ilpd.inventoryservice.mapper;

import org.springframework.stereotype.Component;
import rw.ac.ilpd.inventoryservice.model.sql.ItemGroup;
import rw.ac.ilpd.sharedlibrary.dto.inventory.ItemGroupRequest;
import rw.ac.ilpd.sharedlibrary.dto.inventory.ItemGroupResponse;

import java.time.LocalDateTime;

@Component
public class ItemGroupMapper {
    public ItemGroup toItemGroup(ItemGroupRequest request) {
        return ItemGroup.builder()
                .name(request.getName())
                .acronym(request.getAcronym())
                .description(request.getDescription())
                .build();
    }

    public ItemGroupResponse fromItemGroup(ItemGroup itemGroup) {
        return ItemGroupResponse.builder()
                .id(itemGroup.getId() != null ? itemGroup.getId().toString() : null)
                .name(itemGroup.getName())
                .acronym(itemGroup.getAcronym())
                .description(itemGroup.getDescription())
                .createdAt(itemGroup.getCreatedAt() != null ? itemGroup.getCreatedAt().toString() : null)
                .build();
    }

    public ItemGroup updateItemGroup(ItemGroup existing, ItemGroupRequest request) {
        existing.setName(request.getName());
        existing.setAcronym(request.getAcronym());
        existing.setDescription(request.getDescription());
        return existing;
    }
}