package rw.ac.ilpd.reportingservice.model.nosql.document;

import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "report_settings")
public class Setting {
    @Id
    private String id;
    private String name;
    private String description;
    private String category;
    private String acronym;
    @Indexed(unique = true)
    private String key;
    private String value;
    private boolean isEnabled;
    private boolean isDeleted;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @CreatedBy
    private String createdBy;
    @LastModifiedBy
    private String updatedBy;

    public Setting(String id, String name, String description, String category, String acronym, String key, String value, boolean isEnabled, boolean isDeleted, LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy, String updatedBy) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.acronym = acronym;
        this.key = key;
        this.value = value;
        this.isEnabled = isEnabled;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    public Setting() {
    }

    public static SettingBuilder builder() {
        return new SettingBuilder();
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getCategory() {
        return this.category;
    }

    public String getAcronym() {
        return this.acronym;
    }

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

    public boolean isEnabled() {
        return this.isEnabled;
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

    public String getCreatedBy() {
        return this.createdBy;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
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

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String toString() {
        return "Setting(id=" + this.getId() + ", name=" + this.getName() + ", description=" + this.getDescription() + ", category=" + this.getCategory() + ", acronym=" + this.getAcronym() + ", key=" + this.getKey() + ", value=" + this.getValue() + ", isEnabled=" + this.isEnabled() + ", isDeleted=" + this.isDeleted() + ", createdAt=" + this.getCreatedAt() + ", updatedAt=" + this.getUpdatedAt() + ", createdBy=" + this.getCreatedBy() + ", updatedBy=" + this.getUpdatedBy() + ")";
    }

    public static class SettingBuilder {
        private String id;
        private String name;
        private String description;
        private String category;
        private String acronym;
        private String key;
        private String value;
        private boolean isEnabled;
        private boolean isDeleted;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String createdBy;
        private String updatedBy;

        SettingBuilder() {
        }

        public SettingBuilder id(String id) {
            this.id = id;
            return this;
        }

        public SettingBuilder name(String name) {
            this.name = name;
            return this;
        }

        public SettingBuilder description(String description) {
            this.description = description;
            return this;
        }

        public SettingBuilder category(String category) {
            this.category = category;
            return this;
        }

        public SettingBuilder acronym(String acronym) {
            this.acronym = acronym;
            return this;
        }

        public SettingBuilder key(String key) {
            this.key = key;
            return this;
        }

        public SettingBuilder value(String value) {
            this.value = value;
            return this;
        }

        public SettingBuilder isEnabled(boolean isEnabled) {
            this.isEnabled = isEnabled;
            return this;
        }

        public SettingBuilder isDeleted(boolean isDeleted) {
            this.isDeleted = isDeleted;
            return this;
        }

        public SettingBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public SettingBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public SettingBuilder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public SettingBuilder updatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
            return this;
        }

        public Setting build() {
            return new Setting(this.id, this.name, this.description, this.category, this.acronym, this.key, this.value, this.isEnabled, this.isDeleted, this.createdAt, this.updatedAt, this.createdBy, this.updatedBy);
        }

        public String toString() {
            return "Setting.SettingBuilder(id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", category=" + this.category + ", acronym=" + this.acronym + ", key=" + this.key + ", value=" + this.value + ", isEnabled=" + this.isEnabled + ", isDeleted=" + this.isDeleted + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + ", createdBy=" + this.createdBy + ", updatedBy=" + this.updatedBy + ")";
        }
    }
}
