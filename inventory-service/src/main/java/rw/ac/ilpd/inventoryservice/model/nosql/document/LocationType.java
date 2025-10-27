package rw.ac.ilpd.inventoryservice.model.nosql.document;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * This entity classifies locations into various categories such as campus, province, among others.
 */
@Document
public class LocationType {
    @Id
    private String id;

    private String name;

    private boolean deleteStatus;

    @CreatedDate
    private LocalDateTime createdAt;

    public LocationType() {
    }

    public LocationType(String id, String name, boolean deleteStatus, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.deleteStatus = deleteStatus;
        this.createdAt = createdAt;
    }

    public static LocationTypeBuilder builder() {
        return new LocationTypeBuilder();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "LocationType{" +
                "id: '" + id + '\'' +
                ", name: '" + name + '\'' +
                ", deleteStatus: " + deleteStatus +
                ", createdAt: " + createdAt +
                '}';
    }

    public static class LocationTypeBuilder {
        private String id;
        private String name;
        private boolean deleteStatus;
        private LocalDateTime createdAt;

        LocationTypeBuilder() {
        }

        public LocationTypeBuilder id(String id) {
            this.id = id;
            return this;
        }

        public LocationTypeBuilder name(String name) {
            this.name = name;
            return this;
        }

        public LocationTypeBuilder deleteStatus(boolean deleteStatus) {
            this.deleteStatus = deleteStatus;
            return this;
        }

        public LocationTypeBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public LocationType build() {
            return new LocationType(this.id, this.name, this.deleteStatus, this.createdAt);
        }

        public String toString() {
            return "LocationType.LocationTypeBuilder(id=" + this.id + ", name=" + this.name + ", deleteStatus=" + this.deleteStatus + ", createdAt=" + this.createdAt + ")";
        }
    }
}
