package rw.ac.ilpd.reportingservice.model.nosql.document;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "report_positions")
public class Position {
    @Id
    private String id;
    private String name;
    private String abbreviation;
    private boolean isDeleted;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Position(String id, String name, String abbreviation, boolean isDeleted, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.abbreviation = abbreviation;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Position() {
    }

    public static PositionBuilder builder() {
        return new PositionBuilder();
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getAbbreviation() {
        return this.abbreviation;
    }

    public boolean isDeleted() {
        return this.isDeleted;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String toString() {
        return "Position(id=" + this.getId() + ", name=" + this.getName() + ", abbreviation=" + this.getAbbreviation() + ", isDeleted=" + this.isDeleted() + ", createdAt=" + this.getCreatedAt() + ", updatedAt=" + this.getUpdatedAt() + ")";
    }

    public static class PositionBuilder {
        private String id;
        private String name;
        private String abbreviation;
        private boolean isDeleted;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        PositionBuilder() {
        }

        public PositionBuilder id(String id) {
            this.id = id;
            return this;
        }

        public PositionBuilder name(String name) {
            this.name = name;
            return this;
        }

        public PositionBuilder abbreviation(String abbreviation) {
            this.abbreviation = abbreviation;
            return this;
        }

        public PositionBuilder isDeleted(boolean isDeleted) {
            this.isDeleted = isDeleted;
            return this;
        }

        public PositionBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public PositionBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Position build() {
            return new Position(this.id, this.name, this.abbreviation, this.isDeleted, this.createdAt, this.updatedAt);
        }

        public String toString() {
            return "Position.PositionBuilder(id=" + this.id + ", name=" + this.name + ", abbreviation=" + this.abbreviation + ", isDeleted=" + this.isDeleted + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + ")";
        }
    }
}
