package rw.ac.ilpd.inventoryservice.mapper;

import org.springframework.stereotype.Component;
import rw.ac.ilpd.inventoryservice.model.sql.Item;
import rw.ac.ilpd.inventoryservice.model.sql.ItemGroup;
import rw.ac.ilpd.sharedlibrary.dto.inventory.ItemRequest;
import rw.ac.ilpd.sharedlibrary.dto.inventory.ItemResponse;
import rw.ac.ilpd.sharedlibrary.enums.ItemCategory;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class ItemMapper {
    public Item toItem(ItemRequest request, ItemGroup itemGroup) {
        return Item.builder()
                .name(request.getName())
                .acronym(request.getAcronym())
                .description(request.getDescription())
                .category(ItemCategory.valueOf(request.getCategory()))
                .itemGroup(itemGroup)
                .unitMeasure(request.getUnitMeasure())
                .depreciationRate(request.getDepreciationRate().doubleValue())
                .build();
    }

    public ItemResponse fromItem(Item item) {
        return ItemResponse.builder()
                .id(item.getId() != null ? item.getId().toString() : null)
                .name(item.getName())
                .acronym(item.getAcronym())
                .description(item.getDescription())
                .category(item.getCategory().name())
                .groupId(item.getItemGroup() != null ? item.getItemGroup().getId().toString() : null)
                .unitMeasure(item.getUnitMeasure())
                .depreciationRate(BigDecimal.valueOf(item.getDepreciationRate()))
                .remainingQuantity(item.getRemainingQuantity())
                .createdAt(item.getCreatedAt() != null ? item.getCreatedAt().toString() : null)
                .build();
    }

    public Item updateItem(Item existing, ItemRequest request, ItemGroup itemGroup) {
        existing.setName(request.getName());
        existing.setAcronym(request.getAcronym());
        existing.setDescription(request.getDescription());
        existing.setCategory(ItemCategory.valueOf(request.getCategory()));
        existing.setItemGroup(itemGroup);
        existing.setUnitMeasure(request.getUnitMeasure());
        existing.setDepreciationRate(request.getDepreciationRate().doubleValue());
        return existing;
    }
}