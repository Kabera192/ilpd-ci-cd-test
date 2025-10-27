package rw.ac.ilpd.academicservice.model.nosql.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "aca_activity_types")
public class ActivityType {
    @Id
    private String id;
    private String name;
    private String description;
    private boolean isDeleted = false;

    public ActivityType(String id, String name, String description, boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isDeleted = isDeleted;
    }

    public ActivityType() {
    }

    private static boolean $default$isDeleted() {
        return false;
    }

    public static ActivityTypeBuilder builder() {
        return new ActivityTypeBuilder();
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

    public boolean isDeleted() {
        return this.isDeleted;
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

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String toString() {
        return "ActivityType(id=" + this.getId() + ", name=" + this.getName() + ", description=" + this.getDescription() + ", isDeleted=" + this.isDeleted() + ")";
    }

    public static class ActivityTypeBuilder {
        private String id;
        private String name;
        private String description;
        private boolean isDeleted$value;
        private boolean isDeleted$set;

        ActivityTypeBuilder() {
        }

        public ActivityTypeBuilder id(String id) {
            this.id = id;
            return this;
        }

        public ActivityTypeBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ActivityTypeBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ActivityTypeBuilder isDeleted(boolean isDeleted) {
            this.isDeleted$value = isDeleted;
            this.isDeleted$set = true;
            return this;
        }

        public ActivityType build() {
            boolean isDeleted$value = this.isDeleted$value;
            if (!this.isDeleted$set) {
                isDeleted$value = ActivityType.$default$isDeleted();
            }
            return new ActivityType(this.id, this.name, this.description, isDeleted$value);
        }

        public String toString() {
            return "ActivityType.ActivityTypeBuilder(id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", isDeleted$value=" + this.isDeleted$value + ")";
        }
    }
}
