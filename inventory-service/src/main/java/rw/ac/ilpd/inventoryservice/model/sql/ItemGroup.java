package rw.ac.ilpd.inventoryservice.model.sql;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * This entity represents groups that various stock items can be placed
 * under for better organization.
 */
@Entity
@Table(name = "inv_item_groups")
public class ItemGroup {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private UUID id;

    private String name;

    private String acronym;

    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public ItemGroup() {
    }

    public ItemGroup(UUID id, String name, String acronym, String description, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.acronym = acronym;
        this.description = description;
        this.createdAt = createdAt;
    }

    public static ItemGroupBuilder builder() {
        return new ItemGroupBuilder();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "ItemGroup{" +
                "id: " + id +
                ", name: '" + name + '\'' +
                ", acronym: '" + acronym + '\'' +
                ", description: '" + description + '\'' +
                ", createdAt: " + createdAt +
                '}';
    }

    public static class ItemGroupBuilder {
        private UUID id;
        private String name;
        private String acronym;
        private String description;
        private LocalDateTime createdAt;

        ItemGroupBuilder() {
        }

        public ItemGroupBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public ItemGroupBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ItemGroupBuilder acronym(String acronym) {
            this.acronym = acronym;
            return this;
        }

        public ItemGroupBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ItemGroupBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ItemGroup build() {
            return new ItemGroup(this.id, this.name, this.acronym, this.description, this.createdAt);
        }

        public String toString() {
            return "ItemGroup.ItemGroupBuilder(id=" + this.id + ", name=" + this.name + ", acronym=" + this.acronym + ", description=" + this.description + ", createdAt=" + this.createdAt + ")";
        }
    }
}
