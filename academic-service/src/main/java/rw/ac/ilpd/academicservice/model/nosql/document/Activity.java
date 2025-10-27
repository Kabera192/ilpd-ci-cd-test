package rw.ac.ilpd.academicservice.model.nosql.document;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import rw.ac.ilpd.academicservice.model.nosql.embedding.ActivityLevel;
import rw.ac.ilpd.academicservice.model.nosql.embedding.ActivityOccurrence;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Document(collection = "aca_activities")
public class Activity {
    @Id
    private String id;

    private String title;

    private String description;

    private String activityTypeId;

    private UUID intakeId;

    private UUID roomId;

    @CreatedDate
    private LocalDateTime createdAt;

    private UUID createdById;

    private UUID componentId;

    private UUID moduleId;

    private UUID lecturerId;

    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate startDay;
    private LocalDate endDay;

    List<ActivityOccurrence> activityOccurrences;
    List<ActivityLevel> activityLevels;

    public Activity(String id, String title, String description, String activityTypeId, UUID intakeId, UUID roomId, LocalDateTime createdAt, UUID createdById, UUID componentId, UUID moduleId, UUID lecturerId, LocalTime startTime, LocalTime endTime, LocalDate startDay, LocalDate endDay, List<ActivityOccurrence> activityOccurrences, List<ActivityLevel> activityLevels) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.activityTypeId = activityTypeId;
        this.intakeId = intakeId;
        this.roomId = roomId;
        this.createdAt = createdAt;
        this.createdById = createdById;
        this.componentId = componentId;
        this.moduleId = moduleId;
        this.lecturerId = lecturerId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startDay = startDay;
        this.endDay = endDay;
        this.activityOccurrences = activityOccurrences;
        this.activityLevels = activityLevels;
    }

    public Activity() {
    }

    public static ActivityBuilder builder() {
        return new ActivityBuilder();
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getActivityTypeId() {
        return this.activityTypeId;
    }

    public UUID getIntakeId() {
        return this.intakeId;
    }

    public UUID getRoomId() {
        return this.roomId;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public UUID getCreatedById() {
        return this.createdById;
    }

    public UUID getComponentId() {
        return this.componentId;
    }

    public UUID getModuleId() {
        return this.moduleId;
    }

    public UUID getLecturerId() {
        return this.lecturerId;
    }

    public LocalTime getStartTime() {
        return this.startTime;
    }

    public LocalTime getEndTime() {
        return this.endTime;
    }

    public LocalDate getStartDay() {
        return this.startDay;
    }

    public LocalDate getEndDay() {
        return this.endDay;
    }

    public List<ActivityOccurrence> getActivityOccurrences() {
        return this.activityOccurrences;
    }

    public List<ActivityLevel> getActivityLevels() {
        return this.activityLevels;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setActivityTypeId(String activityTypeId) {
        this.activityTypeId = activityTypeId;
    }

    public void setIntakeId(UUID intakeId) {
        this.intakeId = intakeId;
    }

    public void setRoomId(UUID roomId) {
        this.roomId = roomId;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setCreatedById(UUID createdById) {
        this.createdById = createdById;
    }

    public void setComponentId(UUID componentId) {
        this.componentId = componentId;
    }

    public void setModuleId(UUID moduleId) {
        this.moduleId = moduleId;
    }

    public void setLecturerId(UUID lecturerId) {
        this.lecturerId = lecturerId;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setStartDay(LocalDate startDay) {
        this.startDay = startDay;
    }

    public void setEndDay(LocalDate endDay) {
        this.endDay = endDay;
    }

    public void setActivityOccurrences(List<ActivityOccurrence> activityOccurrences) {
        this.activityOccurrences = activityOccurrences;
    }

    public void setActivityLevels(List<ActivityLevel> activityLevels) {
        this.activityLevels = activityLevels;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Activity)) return false;
        final Activity other = (Activity) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$title = this.getTitle();
        final Object other$title = other.getTitle();
        if (this$title == null ? other$title != null : !this$title.equals(other$title)) return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description))
            return false;
        final Object this$activityTypeId = this.getActivityTypeId();
        final Object other$activityTypeId = other.getActivityTypeId();
        if (this$activityTypeId == null ? other$activityTypeId != null : !this$activityTypeId.equals(other$activityTypeId))
            return false;
        final Object this$intakeId = this.getIntakeId();
        final Object other$intakeId = other.getIntakeId();
        if (this$intakeId == null ? other$intakeId != null : !this$intakeId.equals(other$intakeId)) return false;
        final Object this$roomId = this.getRoomId();
        final Object other$roomId = other.getRoomId();
        if (this$roomId == null ? other$roomId != null : !this$roomId.equals(other$roomId)) return false;
        final Object this$createdAt = this.getCreatedAt();
        final Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        final Object this$createdById = this.getCreatedById();
        final Object other$createdById = other.getCreatedById();
        if (this$createdById == null ? other$createdById != null : !this$createdById.equals(other$createdById))
            return false;
        final Object this$componentId = this.getComponentId();
        final Object other$componentId = other.getComponentId();
        if (this$componentId == null ? other$componentId != null : !this$componentId.equals(other$componentId))
            return false;
        final Object this$moduleId = this.getModuleId();
        final Object other$moduleId = other.getModuleId();
        if (this$moduleId == null ? other$moduleId != null : !this$moduleId.equals(other$moduleId)) return false;
        final Object this$lecturerId = this.getLecturerId();
        final Object other$lecturerId = other.getLecturerId();
        if (this$lecturerId == null ? other$lecturerId != null : !this$lecturerId.equals(other$lecturerId))
            return false;
        final Object this$startTime = this.getStartTime();
        final Object other$startTime = other.getStartTime();
        if (this$startTime == null ? other$startTime != null : !this$startTime.equals(other$startTime)) return false;
        final Object this$endTime = this.getEndTime();
        final Object other$endTime = other.getEndTime();
        if (this$endTime == null ? other$endTime != null : !this$endTime.equals(other$endTime)) return false;
        final Object this$startDay = this.getStartDay();
        final Object other$startDay = other.getStartDay();
        if (this$startDay == null ? other$startDay != null : !this$startDay.equals(other$startDay)) return false;
        final Object this$endDay = this.getEndDay();
        final Object other$endDay = other.getEndDay();
        if (this$endDay == null ? other$endDay != null : !this$endDay.equals(other$endDay)) return false;
        final Object this$activityOccurrences = this.getActivityOccurrences();
        final Object other$activityOccurrences = other.getActivityOccurrences();
        if (this$activityOccurrences == null ? other$activityOccurrences != null : !this$activityOccurrences.equals(other$activityOccurrences))
            return false;
        final Object this$activityLevels = this.getActivityLevels();
        final Object other$activityLevels = other.getActivityLevels();
        if (this$activityLevels == null ? other$activityLevels != null : !this$activityLevels.equals(other$activityLevels))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Activity;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $title = this.getTitle();
        result = result * PRIME + ($title == null ? 43 : $title.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        final Object $activityTypeId = this.getActivityTypeId();
        result = result * PRIME + ($activityTypeId == null ? 43 : $activityTypeId.hashCode());
        final Object $intakeId = this.getIntakeId();
        result = result * PRIME + ($intakeId == null ? 43 : $intakeId.hashCode());
        final Object $roomId = this.getRoomId();
        result = result * PRIME + ($roomId == null ? 43 : $roomId.hashCode());
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        final Object $createdById = this.getCreatedById();
        result = result * PRIME + ($createdById == null ? 43 : $createdById.hashCode());
        final Object $componentId = this.getComponentId();
        result = result * PRIME + ($componentId == null ? 43 : $componentId.hashCode());
        final Object $moduleId = this.getModuleId();
        result = result * PRIME + ($moduleId == null ? 43 : $moduleId.hashCode());
        final Object $lecturerId = this.getLecturerId();
        result = result * PRIME + ($lecturerId == null ? 43 : $lecturerId.hashCode());
        final Object $startTime = this.getStartTime();
        result = result * PRIME + ($startTime == null ? 43 : $startTime.hashCode());
        final Object $endTime = this.getEndTime();
        result = result * PRIME + ($endTime == null ? 43 : $endTime.hashCode());
        final Object $startDay = this.getStartDay();
        result = result * PRIME + ($startDay == null ? 43 : $startDay.hashCode());
        final Object $endDay = this.getEndDay();
        result = result * PRIME + ($endDay == null ? 43 : $endDay.hashCode());
        final Object $activityOccurrences = this.getActivityOccurrences();
        result = result * PRIME + ($activityOccurrences == null ? 43 : $activityOccurrences.hashCode());
        final Object $activityLevels = this.getActivityLevels();
        result = result * PRIME + ($activityLevels == null ? 43 : $activityLevels.hashCode());
        return result;
    }

    public String toString() {
        return "Activity(id=" + this.getId() + ", title=" + this.getTitle() + ", description=" + this.getDescription() + ", activityTypeId=" + this.getActivityTypeId() + ", intakeId=" + this.getIntakeId() + ", roomId=" + this.getRoomId() + ", createdAt=" + this.getCreatedAt() + ", createdById=" + this.getCreatedById() + ", componentId=" + this.getComponentId() + ", moduleId=" + this.getModuleId() + ", lecturerId=" + this.getLecturerId() + ", startTime=" + this.getStartTime() + ", endTime=" + this.getEndTime() + ", startDay=" + this.getStartDay() + ", endDay=" + this.getEndDay() + ", activityOccurrences=" + this.getActivityOccurrences() + ", activityLevels=" + this.getActivityLevels() + ")";
    }

    public static class ActivityBuilder {
        private String id;
        private String title;
        private String description;
        private String activityTypeId;
        private UUID intakeId;
        private UUID roomId;
        private LocalDateTime createdAt;
        private UUID createdById;
        private UUID componentId;
        private UUID moduleId;
        private UUID lecturerId;
        private LocalTime startTime;
        private LocalTime endTime;
        private LocalDate startDay;
        private LocalDate endDay;
        private List<ActivityOccurrence> activityOccurrences;
        private List<ActivityLevel> activityLevels;

        ActivityBuilder() {
        }

        public ActivityBuilder id(String id) {
            this.id = id;
            return this;
        }

        public ActivityBuilder title(String title) {
            this.title = title;
            return this;
        }

        public ActivityBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ActivityBuilder activityTypeId(String activityTypeId) {
            this.activityTypeId = activityTypeId;
            return this;
        }

        public ActivityBuilder intakeId(UUID intakeId) {
            this.intakeId = intakeId;
            return this;
        }

        public ActivityBuilder roomId(UUID roomId) {
            this.roomId = roomId;
            return this;
        }

        public ActivityBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ActivityBuilder createdById(UUID createdById) {
            this.createdById = createdById;
            return this;
        }

        public ActivityBuilder componentId(UUID componentId) {
            this.componentId = componentId;
            return this;
        }

        public ActivityBuilder moduleId(UUID moduleId) {
            this.moduleId = moduleId;
            return this;
        }

        public ActivityBuilder lecturerId(UUID lecturerId) {
            this.lecturerId = lecturerId;
            return this;
        }

        public ActivityBuilder startTime(LocalTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public ActivityBuilder endTime(LocalTime endTime) {
            this.endTime = endTime;
            return this;
        }

        public ActivityBuilder startDay(LocalDate startDay) {
            this.startDay = startDay;
            return this;
        }

        public ActivityBuilder endDay(LocalDate endDay) {
            this.endDay = endDay;
            return this;
        }

        public ActivityBuilder activityOccurrences(List<ActivityOccurrence> activityOccurrences) {
            this.activityOccurrences = activityOccurrences;
            return this;
        }

        public ActivityBuilder activityLevels(List<ActivityLevel> activityLevels) {
            this.activityLevels = activityLevels;
            return this;
        }

        public Activity build() {
            return new Activity(this.id, this.title, this.description, this.activityTypeId, this.intakeId, this.roomId, this.createdAt, this.createdById, this.componentId, this.moduleId, this.lecturerId, this.startTime, this.endTime, this.startDay, this.endDay, this.activityOccurrences, this.activityLevels);
        }

        public String toString() {
            return "Activity.ActivityBuilder(id=" + this.id + ", title=" + this.title + ", description=" + this.description + ", activityTypeId=" + this.activityTypeId + ", intakeId=" + this.intakeId + ", roomId=" + this.roomId + ", createdAt=" + this.createdAt + ", createdById=" + this.createdById + ", componentId=" + this.componentId + ", moduleId=" + this.moduleId + ", lecturerId=" + this.lecturerId + ", startTime=" + this.startTime + ", endTime=" + this.endTime + ", startDay=" + this.startDay + ", endDay=" + this.endDay + ", activityOccurrences=" + this.activityOccurrences + ", activityLevels=" + this.activityLevels + ")";
        }
    }
}
