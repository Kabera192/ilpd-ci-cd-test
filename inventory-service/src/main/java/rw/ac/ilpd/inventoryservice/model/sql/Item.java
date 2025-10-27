package rw.ac.ilpd.inventoryservice.model.sql;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;
import rw.ac.ilpd.sharedlibrary.enums.ItemCategory;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * This entity represents an item inside or outside of the stoch
 */
@Entity
@Table(name = "inv_items")
public class Item {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private UUID id;

    private String name;

    private String acronym;

    private String description;

    @Enumerated(EnumType.STRING)
    private ItemCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    private ItemGroup itemGroup;

    private String unitMeasure;

    private Double depreciationRate;
    private Integer remainingQuantity = 0;

    @CreationTimestamp
    private LocalDateTime createdAt;


    public Item(UUID id, String name, String acronym, String description, ItemCategory category, ItemGroup itemGroup, String unitMeasure, Double depreciationRate, Integer remainingQuantity, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.acronym = acronym;
        this.description = description;
        this.category = category;
        this.itemGroup = itemGroup;
        this.unitMeasure = unitMeasure;
        this.depreciationRate = depreciationRate;
        this.remainingQuantity = remainingQuantity;
        this.createdAt = createdAt;
    }

    public Item() {
    }

    public static ItemBuilder builder() {
        return new ItemBuilder();
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getAcronym() {
        return this.acronym;
    }

    public String getDescription() {
        return this.description;
    }

    public ItemCategory getCategory() {
        return this.category;
    }

    public ItemGroup getItemGroup() {
        return this.itemGroup;
    }

    public String getUnitMeasure() {
        return this.unitMeasure;
    }

    public Double getDepreciationRate() {
        return this.depreciationRate;
    }

    public Integer getRemainingQuantity() {
        return this.remainingQuantity;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(ItemCategory category) {
        this.category = category;
    }

    public void setItemGroup(ItemGroup itemGroup) {
        this.itemGroup = itemGroup;
    }

    public void setUnitMeasure(String unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    public void setDepreciationRate(Double depreciationRate) {
        this.depreciationRate = depreciationRate;
    }

    public void setRemainingQuantity(Integer remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Item)) return false;
        final Item other = (Item) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$acronym = this.getAcronym();
        final Object other$acronym = other.getAcronym();
        if (this$acronym == null ? other$acronym != null : !this$acronym.equals(other$acronym)) return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description))
            return false;
        final Object this$category = this.getCategory();
        final Object other$category = other.getCategory();
        if (this$category == null ? other$category != null : !this$category.equals(other$category)) return false;
        final Object this$itemGroup = this.getItemGroup();
        final Object other$itemGroup = other.getItemGroup();
        if (this$itemGroup == null ? other$itemGroup != null : !this$itemGroup.equals(other$itemGroup)) return false;
        final Object this$unitMeasure = this.getUnitMeasure();
        final Object other$unitMeasure = other.getUnitMeasure();
        if (this$unitMeasure == null ? other$unitMeasure != null : !this$unitMeasure.equals(other$unitMeasure))
            return false;
        final Object this$depreciationRate = this.getDepreciationRate();
        final Object other$depreciationRate = other.getDepreciationRate();
        if (this$depreciationRate == null ? other$depreciationRate != null : !this$depreciationRate.equals(other$depreciationRate))
            return false;
        final Object this$remainingQuantity = this.getRemainingQuantity();
        final Object other$remainingQuantity = other.getRemainingQuantity();
        if (this$remainingQuantity == null ? other$remainingQuantity != null : !this$remainingQuantity.equals(other$remainingQuantity))
            return false;
        final Object this$createdAt = this.getCreatedAt();
        final Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Item;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $acronym = this.getAcronym();
        result = result * PRIME + ($acronym == null ? 43 : $acronym.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        final Object $category = this.getCategory();
        result = result * PRIME + ($category == null ? 43 : $category.hashCode());
        final Object $itemGroup = this.getItemGroup();
        result = result * PRIME + ($itemGroup == null ? 43 : $itemGroup.hashCode());
        final Object $unitMeasure = this.getUnitMeasure();
        result = result * PRIME + ($unitMeasure == null ? 43 : $unitMeasure.hashCode());
        final Object $depreciationRate = this.getDepreciationRate();
        result = result * PRIME + ($depreciationRate == null ? 43 : $depreciationRate.hashCode());
        final Object $remainingQuantity = this.getRemainingQuantity();
        result = result * PRIME + ($remainingQuantity == null ? 43 : $remainingQuantity.hashCode());
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        return result;
    }

    public String toString() {
        return "Item(id=" + this.getId() + ", name=" + this.getName() + ", acronym=" + this.getAcronym() + ", description=" + this.getDescription() + ", category=" + this.getCategory() + ", itemGroup=" + this.getItemGroup() + ", unitMeasure=" + this.getUnitMeasure() + ", depreciationRate=" + this.getDepreciationRate() + ", remainingQuantity=" + this.getRemainingQuantity() + ", createdAt=" + this.getCreatedAt() + ")";
    }

    public static class ItemBuilder {
        private UUID id;
        private String name;
        private String acronym;
        private String description;
        private ItemCategory category;
        private ItemGroup itemGroup;
        private String unitMeasure;
        private Double depreciationRate;
        private Integer remainingQuantity;
        private LocalDateTime createdAt;

        ItemBuilder() {
        }

        public ItemBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public ItemBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ItemBuilder acronym(String acronym) {
            this.acronym = acronym;
            return this;
        }

        public ItemBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ItemBuilder category(ItemCategory category) {
            this.category = category;
            return this;
        }

        public ItemBuilder itemGroup(ItemGroup itemGroup) {
            this.itemGroup = itemGroup;
            return this;
        }

        public ItemBuilder unitMeasure(String unitMeasure) {
            this.unitMeasure = unitMeasure;
            return this;
        }

        public ItemBuilder depreciationRate(Double depreciationRate) {
            this.depreciationRate = depreciationRate;
            return this;
        }

        public ItemBuilder remainingQuantity(Integer remainingQuantity) {
            this.remainingQuantity = remainingQuantity;
            return this;
        }

        public ItemBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Item build() {
            return new Item(this.id, this.name, this.acronym, this.description, this.category, this.itemGroup, this.unitMeasure, this.depreciationRate, this.remainingQuantity, this.createdAt);
        }

        public String toString() {
            return "Item.ItemBuilder(id=" + this.id + ", name=" + this.name + ", acronym=" + this.acronym + ", description=" + this.description + ", category=" + this.category + ", itemGroup=" + this.itemGroup + ", unitMeasure=" + this.unitMeasure + ", depreciationRate=" + this.depreciationRate + ", remainingQuantity=" + this.remainingQuantity + ", createdAt=" + this.createdAt + ")";
        }
    }
}
