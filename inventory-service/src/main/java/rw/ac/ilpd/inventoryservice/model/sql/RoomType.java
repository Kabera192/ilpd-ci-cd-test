package rw.ac.ilpd.inventoryservice.model.sql;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

/**
 * Defines various types of rooms that are at ILPD such as classrooms, offices and so on.
 */
@Entity
@Table(name = "inv_room_types")

public class RoomType {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private UUID id;

    private String name;

    public RoomType() {
    }

    public RoomType(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public static RoomTypeBuilder builder() {
        return new RoomTypeBuilder();
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

    @Override
    public String toString() {
        return "RoomType{" +
                "id: " + id +
                ", name: '" + name + '\'' +
                '}';
    }

    public static class RoomTypeBuilder {
        private UUID id;
        private String name;

        RoomTypeBuilder() {
        }

        public RoomTypeBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public RoomTypeBuilder name(String name) {
            this.name = name;
            return this;
        }

        public RoomType build() {
            return new RoomType(this.id, this.name);
        }

        public String toString() {
            return "RoomType.RoomTypeBuilder(id=" + this.id + ", name=" + this.name + ")";
        }
    }
}
