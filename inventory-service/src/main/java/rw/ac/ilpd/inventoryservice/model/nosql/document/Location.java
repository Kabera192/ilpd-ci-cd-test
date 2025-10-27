package rw.ac.ilpd.inventoryservice.model.nosql.document;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import rw.ac.ilpd.sharedlibrary.enums.BlockType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * This entity stores details of various locations needed in the MIS
 * such as campus locations, event locations among others.
 */
@Document(collection = "inv_locations")
public class Location {
    @Id
    private String id;

    private String name;

    private LocationType type;

    private String parentLocationId;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private BlockType blockType;

    private boolean deleteStatus;

    @CreatedDate
    private LocalDateTime createdAt;

    public Location() {
    }

    public Location(String id, String name, LocationType type, String parentLocationId, BigDecimal lat, BigDecimal lon, BlockType blockType, boolean deleteStatus, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.parentLocationId = parentLocationId;
        this.latitude = lat;
        this.longitude = lon;
        this.blockType = blockType;
        this.deleteStatus = deleteStatus;
        this.createdAt = createdAt;
    }

    public Location(String name, LocationType locationType) {
        this.name = name;
        this.type = locationType;
        this.createdAt = LocalDateTime.now();
        this.deleteStatus = false;
    }

    public static LocationBuilder builder() {
        return new LocationBuilder();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocationType getType() {
        return type;
    }

    public void setType(LocationType type) {
        this.type = type;
    }

    public String getParentLocationId() {
        return parentLocationId;
    }

    public void setParentLocationId(String parentLocationId) {
        this.parentLocationId = parentLocationId;
    }

    public BigDecimal getLat() {
        return latitude;
    }

    public void setLat(BigDecimal lat) {
        this.latitude = lat;
    }

    public BigDecimal getLon() {
        return longitude;
    }

    public void setLon(BigDecimal lon) {
        this.longitude = lon;
    }

    public BlockType getBlockType() {
        return blockType;
    }

    public void setBlockType(BlockType blockType) {
        this.blockType = blockType;
    }

    public boolean isDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id: '" + id + '\'' +
                ", name: '" + name + '\'' +
                ", type: " + type +
                ", parentLocationId: '" + parentLocationId + '\'' +
                ", lat: " + latitude +
                ", lon: " + longitude +
                ", blockType: " + blockType +
                ", deleteStatus: " + deleteStatus +
                ", createdAt: " + createdAt +
                '}';
    }

    public static class LocationBuilder {
        private String id;
        private String name;
        private LocationType type;
        private String parentLocationId;
        private BigDecimal latitude;
        private BigDecimal longitude;
        private BlockType blockType;
        private boolean deleteStatus;
        private LocalDateTime createdAt;

        LocationBuilder() {
        }

        public LocationBuilder id(String id) {
            this.id = id;
            return this;
        }

        public LocationBuilder name(String name) {
            this.name = name;
            return this;
        }

        public LocationBuilder type(LocationType type) {
            this.type = type;
            return this;
        }

        public LocationBuilder parentLocationId(String parentLocationId) {
            this.parentLocationId = parentLocationId;
            return this;
        }

        public LocationBuilder latitude(BigDecimal latitude) {
            this.latitude = latitude;
            return this;
        }

        public LocationBuilder longitude(BigDecimal longitude) {
            this.longitude = longitude;
            return this;
        }

        public LocationBuilder blockType(BlockType blockType) {
            this.blockType = blockType;
            return this;
        }

        public LocationBuilder deleteStatus(boolean deleteStatus) {
            this.deleteStatus = deleteStatus;
            return this;
        }

        public LocationBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Location build() {
            return new Location(this.id, this.name, this.type, this.parentLocationId, this.latitude, this.longitude, this.blockType, this.deleteStatus, this.createdAt);
        }

        public String toString() {
            return "Location.LocationBuilder(id=" + this.id + ", name=" + this.name + ", type=" + this.type + ", parentLocationId=" + this.parentLocationId + ", latitude=" + this.latitude + ", longitude=" + this.longitude + ", blockType=" + this.blockType + ", deleteStatus=" + this.deleteStatus + ", createdAt=" + this.createdAt + ")";
        }
    }
}
