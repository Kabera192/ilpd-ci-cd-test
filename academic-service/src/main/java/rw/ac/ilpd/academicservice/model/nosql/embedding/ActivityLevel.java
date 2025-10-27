package rw.ac.ilpd.academicservice.model.nosql.embedding;

import rw.ac.ilpd.sharedlibrary.enums.ActivityLevelLevels;

import java.util.UUID;

public class ActivityLevel {
    private String id = UUID.randomUUID().toString();
    private ActivityLevelLevels level;
    private UUID levelRefId;

    @Override
    public String toString() {
        return "ActivityLevel{" +
                "id='" + id + '\'' +
                ", level=" + level +
                ", levelRefId=" + levelRefId +
                '}';
    }

    public ActivityLevel(String id, ActivityLevelLevels level, UUID levelRefId) {
        this.id = id;
        this.level = level;
        this.levelRefId = levelRefId;
    }

    public ActivityLevel() {
    }

    public static ActivityLevelBuilder builder() {
        return new ActivityLevelBuilder();
    }

    public String getId() {
        return this.id;
    }

    public ActivityLevelLevels getLevel() {
        return this.level;
    }

    public UUID getLevelRefId() {
        return this.levelRefId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLevel(ActivityLevelLevels level) {
        this.level = level;
    }

    public void setLevelRefId(UUID levelRefId) {
        this.levelRefId = levelRefId;
    }

    public static class ActivityLevelBuilder {
        private String id;
        private ActivityLevelLevels level;
        private UUID levelRefId;

        ActivityLevelBuilder() {
        }

        public ActivityLevelBuilder id(String id) {
            this.id = id;
            return this;
        }

        public ActivityLevelBuilder level(ActivityLevelLevels level) {
            this.level = level;
            return this;
        }

        public ActivityLevelBuilder levelRefId(UUID levelRefId) {
            this.levelRefId = levelRefId;
            return this;
        }

        public ActivityLevel build() {
            return new ActivityLevel(this.id, this.level, this.levelRefId);
        }

        public String toString() {
            return "ActivityLevel.ActivityLevelBuilder(id=" + this.id + ", level=" + this.level + ", levelRefId=" + this.levelRefId + ")";
        }
    }
}
