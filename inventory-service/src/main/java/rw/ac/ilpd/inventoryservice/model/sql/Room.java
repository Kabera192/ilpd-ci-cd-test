package rw.ac.ilpd.inventoryservice.model.sql;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Stores details about rooms for academic and administrative purposes such as
 * classrooms, offices and other rooms other than the hotel rooms.
 */
@Entity
@Table(name = "inv_rooms")
public class Room {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private UUID id;

    private String locationId;

    private String name;

    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    private RoomType roomTypeId;

    @CreationTimestamp
    private LocalDateTime createdAt;


    public Room(UUID id, String locationId, String name, String code, RoomType roomTypeId, LocalDateTime createdAt) {
        this.id = id;
        this.locationId = locationId;
        this.name = name;
        this.code = code;
        this.roomTypeId = roomTypeId;
        this.createdAt = createdAt;
    }

    public Room() {
    }

    public static RoomBuilder builder() {
        return new RoomBuilder();
    }

    public UUID getId() {
        return this.id;
    }

    public String getLocationId() {
        return this.locationId;
    }

    public String getName() {
        return this.name;
    }

    public String getCode() {
        return this.code;
    }

    public RoomType getRoomTypeId() {
        return this.roomTypeId;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setRoomTypeId(RoomType roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static class RoomBuilder {
        private UUID id;
        private String locationId;
        private String name;
        private String code;
        private RoomType roomTypeId;
        private LocalDateTime createdAt;

        RoomBuilder() {
        }

        public RoomBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public RoomBuilder locationId(String locationId) {
            this.locationId = locationId;
            return this;
        }

        public RoomBuilder name(String name) {
            this.name = name;
            return this;
        }

        public RoomBuilder code(String code) {
            this.code = code;
            return this;
        }

        public RoomBuilder roomTypeId(RoomType roomTypeId) {
            this.roomTypeId = roomTypeId;
            return this;
        }

        public RoomBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Room build() {
            return new Room(this.id, this.locationId, this.name, this.code, this.roomTypeId, this.createdAt);
        }

        public String toString() {
            return "Room.RoomBuilder(id=" + this.id + ", locationId=" + this.locationId + ", name=" + this.name + ", code=" + this.code + ", roomTypeId=" + this.roomTypeId + ", createdAt=" + this.createdAt + ")";
        }
    }
}
